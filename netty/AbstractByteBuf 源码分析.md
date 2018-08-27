# 主要成员变量

```java
static final ResourceLeakDetector<ByteBuf> leakDetector = new ResourceLeakDetector<ByteBuf>(ByteBuf.class);

int readerIndex; // 读索引
private int writerIndex; // 写索引
private int markedReaderIndex;//
private int markedWriterIndex;//mark

private int maxCapacity;// 最大容量

private SwappedByteBuf swappedBuf;
```

leakDetector，被定义为static，所有的ByteBuf实例共享同一个ResourceLeakDetector对象。ResourceLeakDetector用于检测对象是否内存泄漏。

# 读操作簇

```java
@Override
public ByteBuf readBytes(byte[] dst, int dstIndex, int length) {
    checkReadableBytes(length);
    getBytes(readerIndex, dst, dstIndex, length);
    readerIndex += length;
    return this;
}
```

在读之前，首先对缓冲区的可用空间进行校验，如下

```java
/**
 * Throws an {@link IndexOutOfBoundsException} if the current
 * {@linkplain #readableBytes() readable bytes} of this buffer is less
 * than the specified value.
 */
protected final void checkReadableBytes(int minimumReadableBytes) {
    ensureAccessible();
    if (minimumReadableBytes < 0) {
        throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
    }
    if (readerIndex > writerIndex - minimumReadableBytes) {
        throw new IndexOutOfBoundsException(String.format(
                "readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s",
                readerIndex, minimumReadableBytes, writerIndex, this));
    }
}
```

如果长度小于0，则抛出`IllegalArgumentException`异常提示参数非法，如果可写的字节数小于需要读取的长度，则抛出`IndexOutOfBoundsException`异常，由于异常中封装了相信的异常信息，可以很方便的进行问题定位。

校验通过之后，调用`getBytes`方法，从当前的读索引开始，复制length个字节到目标byte数组中，由于不同的子类复制操作的技术实现细节不同，因此该方法由子类实现。

```java
/**
 * Transfers this buffer's data to the specified destination starting at
 * the specified absolute {@code index}.
 * This method does not modify {@code readerIndex} or {@code writerIndex}
 * of this buffer.
 *
 * @param dstIndex the first index of the destination
 * @param length   the number of bytes to transfer
 *
 * @throws IndexOutOfBoundsException
 *         if the specified {@code index} is less than {@code 0},
 *         if the specified {@code dstIndex} is less than {@code 0},
 *         if {@code index + length} is greater than
 *            {@code this.capacity}, or
 *         if {@code dstIndex + length} is greater than
 *            {@code dst.length}
 */
public abstract ByteBuf getBytes(int index, byte[] dst, int dstIndex, int length);
```

如果读取成功，需要对读索引进行递增：readIndex +=length.

# 写操作簇

写操作的公共行为在`AbstractByteBuf`中实现，

```java
@Override
public ByteBuf writeBytes(byte[] src, int srcIndex, int length) {
    ensureWritable(length);
    setBytes(writerIndex, src, srcIndex, length);
    writerIndex += length;
    return this;
}
```

将源字节数组中的从srcIndex 开始，到 srcIndex + length 截至的字节数组写入到当前的 ByteBuf。

首先对写入字节数组的长度进行合法性校验，如下

```java
@Override
public ByteBuf ensureWritable(int minWritableBytes) {
    if (minWritableBytes < 0) {
        throw new IllegalArgumentException(String.format(
                "minWritableBytes: %d (expected: >= 0)", minWritableBytes));
    }

    if (minWritableBytes <= writableBytes()) {
        return this;
    }

    if (minWritableBytes > maxCapacity - writerIndex) {
        throw new IndexOutOfBoundsException(String.format(
                "writerIndex(%d) + minWritableBytes(%d) exceeds maxCapacity(%d): %s",
                writerIndex, minWritableBytes, maxCapacity, this));
    }

    // Normalize the current capacity to the power of 2.
    int newCapacity = calculateNewCapacity(writerIndex + minWritableBytes);

    // Adjust to the new capacity.
    capacity(newCapacity);
    return this;
}
```

如果写入的字节数组长度小于0，则抛出 IllegalArgumentException 异常；如果写入的字节数组长度小于当前 ByteBuf 可写的字节数，说明可以写入成功，直接返回；如果写入的字节数组长度大于可以动态扩展的最大可写字节数，说明缓冲区无法写入超过其最大容量的字节数组，抛出 IndexOutOfBoundsException 异常。

	如果当前写入的字节数组长度虽然大于目前 ByteBuf 的可写字节数，但是同过自身的动态扩展可以满足新的写入请求，则进行动态扩展。

	netty的ByteBuf可以动态扩展，为了保证安全性，允许使用者指定最大的容量，在容量范围内，可以先分配个较小的初始容量，后面不够再动态扩展，这样可以达到功能和性能的最优组合。

