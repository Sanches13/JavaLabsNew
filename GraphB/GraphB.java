import java.io.File;
import java.util.*;

public class GraphB<T> implements Graph<T>{
    /* To represent the graph, we use the HashMap
           The keys are the names of the vertices
           The values are lists of adjacent vertices */
    private HashMap<T, ArrayList<adjacentVertex<T>>> graph;

    //The graph can be initialized in two ways: manually and using a file
    //Constructor for initializing the graph manually
    public GraphB() {
        graph = new HashMap<>();
    }

    public GraphB(GraphB<T> copy) {
        graph = new HashMap<>();
        for(T src : copy.graph.keySet()) {
            graph.put(src, new ArrayList<>());
            for (adjacentVertex<T> dst : copy.graph.get(src))
                graph.get(src).add(new adjacentVertex<>(dst.getName(), dst.getWeight()));
        }
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
    public GraphB(String fileName) throws Exception {
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

    //Method that performs the Tarjan's algorithm
    //Returns a stack with a sorted sequence of vertices
    public Stack<T> Tarjan() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");

        HashMap<T, Boolean> visited = new HashMap<>();
        Stack<T> stack = new Stack<>();
        for(T key : graph.keySet())
            visited.put(key, false);

        for(T key : graph.keySet())
            if(!visited.get(key))
                invDFS(key, visited, stack);

        Collections.reverse(stack);
        return stack;
    }

    //Checking that the graph can contain an Eulerian cycle (Number of incoming edges == number of outgoing edges)
    private boolean canContainEulerianCycle() {
        for(T vertexName : graph.keySet()) {
            int numOfIncomingEdges = 0;
            for(T src : graph.keySet())
                if(src != vertexName)
                    for(adjacentVertex<T> dst : graph.get(src))
                        if (vertexName == dst.getName()) {
                            numOfIncomingEdges++;
                            break;
                        }
            if(numOfIncomingEdges != graph.get(vertexName).size())
                return false;
        }
        return true;
    }

    //Method that searches for an Eulerian cycle in an oriented graph by Fleury's algorithm based
    //Returns a vector with the vertex traversal order and null if the Eulerian cycle doesn't exist
    public ArrayList<T> Fleury() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");

        if(!canContainEulerianCycle())
            return null;

        ArrayList<T> result = new ArrayList<>();
        GraphB<T> currentGraph = new GraphB<>(this);
        T currentVertex = getStartVertex();

        result.add(currentVertex);
        Fleury(currentGraph, currentVertex, result);

        //Check that we have bypassed all the edges
        if(result.size() != (getNumOfEdges() + 1))
            result = null;

        return result;
    }

    private void Fleury(GraphB<T> currentGraph, T currentVertex, ArrayList<T> result) throws Exception {
        GraphB<T> graphCopy = new GraphB<>(currentGraph);
        if(graphCopy.graph.get(currentVertex).size() == 0)
            return;
        for(adjacentVertex<T> dst : graphCopy.graph.get(currentVertex)) {
            if(currentGraph.graph.get(currentVertex).size() == 1 || !isBridge(new Edge<>(currentVertex, dst.getName(), dst.getWeight()), currentGraph)) {
                result.add(dst.getName());
                currentGraph.removeEdge(currentVertex, dst.getName());
                currentVertex = dst.getName();
                Fleury(currentGraph, currentVertex, result);
                return;
            }
        }
    }

    //Method that returns the starting vertex to search for an Eulerian cycle
    private T getStartVertex() {
        T vertexName = null;
        for(T key : graph.keySet()) {
            vertexName = key;
            break;
        }
        return vertexName;
    }

    //Method that returns the number of edges of a graph
    private int getNumOfEdges() {
        int numOfEdges = 0;
        for(T src : graph.keySet())
            for(adjacentVertex<T> dst : graph.get(src))
                numOfEdges++;
        return numOfEdges;
    }

