import java.util.Iterator;
import java.util.LinkedList;

public class Main {

    public static void test_add() throws Exception{
        BTreeMap<Integer, Integer> map = new BTreeMap<>(3);
        LinkedList<Integer> values = new LinkedList<>();
        for(int i = 0; i < 100; i++)
            values.add(i);

        //Add from a list of random different elements to the map
        for(int i = 0; i < 20; i++) {
            int index = (int) (Math.random() * values.size());
            map.add(values.get(index), values.get(index));
            values.remove(index);
        }

        //Display all nodes
        System.out.println("Initial tree");
        map.print();

        //Add new element "100"
        map.add(100, 100);

        System.out.println("Tree after adding element 13");
        map.print();
    }

    public static void test_copy_constructor() throws Exception{
        BTreeMap<Integer, Integer> firstMap = new BTreeMap<>(3);
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

        //Display all nodes
        System.out.println("First map");
        firstMap.print();

        BTreeMap<Integer, Integer> secondMap = new BTreeMap<>(firstMap);
        System.out.println("Second map after using copy constructor");
        secondMap.print();
    }

    public static void test_is_empty() throws Exception{
        BTreeMap<String, String> map = new BTreeMap<>(3);
        System.out.println("Map after initialization");
        map.print();
        System.out.println();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.add("Key example", "Data example");
        System.out.println("Map after adding data");
        map.print();
        System.out.println();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.clear();
        System.out.println("Map after removing data");
        map.print();
        System.out.println();
        System.out.println("Checking if the map is empty: " + map.isEmpty());
    }

    public static void test_clear() throws Exception{
        BTreeMap<Integer, String> map = new BTreeMap<>(2);
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes
        System.out.println("Initial map");
        map.print();
        System.out.println();
        System.out.println("Checking if the map is empty: " + map.isEmpty());

        map.clear();
        System.out.println("Map after removing all elements");
        map.print();
        System.out.println();
        System.out.println("Checking if the map is empty: " + map.isEmpty());
    }

    public static void test_get() throws Exception{
        BTreeMap<Integer, String> map = new BTreeMap<>(2);
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes
        System.out.println("Initial tree");
        map.print();

        System.out.println("Data with key 5: " + map.get(5));
        System.out.println("Data with key 15: " + map.get(15));
        System.out.println("Data with key 8: " + map.get(8));
        System.out.println("Data with key 1000: " + map.get(1000));
    }

    public static void test_change() throws Exception{
        BTreeMap<Integer, String> map = new BTreeMap<>(3);
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes
        System.out.println("Initial tree");
        map.print();

        int key = 10;
        map.changeByKey(key, "New value");

        System.out.println("Map after changing element with key " + key);
        map.print();
    }

    public static void test_contains() throws Exception{
        BTreeMap<Integer, String> map = new BTreeMap<>(2);
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display all nodes
        System.out.println("Initial map");
        map.print();

        System.out.println("Data with key 5 exists: " + map.contains(5));
        System.out.println("Data with key 100 exists: " + map.contains(100));
        System.out.println("Data with key 12 exists: " + map.contains(12));
    }

    public static void test_speed_add() throws Exception{
        BTreeMap<Integer, Integer> map = new BTreeMap<>(10);
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
            map.add(i, i);
        long finish = System.currentTimeMillis();
        System.out.println("Time to add 1000000 elements to the map with t = 10: " + (finish - start) + " ms");
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Test 1: Add element");
        test_add();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 2: Copy constructor");
        test_copy_constructor();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 3: Check for emptiness");
        test_is_empty();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 4: Removing all elements");
        test_clear();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 5: Get element");
        test_get();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 6: Change data by key");
        test_change();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 7: Element existence check");
        test_contains();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 8: Checking the speed of adding elements");
        test_speed_add();
        System.out.println("________________________________________________________________________________");
    }
}
