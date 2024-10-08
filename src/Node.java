public class Node implements Comparable<Node> {
    String node;
    int distance;

    Node(String node, int distance) {
        this.node = node;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(distance, other.distance);
    }
}