calculateNewCapacity 首先需要重新计算下扩展后的容量，它有一个参数，等于 writerIndex + minWritableBytes ,也就是满足要求的最小容量。

```java
private int calculateNewCapacity(int minNewCapacity) {
    final int maxCapacity = this.maxCapacity;
    final int threshold = 1048576 * 4; // 4 MiB page

    if (minNewCapacity == threshold) {
        return threshold;
    }

    // If over threshold, do not double but just increase by threshold.
    if (minNewCapacity > threshold) {
        int newCapacity = minNewCapacity / threshold * threshold;
        if (newCapacity > maxCapacity - threshold) {
            newCapacity = maxCapacity;
        } else {
            newCapacity += threshold;
        }
        return newCapacity;
    }

    // Not over threshold. Double up to 4 MiB, starting from 64.
    int newCapacity = 64;
    while (newCapacity < minNewCapacity) {
        newCapacity <<= 1;
    }
	
    return Math.min(newCapacity, maxCapacity);
}
```

首先设置门限阈值为4M，当需要的新容量正好等于门限阈值，则使用阈值作为新的缓冲去容量。如果新申请的内存空间大于阈值，不能采用倍增的方式（防止内存膨胀和浪费）扩张内存，采用每次4M的方式进行内存扩张。扩张的时候需要对扩张后的内存和最大内存（maxCapacity） 进行比较，如果大于缓冲区的最大长度，则使用maxCapacity 作为扩容后的缓冲区容量。

	如果扩容后的新容量小于阈值，则以64为计数进行倍增，直到倍增后的结果大于或等于需要的容量值。

采用倍增或者步进算法的原因：如果以 minNewCapacity 作为目标容量，则本次扩容的可写字节数刚好够本次写入使用。写入完成后，它的可写字节数会变为0，下次做写入操作的时候，需要再次动态扩张。这样会形成第一次动态扩张后，每一次写入操作都会进行动态扩张，由于动态扩张需要进行内存复制，频繁的内存复制会导致性能下降。

	采用先倍增后步进的原因：当内存比较小的情况下，倍增操作并不会带来太多的内存浪费，例如64字节-->128字节-->256字节，这样的内存扩张方式对于大多数应用系统是可以接受的。但是当内存增长到一定阈值后，再进行倍增就可能会带来额外的内存浪费，例如10M，采用倍增后变为20M，很有可能系统只需要12M，扩张到20M后会带来8M内存的浪费。由于每个客户端连接都可能维护自己独立的接受和发送缓冲区，这样随着客户端的线性增长，内存浪费也会比例的增加，因此，达到某个阈值后就需要以步进的方式对内存进行平滑的扩张。

	这个阈值是个经验值，不同的应用场景，这个值可能不同，此处，ByteBuf取值为4M。

	重新计算完成动态扩张后的目标容量后，需要重新创建个新的缓冲区，将原缓冲区的内容复制到新创建的ByteBuf中，最后设置读写索引和mark标签等。不同的子类会对应不同的复制操作，所以该方法依然是一个抽象方法，由子类复制实现。

```java
/**
 * Adjusts the capacity of this buffer.  If the {@code newCapacity} is less than the current
 * capacity, the content of this buffer is truncated.  If the {@code newCapacity} is greater
 * than the current capacity, the buffer is appended with unspecified data whose length is
 * {@code (newCapacity - currentCapacity)}.
 */
public abstract ByteBuf capacity(int newCapacity);
```

# 操作索引

与索引相关的操作主要设计设置读写索引、mark和rest等

```java
@Override
public ByteBuf readerIndex(int readerIndex) {
    if (readerIndex < 0 || readerIndex > writerIndex) {
        throw new IndexOutOfBoundsException(String.format(
                "readerIndex: %d (expected: 0 <= readerIndex <= writerIndex(%d))", readerIndex, writerIndex));
    }
    this.readerIndex = readerIndex;
    return this;
}
```

在重新设置读索引之前需要对索引进行合法性判断，如果它小于0或者大于写索引，则抛出IndexOutOfBoundsException 异常，设置失败。校验通过之后，将索引设置为新的值，然后返回当前的ByteBuf对象。

# 重用缓冲区

