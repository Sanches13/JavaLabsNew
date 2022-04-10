import java.io.File;
import java.util.*;

public class MyGraph<T> implements Graph<T>{
    /* To represent the graph, we use the HashMap
       The keys are the names of the vertices
       The values are lists of adjacent vertices */
    private HashMap<T, ArrayList<adjacentVertex<T>>> graph;

    //The graph can be initialized in two ways: manually and using a file
    //Constructor for initializing the graph manually
    public MyGraph() {
        graph = new HashMap<>();
    }

    //Method that adds a new vertex
    @Override
    public void putVertex(T vertexName) throws Exception{
        if(graph.containsKey(vertexName) || vertexName == null)
            throw new Exception("There is already a vertex named " + vertexName + " in the graph, or you have passed null!");
        graph.put(vertexName, new ArrayList<>());
    }

    //Method that adds a new oriented edge with a start at vertex "src" and an end at vertex "dst"
    @Override
    public void putEdge(T src, T dst, int weight) throws Exception {
        if(src == null || dst == null || weight == 0)
            throw new Exception("You have passed null or weight of the edge is 0!");
        if(!graph.containsKey(src) && !graph.containsKey(dst)) {
            graph.put(src, new ArrayList<>());
            graph.put(dst, new ArrayList<>());
        }
        else if(graph.containsKey(src) && !graph.containsKey(dst))
            graph.put(dst, new ArrayList<>());
        else if(!graph.containsKey(src) && graph.containsKey(dst))
            graph.put(src, new ArrayList<>());
        else {
            for(adjacentVertex<T> vertex : graph.get(src)){
                if(vertex.getName() == dst) {
                    vertex.setWeight(weight);
                    return;
                }
            }
        }
        graph.get(src).add(new adjacentVertex<>(dst, weight));
    }

    //Method that adds a new non-oriented edge connecting vertices "src" and "dst"
    @Override
    public void putNonOrientedEdge(T src, T dst, int weight) throws Exception {
        putEdge(src, dst, weight);
        putEdge(dst, src, weight);
    }

    //Method that removes a vertex named "vertexName"
    @Override
    public void removeVertex(T vertexName) throws Exception{
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        if(!graph.containsKey(vertexName))
            throw new Exception("A vertex with this name doesn't exist!");
        graph.remove(vertexName);
        for(T key : graph.keySet()) {
            for(adjacentVertex<T> vertex : graph.get(key)) {
                if(vertexName == vertex.getName()) {
                    graph.get(key).remove(vertex);
                    break;
                }
            }
        }
    }

    //Method that removes an edge with a start at vertex "src" and an end at vertex "dst"
    @Override
    public void removeEdge(T src, T dst) throws Exception{
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        if(src == null || dst == null)
            throw new Exception("You have passed null!");
        if(!graph.containsKey(src) || !graph.containsKey(dst))
            throw new Exception("Check for vertices in the graph!");
        for(adjacentVertex<T> vertex : graph.get(src)) {
            if(vertex.getName() == dst) {
                graph.get(src).remove(vertex);
                return;
            }
        }
        throw new Exception("There is no such edge in the graph");
    }

    //Method that removes a non-oriented edge connecting vertices "src" and "dst"
    @Override
    public void removeNonOrientedEdge(T src, T dst) throws Exception{
        removeEdge(src, dst);
        removeEdge(dst, src);
    }

    //Constructor for initializing the graph using a file
    @SuppressWarnings("unchecked")
    public MyGraph(String fileName) throws Exception {
        Scanner scanner;
        File file = new File(fileName);

        //The minimum file size is 3, because the graph must contain at least one vertex (vertex name + space + weight)
        if(!file.exists())
            throw new Exception("Your input file doesn't exist");

        if(file.length() < 3)
            throw new Exception("Your input file has the wrong format");

        scanner = new Scanner(file);
        //In the "strList" we write all the lines of the file, separated by spaces
        List<String[]> strList = new ArrayList<>();
        //The "numbersList" will contain the weights of the edges
        List<ArrayList<Integer>> numbersList = new ArrayList<>();
        //The "names" will contain the names of the vertices
        List<T> names = new ArrayList<>();

        while(scanner.hasNextLine())
            strList.add(scanner.nextLine().split("\\s"));
        scanner.close();

        for(int i = 0; i < strList.size(); i++)
            names.add((T) strList.get(i)[0]);

        for(int i = 0; i < strList.size(); i++) {
            numbersList.add(new ArrayList<>());
            for(int j = 1; j < strList.get(i).length; j++) {
                int value = Integer.parseInt(strList.get(i)[j]);
                numbersList.get(i).add(value);
            }
        }

        for(ArrayList<Integer> list : numbersList)
            if(list.size() != strList.size())
                throw new Exception("Your adjacency matrix is not square!");

        //creating a graph
        graph = new HashMap<>();
        for(int i = 0; i < numbersList.size(); i++) {
            graph.put(names.get(i), new ArrayList<>());
            for(int j = 0; j < numbersList.get(i).size(); j++)
                if(numbersList.get(i).get(j) != 0)
                    graph.get(names.get(i)).add(new adjacentVertex<>(names.get(j), numbersList.get(i).get(j)));
        }
    }

