package com.lohasfarm.kim.ninegagofflinetest.unitcontroller;

import android.content.Context;

import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.GagStorage;
import com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk.JsonStorable;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by kim on 2015-11-18.
 */
public class GagController {
    private static GagController _instance = null;
    private static GagStorage _gagStorage = null;

    public GagController() {

    }

    public static GagController getInstance() {
        if(_instance == null) {
            _instance = new GagController();
        }
        return _instance;
    }

    public void sortGags(Context context) {
        _gagStorage.sortAccordingly(context);
    }

    //Initialize Gag Storage
    public boolean initGagStorage(GagStorage gagStorage, Context context) {
        //ex) GagController.getInstance().initGagStorage(new GagStorage, getContext());
        //if Storage is not initialized
        if(_gagStorage == null) {
            //save a newly created storage
            _gagStorage = gagStorage;
            if(_gagStorage instanceof JsonStorable) {
                //a chekcer to double check if storage is jsonStorable
                //load from text file and store it to storage
                _gagStorage = (GagStorage) ((JsonStorable)_gagStorage).loadFromJson(context);
                if(_gagStorage == null) {
                    //if the text file has nothing inside
                    //just store the newly created storage
                    _gagStorage = gagStorage;
                }
            }
            return true;
        }
        return false;
    }
/**
    public LinkedList<Gag> get_gagSortedThroughTime(int number) {
        return _gagStorage.get_gagSortedThroughTime(number);
    }

    public LinkedList<Gag> get_gagSortedThroughTrend(int number) {
        return _gagStorage.get_gagSortedThroughTrend(number);
    }
 **/

    public Gag createRandomPost(int number) {
        final String[] TITLES = {
                "Just sitting here... waiting for my copy",
                "Are you serious?",
                "Kid fights off the need to sleep",
                "Anime vs. Reality",
                "Guy tries to steal human statues money",
                "Plants in Bethesdas office",
                "We have constructed additional pylons. (For charging)",
                "If the Avengers had Pokemons",
                "Ill never have children. Never.",
                "Taking off glasses makes you hotter. Its proven!" };
        Gag gag = new Gag();
        gag.set_id(number);

        gag.set_title(TITLES[(int)(Math.random() * 9)]);
        gag.set_comments((int) (Math.random() * 500));
        gag.set_points((int) (Math.random() * 300));

        Random r =new Random();
        long unixtime=(long) (1293861599+r.nextDouble()*60*60*24*365);
        Date now = new Date(unixtime);
        gag.set_postDate(now);

        gag.set_image((int) (Math.random() * 9));

        return gag;
    }

    public void addGag(Gag gag, Context context) {
        _gagStorage.addGag(gag, context);
    }

    public ArrayList<Gag> getList(int type, int start, int end) {
        return _gagStorage.get_gagList(type, start, end);
    }

    public int getLastLoadedGagNumber(int type) {
        return _gagStorage.get_lastLoadedGagNumber(type);
    }

    public int getTotalSize() {
        return _gagStorage.get_totalSIze();
    }

}
