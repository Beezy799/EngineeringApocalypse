package src.view.playStateView;

import java.awt.*;

//per disegnare tutti gli elementi della mappa dall'alto verso il basso, li ordiniamo secondo la loro posizione y e secondo il
//loro tipo. Ogni elemento della mappa estenderà questa classe molto astratta che implementa una relazione di uguaglianza/disuguaglianza
public abstract class SortableElement implements Comparable<SortableElement> {

    protected int yPosMapForSort;
    protected int typeElemtToSort;    //3 = terzo strato, 4 = quarto strato, 5 = npc/player

    @Override
    public int compareTo(SortableElement e) {

        //prima guardiamo l'altezza, se sono alla stessa altezza, guardiamo il tipo
        int comparation = 0;

        if(yPosMapForSort > e.yPosMapForSort){          //se sta sotto, viene disegnato dopo
            comparation = 1;
        }

        else if(yPosMapForSort < e.yPosMapForSort){     //se sta sopra, viene disegnato prima
            comparation = -1;
        }

        else {                                           //se stanno alla stessa altezza, allora guarda il tipo

            if(typeElemtToSort > e.typeElemtToSort){      //maggiore è il tipo, più tardi viene disegnato
                comparation = 1;
            }

            else if(typeElemtToSort < e.typeElemtToSort){
                comparation = -1;
            }
        }

        return comparation;             //se sia l'altezza che il tipo sono uguali, comparation ressta 0


//        //se sono tile dello stesso tipo non ha senso ordinarli, si risparmia tempo
//        if (this.typeElemtToSort == e.typeElemtToSort && (this.typeElemtToSort == 0 || this.typeElemtToSort == 1))
//            return 0;
//
//        else {
//            if (this.yPosMapForSort < e.yPosMapForSort) //se si trova più in alto deve essere disegnato prima
//                return -1;
//
//            else if (this.yPosMapForSort > e.yPosMapForSort) //se si trova più in basso deve essere disegnato dopo
//                return 1;
//
//            else if (this.yPosMapForSort == e.yPosMapForSort) {    //se si trova nella stessa posizione, prima si disegnano
//                if (this.typeElemtToSort < e.typeElemtToSort)                  //l'oggetto con tipo più basso
//                    return -1;
//                else
//                    return 1;
//            }
//        }
//        return 0;    //non dovremmo mai arrivare qui
    }

    public abstract void draw(Graphics2D g2, int xPlayerMap, int yPlayerMap);
}