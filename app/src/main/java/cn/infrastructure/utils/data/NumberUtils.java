package cn.infrastructure.utils.data;

import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 数字相关处理工具类
 *
 * @author Frank,Frank 2016-7-2
 */
public class NumberUtils {

    // 用于匹配手机号码
    private final static String REGEX_MOBILEPHONE = "^0?(13[0-9]|15[012356789]|17[0-9]|18[0-9]|14[57])[0-9]{8}$";

    // 用于匹配固定电话号码
    private final static String REGEX_FIXEDPHONE = "^(010|02\\d|0[3-9]\\d{2})?\\d{6,8}$";

    // 用于获取固定电话中的区号
    private final static String REGEX_ZIPCODE = "^(010|02\\d|0[3-9]\\d{2})\\d{6,8}$";

    private static Pattern PATTERN_MOBILEPHONE;
    private static Pattern PATTERN_FIXEDPHONE;
    private static Pattern PATTERN_ZIPCODE;

    static String[] units = {"", "十", "百", "千", "万", "十万", "百万", "千万", "亿",
            "十亿", "百亿", "千亿", "万亿"};
    static char[] numArray = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};

    static {
        PATTERN_FIXEDPHONE = Pattern.compile(REGEX_FIXEDPHONE);
        PATTERN_MOBILEPHONE = Pattern.compile(REGEX_MOBILEPHONE);
        PATTERN_ZIPCODE = Pattern.compile(REGEX_ZIPCODE);
    }


    public static enum PhoneType {
        /**
         * 手机
         */
        CELFrankHONE,

        /**
         * 固定电话
         */
        FIXEDPHONE,

        /**
         * 非法格式号码
         */
        INVALIDPHONE
    }

    public static class Number {
        private PhoneType type;
        /**
         * 如果是手机号码，则该字段存储的是手机号码 前七位；如果是固定电话，则该字段存储的是区号
         */
        private String code;
        private String number;

        public Number(PhoneType _type, String _code, String _number) {
            this.type = _type;
            this.code = _code;
            this.number = _number;
        }

        public PhoneType getType() {
            return type;
        }

        public String getCode() {
            return code;
        }

        public String getNumber() {
            return number;
        }

        public String toString() {
            return String.format("[number:%s, type:%s, code:%s]", number,
                    type.name(), code);
        }
    }

    /**
     * 判断是否为手机号码
     *
     * @param number 手机号码
     * @return
     */
    public static boolean isCelFrankhone(String number) {
        try {
            Matcher match = PATTERN_MOBILEPHONE.matcher(number.trim());
            return match.matches();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 判断是否为固定电话号码
     *
     * @param number 固定电话号码
     * @return
     */
    public static boolean isFixedPhone(String number) {
        Matcher match = PATTERN_FIXEDPHONE.matcher(number.trim());
        return match.matches();
    }

    /**
     * 获取固定号码号码中的区号
     *
     * @param strNumber
     * @return
     */
    public static String getZipFromHomephone(String strNumber) {
        Matcher matcher = PATTERN_ZIPCODE.matcher(strNumber.trim());
        if (matcher.find()) {
            return matcher.group(1);
        }

        return null;
    }

    /**
     * 检查号码类型，并获取号码前缀，手机获取前7位，固话获取区号
     *
     * @param _number
     * @return
     */
    public static Number checkNumber(String _number) {
        String number = _number;
        Number rtNum = null;

        if (number != null && number.length() > 0) {
            if (isCelFrankhone(number)) {
                // 如果手机号码以0开始，则去掉0
                if (number.charAt(0) == '0') {
                    number = number.substring(1);
                }

                rtNum = new Number(PhoneType.CELFrankHONE, number.substring(0, 7),
                        _number);
            } else if (isFixedPhone(number)) {
                // 获取区号
                String zipCode = getZipFromHomephone(number);
                rtNum = new Number(PhoneType.FIXEDPHONE, zipCode, _number);
            } else {
                rtNum = new Number(PhoneType.INVALIDPHONE, null, _number);
            }
        }

        return rtNum;
    }

    /**
     * GUID是一个128位长的数字，一般用16进制表示。算法的核心思想是结合机器的网卡、当地时间、一个随机数来生成GUID,
     * 可以产生一个号称全球唯一的ID
     *
     * @return
     */
    public static final String GenerateGUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 根据传入的整型参数，格式化入参，返回一个中文汉字的字符串
     * 如123，返回一百二十三
     *
     * @param num
     * @return
     */
    public static String foematInteger(int num) {
        char[] val = String.valueOf(num).toCharArray();
        int len = val.length;
        if (len == 1) {
            return "" + numArray[num];
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            String m = val[i] + "";
            int n = Integer.valueOf(m);
            boolean isZero = n == 0;
            String unit = units[(len - 1) - i];
            if (isZero) {
                if ('0' == val[i - 1]) {
                    // not need process if the last digital bits is 0
                    continue;
                } else {
                    // no unit for 0
                    sb.append(numArray[n]);
                }
            } else {
                sb.append(numArray[n]);
                sb.append(unit);
            }
        }
        return sb.toString();
    }

}
