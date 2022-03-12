package sol;

import src.City;
import src.IGraph;
import src.Transport;

import java.util.Set;

public class TravelGraph implements IGraph<City, Transport> {

    @Override
    public void addVertex(City vertex) {
        // TODO: implement this method!
    }

    @Override
    public void addEdge(City origin, Transport edge) {
        // TODO: implement this method!
    }

    @Override
    public Set<City> getVertices() {
        // TODO: implement this method!
        return null;
    }

    @Override
    public City getEdgeSource(Transport edge) {
        // TODO: implement this method!
        return null;
    }

    @Override
    public City getEdgeTarget(Transport edge) {
        // TODO: implement this method!
        return null;
    }

    @Override
    public Set<Transport> getOutgoingEdges(City fromVertex) {
        // TODO: implement this method!
        return null;
    }

    // TODO: feel free to add your own methods here!
    // hint: maybe you need to get a City by its name
}