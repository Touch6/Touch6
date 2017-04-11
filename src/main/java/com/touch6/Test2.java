package com.touch6;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LONG on 2017/4/11.
 */
public class Test2 {
    public static void main(String[] args) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("D://log.out"));
        String line;
        String temp;
        Pattern patten = Pattern.compile("^.*\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}.*$");
        StringBuilder sb=new StringBuilder();
        while ((line = reader.readLine()) != null) {
            line=line.substring(14,14+16);
            Matcher matcher=patten.matcher(line);
            if(matcher.matches()){

            }else{

            }
        }
    }
}
