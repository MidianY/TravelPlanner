package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

public class Dijkstra<V, E> implements IDijkstra<V, E> {
    private HashMap<V, E> path;
    private HashMap<V, Double> costs;

    public Dijkstra(){
        this.path = new HashMap<>();
        this.costs = new HashMap<>();
    }

    // TODO: implement the getShortestPath method!
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        this.checkPath(graph, source, edgeWeight); //should only be called if shortest path is called?

        System.out.println(this.path.get(destination));
        List<E> shortestPath = new ArrayList<>();
        shortestPath.add(this.path.get(destination));

        V current = graph.getEdgeSource(this.path.get(destination));
        while(!current.equals(source)){
            shortestPath.add(this.path.get(current));
            current = graph.getEdgeSource(this.path.get(current));
        }
        return shortestPath;
    }



    public void checkPath(IGraph<V, E> graph, V source,
                             Function<E, Double> edgeWeight) {

        Comparator<V> weights = (weight1, weight2) -> {
            return Double.compare(this.costs.get(weight1), this.costs.get(weight2));
        };

        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(weights);
        for (V vertex : graph.getVertices()) {
            this.costs.put(vertex, Double.POSITIVE_INFINITY);
        }
        this.costs.put(source, (double) 0);

        toCheckQueue.addAll(graph.getVertices());
        while (!toCheckQueue.isEmpty()) {
            V currentVertex = toCheckQueue.poll();
            for (E edge : graph.getOutgoingEdges(currentVertex)) {
                V neighborVertex = graph.getEdgeTarget(edge);

                if (this.costs.get(currentVertex) + edgeWeight.apply(edge) < this.costs.get(neighborVertex)){
                    this.costs.put(neighborVertex, this.costs.get(currentVertex) + edgeWeight.apply(edge));
                    this.path.put(neighborVertex, edge);
                }
            }
        }
    }
}
