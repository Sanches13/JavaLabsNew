import java.util.Arrays;

public class Main {

    public static void test_constructor1() {
        MyGraph<String> graph = new MyGraph<>();
        try {
            graph.putVertex("A");
            graph.putVertex("B");
            graph.putVertex("C");
            graph.putVertex("D");
            graph.putVertex("E");
            graph.putVertex("F");

            graph.putEdge("A", "B", 10);
            graph.putEdge("C", "D", 12);
            graph.putEdge("B", "D", 1);
            graph.putNonOrientedEdge("A", "E", 5);
            graph.putNonOrientedEdge("D", "E", 7);
            System.out.println("Initial graph:");
            graph.print();

            graph.removeVertex("F");
            graph.removeEdge("B", "D");
            graph.removeNonOrientedEdge("A", "E");
            System.out.println("Graph after removing vertex F, edge between B and D, non-oriented edge between A and E");
            graph.print();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void test_constructor2() {
        MyGraph<String> graph;
        try {
            graph = new MyGraph<>("tests/test1.txt");
            System.out.println("Element [i][0] is the name of the vertex with index i");
            System.out.println("The element [i][j] is the weight of the edge with the beginning at vertex i and the end at vertex j-1, j>=1");
            System.out.println("Graph:");
            graph.print();
        } catch(Exception e) {
            System.out.println(e);
        }

    }

    public static void test_DFS() {
        MyGraph<String> graph;
        try {
            graph = new MyGraph<>("tests/test2.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("DFS, starting vertex - A");
            System.out.println(graph.DFS("A"));
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_BFS() {
        MyGraph<String> graph;
        try {
            graph = new MyGraph<>("tests/test2.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("BFS, starting vertex - A");
            System.out.println(graph.BFS("A"));
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Dijkstra() {
        MyGraph<String> graph;
        try {
            //Example from https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm
            graph = new MyGraph<>("tests/test3.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Dijkstra's algorithm, starting vertex - 1");
            System.out.println(Arrays.toString(graph.Dijkstra("1")));
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Kruskal1() {
        MyGraph<String> graph;
        try {
            //Example from https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
            graph = new MyGraph<>("tests/test4.txt");
            System.out.println("Graph:");
            graph.print();
            System.out.println("Weight of the minimum spanning tree: 39");

            System.out.println("Kruskal's algorithm");
            MyGraph<String> spanningGraph = graph.Kruskal();
            spanningGraph.print();
            System.out.println("Weight of the minimum spanning tree: " + spanningGraph.getWeight());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Prim1() {
        MyGraph<String> graph;
        try {
            //Example from https://en.wikipedia.org/wiki/Kruskal%27s_algorithm
            graph = new MyGraph<>("tests/test4.txt");
            System.out.println("Graph:");
            graph.print();
            System.out.println("Weight of the minimum spanning tree: 39");

            System.out.println("Prim's algorithm");
            MyGraph<String> spanningGraph = graph.Prim();
            spanningGraph.print();
            System.out.println("Weight of the minimum spanning tree: " + spanningGraph.getWeight());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Kruskal2() {
        MyGraph<String> graph;
        try {
            //Example from https://www.geeksforgeeks.org/wp-content/uploads/Fig-11.jpg
            //Result https://www.geeksforgeeks.org/wp-content/uploads/MST5.jpg
            graph = new MyGraph<>("tests/test5.txt");
            System.out.println("Graph:");
            graph.print();
            System.out.println("Weight of the minimum spanning tree: 37");

            System.out.println("Kruskal's algorithm");
            MyGraph<String> spanningGraph = graph.Kruskal();
            spanningGraph.print();
            System.out.println("Weight of the minimum spanning tree: " + spanningGraph.getWeight());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Prim2() {
        MyGraph<String> graph;
        try {
            //Example from https://www.geeksforgeeks.org/wp-content/uploads/Fig-11.jpg
            //Result https://www.geeksforgeeks.org/wp-content/uploads/MST5.jpg
            graph = new MyGraph<>("tests/test5.txt");
            System.out.println("Graph:");
            graph.print();
            System.out.println("Weight of the minimum spanning tree: 37");

            System.out.println("Prim's algorithm");
            MyGraph<String> spanningGraph = graph.Prim();
            spanningGraph.print();
            System.out.println("Weight of the minimum spanning tree: " + spanningGraph.getWeight());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_FloydWarshall() {
        MyGraph<String> graph;
        try {
            //Example from https://en.wikipedia.org/wiki/Floyd%E2%80%93Warshall_algorithm
            graph = new MyGraph<>("tests/test6.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Floyd-Warshall algorithm");
            int[][] result = graph.FloydWarshall();
            for(int i = 0; i < result.length; i++) {
                for(int j = 0; j < result.length; j++)
                    System.out.print(result[i][j] + " ");
                System.out.println();
            }
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Test 1: Creating a graph manually");
        test_constructor1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 2: Creating a graph using file");
        test_constructor2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 3: DFS");
        test_DFS();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 4: BFS");
        test_BFS();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 5: Dijkstra's algorithm");
        test_Dijkstra();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 6: The first test of the Kruskal's algorithm");
        test_Kruskal1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 7: The first test of the Prim's algorithm");
        test_Prim1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 8: The second test of the Kruskal's algorithm");
        test_Kruskal2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 9: The second test of the Prim's algorithm");
        test_Prim2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 10: Floyd-Warshall algorithm");
        test_FloydWarshall();
        System.out.println("________________________________________________________________________________");
    }
}
