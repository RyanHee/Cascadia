import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.List;

public class Game {
    private ArrayList<String> tileNames, animalDeck;
    private Queue<String> actionLog;
    private ArrayList<Node> startTile;
    private String[] tileName4, animalToken4;
    private Player[]playerlst;
    private int cur;
    private Scoring scoring;
    private String dupAnimal;
    private int turn = 1;
    private HashSet<Node> visited = new HashSet<>();
    HashMap<String, BufferedImage>pfpmp;
    private boolean animalAllowed = false;

    
    public Game(int numOfPlayers) {
        Scanner sc = new Scanner(System.in);
        try{
            sc = new Scanner(getClass().getResourceAsStream("names.txt"));
        }catch(Exception e){
            e.printStackTrace();
        }
        ArrayList<String>tn1=new ArrayList<>();
        tileNames = new ArrayList<>();
        animalDeck = new ArrayList<>();
        actionLog = new LinkedList<>();
        while (sc.hasNext()){
            tn1.add(sc.next());
        }

        int tilenum = numOfPlayers*20+3;
        for (int i=0;i<tilenum;i++){
            tileNames.add(tn1.remove(0));
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
        startTile=new ArrayList<>();

        try{

            sc = new Scanner(getClass().getResourceAsStream("start.txt"));
        }catch(Exception e){
               e.printStackTrace();
        }



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


        playerlst=new Player[numOfPlayers];
        for (int i=0;i<playerlst.length;i++){
            playerlst[i]=new Player(startTile.get(i), i);
        }

        pfpmp=new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("blue");
        list.add("pink");
        list.add("red");
        list.add("green");
        try{
            pfpmp.put("blue", ImageIO.read(getClass().getResource("img/Blue Shell.png")));
            pfpmp.put("pink", ImageIO.read(getClass().getResource("img/Princess Peach.png")));
            pfpmp.put("red", ImageIO.read(getClass().getResource("img/Shy Guy.png")));
            pfpmp.put("green", ImageIO.read(getClass().getResource("img/Yoshi.png")));
        }
        catch (Exception E){
            E.printStackTrace();
        }

        Collections.shuffle(list);
        for (int i=0;i<playerlst.length;i++){
            String s = list.remove(0);
            playerlst[i].setPfp(pfpmp.get(s));
            if (s.equals("blue")){
                playerlst[i].setColor(new Color(98, 188, 240));
            }
            if (s.equals("red")){
                playerlst[i].setColor(new Color(235, 56, 56));
            }
            if (s.equals("green")){
                playerlst[i].setColor(new Color(79, 189, 99));
            }
            if (s.equals("pink")){
                playerlst[i].setColor(Color.PINK);
            }
        }

        while(tileNames.size()> (20*playerlst.length)+3) {
        	tileNames.remove(tileNames.size()-1);
        }
        cur=0;
        scoring=new Scoring();
    }

    public Scoring getScoring() {
    	return scoring;
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
            infoBox("The 4 animals shown were the same and cleared.");
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
        if(cur>=playerlst.length) {
        	cur=cur%playerlst.length;
        	turn++;
        }
    }
    
    public int getTurn() {
    	return turn;
    }

    public Player getCurrPlayer(){
        return playerlst[cur];
    }
    
    public int getCurPlayerNum() {
    	return cur;
    }

    public int getPlayerNum(){
        return playerlst.length;
    }

    public Player[] pList() {
    	return playerlst;
    }


	public void scoreAllPlayer(){
		HashMap[]hmplst=new HashMap[playerlst.length];
		for (int i=0;i<playerlst.length;i++){
			int a = scoring.score(playerlst[i].getBoard());
            playerlst[i].setLandmp(scoring.getLandScoreMap());
            playerlst[i].setAnimalmp(scoring.getAnimalmp());
            //System.out.println("mp: "+i+" "+playerlst[i].getAnimalmp());
			playerlst[i].setLandScore(scoring.getLandScore());
            playerlst[i].setAnimalScore(scoring.getAnimalScore());
			hmplst[i]=scoring.getLandScoreMap();
		}

		String[] land=new String[]{"D", "F", "S", "L", "M"};
		if (playerlst.length==2){
			for (String s:land){
				int score0=(int)hmplst[0].get(s);
				int score1=(int)hmplst[1].get(s);
				if (score0==score1 && score0>1){
					playerlst[0].setBonus(s, 1);
					playerlst[1].setBonus(s, 1);
				}
				else if (score0>score1){
					playerlst[0].setBonus(s, 2);
					playerlst[1].setBonus(s, 0);
				}
				else if (score1>score0){
					playerlst[0].setBonus(s, 0);
					playerlst[1].setBonus(s, 2);
				}
			}
		}

		else{
			for (String s:land){
				int most=1;
				for (int i=0;i<playerlst.length;i++){
					int a = (int) hmplst[i].get(s);
					if (a>most){
						most=a;
					}
				}
				List<Integer> list = new ArrayList<>();
				if (most>1){
					for (int i=0;i<playerlst.length;i++){
						int a = (int) hmplst[i].get(s);
						if (a==most){
							list.add(i);
						}
					}

					if (list.size()>=3){
						for (int i=0;i<playerlst.length;i++){
							if (list.contains(i)){
								playerlst[i].setBonus(s, 1);
							}
						}
					}
					else if (list.size()==2){
						for (int i=0;i<playerlst.length;i++){
							if (list.contains(i)){
								playerlst[i].setBonus(s, 2);
							}
						}
					}
					else if (!list.isEmpty()){
						int second=0;
						int secondcnt=0;
						int secondplayer=-1;

						for (int i=0;i<playerlst.length;i++){
							int a = (int) hmplst[i].get(s);
							if (a==most){
								playerlst[i].setBonus(s, 3);
							}
							else if (a>second){
								second=a;
								secondplayer=i;
							}
						}
						for (int i=0;i<playerlst.length;i++){
							int a = (int) hmplst[i].get(s);
							if (a==second){
								secondcnt++;
							}
						}
						if (secondcnt==1){
							playerlst[secondplayer].setBonus(s, 1);
						}
					}
				}

			}
		}


		//return mplst;
	}
    
    public int curPlayerScore(){
        return playerlst[cur].getScore();
    }
    
    public void getAllowedSpace(Node n, String animal) {
    	if (n==null)
             return ;
        if (visited.contains(n))
             return ;
        visited.add(n);
    	if(n.animalsAllowed(animal)) {
    		animalAllowed = true;
    		return ;
    	}
    	else {
	    	for (Node c:n.getNeighbors())
	            getAllowedSpace(c, animal);
    	}
    }
    
    public boolean getAnimalAllowed(Node n, String animal) {
    	animalAllowed = false;
    	getAllowedSpace(n,animal);
    	visited.clear();
    	if(!animalAllowed) {
    		infoBox("There is no animal space for the animal so please click the next button to continue.");
    	}
    	return animalAllowed;
    }
    
    public void addAction(String action) {
    	actionLog.add(action);
    	updateActionLog();
    }
    
    public void updateActionLog() {
    	if(actionLog.size() > 8) {
    		actionLog.poll();
    	}
    }
    
    public Queue<String> getActionLog(){
    	return actionLog;
    }

    public void infoBox(String message) {
        JOptionPane.showMessageDialog(null, message, "Cascadia", JOptionPane.INFORMATION_MESSAGE);
    }
    
}
