package com.xvr;

public class Start {

    public static void main (String[] args){

        String[] strings = new String[15] ;
        long[] var = new long[15];
        for (int i =0; i<15; i++){
            strings[i] = "class"+i;
            var[i] = i*10;

        }

        Searcher searcher = new Searcher();
        searcher.refresh(strings,var);
    }
}
