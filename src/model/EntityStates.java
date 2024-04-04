package src.model;

public enum EntityStates {
    IDLE(0),

    MOVE(1),
    ATTACKING(2),
    THROWING(4),
    PARRING(3),
    DYING(5),
    SPEAKING(0),
    HITTED(0),      //l'animazione di qundo viene colpito è la stessa di quando sta fermo
    RUNAWAY(1),
    CHASE(1),
    RECHARGE(0),
    CFU_FOUND(6);

    private int constantInAnimationArray;

    EntityStates(int i){
        constantInAnimationArray = i;
    }

    public int getConstantInAnimationArray(){
        return constantInAnimationArray;
    }

}

