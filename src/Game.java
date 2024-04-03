import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private ArrayList<String> tileNames, animalDeck;
    private String[] tileName4, animalToken4;
    private Player[]playerlst;
    private int cur;
    public Game() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("names.txt"));
        tileNames = new ArrayList<>();
        animalDeck = new ArrayList<>();
        while (sc.hasNext()){
            tileNames.add(sc.next());
        }
        for (int i=0;i<20;i++){
            animalDeck.add("B");
            animalDeck.add("E");
            animalDeck.add("H");
            animalDeck.add("S");
            animalDeck.add("F");
        }
        Collections.shuffle(animalDeck);
        Collections.shuffle(tileNames);

        tileName4=new String[4];
        animalToken4=new String[4];
        for (int i=0;i<4;i++){
            tileName4[i]= tileNames.remove(0);
            animalToken4[i]= animalDeck.remove(0);
        }
        playerlst=new Player[4];
        for (int i=0;i<4;i++){
            playerlst[i]=new Player(new Node("", "MF-FBE"), i);
        }
        cur=0;
    }

    public String[]getTileName4(){
        return tileName4;
    }

    public String[] getAnimalToken4() {
        return animalToken4;
    }

    public void updateAnimalDeck(int numSelectedAnimal){
        animalToken4[numSelectedAnimal]=animalDeck.remove(0);
    }

    public void updateTileDeck(int numSelectedTile){
        tileName4[numSelectedTile] = tileNames.get(0);
        tileNames.remove(0);
    }

    public void nextTurn(){
        cur++;
        cur=cur%4;

    }

    public Player getCurrPlayer(){
        return playerlst[cur];
    }
}
