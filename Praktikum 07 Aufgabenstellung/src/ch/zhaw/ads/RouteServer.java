package ch.zhaw.ads;

import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

public class RouteServer implements CommandExecutor {
    Queue<DijkstraNode> redNodes = null;
    DijkstraNode startNode = null;
    DijkstraNode currentNode = null;
    DijkstraNode neighbour = null;

    Double dist = 0.0;
    /**
    build the graph given a text file with the topology
    */
    public Graph<DijkstraNode, Edge> createGraph(String topo) throws Exception{
        Graph<DijkstraNode,Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);
        String[] rows = topo.split("\n");
        for (String row : rows) {
            String[] data = row.split(" ");
            try {
                graph.addEdge(data[0], data[1], Double.parseDouble(data[2]));
                graph.addEdge(data[1], data[0], Double.parseDouble(data[2]));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return graph;
    }


    /**
    apply the dijkstra algorithm
    */
    public void dijkstraRoute(Graph<DijkstraNode, Edge> graph, String from, String to) {
        redNodes = new PriorityQueue<>();
        startNode = graph.findNode(from);
        for(DijkstraNode node : graph.getNodes()){
            node.setDist(Integer.MAX_VALUE);
            node.prev = null;
        }
        startNode.setDist(0);
        redNodes.add(startNode);

        while (!redNodes.isEmpty()){
            currentNode = redNodes.remove();
            if(currentNode == graph.findNode(to)){
                return;
            }
            currentNode.setMark(true);
            for(Edge edge : currentNode.getEdges()){
                neighbour = (DijkstraNode) edge.getDest();
                if(!neighbour.getMark()){
                    dist = currentNode.getDist() + edge.getWeight();
                    if(dist < neighbour.getDist()){
                        neighbour.setDist(dist);
                        neighbour.setPrev(currentNode);
                        redNodes.remove(neighbour);
                        redNodes.add(neighbour);
                    }
                }
            }
        }
    }

    /**
    find the route in the graph after applied dijkstra
    the route should be returned with the start town first
    */
    public List<DijkstraNode> getRoute(Graph<DijkstraNode, Edge> graph, String to) {
        List<DijkstraNode> route = new LinkedList<>();
        DijkstraNode town = graph.findNode(to);
        do {
            route.add(0, town);
            town = town.getPrev();
        } while (town != null);
        return route;
    }

    public String execute(String topo) throws Exception {
        Graph<DijkstraNode, Edge> graph = createGraph(topo);
        dijkstraRoute(graph, "Winterthur", "Lugano");
        List<DijkstraNode> route = getRoute(graph, "Lugano");
        // generate result string
        StringBuilder builder = new StringBuilder();
        for (DijkstraNode rt : route) builder.append(rt).append("\n");
        return builder.toString();
    }

    public static void main(String[] args)throws Exception {
        String swiss = "Winterthur Zürich 25\n" +
                    "Zürich Bern 126\n" +
                    "Zürich Genf 277\n" +
                    "Zürich Luzern 54\n" +
                    "Zürich Chur 121\n" +
                    "Zürich Berikon 16\n" +
                    "Bern Genf 155\n" +
                    "Genf Lugano 363\n" +
                    "Lugano Luzern 206\n" +
                    "Lugano Chur 152\n" +
                    "Chur Luzern 146\n" +
                    "Luzern Bern 97\n" +
                    "Bern Berikon 102\n" +
                    "Luzern Berikon 41\n";
        RouteServer server = new RouteServer();
        System.out.println(server.execute(swiss));
    }
}
