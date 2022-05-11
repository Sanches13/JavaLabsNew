import java.util.Arrays;

public class Main {

    public static void test_Tarjan1() {
        GraphB<String> graph;
        try {
            //Example from the lecture "Algorithms on graphs - 2", page 9
            graph = new GraphB<>("tests/test1.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Tarjan's algorithm: ");
            System.out.println(graph.Tarjan());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Tarjan2() {
        GraphB<String> graph;
        try {
            //Example from the https://upload.wikimedia.org/wikipedia/commons/6/6e/Algorithm_Tarjan.png
            graph = new GraphB<>("tests/test4.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Tarjan's algorithm: ");
            System.out.println(graph.Tarjan());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Fleury1() {
        GraphB<String> graph;
        try {
            //Example from the lecture "Algorithms on graphs - 2", page 33
            graph = new GraphB<>("tests/test2.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Fleury's algorithm: ");
            System.out.println(graph.Fleury());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Fleury2() {
        GraphB<String> graph;
        try {
            //Example from the https://present5.com/presentation/1/123007399_140307126.pdf-img/123007399_140307126.pdf-16.jpg
            graph = new GraphB<>("tests/test5.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Fleury's algorithm: ");
            System.out.println(graph.Fleury());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_getEulerCycle1() {
        GraphB<String> graph;
        try {
            //Example from the lecture "Algorithms on graphs - 2", page 33
            graph = new GraphB<>("tests/test2.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Search for an Eulerian cycle in an oriented graph by an algorithm based on cycles:");
            System.out.println(graph.getEulerCycle());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_getEulerCycle2() {
        GraphB<String> graph;
        try {
            //Example from the https://present5.com/presentation/1/123007399_140307126.pdf-img/123007399_140307126.pdf-16.jpg
            graph = new GraphB<>("tests/test5.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Search for an Eulerian cycle in an oriented graph by an algorithm based on cycles:");
            System.out.println(graph.getEulerCycle());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Kosaraju1() {
        GraphB<String> graph;
        try {
            //Example from the lecture "Algorithms on graphs - 2", page 46
            graph = new GraphB<>("tests/test3.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Kosaraju's algorithm: ");
            System.out.println(graph.Kosaraju());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void test_Kosaraju2() {
        GraphB<String> graph;
        try {
            //Example from the https://slideplayer.com/slide/15954290/88/images/59/Strongly+Connected+Components.jpg
            graph = new GraphB<>("tests/test6.txt");
            System.out.println("Graph:");
            graph.print();

            System.out.println("Kosaraju's algorithm: ");
            System.out.println(graph.Kosaraju());
        } catch(Exception e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        System.out.println("Test 1: Tarjan's algorithm");
        test_Tarjan1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 2: Tarjan's algorithm");
        test_Tarjan2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 3: Fleury's algorithm");
        test_Fleury1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 4: Fleury's algorithm");
        test_Fleury2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 5: Search for an Eulerian cycle in an oriented graph by an algorithm based on cycles");
        test_getEulerCycle1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 6: Search for an Eulerian cycle in an oriented graph by an algorithm based on cycles");
        test_getEulerCycle2();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 7: Kosaraju's algorithm");
        test_Kosaraju1();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 8: Kosaraju's algorithm");
        test_Kosaraju2();
        System.out.println("________________________________________________________________________________");
    }
}
