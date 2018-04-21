package com.vorspack.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexTool {
    public static String getInfo(String regex,String input){
      return  getInfo(regex, input, 0);
    }
    public static String getInfo(String regex,String input,int groupNum){
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        if(matcher.find()){
            return matcher.group(groupNum);
        }else {
            throw new RuntimeException("Pattern not matched in " + input);
        }
    }

    public static String[] getGroups(String regex,String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String[] groups;
        if(matcher.find()){
            int count = matcher.groupCount();
            groups=new String[count+1];
            for (int i = 0; i <=count; i++) {
                groups[i] = matcher.group(i);
            }
        } else throw new RuntimeException("Pattern not matched in " + input);
        return groups;
    }

}
