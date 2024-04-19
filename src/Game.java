import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Game {
    private ArrayList<String> tileNames, animalDeck;
    private ArrayList<Node> startTile;
    private String[] tileName4, animalToken4;
    private Player[]playerlst;
    private int cur;
    private Scoring scoring;
    private int turn = 1;
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
        
        //testing purposes
        //animalDeck.add(0,"B");
        //animalDeck.add(0,"B");
        //animalDeck.add(0,"B");
        //animalDeck.add(0,"B");

        tileName4=new String[4];
        animalToken4=new String[4];
        for (int i=0;i<4;i++){
            tileName4[i]= tileNames.remove(0);
            animalToken4[i]= animalDeck.remove(0);
        }
        startTile=new ArrayList<>();
        sc = new Scanner(new File("start.txt"));
        while (sc.hasNext()){
            String v = sc.next();
            if (v.split("-")[1].length()==1){
                Node n = new Node("", v);
                n.getNeighbors()[2].setVal(sc.next());
                n.getNeighbors()[3].setVal(sc.next());
                for (int i=0;i<5;i++){
                    n.getNeighbors()[2].addRotateAngle();
                }
                n.getNeighbors()[3].addRotateAngle();

                startTile.add(n);
            }
        }
        //System.out.println(startTile);
        Collections.shuffle(startTile);


        playerlst=new Player[4];
        for (int i=0;i<4;i++){
            playerlst[i]=new Player(startTile.get(i), i);
        }
        while(tileNames.size()!= (4*playerlst.length)+3) {
        	tileNames.remove(tileNames.size()-1);
        }
        cur=0;
        scoring=new Scoring();


    }
    
    public Player[] getPlayerList() {
    	return playerlst;
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
    
    public void returnAnimalToken(String token) {
    	animalDeck.add(token);
    }

    public void nextTurn(){
        cur++;
        if(cur>=4) {
        	cur=cur%4;
        	turn++;
        	//System.out.println("turn"+ turn);
        }
    }
    
    public int getTurn() {
    	return turn;
    }

    public Player getCurrPlayer(){
        return playerlst[cur];
    }
    
    public int getPlayerNum() {
    	return cur;
    }

    public int curPlayerScore(){
        int num = 0;
        num+=scoring.bearB(playerlst[cur].getBoard());
        int h = scoring.hawkA(playerlst[cur].getBoard(),true);
        if(h==1) num+= 2;
        if(h==2) num+= 5;
        if(h==3) num+= 8;
        if(h==4) num+= 11;
        if(h==5) num+=14;
        if(h==6) num+= 18;
        if(h==7) num+= 22;
        if(h>=8) num+= 26;
        num+=scoring.foxA(playerlst[cur].getBoard(),true);
        return num;
    }
}
