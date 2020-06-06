package com.lab.Test;

import com.lab.parse.parsing;
import com.lab.wordscanner.utils.readline;
import com.lab.wordscanner.wordscan;

import java.io.IOException;
import java.util.*;

public class test {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("请选择输入方式：\n 1：命令行\n2：文件名\n");
        String[] type={"command","file name"};
        String line = s.nextLine();
        Integer inp=Integer.parseInt(line);
        System.out.println(">>>" + type[inp-1]+'\n');
        if(inp==1){
            wordscan word1=new wordscan();
        }else if (inp==2) {
            System.out.println("输入文件名：");
            line=s.nextLine();
            wordscan word2=new wordscan(line);
        }else {
            System.out.println("wrong input");
        }

        parsing pp=new parsing("src/com/lab/files/1_word.txt");
        pp.readmlist();

   }


}
