package ex1.src;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;


public class WGraph_DS implements weighted_graph, Serializable
{
    /**
     * this class is the graph class and has an inner class for nodes
     * there are 4 parts to the graph
     * 1. a hashmap that represents all the nodes in the graph called -graph.
     * 2. a hashmap that represent all the edges in the graph- called edges- to each node there is anther hashmap within the value
     *      of the hashmap that has all the keys of his neighbors and all the wight of the edge between them.
     * 3. edgesize- keep count on the number of edges in the graph
     * 4.MC counts the number of changes that happens in the graph
     */
    private HashMap<Integer,node_info> graph;
    private HashMap<Integer,HashMap<Integer,Double>> edges;
    private int edgesize;
    private int MC;

    /**
     * this is the builder of the graph
     * starts the graph hashmap and the edges hashmap
     */
    public WGraph_DS ()
    {
        graph=new HashMap<>();
        edges=new HashMap<>();
    }

    /**
     * this function returns the node with this given key
     * @param key - the node_id
     * @return the node, if this node is not in the graph return null
     */
    @Override
    public node_info getNode(int key)
    {
        if(graph.get(key) != null){
            return graph.get(key);
        }
        return null;
    }

    /**
     * this function check to see if there is an edge between two given keys (nodes)
     * @param node1- the first node
     * @param node2-the second node
     * @return true if there is an edge false if there is not an edge or one of
     * the nodes is not in the graph
     */
    @Override
    public boolean hasEdge(int node1, int node2)
    {
        if (graph.get(node1)!=null && graph.get(node2) !=null)
        {
            if (edges.get(node1).get(node2) != null && edges.get(node2).get(node1) != null) {
                return true;
            }
        }
        return false;
    }

    /**
     * this function gets the edge between two nodes
     * @param node1 the first node
     * @param node2-the second node
     * @return the edge that there is between the nodes if they have an edge
     * and if there is no edge returns -1.
     */
    @Override
    public double getEdge(int node1, int node2)
    {
        if (edges.get(node1).containsKey(node2))
        {
            return edges.get(node1).get(node2);
        }
        return -1;
    }

    /**
     * this function adds a node to the graph if he is not there already
     * and create for him a line in edges with a empty hashmap for his neighbors
     * @param key- the key of the wanted node
     */

    @Override
    public void addNode(int key)
    {
        if (!graph.containsKey(key))
        {
            node_info n = new NodeInfo(key);
            graph.put(key, n);
            HashMap<Integer, Double> neighbors = new HashMap<Integer, Double>();
            edges.put(key, neighbors);
            MC++;
        }
    }

    /**
     * this function connects two nodes with a given wight, if there is already an edge
     * between this two nodes its checks if the w is different from the wight there is
     * now and if so it changes the wight to the given w
     * if one of the nodes is not in the graph this function does nothing
     * @param node1 the first node
     * @param node2-the second node
     * @param w- the weight of the edge
     */
    @Override
    public void connect(int node1, int node2, double w)
    {
        if(node1 == node2 |w<0)
        {
            return;
        }
        if (!graph.containsKey(node1) & !graph.containsKey(node2)){return;}
        if (edges.get(node1).containsKey(node2))
        {
            if (edges.get(node1).get(node2)!=w)
            {
                edges.get(node1).replace(node2, w);
                edges.get(node2).replace(node1,w);
                MC++;
            }
        }
        else
        {
            edges.get(node1).put(node2, w);
            edges.get(node2).put(node1, w);
            edgesize++;
            MC++;
        }

    }

    /**
     * this function returns all the nodes in the graph
     * @return an arraylist contains all the nodes in the graph
     */
    @Override
    public Collection<node_info> getV() {
        return graph.values();
    }

    /**
     * this function returns all the neighbors of a given node
     * @param node_id- the node we want his neighbors
     * @return ArrayList of all the neighbors of the node
     */
    @Override
    public Collection<node_info> getV(int node_id)
    {
        if (edges.get(node_id) == null || graph.get(node_id) ==null)
        {
            return null;
        }
        ArrayList<node_info> ni = new ArrayList<node_info>();
        for (int key: edges.get(node_id).keySet())
        {
          ni.add(graph.get(key));
        }
        return ni;
    }

    /**
     * this function removes a given node from the graph
     * this mean we go to all the neighbors of the given node and delete the given node
     * from their list of neighbors and then deletes the node ftom the graph and the
     * edges list
     * @param key-the key of the node we want to delete
     * @return the deleted node
     */
    @Override
    public node_info removeNode(int key)
    {
        if (graph.get(key)==null)
        {
            return null;
        }
        if (edges.containsKey(key))
        {
            for (int k : edges.get(key).keySet()) {
                edges.get(k).remove(key);
                edgesize--;
                MC++;
            }
        }
        edges.remove(key);
        MC++;
        node_info n = graph.get(key);
        graph.remove(key);
        return n;
    }

