package com.lab.translation;

import com.lab.wordscanner.utils.wordtype;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Stack;

public class translate {
    String[] midcode;
    ArrayList<String[]> code_four = new ArrayList<>();
    private Stack<String[]> reduce_values = new Stack<>();
    private wordtype wordtype_m=new wordtype();
    private Integer T=1;

    public void build_value(String value_r,String sym){
        //System.out.println("记录符号 "+sym+": "+value_r);
        reduce_values.push(new String[]{value_r,sym});
    }
    public void change_value(String sym){
        String[] temp=reduce_values.pop();
        reduce_values.push(new String[]{temp[0],sym});
        /**
        if (temp[0].charAt(0)=='T'){
            reduce_values.push(new String[]{temp[0],sym});
        }else {
            reduce_values.push(new String[]{temp[0],sym});
        }*/

    }
    public void transto(String[] codelist,String reduce,BufferedWriter writer) throws IOException {
        if (codelist.length==3){
            int pos = Arrays.asList(wordtype.keyopname).indexOf(codelist[1]);
            if (pos!=-1){
                //if (reduce_values.peek()[1]==codelist[2])
                    codelist[2]=reduce_values.pop()[0];
                //if (reduce_values.peek()[1]==codelist[0])
                    codelist[0]=reduce_values.pop()[0];
                //助记符申请T，以后四元式自动替换
                reduce_values.push(new String[]{"T"+T,reduce});
                code_four.add(new String[]{wordtype.keyop[wordtype_m.getkeyvalue(codelist[1])],codelist[0],codelist[2],"T"+T});

                //System.out.println(wordtype.keyop[wordtype_m.getkeyvalue(codelist[1])]+","+codelist[0]+","+codelist[2]+","+"T"+T);
                writer.write(wordtype.keyop[wordtype_m.getkeyvalue(codelist[1])]+","+codelist[0]+","+codelist[2]+","+"T"+T+"\n");
                T++;
                return;

            }else {

                    //if (reduce_values.peek()[1]==codelist[1])
                    codelist[1]=reduce_values.pop()[0];
                    //助记符申请T，以后四元式自动替换
                    reduce_values.push(new String[]{codelist[1],reduce});
                    //code_four.add(new String[]{wordtype.keyop[wordtype_m.getkeyvalue(codelist[0])],codelist[1],wordtype.keyop[wordtype_m.getkeyvalue(codelist[2])],"T"+T});
                    //T++;
                    //System.out.println(wordtype.keyop[wordtype_m.getkeyvalue(codelist[0])]+","+codelist[1]+","+wordtype.keyop[wordtype_m.getkeyvalue(codelist[2])]+","+"T"+T);
                    //writer.write(wordtype.keyop[wordtype_m.getkeyvalue(codelist[0])]+","+codelist[1]+","+wordtype.keyop[wordtype_m.getkeyvalue(codelist[2])]+","+"T"+T+"\n");

                //T++;

                return;
            }
        }
        System.out.println("???: "+codelist.toString()+reduce);
    }
}
