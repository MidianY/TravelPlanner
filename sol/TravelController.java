package sol;

import com.sun.source.tree.ReturnTree;
import src.*;
import test.simple.SimpleEdge;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class
TravelController implements ITravelController<City, Transport> {
    private Dijkstra dijkstra;
    private BFS bfs;

    // Why is this field of type TravelGraph and not IGraph?
    // Are there any advantages to declaring a field as a specific type rather than the interface?
    // If this were of type IGraph, could you access methods in TravelGraph not declared in IGraph?
    // Hint: perhaps you need to define a method!
    private TravelGraph graph;

    public TravelController() {
        this.dijkstra = new Dijkstra();
        this.bfs = new BFS();
        this.graph = new TravelGraph();
    }

    @Override
    public String load(String citiesFile, String transportFile) {
        this.graph = new TravelGraph();
        TravelCSVParser parser = new TravelCSVParser();

        Function<Map<String, String>, Void> addVertex = map -> {
            this.graph.addVertex(new City(map.get("name")));
            return null; // need explicit return null to account for Void type
        };

        try {
            // pass in string for CSV and function to create City (vertex) using city name
            parser.parseLocations(citiesFile, addVertex);
        } catch (IOException e) {
            return "Error parsing file: " + citiesFile;
        }

        Function<Map<String, String>, Void> addEdge = map -> {
            this.graph.addEdge(this.graph.getCityName(map.get("origin")), new Transport(this.graph.getCityName(map.get("origin")),
                    this.graph.getCityName(map.get("destination")),
                     TransportType.fromString(map.get("type")), Double.parseDouble(map.get("price")), Double.parseDouble(map.get("duration"))));
            return null;
        };

        try{
            parser.parseTransportation(transportFile, addEdge);
        }
        catch (IOException e){
            return "Error parsing file: " + transportFile;
        }

        return "Successfully loaded cities and transportation files.";
    }

    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        Function<Transport, Double> priceCalculation = e -> e.getMinutes();
        return this.dijkstra.getShortestPath(this.graph,this.graph.getCityName(source),
                this.graph.getCityName(destination), priceCalculation);
    }

    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        Function<Transport, Double> priceCalculation = e -> e.getPrice();

        return   this.dijkstra.getShortestPath(this.graph,this.graph.getCityName(source),
                this.graph.getCityName(destination), priceCalculation);
    }

    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {

        return this.bfs.getPath(this.graph, this.graph.getCityName(source), this.graph.getCityName(destination));
    }
}
