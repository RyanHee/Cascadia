import java.util.HashMap;
import java.util.HashSet;

public class Scoring {
    private HashMap<Integer, Integer> opp;
    private HashMap<String, Integer> landScore;
    private HashSet<Node> allVisited;
    private String[]land;
    public Scoring(){
        opp = new HashMap<>();
        opp.put(0,3);
        opp.put(1,4);
        opp.put(2,5);
        opp.put(3,0);
        opp.put(4,1);
        opp.put(5,2);
        land=new String[]{"D", "F", "S", "L", "M"};
        landScore=new HashMap<>();
        for (String s:land){
            landScore.put(s, 0);
        }
    }

    public int landMass(Node node){
        int s=0;

        landScore=new HashMap<>();
        for (String l:land){
            landScore.put(l, 0);
        }
        for (int i=0;i<5;i++){
            allVisited=new HashSet<>();
            dfsLand(node, land[i]);
            System.out.println(i);
            s+=landScore.get(land[i]);
        }
        return s;
    }

    public void dfsLand(Node node, String land){
        if (allVisited.contains(node)){
            return ;
        }
        if (node.getVal()==null){
            return ;
        }
        allVisited.add(node);
        boolean score=false;
        for (int i=0;i<6;i++){
            if (node.getSides()[i].equals(land)){
                score=true;
            }
        }
        if (score){
            System.out.println("a"+land);
            landScore.replace(land, Math.max(landScore.get(land), scoreLand(node, land, new HashSet<>(), 0)));
        }
        for (int i=0;i<6;i++){
            dfsLand(node.getNeighbors()[i], land);
        }
    }

    public int scoreLand(Node node, String land, HashSet<Node> visited, int sum){

        allVisited.add(node);
        if (visited.contains(node)){
            return 0;
        }
        visited.add(node);
        Node[]lst = node.getNeighbors();
        int a = 0;
        //System.out.println(node);
        for (int i=0;i<6;i++){
            //System.out.println(lst[i]);
            if (node.getSides()[i]!=null && node.getSides()[i].equals(land)){
                a++;
                if (lst[i].getSides()[opp.get(i)]!=null && lst[i].getSides()[opp.get(i)].equals(land)){
                    sum+=scoreLand(lst[i], land, visited, sum);
                }
            }
        }
        if (a>0){
            sum++;
        }
        return sum;
    }

}
