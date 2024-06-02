package src.view.inputs;

//serve per salvare lo stato del tasto ;
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

    public static void resetBooleans(){
        for(InputState input : InputState.values()){
            input.setPressed(false);
        }
    }

}
