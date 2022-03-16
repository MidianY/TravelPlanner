package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {
    private HashMap<String, City> vertices;

    public TravelGraph(){
        this.vertices = new HashMap<>();
    }

    @Override
    public void addVertex(City vertex) {
        this.vertices.put(vertex.toString(), vertex);
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        origin.addOut(edge);
    }

    @Override
    public Set<City> getVertices() { //ask about casting the collection to a set
        Set<City> cities = new HashSet<>();
        for(City city: this.vertices.values()){
            cities.add(city);
        }
        return cities;
        //return (Set)this.vertices.values();
    }

    @Override
    public City getEdgeSource(Transport edge) {
        return edge.getSource();
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        return edge.getTarget();
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        return fromVertex.getOutgoing();
    }

    public City getCityName(String cityName){ //consider an if statement
      return this.vertices.get(cityName);
    }

}