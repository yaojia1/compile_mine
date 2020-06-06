package com.lab.parse;

import com.lab.translation.translate;
import com.lab.wordscanner.utils.readline;
import com.lab.wordscanner.utils.wordtype;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;

public class parsing {

    private final static Integer action_w=11;
    private final static Integer action_h=16;
    private final static Integer G_num=9;
    private List<String > list=new ArrayList<String>();
    private LinkedList<Map<String,String>> mList= new LinkedList<>();
    private String[] LRGS_0={
            "E'->E",//0
            "E->E+T",//1
            "E->E-T",//2
            "E->T",//3
            "T->T*F",//4
            "T->T/F",//5
            "T->F",//6
            "F->(E)",//7
            "F->i"//8
    };
    private LinkedHashMap<String,String[]>[] LRGS_1=new LinkedHashMap[G_num];

    private Stack<Integer> stackState=new Stack<>();;
    private Queue<Map<String,String>> stackInput=new LinkedList<>();
    private Stack<String> stackSymbol=new Stack<>();

    private Stack<String> reduce_values=new Stack<>();
    private String action;
    private wordtype wordtype=new wordtype();
    private translate trans_mine = new translate();
    /**private String[][] ACTION2={
            {"S4","","" ,"" , "", "","S5","","1","2","3"},
            {"","","S6","S7","","","","Acc","","",""},
            {"","R3","R3","R3","S8","S9","R3","","",""},
            {"S4\t\t\t\t\t\tS5\t\t1\t2\t3"}
    };*/
    private String[][] ACTION= new String[action_h][action_w];


    private void buildLRGS(){
        String[] temp;
        int j=0;
        //LinkedHashMap<String,String[]> tempmap;
        for (String gs:LRGS_0){
            temp=gs.split("->");
            //tempmap=LRGS_1[j];
            //tempmap.put(temp[0],new String[5]);

            LRGS_1[j]=new LinkedHashMap<>();
            LRGS_1[j].put(temp[0],new String[5]);
            int[] poses= readline.matchoppls(temp[1]);
            int i=0;
            if (poses[0]==-1){
                //tempmap.get(temp[0])[i]=temp[1];
                LRGS_1[j].get(temp[0])[i]=temp[1];
            }

            while (poses[0]!=-1){
                if (poses[0]==0){
                    LRGS_1[j].get(temp[0])[i]=wordtype.keyopname[poses[1]];
                    //System.out.println("LRGS_1"+temp[0]+": "+i+": "+LRGS_1[j].get(temp[0])[i]);
                    temp[1]=temp[1].substring(wordtype.keyop[poses[1]].length());
                }else {
                    LRGS_1[j].get(temp[0])[i]=temp[1].substring(0,poses[0]);
                    //System.out.println("LRGS_1"+temp[0]+": "+i+": "+LRGS_1[j].get(temp[0])[i]);
                    i++;
                    LRGS_1[j].get(temp[0])[i]=wordtype.keyopname[poses[1]];
                    //System.out.println("LRGS_1"+temp[0]+": "+i+": "+LRGS_1[j].get(temp[0])[i]);
                    temp[1]=temp[1].substring(wordtype.keyop[poses[1]].length()+poses[0]);
                }
                i++;
                poses=readline.matchoppls(temp[1]);
                if (poses[0]==-1){
                    LRGS_1[j].get(temp[0])[i]=temp[1];
                    //System.out.println("LRGS_1"+temp[0]+": "+i+": "+LRGS_1[j].get(temp[0])[i]);
                }
            }
            //LRGS_1[j]=tempmap;
            j++;
        }

        for (int k=0;k<G_num;k++){
            for (String ss : LRGS_1[k].keySet()){
                System.out.println(ss+": "+ Arrays.toString(LRGS_1[k].get(ss)));

            }

        }

    }

    public void buildaction() throws IOException {
        FileReader fread = new FileReader("src/com/lab/files/ACTION.txt");
        BufferedReader bf=new BufferedReader(fread);
        String strLine=bf.readLine();
        String[] aclist;

        int i=0;
        int j=0;
        while(strLine!=null){
            //System.out.println("strLine:"+strLine);
            aclist=strLine.split("\t");
            i=0;
            for (String ss:aclist){
                ACTION[j][i]=ss.trim();
                //System.out.println("#"+ss+"#");
                i++;
            }

            strLine=bf.readLine();
            j++;
        }
        bf.close();
        fread.close();
        for (String[] sss:ACTION){
            System.out.println(Arrays.asList(sss));
        }
    }

    public parsing(String filename) throws IOException {
        readFile(filename);
        buildaction();
        buildLRGS();
        readmlist();
        analyzeLR();

    }

    public void readmlist(){
        for (Map kk: mList){
            for (Object k : kk.keySet()){
                System.out.println(k+","+kk.get(k));
            }
        }
    }


    private Map<String,String> divide(String str) {
        Map<String,String> maptemp=new LinkedHashMap<>();
        String[] codelist = str.split(",");
        //System.out.println(s);
        //单词分析类型
        if(codelist[0]==null || "".equals(codelist[0]) || codelist.length<=1){
            return null;
        }
        maptemp.put(codelist[0],codelist[1]);
        return maptemp;
    }
    public void readFile(String filename) throws IOException {
        FileReader fread = new FileReader(filename);
        BufferedReader bf=new BufferedReader(fread);
        String strLine=bf.readLine();
        //stackInput=new Stack<>();

        while(strLine!=null && !"".equals(strLine)){
            //System.out.println("strLine:"+strLine);
            list.add(strLine);

            mList.add(divide(strLine));
            stackInput.offer(divide(strLine));

            //System.out.println("读取下一行=========================");
            //list.clear();
            //mList.clear();
            strLine=bf.readLine();
        }

        bf.close();
        fread.close();

    }

