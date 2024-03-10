package src.model.mapModel;

public enum Rooms {

    //è una lista delle possibili stanze dove diciamo la stanza attuale
    //possiamo associare qui ad ogni stanza la sua musica e altre cose relative alla stanza se servono

    //lista delle entitàModel  --> EntityModel = [EntityController, EntityView]
    //lista dei passaggi
    //lista eventi


    AULA_STUDIO("/res/map/e.json");

    private Map map;
    private int valorePerProvare;

    //costruttore
    Rooms(String mapPath){
      map = new Map();
      map.loadMap(mapPath);
      //System.out.println("stanza creata");
    }

    public static Rooms actualRoom = AULA_STUDIO;

    public Map getMap(){
        return map;
    }

    public int getValorePerProvare(){
        return valorePerProvare;
    }

}
