package com.xvr;

public class Start {

    public static void main (String[] args){

        String[] strings = new String[15] ;
        long[] var = new long[15];
        for (int i =0; i<15; i++){
            var[i] = i*10;
        }
        strings[0]="Start";
        strings[1]="Indianapolis";
        strings[2]="NextInd";
        strings[3]="Index";
        strings[4]="Indexation";
        strings[5]="Close";
        strings[6]="People";
        strings[7]="Human";
        strings[8]="Foindow";
        strings[9]="Device";
        strings[10]="Sweet";
        strings[11]="Road";
        strings[12]="Stage";
        strings[13]="Back";
        strings[14]="World";

        Searcher searcher = new Searcher();
        searcher.refresh(strings,var);
        searcher.guess("Ind");
    }
}
