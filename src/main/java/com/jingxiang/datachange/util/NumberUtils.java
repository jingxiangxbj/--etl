package com.jingxiang.datachange.util;


public class NumberUtils {
    /**
     * description: 获取比给定数值小并大于等于0的值
     * param: [num]
     * return: long
     */
    public static long getLessThanNum(long num) {
        if (num < 0) return 0;
        return (long) (Math.random() * num);
    }

    /**
     * description: 生成假数字 生成指定个数的随机数字
     * param: [num]
     * return: java.lang.String
     */
    public static String getRandomNum(long num) {
        StringBuilder returnLong = new StringBuilder("");
        try {
            for (int i = 0; i < num; i++) {
                long l = (long) (Math.random() * 10);
                while (l == 0l) {
                    l = (long) (Math.random() * 10);
                }
                returnLong.append(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnLong.toString();
    }

}

