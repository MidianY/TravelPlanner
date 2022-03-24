package sol;

import src.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * TravelController class, it is where all the algorithms are controlled, and assigned to which
 * edgeWeight(cheapest route, most direct route and fastest route) is suitable each algorithm.
 */
public class TravelController implements ITravelController<City, Transport> {
    private Dijkstra dijkstra;
    private BFS bfs;
    private TravelGraph graph;

    /**
     * This is the constructor of the travelController class, a new Dijkstra, bfs, and graph are instantiated
     * to be accessible through the entire class
     */
    public TravelController() {
        this.dijkstra = new Dijkstra();
        this.bfs = new BFS();
        this.graph = new TravelGraph();
    }

    /**
     *  Loads CSVs and turns them into strings so that components of data can be accessed by the user.
     * @param citiesFile    the filename of the cities csv
     * @param transportFile the filename of the transportations csv
     * @return
     */
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

    /**
     *  Finds the fastest route in between two cities using Dijkstra algorithm
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return
     */
    @Override
    public List<Transport> fastestRoute(String source, String destination) {
        if(this.graph.getCityName(source) == null || this.graph.getCityName(destination) == null){
            throw new IllegalArgumentException("City does not exist");
        }
        else{
            Function<Transport, Double> priceCalculation = e -> e.getMinutes();
            return this.dijkstra.getShortestPath(this.graph,this.graph.getCityName(source),
                    this.graph.getCityName(destination), priceCalculation);
        }
    }

    /**
     * Finds the cheapest route in between two cities using Dijkstra algorithm
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return
     */
    @Override
    public List<Transport> cheapestRoute(String source, String destination) {
        if(this.graph.getCityName(source) == null || this.graph.getCityName(destination) == null){
            throw new IllegalArgumentException("City does not exist");
        }
        else{
            Function<Transport, Double> priceCalculation = e -> e.getPrice();

            return   this.dijkstra.getShortestPath(this.graph,this.graph.getCityName(source),
                    this.graph.getCityName(destination), priceCalculation);
        }
    }

    /**
     * Finds the most direct route in between two cities using BFS algorithm
     * @param source      the name of the source city
     * @param destination the name of the destination city
     * @return
     */
    @Override
    public List<Transport> mostDirectRoute(String source, String destination) {

        if(this.graph.getCityName(source) == null || this.graph.getCityName(destination) == null){
            throw new IllegalArgumentException("City does not exist");
        }
        else{
            return this.bfs.getPath(this.graph, this.graph.getCityName(source), this.graph.getCityName(destination));
        }
    }
}
