package com.novel.background.common;

import java.util.HashMap;
import java.util.Map;

public class ParseNum {
    public final static Map<Character,Integer> digit = new HashMap<>();
    public final static Map<Character,Integer> position = new HashMap<>();
    static{
        digit.put('零', 0);
        digit.put('一', 1);
        digit.put('二', 2);
        digit.put('三', 3);
        digit.put('四', 4);
        digit.put('五', 5);
        digit.put('六', 6);
        digit.put('七', 7);
        digit.put('八', 8);
        digit.put('九', 9);
        position.put('十', 1);
        position.put('百',2);
        position.put('千', 3);
    }
    /**
     * 将num中文转为阿拉伯数字
     * 考虑范围: 0~9999_9999
     * @param num
     * @return
     */
    public int parse(String num){
        //一共八位
        char []nums = new char[8];
        int p = 7,now = 7;
        char []c = num.toCharArray();
        /**
         * 从右到左逐个字符解析
         * 通过p控制万位,pp控制万以下的单位,now记录当前单位
         */
        for(int i  = c.length-1;i>=0;i--){
            if(c[i]=='万'){
                now = p = 3;
            }else{
                int d = digit.getOrDefault(c[i], -1);
                if(d==0){
                    continue;
                }if(d==-1){
                    int pp = position.getOrDefault(c[i], -1);
                    if(pp==-1)
                        throw new RuntimeException("\""+num+"\"中存在无法解析的字符: "+i+":"+c[i]);
                    else{
                        now = p-pp;
                        //允许"一十"省略"一",即"十"
                        if(now==6)
                            nums[now] = 1;
                    }
                }else{
                    nums[now] = (char) d;
                }
            }
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = (char) (nums[i]+'0');
        }
        return Integer.parseInt(new String(nums));
    }
    
}