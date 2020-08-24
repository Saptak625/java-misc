package com.saptakdas.misc;

import java.util.LinkedList;

public class Probability {
    public static void main(String[] args) {
        var list=new LinkedList();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");
        list.addLast("E");
        System.out.println(getCombinations(list,4));
    }
    public static LinkedList getCombinations(LinkedList iterable, int k){
        var n=iterable.size();
        var storageList=new LinkedList();
        var list=new LinkedList();
        //Prepping
        for (int i=0; i<k; i++){
            list.addLast(i+1);
        }
        //Algorithm
        boolean changed;
        while (((int) list.get(0))<(n-k+2)){
            changed=false;
            for (int i=0; i<list.size(); i++) {
                var index=list.size()-i-1;
                if ((int) list.get(index) > n) {
                    list.set(index-1,(int) list.get(index-1)+1);
                    for (int j=index; j<list.size(); j++){
                        list.set(j, (int) list.get(j-1)+1);
                    }
                    changed=true;
                }
            }
            if (!changed) {
                storageList.addLast(indexesToCombinations(list, iterable));
                list.set(list.size() - 1, (int) list.get(list.size() - 1) + 1);
            }
        }
        return storageList;
    }
    private static LinkedList indexesToCombinations(LinkedList<Integer> list, LinkedList iterable){
        var returnlist=new LinkedList();
        for (int index:list) {
            int i=index-1;
            returnlist.addLast(iterable.get(i));
        }
        return returnlist;
    }
}
