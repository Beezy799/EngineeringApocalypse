package src.model.mapModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import src.model.Hitbox;


public class TilesetModel {

    TileModel[] tiles;
    TileCmplete[] tilesComplete;



    public void loadTileset(String tilesetPath){

        JSONParser parser = new JSONParser();
        BufferedReader reader = null;

        try {
            InputStream is = getClass().getResourceAsStream(tilesetPath);
            reader = new BufferedReader(new InputStreamReader(is));
            // Leggi il file JSON
            Object obj = parser.parse(reader);

            // Crea un oggetto JSON
            JSONObject jsonObject = (JSONObject) obj;

            // Ottieni l'array "tiles"
            JSONArray tilesJson = (JSONArray) jsonObject.get("tiles");

            //ora che abbiamo il numero dei tile, possiamo creare l'array
            tiles = new TileModel[tilesJson.size()];
            tilesComplete = new TileCmplete[tilesJson.size()];

            System.out.println(tilesJson.size());



            // Itera attraverso gli elementi dell'array "tiles"
            for (int index = 0; index < tilesJson.size(); index++) {
                JSONObject tileObject = (JSONObject) tilesJson.get(index);

                // Leggi i valori dell'oggetto "tile"
                String tipo = (String) tileObject.get("tipo");
                boolean solid = (boolean) tileObject.get("solid");

                //prendiamo la hitbox del tile
                JSONArray bounds = (JSONArray) tileObject.get("bounds");
                Hitbox hitbox = new Hitbox();
                hitbox.setX(Integer.parseInt(bounds.get(0).toString()));
                hitbox.setY(Integer.parseInt(bounds.get(1).toString()));
                hitbox.setWidth(Integer.parseInt(bounds.get(2).toString()));
                hitbox.setHeight(Integer.parseInt(bounds.get(3).toString()));

                //ora che abbiamo tutti i campi, creo il tile e lo metto nell'array
                tiles[index] = new TileModel(tipo, solid, hitbox);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }
}
