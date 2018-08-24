package com.zhtx.mindlib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 文件描述：
 * 作者：Coding Farmer_5207
 * 创建时间：2018/6/27
 * 更改时间：2018/6/27
 */

public class StringUtil {

    private static StringUtil mStringUtil = null;

    public static StringUtil getInstance () {
        if (mStringUtil == null) {
            mStringUtil = new StringUtil();
        }
        return mStringUtil;
    }

    public String toTime (int old_time) {
        String new_time = "01:00:00";
        if (old_time == 60) {
            new_time = "01:00:00";
        } else if (old_time <10) {
            new_time = "00:0" + old_time +":00" ;
        } else {
            new_time = "00:" + old_time +":00" ;
        }
        return new_time;
    }

    // 判断一个字符串是否含有数字
    public static boolean HasDigit(String content) {
        boolean flag = false;
        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(content);
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }

    // 判断一个字符串中是否包含<<<<<字母>>>>>>>
    public static boolean judgeContainsStr(String cardNum) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    // 判断一个字符串中是否有包含<<<<<<小写字母>>>>>>>>
    public static boolean judgeContainsLowercaseStr(String cardNum) {
        String regex=".*[a-z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    // 判断一个字符串中是否包含<<<<<<大写字母>>>>>
    public static boolean judgeContainsCapitalStr(String cardNum) {
        String regex=".*[A-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(cardNum);
        return m.matches();
    }

    //不能全是相同的数字或者字母（如：000000、111111、aaaaaa） 全部相同返回true
    public static boolean equalStr(String numOrStr){
        boolean flag = true;
        char str = numOrStr.charAt(0);
        for (int i = 0; i < numOrStr.length(); i++) {
            if (str != numOrStr.charAt(i)) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    //不能是连续的数字   连续数字返回true
    public static boolean isOrderNumeric(String numOrStr){
        boolean flag = true;//如果全是连续数字返回true
        boolean flag_subtract = true;//如果全是递减数字返回true
        boolean flag_add = true;//如果全是递增数字返回true

        //递减（如：987654、876543）
        for (int i = 0; i < numOrStr.length(); i++) {
            if (i > 0) {
                int num = Integer.parseInt(numOrStr.charAt(i)+"");
                int num_ = Integer.parseInt(numOrStr.charAt(i-1)+"")-1;
                if (num != num_) {
                    flag_subtract = false;
                    break;
                }
            }
        }

        //递增（如：123456、12345678）
        for (int i = 0; i < numOrStr.length(); i++) {
            if (i > 0) {
                int num = Integer.parseInt(numOrStr.charAt(i)+"");
                int num_ = Integer.parseInt(numOrStr.charAt(i-1)+"")+1;
                if (num != num_) {
                    flag_add = false;
                    break;
                }
            }
        }

        //全是连续数字
        if (flag_subtract || flag_add){
            flag = true;
        }else {
            flag = false;
        }

        return flag;
    }

    //判断一个字符串是否由：字母+数字组成,并且必须包含一个大写字母
    public static boolean judgePasswordFormat(String content){
        if(HasDigit(content) &&judgeContainsCapitalStr(content)&&judgeContainsLowercaseStr(content)){
            return true;
        }else{
            return false;
        }
    }

    //判断一个字符串是否由：字母+数字组成
    public static boolean judgePasswordFormatTwo(String content){
        if(HasDigit(content) &&(judgeContainsCapitalStr(content)||judgeContainsLowercaseStr(content))){
            return true;
        }else{
            return false;
        }
    }

    //判断一个字符串是否是全相同的数字或连续的数字
    public static boolean isContinuousIdentical(String num) {

        if (equalStr(num) || isOrderNumeric(num)) {
            return true;
        }else {
            return false;
        }

    }

}
