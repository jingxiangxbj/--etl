package com.jingxiang.datachange.util;


public class StringUtils {

    /**
     * Description: 初始化输入参数，如果未输入参数，那么退出程序
     * Param: [args, length, msg]
     * Return: void
     */
    public static void init(String[] args, int length, String msg) {
        if (args.length != length) {
            System.out.println(msg);
            System.exit(-1);
        }
    }


    /**
     * Description: 如果字符串中包含有反斜杠、单引号、双引号字符时，为他们添加转义字符\
     * Param: [str]
     * Return: java.lang.String
     */
    public static String addEscape(String str) {
        str = str.replaceAll("\\\\", "\\\\\\\\")
                .replaceAll("\"", "\\\\\"")
                .replaceAll("'", "\\\\'");
        return str;
    }

    /**
     * description: 将下划线的命名法改为驼峰命名法
     * param: [key]
     * return: java.lang.String
     */
    public static String camelNamed(String key) {
        StringBuilder sb = new StringBuilder("");
        try {
            if (key != null && key.contains("_")) {
                String[] splits = key.split("_");
                sb.append(splits[0].substring(0, 1).toLowerCase());
                sb.append(splits[0].substring(1));
                for (int i = 1; i < splits.length; i++) {
                    sb.append(splits[i].substring(0, 1).toUpperCase());
                    sb.append(splits[i].substring(1));
                }
            } else {
                return key;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
