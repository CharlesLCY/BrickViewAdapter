package com.lcy.practice.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Json 工具类
 * 提供简单的 Json 字符串美化和 Json 字符串压缩方法.
 * <p>
 * Create By 吴荣 at 2018-09-25 17:24
 */
public class JsonUtil {

    /**
     * Json 字符串美化.
     *
     * @param json 待处理的 Json 字符串
     * @return 美化后的 Json 字符串.
     */
    public static String jsonBeautify(String json) {
        StringBuilder stringBuffer = new StringBuilder();
        int index = 0;
        int count = 0;
        while (index < json.length()) {
            char ch = json.charAt(index);
            if (ch == '{' || ch == '[') {
                stringBuffer.append(ch);
                stringBuffer.append('\n');
                count++;
                for (int i = 0; i < count; i++) {
                    stringBuffer.append('\t');
                }
            } else if (ch == '}' || ch == ']') {
                stringBuffer.append('\n');
                count--;
                for (int i = 0; i < count; i++) {
                    stringBuffer.append('\t');
                }
                stringBuffer.append(ch);
            } else if (ch == ',') {
                stringBuffer.append(ch);
                stringBuffer.append('\n');
                for (int i = 0; i < count; i++) {
                    stringBuffer.append('\t');
                }
            } else {
                stringBuffer.append(ch);
            }
            index++;
        }
        return stringBuffer.toString();
    }

    /**
     * Json 压缩.
     *
     * @param json 待处理的 Json 字符串.
     * @return 压缩后的 Json 字符串.
     */
    public static String compression(String json) {
        String regEx = "[\t\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(json);
        return m.replaceAll("").trim();
    }
}
