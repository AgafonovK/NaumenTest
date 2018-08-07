package com.xvr;

import java.util.*;

public class Helper {
    /*
        treemap: key - nameClass, value - msec;
        valueForSort: help structure for sorting

     */
    private TreeMap<String,Long> ClassDateCreate = new TreeMap<>();
    private List<Long> valueForSort = new ArrayList<>();
    private String[] nameClass;
    private long[] varMs;

    Helper(String [] nameClass1, long[] varMs1){
        this.nameClass = nameClass1;
        this.varMs = varMs1;
    }

    public void init(){
        for (int i = 0; i<nameClass.length; i++){
            ClassDateCreate.put(nameClass[i],varMs[i]);
        }
        System.out.println(ClassDateCreate);

    }

    public String[] search(String varSearchStr){


        //findClasses contains find elements only. Sorted by nameClass.
        // valueForSort contains value in msec.
        TreeMap<String,Long> findClasses = new TreeMap<>();
        for (Map.Entry<String,Long> var3 : ClassDateCreate.entrySet()){
            if (var3.getKey().startsWith(varSearchStr)){
                findClasses.put(var3.getKey(),var3.getValue());
                valueForSort.add(var3.getValue());

            }
        }

        System.out.println("найденные классы: " + findClasses);
        System.out.println("для сортировки: " + valueForSort);
        Collections.sort(valueForSort);
        // for final return string[]
        String[] finalFindClass = new String[12];

        System.out.println(valueForSort.size());
        for (int i = 0; i< valueForSort.size(); i++){
            //return array must be 12
            if (i==12){
                break;
            }

            for (Map.Entry<String,Long> var7: findClasses.entrySet()){
                if (valueForSort.get(i).equals(var7.getValue())){
                    finalFindClass[i]=var7.getKey();
                 //  System.out.println(finalFindClass[i] + " " + var7.getKey());
                }
            }
        }
        for (String str: finalFindClass) {
            System.out.println("отсортированный: " + str);
        }
        return finalFindClass;
    }
}
