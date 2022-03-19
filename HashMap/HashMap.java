import java.util.ArrayList;
import java.util.LinkedList;

public class HashMap<T extends Comparable<T>, E> {
    static final private int INITIAL_SIZE = 16;

    private ArrayList<LinkedList<Pair<T, E>>> table;
    private int size;
    private int numOfLists;
    float maxLoadFactor = 2.0f;

    //Constructors
    public HashMap() {
        numOfLists = INITIAL_SIZE;
        size = 0;
        table = new ArrayList<>();
        for(int i = 0; i < numOfLists; i++)
            table.add(new LinkedList<>());
    }

    public HashMap(int numOfLists) throws Exception{
        if(numOfLists <= 0)
            throw new Exception("Incorrect number of lists");
        this.numOfLists = numOfLists;
        size = 0;
        table = new ArrayList<>();
        for(int i = 0; i < numOfLists; i++)
            table.add(new LinkedList<>());
    }

    //Method that removes all elements
    public void clear() {
        for(int i = 0; i < numOfLists; i++)
            table.get(i).clear();
        size = 0;
    }

    //Method that adds a new Key-Data pair
    public void add(T key, E data) {
        table.get(indexFor(key, numOfLists)).add(new Pair<>(key, data));
        size++;
        if(currentLoadFactor() >= maxLoadFactor)
            rehashing(2 * numOfLists + 1);
    }

    //Method that returns data by key
    public E get(T key) {
        if(size == 0)
            return null;
        for(Pair<T, E> elem : table.get(indexFor(key, numOfLists)))
            if(elem.getKey() == key)
                return elem.getData();
        return null;
    }

    //Method that removes data by key and returns the removed element if it exists and null otherwise
    public E remove(T key) {
        if(size == 0)
            return null;
        for(Pair<T, E> elem : table.get(indexFor(key, numOfLists))) {
            if(elem.getKey() == key) {
                E data = elem.getData();
                table.get(indexFor(key, numOfLists)).remove(elem);
                size--;
                return data;
            }
        }
        return null;
    }

    //Method that returns the current number of elements
    public int size() {
        return size;
    }

    //Method that returns the max load factor
    public float maxLoadFactor() {
        return maxLoadFactor;
    }

    //Method that changes max load factor
    public void changeLoadFactor(float loadFactor) {
        this.maxLoadFactor = loadFactor;

        /*If the current load factor is greater than or equal to the max load factor,
          then we call the rehash method with an increase in the number of lists.
          Otherwise, we call the rehash method with a decrease in the number of lists */
        if(currentLoadFactor() >= maxLoadFactor)
            rehashing(2 * numOfLists + 1);
        else
            rehashingWithDecreaseNumOfLists();
    }

    //Method that returns current load factor
    public float currentLoadFactor() {
        return (float)size / numOfLists;
    }

    //Method that performs rehashing
    public void rehashing(int newNumOfLists) {
        ArrayList<LinkedList<Pair<T, E>>> newTable = new ArrayList<>();
        for(int i = 0; i < newNumOfLists; i++)
            newTable.add(new LinkedList<>());
        for(int i = 0; i < numOfLists; i++) {
            for(Pair<T, E> elem : table.get(i)) {
                int index = indexFor(elem.getKey(), newNumOfLists);
                newTable.get(index).add(elem);
            }
        }
        numOfLists = newNumOfLists;
        table = newTable;
    }

    //Method that performs rehashing to decrease the number of lists
    public void rehashingWithDecreaseNumOfLists() {
        int oldNumOfLists = numOfLists;
        while(currentLoadFactor() < maxLoadFactor)
            numOfLists--;
        numOfLists++;
        ArrayList<LinkedList<Pair<T, E>>> newTable = new ArrayList<>();
        for(int i = 0; i < numOfLists; i++)
            newTable.add(new LinkedList<>());
        for(int i = 0; i < oldNumOfLists; i++) {
            for(Pair<T, E> elem : table.get(i)) {
                int index = indexFor(elem.getKey(), numOfLists);
                newTable.get(index).add(elem);
            }
        }
        table = newTable;
    }

    //Method that prints all elements
    public void print() {
        for(int i = 0; i < numOfLists; i++) {
            System.out.print(i + ": [ ");
            for(Pair<T, E> elem : table.get(i))
                System.out.print(elem);
            System.out.println(" ]");
        }
    }

    //Method that calculates the list number for an element with a specific key using a hash function
    private int indexFor(T key, int numOfLists) {
        return key.hashCode() % numOfLists;
    }
}

//Class describing a key-data pair
class Pair<T extends Comparable<T>, E> implements Comparable<Pair<T, E>> {
    private T key;
    private E data;

    //Constructor
    public Pair(T key, E data) {
        this.key = key;
        this.data = data;
    }

    public T getKey() {
        return key;
    }

    public E getData() {
        return data;
    }

    //Method that compares 2 pairs with each other
    @Override
    public int compareTo(Pair<T, E> pair) {
        return this.key.compareTo(pair.key);
    }

    @Override
    public String toString() {
        return "(" + getKey() + ", " + getData() + "), ";
    }
}
