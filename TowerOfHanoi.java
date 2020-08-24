package com.saptakdas.misc;

import java.util.Hashtable;
import java.util.LinkedList;

public class TowerOfHanoi {
    private static int numberOfTiles;
    private static Hashtable<String, LinkedList<Integer>> currentState;
    private static LinkedList<String> allTowerNames;

    //Constructor
    public TowerOfHanoi(int tiles){
        this.numberOfTiles=tiles;
        reset();
        LinkedList<String> towerNames=new LinkedList();
        towerNames.addLast("A");
        towerNames.addLast("B");
        towerNames.addLast("C");
        allTowerNames=towerNames;
    }
    //Getters
    public int getNumberOfTiles() {
        return numberOfTiles;
    }

    public void getCurrentState() {
        System.out.println();
        System.out.println("A: "+currentState.get("A"));
        System.out.println("B: "+currentState.get("B"));
        System.out.println("C: "+currentState.get("C"));
        System.out.println();
    }

    //Methods for solving
    public void solveByRecursion(){
        //Start point is defaulted to "A"
        //End point is defaulted to "C"
        var startTower="A";
        var endTower="C";
        solveByRecursion(startTower, endTower);
    }

    public void solveByRecursion(String startTower, String endTower){
        if (!checkBeforeSolving(startTower)){
            System.out.println("Error: Tiles are not on one tower "+startTower+".");
            System.exit(0);
        }
        System.out.println("Solving Tower...");
        moveMany(numberOfTiles, startTower, endTower);
    }

    public void move(String startTower, String endTower){
        try {
            int sizeOfStartTower=currentState.get(startTower).size();
            int lastElementInStartTower = currentState.get(startTower).get(sizeOfStartTower-1);
            currentState.get(startTower).removeFirstOccurrence(lastElementInStartTower);
            currentState.get(endTower).addLast(lastElementInStartTower);
            getCurrentState();
        }catch(IndexOutOfBoundsException e){
            System.out.println("Error: This move cannot be executed because there are no tiles on "+startTower+".");
            System.exit(0);
        }
    }

    public void moveMany(int tiles, String startTower, String endTower){
        String otherTower=getOtherTower(startTower, endTower);
        if (tiles>1){
            moveMany(tiles-1, startTower, otherTower);
            moveMany(1, startTower, endTower);
            moveMany(tiles-1, otherTower, endTower);
        }
        else {
            move(startTower, endTower);
        }
    }

    private String getOtherTower(String startTower, String endTower){
        String otherTowerName=null;
        for (String towerName: allTowerNames){
            if (!towerName.equals(startTower) && !towerName.equals(endTower))
                otherTowerName=towerName;
        }
        return otherTowerName;
    }

    private boolean checkBeforeSolving(String filledTowerName){
        var counter=0;
        String name = null;
        for (String towerName: allTowerNames){
            if (currentState.get(towerName).size()>0) {
                counter++;
                name=towerName;
            }
        }
        if (counter==1 && name.equals(filledTowerName))
            return true;
        else
            return false;
    }

    public void reset(){
        Hashtable<String,LinkedList<Integer>> currentStateHashtable=new Hashtable<>();
        LinkedList<Integer> rowA=new LinkedList<>();
        for (int i=numberOfTiles; i>=1; i--)
            rowA.addLast(i);
        currentStateHashtable.put("A",rowA);
        currentStateHashtable.put("B",new LinkedList());
        currentStateHashtable.put("C",new LinkedList());
        this.currentState=currentStateHashtable;
    }
}

class HanoiTest{
    public static void main(String[] args) {
        var tower1= new TowerOfHanoi(3);
        System.out.println(tower1.getNumberOfTiles());
        tower1.getCurrentState();
        tower1.solveByRecursion();
    }
}