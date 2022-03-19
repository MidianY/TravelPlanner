package test;

import org.junit.Before;
import org.junit.Test;
import sol.BFS;
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

    /**
     * Creates a simple graph.
     * You'll find a similar method in each of the Test files.
     * Normally, we'd like to use @Before, but because each test may require a different setup,
     * we manually call the setup method at the top of the test.
     *
     * TODO: create more setup methods!
     */

    @Before
    public void setUp(){
        this.graph = new SimpleGraph();

        this.a = new SimpleVertex("Boston");
        this.b = new SimpleVertex("Providence");
        this.c = new SimpleVertex("NewYork");
        this.d = new SimpleVertex("NewPort");
        this.e = new SimpleVertex("Hartford");
        this.f = new SimpleVertex("DC");

        this.graph.addVertex(this.a);
        this.graph.addVertex(this.b);
        this.graph.addVertex(this.c);
        this.graph.addVertex(this.d);
        this.graph.addVertex(this.e);
        this.graph.addVertex(this.f);

        this.bfs = new BFS();
        this.travelController = new TravelController();
    }

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

    public void simpleGraph1() {
        this.graph.addEdge(this.a, new SimpleEdge(2, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.a, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(3, this.c, this.d));
        this.graph.addEdge(this.d, new SimpleEdge(100, this.d, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(2, this.e, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(5, this.b, this.f));
    }

    public void simpleGraph2() {
        this.graph.addEdge(this.a, new SimpleEdge(1, this.a, this.b));
        this.graph.addEdge(this.b, new SimpleEdge(1, this.b, this.c));
        this.graph.addEdge(this.c, new SimpleEdge(1, this.c, this.e));
        this.graph.addEdge(this.a, new SimpleEdge(100, this.a, this.f));
        this.graph.addEdge(this.f, new SimpleEdge(100, this.f, this.e));
    }

    @Test
    public void testBasicBFS() {
        this.makeSimpleGraph();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 200.0, DELTA);
        assertEquals(path.size(), 2);
    }

    @Test
    public void testBasicBFS2() {
        this.simpleGraph1();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.e);
        assertEquals(SimpleGraph.getTotalEdgeWeight(path), 104.0, DELTA);
        assertEquals(path.size(), 3);
    }

    @Test
    public void testBFSNoPath() {
        this.simpleGraph2();
        List<SimpleEdge> path = this.bfs.getPath(this.graph, this.a, this.d);
        List<SimpleEdge> emptyList = new ArrayList();
        assertEquals(path,emptyList);
    }

    @Test
    public void testBFSSameVertex(){ //what does same vertex mean
        this.simpleGraph1();
        BFS<SimpleVertex, SimpleEdge> bfs = new BFS<>();
        List<SimpleEdge> path = bfs.getPath(this.graph, this.a, this.a);
        List<SimpleEdge> sampleList = new ArrayList();
        sampleList.add(new SimpleEdge(2, this.a, this.a));
        assertEquals(path,sampleList);
    }

    @Test
    public void testBFSMostDirectRoute(){
        this.simpleGraph1();
        this.travelController.mostDirectRoute(this.a.toString(), this.d.toString());
        assertEquals(2, this.travelController.mostDirectRoute(this.a.toString(), this.d.toString()).size());
    }


    // TODO: write more tests + make sure you test all the cases in your testing plan!
}
