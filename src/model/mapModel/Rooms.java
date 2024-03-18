package src.model.mapModel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.model.Constants;
import src.model.Hitbox;
import src.model.IModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//è una lista delle possibili stanze dove diciamo la stanza attuale
//possiamo associare qui ad ogni stanza la sua musica e altre cose relative alla stanza se servono
public enum Rooms {
    //lista delle entitàModel  --> EntityModel = [EntityController, EntityView]
    //lista eventi
    DORMITORIO("/res/map/dormitorio.json", "/res/map/datiDormitorio.json", Constants.SoundConstants.DORMITORIO_MUSIC),
    BIBLIOTECA("/res/map/biblioteca.json", "/res/map/datiBiblioteca.json", Constants.SoundConstants.BIBLIOTECA_MUSIC),
    STUDIO_PROF("/res/map/studioProf.json", "/res/map/datiStudioProf.json", Constants.SoundConstants.BOSS_FIRTST_PHASE_MUSIC),
    LABORATORIO("/res/map/laboratorio.json", "/res/map/datiLaboratorio.json", Constants.SoundConstants.LABORATORIO_MUSIC),
    AULA_STUDIO("/res/map/aulaStudio.json", "/res/map/datiAulaStudio.json", Constants.SoundConstants.AULA_STUDIO_MUSIC),
    TENDA("/res/map/tenda.json", "/res/map/datiTenda.json", Constants.SoundConstants.TENDA_MUSIC);

    private Map map;
    private ArrayList<Passage> passages;
    private static IModel model;
    private int musicIndex;

    //costruttore
    Rooms(String mapPath, String pathRoomData, int m){
      map = new Map();
      map.loadMap(mapPath);
      musicIndex = m;
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
            int nuovaxPlayer = Integer.parseInt(passage.get("nuovaxPlayer").toString());
            int nuovayPlayer = Integer.parseInt(passage.get("nuovayPlayer").toString());
            int cfuRichiesti = Integer.parseInt(passage.get("cfuRichiesti").toString());

            String m = passage.get("messaggio").toString();
            Hitbox borders = new Hitbox(xPosPassaggio, yPosPassaggio, larghezza, altezza);
            passages.add(new Passage(borders, nextRoom, nuovaxPlayer, nuovayPlayer, cfuRichiesti, m));
        }
    }

    public static Rooms actualRoom = BIBLIOTECA;

    public Map getMap(){
        return map;
    }
    public ArrayList<Passage> getPassages(){
        return passages;
    }

    //le stanze si salvano il riferimento al model
    public static void setModel(IModel m){
        model = m;
    }

    //le stanze passano il riferimento del model perchè si creano prima di lui.
    //il model, quando ha finito di crearsi, passa il suo riferimento a questa classe
    //le varie stanze contengono cose che hanno bisogno del model e con questo stratagemma
    //possiamo passare loro il model
    public static IModel getModel(){
        return model;
    }

    public int getMusicIndex(){
        return musicIndex;
    }

}
