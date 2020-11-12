package com.ruoyi.common.utils;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证手机号,0-1之间的两位小数,正正数
 */
@Component
public class VertifyUtil {
    /**
     * 验证是否是手机号
     * @param mobiles
     * @return
     */
    public static boolean isMobileNO(String mobiles) {
        if(mobiles==null||mobiles.isEmpty()){
            return false;
        }
        String regExp = "^((13[0-9])|(14[5,7,9])|(15[0-3,5-9])|(166)|(17[3,5,6,7,8])" +
                "|(18[0-9])|(19[8,9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    /**
     * 验证0-1之间的小数
     * @param num
     * @return
     */
    public static boolean verifyNum1(String num){
        if(num==null||num.isEmpty()){
            return false;
        }
        String strVerify = "^(0(\\.\\d{1,2})?|1(\\.0{1,2})?)$";
        Pattern p = Pattern.compile(strVerify);
        Matcher m = p.matcher(num);
        return m.find();
    }


    /**
     * 验证是否为正数
     * @param num
     * @return
     */
    public static boolean verifyNum2(String num){
        if(num==null||num.isEmpty()){
            return false;
        }
        String strVerify = "^((0{1}\\.\\d{1,2})|([1-9]\\d*\\.{1}\\d{1,2})|([1-9]+\\d*)|0)$";
        Pattern p = Pattern.compile(strVerify);
        Matcher m = p.matcher(num);
        return m.find();
    }

}
