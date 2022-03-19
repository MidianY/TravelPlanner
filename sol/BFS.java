package sol;
import src.IBFS;
import src.IGraph;
import java.util.*;

public class BFS<V, E> implements IBFS<V, E> {
    private HashMap<V, E> cityTransport;

    public BFS(){
        this.cityTransport = new HashMap<>();
    }

    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        List<E> path = new ArrayList<>();
        if(checkPath(graph, start, end)){
                path.add(this.cityTransport.get(end));
            V current = graph.getEdgeSource(this.cityTransport.get(end));
            while(!current.equals(start)){
                path.add(this.cityTransport.get(current));
                current = graph.getEdgeSource(this.cityTransport.get(current));
            }
        }
        System.out.println(path);
        return path;
    }

    //call this helper in getPath and tells you whether a path exists, if true it exists if false throw error
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
            this.cityTransport.put(targetVertex, checkingEdge);
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
