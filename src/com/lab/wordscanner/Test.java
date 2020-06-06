package com.lab.wordscanner;

import java.io.IOException;
import java.util.Scanner;

public class Test {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        System.out.println("输入文件名：");
        String line=s.nextLine();
        wordscan word2=new wordscan(line);
    }
}

