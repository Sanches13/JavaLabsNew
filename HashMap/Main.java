import java.util.LinkedList;

public class Main {

    public static void test_add() {
        HashMap<Integer, Integer> map = new HashMap<>();

        //Create an auxiliary linked list with 1000 different elements
        LinkedList<Integer> values = new LinkedList<>();
        for(int i = 0; i < 1000; i++)
            values.add(i);

        //Add from a list of random different elements to the map
        for(int i = 0; i < 100; i++) {
            int index = (int) (Math.random() * values.size());
            map.add(values.get(index), values.get(index));
            values.remove(index);
        }

        //Display the map
        map.print();
        System.out.println("Current size: " + map.size());
        System.out.println("Max load factor: " + map.maxLoadFactor());
        System.out.println("Current load factor: " + map.currentLoadFactor());
    }

    public static void test_clear(){
        HashMap<Integer, String> map = new HashMap<>();

        //Add some elements to the map
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        //Display the map
        System.out.println("Initial map");
        map.print();
        System.out.println("Current size: " + map.size());
        System.out.println();

        //Remove all elements
        map.clear();
        System.out.println("Map after removing all elements");
        map.print();
        System.out.println("Current size: " + map.size());
    }

    public static void test_get(){
        HashMap<Integer, String> map = new HashMap<>();
        map.add(10, "ten");
        map.add(7, "seven");
        map.add(15, "fifteen");
        map.add(5, "five");
        map.add(8, "eight");
        map.add(12, "twelve");

        System.out.println("Initial map");
        map.print();

        System.out.println("Data with key 5: " + map.get(5));
        System.out.println("Data with key 15: " + map.get(15));
        System.out.println("Data with key 8: " + map.get(8));
        System.out.println("Data with key 1000: " + map.get(1000));
    }

    public static void test_remove() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 31; i++)
            map.add(i, i);

        System.out.println("Initial map");
        map.print();
        System.out.println("Current size: " + map.size());

        //Remove element "7"
        map.remove(7);

        System.out.println("Map after removing element 7");
        map.print();
        System.out.println("Current size: " + map.size());
    }

    public static void test_rehashing() {
        HashMap<Integer, Integer> map = new HashMap<>();
        System.out.println("The initial number of lists is 16. Add 31 elements to the map:");
        for (int i = 0; i < 31; i++)
            map.add(i, i);

        System.out.println("Initial map");
        map.print();
        System.out.println("Current size: " + map.size());
        System.out.println("Current load factor: " + map.currentLoadFactor());

        System.out.println("We have added 31 elements as this is the maximum number of elements at whish rehashing doesn't occur");
        System.out.println("Add one more element, after which rehashing will occur: ");
        map.add(777, 777);

        map.print();
        System.out.println("Current size: " + map.size());
        System.out.println("Current load factor: " + map.currentLoadFactor());
    }

    //Here the method to rehashing with decreasing the number of lists is also checked
    public static void test_changeLoadFactor() {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < 31; i++)
            map.add(i, i);
        System.out.println("Initial map");
        map.print();
        System.out.println("Current size: " + map.size());
        System.out.println("Max load factor: " + map.maxLoadFactor());
        System.out.println("Current load factor: " + map.currentLoadFactor());
        System.out.println();

        System.out.println("We have added 31 elements as this is the maximum number of elements at whish rehashing doesn't occur");
        System.out.println("Now let's decrease the max load factor");
        map.changeLoadFactor(1);
        System.out.println("Current size: " + map.size());
        System.out.println("Max load factor: " + map.maxLoadFactor());
        System.out.println("Current load factor: " + map.currentLoadFactor());
        map.print();
        System.out.println();

        System.out.println("Now let's increase the max load factor");
        System.out.println("Here the method to rehashing with decreasing the number of lists is also checked");
        map.changeLoadFactor(1.5f);
        System.out.println("Current size: " + map.size());
        System.out.println("Max load factor: " + map.maxLoadFactor());
        System.out.println("Current load factor: " + map.currentLoadFactor());
        map.print();
    }

    public static void test_speed_add() {
        HashMap<Integer, Integer> map = new HashMap<>();
        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
            map.add(i, i);
        long finish = System.currentTimeMillis();
        System.out.println("Time to add 1000000 elements to the map: " + (finish - start) + " ms");
    }

    public static void main(String[] args) throws Exception{
        System.out.println("Test 1: Add element");
        test_add();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 2: Removing all elements");
        test_clear();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 3: Get element");
        test_get();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 4: Remove element");
        test_remove();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 5: Rehashing due to too high load factor");
        test_rehashing();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 6: Load factor change");
        test_changeLoadFactor();
        System.out.println("________________________________________________________________________________");

        System.out.println("Test 7: Checking the speed of adding elements");
        test_speed_add();
        System.out.println("________________________________________________________________________________");
    }
}