    //Method that performs the DFS algorithm
    //Name of the initial vertex: vertexName
    public ArrayList<T> DFS(T vertexName) throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        if(!graph.containsKey(vertexName))
            throw new Exception("There is no such vertex!");
        ArrayList<T> visited = new ArrayList<>();
        DFS(vertexName, visited);
        return visited;
    }

    private void DFS(T vertexName, ArrayList<T> visited) {
        visited.add(vertexName);
        for(adjacentVertex<T> vertex : graph.get(vertexName)) {
            if(!visited.contains(vertex.getName()))
                DFS(vertex.getName(), visited);
        }
    }

    //Method that performs the BFS algorithm
    //Name of the initial vertex: vertexName
    public ArrayList<T> BFS(T vertexName) throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        if(!graph.containsKey(vertexName))
            throw new Exception("There is no such vertex!");

        ArrayList<T> visited = new ArrayList<>();
        ArrayDeque<T> queue = new ArrayDeque<>();
        queue.push(vertexName);
        while (queue.size() != 0) {
            T vertex = queue.getLast();
            queue.remove(vertex);
            if (!visited.contains(vertex))
                visited.add(vertex);
            for (adjacentVertex<T> v : graph.get(vertex))
                if (!visited.contains(v.getName())) {
                    queue.push(v.getName());
                    visited.add(v.getName());
                }
        }
        return visited;
    }

    //Method that performs the Dijkstra's algorithm
    //The name of the starting vertex from which we find all the shortest paths: vertexName
    public int[] Dijkstra(T vertexName) throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        if(!graph.containsKey(vertexName))
            throw new Exception("There is no such vertex!");

        if(containsEdgeWithNegativeWeight())
            throw new Exception("The graph contains an edge with negative weight");

        HashMap<T, Boolean> visited = new HashMap<>();
        HashMap<T, Integer> lengths = new HashMap<>();

        for(T key : graph.keySet()) {
            visited.put(key, false);
            lengths.put(key, Integer.MAX_VALUE);
        }
        lengths.replace(vertexName, 0);

        while(unvisited(visited)) {
            T key = getMinUnvisited(lengths, visited);
            visited.replace(key, true);
            for (adjacentVertex<T> vertex : graph.get(key)) {
                if (lengths.get(vertex.getName()) > lengths.get(key) + vertex.getWeight())
                    lengths.replace(vertex.getName(), lengths.get(key) + vertex.getWeight());
            }
        }
        int[] result = new int[graph.size()];
        int i = 0;
        for(T key : lengths.keySet()) {
            if(lengths.get(key) == Integer.MAX_VALUE) {
                result[i] = 0;
                continue;
            }
            result[i] = lengths.get(key);
            i++;
        }
        return result;
    }

    //The method used in the Dijkstra's algorithm
    //Returns the name of a vertex that has not yet been visited, the weight of the edge to which is minimal
    private T getMinUnvisited(HashMap<T, Integer> lengths, HashMap<T, Boolean> visited) {
        T key = null;
        int min = Integer.MAX_VALUE;
        for(T nextKey : lengths.keySet()) {
            if(!visited.get(nextKey))
                if(lengths.get(nextKey) <= min) {
                    min = lengths.get(nextKey);
                    key = nextKey;
                }
        }
        return key;
    }

    //The method used in the Dijkstra's algorithm
    //Returns true until all vertices are visited
    private boolean unvisited(HashMap<T, Boolean> vertices) {
        for(T key : vertices.keySet())
            if(!vertices.get(key))
                return true;
        return false;
    }

    private boolean containsEdgeWithNegativeWeight() {
        for(T key : graph.keySet())
            for(adjacentVertex<T> vertex : graph.get(key))
                if (vertex.getWeight() < 0)
                    return true;
        return false;
    }

    //Method that performs the Prim's algorithm
    //Returns a graph that is a spanning tree
    public MyGraph<T> Prim() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        MyGraph<T> currentGraph = new MyGraph<>();
        for(T key : graph.keySet()) {
            currentGraph.graph.put(key, new ArrayList<>());
            break;
        }
        Edge<T> edge;

        while((edge = getMinEdge(currentGraph)).getWeight() != Integer.MAX_VALUE) {
            if(currentGraph.graph.containsKey(edge.getSrc()))
                currentGraph.graph.put(edge.getDst(), new ArrayList<>());
            else
                currentGraph.graph.put(edge.getSrc(), new ArrayList<>());
            currentGraph.graph.get(edge.getSrc()).add(new adjacentVertex<>(edge.getDst(), edge.getWeight()));
        }
        return currentGraph;
    }

    //The method used in the Prim's algorithm
    //Returns the minimum edge whose beginning or end belongs to the current graph
    private Edge<T> getMinEdge(MyGraph<T> currentGraph) {
        Edge<T> minEdge = new Edge<>(null, null, Integer.MAX_VALUE);
        for(T key : graph.keySet()) {
            if(currentGraph.graph.containsKey(key)) {
                for(adjacentVertex<T> v1 : graph.get(key))
                    if(!currentGraph.graph.containsKey(v1.getName()))
                        if(v1.getWeight() < minEdge.getWeight())
                            minEdge = new Edge<>(key, v1.getName(), v1.getWeight());
            }
            else {
                for(adjacentVertex<T> v2 : graph.get(key))
                    if(currentGraph.graph.containsKey(v2.getName()))
                        if(v2.getWeight() < minEdge.getWeight())
                            minEdge = new Edge<>(key, v2.getName(), v2.getWeight());
            }
        }
        return minEdge;
    }

    //Method that performs the Floyd-Warshall algorithm
    public int[][] FloydWarshall() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        //To perform the algorithm, initialize the adjacency matrix of the graph
        int[][] AM = toAdjecencyMatrix();
        for(int k = 0; k < AM.length; k++) {
            for (int i = 0; i < AM.length; i++)
                for (int j = 0; j < AM.length; j++)
                    if ((AM[i][k] + AM[k][j]) < AM[i][j])
                        AM[i][j] = AM[i][k] + AM[k][j];

            for (int i = 0; i < AM.length; i++)
                if(AM[i][i] < 0)
                    throw new Exception("There is a negative weight cycle in the graph");
        }

        //Checking for a negative weight cycle
        //https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
        for(int i = 0; i < AM.length; i++)
            for(int j = 0; j < AM.length; j++)
                if(AM[i][j] == Integer.MAX_VALUE / 2)
                    AM[i][j] = 0;
        return AM;
    }

    //Method that performs the Kruskal's algorithm
    //Returns the minimum edge whose beginning or end belongs to the current graph
    public MyGraph<T> Kruskal() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");
        MyGraph<T> newGraph = new MyGraph<>();
        ArrayList<HashSet<T>> subGraphs = new ArrayList<>();
        ArrayList<Edge<T>> edges = new ArrayList<>();
        for(T key : graph.keySet())
            for(adjacentVertex<T> vertex : graph.get(key))
                edges.add(new Edge<>(key, vertex.getName(), vertex.getWeight()));
        edges = sortEdges(edges);

        for(Edge<T> edge : edges) {
            if(!newGraph.graph.containsKey(edge.getSrc()) && !newGraph.graph.containsKey(edge.getDst())) {
                subGraphs.add(new HashSet<>());
                subGraphs.get(subGraphs.size() - 1).add(edge.getSrc());
                subGraphs.get(subGraphs.size() - 1).add(edge.getDst());

                newGraph.graph.put(edge.getSrc(), new ArrayList<>());
                newGraph.graph.put(edge.getDst(), new ArrayList<>());
            }

            else if(newGraph.graph.containsKey(edge.getSrc()) && !newGraph.graph.containsKey(edge.getDst())) {
                newGraph.graph.put(edge.getDst(), new ArrayList<>());
                for(HashSet<T> set : subGraphs) {
                    if(set.contains(edge.getSrc())){
                        set.add(edge.getDst());
                        break;
                    }
                }
            }

            else if(!newGraph.graph.containsKey(edge.getSrc()) && newGraph.graph.containsKey(edge.getDst())) {
                newGraph.graph.put(edge.getSrc(), new ArrayList<>());
                for(HashSet<T> set : subGraphs) {
                    if(set.contains(edge.getDst())){
                        set.add(edge.getSrc());
                        break;
                    }
                }
            }

            else {
                HashSet<T> src = null;
                HashSet<T> dst = null;
                for(HashSet<T> set : subGraphs) {
                    if(set.contains(edge.getSrc()))
                        src = set;
                    if(set.contains(edge.getDst()))
                        dst = set;
                }
                if(src == dst)
                    continue;

                for(T vertex : dst)
                    src.add(vertex);
                subGraphs.remove(dst);
            }
            newGraph.graph.get(edge.getSrc()).add(new adjacentVertex<>(edge.getDst(), edge.getWeight()));
        }
        return newGraph;
    }

    //Method that arranges all edges of the graph in non-decreasing order
    private ArrayList<Edge<T>> sortEdges(ArrayList<Edge<T>> edges) {
        ArrayList<Edge<T>> sortedEdges = new ArrayList<>();
        while(!edges.isEmpty())
            sortedEdges.add(getMinEdge(edges));
        return sortedEdges;
    }

    //Method that returns an edge with a minimum weight
    private Edge<T> getMinEdge(ArrayList<Edge<T>> edges) {
        Edge<T> minEdge = new Edge<>(null, null, Integer.MAX_VALUE);
        for(Edge<T> e : edges)
            if(e.getWeight() < minEdge.getWeight())
                minEdge = e;
        edges.remove(minEdge);
        return minEdge;
    }

    //Method that returns the adjacency matrix of a graph
    private int[][] toAdjecencyMatrix() {
        int[][] adjacencyMatrix = new int[graph.size()][graph.size()];
        ArrayList<T> vertices = new ArrayList<>();
        for(T key : graph.keySet())
            vertices.add(key);

        int i = 0;
        for(T k1 : vertices) {
            int k2 = 0;
            for(int j = 0; j < graph.size(); j++) {
                //We need to use Integer.MAX_VALUE / 2 instead of Integer.MAX_VALUE
                // as the "infinity" for the Floyd-Warshall algorithm to work correctly
                adjacencyMatrix[i][j] = Integer.MAX_VALUE / 2;
                if(k2 < graph.get(k1).size() && graph.get(k1).get(k2).getName() == vertices.get(j)) {
                    adjacencyMatrix[i][j] = graph.get(k1).get(k2).getWeight();
                    k2++;
                }
            }
            i++;
        }
        for(i = 0; i < adjacencyMatrix.length; i++)
            adjacencyMatrix[i][i] = 0;
        return adjacencyMatrix;
    }

    public int getWeight() {
        int weight = 0;
        for(T key : graph.keySet())
            for(adjacentVertex<T> vertex : graph.get(key))
                weight += vertex.getWeight();
        return weight;
    }

    //Method that displays the graph on the screen
    @Override
    public void print() {
        for(T key : graph.keySet()) {
            System.out.print(key + ": ");
            for(adjacentVertex<T> vertex : graph.get(key))
                System.out.print(key + " => " + vertex.getName() + " (" + vertex.getWeight() + "); ");
            System.out.println();
        }
        System.out.println();
    }
}

//Class describing an edge
class Edge<T> {
    private T src;
    private T dst;
    private int weight;

    public Edge(T src, T dst, int weight) {
        this.src = src;
        this.dst = dst;
        this.weight = weight;
    }

    public T getSrc() {
        return src;
    }

    public T getDst() {
        return dst;
    }

    public int getWeight() {
        return weight;
    }
}

//Class describing an adjacent vertex
class adjacentVertex<T> {
    //Name of the adjacent vertex
    private T name;
    //The weight of the edge connecting the current vertex and the adjacent one
    private int weight;

    public adjacentVertex(T name, int weight) {
        this.name = name;
        this.weight = weight;
    }

    public T getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight =  weight;
    }
}
