package sol;

import src.IBFS;
import src.IGraph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class BFS<V, E> implements IBFS<V, E> {
    private HashMap<V, E> cityTransport;

    public BFS(){
        this.cityTransport = new HashMap<>();
    }


    // TODO: implement the getPath method!
    @Override
    public List<E> getPath(IGraph<V, E> graph, V start, V end) {
        if(checkExist(graph, start, end)){
            V current = graph.getEdgeSource(this.cityTransport.get(end));
            while(!current.equals(start)){
                this.cityTransport.get(current);
            }

        }

        //if the city is equal to the start
        //while loop
        //look for end node in the hashmap
        //get previous node from that edge
        //building it backwards until the node that you hit is the start node
        //if(checkExist())
        return null;
    }

    //call this helper in getPath and tells you whether a path exists, if true it exists if false throw error
    private boolean checkExist(IGraph<V, E> graph, V start, V end){
        LinkedList<E> toCheck = new LinkedList<>(graph.getOutgoingEdges(start)); //contains outgoing edges from the start
        HashSet<E> visited = new HashSet<>(graph.getOutgoingEdges(start)); //not sure

        while (!toCheck.isEmpty()){
            E checkingVertex = toCheck.removeFirst();
            V targetVertex = graph.getEdgeTarget(checkingVertex);
            if(visited.contains(start)){
                continue;
            }
            if(!this.cityTransport.containsKey(targetVertex)){
                this.cityTransport.put(targetVertex, checkingVertex);
            }
            if(end.equals(targetVertex)){
                return true;
            }
            else{
               toCheck.addAll(graph.getOutgoingEdges(targetVertex));
            }
        }
        return false;
    }


    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}
