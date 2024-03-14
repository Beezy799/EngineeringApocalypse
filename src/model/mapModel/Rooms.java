package src.model.mapModel;

public enum Rooms {

    //è una lista delle possibili stanze dove diciamo la stanza attuale
    //possiamo associare qui ad ogni stanza la sua musica e altre cose relative alla stanza se servono

    //lista delle entitàModel  --> EntityModel = [EntityController, EntityView]
    //lista dei passaggi
    //lista eventi


    DORMITORIO("/res/map/dormitorio.json"),
    BIBLIOTECA("/res/map/biblioteca.json"),
    STUDIO_PROF("/res/map/studioProf.json"),
    LABORATORIO("/res/map/laboratorio.json"),
    AULA_STUDIO("/res/map/aulaStudio.json"),

    TENDA("/res/map/tenda.json");

    private Map map;

    //costruttore
    Rooms(String mapPath){
      map = new Map();
      map.loadMap(mapPath);
    }

    public static Rooms actualRoom = AULA_STUDIO;

    public Map getMap(){
        return map;
    }

}
