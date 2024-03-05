package src.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.Reader;


public class Map {
    public void readFile() {

        JSONParser parser = new JSONParser();
        Reader reader = null;

        try {
            reader = new FileReader("res/esempio.json");
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        Object jsonObj = null;
        try {
            jsonObj = parser.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        JSONObject jsonObject = (JSONObject) jsonObj;

        long height = (long) jsonObject.get("height");
        System.out.println("height = " + height);

        long length = (Long) jsonObject.get("length");
        System.out.println("length = " + length);


        JSONArray valori = (JSONArray) jsonObject.get("layer_one");

        int[] values = new int[10];
        String s = "";

        for(int i = 0; i < 10; i++){
            values[i] = Integer.parseInt(valori.get(i).toString());
            System.out.println(values[i]);
        }

        JSONArray secondoLayer = (JSONArray) jsonObject.get("layer_two");

        int[] layerDue = new int[10];

        for(int i = 0; i < 10; i++){
            layerDue[i] = Integer.parseInt(secondoLayer.get(i).toString());
            System.out.println(layerDue[i]);
        }

        try {
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
