package com.lohasfarm.kim.ninegagofflinetest.unitstorage.disk;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by kim on 2015-11-18.
 */
public class FileManager {
    private static FileManager instance = new FileManager();
    private final String _TAG = "FileManager";
    private boolean _ExternalStorageAvailable = false;
    private boolean _ExternalStorageWriteable = false;
    public FileManager() {

    }

    public static FileManager getInstance() {
        return instance;
    }

    public void deleteGagJson(Context context) {
        File dir = context.getFilesDir();
        File file = new File(dir, "GAGJSON.txt");
        if(file.delete()){
            Log.d(_TAG, "Internal File GAGJSON.txt was deleted");
        }
    }


    public void writeToFile(String inputString, String fileName, Context context) {
       try {
            FileOutputStream fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);

            fos.write(inputString.getBytes());
            fos.close();
            Log.d(_TAG, "GAGJSON file was saved correctly");
        } catch (Exception e) {
            Log.e(_TAG, "Error while writing to GAGJSON file", e);
        }
    }

    public String loadFromFile(String fileName, Context context) {
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder total = new StringBuilder();
            String line;
            while((line = r.readLine()) != null) {
                total.append(line);
            }
            r.close();
            inputStream.close();
            Log.d(_TAG, "Successfully loading from GAGJSON file");
            return total.toString();
        } catch (Exception e) {
            Log.e(_TAG, "Error while loading from GAGJSON file", e);
        }
        return "";


    }
}
