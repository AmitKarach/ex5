package ex1.src;

import java.io.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

/**
 * this class is all the algoritems we can do with a graph
 */
public class WGraph_Algo implements weighted_graph_algorithms, Serializable
{
    weighted_graph graph;

    /**
     * the builder of the WGraph_Algo
     * gets a weighted_graph and does a shallow copy to our graph
     * @param g- a weighted_graph to init our graph with
     */
    @Override
    public void init(weighted_graph g)
    {
        graph =g;
    }

    /**
     * this function returns the graph
     * @return weighted_graph-graph
     */
    @Override
    public weighted_graph getGraph() {
        return graph;
    }

    /**
     * this function does a deep copy to our graph to a new graph
     * @return the new graph
     */
    @Override
    public weighted_graph copy() {
        WGraph_DS newGraph =new WGraph_DS();
        for (node_info n: graph.getV())
        {
            newGraph.addNode(n.getKey());
        }
        for (node_info n: graph.getV())//going through all the nodes in the graph
        {
            for (node_info ni: graph.getV(n.getKey()))//going through all the neighbors of the node
            {
                newGraph.connect(n.getKey(),ni.getKey(),graph.getEdge(n.getKey(),ni.getKey()));//creating a connection in newGraph
            }
        }
        return newGraph;
    }

    /**
     * this function checks if all the nodes of the graph are connected
     * after using the DJ function this function goes over every node and
     * checks if his tag is not Integer.Max_Value because the function DJ
     * goes over all the nodes that are connected to a certion node and give them
     * tag that, if DJ dident give this node a tag that means he is not connected to
     * the node and that means that the graph is not connected
     * @return true if the graph is all connected
     */
    @Override
    public boolean isConnected() {
        if (graph.getV()==null ||graph.getV().size()==0 || graph.getV().size()==1)
        {
            return true;
        }
        Iterator<node_info> nodes = graph.getV().iterator();
        while (nodes.hasNext())
        {
            node_info start= nodes.next();;
            if (start !=null)
            {
                DJ(start);
            }
            for (node_info n: graph.getV())
            {
                if (n.getTag()==Integer.MAX_VALUE)
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    /**
     * this function returns the shortest distance between this two nodes
     * using the DJ function that does that
     * @param src - start node
     * @param dest - end (target) node
     * @return the smallest wight of all the edges between them
     */
    @Override
    public double shortestPathDist(int src, int dest) {
      if (isConnected()==false)
      {
          return -1;
      }
        DJ (graph.getNode(src));
        return graph.getNode(dest).getTag();
    }

    /**
     * this function returns all the nodes that are in the smallest path
     * between this nodes (when src is the first in the list and dest is the last)
     * using the DJ function to map the graph and shortestPath that gets only the dest
     * node (node_info)
     * @param src - start node
     * @param dest - end (target) node
     * @return a LinkedList contains all the nodes in the smallest path between this two nodes
     * returns null if there is no path or if there are no nodes like that in the graph
     */
    @Override
    public List<node_info> shortestPath(int src, int dest) {
        if (graph.getNode(src)==null ||graph.getNode(dest)==null)
        {
            return null;
        }
        if (src ==dest)
        {
            List<node_info> re= new LinkedList<>();
            re.add(graph.getNode(src));
            return re;
        }
        DJ (graph.getNode(src));
        if (graph.getNode(dest).getTag() ==Integer.MAX_VALUE)
        {
            return null;
        }
        return shortestPath(graph.getNode(dest));
    }

    /**
     * saves the graph (object) to computer in the given file name
     * @param file - the file name (may include a relative path).
     * @return true if it could save the graph
     */
    @Override
    public boolean save(String file) {
        try {
            FileOutputStream fileOut = new FileOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(graph);
            out.close();
            fileOut.close();
            return true;
        } catch (IOException i) {
            i.printStackTrace();
            return false;
        }

    }

    /**
     * load a graph (object) from a given file name in the computer
     * @param file - file name
     * @returntrue if it could load
     */
    @Override
    public boolean load(String file) {
        try {
            FileInputStream fileIn = new FileInputStream(file);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            graph = (WGraph_DS) in.readObject();
            in.close();
            fileIn.close();
            return true;
        } catch (IOException | ClassNotFoundException i) {
            i.printStackTrace();
            return false;
        }
    }

    /**
     * this function starts by giving all the nodes in the graph tag= Integer.Max_Value and changing all the nodes info
     * to "unvisited"
     * after that he gives the given starting node tag value of 0 and chang his info to "visited" and the goes through all the
     * neighbors of each node starting with our starting node and giving them the min distance between them and the
     * starting node (the distance is the smallest wight of edges between them and the starting node)
     * after standing on a node we will change his info to "visited" which means we found the shortest path to him and
     * we dont need to check for a smaller path for him down the road.
     * i am using here a PriorityQueue
     * @param s- the starting node
     */

    private void DJ(node_info s)
    {
        Iterator<node_info> nodes = graph.getV().iterator();
        while (nodes.hasNext())
        {
            node_info n = nodes.next();
            n.setTag(Integer.MAX_VALUE);
            n.setInfo("unvisited");

        }
        PriorityQueue<node_info> queue = new PriorityQueue<node_info>();
        queue.add(s);
        s.setTag(0);
        s.setInfo("visited");

        while (!queue.isEmpty())
        {
            node_info current= queue.poll();
            current.setInfo("visited");
            if (graph.getV(current.getKey()) != null)
            {
                for (node_info ni : graph.getV(current.getKey())) {
                    double dis = graph.getEdge(current.getKey(), ni.getKey()) + current.getTag();
                    if (dis < ni.getTag() && ni.getInfo() == "unvisited") {
                        ni.setTag(dis);
                        queue.add(ni);

                    }
                }
            }

        }
    }

    /**
     * this function creates a LinkedList and works the way back from the given node to the src node
     * by checking if the nodes tag is equal to a neighbor + the edge between them if so we put the neighbor inthe list
     * and checking his neighbors until we get to the src node
     * this is the shortest route to the node because of the DJ function
     * @param dest- the destination
     * @return LinkedList with the smallest route to the node
     */
    public List<node_info> shortestPath(node_info dest)
    {

        List <node_info> shorti = new LinkedList<>();
        shorti.add(dest);
        node_info current=dest;

        while (current.getTag()>0)
        {
            Iterator<node_info> neighbors = graph.getV(current.getKey()).iterator();
            node_info next = neighbors.next();
            while (neighbors.hasNext() && current.getTag() != next.getTag()+graph.getEdge(current.getKey(),next.getKey()))
            {
                next=neighbors.next();
            }
            current=next;
            shorti.add(0,next);
        }
        return shorti;
    }

}

