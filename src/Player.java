import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Player implements Comparable {
    private final int turn;
    private int nt;
    private int lscore;
    private int animalScore;
    private Node board;
    private HashMap<String, Integer>bonusmp;
    private HashMap<String, Integer>landmp, animalmp;
    private BufferedImage pfp;
    private Color color;

    public Player(Node n, int t){
        turn = t;
        board = n;
        lscore = 0;
        bonusmp=new HashMap<>();
        landmp=new HashMap<>();
        animalmp=new HashMap<>();
        String[] land=new String[]{"D", "F", "S", "L", "M"};
        for (String s:land){
            bonusmp.put(s, 0);
        }
    }

    public int getTurn(){return turn;}
    public Node getBoard(){
        return board;
    }

    public void addNt(){
        nt++;
    }
    
    public int getNumTokens() {
    	return nt;
    }

    public boolean useNt(){
        if (nt>0){
            nt--;
            return true;
        }
        return false;
    }

    public int bonusScore(){
        int a=0;
        for (String s:bonusmp.keySet()){
            a+= bonusmp.get(s);
        }
        return a;
    }

    public int getAnimalScore() {
        return animalScore;
    }

    public void setAnimalScore(int animalScore) {
        this.animalScore = animalScore;
    }

    public int getScore() {
    	return lscore+animalScore+nt+bonusScore();
    }

    public int getLandScore() {
        return lscore;
    }

    public void setLandScore(int s) {
    	lscore = s;
    }

    public void setBonus(String l, int i){
        bonusmp.put(l, i);
    }

    public HashMap<String, Integer> getAnimalmp() {
        return animalmp;
    }

    public HashMap<String, Integer> getLandmp() {
        return landmp;
    }

    public void setAnimalmp(HashMap<String, Integer> a) {
        this.animalmp = a;
    }

    public void setLandmp(HashMap<String, Integer> l) {
        this.landmp = l;
    }

    public HashMap<String, Integer> getBonusmp() {
        return bonusmp;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setPfp(BufferedImage pfp) {
        this.pfp = pfp;
    }

    public BufferedImage getPfp() {
        return pfp;
    }

    public Color getColor() {
        return color;
    }


    @Override
    public int compareTo(Object o) {
        try{
            Player p = (Player) o;
            if (this.getScore()==p.getScore()){
                if (this.getNumTokens()==p.getNumTokens()){
                    if (this.getTurn()>p.getTurn()){
                        return 1;
                    }
                    return -1;
                }
                else if(this.getNumTokens()>p.getNumTokens()){
                    return 1;
                }
                return -1;
            }
            else if (this.getScore()>p.getScore()){
                return 1;
            }
            return -1;
        }
        catch (Exception E){

        }
        return 0;
    }
}

