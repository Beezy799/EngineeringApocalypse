package src.model;

/*
Mentre tutti gli altri gamestateInput possono comunicare col rispettivo gamestate manager,
il playStateInput no, perchè deve uscire dal view e serve una gestione più precisa.
Possiamo creare nel model una rappresentazione della tastiera e del mouse durante il
playstate: mettiamo un booleano per ogni tasto, se è premuto diventa true, se è rilasciato
diventa false. (se premiamo 'w' il playStateInput dice al model di mettere w = true..)
Il controller, durante ogni update, si fa dare dal model lo stato della tastiera/mouse
in quel momento (deve essere synchronized, come i metodi del playSateInput, lo stato della
tastiera/mouse non può cambiare mentre il playstate lo sta prendendo) ed agisce di
conseguenza. (vede che 'w' è stato premuto, quindi sposta il player)
 */
public enum InputState {

    //keyboard
    W(false),
    S(false),
    A(false),
    D(false),
    SPACE(false),
    ENTER(false),
    P(false),
    ESCAPE(false),
    E(false),

    //mouse
    RIGHT_CLICK(false),
    LEFT_CLICK(false),
    MIDDLE_CLICK(false);

    private boolean isPressed;

    InputState(boolean b) {
        isPressed = b;
    }

    public void setPressed(boolean b){
        isPressed = b;
    }

    public boolean getPressed(){
        return isPressed;
    }

}