```java
@Override
public ByteBuf discardReadBytes() {
    ensureAccessible();
    if (readerIndex == 0) {
        return this;
    }

    if (readerIndex != writerIndex) {
        setBytes(0, this, readerIndex, writerIndex - readerIndex);
        writerIndex -= readerIndex;
        adjustMarkers(readerIndex);
        readerIndex = 0;
    } else {
        adjustMarkers(readerIndex);
        writerIndex = readerIndex = 0;
    }
    return this;
}
```

首先都对读索引进行判断，如果为0则说明没有可重用的缓冲区，直接返回。如果读索引大于0  且读索引不等于写索引，说明缓冲区中既有已经读取过的被丢弃的缓冲区，也有尚未读取的可读缓冲区。调用`setBytes(0, this, readerIndex, writerIndex - readerIndex);`方法进行字节数组复制。将尚未读取的字节数组复制到缓冲区的起始位置，然后重新设置读写索引，读索引设置为0，写索引设置为之前的写索引减去读索引（重用的缓冲区长度）。

在设置读写索引的同时，需要同时调整 markedReaderIndex 和 markedWriterIndex，调整mark的代码如下

```java
protected final void adjustMarkers(int decrement) {
    int markedReaderIndex = this.markedReaderIndex;
    if (markedReaderIndex <= decrement) {
        this.markedReaderIndex = 0;
        int markedWriterIndex = this.markedWriterIndex;
        if (markedWriterIndex <= decrement) {
            this.markedWriterIndex = 0;
        } else {
            this.markedWriterIndex = markedWriterIndex - decrement;
        }
    } else {
        this.markedReaderIndex = markedReaderIndex - decrement;
        markedWriterIndex -= decrement;
    }
}
```

	首先对备份的markedReaderIndex和需要减少的decrement进行判断，如果小于需要的减少的值，则将markedReaderIndex设置为0，**注意**，无论`markedReaderIndex` 还是`markedWriterIndex` ，它的取值都不能小于0。如果markedWriterIndex也小于需要减少的值，则markedWriterIndex 设置为0，否则markedWriterIndex 减去decrement之后的值就是新的markedWriterIndex 。

	如果需要减小的值小于	markedReaderIndex，则它也一定也小于markedWriterIndex ，markedReaderIndex 和markedWriterIndex 的新值就是减去decrement之后的取值。

	如果readerIndex等于writerIndex,则说明没有可读的字节数组，那就不需要进行内存复制，直接调整mark，将读写索引设置为0即可完成缓冲区的重用.

```java
else {
        adjustMarkers(readerIndex);
        writerIndex = readerIndex = 0;
    }
// 没有可读的字节数组，不需要内存复制
```

# skipBytes

在解码的时候，有时候需要丢弃非法的数据报，或者跳跃过不需要读取的字节或字节数组，此时使用skipBytes就非常方便。它可以忽略指定长度的字节数组，读操作时直接跳过这些数据读取后面的可读的缓冲区。

```java
@Override
public ByteBuf skipBytes(int length) {
    checkReadableBytes(length);

    int newReaderIndex = readerIndex + length;
    if (newReaderIndex > writerIndex) {
        throw new IndexOutOfBoundsException(String.format(
                "length: %d (expected: readerIndex(%d) + length <= writerIndex(%d))",
                length, readerIndex, writerIndex));
    }
    readerIndex = newReaderIndex;
    return this;
}
```

首先判断跳过的长度是否大于当前缓冲区可读的字节数组长度，如果大于可读字节数组长度，则抛出IndexOutOfBoundsException；如果参数本身为负数，则抛出IllegalArgumentException异常。

校验的源码：

```java
/**
 * Throws an {@link IndexOutOfBoundsException} if the current
 * {@linkplain #readableBytes() readable bytes} of this buffer is less
 * than the specified value.
 */
protected final void checkReadableBytes(int minimumReadableBytes) {
    ensureAccessible();
    if (minimumReadableBytes < 0) {
        throw new IllegalArgumentException("minimumReadableBytes: " + minimumReadableBytes + " (expected: >= 0)");
    }
    if (readerIndex > writerIndex - minimumReadableBytes) {
        throw new IndexOutOfBoundsException(String.format(
                "readerIndex(%d) + length(%d) exceeds writerIndex(%d): %s",
                readerIndex, minimumReadableBytes, writerIndex, this));
    }
}
```
如果检验通过，则设置新的读索引为旧的索引值与跳跃的长度之和，然后对新的读索引进行判断，如果大于写索引，则抛出IndexOutOfBoundsException异常，如果合法，则读索引设置为新的读索引。这样后续的读操作的时候就会从新的读索引开始，跳过length个字节。