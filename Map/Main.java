import java.util.Iterator;

public class Main {

    //using the tree from example №1 of adding an element from the lecture on AVL-trees
    public static void test_add() {
        Map<Integer, Integer> map = new Map<>();
        map.add(10, 10);
        map.add(7, 7);
        map.add(15, 15);
        map.add(5, 5);
        map.add(8, 8);
        map.add(12, 12);

        //Display all nodes with their parent and height
        System.out.println("Initial tree");
        map.print();

        //Add new element "13"
        map.add(13, 13);

        System.out.println("Tree after adding element 13");
        map.print();

    }

    //using the tree from example №2 of removing an element from the lecture on AVL-trees
    public static void test_remove() {
        Map<Integer, Integer> map = new Map<>();
        map.add(10, 10);
        map.add(7, 7);
        map.add(17, 17);
        map.add(4, 4);
        map.add(9, 9);
        map.add(15, 15);
        map.add(20, 20);
        map.add(1, 1);
        map.add(6, 6);
        map.add(8, 8);
        map.add(19, 19);
        map.add(5, 5);

        //Display all nodes with their parent and height
        System.out.println("Initial tree");
        map.print();

        //Remove element "7"
        map.remove(7);

        System.out.println("Tree after removing element 7");
        map.print();
    }

    public static void test_copy_constructor() {
        Map<Integer, Integer> firstMap = new Map<>();
        firstMap.add(10, 10);
        firstMap.add(7, 7);
        firstMap.add(17, 17);
        firstMap.add(4, 4);
        firstMap.add(9, 9);
        firstMap.add(15, 15);
        firstMap.add(20, 20);
        firstMap.add(1, 1);
        firstMap.add(6, 6);
        firstMap.add(8, 8);
        firstMap.add(19, 19);
        firstMap.add(5, 5);

        //Display all nodes with their parent and height
        System.out.println("First map");
        firstMap.print();

        Map<Integer, Integer> secondMap = new Map<>(firstMap);
        System.out.println("Second map after using copy constructor");
        secondMap.print();
    }

    public static void test_is_empty() {
        Map<String, String> map = new Map<>();
        System.out.println("Map after initialization");
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.add("Key example", "Data example");
        System.out.println("Map after adding data");
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.remove("Key example");
        System.out.println("Map after removing data");
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());
    }

    public static void test_get(){
        Map<Integer, String> map = new Map<>();
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes with their parent and height
        System.out.println("Initial tree");
        map.print();

        System.out.println("Data with key 5: " + map.get(5));
        System.out.println("Data with key 15: " + map.get(15));
        System.out.println("Data with key 8: " + map.get(8));
        System.out.println("Data with key 1000: " + map.get(1000));
    }

    public static void test_change(){
        Map<Integer, String> map = new Map<>();
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes with their parent and height
        System.out.println("Initial tree");
        map.print();

        try {
            map.changeByKey(8, "New value");
        } catch(Exception e) {
            System.out.println(e);
        }

        System.out.println("Map after changing element with key 8");
        map.print();
    }

    public static void test_clear(){
        Map<Integer, String> map = new Map<>();
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes with their parent and height
        System.out.println("Initial map");
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.clear();
        System.out.println("Map after removing all elements");
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());
    }

    public static void test_contains(){
        Map<Integer, String> map = new Map<>();
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes with their parent and height
        System.out.println("Initial map");
        map.print();

        System.out.println("Data with key 5 exists: " + map.contains(5));
        System.out.println("Data with key 100 exists: " + map.contains(100));
        System.out.println("Data with key 12 exists: " + map.contains(12));
    }

    public static void test_iterator() {
        Map<Integer, Integer> map = new Map<>();
        map.add(10, 10);
        map.add(7, 7);
        map.add(15, 15);
        map.add(5, 5);
        map.add(8, 8);
        map.add(12, 12);
        System.out.println("Initial map");
        map.print();

        Iterator<Node<Pair<Integer, Integer>>> iter = map.iterator();
        System.out.println("Display all data by their keys, obtained using an iterator");
        while(iter.hasNext()) {
            Node<Pair<Integer, Integer>> current = iter.next();
            System.out.print(current.getData() + " ");
        }
        System.out.println();
    }

    public static void test_remove_iterator() {
        Map<Integer, Integer> map = new Map<>();
        map.add(10, 10);
        map.add(7, 7);
        map.add(15, 15);
        map.add(5, 5);
        map.add(8, 8);
        map.add(12, 12);
        System.out.println("Initial map");
        map.print();

        Iterator<Node<Pair<Integer, Integer>>> iter = map.iterator();
        System.out.println("Remove all elements using the iterator method remove()");
        while(iter.hasNext())
            iter.remove();
        map.print();
        System.out.println("Checking if the map is empty: " + map.isEmpty());
    }

    public static void test_speed_remove_add() {
        Map<Integer, Integer> map = new Map<>();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
            map.add(i, i);
        long finish = System.currentTimeMillis();
        System.out.println("Time to add 1000000 elements to the map: " + (finish - start) + " ms");

        Iterator<Node<Pair<Integer, Integer>>> iter = map.iterator();
        start = System.currentTimeMillis();
        while(iter.hasNext())
            iter.remove();
        finish = System.currentTimeMillis();
        System.out.println("Time to remove 1000000 elements to the map: " + (finish - start) + " ms");
    }

    public static void main(String[] args) {
        System.out.println("Test 1: Add element");
        System.out.println("Using the tree from example №1 of adding an element from the lecture on AVL-trees");
        test_add();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 2: Remove element");
        System.out.println("Using the tree from example №2 of removing an element from the lecture on AVL-trees");
        test_remove();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 3: Copy constructor");
        test_copy_constructor();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 4: Check for emptiness");
        test_is_empty();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 5: Get element");
        test_get();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 6: Change data by key");
        test_change();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 7: Removing all elements");
        test_clear();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 8: Element existence check");
        test_contains();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 9: Iterator");
        test_iterator();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 10: Method to remove an element in iterator");
        test_remove_iterator();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 11: Checking the speed of adding elements");
        test_speed_remove_add();
        System.out.println("________________________________________________________________________________");
    }
}
