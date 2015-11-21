package com.lohasfarm.kim.ninegagofflinetest.fakeRemoteService;

import android.util.Log;

import com.lohasfarm.kim.ninegagofflinetest.unit.Gag;
import com.lohasfarm.kim.ninegagofflinetest.unitcontroller.GagController;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by kim on 2015-11-21.
 */
public class FakeRemoteService {
    private static final String _TAG = FakeRemoteService.class.getSimpleName();
    private LinkedList<Gag> _gagArray = GagController.getInstance().getEntireList();
    private int _gagIndex = 0;
    public ArrayList<Gag> getEntireList() {
        ArrayList<Gag> results = new ArrayList<Gag>();
        for(Gag newGag : _gagArray) {
            results.add(newGag);
        }
        return results;
    }

    public ArrayList<Gag> getNextGagBatch(int batchSize) {
        ArrayList<Gag> results = new ArrayList<Gag>();
        try {
            for (int i = _gagIndex; i < (_gagIndex + batchSize < _gagArray.size() ? _gagIndex + batchSize : _gagArray.size()); i++) {
                results.add(_gagArray.get(i));
                if (_gagIndex != 0) {
                    // Delay when retrieving each item
                    Thread.sleep(400L);
                }
            }
            // Keep track where the pointer is
            _gagIndex = _gagIndex + batchSize;
        } catch (InterruptedException e) {
            _gagIndex = 0;
            results.clear();
            Log.i(_TAG, "Fake Pulling Service Interrupted");
        }
        return results;
    }

    public void reset() {
        _gagIndex = 0;
    }

}
