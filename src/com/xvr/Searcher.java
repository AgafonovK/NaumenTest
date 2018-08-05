package com.xvr;

public class Searcher implements ISearcher {

    private Helper helper;

    public Searcher() {
    }

    @Override
    public void refresh(String[] classNames, long[] modificationDates) {
        try {
            helper = new Helper(classNames, modificationDates);
            helper.init();
        }catch (IllegalArgumentException e){
            System.out.println(e.fillInStackTrace());
        }

    }

    @Override
    public String[] guess(String start) {

        String[] var = helper.search(start);
        for(String str: var){
            System.out.println(str);
        }
        return var;
    }
}
