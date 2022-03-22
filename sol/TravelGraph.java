package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * This is the TravelGraph class that represents the graph
 */
public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> vertices;

    /**
     * This is the constructor of the TravelGraph class, it instantiates the vertices hashMap, which maps
     * Names of Cities to the actual city objects.
     */
    public TravelGraph(){
        this.vertices = new HashMap<>();
    }

    /**
     * Adds a vertex to the graph.
     * @param vertex the vertex
     */
    @Override
    public void addVertex(City vertex) {
        this.vertices.put(vertex.toString(), vertex);
    }

    /**
     * Adds an edge to the graph.
     * @param origin the origin of the edge.
     * @param edge
     */
    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    /**
     * Gets a set of vertices in the graph.
     * @return
     */
    @Override
    public Set<City> getVertices() { //ask about casting the collection to a set
        return new HashSet<>(this.vertices.values());
    }

    /**
     * Gets the source of an edge.
     * @param edge the edge
     * @return
     */
    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    /**
     * Gets the target of an edge.
     * @param edge the edge
     * @return
     */
    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    /**
     * Gets the outgoing edges of a vertex.
     * @param fromVertex the vertex
     * @return
     */
    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    /**
     * takes in a name of a city and returns the actual City object.
     * @param cityName
     * @return
     */
    public City getCityName(String cityName){
      return this.vertices.get(cityName);
    }

}