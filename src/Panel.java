import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Panel extends JPanel implements ActionListener {
    //private int[]xlst;
    //private int[]ylst;

    private HashMap<String, BufferedImage[]>animalTokenMap;

    private int angle, numSelectedTile, numSelectedAnimal;
    private static BufferedImage selectOutline, outline, rotateImage;
    private static BufferedImage natureToken;
    private Node nodeSelected;
    private HexButton rotate;
    private BufferedImage[] tiles4;
    private HexButton[] fourButtonTiles;
    private InvisButton[]fourButtonAnimal;
    private boolean tileChose = false, dupAnimalsUsed = false, natureTokenUsed = false, mixMatchUsed = false, clearAnimalsUsed = false, noAnimalPlace = false, actionLogUsed = false;
    private int state;
    private int prog;
    private boolean drawHighlightAnimal;
    private JButton confirmB, cancelB, nextB;
    private JButton help, scoreCards, actionLog, useNature, removeDups;
    private JButton confirmClear, clearAnimals, mixMatch;
    private HashSet<Integer> animalsToClear = new HashSet<>();
    private String curVal, curAnimal;
    private BoardPanel bp,sp1,sp2,sp3;
    private BufferedImage dpad;
    private Game game;
    private static int aggrrrrhhhhhhh;
    private boolean first = true;
    //private HexButton hexButton;
    public Panel()  {
        nodeSelected=null;
        numSelectedAnimal=-1;
        game=new Game(4);//change to right number of players
        try{
            //img = ImageIO.read(Panel.class.getResource("tile.png"));
            //img1 = ImageIO.read(Panel.class.getResource("tile1.png"));
            dpad=ImageIO.read(new File("img/DPAD.jpg"));
            outline=ImageIO.read(new File("img/tileOutline.png"));
            selectOutline=ImageIO.read(new File("img/selectedTile.png"));
            natureToken=ImageIO.read(new File("img/tokens/nature-token.png"));


            tiles4=new BufferedImage[4];
            fourButtonTiles =new HexButton[4];
            fourButtonAnimal= new InvisButton[4];
            animalTokenMap=new HashMap<>();

            for (int i=0;i<4;i++){
                tiles4[i]=ImageIO.read(new File("img/Tile/"+game.getTileName4()[i]+".png"));
                fourButtonTiles[i]=new HexButton("");
                fourButtonTiles[i].addActionListener(this);
                fourButtonAnimal[i]=new InvisButton("");
                fourButtonAnimal[i].addActionListener(this);
                //fourButtonAnimal[i].showButton();
            }

            //System.out.println("here");
            String[]A=new String[]{"B", "E", "F", "H", "S"};
            String[] ALong=new String[]{"bear", "elk", "fox", "hawk", "salmon"};
            for (int i=0;i<5;i++){
                animalTokenMap.put(A[i], new BufferedImage[]{ImageIO.read(new File("img/tokens/"+ALong[i]+".png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Active.png")),
                        ImageIO.read(new File("img/tokens/"+ALong[i]+"Inactive.png"))});
            }
            rotateImage=ImageIO.read(new File("img/tilePlacementRotateClockwise.png"));
            //System.out.println(Arrays.toString(tiles4));
        }
        catch (Exception e){
            System.out.println(1231);
        }
        angle=0;
        numSelectedTile =-1;
        confirmB=new JButton("confirm");
        cancelB=new JButton("cancel");
        nextB=new JButton("next turn");
        help=new JButton("Help");
        scoreCards=new JButton("Scoring Cards");
        actionLog=new JButton("Action Log");
        useNature=new JButton("Use Nature Token");
        removeDups=new JButton("Remove Duplicate Tokens");
        confirmClear = new JButton("Confirm Clearing Animals");
        clearAnimals = new JButton("Clear X Animals");
        mixMatch = new JButton("Mix & Match tile & token");
        rotate = new HexButton("arrow.png");

        confirmB.addActionListener(this);
        cancelB.addActionListener(this);
        nextB.addActionListener(this);
        help.addActionListener(this);
        scoreCards.addActionListener(this);
        actionLog.addActionListener(this);
        useNature.addActionListener(this);
        removeDups.addActionListener(this);
        confirmClear.addActionListener(this);
        clearAnimals.addActionListener(this);
        mixMatch.addActionListener(this);
        add(cancelB);
        add(nextB);
        add(confirmB);
        add(help);
        add(scoreCards);
        add(actionLog);
        add(useNature);
        add(removeDups);
        add(confirmClear);
        add(clearAnimals);
        add(mixMatch);
        add(rotate);

        sp1 = new BoardPanel(game.getCurrPlayer().getBoard(),animalTokenMap,this);
        sp2 = new BoardPanel(game.getCurrPlayer().getBoard(),animalTokenMap,this);
        sp3 = new BoardPanel(game.getCurrPlayer().getBoard(),animalTokenMap,this);
        add(sp1); 
        add(sp2);
        add(sp3);

        curVal="";
        state=0;
        prog = 102;
        rotate.addActionListener(this);
        add(rotate);
        
        bp=new BoardPanel(game.getCurrPlayer().getBoard(), animalTokenMap, this);
        add(bp);
        //setBackground(Color.WHITE);
        setBackground(new Color(3, 107, 156));
        //setBackground(new Color(82, 120, 134)); //grayish blue
        aggrrrrhhhhhhh= 0;
    }

    public void paint(Graphics g){
        super.paint(g);
        System.out.printf("P%s\n",aggrrrrhhhhhhh++);
        
        //System.out.println(game.getList());
        if(first) {
        	first = !first;
        	game.updateScore();
        	
        }
        	
        g.setFont(new Font("Arial", Font.PLAIN, 15));
        g.drawImage(selectOutline, getWidth()*58/128, getHeight()*22/25, 45, 50, null);
        
        //System.out.println("bruh time"+game.getPlayerList()[0].getScore());
        
        HashMap<String, Integer> map = game.getBonuses();
        String h = Integer.toString(game.getPlayerNum());
        game.getScoring().score(game.getCurrPlayer().getBoard());
        g.drawString(game.getScoring().getLandScore().toString() +" / "+map.get(h), getWidth()*126/256, getHeight()*23/25);
        String[] animal = new String[]{"B", "E", "F", "H", "S"};
        for(int i = 0; i<animal.length; i++) {
        	g.drawImage(animalTokenMap.get(animal[i])[0], getWidth()*(134+15*i)/256, getHeight()*22/25, 50, 50, null);
        	g.drawString(game.getScoring().getAnimalScore(animal[i]).toString(), getWidth()*(145+15*i)/256, getHeight()*23/25);
        }

        g.setColor(new Color(0,0,0));
        g.setFont(new Font("Arial", Font.PLAIN, 30));
        g.drawString("Turn "+game.getTurn(), getWidth()/40-20, 40);
        g.setFont(new Font("Arial", Font.PLAIN, 25));
        g.drawString("Player "+(game.getPlayerNum()+1), getWidth()/15+40, 40);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawImage(natureToken, getWidth()/5, 10, 50, 50, null);
        g.drawString(": "+game.getCurrPlayer().getNumTokens(), getWidth()/5+60, 40);
        g.drawString("Current Score: "+String.valueOf(game.curPlayerScore()), getWidth()/15+20, 70);
       
        //System.out.println("bruh "+game.getPlayerList()[0].getScore());
        g.setColor(new Color(222,184,135));
        g.setColor(new Color(159, 223, 223));
        g.setColor(new Color(165, 213, 232));

        g.fillRect(getWidth()*6/8+getWidth()/16,0,getWidth()*2/10,getHeight());
        g.setColor(Color.BLACK);
        
        int yPlay = 0;
        for(int pNum = 1; pNum<5; pNum++) {
        	if(pNum != game.getPlayerNum()+1){

                if(yPlay==0){
                    sp1.setScale(.2);
                    sp1.sp = true;
                    sp1.setBoard(game.pList()[pNum-1].getBoard());
                    sp1.setShift(0,(int)(120*.7));
                    sp1.setBounds(getWidth()*6/8+getWidth()/16,getHeight()*yPlay/4+60,getWidth()*2/10,getHeight()*3/18);
                    
                }else if(yPlay==1){
                    sp2.setScale(.2);
                    sp2.sp = true;
                    sp2.setBoard(game.pList()[pNum-1].getBoard());
                    sp2.setShift(0,(int)(120*.7));
                    sp2.setBounds(getWidth()*6/8+getWidth()/16,getHeight()*yPlay/4+60,getWidth()*2/10,getHeight()*3/18);
                    
                }else  if(yPlay==2){
                    sp3.setScale(.2);
                    sp3.sp = true;
                    sp3.setBoard(game.pList()[pNum-1].getBoard());
                    sp3.setShift(0,(int)(120*.7));
                    sp3.setBounds(getWidth()*6/8+getWidth()/16,getHeight()*yPlay/4+60,getWidth()*2/10,getHeight()*3/18);
                    
                }
                
                g.setFont(new Font("Arial", Font.PLAIN, 15));
                g.drawImage(selectOutline, getWidth()*58/128, getHeight()*22/25, 45, 50, null);
                
                //System.out.println("bruh time"+game.getPlayerList()[0].getScore());
                
                
                
                game.getScoring().score(game.getPlayerList()[pNum-1].getBoard());
                g.setFont(new Font("Arial", Font.PLAIN, 8));
                g.drawImage(selectOutline,  getWidth()*5/6-getWidth()/64,  getHeight()*yPlay/4+30, 22, 25, null);
                g.drawString(game.getScoring().getLandScore().toString() +" / "+map.get(Integer.toString(pNum-1)), getWidth()*5/6+getWidth()/256, getHeight()*yPlay/4+getHeight()*1/16);
                for(int i = 0; i<animal.length; i++) {
                	g.drawImage(animalTokenMap.get(animal[i])[0], getWidth()*143/168+getWidth()*i*15/512, getHeight()*(yPlay)/4+getHeight()*3/64, 20, 20, null);
                	g.drawString(game.getScoring().getAnimalScore(animal[i]).toString(), getWidth()*5/6+getWidth()*(i*15+18)/512, getHeight()*yPlay/4+getHeight()*1/16);
                }
                
                game.getPlayerList()[pNum-1].setBonus(map.get(Integer.toString(pNum-1)));
        		g.setFont(new Font("Arial", Font.PLAIN, 18));
        		g.drawString("Player "+pNum, getWidth()*13/16+10,getHeight()*(yPlay)/4+getHeight()*8/256);
        		g.setFont(new Font("Arial", Font.PLAIN, 15));
        		g.drawImage(natureToken, getWidth()*7/8+5, getHeight()*yPlay/4+1, 30, 30, null);
        		g.drawString(": "+game.getPlayerList()[pNum-1].getNumTokens(), getWidth()*7/8+40, getHeight()*yPlay/4+20);
        		g.setFont(new Font("Arial", Font.PLAIN, 10));
        		g.drawString("Current Score: "+String.valueOf(game.getPlayerList()[pNum-1].getScore() +game.getPlayerList()[pNum-1].getBonus()), getWidth()*13/14, getHeight()*yPlay/4+getHeight()*7/256);
        		g.drawRect(getWidth()*13/16, getHeight()*(yPlay+1)/4, getWidth()*24/128, 2);
        		//draw other players boards (but not as buttons)
                yPlay++;
        	}
        }
        
        for(int i = 0;i<5;i++) {
            g.drawRect(getWidth()/7-i, getHeight()/8-i, getWidth() - getWidth() / 3+2*i, getHeight()*3/4 +2*i);
        }
       
        //cancelB.setVisible(false);//make buttons appear at right time
        if(tileChose || (mixMatchUsed && state == 4) || noAnimalPlace) {
        	cancelB.setVisible(true);
        	if(noAnimalPlace) {
        		g.setFont(new Font("Arial", Font.PLAIN, 13));
        		g.drawString("There is no place for this animal. Please click cancel to replace the animal and end your turn.", getWidth()*1/50, getHeight()*23/25);
        	}
        	if(tileChose) {
        		removeDups.setVisible(false);
        	}
        }
        //allow the user to choose not to keep animal
        else if(!noAnimalPlace && !clearAnimalsUsed && state == 3) {
        	cancelB.setVisible(true);
        	g.setFont(new Font("Arial", Font.PLAIN, 15));
    		g.drawString("You may choose to click cancel, not place an animal and end your turn.", getWidth()*4/50, getHeight()*23/25);
        }
        else {
        	cancelB.setVisible(false);
        }
        if(nodeSelected!=null) {
        	//removeDups.setVisible(false);
        	confirmB.setVisible(true);
        	g.drawImage(rotateImage, 125, 488, 50, 55, null);
        	rotate.setVisible(true);
        }
        else {
        	confirmB.setVisible(false);
        	rotate.setVisible(false);
        }
        
        if(game.getCurrPlayer().getNumTokens() == 0 || natureTokenUsed) {
        	useNature.setVisible(false);
        }
        else if(!natureTokenUsed){
        	useNature.setVisible(true);
        	clearAnimals.setVisible(false);
        	mixMatch.setVisible(false);
        	confirmClear.setVisible(false);
        }

        if(!dupAnimalsUsed && !tileChose) {
        	//3 same animal
            if (game.cntDup()==3){
                removeDups.setVisible(true);
            }
	        else{ // less than 3
                removeDups.setVisible(false);
            }

        }
        //g.drawImage(dpad, 800, 600, 240, 240, null);


       
        //left.showButton();
        
        for (int i=0;i<4;i++){
            add(fourButtonTiles[i]);
            add(fourButtonAnimal[i]);
            g.drawImage(tiles4[i], getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            g.drawImage(outline,   getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            fourButtonTiles[i].setBounds(getWidth()/75, getHeight()/8+i*95, 75, 87);
            if (i == numSelectedTile){
                g.drawImage(selectOutline, getWidth()/75, getHeight()/8+i*95, 75, 87, null);
            }
            if(!clearAnimalsUsed) {
	            if (i==numSelectedAnimal&&drawHighlightAnimal) {
	                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/8+i*95, 60, 60, null);
	
	            }else{
	                g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/8+i*95, 60, 60, null);
	            }
            }
            else {
            	if(animalsToClear.contains(i)) {
            		g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[1], 115, getHeight()/8+i*95, 60, 60, null);
            	}
            	else {
            		g.drawImage(animalTokenMap.get(game.getAnimalToken4()[i])[0], 115, getHeight()/8+i*95, 60, 60, null);
            	}
            }
            fourButtonAnimal[i].setBounds(115, getHeight()/8+i*95, 60, 60);
        }
        
        if(actionLogUsed) {
        	Queue<String> actions = game.getActionLog();
        	g.setFont(new Font("Comic Sans", Font.BOLD, 10));
            Iterator<String> it = actions.iterator();
            int i=0;
        	while (it.hasNext()) {
        		g.drawString(it.next(), getWidth()*13/16+10, getHeight()*4/5+(i*20));
                i++;
        	}
        }
        Graphics2D g2 = (Graphics2D) g.create();
        if(prog<=100){
        g2.setStroke(new BasicStroke(6));
        g2.setColor(Color.BLACK);
        g2.drawRect(getWidth()/15, getHeight()/10*9, getWidth()/3, getHeight()/20);
        g2.setColor(Color.GREEN);
        g2.fillRect(getWidth()/15, getHeight()/10*9, getWidth()*prog/300, getHeight()/20);
        }
        if(prog<101){
            prog++;
            help.setVisible(false);
            scoreCards.setVisible(false);
            actionLog.setVisible(false);
            useNature.setVisible(false);
            removeDups.setVisible(false);
            nextB.setVisible(false);
            confirmClear.setVisible(false);
            clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
        try{
            Thread.sleep(5);
        }
        catch (Exception E){

        }
        repaint();
        }else if (prog==101){
            try{
                Thread.sleep(250);
                prog++;
                nextTurn();
            }
            catch (Exception E){
    
            }
        }
        rotate.setBounds(125, 490, 50, 50);
        bp.setBounds(getWidth()/7, getHeight()/8, getWidth() - getWidth() / 3, getHeight()*3/4);
        cancelB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/10, getWidth()/15-10, getHeight()/15);
        nextB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5, getWidth()/15-10, getHeight()/15);
        confirmB.setBounds(getWidth()/30-30, getHeight()*3/5+getHeight()/5+getHeight()/10, getWidth()/15-10, getHeight()/15);
        help.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        scoreCards.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
        actionLog.setBounds(getWidth()/3+getWidth()/5, getHeight()/25, getWidth()/15, getHeight()/15);
        useNature.setBounds(getWidth()/3+getWidth()/5+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
        removeDups.setBounds(getWidth()/3+getWidth()/5+getWidth()/5, getHeight()/25, getWidth()/15, getHeight()/15);
        confirmClear.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        clearAnimals.setBounds(getWidth()/3, getHeight()/25, getWidth()/15, getHeight()/15);
        mixMatch.setBounds(getWidth()/3+getWidth()/10, getHeight()/25, getWidth()/15, getHeight()/15);
    }

    public int getState(){
        return state;
    }

    public String getCurVal(){
        return curVal;
    }

    public String getCurAnimal(){
        return curAnimal;
    }
    
    public Game getGame() {
    	return game;
    }

    public void nextA(){
        state++;
        game.updateAnimal4(numSelectedAnimal);

        numSelectedAnimal=-1;
        drawHighlightAnimal=false;

    }
    public void next(Node node){
        nodeSelected=node;
        tileChose = false;
        state++;
        //update deck
        game.updateTileDeck(numSelectedTile);
        numSelectedAnimal=numSelectedTile;
        try {
            tiles4[numSelectedTile] = ImageIO.read(new File("img/Tile/" + game.getTileName4()[numSelectedTile] + ".png"));
        } catch (Exception E) {
            System.out.println("blah");
        }
        numSelectedTile=-1;
    }
    
    public void openWebPage(String url){
	   try {         
		   java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
	   }
	   catch (java.io.IOException e) {
	       System.out.println(e.getMessage());
	   }
	}
    
    public void nextTurn() {
    	//game.curPlayerScore();//sets score of player
    	game.nextTurn();
        bp.setBoard(game.getCurrPlayer().getBoard());
        state=0;
        dupAnimalsUsed = false;
        natureTokenUsed = false;
        mixMatchUsed = false;
        clearAnimalsUsed = false;
        noAnimalPlace = false;
        curVal="";
    	numSelectedTile = -1;
    	numSelectedAnimal =-1;
    	curAnimal ="";
        help.setVisible(true);
    	scoreCards.setVisible(true);
        actionLog.setVisible(true);
        nextB.setVisible(true);
        
        game.addAction("Next Turn: Player "+(game.getPlayerNum()+1));
        if(game.getTurn() > 20) {
        	//end the game
        }
        repaint();
        //return;
    }
    

    
    @Override
    public void actionPerformed(ActionEvent e) {

        //System.out.println(state);

    	if(e.getSource().equals(scoreCards)) {
    		//show scoring cards
    	}
    	
    	if(e.getSource().equals(actionLog)) {
    		actionLogUsed = !actionLogUsed;
    		if(actionLogUsed) {
    			game.getActionLog().clear();
    			game.addAction("Action Log was turned on.");
    		}
    		repaint();
    		return;
    	}
        
        if(e.getSource().equals(confirmClear)) {
            for (int i:animalsToClear){
                game.returnAnimalToken(game.getAnimalToken4()[i]);
                game.updateAnimal4(i);
            }
        	clearAnimalsUsed = false;
        	drawHighlightAnimal = false;
        	confirmClear.setVisible(false);
        	animalsToClear.clear();
        	state = 0;
        	if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" confirmed clearing animals.");
            }
        	repaint();
    		return;
        }
        
        if(e.getSource().equals(clearAnimals)) {
        	clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            confirmClear.setVisible(true);
            clearAnimalsUsed = true;
            state = 3;
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" chose to clear animals.");
            }
            repaint();
        }
        if(e.getSource().equals(mixMatch)) {
            curAnimal="";
        	numSelectedAnimal = -1;//basically u can choose any animal
        	mixMatchUsed = true;
        	clearAnimals.setVisible(false);
            mixMatch.setVisible(false);
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" chose to mix and match tile & token.");
            }
            repaint();
        }
        
        if(e.getSource().equals(useNature)) {
            if (!game.getCurrPlayer().useNt())
                return;
        	help.setVisible(false);
        	scoreCards.setVisible(false);
            actionLog.setVisible(false);
            useNature.setVisible(false);
            dupAnimalsUsed = true;//turns off replace duplicate after using nature token
            removeDups.setVisible(false);
            clearAnimals.setVisible(true);
            mixMatch.setVisible(true);
            natureTokenUsed = true;
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" used a nature token.");
            }
            repaint();
            //remove token (token >0 -> nature token buttons appears)
        }
        
        //help button -> open link
        if(e.getSource().equals(help)) {
        	openWebPage("https://www.alderac.com/wp-content/uploads/2021/08/Cascadia-Rules.pdf");
        }
        //remove duplicate animals
        if(e.getSource().equals(removeDups) && !dupAnimalsUsed) {
        	dupAnimalsUsed = true;
            game.removeDups();
            removeDups.setVisible(false);
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" chose to remove duplicate animals.");
            }
            repaint();
            return;
        }
        
        //will need to remove later on
        if (e.getSource().equals(nextB)){
            nextTurn();
            repaint();
            return;
        }
        //select tile
        for (int i=0;i<4;i++){
            HexButton b = fourButtonTiles[i];
            if (e.getSource().equals(b) && state==0 && !curVal.equals(game.getTileName4()[i])){
                //System.out.println("FourbUttons");
                curVal= game.getTileName4()[i];
                //System.out.println(curVal);
                numSelectedTile=i;
                if(!mixMatchUsed) {
                    //System.out.println("no mixMatch");
                	numSelectedAnimal=i;
                	curAnimal = game.getAnimalToken4()[i];
                    //drawHighlightAnimal=true;
                	//state = 3;
                }

                nodeSelected=null;
                state++;
                tileChose = true;
                if(actionLogUsed) {
                	String habitats = curVal.substring(0,2);
                	String animals = curVal.substring(3);
                	int l = animals.length();
                	if(habitats.contains("D")) habitats += " and Desert";
                	if(habitats.contains("F")) habitats += " and Forest";
                	if(habitats.contains("L")) habitats += " and Lake";             	
                	if(habitats.contains("M")) habitats += " and Mountain";        	
                	if(habitats.contains("S")) habitats += " and Swamp";
                	habitats = habitats.substring(7);
                	if(animals.contains("B")) animals += " and Bear";
                	if(animals.contains("E")) animals += " and Elk";
                	if(animals.contains("F")) animals += " and Fox";             	
                	if(animals.contains("H")) animals += " and Hawk";        	
                	if(animals.contains("S")) animals += " and Salmon";
                	animals = animals.substring(l+5);
                	
                	game.addAction("Player "+(game.getPlayerNum()+1)+" picked tile: "+habitats+".");
                	game.addAction("This tile can hold "+animals+".");
                }
                
                repaint();
                return;
            }
            //cancel tile via clicking it
            else if(curVal.equals(game.getTileName4()[i])){
            	curVal ="";
            	numSelectedTile = -1;
            	numSelectedAnimal = -1;
            	curAnimal = "";
            	state = 0;
            	tileChose = false;
            	if(actionLogUsed) {
            		game.addAction("Player "+(game.getPlayerNum()+1)+" unselected their tile.");
                }
            	repaint();
            	return;
            }
        }
        //cancel tile
        if(e.getSource().equals(cancelB) && state==1) {
        	curVal="";
        	numSelectedTile = -1;
        	numSelectedAnimal =-1;
        	curAnimal ="";
        	state = 0;
        	tileChose = false;
        	if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" unselected their tile.");
            }
        	repaint();
        	return;
        	
        }
        //rotate angle
        if (nodeSelected!=null && e.getSource().equals(rotate) && state==2){
            nodeSelected.addRotateAngle();
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" rotated their tile.");
            }
            repaint();
            return;
        }
        //confirm tile placement
        if (e.getSource().equals(confirmB)&&state==2) {
            nodeSelected = null;
            if (!mixMatchUsed)
                drawHighlightAnimal = true;
            dupAnimalsUsed = true;
            removeDups.setVisible(false);//turns off replace duplicate after placing tile
            state++;
            if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" confirmed their tile placement.");
            }
            //System.out.println(state);
            repaint();
            //return;
        }




    	//clearAnimals
    	if(state == 3 && clearAnimalsUsed) {
        	for (int i=0;i<4;i++){
                InvisButton b = fourButtonAnimal[i];
                if (e.getSource().equals(b)){
                	//System.out.println("click to clear");
                	if(!animalsToClear.contains(i)) {
                		animalsToClear.add(i);
                	}
                	else {
                		animalsToClear.remove(i);
                	}
                	repaint();
                	return;
                }
    		}
    	}
        //pick animal (mix&match)
    	else if(state == 3 && mixMatchUsed) {
    		for (int i=0;i<4;i++){
                InvisButton b = fourButtonAnimal[i];
                //cancel animal by clicking on it
                if (e.getSource().equals(b) && i==numSelectedAnimal) {
                	numSelectedAnimal = -1;
                	curAnimal = "";
                	drawHighlightAnimal = false;
                	cancelB.setVisible(false);
                	repaint();
                	return;
                }
                else if (e.getSource().equals(b)){
                    numSelectedAnimal = i;
                    curAnimal = game.getAnimalToken4()[i];
                    drawHighlightAnimal = true;
                    cancelB.setVisible(true);
                    repaint();
                    return;
                }
    		}
    	}

    	//no place to put animal
    	else if (state == 3 && !game.getAnimalAllowed(game.getCurrPlayer().getBoard(), curAnimal)) {
    		cancelB.setVisible(true);
    		noAnimalPlace = true;
    		drawHighlightAnimal = false;
    		state++;
    		if(actionLogUsed) {
        		//game.addAction("Player "+(game.getPlayerNum()+1)+" had no place to put a "+ curAnimal+ "token.");
        		
        		if(curAnimal.equals("B")) {
                	game.addAction("Player "+(game.getPlayerNum()+1)+ " had no place to put a bear token.");
                }
                if(curAnimal.equals("E")) {
                	game.addAction("Player "+(game.getPlayerNum()+1)+ " had no place to put an elk token.");
                }
                if(curAnimal.equals("F")) {
                	game.addAction("Player "+(game.getPlayerNum()+1)+ " had no place to put a fox token.");
                }
                if(curAnimal.equals("H")) {
                	game.addAction("Player "+(game.getPlayerNum()+1)+ " had no place to put a hawk token.");
                }
                if(curAnimal.equals("S")) {
                	game.addAction("Player "+(game.getPlayerNum()+1)+ " had no place to put a salmon token.");
                }
            }
    		repaint();
    		
    		//System.out.println("no space for animal");
    	}
    	
    	else if(state == 3 && game.getAnimalAllowed(game.getCurrPlayer().getBoard(), curAnimal)) {
    		cancelB.setVisible(true);
    		repaint();
    	}
    	
    	if(e.getSource().equals(cancelB)&&state==3 &&!mixMatchUsed) {
    		if(actionLogUsed) {
        		game.addAction("Player "+(game.getPlayerNum()+1)+" chose not to place an animal token.");
            }
            nextTurn();
    		repaint();
    		return;
    	}
    	
    	
    	if(e.getSource().equals(cancelB)&&state==4 &&!mixMatchUsed) {
    		game.returnAnimalToken(game.getAnimalToken4()[numSelectedAnimal]);
            game.updateAnimal4(numSelectedAnimal);
            nextTurn();
    		repaint();
    		return;
    	}

        //cancel animal chosen wrong in mix and match
        if (e.getSource().equals(cancelB)&&state==4 &&mixMatchUsed){
            curAnimal="";
            numSelectedAnimal=-1;
            drawHighlightAnimal=false;
            repaint();

            state--;


            return;
        }
    }
    public void resetProg(){
        prog = 0;
    }
}