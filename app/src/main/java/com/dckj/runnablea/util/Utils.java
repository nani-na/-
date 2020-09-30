package com.dckj.runnablea.util;

import android.app.Activity;
import android.view.WindowManager;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class Utils {
    /**
     * 设置页面透明度
     * @param activity
     * @param bgAlpha
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }


    /**
     * 获取汉语第一个字的首英文字母
     * @param src
     * @return
     */
    public static String getTerm(String src){
        String res = spell(src);
        if(res!=null&&res.length()>0){
            if (src.startsWith("重庆")){
                return "C,chongqing,CHONGQING";
            }
            return res.toUpperCase().charAt(0) + "," + res + "," + res.toUpperCase();
        }else{
            return "OT,ot,OT";
        }
    }
    /** 汉语拼音格式化工具类 */

    private static HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
    /**
     * 获取字符串内的所有汉字的汉语拼音
     * @param src
     * @return
     */
    public static String spell(String src) {
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE); // 小写拼音字母
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE); // 不加语调标识
        format.setVCharType(HanyuPinyinVCharType.WITH_V); // u:的声母替换为v

        StringBuffer sb = new StringBuffer();
        int strLength = src.length();
        try {
            for (int i = 0; i < strLength; i++) {
                // 对英文字母的处理：小写字母转换为大写，大写的直接返回
                char ch = src.charAt(i);
                if (ch >= 'a' && ch <= 'z')
                    sb.append((char) (ch - 'a' + 'A'));
                if (ch >= 'A' && ch <= 'Z')
                    sb.append(ch);
                // 对汉语的处理
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(ch, format);
                if (arr != null && arr.length > 0)
//                    sb.append(arr[0]).append(" ");
                    sb.append(arr[0]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
