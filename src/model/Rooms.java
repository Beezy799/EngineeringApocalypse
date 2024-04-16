package src.model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import src.controller.entitycontroller.enemy.GhostController;
import src.controller.entitycontroller.enemy.NullafacenteController;
import src.controller.entitycontroller.enemy.RobotController;
import src.controller.entitycontroller.npc.*;
import src.controller.Hitbox;
import src.model.entity.EnemyComplete;
import src.model.entity.NpcComplete;
import src.model.events.*;
import src.model.mapModel.Map;
import src.model.mapModel.Passage;
import src.view.IView;
import src.view.entityView.enemy.GhostView;
import src.view.entityView.enemy.NullafacenteView;
import src.view.entityView.enemy.RobotView;
import src.view.entityView.npc.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

//è una lista delle possibili stanze dove diciamo la stanza attuale
public enum Rooms {
    BIBLIOTECA("/res/map/biblioteca.json", "/res/map/datiBiblioteca.json", Constants.SoundConstants.BIBLIOTECA_MUSIC),
    DORMITORIO("/res/map/dormitorio.json", "/res/map/datiDormitorio.json", Constants.SoundConstants.DORMITORIO_BUIO),
    STUDIO_PROF("/res/map/studioProf.json", "/res/map/datiStudioProf.json", Constants.SoundConstants.BOSS_FIRTST_PHASE_MUSIC),
    LABORATORIO("/res/map/laboratorio.json", "/res/map/datiLaboratorio.json", Constants.SoundConstants.LABORATORIO_MUSIC),
    AULA_STUDIO("/res/map/aulaStudio.json", "/res/map/datiAulaStudio.json", Constants.SoundConstants.AULA_STUDIO_MUSIC),
    TENDA("/res/map/tenda.json", "/res/map/datiTenda.json", Constants.SoundConstants.TENDA_MUSIC);

    private Map map;
    private ArrayList<Passage> passages;
    private ArrayList<NpcComplete> npcList;
    private ArrayList<EnemyComplete> enemyList;
    private ArrayList<BulletComplete> buletList;
    private ArrayList<Event> events;
    private static IModel model;
    private int musicIndex;
    private String dataPath;

    //costruttore della singola stanza
    Rooms(String mapPath, String pathRoomData, int m){
        map = new Map();
        map.loadMap(mapPath);
        musicIndex = m;
        passages = new ArrayList<>();
        npcList = new ArrayList<>();
        enemyList = new ArrayList<>();
        events = new ArrayList<>();
        buletList = new ArrayList<>();
        loadPassages(pathRoomData);
        dataPath = pathRoomData;
    }

