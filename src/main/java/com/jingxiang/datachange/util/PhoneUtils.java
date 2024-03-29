package com.jingxiang.datachange.util;


import java.util.HashMap;

public class PhoneUtils {
    private static HashMap<String, String> phoneMap = new HashMap<>();
    public static String[] phonePrefixs;

    static {
        phoneInit();
        phonePrefixs = new String[]{"135", "136", "137", "138", "139", "147", "148", "150", "151", "152",
                "157", "158", "159", "178", "182", "183", "184", "187", "188", "198", "130", "131", "132", "145",
                "146", "155", "156", "166", "175", "176", "185", "186", "171", "133", "149", "153", "173", "177",
                "180", "181", "189", "199"};
    }

    /**
     * Description: 获取手机号运营商
     * Param: [phone]
     * Return: java.lang.String 1代表中国移动，2代表中国联通，3代表中国电信
     */
    public static String getPhoneFrom(String phone) {
        String phoneFrom = "";
        String phoneRegex = "(13[0-9]|14[579]|15[0-3,5-9]|17[0135678]|18[0-9])\\d{8}";
        if (phone != null && phone.matches(phoneRegex)) {
            String phonePrefix = phone.substring(0, 3);
            char c4 = phone.charAt(3);
            char c5 = phone.charAt(4);
            if (phoneMap.containsKey(phonePrefix)) {
                phoneFrom = phoneMap.get(phonePrefix);
            } else if ("134".equals(phonePrefix)) {
                if (c4 == '9') {
                    phoneFrom = "3";
                } else {
                    phoneFrom = "1";
                }
            } else if ("144".equals(phonePrefix)) {
                if (c4 == '0') {
                    phoneFrom = "1";
                }
            } else if ("141".equals(phonePrefix)) {
                if (c4 == '0') {
                    phoneFrom = "3";
                }
            } else if ("174".equals(phonePrefix)) {
                if (c4 == '0') {
                    if (c5 == '0' || c5 == '1' || c5 == '2' || c5 == '3' || c5 == '4' || c5 == '5')
                        phoneFrom = "3";
                }
            } else if ("170".equals(phonePrefix)) {
                if (c4 == '0' || c4 == '1' || c4 == '2') {
                    phoneFrom = "3";
                } else if (c4 == '3' || c4 == '5' || c4 == '6') {
                    phoneFrom = "1";
                } else {
                    phoneFrom = "2";
                }
            } else {
                phoneFrom = "4";
            }
        } else {
            return ""; // 手机号格式不正确
        }
        return phoneFrom;
    }

    /**
     * Description: 生成假手机号，运营商真实存在
     * Param: []
     * Return: String
     */
    public static String getRandomPhone() {
        return PhoneUtils.phonePrefixs[(int) NumberUtils.getLessThanNum(PhoneUtils.phonePrefixs.length)] + NumberUtils.getRandomNum(8);
    }

    /**
     * Description: 指定运营商号段前缀，随机生成电话号码
     * Param: [mobilePrefix]
     * Return: java.lang.String
     */
    public static String getRandomPhone(String mobilePrefix) {
        return mobilePrefix + NumberUtils.getRandomNum(8);
    }

    /**
     * Description: 手机号运营商匹配项初始化
     * Param: []
     * Return: void
     */
    private static void phoneInit() {
        String mobile = "1";
        String unicom = "2";
        String telcom = "3";
        String[] mobilePrefixs = new String[]{"135", "136", "137", "138", "139",
                "147", "148", "150", "151", "152", "157", "158", "159", "178",
                "182", "183", "184", "187", "188", "198"};
        String[] unicomPrefixs = new String[]{"130", "131", "132", "145", "146",
                "155", "156", "166", "175", "176", "185", "186", "171"};
        String[] telcomPrefixs = new String[]{"133", "149", "153", "173", "177",
                "180", "181", "189", "199"};
        for (String phonePrefix : mobilePrefixs) {
            phoneMap.put(phonePrefix, mobile);
        }
        for (String phonePrefix : unicomPrefixs) {
            phoneMap.put(phonePrefix, unicom);
        }
        for (String phonePrefix : telcomPrefixs) {
            phoneMap.put(phonePrefix, telcom);
        }
    }



    /**
     * description: 清洗移动手机号
     * 1.去除前面的+86
     * 2.去重手机号中间的空格
     * 3.仅清洗移动手机号，其他固定电话手机号、平台手机号、国际手机号暂不处理，原样返回
     * param: [phone]
     * return: java.lang.String
     */
    public static String etlPhone(String phone) {
        if (phone == null) return null;
        String returnPhone = phone;
        if (phone.matches(RegexUtils.phoneRegex)) {
            returnPhone = phone.substring(phone.indexOf("1")).replaceAll("\\s", "");
        }
        return returnPhone;
    }

}
