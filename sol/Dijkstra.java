package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.*;
import java.util.function.Function;

/**
 * This is the Dijkstra class, it implements the Dijkstra algorithm, which is essentially used to obtain
 * the cheapest route and fastest route.
 * @param <V>
 * @param <E>
 */
public class Dijkstra<V, E> implements IDijkstra<V, E> {
    private HashMap<V, E> path;
    private HashMap<V, Double> costs;

    /**
     * This is the constructor of the Dijkstra class, it is where the path hashMap
     * (maps cities(vertices) to transport(edges)) and costs hashMap(maps cities(vertices) to edgeWeights)
     * are instantiated.
     */
    public Dijkstra(){
        this.path = new HashMap<>();
        this.costs = new HashMap<>();
    }

    /**
     * Gets the path from one city to another. Best suitable for obtaining the cheapest and fastest routes.
     * @param graph       the graph including the vertices
     * @param source      the source vertex
     * @param destination the destination vertex
     * @param edgeWeight
     * @return
     */
    @Override
    public List<E> getShortestPath(IGraph<V, E> graph, V source, V destination,
                                   Function<E, Double> edgeWeight) {

        this.checkPath(graph, source, edgeWeight);
        List<E> shortestPath = new ArrayList<>();
        V current = destination;
        E currentEdge = this.path.get(current);
        while(currentEdge != null){
            shortestPath.add(0, this.path.get(current));
            current = graph.getEdgeSource(this.path.get(current));
            currentEdge = this.path.get(current);
        }

        return shortestPath;
    }

    /**
     *
     * @param graph
     * @param source
     * @param edgeWeight
     */
    public void checkPath(IGraph<V, E> graph, V source,
                             Function<E, Double> edgeWeight) {

        Comparator<V> weights = (weight1, weight2) -> {
            return Double.compare(this.costs.get(weight1), this.costs.get(weight2));
        };

        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(weights);
        for (V vertex : graph.getVertices()) {
            this.costs.put(vertex, Double.POSITIVE_INFINITY);
            this.path.put(vertex, null);
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
                    toCheckQueue.remove(neighborVertex);
                    toCheckQueue.add(neighborVertex);
                }
            }
        }
    }
}
