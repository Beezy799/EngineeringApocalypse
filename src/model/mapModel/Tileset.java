package src.model.mapModel;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import src.model.Hitbox;
import src.view.main.GamePanel;

public class Tileset {

    TileComplete[] tilesComplete;


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
            tilesComplete = new TileComplete[tilesJson.size()];

            // Itera attraverso gli elementi dell'array "tiles"
            for (int index = 0; index < tilesJson.size(); index++) {
                JSONObject tileObject = (JSONObject) tilesJson.get(index);

                // Leggi i valori dell'oggetto "tile"
                String tipo = (String) tileObject.get("tipo");
                boolean solid = (boolean) tileObject.get("solid");

                //prendiamo la hitbox del tile
                JSONArray bounds = (JSONArray) tileObject.get("bounds");
                Hitbox hitbox = new Hitbox();
                hitbox.setX((int)(Integer.parseInt(bounds.get(0).toString()) * GamePanel.SCALE));
                hitbox.setY((int)(Integer.parseInt(bounds.get(1).toString()) * GamePanel.SCALE));
                hitbox.setWidth((int)(Integer.parseInt(bounds.get(2).toString()) * GamePanel.SCALE));
                hitbox.setHeight((int)(Integer.parseInt(bounds.get(3).toString()) * GamePanel.SCALE));

                //ora che abbiamo tutti i campi, creo il tileModel e lo metto nel corrispondete tile completo nell'array
                tilesComplete[index] =  new TileComplete(new TileModel(tipo, solid, hitbox));
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }


    public TileComplete[] getTileArray(){
        return tilesComplete;
    }

    public TileComplete getTile(int index){
        return tilesComplete[index];
    }

}
