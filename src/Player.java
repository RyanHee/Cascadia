public class Player {
    private int turn;
    private int nt;
    private int score;
    private Node board;
    public Player(Node n, int t){
        turn = t;
        board = n;
        score = 0;
    }

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
    
    public int getScore() {
    	return score;
    }

    public void setScore(int s) {
    	score = s;
    }





}
