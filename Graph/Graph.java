import java.util.ArrayList;

public interface Graph<T> {
    //Method that adds a new vertex
    void putVertex(T vertexName) throws Exception;

    //Method that adds a new oriented edge with a start at vertex "src" and an end at vertex "dst"
    void putEdge(T src, T dst, int weight) throws Exception;

    //Method that adds a new non-oriented edge connecting vertices "src" and "dst"
    void putNonOrientedEdge(T src, T dst, int weight) throws Exception;

    //Method that removes a vertex named "vertexName"
    void removeVertex(T vertexName) throws Exception;

    //Method that removes an edge with a start at vertex "src" and an end at vertex "dst"
    void removeEdge(T src, T dst) throws Exception;

    //Method that removes a non-oriented edge connecting vertices "src" and "dst"
    void removeNonOrientedEdge(T src, T dst) throws Exception;

    //Method that displays the graph on the screen
    void print();
}
