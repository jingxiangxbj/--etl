package com.jingxiang.datachange.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ETLUtil {
    public static String etlPhone(String phone) {
    /**
     * description: 清洗移动手机号
     * 1.去除前面的+86
     * 2.去重手机号中间的空格
     * 3.仅清洗移动手机号，其他固定电话手机号、平台手机号、国际手机号暂不处理，原样返回
     * param: [phone]
     * return: java.lang.String
     */
    if (phone == null) return null;
    String returnPhone = phone;
    if (phone.matches(RegexUtils.phoneRegex)) {
        returnPhone = phone.substring(phone.indexOf("1")).replaceAll("\\s", "");
    }
    return returnPhone;
}
    /**
     * description: 姓名中除了 汉字和· 应该没有其他字符了，所以删除特殊字符或者字母等
     * param: [name]
     * return: java.lang.String
     */
    public static String etlName(String name) {
        String returnName = name;
        try {
            if (name != null) {
                if (!name.matches(RegexUtils.nameRegex)) {
                    StringBuilder sb = new StringBuilder("");
                    Pattern pattern = Pattern.compile("([·\\u4e00-\\u9fa5]+)");
                    Matcher matcher = pattern.matcher(name);
                    while (matcher.find()) {
                        String group = matcher.group();
                        sb.append(group);
                    }
                    returnName = sb.toString();
                }
            } else {
                returnName = "";
            }
        } catch (Exception e) {
        }
        return returnName;
    }

}
