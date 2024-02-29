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
public class InputState {

}
