package com.xvr;

import java.util.HashMap;
import java.util.Map;

public class Helper {

    HashMap <Long,String> hashMap = new HashMap<>();
    private String[] nameClass;
    private long[] varMs;

    Helper(String [] nameClass1, long[] varMs1){
        this.nameClass = nameClass1;
        this.varMs = varMs1;
    }

    public HashMap<Long,String> init(){
        for (int i = 0; i<nameClass.length; i++){
            hashMap.put(varMs[i],nameClass[i]);
        }
        hashMap.
        for (Map.Entry<Long, String> value: hashMap.entrySet()){
            System.out.println(value.getKey() + " " + value.getValue());
        }

        return hashMap;
    }
}
