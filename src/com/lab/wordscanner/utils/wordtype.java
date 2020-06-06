package com.lab.wordscanner.utils;

import java.util.*;

public class wordtype {

    private static List<Map<String,Integer>> keywords=new ArrayList<>();
    public final static Integer UCON =6;  //Suppose the class number of unsigned constant is 7

    public final static Integer KETWORD=1;
    public final static String[] keyword={"if", "then", "else","for","begin","end"};
    public final static Integer ID=keyword.length+1;
    public final static Integer INT=keyword.length+2;
    public final static Integer REAL=keyword.length+3;
    public final static Integer KETOP=keyword.length+4;
    public final static String[] keyop = new String[]{
            "(",
            ")",
            "+" ,
            "-" ,
            "*" ,
            "/",
            "#",
            "<" ,
            "<=" ,
            "=" ,
            "<>" ,
            ">" ,
            ">=" ,
            ":="



    };
    public final static String[] keyopname = new String[]{
            "BEGIN",
            "END",
            "PL",
            "MI",
            "MU",
            "DI",
            "#",
            "LT",
            "LE",
            "EQ",
            "NE",
            "GT",
            "GE",
            "IS"


    };
    public wordtype(){
        //Map<String, String> map1= new HashMap<>();


        for (int i=0;i<keyword.length;i++){
            //map1.put(ss.toUpperCase(),ss);
            keywords.add(builtmap(keyword[i],KETWORD+i));
            //map1.clear();
        }

        keywords.add(builtmap("ID",ID));
        keywords.add(builtmap("INT",INT));

        keywords.add(builtmap("REAL",REAL));
        for(int i=0;i<keyop.length;i++){
            keywords.add(builtmap(keyopname[i],KETOP+i));
        }

    }
    private Map<String, Integer> builtmap(String ss , Integer id){
        Map<String, Integer> map1= new HashMap<>();
        map1.put(ss.toUpperCase(),id);
        return map1;
    }
    private Map<String, String> builtmap (String ss,String ss2){
        Map<String, String> map1= new HashMap<>();
        map1.put(ss,ss2);
        return map1;
    }
    public String getkeyname(Integer pos){
        Map<String, Integer> mm=keywords.get(pos-1);
        for (String s1 : mm.keySet()) {
            return s1;

        }
        return null;
    }
    public Integer getkeyvalue(Integer pos){
        Map<String, Integer> mm=keywords.get(pos-1);
        for (String s1 : mm.keySet()) {
            return mm.get(s1);

        }
        return null;
    }
    public Integer getkeyvalue(String name){
        int ii=Arrays.asList(keyopname).indexOf(name);
        if (ii>=0){
            return ii;
        }
        return -1;
    }

}
