package com.android.outbaselibrary.utils;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Created by hanhuailong on 2018/3/26.
 */

public class StringUtil {
    public static final String EMPTY = "";

    public static final String SPACE = " ";

    public static final String TOPIC = "##";

    public static final String AT = "@";

    private static Pattern emojiRegexp;

    static {
        emojiRegexp = Pattern
                .compile("[🀄🅰🅱🅾🅿🆎🆒🆔🆕🆗🆙🆚🇧🇨🇩🇪🇪🇫🇬🇮🇯🇰🇳🇵🇷🇷🇷🇸🇸🇹🇺🇺🈁🈂🈚🈯🈳🈵🈶🈷🈸🈹🈺🉐🌀🌂🌃🌄🌅🌆🌇🌈🌊🌙🌟🌴🌵🌷🌸🌹🌺🌻🌾🍀🍁🍂🍃🍅🍆🍉🍊🍎🍓🍔🍘🍙🍚🍛🍜🍝🍞🍟🍡🍢🍣🍦🍧🍰🍱🍲🍳🍴🍵🍶🍸🍺🍻🎀🎁🎂🎃🎄🎅🎆🎇🎈🎉🎌🎍🎎🎏🎐🎑🎒🎓🎡🎢🎤🎥🎦🎧🎨🎩🎫🎬🎯🎰🎱🎵🎶🎷🎸🎺🎾🎿🏀🏁🏃🏄🏆🏈🏊🏠🏢🏣🏥🏦🏧🏨🏩🏪🏫🏬🏭🏯🏰🐍🐎🐑🐒🐔🐗🐘🐙🐚🐛🐟🐠🐤🐦🐧🐨🐫🐬🐭🐮🐯🐰🐱🐳🐴🐵🐶🐷🐸🐹🐺🐻👀👂👃👄👆👇👈👉👊👋👌👍👎👏👐👑👒👔👕👗👘👙👜👟👠👡👢👣👦👧👨👩👫👮👯👱👲👳👴👵👶👷👸👻👼👽👾👿💀💁💂💃💄💅💆💇💈💉💊💋💍💎💏💐💑💒💓💔💗💘💙💚💛💜💝💟💡💢💣💤💦💨💩💪💰💱💱💹💹💺💻💼💽💿📀📖📝📠📡📢📣📩📫📮📱📲📳📴📶📷📺📻📼🔊🔍🔑🔒🔓🔔🔝🔞🔥🔨🔫🔯🔰🔱🔲🔳🔴🕐🕑🕒🕓🕔🕕🕖🕗🕘🕙🕚🕛🗻🗼🗽😁😂😃😄😉😊😌😍😏😒😓😔😖😘😚😜😝😞😠😡😢😣😥😨😪😭😰😱😲😳😷🙅🙆🙇🙌🙏🚀🚃🚄🚅🚇🚉🚌🚏🚑🚒🚓🚕🚗🚙🚚🚢🚤🚥🚧🚬🚭🚲🚶🚹🚺🚻🚼🚽🚾🛀☺✨❕❔✊✌✋☝☀☔☁⛄⚡☕〽♦♣♠⛳⚾⚽➿☎✂⛪⛺⛵⛲♨⚠⛽⃣⃣⃣⃣⃣⃣⃣⬅⬇⬆⃣⃣⃣⃣➡↗↖↘↙◀▶⏪⏩♿✳㊗㊙✴♈♉♊♋♌♍♎⛎♓♒♑♐♏❌⭕]");
    }

    public static CharSequence stripEmoji(CharSequence content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }

        if (!SystemUtil.hasJellyBean()) {
            content = emojiRegexp.matcher(content).replaceAll("");
        }
        return content;
    }

    public static String stripEmojiIgnoreVersion(String content) {
        if (TextUtils.isEmpty(content)) {
            return "";
        }

        content = emojiRegexp.matcher(content).replaceAll("");
        return content;
    }

    public static String stripNewLines(String content) {
        return content.replaceAll("\r\n", "");
    }

    public static String stripNullValue(String content) {
        return content.replaceAll("null", "");
    }

    public static boolean equals(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equals(str2);
    }

    public static boolean equalsIgnoreCase(String str1, String str2) {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }

    public static boolean equalsIgnoreCaseAfterTrim(String str1, String str2) {
        return str1 == null ? str2 == null : str1.trim().equalsIgnoreCase(str2.trim());
    }

    public static String subString(String content, int maxLength) {
        return subString(content, maxLength, Boolean.FALSE);
        //        if (content.length() >= maxLength) {
        //            return content.substring(0, maxLength - 1);
        //        } else {
        //            return content;
        //        }
    }

    public static String subString(String content, int maxLength, boolean showSuffix) {
        if (TextUtils.isEmpty(content) || (maxLength <= 0)) {
            return "";
        }

        if (content.length() >= maxLength) {
            content = content.substring(0, maxLength - 1);
            if (showSuffix) {
                content += "...";
            }
        }

        return content;
    }

    public static String left(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(0, len);
    }

    public static String right(String str, int len) {
        if (str == null) {
            return null;
        }
        if (len < 0) {
            return EMPTY;
        }
        if (str.length() <= len) {
            return str;
        }
        return str.substring(str.length() - len);
    }

    /**
     * Replace "'" with "\'", and """ "\""
     *
     * @param src Source string
     * @return
     */
    public static String quotationRegex(String src) {
        return src.replaceAll("\'", "\\\\" + "'").replaceAll("\"", "\\\\" + "\"");
    }

    public static String trim(String str) {
        if (str == null) {
            return null;
        }

        str = str.trim();

        int len = str.length();
        int st = 0;
        char[] val = str.toCharArray();
        while ((st < len) && ((val[st] <= ' ') || (val[st] == '　'))) {
            st++;
        }
        while ((st < len) && ((val[len - 1] <= ' ') || (val[len - 1] == '　'))) {
            len--;
        }
        return str.substring(st, len);
    }
    public static String stringToMD5(String data) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16) buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            return buf.toString();

        } catch (NoSuchAlgorithmException e) {

            e.printStackTrace();
        }

        return null;

    }
}
