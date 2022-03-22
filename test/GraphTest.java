package test;

import org.junit.Assert;
import org.junit.Test;
import sol.TravelGraph;
import src.City;
import src.Transport;
import src.TransportType;
import test.simple.SimpleEdge;
import test.simple.SimpleGraph;
import test.simple.SimpleVertex;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Your Graph method tests should all go in this class!
 * The test we've given you will pass, but we still expect you to write more tests using the
 * City and Transport classes.
 * You are welcome to write more tests using the Simple classes, but you will not be graded on
 * those.
 *
 * TODO: Recreate the test below for the City and Transport classes
 * TODO: Expand on your tests, accounting for basic cases and edge cases
 */
public class GraphTest {
    private SimpleGraph graph;

    private SimpleVertex a;
    private SimpleVertex b;
    private SimpleVertex c;
    private SimpleEdge edgeAB;
    private SimpleEdge edgeBC;
    private SimpleEdge edgeCA;
    private SimpleEdge edgeAC;

    private City boston;
    private City providence;
    private City newYork;
    private City washington;
    private City chicago;
    private TravelGraph travelGraph;

    /**
     * Constructor of the GraphTest class. It instantiates a travelController as instance variables to be used throughout
     * the tests
     */
    public GraphTest() {
        this.travelGraph = new TravelGraph();
    }

    /**
     * Creates a simple graph.
     */
    private void createSimpleGraph() {
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("A");
        this.b = new SimpleVertex("B");
        this.c = new SimpleVertex("C");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);

        // create and insert edges
        this.edgeAB = new SimpleEdge(1, this.a, this.b);
        this.edgeBC = new SimpleEdge(1, this.b, this.c);
        this.edgeCA = new SimpleEdge(1, this.c, this.a);
        this.edgeAC = new SimpleEdge(1, this.a, this.c);

        this.graph.addEdge(this.a, this.edgeAB);
        this.graph.addEdge(this.b, this.edgeBC);
        this.graph.addEdge(this.c, this.edgeCA);
        this.graph.addEdge(this.a, this.edgeAC);
    }

    public void makeComplexGraph(){
        this.boston = new City("Boston");
        this.providence = new City("Providence");
        this.newYork = new City("newYork");
        this.washington = new City("Washington");
        this.chicago = new City("Chicago");

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

    @Test
    public void testGetVertices() {
        this.createSimpleGraph();
        // test getVertices to check this method AND insertVertex
        assertEquals(this.graph.getVertices().size(), 3);
        assertTrue(this.graph.getVertices().contains(this.a));
        assertTrue(this.graph.getVertices().contains(this.b));
        assertTrue(this.graph.getVertices().contains(this.c));
    }

    @Test
    public void testGetEdgeSource() {
        this.createSimpleGraph();
        assertEquals(this.graph.getEdgeSource(this.edgeAB), this.a);
    }

    /**
     * Test that ensures that the travel graph is returning the correct number of edges that
     * extend from a city
     */
    @Test
    public void testGetOutgoingEdges(){
        this.makeComplexGraph();
        Assert.assertEquals(2, this.travelGraph.getOutgoingEdges(this.providence).size());
        Assert.assertEquals(2, this.travelGraph.getOutgoingEdges(this.newYork).size());
        Assert.assertEquals(0, this.travelGraph.getOutgoingEdges(this.chicago).size());
        Assert.assertEquals(1, this.travelGraph.getOutgoingEdges(this.washington).size());
    }

    /**
     * Test that ensures that the travel graph is returning the correct city based on the string passed
     * into the getCityName helper method
     */
    @Test
    public void testGetNames(){
        this.makeComplexGraph();
        Assert.assertEquals(this.chicago, this.travelGraph.getCityName("Chicago"));
        Assert.assertEquals(this.washington, this.travelGraph.getCityName("Washington"));
        Assert.assertEquals(this.boston, this.travelGraph.getCityName("Boston"));
    }

    /**
     * Tests whether the travel graph is returning the correct number of cities as well as testing whether
     * the hashset contain all the cities
     */
    @Test
    public void testGetVerticesComplex(){
        this.makeComplexGraph();
        assertEquals(this.travelGraph.getVertices().size(), 5);
        assertTrue(this.travelGraph.getVertices().contains(this.newYork));
        assertTrue(this.travelGraph.getVertices().contains(this.providence));
        assertTrue(this.travelGraph.getVertices().contains(this.chicago));
    }

}
