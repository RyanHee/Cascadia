import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    private boolean animalAllowed = false;
    private HashMap<Integer, HashMap<String, Integer>> bonusPlayerScores;
    private HashMap<String, String> bonuses;
    private HashMap<String, Integer> bonus = new HashMap<>();
    
    public Game(int numOfPlayers) {
        Scanner sc = new Scanner(System.in);
        try{
         sc = new Scanner(new File("names.txt"));
        }catch(Exception e){
            e.printStackTrace();
        }
        tileNames = new ArrayList<>();
        animalDeck = new ArrayList<>();
        actionLog = new LinkedList<>();
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

        try{
            sc = new Scanner(new File("start.txt"));
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
        while(tileNames.size()> (20*playerlst.length)+3) {
        	tileNames.remove(tileNames.size()-1);
        }
        cur=0;
        scoring=new Scoring();
        bonuses = new HashMap<>();
        bonusPlayerScores = new HashMap<>();
        getBonuses();
        //System.out.println(bonusPlayerScores);
    }
    
    public void updateBonus() {
    	for(int i=0; i<playerlst.length; i++) {
        	getScoring().score(playerlst[i].getBoard());
        	bonusPlayerScores.put(i, getScoring().getLandScoreList());
        }
    }
    
    /*public HashMap<Integer, HashMap<String, Integer>> getList(){
    	updateBonus();
    	return bonusPlayerScores;
    }*/
    
    public HashMap<String, Integer> getBonuses(){
    	String[] hold = {"D", "F", "L", "M", "S"};
    	boolean extra = false;
    	
    	for(int i =0; i<playerlst.length; i++) {
    		bonus.put(Integer.toString(i),0);
    	}
    	updateBonus();
    	for(int i=0; i<5; i++) {
    		bonuses.put(hold[i], "9-0");//empty hold
    	}
    	calculateBonus();
    	for(int i=0; i<bonuses.size(); i++) {
    		//1st place bonus
    		String pBonus = bonuses.get(hold[i]);
    		int num = 0;
    		String holdP = pBonus.substring(0,pBonus.indexOf("-"));
    		String[] play = holdP.split("&");
    		if(!pBonus.contains(",")) {
    			num = Integer.parseInt(pBonus.substring(pBonus.indexOf("-")+1));
    		}
    		else {
    			num = Integer.parseInt(pBonus.substring(pBonus.indexOf("-")+1, pBonus.indexOf(",")));
    			extra = true;
    		}
    		for(int m = 0; m<play.length; m++) {
    			if(bonus.get(play[m])==null) {
    				bonus.put(play[m], num);
    			}
    			else {
    				bonus.put(play[m], bonus.get(play[m])+num);
    			}
    		}
    		//2nd place bonus
    		if(extra) {
    			String e = pBonus.substring(pBonus.indexOf(",")+1, pBonus.indexOf(",")+2);
    			if(bonus.get(e)==null) {
    				bonus.put(e, 1);
    			}
    			else {
    				bonus.put(e, bonus.get(e)+1);
    			}
    		}
    	}
    	//System.out.println(bonus);
    	/*for(int i=0; i<playerlst.length; i++) {
    		playerlst[i].setScore(playerlst[i].getScore()+bonus.get(Integer.toString(i)));
    	}*/
    	//find how much each player gets in points & move from bonuses to bonus
    	return bonus;
    }
    
    private void calculateBonus() {
    	String[] hold = {"D", "F", "L", "M", "S"};
    	int max = 1;
    	int twoLarge = 1;
    	boolean tie = false;
    	boolean tieMult = false;
    	boolean tie2nd = false;
    	//2 players
    	if(bonusPlayerScores.size() == 2) {
    		for(int q = 0; q<5; q++) {
	    		for(int i =0; i<bonusPlayerScores.size(); i++) {
	    			HashMap<String, Integer> playerScore = bonusPlayerScores.get(i);
	    			if(playerScore.get(hold[q]) > max) {
		    			max = playerScore.get(hold[q]);
		    			bonuses.put(hold[q], i +"-2");
		    		}
	    			else if(playerScore.get(hold[q]) == max && playerScore.get(hold[q])>1) {
	    				bonuses.remove(hold[q]);
	    				bonuses.put(hold[q], "0&1-1");
	    			}
	    		}
	    		max = 1;
    		}
    	}
    	//3 or more players
    	else if(bonusPlayerScores.size() >= 3) {
	    	for(int q = 0; q<5; q++) {
	    		for(int i =0; i<bonusPlayerScores.size(); i++) {
	    			HashMap<String, Integer> playerScore = bonusPlayerScores.get(i);
	    			//largest = 3 pts 
		    		if(playerScore.get(hold[q]) > max) {
		    			max = playerScore.get(hold[q]);
		    			bonuses.put(hold[q], i +"-3");
		    		}
		    		//tie 2 largest = 2 pts
		    		else if(playerScore.get(hold[q]) == max && playerScore.get(hold[q])>1 && !tie) {
		    			tie = true;
		    			String s = bonuses.get(hold[q]);
		    			int prevPlayer = Integer.parseInt(s.substring(0,1));
		    			bonuses.remove(hold[q]);
		    			bonuses.put(hold[q], prevPlayer+"&"+i+"-2");
		    		}
		    		//tie 3 largest = 1 pt
		    		else if(tie && playerScore.get(hold[q]) == max && playerScore.get(hold[q])>1) {
		    			if(!tieMult) {
			    			tieMult = true;
			    			String s = bonuses.get(hold[q]);
			    			String prevPlayers = (s.substring(0,3));
			    			bonuses.remove(hold[q]);
			    			bonuses.put(hold[q], prevPlayers+"&"+i+"-1");
		    			}
		    			// tie 4 largest = 1 pt
		    			else {
		    				String s = bonuses.get(hold[q]);
		    				s = s.substring(0,5) + "&" + i +"-1";
		    				bonuses.put(hold[q], s);
		    			}
		    		}
		    		//2nd place bonus
		    		else {
		    			if(!tie) {
			    			if(playerScore.get(hold[q]) > twoLarge) {
			    				twoLarge = playerScore.get(hold[q]);
			    				String holdBonus = bonuses.get(hold[q]);
			    				bonuses.remove(hold[q]);
			    				bonuses.put(hold[q], holdBonus+","+i+"-1");
			    			}
			    			else if(playerScore.get(hold[q]) == twoLarge && playerScore.get(hold[q])>1 &&!tie2nd) {
			    				tie2nd = true;//already checked for 2 players tied for 2nd
			    				String recount = bonuses.get(hold[q]);
			    				bonuses.remove(hold[q]);
			    				bonuses.put(hold[q], recount.substring(0,recount.indexOf(",")));
			    			}
		    			}
		    		}
	    		}
	    		max = 1;
	    		twoLarge = 1;
	    		tie = false;
	    		tieMult = false;
	    		tie2nd = false;
	    	}
    	}
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
            //Panel.useInfoBox("The 4 animals shown were the same and were cleared.");
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
    public Player[] pList() {
    	return playerlst;
    }

    public void updateScore() {
    	int hold = cur;
    	for(int i=0; i<playerlst.length;i++) {
    		cur = i;
    		playerlst[i].setScore(curPlayerScore());
    	}
    	cur = hold;
    }
    
    public int curPlayerScore(){
        int num = 0;
        num+=scoring.score(playerlst[cur].getBoard());
        String h = Integer.toString(cur);
        playerlst[cur].setBonus(bonus.get(h));
        playerlst[cur].setScore(num+playerlst[cur].getNumTokens());
        //System.out.println("cur player"+h+"money" +bonus.get(h) +":"+playerlst[cur].getScore());
        return playerlst[cur].getScore()+playerlst[cur].getBonus();
    }
    
    public void getAllowedSpace(Node n, String animal) {
    	if (n==null)
             return ;
        if (visited.contains(n))
             return ;
        //System.out.println("its new and exists");
        visited.add(n);
    	if(n.animalsAllowed(animal)) {
    		animalAllowed = true;
    		//System.out.println("its allowed");
    		return ;
    	}
    	else {
    		//System.out.println("neighbor check");
	    	for (Node c:n.getNeighbors())
	            getAllowedSpace(c, animal);
    	}
    }
    
    public boolean getAnimalAllowed(Node n, String animal) {
    	animalAllowed = false;
    	getAllowedSpace(n,animal);
    	//System.out.println("animal"+animalAllowed);
    	visited.clear();
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

}
