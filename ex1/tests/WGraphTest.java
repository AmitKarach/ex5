package ex1.tests;

import ex1.src.WGraph_Algo;
import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class WGraphTest
{
    @Test
    void Connect ()
    {
        weighted_graph graph = accurateGraph();
        graph.connect(0,1,1);
        graph.connect(0,1,2);
        double d= graph.getEdge(0,1);
        assertEquals(2,d);
        graph.removeEdge(0,1);
        graph.removeEdge(0,1);
        d= graph.getEdge(0,1);
        assertEquals(-1,d);
    }

   @Test
   void edgeSize()
   {
       weighted_graph graph =accurateGraph();
       int edgeSize= graph.edgeSize();

       graph.removeEdge(0,10);
       graph.removeEdge(5,9);
       graph.removeEdge(5,9);
       assertEquals(edgeSize-1, graph.edgeSize());

   }
    @Test
    void isConnected ()
    {
        WGraph_DS graph = new WGraph_DS();
        for (int i=0; i<10;i++)
        {
            graph.addNode(i);
        }
        WGraph_Algo g =new WGraph_Algo();
        g.init(graph);
        assertFalse(g.isConnected());
        for (int i=1; i<10; i++)
        {
            graph.connect(0,i,1.1);
        }
        g.init(graph);
        assertTrue(g.isConnected());
        graph.removeEdge(0,1);
        g.init(graph);
        assertFalse(g.isConnected());
    }

    @Test
    void shortestPathD ()
    {

        WGraph_Algo g =new WGraph_Algo();
        g.init(accurateGraph());

        double d =g.shortestPathDist(0,9);
        assertEquals(d, 12);

    }

    @Test
    void shortestPath ()
    {
        WGraph_Algo g =new WGraph_Algo();
        g.init(accurateGraph());

        List<node_info> get= g.shortestPath(0,9);
        int [] check ={0,1,5,9};
        int i=0;
        for (node_info n:get)
        {
            assertEquals(n.getKey(),check[i]);
            i++;
        }
    }

    @Test
    void saveLoad()
    {
        WGraph_DS graph = createGraph(100,400,1);
        WGraph_Algo g1 = new WGraph_Algo();
        g1.init(graph);
        assertTrue(g1.save("graph.obj"));
        WGraph_Algo g2 = new WGraph_Algo();
        assertTrue(g2.save("graph.obj"));
    }



    private WGraph_DS createGraph (int v, int e, int seed)
    {
        WGraph_DS graph = new WGraph_DS();
        Random rand = new Random(seed);
        for (int i=0; i<v;i++)
        {
            graph.addNode(i);
        }
        while (graph.edgeSize()<e)
        {
            int a = rand.nextInt(v);
            int b = rand.nextInt(v);
            double w = rand.nextDouble()*10;
            graph.connect(a,b,w);
        }
        return graph;
    }
    //
    private WGraph_DS accurateGraph ()
    {
        WGraph_DS graph = new WGraph_DS();
        for (int i=0; i<11;i++)
        {
            graph.addNode(i);
        }
        graph.connect(0,1,1);
        graph.connect(0,2,2);
        graph.connect(0,3,3);

        graph.connect(1,4,17);
        graph.connect(1,5,1);
        graph.connect(2,4,1);
        graph.connect(3, 5,10);
        graph.connect(3,6,100);
        graph.connect(5,7,1.1);
        graph.connect(6,7,10);
        graph.connect(7,10,2);
        graph.connect(6,8,30);
        graph.connect(8,10,10);
        graph.connect(4,10,30);
        graph.connect(3,9,10);
        graph.connect(8,10,10);
        graph.connect(5,9,10);

        return graph;
    }
}