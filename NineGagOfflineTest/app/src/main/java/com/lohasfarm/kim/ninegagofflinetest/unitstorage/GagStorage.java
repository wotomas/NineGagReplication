package com.lohasfarm.kim.ninegagofflinetest.unitstorage;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk.FileManager;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk.JsonStorable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by kim on 2015-11-18.
 */
public class GagStorage implements JsonStorable{
    //hot
    private ArrayList<Gag> _gagList;
    //fresh
    private ArrayList<Gag> _gagSortedThroughTime;
    //trending
    private ArrayList<Gag> _gagSortedThroughComments;
    //top
    private ArrayList<Gag> _gagSortedThroughPoints;

    //private int[]
    //0 - Horizontal List loaded Number
    //1 - Vertical List loaded Number
    //2 - Trending List loaded Number
    //3 - Fresh List loaded Number
    //4 - Top List loaded Number
    private int[] _lastLoadedGagNumber = {0,0,0,0,0};

    private int _gagNumber = 0;

    public GagStorage() {
        _gagList = new ArrayList<Gag>();
        _gagSortedThroughTime = new ArrayList<Gag>();
        _gagSortedThroughComments = new ArrayList<Gag>();
        _gagSortedThroughPoints = new ArrayList<Gag>();
    }

    public void sortAccordingly(Context context) {
        ArrayList<Gag> sample = new ArrayList<Gag>();
        sample.addAll(_gagList);
        _gagList = doMergeSort(sample, "id");

        _gagSortedThroughTime = doMergeSort(sample, "time");
        //Log.d("SortingTest", "Sorted Through time: " + _gagSortedThroughTime.toString());
        _gagSortedThroughComments = doMergeSort(sample, "comments");
        //Log.d("SortingTest", "Sorted Through comments: " + _gagSortedThroughComments.toString());
        _gagSortedThroughPoints = doMergeSort(sample, "points");
        saveToJson(context);
        //Log.d("SortingTest", "Sorted Through points: " + _gagSortedThroughPoints.toString());

    }

    public int get_totalSIze() {
        return _gagNumber;
    }

    public int get_lastLoadedGagNumber(int number) {
        return _lastLoadedGagNumber[number];
    }

    public ArrayList<Gag> get_gagList(int type, int start, int end) {
        Log.d("FragmentCheck", "Input fragment getlist is " + Integer.toString(type));
        ArrayList<Gag> output = new ArrayList<Gag>();
        int counter = start;
        switch(type) {
            case 0:
                Log.d("FragmentCheck", "Get From normal list");
                for(Gag gag : _gagList){
                    if(counter == _gagList.size()) {
                        break;
                    }
                    output.add(gag);
                    counter++;
                    _lastLoadedGagNumber[type] = counter;
                    if(counter > end) {
                        break;
                    }
                }
                break;
            case 1:
                Log.d("FragmentCheck", "Get From vertical list");
                for(Gag gag : _gagSortedThroughComments){
                    if(counter == _gagSortedThroughComments.size()) {
                        break;
                    }
                    output.add(gag);
                    counter++;
                    _lastLoadedGagNumber[type] = counter;
                    if(counter > end) {
                        break;
                    }
                }
                break;
            case 2:
                Log.d("FragmentCheck", "Get From Point list");
                for(Gag gag : _gagSortedThroughComments){
                    if(counter == _gagSortedThroughComments.size()) {
                        break;
                    }
                    output.add(gag);
                    counter++;
                    _lastLoadedGagNumber[type] = counter;
                    if(counter > end) {
                        break;
                    }
                }
                break;
            case 3:
                Log.d("FragmentCheck", "Get From Comments list");
                for(Gag gag : _gagSortedThroughTime){
                    if(counter == _gagSortedThroughTime.size()) {
                        break;
                    }
                    output.add(gag);
                    counter++;
                    _lastLoadedGagNumber[type] = counter;
                    if(counter > end) {
                        break;
                    }
                }
                break;
            case 4:
                Log.d("FragmentCheck", "Get From Time list");
                for(Gag gag : _gagSortedThroughPoints){
                    if(counter == _gagSortedThroughPoints.size()) {
                        break;
                    }
                    output.add(gag);
                    counter++;
                    _lastLoadedGagNumber[type] = counter;
                    if(counter > end) {
                        break;
                    }
                }
                break;
        }

        return output;
    }

    public void addGag(Gag gag, Context context) {
        _gagList.add(gag);
        _gagNumber++;
        saveToJson(context);
    }

    private ArrayList<Gag> doMergeSort(ArrayList<Gag> gagList, String type) {
        //use MergeSort sort because
        //1. Mergesort works even better on linked lists than it does on arrays
        //2. Stable
        //3. guaranteed n Log n performance
        ArrayList<Gag> output = new ArrayList<Gag>();
        output.addAll(gagList);
        //Log.d("Sorting Test", "output before: " + output.toString());
        if(type.equals("time")) {
            Collections.sort(output, new Comparator<Gag>() {
                @Override
                public int compare(Gag lhs, Gag rhs) {
                    return rhs.get_postDate().compareTo(lhs.get_postDate());
                }
            });
            Log.d("Sorting Test", "Sort Once!");
        } else if(type.equals("points")) {
            Collections.sort(output, new Comparator<Gag>() {
                @Override
                public int compare(Gag lhs, Gag rhs) {
                    return rhs.get_points() - lhs.get_points();
                }
            });
            Log.d("Sorting Test", "Sort Twice!");
        } else if(type.equals("comments")){
            Collections.sort(output, new Comparator<Gag>() {
                @Override
                public int compare(Gag lhs, Gag rhs) {
                    return rhs.get_comments() - lhs.get_comments();
                }
            });
            Log.d("Sorting Test", "Sort Thrice!");
        } else {
            Collections.sort(output, new Comparator<Gag>() {
                @Override
                public int compare(Gag lhs, Gag rhs) {
                    return lhs.get_id() - rhs.get_id();
                }
            });
        }
        return output;
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
