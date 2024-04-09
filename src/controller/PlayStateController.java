package src.controller;

//classe che contiene le cose che ci servono durante lo stato play, tipo le collisioni
public class PlayStateController {

    private CollisionChecker collisionChecker;
    private  IController controller;

    public PlayStateController(IController c){
        controller = c;
        collisionChecker = new CollisionChecker(c);
    }

    public CollisionChecker getCollisionChecker(){
        return  collisionChecker;
    }
}