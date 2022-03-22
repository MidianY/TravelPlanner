package test;

import org.junit.Test;
import sol.Dijkstra;
import sol.TravelController;
import sol.TravelGraph;
import src.City;
import src.IDijkstra;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import static org.junit.Assert.assertEquals;

/**
 * Your Dijkstra's tests should all go in this class!
 * The test we've given you will pass if you've implemented Dijkstra's correctly, but we still
 * expect you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class DijkstraTest {

    private static final double DELTA = 0.001;
    private SimpleGraph graph;
    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;

    private City boston;
    private City providence;
    private City newYork;
    private City washington;
    private City chicago;
    private City kentucky;
    private City durham;
    private City atlanta;
    private City texas;
    private TravelGraph travelGraph;
    private Dijkstra<City, Transport> dijkstra;
    private TravelController travelController;

    /**
     * Constructor of the DijkstraTest class. It instantiates both the dijkstra and travelController as instance variables
     * to be used throughout the tests
     */
    public DijkstraTest(){
        this.travelController = new TravelController();
        this.dijkstra = new Dijkstra<>();
    }

    /**
     * Creates a graph based on the information from the cities3 and transport3 files we created
     */
    private void graph1(){
        this.travelController.load("data/cities4.csv", "data/transport4.csv");
    }

    /**
     * Creates a graph basing on information from the cities4 and transport4 files we created
     */
    public void graph2(){
        this.travelController.load("data/cities4.csv", "data/transport4.csv");
    }

    /**
     * Creates a simple graph
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();
        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);

        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.b));
        this.graph.addEdge(this.a, new SimpleEdge(3, this.a, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.e));
        this.graph.addEdge(this.c, new SimpleEdge(6, this.c, this.b));
        this.graph.addEdge(this.c, new SimpleEdge(2, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.b));
        this.graph.addEdge(this.d, new SimpleEdge(5, this.e, this.d));
    }

    /**
     * Creates a complex graph
     */
    private void complexGraph(){
        this.travelGraph = new TravelGraph();
        this.boston = new City("Boston");
        this.providence = new City("Providence");
        this.newYork = new City("newYork");
        this.chicago = new City("Chicago");
        this.washington = new City("Washington");
        this.kentucky = new City("Kentucky");
        this.durham = new City("Durham");
        this.atlanta = new City("Atlanta");
        this.texas = new City("Texas");

        this.travelGraph.addVertex(this.boston);
        this.travelGraph.addVertex(this.washington);
        this.travelGraph.addVertex(this.providence);
        this.travelGraph.addVertex(this.newYork);
        this.travelGraph.addVertex(this.chicago);
        this.travelGraph.addVertex(this.kentucky);
        this.travelGraph.addVertex(this.durham);
        this.travelGraph.addVertex(this.atlanta);
        this.travelGraph.addVertex(this.texas);

        this.travelGraph.addEdge(this.providence, new Transport(this.providence, this.washington,
                TransportType.BUS, 5.0, 8.0));
        this.travelGraph.addEdge(this.providence, new Transport(this.providence, this.chicago,
                TransportType.TRAIN, 10.0, 2.0));
        this.travelGraph.addEdge(this.washington, new Transport(this.washington, this.newYork,
                TransportType.BUS, 6.0, 1.0));
        this.travelGraph.addEdge(this.chicago, new Transport(this.chicago, this.boston,
                TransportType.BUS, 40.0, 10.0));
        this.travelGraph.addEdge(this.chicago, new Transport(this.chicago, this.newYork,
                TransportType.BUS, 20.0, 6.0));
        this.travelGraph.addEdge(this.newYork, new Transport(this.newYork, this.boston,
                TransportType.BUS, 30.0, 6.0));
        this.travelGraph.addEdge(this.kentucky, new Transport(this.kentucky, this.chicago,
                TransportType.BUS, 15.0, 5.0));
        this.travelGraph.addEdge(this.kentucky, new Transport(this.kentucky, this.durham,
                TransportType.BUS, 30.0, 1.0));
        this.travelGraph.addEdge(this.durham, new Transport(this.durham, this.providence,
                TransportType.BUS, 15.0, 5.0));
        this.travelGraph.addEdge(this.boston, new Transport(this.boston, this.kentucky,
                TransportType.BUS, 30.0, 1.0));
        this.travelGraph.addEdge(this.atlanta, new Transport(this.atlanta, this.washington,
                TransportType.BUS, 30.0, 1.0));
    }


    @Test
    public void testSimple() {
        this.createSimpleGraph();

        IDijkstra<SimpleVertex, SimpleEdge> dijkstra = new Dijkstra<>();
        Function<SimpleEdge, Double> edgeWeightCalculation = e -> e.weight;
        // a -> c -> d -> b
        List<SimpleEdge> path =
                dijkstra.getShortestPath(this.graph, this.a, this.b, edgeWeightCalculation);
        assertEquals(6, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(3, path.size());

        // c -> d -> b
        path = dijkstra.getShortestPath(this.graph, this.c, this.b, edgeWeightCalculation);
        assertEquals(3, SimpleGraph.getTotalEdgeWeight(path), DELTA);
        assertEquals(2, path.size());
    }

    /**
     * Method tests to ensure Dijkstra's Algorithm is giving two different routes to the same destination when
     * it is testing for fastest and cheapest route
     */
    @Test
    public void testDijkstra() {
        this.graph1();
        //Providence -> Chicago -> Boston
        assertEquals(2, this.travelController.fastestRoute("Providence", "Boston").size());

        //Providence -> Washington -> Florida -> Boston
        assertEquals(3, this.travelController.cheapestRoute("Providence", "Boston").size());
    }

    /**
     * Upon using the function to calculate the edgeweight, this test considers price, the expected output is three as the cheapest route
     * involves taking three stops
     */
    @Test
    public void testDijkstraCheapestPath(){
        //Providence -> Washington -> Florida -> Boston
        this.complexGraph();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();
        assertEquals(3, this.dijkstra.getShortestPath(this.travelGraph, this.providence, this.boston, edgeWeightCalculation).size());
    }

    /**
     * Upon using the function to calculate the edgeweight, this test considers minutes, the expected output is 2 as the fastest route
     * involves taking two stops
     */
    @Test
    public void testDijkstraFastestPath(){
        //Providence -> Chicago -> Boston
        this.complexGraph();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getMinutes();
        assertEquals(2, this.dijkstra.getShortestPath(this.travelGraph, this.providence, this.boston, edgeWeightCalculation).size());
    }

    /**
     * This path is slightly more complex than the earlier test, it involves traversing through many more cities
     * if the desired route is based on the cheapest price. The path that it should take is by stopping at the cities
     *  Providence -> Chicago -> Boston -> Kentucky -> Durham
     */
    @Test
    public void test2DijkstraCheapest(){
        this.complexGraph();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getMinutes();
        assertEquals(4, this.dijkstra.getShortestPath(this.travelGraph, this.providence, this.durham, edgeWeightCalculation).size());
    }

    /**
     * The fastest path for this graph involves making the stops Providence -> Washington -> NewYork -> Boston -> Kentucky -> Durham
     * The overall edgeweights associated with price is 101. The only other path from Providence to Durham has a total edgeweight
     * of 110 and only has 4 stops thus this test accurately utilized dijkstra's algorithm.
     */
    @Test
    public void test2DijkstraFastest(){
        this.complexGraph();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getPrice();
        assertEquals(5, this.dijkstra.getShortestPath(this.travelGraph, this.providence, this.durham, edgeWeightCalculation).size());
    }


    /**
     * Ensures the algorithm returns an empty list when two cities have no path for the complex graph.
     * Looking at the graph we created there is only a path from atlanta to washington, not the other way around
     * therefore it should return an empty path of size 0
     */
    @Test
    public void testDijkstraNoPath(){
        this.complexGraph();
        List<Transport> noPath = new ArrayList<>();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getMinutes();
        assertEquals(noPath, this.dijkstra.getShortestPath(this.travelGraph, this.washington, this.atlanta, edgeWeightCalculation));
        assertEquals(0, this.dijkstra.getShortestPath(this.travelGraph, this.washington, this.atlanta, edgeWeightCalculation).size());
    }

    //fix this... test should create an error because there is no transportation method from texas to any other vertex
    @Test
    public void testException(){
        this.complexGraph();
        Function<Transport, Double> edgeWeightCalculation = e -> e.getMinutes();
        assertEquals(0, this.dijkstra.getShortestPath(this.travelGraph, this.texas, this.providence, edgeWeightCalculation).size());
    }

}
