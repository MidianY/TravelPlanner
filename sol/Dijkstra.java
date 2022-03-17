package sol;

import src.IDijkstra;
import src.IGraph;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
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
        // when you get to using a PriorityQueue, remember to remove and re-add a vertex to the
        // PriorityQueue when its priority changes!
        return null;
    }






    public boolean checkPath(IGraph<V, E> graph, V source, V destination,
                             Function<E, Double> edgeWeight) {
        Comparator<V> weights = (weight1, weight2) -> {
            return Double.compare(this.costs.get(weight1), this.costs.get(weight2));
        };
        PriorityQueue<V> toCheckQueue = new PriorityQueue<>(weights);
        for (V vertex : graph.getVertices()) {
            this.costs.put(source, (double)0);
            this.costs.put(vertex, Double.POSITIVE_INFINITY);

            while (!toCheckQueue.isEmpty()) {
                V targetVertex = toCheckQueue.poll();
                for (E edge : graph.getOutgoingEdges(targetVertex)) {
                    if (targetVertex.)
                }
            }
        }
    }




    // TODO: feel free to add your own methods here!
}