    private void loadPassages(String pathRoomData) {
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

    public void loadNPCs(IView view){
        BufferedReader reader = null;

        try {
            InputStream is = getClass().getResourceAsStream(dataPath);
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

        JSONArray entitiesInRoom = (JSONArray) jsonObject.get("npc");
        for(int i = 0; i < entitiesInRoom.size(); i++) {

            JSONObject entityData = (JSONObject) entitiesInRoom.get(i);

            String nome = entityData.get("nome").toString();
            int riga = Integer.parseInt(entityData.get("riga").toString());
            int colonna = Integer.parseInt(entityData.get("colonna").toString());

            switch (nome){
                case "Ermenegildo":
                    ErmenegildoView ev = new ErmenegildoView(view, i);
                    ErmenegildoController ec = new ErmenegildoController(colonna, riga, model.getController(), i);
                    NpcComplete erm = new NpcComplete(ec, ev);
                    npcList.add(erm);
                    break;
                case "pupa":
                    PupaView pv = new PupaView(view, i);
                    PupaController pc = new PupaController(colonna, riga, model.getController(), i);
                    NpcComplete pupa = new NpcComplete(pc, pv);
                    npcList.add(pupa);
                    break;
                case "prof":
                    ProfView prv = new ProfView(view, i);
                    ProfController prc = new ProfController(colonna, riga, model.getController(), i);
                    NpcComplete prof = new NpcComplete(prc, prv);
                    npcList.add(prof);
                    break;
                case "gatto":
                    CatView cv = new CatView(view, i);
                    CatController cc = new CatController(colonna, riga, model.getController(), i);
                    NpcComplete cat = new NpcComplete(cc, cv);
                    npcList.add(cat);
                    break;
                case "nerd":
                    NerdView nv = new NerdView(view, i);
                    NerdController nc = new NerdController(colonna, riga, model.getController(), i);
                    NpcComplete nerd = new NpcComplete(nc, nv);
                    npcList.add(nerd);
                    break;
            }

        }

    }

    public void loadEnemies(IView view) {
        BufferedReader reader = null;

        try {
            InputStream is = getClass().getResourceAsStream(dataPath);
            reader = new BufferedReader(new InputStreamReader(is));
        } catch (Exception e) {
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

        JSONArray entitiesInRoom = (JSONArray) jsonObject.get("enemy");
        for (int i = 0; i < entitiesInRoom.size(); i++) {

            JSONObject entityData = (JSONObject) entitiesInRoom.get(i);

            String nome = entityData.get("nome").toString();
            int riga = Integer.parseInt(entityData.get("riga").toString());
            int colonna = Integer.parseInt(entityData.get("colonna").toString());

            switch (nome) {
                case "nullafacente":
                    NullafacenteView nv = new NullafacenteView(view, i);
                    NullafacenteController nc = new NullafacenteController(colonna, riga, model.getController(), i);
                    EnemyComplete nullaf = new EnemyComplete(nc, nv);
                    enemyList.add(nullaf);
                    break;
                case "fantasma":
                    GhostView gv = new GhostView(view, i);
                    GhostController gc = new GhostController(colonna, riga, model.getController(), i);
                    EnemyComplete ghost = new EnemyComplete(gc, gv);
                    enemyList.add(ghost);
                    break;
                case"robot":
                    RobotView rv = new RobotView(view, i);
                    RobotController rc = new RobotController(colonna, riga, model.getController(), i);
                    EnemyComplete robot = new EnemyComplete(rc, rv);
                    enemyList.add(robot);
                    break;
            }
        }
    }

    public void loadEvents(IModel m){
        BufferedReader reader = null;

        try {
            InputStream is = getClass().getResourceAsStream(dataPath);
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

        JSONArray eventsInRoom = (JSONArray) jsonObject.get("eventi");
        for(int i = 0; i < eventsInRoom.size(); i++) {

            JSONObject entityData = (JSONObject) eventsInRoom.get(i);

            String nome = entityData.get("nome").toString();
            int riga = Integer.parseInt(entityData.get("riga").toString());
            int colonna = Integer.parseInt(entityData.get("colonna").toString());
            int width = Integer.parseInt(entityData.get("larghezza").toString());
            int height = Integer.parseInt(entityData.get("altezza").toString());

            Hitbox bounds = new Hitbox(colonna, riga, width, height);

            switch (nome){
                case "Caffe":
                    events.add(new Caffe(bounds, m, i));
                    break;
                case "Piano":
                    events.add(new Piano(bounds, m, i));
                    break;
                case "cfu":
                    events.add(new CFU(bounds, m, i));
                    break;
                case "luce":
                    events.add(new Light(bounds, m, i));
                    break;
                case "appunti":
                    events.add(new Notes(bounds, m, i));
                    break;
            }

        }

    }

    public Map getMap(){
        return map;
    }
    public ArrayList<Passage> getPassages(){
        return passages;
    }
    public int getMusicIndex(){
        return musicIndex;
    }
    public ArrayList<NpcComplete> getNpc(){
        return npcList;
    }
    public ArrayList<EnemyComplete> getEnemy(){
        return enemyList;
    }

    public ArrayList<Event> getEvents(){
        return events;
    }

    public void setNewMusic(int i){
        musicIndex = i;
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
    public static Rooms actualRoom = BIBLIOTECA;

    public void resetEventsAndEenemyes() {
        for(Event event : events){
            event.setEndInteraction(false);
        }
        for(EnemyComplete enemyComplete: enemyList){
            enemyComplete.reset();
        }
        for(NpcComplete npc : npcList){
            npc.reset();
        }
        buletList.clear();
    }

    public void deleteBullet(int index){
        for (int i = index; i < buletList.size(); i++){
            buletList.get(i).abbassaIndice();
        }
        buletList.remove(index);
    }

    public ArrayList<BulletComplete> getBuletList(){
        return buletList;
    }
}