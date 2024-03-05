package src.model;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import java.io.Reader;


public class Map {

    private int[][][] strati;
    private final int FIRST_LAYER = 0;
    private final int SECOND_LAYER = 1;
    private final int THIRD_LAYER = 2;
    private final int FOURTH_LAYER = 3;



    public void loadMap(String mapPath) {

        strati = new int[4][][];

        //roba per leggere il file
        JSONParser parser = new JSONParser();
        Reader reader = null;

        try {
            reader = new FileReader(mapPath);
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        //per leggere il file di tipo json
        Object jsonObj = null;
        try {
            jsonObj = parser.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) jsonObj;

        //prendo le dimensioni della stanza
        int height = Integer.parseInt(jsonObject.get("height").toString());
        System.out.println("height = " + height);

        int length = Integer.parseInt(jsonObject.get("length").toString());
        System.out.println("length = " + length);

        strati[FIRST_LAYER] = new int[height][length];
        strati[SECOND_LAYER] = new int[height][length];
        strati[THIRD_LAYER] = new int[height][length];
        strati[FOURTH_LAYER] = new int[height][length];

        int row = -1;
        int col = 0;

        //primo layer
        JSONArray firstLayerJson = (JSONArray) jsonObject.get("layer_one");
        for(int i = 0; i < firstLayerJson.size(); i++){
            if(i % length == 0) {
                row++;
                col = 0;
            }
            col = i % length;
            strati[FIRST_LAYER][row][col] = Integer.parseInt(firstLayerJson.get(i).toString());
        }


        //secondo layer
        JSONArray secondLayerJson = (JSONArray) jsonObject.get("layer_two");
        row = -1;
        col = 0;
        for(int i = 0; i < secondLayerJson.size(); i++){
            if(i % length == 0) {
                row++;
                col = 0;
            }
            col = i % length;
            strati[SECOND_LAYER][row][col] = Integer.parseInt(secondLayerJson.get(i).toString());
        }


        //terzo layer
        JSONArray thirdLayerJson = (JSONArray) jsonObject.get("layer_three");
        row = -1;
        col = 0;
        for(int i = 0; i < secondLayerJson.size(); i++){
            if(i % length == 0) {
                row++;
                col = 0;
            }
            col = i % length;
            strati[THIRD_LAYER][row][col] = Integer.parseInt(thirdLayerJson.get(i).toString());
        }


        //quarto layer
        JSONArray fouthLayerJson = (JSONArray) jsonObject.get("layer_four");
        row = -1;
        col = 0;
        for(int i = 0; i < fouthLayerJson.size(); i++){
            if(i % length == 0) {
                row++;
                col = 0;
            }
            col = i % length;
            strati[FOURTH_LAYER][row][col] = Integer.parseInt(fouthLayerJson.get(i).toString());
        }

        try {
            reader.close();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        //stampa matrici
        for(int i = 0; i < 4; i++) {
            for (int y = 0; y < height; y++) {
                for (int z = 0; z < length; z++) {
                    System.out.print(strati[i][y][z] + " ");
                }
                System.out.println();
            }
            System.out.println("///////////////////////////////////////////");
        }

    }

}
