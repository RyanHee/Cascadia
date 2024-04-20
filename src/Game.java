import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Game {
    private ArrayList<String> tileNames, animalDeck;
    private ArrayList<Node> startTile;
    private String[] tileName4, animalToken4;
    private Player[]playerlst;
    private int cur;
    private Scoring scoring;
    private String dupAnimal;
    private int turn = 1;
    public Game() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("names.txt"));
        tileNames = new ArrayList<>();
        animalDeck = new ArrayList<>();
        while (sc.hasNext()){
            tileNames.add(sc.next());
        }
        for (int i=0;i<20;i++){
            /*
            animalDeck.add("S");
            animalDeck.add("S");
            animalDeck.add("S");
            animalDeck.add("S");
            animalDeck.add("S");
             */

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
        if (cntDup()==4){
            for (int i=0;i<4;i++){
                returnAnimalToken(animalToken4[i]);
                updateAnimal4(i);
            }
        }
        return animalToken4;
    }

    public int cntDup(){
        HashMap<String, Integer>mp=new HashMap<>();
        dupAnimal="";
        for (String animal:animalToken4){
            mp.putIfAbsent(animal, 0);
            mp.put(animal, mp.get(animal)+1);
        }
        Set<String> st = mp.keySet();
        int max=0;
        for (String s:st){
            if (mp.get(s)>max){
                max=mp.get(s);
                dupAnimal=s;
            }
        }
        return max;
    }

    public String getDupAnimal(){
        return dupAnimal;
    }

    public void removeDups(){
        while (cntDup()>2){
            for (int i=0;i<4;i++){
                if (animalToken4[i].equals(dupAnimal)){
                    returnAnimalToken(dupAnimal);
                    updateAnimal4(i);
                }
            }
        }


    }
    public void updateAnimal4(int numSelectedAnimal){
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
        num+=scoring.score(playerlst[cur].getBoard());
        playerlst[cur].setScore(num);
        return num;
    }
}
