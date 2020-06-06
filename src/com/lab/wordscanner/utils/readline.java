package com.lab.wordscanner.utils;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Pattern;

public class readline {
    String[] codelist;

    //List<Map<Integer,String>> mList= new ArrayList<>();

    private ArrayList<String[]> mList = new ArrayList<>();
    public List<Map<Integer,String>> key_word= new ArrayList<>();

    public void init_keyword(){
        //Map<Integer,String> m1 = HashMap<>();
        //key_word.add(m1.put(1,"IF"));


    }
    public ArrayList<String[]> Divide(String str){
        //mList.clear();
        if (!mList.isEmpty())
            mList.clear();
        for (String s : codelist = str.split(" ")) {
            //System.out.println(s);
            //单词分析类型
            if(s==null || "".equals(s)) continue;
            //mList.add(istype(s));
            mList.addAll(istype(s));

            //System.out.println(istype(s));
        }
        return mList;
    }

    private Integer matchs(String string1,String string2){
        int pos2=0;
        int pl=0;
        if(string1.regionMatches(0,string2,0,string2.length())){
            if(string1.length()==string2.length()){
                return 0;
            }
            if (string1.length()<string2.length()){
                return -1;
            }
            if (string1.length()>string2.length()){
                int pos=0;
                for (String word : wordtype.keyop) {
                    if(word.equals(string1.substring(string2.length())) || word.equals(string1.substring(0,string2.length()))){
                        if (pl<word.length()){
                            pl=word.length();
                            pos2=pos;
                        }
                    }
                    pos++;
                }

                if (pos2==-1){
                    return string2.length();
                }else {
                    return -1;
                }

            }
        }
        return -1;
    }
    public static int[] matchoppls(String string1){
        int pos=-1;
        int i=0;
        for (String word : wordtype.keyop) {
            if (string1.contains(word)){

                    if (word=="<" || word==">" || word==":" ||word=="="){
                        int ppp=string1.indexOf(word);
                        if (ppp+1<string1.length() && string1.charAt(ppp+1)!='=' && string1.charAt(ppp+1)!='>' && ppp>0 && !Arrays.asList(wordtype.keyop).contains(string1.substring(ppp-1,ppp+1))){
                            pos=ppp;
                            //System.out.println(string1+pos);
                            return new int[]{pos , i};
                        }
                    }else {
                        pos=string1.indexOf(word);
                        //System.out.println(string1+pos);
                        return new int[]{pos , i};
                    }


            }
            i++;
        }
        //System.out.println(string1+pos);
        return new int[]{-1 , -1};
    }
    public static int[] matchop(String string1){
        int pos=-1;
        int i=0;
        for (String word : wordtype.keyop) {
            if (string1.contains(word)){
                if(word=="-" && string1.indexOf("-")!=0 && string1.charAt(string1.indexOf("-")-1)=='E'){
                    //System.out.println(string1+"+++++");
                }else {
                    if (word=="<" || word==">" || word==":" ||word=="="){
                        int ppp=string1.indexOf(word);
                        if (ppp+1<string1.length() && string1.charAt(ppp+1)!='=' && string1.charAt(ppp+1)!='>' && ppp>0 && !Arrays.asList(wordtype.keyop).contains(string1.substring(ppp-1,ppp+1))){
                            pos=ppp;
                            //System.out.println(string1+pos);
                            return new int[]{pos , i};
                        }
                    }else {
                        pos=string1.indexOf(word);
                        //System.out.println(string1+pos+":"+i);
                        return new int[]{pos , i};
                    }
                }

            }
            i++;
        }
        //System.out.println(string1+pos);
        return new int[]{-1 , -1};
    }
    private ArrayList<String[]> istype(String string){
        Map<Integer, String> map1= new LinkedHashMap<>();
       ArrayList<String[]> map2=new ArrayList<>();
        //var pos= Arrays.binarySearch(wordtype.keyword,string);
        Integer pos=0;

        if(isReal(string)){
            string = String.valueOf(getDoubleNumber(string));
            map1.put(wordtype.REAL,string);

            map2.add(new String[]{String.valueOf(wordtype.REAL) , string});

        }
        pos=-1;
        int pll=0;
        int[] poss=matchop(string);
        pos = poss[0];
        while (pos!=-1){
            if(pos==0){
                /**
                int pos2=0;
                for (String word : wordtype.keyop) {

                    if(word.length()<=string.length() && word.equals(string.substring(0,word.length()))){

                        if (pll<word.length()){
                            pos=pos2;
                            pll=word.length();
                        }
                    }
                    pos2++;
                }*/
                pll=Arrays.asList(wordtype.keyop).get(poss[1]).length();
                map1.put(poss[1]+wordtype.KETOP,string.substring(0,pll));
                //System.out.println("编译中"+poss[1]+wordtype.KETOP+","+wordtype.KETOP+" "+string.substring(0,pll));
                map2.add(new String[]{String.valueOf(poss[1]+ wordtype.KETOP) ,string.substring(0,pll)});
                string=string.substring(pll);
                //System.out.println("cut1:"+string);

            }else if (pos>0){
                //map1.putAll(istype(string.substring(0,pos)));
                map2.addAll(istype(string.substring(0,pos)));
                string=string.substring(pos);
                //System.out.println("编译中"+string+" "+poss[1]);
                /**
                int pos2=0;
                pll=0;
                for (String word : wordtype.keyop) {
                    if(word.length()<=string.length() &&word.equals(string.substring(0,word.length()))){
                        if (pll<word.length()){
                            pos=pos2;
                            pll=word.length();
                        }
                    }
                    pos2++;
                }*/
                pll=Arrays.asList(wordtype.keyop).get(poss[1]).length();
                map1.put(poss[1]+wordtype.KETOP,string.substring(0,pll));
                map2.add(new String[]{String.valueOf(poss[1]+ wordtype.KETOP) ,string.substring(0,pll)});
                string=string.substring(pll);
                //System.out.println("编译中cut3:"+poss[1]+"+"+wordtype.KETOP+string);
                if (string.length()==0){
                    //return map1;
                    return map2;
                }

            }


            pos=-1;
            pll=0;
            poss = matchop(string);
            pos=poss[0];
        }


        pos=0;
        int temp=0;
        for (String word : wordtype.keyword) {
            temp=matchs(string,word);
            if (temp == 0) {
                map1.put(pos+wordtype.KETWORD,string);
                map2.add(new String[]{""+pos+wordtype.KETWORD,string});
                //return map1;
                return map2;
            }
            if (temp>0){
                map1.put(pos+wordtype.KETWORD,string.substring(0,temp));
                map2.add(new String[]{""+pos+wordtype.KETWORD,string.substring(0,temp)});
                //System.out.println(string.substring(0,temp)+temp+string);
                string=string.substring(temp);
            }
            /**
             if(word.equals(string)){
             map1.put(pos+wordtype.KETWORD,string);
             return map1;
             }*/
            pos++;
        }

        pos=0;
        temp=0;
        if(string==null || "".equals(string)) return map2;//return map1;
        for (String word : wordtype.keyop) {
            temp=matchs(string,word);
            if (temp == 0) {
                map1.put(pos+wordtype.KETOP,string);
                map2.add(new String[]{""+pos+wordtype.KETOP,string});
                return map2;
            }
            if (temp>0){
                map1.put(pos+wordtype.KETOP,string.substring(0,temp));
                map2.add(new String[]{""+pos+wordtype.KETOP,string.substring(0,temp)});
                //System.out.println(string.substring(0,temp)+temp+string);
                string=string.substring(temp);
            }
            /**if(word.equals(string)){
                map1.put(pos+wordtype.KETOP,string);
                return map1;
            }*/
            pos++;
        }
        /*
        if (pos>-1 && pos<wordtype.keyword.length){
            System.out.println(pos);
            map1.put(pos+1,string);
            return map1;
        }*/
        if(isInteger(string)){
            map1.put(wordtype.INT,string);
            map2.add(new String[]{""+wordtype.INT,string});
            return map2;
        }else if(isDouble(string)){
            map1.put(wordtype.REAL,string);
            map2.add(new String[]{""+wordtype.REAL,string});
            return map2;
        }else if (isReal(string)){
            string = String.valueOf(getDoubleNumber(string));
            map2.add(new String[]{""+wordtype.REAL,string});
            map1.put(wordtype.REAL,string);
            return map2;
        }
        /*pos=Arrays.binarySearch(wordtype.keyop,string);
        if(pos!=-1){
            map1.clear();
            map1.put(pos+1+wordtype.REAL,string);
            return map1;
        }*/
        if(string.equals(";") || string==";" ){
            map1.clear();
            map1.put(wordtype.UCON,string);
            map2.add(new String[]{""+wordtype.UCON,string});
            return map2;
        }
        if(IsID(string)){
            map1.clear();
            map1.put(wordtype.ID,string);
            map2.add(new String[]{""+wordtype.ID,string});
            return map2;
        }

        System.out.println("weong input"+string);
        return map2;
    }
    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    public static boolean isInteger(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return pattern.matcher(str).matches();
    }
    /*
     * 判断是否为浮点数，包括double和float
     * @param str 传入的字符串
     * @return 是浮点数返回true,否则返回false
     */
    public static boolean isDouble(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }

    public static boolean isReal(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*[Ee][\\\\+-]?[0-9]*");
        return pattern.matcher(str).matches();
    }

    private double getDoubleNumber(String str){
        double number = 0;
        BigDecimal bd = new BigDecimal(str);
        number =  Double.parseDouble(bd.toPlainString());

        return number;
    }

    public static boolean IsID(String word) {
        Pattern pattern = Pattern.compile("([a-z]|[A-Z]*$)([a-zA-Z0-9]*$)?");
        return pattern.matcher(word).matches();
    }
/*
    public static boolean isNumber(String number) {
        //先判断number不为空。
        if (number == null || "".equals(number))
            return false;
        int index = number.indexOf(".");
        if (index < 0) {
            //判断number是否为数字。
            return StringUtils.isNumeric(number);
        } else {
            String num1 = number.substring(0 , index);
            String num2 = number.substring(index + 1);

            return StringUtils.isNumeric(num1) && StringUtils.isNumeric(num2);
        }
    }
 */
}
