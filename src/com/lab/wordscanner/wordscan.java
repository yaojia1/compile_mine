package com.lab.wordscanner;

import com.lab.wordscanner.utils.readline;
import com.lab.wordscanner.utils.wordtype;

import java.io.*;
import java.util.*;

/**
 *
 */
public class wordscan {
    //public static List<Map<Integer,String>> mList= new ArrayList<>();//扫描结果

    private ArrayList<String[]> mList=new ArrayList<String[]>();
    public wordtype wordtype = new wordtype();
    private List<String >list=new ArrayList<String>();//输入的命令
    public wordscan(String chs) throws IOException {
        /**
         * 输入文件名，绝对位置
         */

        //readtype();


        File file = new File("src/com/lab/files/SCAN_temp.txt");

        // if file doesnt exists, then create it
        if (!file.exists()) {
            file.createNewFile();
        }

        BufferedWriter writer = new BufferedWriter(new FileWriter("src/com/lab/files/SCAN_temp.txt"));
        readFile(chs,writer);
        /**
        for (Map<Integer,String> map : mList) {
            for (Integer s1 : map.keySet()) {
                writer.write(s1+","+map.get(s1) + "\n");
                System.out.print(s1+","+map.get(s1) + "\n");
            }
        }
         */
        writer.close();

    }
    public wordscan() throws IOException {
        /**
         * 无输入，默认读取命令行
         */
        Scanner s = new Scanner(System.in);
        readline r=new readline();
        BufferedWriter writer = new BufferedWriter(new FileWriter("src\\com\\lab\\files\\SCAN_temp.txt"));
        int count=0;
        while (true){
            System.out.println("请输入命令：quit退出\n");

            String line = s.nextLine();
            if(line.equals("quit")) break;
            mList.clear();
            mList.addAll(r.Divide(line+"#"));
            /**


            File file = new File("E:\\SCAN_temp.txt");

            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }


             */
            for ( String[] map: mList) {
                //System.out.print(map[0]+","+map[1]);
                System.out.println(wordtype.getkeyname(Integer.parseInt(map[0]))+","+map[1]);
                writer.write(wordtype.getkeyname(Integer.parseInt(map[0]))+","+map[1] + "\n");
                /**
                 for (Integer s1 : map.keySet()) {
                 writer.write(wordtype.getkeyname(s1)+","+map.get(s1) + "\n");
                 System.out.print(s1+","+wordtype.getkeyname(s1)+","+map.get(s1) + "\n");

                 }*/
            }
            //
            //readcom(line);
            //list.add(line);
            //readtype();
            //list.clear();
            //mList.clear();
            count++;
        }
        writer.close();
        System.out.format("读取代码%d行，词法扫描已完成，生成代码存放至 SCAN_temp.txt 中\n",count);

    }
    /**
    private void readcom(String string){
        readline r=new readline();
        mList.addAll(r.Divide(string));
    }*/

    private void readtype(){
        readline r=new readline();
        for(String s1:list){
            mList.addAll(r.Divide(s1+"#"));
          //  System.out.println(r.Divide(s1));
        }

        /**
        for (Map<Integer,String> map : mList) {
            for (Integer s : map.keySet()) {
                System.out.print(s+","+map.get(s) + "\n");
            }
        }*/

    }

    public void readFile(String filename,BufferedWriter writer) throws IOException {

        System.out.println("开始从文件读取源代码");
        FileReader fread = new FileReader(filename);
        BufferedReader bf=new BufferedReader(fread);
        String strLine=bf.readLine();
        int count=0;
        while(strLine!=null){
            //System.out.println("strLine:"+strLine);
            list.add(strLine);
            readtype();
            for ( String[] map: mList) {
                //System.out.print(map[0]+","+map[1]);
                writer.write(wordtype.getkeyname(Integer.parseInt(map[0]))+","+map[1] + "\n");
                /**
                for (Integer s1 : map.keySet()) {
                    writer.write(wordtype.getkeyname(s1)+","+map.get(s1) + "\n");
                    System.out.print(s1+","+wordtype.getkeyname(s1)+","+map.get(s1) + "\n");

                }*/
            }
            //System.out.println("读取下一行=========================");
            list.clear();
            mList.clear();
            strLine=bf.readLine();
            count++;
        }
        System.out.format("读取代码%d行，词法扫描已完成，生成代码存放至 SCAN_temp.txt 中\n",count);
        bf.close();
        fread.close();
    }

}