    /**
     * this function removes the edge between two nodes of there is an edge between them
     * @param node1 the first node
     * @param node2-the second node
     */
    @Override
    public void removeEdge(int node1, int node2)
    {
        if (edges.get(node1).get(node2)!=null)
        {
            edges.get(node1).remove(node2);
            edges.get(node2).remove(node1);
            edgesize--;
        }
    }

    /**
     * this function returns the number of nodes in the graph
     * @return number of nodes in graph
     */
    @Override
    public int nodeSize() {
        return graph.size();
    }

    /**
     * this function returns the number of edges in the graph
     * @return number of edges in the graph
     */
    @Override
    public int edgeSize() {
        return edgesize;
    }

    /**
     * this function returns the number of changes that happend in the graph
     * @return MC-number of changes
     */
    @Override
    public int getMC() {
        return MC;
    }

    /**
     * implements the funtion equals for our graph
     * two graph will be equals if they have the same nodes and the same edges with the same wight
     * @param obj-the second graph
     * @return true if this graph is equal to our graph
     *          false if not
     */

    @Override
    public boolean equals(Object obj)
    {
        weighted_graph g = (weighted_graph) obj;
        Iterator<node_info> nodesA = getV().iterator();
        Iterator<node_info> nodesB = g.getV().iterator();
        while (nodesA.hasNext() && nodesB.hasNext())
        {
            node_info a =nodesA.next();
            node_info b =nodesB.next();
            if (a.equals(b) == false)
            {
                return false;
            }
            if (getV(a.getKey()) !=null)
            {
                if (g.getV(b.getKey()) ==null)
                {
                    return false;
                }
                Iterator<node_info> neighborsA = getV(a.getKey()).iterator();
                Iterator<node_info> neighborsB = g.getV(b.getKey()).iterator();
                while (neighborsA.hasNext()&& neighborsB.hasNext())
                {
                    node_info neiA= neighborsA.next();
                    node_info neiB= neighborsB.next();
                    if (neiA.equals(neiB) == false)
                    {
                        return false;
                    }
                    if (getEdge(a.getKey(), neiA.getKey()) != g.getEdge(b.getKey(), neiB.getKey()))
                    {
                        return false;
                    }
                }
            }
        }
        return true;

    }

    /**
     * this inner class represents the node of the graph
     */

    private class NodeInfo implements node_info, Comparable, Serializable
    {
        /**
         * every node have 3 parts
         * 1. key- the nodes id
         * 2. info- this is where we will write if the node has bean vist or not in
         *          the function DJ in WGraph_Algo later.
         * 3.tag- this will save the wight of the node from a certion node ,this will also
         *          be used in the WGraph_Algo
         */
        private int key;
        private String info;
        private double tag;

        /**
         * a builder for the node
         * @param k-the key
         */
        public NodeInfo(int k)
        {
            key= k;
        }

        /**
         * this function returns the key of the node
         * @return key
         */
        @Override
        public int getKey() {
            return key;
        }

        /**
         * this function returns the info of the node
         * @return String-info
         */
        @Override
        public String getInfo() {
            return info;
        }

        /**
         * this function sets the nodes info with a given String
         * @param s-the new info
         */
        @Override
        public void setInfo(String s)
        {
            info =s;
        }

        /**
         * this function return the nodes tag
         * @return double tag
         */
        @Override
        public double getTag() {
            return tag;
        }

        /**
         * this function sets the nodes tag with a given value
         * @param t - the new value of the tag
         */
        @Override
        public void setTag(double t) {
            tag=t;
        }

        /**
         * this function compers two nodes tags
         * @param o-the given node
         * @return -1 if the given nodes tag is greater then our nodes tag
         *          1 if our nodes tag is greater then the given tag
         *          0 if they are the same
         */
        @Override
        public int compareTo(Object o)
        {
            node_info n = (node_info) o;
            if (n.getTag()>tag)
                return -1;
            if (n.getTag()<tag)
                return 1;
            return 0;
        }

        /**
         * this function checks if two nodes ar the same
         * @param obj-the given node
         * @return true if they have the same key
         */
        @Override
        public boolean equals(Object obj) {
            node_info n = (node_info) obj;
            if (key == n.getKey())
                return true;
            return false;
        }
    }
    
}
