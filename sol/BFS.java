package sol;
import src.IBFS;
import src.IGraph;
import java.util.*;

/**
 * This is the BFS class, it implements the BFS algorithm, which is essentially used to obtain the most direct path
 */
public class BFS<V, E> implements IBFS<V, E> {
    private HashMap<V, E> cityTransport;

    /**
     * This is the constructor of the BFS class, it is where the hashmap connecting cities(vertices) to transport(edges)
     * is instantiated to be used throughout the entire class
     */
    public BFS(){
        this.cityTransport = new HashMap<>();
    }

    /**
     * Gets the path from one city to another. Best suitable for obtaining the most direct route
     * @param graph the graph including the vertices
     * @param start the start vertex
     * @param end   the end vertex
     * @return
     */
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        List<E> path = new ArrayList<>();
        if(checkPath(graph, start, end)){
                path.add(0, this.cityTransport.get(end));
            V current = graph.getEdgeSource(this.cityTransport.get(end));
            while(!current.equals(start)){
                path.add(0, this.cityTransport.get(current));
                current = graph.getEdgeSource(this.cityTransport.get(current));
            }
        }
        return path;
    }

    /**
     * This is a helper method for getPath. It is a boolean that checks whether there is a path between two cities.
     * If there is a path, it returns true and false otherwise.
     * @param graph
     * @param start
     * @param end
     * @return
     */
    private boolean checkPath(IGraph<V, E> graph, V start, V end){
        Queue<E> toCheck = new LinkedList<>(graph.getOutgoingEdges(start)); //contains outgoing edges from the start
        HashSet<V> visited = new HashSet<>();

        while (!toCheck.isEmpty()) {
            E checkingEdge = toCheck.poll();
            V targetVertex = graph.getEdgeTarget(checkingEdge);
            if (visited.contains(targetVertex)) {
                continue;
            }
            visited.add(targetVertex);
            if(!this.cityTransport.containsKey(targetVertex)) {
                this.cityTransport.put(targetVertex, checkingEdge);
            }
            if (end.equals(targetVertex)) {
                return true;
            }
            else{
                toCheck.addAll(graph.getOutgoingEdges(targetVertex));
            }
        }
        return false;
    }


}
