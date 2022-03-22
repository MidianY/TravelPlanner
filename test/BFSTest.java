package test;

import org.junit.Test;
import sol.BFS;
import src.City;
import sol.TravelGraph;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import java.util.ArrayList;
import java.util.List;
import sol.TravelController;

import static org.junit.Assert.assertEquals;

/**
 * Your BFS tests should all go in this class!
 * The test we've given you will pass if you've implemented BFS correctly, but we still expect
 * you to write more tests using the City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class BFSTest {

    private static final double DELTA = 0.001;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleVertex d;
    private SimpleVertex e;
    private SimpleVertex f;
    private SimpleGraph graph;
    private BFS<SimpleVertex, SimpleEdge> bfs;
    private TravelController travelController;
    private City boston;
    private City providence;
    private City newYork;
    private City washington;
    private City chicago;
    private TravelGraph travelGraph;
    private BFS<City, Transport> bfs2;



    /**
     * Constructor of the BFSTest class. It instantiates bfs and travelController as instance variables to be used in
     * the entire class when necessary.
     */
    public BFSTest() {
        this.bfs = new BFS();
        this.travelController = new TravelController();
        this.bfs2 = new BFS<>();
    }

    /**
     * Creates a graph basing on information from the cities3 and transport3 files we created
     */
    public void graph1(){
        this.travelController.load("data/cities3.csv", "data/transport3.csv");
    }

    /**
     * Creates a graph basing on information from the cities4 and transport4 files we created
     */
    public void graph2(){
        this.travelController.load("data/cities4.csv", "data/transport4.csv");
    }

    /**
     * Creates a basic simple graph
     */
    public void makeSimpleGraph() {
        this.graph = new SimpleGraph();
        this.a = new SimpleVertex("a");
        this.b = new SimpleVertex("b");
        this.c = new SimpleVertex("c");
        this.d = new SimpleVertex("d");
        this.e = new SimpleVertex("e");
        this.f = new SimpleVertex("f");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.d, new SimpleEdge(1, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    /**
     * Creates a complex graph
     */
    public void makeComplexGraph(){
        this.travelGraph = new TravelGraph();
        this.boston = new City("Boston");
        this.providence = new City("Providence");
        this.newYork = new City("newYork");
        this.chicago = new City("Chicago");
        this.washington = new City("Washington");

        this.travelGraph.addVertex(this.boston);
        this.travelGraph.addVertex(this.washington);
        this.travelGraph.addVertex(this.providence);
        this.travelGraph.addVertex(this.newYork);
        this.travelGraph.addVertex(this.chicago);

        this.travelGraph.addEdge(this.washington, new Transport(this.washington, this.newYork,
                TransportType.BUS, 5.0, 3.0));
        this.travelGraph.addEdge(this.boston, new Transport(this.washington, this.boston,
                TransportType.TRAIN, 3.0, 1.0));
        this.travelGraph.addEdge(this.newYork, new Transport(this.newYork, this.boston,
                TransportType.BUS, 1.0, 2.0));
        this.travelGraph.addEdge(this.providence, new Transport(this.boston, this.providence,
                TransportType.TRAIN, 6.0, 1.0));
        this.travelGraph.addEdge(this.providence, new Transport(this.providence, this.newYork,
                TransportType.BUS, 2.0, 1.0));
        this.travelGraph.addEdge(this.newYork, new Transport(this.newYork, this.chicago,
                TransportType.TRAIN, 5.0, 3.0));
    }

    /**
     * Checks whether the bfs algorithm executes as expected for complex graphs(using Transport and City classes).
     */
    @Test
    public void testGraph(){
        this.makeComplexGraph();
        List<Transport> path = this.bfs2.getPath(this.travelGraph, this.washington, this.boston);
        assertEquals(path.size(), 2);
    }

    /**
     * Creates a basic simple graph
     */
    public void makeSimpleGraph2(){
        this.graph = new SimpleGraph();
        this.a = new SimpleVertex("Boston");
        this.b = new SimpleVertex("Providence");
        this.c = new SimpleVertex("NewYork");
        this.d = new SimpleVertex("Chicago");
        this.e = new SimpleVertex("Washington");
        this.f = new SimpleVertex("Vermont");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    /**
     * Checks whether the getPath algorithm of bfs is executed as expected for the first simple graph
     */
    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * Checks whether the getPath algorithm of bfs is executed as expected for the second simple graph
     */
    @Test
    public void testBasicBFS2(){
        this.makeSimpleGraph2();
        List<SimpleEdge> path = this.bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 101.0, DELTA);
        assertEquals(path.size(), 2);
    }

    /**
     * Checks whether bfs works for obtaining the most direct route between two cities by comparing the size expected
     * and the actual size of the route for the first graph from the csv files(cities3 and transport3).
     */
    @Test
    public void testBFSMostDirectRoute1(){
        this.graph1();
        assertEquals(1, this.travelController.mostDirectRoute("Boston", "Providence").size());
    }

    /**
     * Checks whether bfs works for obtaining the most direct route between two cities by comparing the size expected
     * and the actual size of the route for the second graph from the csv files(cities4 and transport4).
     */
    @Test
    public void testBFSMostDirectRoute2(){
        this.graph2();
        assertEquals(2, this.travelController.mostDirectRoute("Providence","Boston").size());
    }

    /**
     * Checks whether the bfs getPath algorithm returns an empty list when two cities have no path for the complex graph
     */
    @Test
    public void testBFSNoPath(){
        this.makeComplexGraph();
        List<Transport> noPath = new ArrayList<>();
        assertEquals(noPath, this.bfs2.getPath(this.travelGraph, this.boston, this.providence));
        assertEquals(0, this.bfs2.getPath(this.travelGraph, this.boston, this.providence).size());
    }

//    /**
//     * Checks whether the bfs getPath algorithm returns an empty list when trying to get a path between
//     * a city and itself.
//     */
//    @Test
//    public void testBFSSameVertex(){
//        this.makeComplexGraph();
//        List<Transport> path = new ArrayList<>();
//        assertEquals(path, this.bfs2.getPath(this.travelGraph, this.boston, this.boston));
//        assertEquals(0,this.bfs2.getPath(this.travelGraph, this.boston, this.boston).size());
//    }
}
