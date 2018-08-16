package com.zhaowb.netty.ch12;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {"动手成功，伸手落空。",
            "想喝甜水自己挑。",
            "路要自己走，关要自己闯。",
            "天上下雨地下滑，自己跌倒自己爬。",
            "家有斗量金，不如自己有本领。",
            "一等二靠三落空，一想二干三成功。",
            "不怕别人瞧不起，就怕自己不争气。",
            "锯一响就有锯末。",
            "血书必须要血写。（西班牙谚语）",
            "与其悲叹自己的命运，不如相信自己的力量。（蒙古谚语）",
            "眼望高山，脚踏实地。",
            "不图一时乱拍手，只求他日暗点头。",
            "看到了目标，并没有到达目的地。",
            "临渊羡鱼，不如退而结网。",
            "虽然有了好种子，庆贺丰收还太早。",
            "要想成功，必须走完从说到做这段路。",
            "喊破嗓子，不如甩开膀子。",
            "不实心，不成事；不虚心，不知事。",
            "道儿是人走出来的，辄儿是车轧出来的。",
            "别只看骑车马如飞龙，也要看他驯马时留下的伤痕。",
            "打鱼的不离河，打柴的不离山。",
            "芳香的花不一定好看，能干得人不一定会说。（高山族谚语）",
            "清晨的阳光不算温暖，瞬息的安逸不算幸福。（蒙古谚语）",
            "垦耕者讲实干，懒惰者讲茶饭。（蒙古谚语）",
            "想，要壮志凌云；干，要脚踏实地。（缅甸谚语）",
            "多深的地基，垒多高的墙。（日本谚语）",
            "巴黎不是一天建成的。（法国谚语）",
            "如果不废力，东西没价值。（西班牙谚语）",
            "一个实际行动，胜过一打纲领。（德国谚语）",
            "不怕无能，就怕无恒。",
            "竹子是一节一节长出来的；功夫是一天一天练出来的。",
            "绳锯木断，水滴石穿。",
            "如果每天挖掉一些土，就是高山也能铲平。",
            "冰冻三尺，非一日之寒。",
            "苦练日久，得心应手。",
            "人有恒，万事成；人无恒，万事空。",
            "胜利者不一定是跑得最快的人，而是最能持久的人。",
            "吃饭吃饱，做事做好。",
            "摆船要摆到岸。",
            "开弓没有回头箭。",
            "望远镜能展望前进的目标，却不能缩短要走的路程。",
            "大胆的尝试等于成功的一半。（美国谚语）",
            "虾儿虽小，却能遨游大海。（缅甸谚语）",
            "滴水也能装满缸。（尼泊尔谚语）",
            "慢火煮出好饴糖。（英国谚语）",
            "刀在石上磨，人在世上练。",
            "铁要打，人要练。",
            "老天不负勤苦人。",
            "艺高人胆大。",
            "身经百战，浑身是胆。",
            "钢不压不成材。",
            "有苦干的精神，事情便成功了一半。",
            "雨淋青松松更青，雪打红梅梅更红。",
            "不经风雨不成材，不经高温不成钢。",
            "铁是打出来的，马是骑出来的。",
            "夜越黑珍珠越亮，天越冷梅花越香。",
            "能力同肌肉一样，锻炼才能生长。",
            "好马要是三年不骑，会比炉子还笨。",
            "牡丹花好看，可没有菊花耐寒。",
            "不经琢磨，宝石也不会发光。（日本谚语）",
            "好舵手不怕风浪，好猎手不怕虎狼。",
            "敢伏虎者上高山，能降龙者下大海。",
            "要想挤狮奶，就得有斗狮的胆量。",
            "天不怕、地不怕，老虎屁股也要摸一下。",
            "人越勇敢，伴儿越多。",
            "树不坚硬虫来咬。",
            "骏马总是迎来暴风骤雨奔跑；冬青总是傲着严冬冰雪鼎立。",
            "不经长途，不知马骏。",
            "攀登高峰，就要和斜坡做斗争。",
            "敢过大江，不怕小河。",
            "治疮莫怕挖肉。",
            "戳了马蜂窝，不怕蜂子蛰。",
            "没有不遇风浪的海船，没有不遇雷雨的雄鹰。",
            "崎岖路上的石块，磨不破脚底的老茧。",
            "勇敢与成功如影随形。",
            "山高自有行路客，水深也有渡船人。",
            "险山不绝行路客，水深也有渡船人。",
            "不担险，练不出一身胆。",
            "走尽崎岖路，自有平坦路。",
            "好汉喜欢烈性的马。",
            "漫长的旅途检验好马，艰苦的历程考验英雄。",
            "怕火花的不是好铁匠。",
            "坚强的意志，不屈的精神，是勇士最亲密的战友。（哈萨克族谚语）",
            "坚强不屈是英雄的品质，贪生怕死是懦夫的本性。（哈萨克族谚语）",
            "辛勤寻求智慧的人，永远不向困难低头。（哈萨克族谚语）",
            "如果潜水的人惧怕鳄鱼，他就取不到价值昂贵的珍珠。（维吾尔族谚语）",
            "浪再大也在船底下，山再高也人脚下。（缅甸谚语）",
            "平静的海洋里，练不出优秀的航海家。（英国谚语）",
            "丧失勇气，丧失一切。（德国谚语）",
            "暴风雪折不断雄鹰的翅膀。（蒙古谚语）",
            "鹰有时比鸡飞的低，但鸡永远不能飞的像雄鹰那么高。（前苏联谚语）",
            "畏惧大海的人不会成为航海家。（东非谚语）",
            "谩骂不能使敌人退却，斗争才能赶走敌人。（马里谚语）",
            "狗对勇士只能狂吠，见了懦夫便咬脚后退。（马里谚语）",
            "惹暴躁者发怒很容易，叫傻瓜上当也不难，吓唬胆小鬼更是轻而易举。（马里谚语）",
            "莫笑跌跤的开路先锋。（非洲谚语）",
            "作战不光靠闪亮的武器，还要靠一颗英雄的心。（南斯拉夫谚语）",
            "身经百战成勇士。（柬埔寨谚语）",
            "打虎岂怕虎咬。",
            "要学游泳，必须下水。",
            "不穿尖头鞋，不知脚趾疼。",
            "看人担担不吃力，事非经过不知难。",
            "担子不在谁身上，谁不觉得重。",
            "自己的鞋，知道是紧在哪儿。（西班牙谚语）",
            "不是撑船手，休拿竹篙头。",
            "没有金刚钻，甭拦瓷器活。",
            "没有钩子嘴，吃不了瓢儿食。",
            "有吃刀子的嘴，还得有化刀子的肚子。",
            "有多大的脚，穿多大的鞋。",
            "棋逢对手难藏行。",
            "菜刀不能削自己的柄。",
            "马鞭虽长，不及马腹。",
            "聪明的艄公，不跟坏天气硬碰。",
            "一个人不能同时即纺纱又织布。（英国谚语）",
            "量力砍树，量才办事。（苏联谚语）",
            "心要热，头要冷。",
            "急躁越多，智慧越少。",
            "当最善于骑马的时候，需防止从马上跌跤。",
            "癫狂的马容易闪失，慌张的人会惹乱子。",
            "慢慢弯，脆木做成车轮；猛力压，软竹断为两截。",
            "有打鱼的时候，也有晒网的时候。",
            "长久拉直的弓是要坏的。",
            "拼命不能算勇敢。（西班牙谚语）",
            "挨金似金，挨玉似玉。",
            "八成熟，十成收；十成熟，二成丢。",
            "把舵的不慌，乘船的稳当。",
            "白米饭好吃，五谷田难种。",
            "百日连阴雨，总有一朝晴。",
            "百闻不如一见，百见不如一干。",
            "败家子挥金如粪，兴家人惜粪如金。",
            "帮人要帮到底，救人要救到头。",
            "帮助别人要忘掉，别人帮己要记牢。",
            "饱带饥粮，晴带雨伞。",
            "暴饮爆食易生病，定时定量保康宁。",
            "背后不商量，当面无主张。",
            "笨人先起身，笨鸟早出林。",
            "鞭打的快马，事找的忙人。",
            "病从口入，寒从脚起。",
            "病好不谢医，下次无人医。",
            "病急乱投医，逢庙就烧香。",
            "病来如山倒，病去如抽丝。",
            "病人心多，忙人事多。",
            "补漏趁天晴，读书趁年轻。",
            "不担三分险，难练一身胆。",
            "不当家不知柴米贵，不养儿不知父母恩。",
            "不到江边不脱鞋，不到火候不揭锅。",
            "不会烧香得罪神，不会讲话得罪人。",
            "不会做小事的人，也做不出大事来。",
            "不见兔子不撒鹰。",
            "不经冬寒，不知春暖。",
            "不可不算，不可全算。",
            "不磨不炼，不成好汉。",
            "不怕百事不利，就怕灰心丧气。",
            "不怕路长，只怕心老。",
            "不怕乱如麻，只怕不调查。",
            "不怕慢，就怕站；站一站，二里半。",
            "不怕年老，就怕躺倒。",
            "不怕人不敬，就怕己不正。",
            "不怕人不请，就怕艺不精。",
            "不怕山高，就怕脚软。",
            "不怕少年苦，只怕老来穷。",
            "不怕事难，就怕手懒。",
            "不怕天寒地冻，就怕手脚不动。",
            "不怕学不成，就怕心不诚。",
            "不怕学问浅，就怕志气短。",
            "不挑担子不知重，不走长路不知远。",
            "不听老人言，吃亏在眼前。",
            "不图便宜不上当，贪图便宜吃大亏。",
            "槽里无粮猪拱猪，分脏不均狗咬狗。",
            "草若无心不发芽，人若无心不发达。",
            "馋人家里没饭吃，懒人家里没柴烧。",
            "常赌无赢家。",
            "常骂不惊，常打不怕。",
            "常说口里顺，常做手不笨。",
            "常在有时思无时，莫到无时想有时。",
            "长江不据细流，泰山不择土石。",
            "长五月，短十月，不长不短二八月。",
            "长兄为父，老嫂比母。",
            "朝里有人好做官，家里有狗好看门。",
            "车到山前必有路，船到桥头自然直。",
            "车有车道，马有马路。",
            "撑痢疾，饿伤寒。",
            "撑死胆大的人，饿死胆小的鬼。",
            "秤能称轻重，话能量人心。",
            "秤砣虽小，能压千斤。",
            "吃不穷，穿不穷，不会打算一世穷。",
            "吃不言，睡不语。",
            "吃吃喝喝，人走下坡。",
            "吃饭防噎，走路防跌。",
            "吃饭先喝汤，老了不受伤。",
            "吃过的馍馍不香，嚼过的甘蔗不甜。",
            "吃酒不吃菜，必定醉的快。",
            "吃人家的嘴短，拿人家的手短。",
            "迟干不如早干，蛮干不如巧干。",
            "尺有所短，寸有所长。",
            "宠狗上灶，宠子不孝。",
            "出汗不迎风，走路不凹胸。",
            "出家三天，佛在面前；出家三年，佛在西天。",
            "出门看天色，炒菜看火色。",
            "出门靠朋友，在家靠父母。",
            "出门问路，入乡问俗。",
            "船头座的稳，不怕风来颠。",
            "船载千斤，掌舵一人。",
            "疮怕有名，病怕没名。",
            "创业百年，败家一天。",
            "吹嘘自己的人，等于在宣传他的无知。"};

    private String nextQuote(){
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {
        String req = packet.content().toString(CharsetUtil.UTF_8);// 将packet 内容转换为字符串（利用ByteBuf的toString（CharSet）方法）
        System.out.println(req);
        if ("谚语词典查询?".equals(req)){

            // DatagramPacket（发生的内容，目的地址（IP和端口））
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询结果:" + nextQuote(),CharsetUtil.UTF_8),packet.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
        cause.printStackTrace();
    }
}
