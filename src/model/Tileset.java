package src.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.json.simple.JSONArray;


public class Tileset {

    public void loadTileset(String tilesetPath){


    /*    JSONParser parser = new JSONParser();
        Reader reader = null;

        try {
            reader = new FileReader(tilesetPath);
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


        Map address = ((Map)jsonObj.get("address"));

        Iterator<Map.Entry> itr1 = address.entrySet().iterator();
        while (itr1.hasNext()) {
            Map.Entry pair = itr1.next();
            System.out.println(pair.getKey() + " : " + pair.getValue());
        }

        // getting phoneNumbers
        JSONArray ja = (JSONArray) jo.get("phoneNumbers");

        // iterating phoneNumbers
        Iterator itr2 = ja.iterator();
*/
        JSONParser parser = new JSONParser();

        try {
            // Leggi il file JSON
            Object obj = parser.parse(new FileReader(tilesetPath));

            // Crea un oggetto JSON
            JSONObject jsonObject = (JSONObject) obj;

            // Ottieni l'array "tiles"
            JSONArray tiles = (JSONArray) jsonObject.get("tiles");

            // Itera attraverso gli elementi dell'array "tiles"
            for (Object tile : tiles) {
                JSONObject tileObject = (JSONObject) tile;

                // Leggi i valori dell'oggetto "tile"
                String tipo = (String) tileObject.get("tipo");
                boolean solid = (boolean) tileObject.get("solid");
                JSONArray bounds = (JSONArray) tileObject.get("bounds");
                int number = ((Long) tileObject.get("number")).intValue();

                // Stampa i valori letti
                System.out.println("Tipo: " + tipo);
                System.out.println("Solid: " + solid);
                System.out.println("Bounds: " + bounds);
                System.out.println("Number: " + number);
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
