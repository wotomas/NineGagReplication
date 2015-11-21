package com.lohasfarm.kim.ninegagofflinetest.unitstorage;

import android.content.Context;

import com.google.gson.Gson;
import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk.FileManager;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk.JsonStorable;

import java.util.LinkedList;

/**
 * Created by kim on 2015-11-18.
 */
public class GagStorage implements JsonStorable{
    private LinkedList<Gag> _gagList;
    //private LinkedList<Gag> _gagSortedThroughTime;
    //private LinkedList<Gag> _gagSortedThroughTrend;
    //private int _lastLoadedGagNumber;

    private int _gagNumber = 0;

    public GagStorage() {
        _gagList = new LinkedList<Gag>();
        //_gagSortedThroughTime = doInsertionSort(_gagList, "time");
        //_gagSortedThroughTrend = doInsertionSort(_gagList, "trend");
    }

   // public int get_lastLoadedGagNumber() {
        //return _lastLoadedGagNumber;
   // }

    public LinkedList<Gag> getEntireList() {
        return _gagList;
    }

    public LinkedList<Gag> get_gagList(int start, int end) {
        LinkedList<Gag> output = new LinkedList<Gag>();
        int counter = start;
        for(Gag gag : _gagList){
            if(counter == _gagList.size()) {
                break;
            }
            output.add(counter, gag);
            counter++;
            if(counter > end) {
                break;
            }
        }
        //_lastLoadedGagNumber = end;
        return output;
    }

    /**
    public LinkedList<Gag> get_gagSortedThroughTime(int number) {
        LinkedList<Gag> output = new LinkedList<Gag>();
        int counter = 0;
        for(Gag gag : _gagSortedThroughTime) {
            output.add(counter, _gagSortedThroughTime.get(counter));
            if(counter == number) {
                break;
            }
        }
        return output;
    }

    public LinkedList<Gag> get_gagSortedThroughTrend(int number) {
        LinkedList<Gag> output = new LinkedList<Gag>();
        int counter = 0;
        for(Gag gag : _gagSortedThroughTrend) {
            output.add(counter, _gagSortedThroughTrend.get(counter));
            if(counter == number) {
                break;
            }
        }
        return output;
    }
    **/

    public void addGag(Gag gag, Context context) {
        _gagList.add(gag);
        _gagNumber++;
        saveToJson(context);
    }

    private LinkedList<Gag> doInsertionSort(LinkedList<Gag> gagList, String type) {
        //use insertion sort because
        //1. there is high possibility that time is already sorted
        // ex) new posts are added into bottom, so insertion should run from bottom
        //2. stable, but becomes inefficient in large lists
        if(type.equals("time")) {
            Gag temp = new Gag();
            for(int i = 1; i < gagList.size(); i++) {
                for(int j = 1; j > 0; j--) {
                    if(gagList.get(j).get_postDate().getTime() < gagList.get(j-1).get_postDate().getTime() ) {
                        //if time is larger, it means more time has past since 1970,
                        //therefore its a more recent post
                        temp = gagList.get(j);
                        gagList.set(j, gagList.get(j-1));
                        gagList.set(j-1, temp);
                    }
                }
            }
        } else {
            Gag temp = new Gag();
            for(int i = 1; i < gagList.size(); i++) {
                for(int j = 1; j > 0; j--) {
                    if(gagList.get(j).get_points() < gagList.get(j-1).get_points() ) {
                        //if time is larger, it means more time has past since 1970,
                        //therefore its a more recent post
                        temp = gagList.get(j);
                        gagList.set(j, gagList.get(j-1));
                        gagList.set(j-1, temp);
                    }
                }
            }
        }
        return gagList;
    }

    @Override
    public String getFileName() {
        return "GAGJSON.txt";
    }

    @Override
    public Object loadFromJson(Context context) {
        //create a new Gson class to load file from the saved text file.
        //if the textfile is correctly loaded, deserialize the Json read into GagStorage class
        Gson gson = new Gson();
        String json = FileManager.getInstance().loadFromFile(getFileName(), context);
        if(json.equals("")) {
            return null;
        }

        return gson.fromJson(json, GagStorage.class);

    }

    @Override
    public void saveToJson(Context context) {
        Gson gson = new Gson();
        FileManager.getInstance().writeToFile(gson.toJson(this), getFileName(), context);
    }



}
