import java.util.HashMap;
import java.util.HashSet;

public class Scoring {
    private HashMap<Integer, Integer>map = new HashMap<>();
    private String[]land;
    public Scoring(){
        map.put(0,3);
        map.put(1,4);
        map.put(2,5);
        map.put(3,0);
        map.put(4,1);
        map.put(5,2);
        land=new String[]{"D", "F", "S", "L", "M"};

    }

    public int landMass(Node node){
        int s=0;
        for (int i=0;i<5;i++){
            s+=dfsLand(node, land[i], new HashSet<>(), 0);
        }
        return s;
    }

    public int dfsLand(Node node, String land, HashSet<Node> visited, int sum){
        System.out.println(node);
        if (visited.contains(node)){
            return 0;
        }
        visited.add(node);
        Node[]lst = node.getNeighbors();
        int a = 0;
        for (int i=0;i<6;i++){

            if (node.getSides()[i].equals(land)){
                a++;
                if (lst[i].getSides()[map.get(i)].equals(land)){
                    sum+=dfsLand(lst[i], land, visited, sum);
                }
            }
        }
        if (a>0){
            sum++;
        }
        return sum;
    }

}
