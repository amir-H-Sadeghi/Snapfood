import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    public static Graph map = new Graph();
    public static Map<String, Map<String, Integer>>graph;
    Graph(){
        graph = new HashMap<>();
        readFile();
    }
    public static void readFile(){
        try{
            File file=new File("graph.txt");
            Scanner scanner=new Scanner(file);
            String[] line = scanner.nextLine().split(" ");
            int numNodes=Integer.parseInt(line[0]);
            int numEdges=Integer.parseInt(line[1]);
            for(int i=0;i<numNodes; i++){
                String node=Integer.toString(i+1);
                graph.put(node,new HashMap<>());
            }
            for(int i=0;i<numEdges; i++){
                String[]edgeInfo=scanner.nextLine().split(" ");
                String node1=edgeInfo[0];
                String node2=edgeInfo[1];
                int weight=Integer.parseInt(edgeInfo[2]);
                graph.get(node1).put(node2,weight);
                graph.get(node2).put(node1,weight);
            }
            scanner.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Map<String, Integer> dijkstra(Map<String, Map<String, Integer>> graph, String start , String end) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        Map<String, Integer> distances = new HashMap<>();
        Map<String,LinkedList<String>> bestPath = new HashMap<>();
        for (String node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentDistance = current.distance;
            String currentNode = current.node;
            for (Map.Entry<String, Integer> neighbor : graph.get(currentNode).entrySet()) {
                String neighborNode = neighbor.getKey();
                LinkedList<String> value = new LinkedList<>();
                int weight = neighbor.getValue();
                int distance = currentDistance + weight;
                if (distance < distances.get(neighborNode)) {
                    distances.put(neighborNode, distance);
                    pq.offer(new Node(neighborNode, distance));
                    if(bestPath.containsKey(currentNode))
                        value = (LinkedList<String>) bestPath.get(currentNode).clone();
                    value.add(neighborNode);
                    bestPath.put(neighborNode,value);
                }
            }

        }
        if(bestPath.get(end).isEmpty())
            System.out.println("no path");
        System.out.print(start+" ");
        for (String path : bestPath.get(end)){
            System.out.print(path+" ");
        }
        System.out.println();
        return distances;
    }
    static void showMap(){
        for(Map.Entry<String , Map<String , Integer>> map : graph.entrySet()){
            for (Map.Entry<String , Integer> neighborNode : map.getValue().entrySet()){
                System.out.println("Node with name "+map.getKey()+" is neighbor with node "+neighborNode.getKey()+" with weight "+neighborNode.getValue());
            }
        }
    }
}
