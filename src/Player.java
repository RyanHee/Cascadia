public class Player {
    private int turn;
    private int nt;
    private Node board;
    public Player(Node n, int t){
        turn = t;
        board = n;
    }

    public Node getBoard(){
        return board;
    }

    public void addNt(){
        nt++;
    }

    public boolean useNt(){
        if (nt>0){
            nt--;
            return true;
        }
        return false;
    }







}
