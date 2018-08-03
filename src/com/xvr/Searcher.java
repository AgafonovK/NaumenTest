package com.xvr;

import java.util.HashMap;

public class Searcher implements ISearcher {

    public Searcher() {
    }

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        try {
            Helper helper = new Helper(classNames, modificationDates);
            helper.init();
        }catch (IllegalArgumentException e){
            System.out.println(e.fillInStackTrace());
        }

    }

    @Override
    public String[] guess(String start) {
        return new String[0];
    }
}
