package src.model.mapModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.model.Hitbox;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public enum Rooms {

    //è una lista delle possibili stanze dove diciamo la stanza attuale
    //possiamo associare qui ad ogni stanza la sua musica e altre cose relative alla stanza se servono

    //lista delle entitàModel  --> EntityModel = [EntityController, EntityView]
    //lista dei passaggi
    //lista eventi


    //DORMITORIO("/res/map/dormitorio.json"),
    //BIBLIOTECA("/res/map/biblioteca.json"),
    //STUDIO_PROF("/res/map/studioProf.json"),
    //LABORATORIO("/res/map/laboratorio.json"),
    //AULA_STUDIO("/res/map/aulaStudio.json"),

    TENDA("/res/map/tenda.json", "/res/map/datiTenda.json");

    private Map map;
    private ArrayList<Passage> passages;
    //array di entità

    //costruttore
    Rooms(String mapPath, String pathRoomData){
      map = new Map();
      map.loadMap(mapPath);
      passages = new ArrayList<>();
      loadPassagesAndEntities(pathRoomData);
    }

    private void loadPassagesAndEntities(String pathRoomData) {
        //roba per leggere il file
        BufferedReader reader = null;

        try {
            InputStream is = getClass().getResourceAsStream(pathRoomData);
            reader = new BufferedReader(new InputStreamReader(is));
        }
        catch (Exception e){
            e.printStackTrace();
        }

        //per leggere il file di tipo json
        JSONParser parser = new JSONParser();
        Object jsonObj = null;
        try {
            jsonObj = parser.parse(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JSONObject jsonObject = (JSONObject) jsonObj;

        //passaggi
        JSONArray passagesInRoom = (JSONArray) jsonObject.get("passaggi");
        for(int i = 0; i < passagesInRoom.size(); i++){

            JSONObject passage = (JSONObject) passagesInRoom.get(i);
            String nextRoom = passage.get("nuova stanza").toString();
            int xPosPassaggio = Integer.parseInt(passage.get("xPosPassaggio").toString());
            int yPosPassaggio = Integer.parseInt(passage.get("yPosPassaggio").toString());
            int larghezza = Integer.parseInt(passage.get("larghezza").toString());
            int altezza = Integer.parseInt(passage.get("altezza").toString());
            int nuovaxPlayer = Integer.parseInt(passage.get("nuvaxPlayer").toString());
            int nuovayPlayer = Integer.parseInt(passage.get("nuovayPlater").toString());
            int cfuRichiesti = Integer.parseInt(passage.get("cfuRichiesti").toString());

            Hitbox borders = new Hitbox(xPosPassaggio, yPosPassaggio, larghezza, altezza);
            passages.add(new Passage(borders, nextRoom, nuovaxPlayer, nuovayPlayer, cfuRichiesti));
        }
    }

    public static Rooms actualRoom = TENDA;

    public Map getMap(){
        return map;
    }

}
