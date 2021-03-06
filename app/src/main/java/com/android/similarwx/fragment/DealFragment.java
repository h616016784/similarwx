package com.android.similarwx.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.similarwx.R;
import com.android.similarwx.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by hanhuailong on 2018/7/31.
 */

public class DealFragment extends BaseFragment {

    @BindView(R.id.deal_tv)
    TextView dealTv;
    Unbinder unbinder;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_deal;
    }

    @Override
    protected void onInitView(View contentView) {
        mActionbar.setTitle("用户协议");
        unbinder = ButterKnife.bind(this, contentView);
        String text = "泡泡社交\n" +
                "我们在此特别提醒您（用户）在注册成为用户之前，请认真阅读本《用户协议》（以下简称“协议”），确保您充分理解本协议中各条款。请您审慎阅读并选择接受或不接受本协议。除非您接受本协议所有条款，否则您无权注册、登录或使用本协议所涉服务。您的注册、登录、使用等行为将视为对本协议的接受，并同意接受本协议各项条款的约束。\n" +
                "本协议约定我们与用户之间关于“泡泡社交”软件服务（以下简称“服务”）的权利义务。“用户”是指注册、登录、使用本服务的个人。本协议可由我们随时更新，更新后的协议条款一旦公布即代替原来的协议条款，恕不再另行通知，用户可在本网站查阅最新版协议条款。在我们修改协议条款后，如果用户不接受修改后的条款，请立即停止使用我们提供的服务，用户继续使用我们提供的服务将被视为接受修改后的协议。\n" +
                "一、\t服务内容\n" +
                "1、\t本服务的具体内容由我们根据实际情况提供，包括但不限于授权用户通过其账号进行即时通讯、添加好友、加入群组、发布留言。我们可以对其提供的服务予以变更，且我们提供的服务内容可能随时变更；用户将会收到我们关于服务变更的通知。\n" +
                "2、\t我们积累了一些经验。泡泡社交推出的一款移动社交应用，用户可以通过泡泡社交私聊约会、群聊聚会等。\n" +
                "泡泡社交支持发送文字、图片以及地图位置，随时随地了解和认识身边的陌生人。泡泡社交推动了线上线下关系的有效转化，促进了陌生人之间的关系沉淀，拓展你的交际圈子，无论何时何地，通过泡泡社交你都可以认识新的、你感兴趣的人。\n" +
                "二、\t内容规范\n" +
                "1、\t本条所述内容是指用户使用本服务过程中所制作、上载、复制、发布、传播的任何内容，包括但不限于账号头像、名称、用户说明等注册信息及认证资料，或文字、语音、图片、视频、图文等发送、回复或自动回复消息和相关链接页面，以及其他使用账号或本服务所产生的内容。\n" +
                "2、\t用户不得利用“泡泡社交”账号或本服务制作、上载、复制、发布、传播如下法律、法规和政策禁止的内容：\n" +
                "(1)\t反对宪法所确定的基本原则的；\n" +
                "(2)\t危害国家安全，泄露国家秘密，颠覆国家政权，破坏国家统一的：\n" +
                "(3)\t损害国家荣誉和利益的：\n" +
                "(4)\t煽动民族仇恨、民族歧视，破坏民族团结的：\n" +
                "(5)\t破坏国家宗教政策，宣扬邪教和封建迷信的：\n" +
                "(6)\t散步谣言，扰乱社会秩序，破坏社会稳定的：\n" +
                "(7)\t散步淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；\n" +
                "(8)\t侮辱或者诽谤他人，侵害他人合法权益的；\n" +
                "(9)\t不遵守法律法规底线、社会主义制度底线、国家利益底线、公民合法权益底线、社会公共秩序底线、道德风尚底线和信息真实性底线的“七条底线”要求的；\n" +
                "(10)\t含有法律、行政法规禁止的其他内容的信息。\n" +
                "3、\t用户不得利用“泡泡社交”账号或本服务制作、上载、复制、发布、传播如下干扰“泡泡社交”正常运营，以及侵犯其他用户或第三方合法权益的内容：\n" +
                "（1）\t含有任何性或性暗示的；\n" +
                "（2）\t含有辱骂、恐吓、威胁内容的；\n" +
                "（3）\t含有骚扰、垃圾广告、恶意信息、诱骗信息的；\n" +
                "（4）\t涉及他人隐私、个人信息或资料的；\n" +
                "（5）\t侵害他人名誉权、肖像权、知识产权、商业秘密等合法权利的；\n" +
                "（6）\t含有其他干扰本服务正常运营和侵犯其他用户或第三方合法权益内容的信息。\n" +
                "三、\t使用规则\n" +
                "1、\t用户在本服务中或通过本服务所传送、发布任何内容并不反映或代表，也不得被视为反映或代表我们的观点、立场或政策，我们对此不承担任何责任。\n" +
                "2、\t用户不得利用“泡泡社交”账号或本服务进行如下行为；\n" +
                "（1）\t提交、发布虚假信息，或盗用他人头像或资料，冒充、利用他人名义的；\n" +
                "（2）\t强制、诱导其他用户关注、点击链接页面或分享信息的；\n" +
                "（3）\t虚构事实、隐瞒真相以误导、欺骗他人的；\n" +
                "（4）\t利用技术手段批量建立虚假账号的；\n" +
                "（5）\t利用“泡泡社交”账号或本服务从事任何违法犯罪活动的；\n" +
                "（6）\t制作、发布与以上行为相关的方法、工具，或对此类方法、工具进行运营或传播，无论这些行为是否为商业目的的；\n" +
                "（7）\t其他违反法律法规规定、侵犯其他用户合法权益、干扰“泡泡社交”正常运营或我们未明示授权的行为。\n" +
                "3、\t用户须对利用“泡泡社交”账号或本服务传送信息的真实性、合法性、无害性、准确性、有效性等全权负责，与用户所传播的信息相关的任何法律责任由用户自行承担，与我们无关。如因此给我们或第三方造成损害的，用户应当依法予以赔偿。\n" +
                "4、\t我们提供的服务中可能包括广告，用户同意在使用过程中显示我们和第三方供应商、合作伙伴提供的广告。除法律法规明确规定外，用户应自行对依该广告信息进行的交易负责，对用户因依该广告信息进行的交易或前述广告商提供的内容而遭受的损失或损害，我们不承担任何责任。\n" +
                "5、\t除非我们书面许可，用户不得从事下列任一行为：\n" +
                "（1）\t删除软件及其副本上关于著作权的信息；\n" +
                "（2）\t对软件进行反向工程、反向汇编、反向编译，或者以其他方式方式尝试发现软件的源代码；\n" +
                "（3）\t对我们拥有知识产权的内容进行使用、出租、出借、复制、修改、链接、转载、汇编、发表、出版、建立镜像站点等；\n" +
                "（4）\t对软件或者软件运行过程中释放到任何终端内存中的数据、软件运行过程中客户端与服务器端的交互数据，以及软件运行所必需的系统数据，进行复制、修改、增加、删除、挂接运行或创作任何衍生作品，形式包括但不限于使用插件、外挂或非经我们授权的第三方工具/服务接入软件和相关系统；\n" +
                "（5）\t通过修改或伪造软件运行中的指令、数据，增加、删除、变动软件的功能或运行效果，或者将用户于上述用途的软件、方法进行运营或向公众传播，无论这些行为是否为商业目的；\n" +
                "（6）\t通过非我们开发、授权的第三方软件、插件、外挂、系统，登录或使用我们软件及服务，或制作、发布、传播非我们开发、授权的第三方软件、插件、外挂、系统。\n" +
                "四、\t账户管理\n" +
                "1、“泡泡社交”账号的所有权归我们所有，用户完成申请注册手续后，获得“泡泡社交”账号的使用权，该使用权仅属于初始申请注册人，禁止赠与、借用、租用、转让或售卖。我们因经营需要，有权回收用户的“泡泡社交”账号。\n" +
                "2、用户可以更改、删除“泡泡社交”账号上的个人资料、注册信息及传送内容等，但需注意，删除有关信息的同时也会删除用户储存在系统中的文字和图片。用户需承担该风险。\n" +
                "3、用户有责任妥善保管注册账号信息及账号密码的安全，因用户保管不善可能导致遭受盗号或密码失窃，责任由用户自行承担。用户需要对注册账号以及密码下的行为承担法律责任。用户同意在任何情况下不使用其他用户的账号或密码。在用户怀疑他人使用账号或密码时，用户同意立即通知我们。\n" +
                "4、用户应遵守本协议的各项条款，正确、适当地使用本服务，如因用户违反本协议中的任何条款，我们在通知用户后有权依据协议中断或终止对违约用户“泡泡社交”账号提供服务。同时，我们保留在任何时候收回“泡泡社交”账号、用户名的权利。\n" +
                "五、数据储存\n" +
                "          1、我们不对用户在本服务中相关数据的删除或储存失败负责。\n" +
                "          2、我们可以根据实际情况自行决定用户在本服务中数据的最长储存期限，并在服务器上为其分配数据最大存储空间等。用户可根据自己的需要自行备份本服务中的相关数据。\n" +
                "           3、如用户停止使用本服务或本服务终止，我们可以从服务器上永久地删除用户的数据。本服务停止、终止后，我们没有义务向用户返还任何数据。\n" +
                "六、风险承担\n" +
                "           1、用户理解并同意，“泡泡社交”仅为用户提供信息分享、传送及获取的平台，用户必须为自己注册账号下的一切行为负责，包括用户所传送的任何内容以及由此产生的任何后果。用户应对“泡泡社交”及本服务中的内容自行加以判断，并承担因使用内容而引起的所有风险，包括因对内容的正确性、完整性或实用性的依赖而产生的风险。我们无法且不会对因用户行为而导致的任何损失或损害承担责任。\n" +
                "如果用户发现任何人违反本协议约定或以其他不当的方式使用本服务，请立即向我们举报或投诉，我们将依本协议约定进行处理。\n" +
                "2、用户理解并同意，因业务发展需要，我们保留单方面对本服的全部或部分服务内容变更、暂停、终止或撤销的权利，用户需承担此风险。\n" +
                "七、法律责任\n" +
                "           1、如果我们发现或收到他人举报或投诉用户违反本协议约定的，我们有权不经通知随时对相应内容，包括但不限于用户资料、聊天记录进行审查、删除，并视情节严重对违规账号处以包括但不限于警告、账号封禁、设备封禁、功能封禁的处罚，且通知用户处理结果。\n" +
                "          2、因违反用户协议被封禁的用户，可以自行到我们平台查询封禁期限，并在封禁期限届满后自助解封。其中，被实施功能封禁的用户会在封禁期届满后自动恢复被封禁的功能。被封禁用户可向我们网站相关页面提交申诉，我们将对申诉进行审查，并自行合理判断决定是否变更处罚措施。\n" +
                "         3、用户理解并同意，我们有权以合理判断对违反有关法律法规或本协议规定的行为进行处罚，对违法违规的任何用户采取适当的法律行动，并依据法律法规保存有关信息向有关部门报告等，用户应承担由此而产生的一切法律责任。\n" +
                "         4、用户理解并同意，因用户违反本协议约定，导致或产生的任何第三方主张的任何索赔、要求或损失，包括合理的律师费，用户应当赔偿我们与合作公司、关联公司，并使之免受损害。\n" +
                "八、不可抗力及其他免责事由\n" +
                "          1、用户理解并确认，在使用本服务的过程中，可能会遇到不可抗力等风险因素，使本服务发生中断。不可抗力使指不能预见、不能克服并不能避免且对一方或双方造成重大影响的客观事件，包括但不限于自然灾害如洪水、地震、瘟疫流行和风暴等以及社会事件如战争、动乱、政府行为等。出现上述情况时，我们将努力在第一时间与相关单位配合，及时进行修复，但是由此给用户或第三方造成的损失，我们及合作单位在法律允许的范围内免责。\n" +
                "        2、本服务同大多数互联网服务一样，受包括但不限于用户原因、网络服务质量、社会环境等因素的差异影响，可能受到各种安全问题的侵扰，如他人利用用户的资料，造成现实生活中的骚扰；用户下载安装的其他软件或访问的其他网站中含有“特洛伊木马”等病毒，威胁到用户的计算机信息和数据的安全，继而影响本服务的正常使用等等。用户应加强信息安全及使用者资料的保护意识，要注意加强密码保护，以免遭致损失和骚扰。\n" +
                "       3、用户理解并确认，本服务存在因不可抗力、计算机病毒或黑客攻击、系统不稳定、用户所在位置、用户关机以及其他任何技术、互联网络、通信线路等造成的服务中断或不能满足用户要求的风险，因此导致的用户或第三方任何损失，我们不承担任何责任。\n" +
                "        4、用户理解并确认，在使用本服务过程中存在来自任何他人的包括误导性的、欺骗性的、威胁性的、诽谤性的、令人反感的或非法的信息，或侵犯他人权利的匿名或冒名的信息，以及伴随该等信息的行为，因此导致的用户或第三方的任何损失，我们不承担任何责任。\n" +
                "         5、用户理解并确认，我们需要定期或不定期地对“泡泡社交”平台或相关的设备进行检修或者维护，如因此类情况而造成服务在合理时间内的中断，我们无需为此承担任何责任，但我们应事先进行通告。\n" +
                "         6、我们依据法律法规、本协议约定获得处理违法违规或违约内容的权利，该权利不构成我们的义务或承诺，我们不能保证及时发现违法违规或违约行为或进行相应处理。\n" +
                "         7、用户理解并确认，对于我们向用户提供的下列产品或者服务的质量缺陷及其引发的任何损失，我们无需承担任何责任：\n" +
                "             （1）我们向用户免费提供的服务；\n" +
                "             （2）我们向用户赠送的任何产品或者服务。\n" +
                "          8、在任何情况写，我们均不对任何间接性、后果性、惩罚性、偶然性、特殊性或刑罚性的损害，包括因用户使用“泡泡社交”或本服务而遭受的利润损失，承担责任（即使我们已被告知该等损失的可能性亦然）。尽管本协议中可能含有相悖的规定，我们对用户承担的全部责任，无论因何原因或何种行为方式，始终不超过用户因使用我们提供的服务。\n" +
                "九、服务的变更、中断、终止\n" +
                "          1、鉴于网络服务的特殊性，用户同意我们有权随时更新、中断或终止部门或全部的服务。我们变更、中断或终止的服务，我们应当在变更、中断或终止之前通知用户，并应当受影响的用户提供等值的替代性和服务；如用户不愿意接受替代性的服务，如果该用户已经向我们支付的泡泡社交币，我们应当按照该用户实际使用服务的情况扣除相应泡泡社交币之后将剩余的泡泡社交币退还用户的泡泡社交币账户中。\n" +
                "         2、如发生下列任何一种情形，我们有权变更、中断或终止向用户提供的服务，而无需对用户或任何第三方承担任何责任：\n" +
                "              （1）根据法律规定用户应提交真实信息，而用户提供的个人资料不真实、或与注册时信息不一致又未能提供合理证明；\n" +
                "              （2）用户违反相关法律法规或本协议的约定；\n" +
                "              （3）按照法律规定或有权机关的要求；\n" +
                "              （4）出于安全的原因或其他必要的情形。\n\n";

        dealTv.setText(text);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
