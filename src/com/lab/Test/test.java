package com.lab.Test;

import com.lab.parse.parsing;
import com.lab.wordscanner.utils.readline;
import com.lab.wordscanner.wordscan;

import java.io.IOException;
import java.util.*;

public class test {

    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("请将输入文件放置在files目录下\n请选择输入方式：\n 1：命令行\n 2：文件名\n3:退出");
        String[] type={"command","file name"};
        String line = s.nextLine();
        Integer inp=Integer.parseInt(line);
        while (inp!=3){
            try {
                System.out.println(">>>" + type[inp-1]+'\n');
            } catch (Exception e) {
                System.out.println("wrong input");
            }

            if(inp==1){
                try {
                    wordscan word1=new wordscan();
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("wrong input");
                }

            }else if (inp==2) {
                System.out.println("输入文件名：");
                line=s.nextLine();
                try {
                    wordscan word2=new wordscan("src/com/lab/files/"+line);
                    break;
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("wrong input");
                }

            }else {
                System.out.println("wrong input");
            }
            line = s.nextLine();
            inp=Integer.parseInt(line);
        }
        if (inp==3)
            return;
        parsing pp=new parsing("src/com/lab/files/SCAN_temp.txt");
        //pp.readmlist();

   }


}