    public void analyzeLR() throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(new File("src/com/lab/files/four.txt")));

        String action = "";
        Integer index = 0;
        stackState.push(0);
        Map<String, String> inputword = stackInput.peek();
        String a="";
        for (String aa:inputword.keySet()){
            a =aa;
        }

        System.out.println("****************LR分析过程**********");
        System.out.format("%-35s\t%-35s\t%-10s\tAction\n","state","Symbol","Input");
        //this.displayLR();
        int ac=0;
        while (true) {


            System.out.format("%-35s\t%-35s\t%-10s\t%s\n",stackState.toString(),stackSymbol.toString(),a,action);
            Integer ss = stackState.peek();
            //System.out.println(Action(ss,a));
            // 查表为移进


            if (Action(ss,a)!=null && !"".equals(Action(ss,a)) && Action(ss,a).charAt(0) == 'S') {
                String input_value=stackInput.poll().get(a);
                int tempint=Integer.valueOf(Action(ss, a).substring(1));
                stackState.push(tempint);
                if (a.equals("ID") || a.equals("INT") || a.equals("REAL")){
                    stackSymbol.push("i");
                }else {
                    stackSymbol.push(a);
                }

                if (isid(a)){
                    reduce_values.push(input_value);
                }

                //System.out.println("\t"+ss+"\t\t"+a+"\t\t\t\t"+Action(ss,a));
                if (stackInput.isEmpty())
                    break;
                inputword = stackInput.peek();
                a="";
                for (String aa:inputword.keySet()){
                    a =aa;
                }
                action = "shift "+tempint;
                //System.out.println("action="+action);

            }
            // 查表为归约
            else if (Action(ss,a)!=null && !"".equals(Action(ss,a)) && Action(ss, a).charAt(0) == 'R') {
                // 获取文法串
                int tempint=Integer.valueOf(Action(ss, a).substring(1));
                LinkedHashMap<String, String[]> str = LRGS_1[tempint];
                String[] strss=null;
                for (String[] strs:str.values())
                    strss=strs;//文法右组成
                int len = 0;
                while (strss[len]!=null && !"".equals(strss[len])){
                    len++;
                }

                String ssss=null;
                for (String ss2:str.keySet()){
                    ssss=ss2;
                    //System.out.println("规约"+ssss);

                }
                /**
                 * 在此加入四元式生成,用栈存放单个规约
                 */
                if (len==3){
                    String[] str_trans = new String[3];
                    for (int i = 0; i < len; i++) {
                        str_trans[len-1-i]=stackSymbol.pop();
                        stackState.pop();
                        //System.out.println("pop: "+str_trans[i]+"\nstate pop: "+stackState.pop());
                    }

                    trans_mine.transto(str_trans,ssss,writer);
                }else {
                    if (len==1){
                        String str_trans = stackSymbol.peek();
                        if (str_trans.equals("i")){
                            trans_mine.build_value(reduce_values.pop(),ssss);
                        }else {
                            trans_mine.change_value(ssss);
                        }
                    }
                    for (int i = 0; i < len; i++) {
                        stackSymbol.pop();
                        stackState.pop();
                        //System.out.println("pop: "+stackSymbol.pop()+"\nstate pop: "+stackState.pop());
                    }
                }

                // 弹出右部长度的符号和状态

                // goto的值进栈



                stackSymbol.push(ssss);//???????????
                Integer t = stackState.peek();
                String tempac=Action(t,stackSymbol.peek());
                //System.out.println("GOTO"+tempac);
                stackState.push(Integer.parseInt(tempac));//存疑？？？？？


                //System.out.println("key: "+str.keySet().toString());
                action = "reduce:" + tempint;
                //System.out.println("Action= "+action);

            } else if (Action(ss , a).equals("Acc")){
                System.out.println("program is accept!");
                break;
            }
            else{
                System.out.println("ERROR!");
                if (stackInput.isEmpty())
                    break;
                inputword = stackInput.poll();

                action = "jump ";
                ac++;
                System.out.println("action="+action+a+"input: "+ss+a+Action(ss,a));

                a="";
                for (String aa:inputword.keySet()){
                    a =aa;
                }
            }

        }
        System.out.println("analyze LR successfully , error :"+ac);
        System.out.println("****************LR分析过程**********");
        writer.close();
    }

    private boolean isid(String wordt) {
        if (wordt.equals("ID") || wordt.equals("INT") || wordt.equals("REAL")){
            return true;
        }
        return false;
    }

    private void seestackstate() {
        //for (Integer i:stackState)
          //  System.out.format("%d,",i);
        System.out.format("%35s",stackState.toString());

    }
    private char seestacksm() {
        //for (String i:stackSymbol)
         //   System.out.format("%s,",i);

        System.out.format("%35s,",stackSymbol.toString());

        return '|';
    }

    private String Action(Integer state,String wordt) {
        int ww=0;
        if (wordt.equals("ID") || wordt.equals("INT") || wordt.equals("REAL") || wordt.equals("i")){
            ww=6;
            return ACTION[state][ww];
        }



        switch (wordt){
            case "E":ww=action_w-3;break;
            case "T":ww=action_w-2;break;
            case "F":ww=action_w-1;break;
            case "#":ww=action_w-4;break;
            default:ww=-1;
        }
        if (ww!=-1)
            return ACTION[state][ww];

        ww=wordtype.getkeyvalue(wordt);


        if (ww!=-1 && ww<action_w-3){
            return ACTION[state][ww];
        }
        //System.out.println(state+","+wordt);
        return null;
    }




}