    //Method that checks whether an edge is a bridge
    private boolean isBridge(Edge<T> edge, GraphB<T> currentGraph) throws Exception {
        int firstLength = currentGraph.DFS(edge.getSrc()).size();
        currentGraph.removeEdge(edge.getSrc(), edge.getDst());

        int secondLength = currentGraph.DFS(edge.getDst()).size();
        currentGraph.putEdge(edge.getSrc(), edge.getDst(), edge.getWeight());

        //If the number of connectivity components has changed when deleting an edge, then the edge is a bridge
        return firstLength != secondLength;
    }

    //Method that searches for an Eulerian cycle in an oriented graph by an algorithm based on cycles
    //Returns a vector with the vertex traversal order and null if the Eulerian cycle doesn't exist
    public ArrayList<T> getEulerCycle() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");

        if(!canContainEulerianCycle())
            return null;

        ArrayList<T> result = new ArrayList<>();
        T currentVertex = getStartVertex();
        GraphB<T> currentGraph = new GraphB<>(this);

        getEulerCycle(currentVertex, result, currentGraph);
        Collections.reverse(result);

        //Check that we have bypassed all the edges
        if(result.size() != (getNumOfEdges() + 1))
            result = null;

        return result;
    }

    private void getEulerCycle(T currentVertex, ArrayList<T> result, GraphB<T> currentGraph) throws Exception {
        Stack<T> eulerCycle = new Stack<>();
        eulerCycle.push(currentVertex);
        while(!eulerCycle.isEmpty()) {
            currentVertex = eulerCycle.peek();
            if(currentGraph.graph.get(currentVertex).size() == 0) {
                result.add(currentVertex);
                eulerCycle.pop();
            }
            else {
                T newVertex = currentGraph.graph.get(currentVertex).get(0).getName();
                currentGraph.removeEdge(currentVertex, newVertex);
                eulerCycle.push(newVertex);
            }
        }
    }

    //Method that returns a graph with inverted edges
    private GraphB<T> getInvertedGraph() throws Exception {
        GraphB<T> invertedGraph = new GraphB<>();
        for(T src : graph.keySet())
            for(adjacentVertex<T> dst : graph.get(src))
                invertedGraph.putEdge(dst.getName(), src, dst.getWeight());
        return invertedGraph;
    }

    //Method that performs the Kosaraju's algorithm
    //Returns a vector with components of strong connectivity
    public ArrayList<ArrayList<T>> Kosaraju() throws Exception {
        if(graph.isEmpty())
            throw new Exception("Your graph is empty!");

        GraphB<T> invertedGraph = getInvertedGraph();
        Stack<T> stack = new Stack<>();

        HashMap<T, Boolean> visited = new HashMap<>();
        for(T key : graph.keySet())
            visited.put(key, false);

        for(T key : invertedGraph.graph.keySet())
            if(!visited.get(key))
                invDFS(key, visited, stack);
        Collections.reverse(stack);

        ArrayList<ArrayList<T>> result = new ArrayList<>();

        for(T key : graph.keySet())
            visited.put(key, false);

        while(!stack.isEmpty()) {
            T vertexName = stack.pop();
            if(!visited.get(vertexName))
                result.add(DFS(vertexName, visited, new ArrayList<>()));
        }
        return result;
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

    //Method that performs the DFS algorithm
    private ArrayList<T> DFS(T vertexName) {
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

    private ArrayList<T> DFS(T vertexName, HashMap<T, Boolean> visited, ArrayList<T> result) {
        visited.replace(vertexName, true);
        result.add(vertexName);
        for(adjacentVertex<T> vertex : graph.get(vertexName))
            if(!visited.get(vertex.getName()))
                DFS(vertex.getName(), visited, result);
        return result;
    }

    //The DFS algorithm, with which the exit time from each vertex is fixed
    private void invDFS(T vertexName, HashMap<T, Boolean> visited, Stack<T> result) {
        visited.replace(vertexName, true);
        for(adjacentVertex<T> vertex : graph.get(vertexName)) {
            if(!visited.get(vertex.getName()))
                invDFS(vertex.getName(), visited, result);
        }
        result.push(vertexName);
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

    public void setSrc(T src) {
        this.src = src;
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

