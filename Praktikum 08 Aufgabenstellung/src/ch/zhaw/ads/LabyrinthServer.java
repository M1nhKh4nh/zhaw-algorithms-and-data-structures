package ch.zhaw.ads;

import java.awt.*;

public class LabyrinthServer implements CommandExecutor {
    ServerGraphics g = new ServerGraphics();

    public Graph<DijkstraNode, Edge> createGraph(String s) {
        // TODO implement 8.2
        Graph<DijkstraNode,Edge> graph = new AdjListGraph<>(DijkstraNode.class, Edge.class);
        String[] rows = s.split("\n");
        for (String row : rows) {
            String[] data = row.split(" ");
            try {
                graph.addEdge(data[0], data[1], 0);
                graph.addEdge(data[1], data[0], 0);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        }
        return graph;
    }

    public void drawLabyrinth(Graph<DijkstraNode, Edge> graph) {
        // TODO implement 8.3
        g.setColor(Color.WHITE);
        graph.getNodes().forEach(node -> node.getEdges().forEach(edge -> g.drawPath(node.getName(), edge.getDest().getName(), false)));
    }

    private boolean search(DijkstraNode current, DijkstraNode ziel) {
        current.setMark(true);
        if(current.name.equals(ziel.getName())){
            return true;
        }

        for(Edge edge : current.getEdges()){
            if(!((DijkstraNode) edge.getDest()).getMark()) {
                if (search((DijkstraNode) edge.getDest(), ziel)) {
                    ((DijkstraNode) edge.getDest()).setPrev(current);
                    return true;
                }
            }
        };
        current.setMark(false);
        return false;
    }

    // search and draw result
    public void drawRoute(Graph<DijkstraNode, Edge> graph, String startNode, String zielNode) {
        // TODO implement 8.4
        search(graph.findNode(startNode), graph.findNode(zielNode));

        g.setColor(Color.RED);
        DijkstraNode current = graph.findNode(zielNode);
        do {
            g.drawPath(current.name, current.prev.getName(), true);
            current = current.prev;
        } while(!current.name.equals(startNode));
    }

    public String execute(String s) {
        Graph<DijkstraNode, Edge> graph;
        graph = createGraph(s);
        drawLabyrinth(graph);
        drawRoute(graph, "0-6", "3-0");
        return g.getTrace();
    }
}
