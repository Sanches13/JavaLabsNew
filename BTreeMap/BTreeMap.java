public class BTreeMap<T extends Comparable<T>, E> {
    private Node<Pair<T, E>> root;

    //Minimum degree of Btree
    private int t;

    //Constructor
    public BTreeMap(int t) throws Exception {
        if(t < 2)
            throw new Exception("Incorrect t parameter(t must be more or equal than 2)");
        this.t = t;
    }

    //Copy constructor
    public BTreeMap(BTreeMap<T, E> map) {
        this.t = map.t;
        root = new Node<>(t);
        copyNode(root, map.root);
    }

    private void copyNode(Node<Pair<T, E>> current, Node<Pair<T, E>> copy) {
        current.dataSize = copy.dataSize;
        current.leaf = copy.leaf;
        for(int i = 0; i < current.dataSize; i++)
            current.data[i] = copy.data[i];

        if(!current.leaf) {
            for(int i = 0; copy.children[i] != null; i++) {
                current.children[i] = new Node<>(t);
                copyNode(current.children[i], copy.children[i]);
            }
        }
    }

    //Method that removes all elements
    public void clear() {
        root = null;
        System.gc();
    }

    //Method that performs a binary search of an element in node
    private int binarySearch(Node<Pair<T, E>> current, T key) {
        int min = 0;
        int max = current.dataSize - 1;
        int middle = (max + min) / 2;
        while(min <= max && key.compareTo(current.data[middle].getKey()) != 0) {
            middle = (max + min) / 2;
            if(key.compareTo(current.data[middle].getKey()) < 0)
                max = middle - 1;
            else
                min = middle + 1;
        }

        if(current.data[middle] != null && key.compareTo(current.data[middle].getKey()) > 0)
            return middle + 1;
        return middle;
    }

    /*Used sources:
    https://habr.com/ru/post/114154/
    https://codechick.io/tutorials/dsa/dsa-b-tree
    https://neerc.ifmo.ru/wiki/index.php?title=B-%D0%B4%D0%B5%D1%80%D0%B5%D0%B2%D0%BE
     */

    //Method that adds a new Key-Data pair
    public void add(T key, E data) {
        if(root == null) {
            root = new Node<>(t);
            root.data[0] = new Pair<>(key, data);
            root.dataSize++;
            return;
        }

        if(root.dataSize == 2 * t - 1) {
            Node<Pair<T, E>> newRoot = new Node<>(t);
            rootSplit(newRoot);
            add(newRoot, key, data);
        }
        else
            add(root, key, data);
    }

    private void add(Node<Pair<T, E>> node, T key, E element) {
        int index = binarySearch(node, key);
        if(node.leaf) {
            for(int i = node.dataSize; i > index; i--)
                node.data[i] = node.data[i - 1];
            node.data[index] = new Pair<>(key, element);
            node.dataSize++;
        }
        else {
            Node<Pair<T, E>> tmp = node.children[index];
            if(tmp.dataSize == 2 * t - 1) {
                split(node, tmp);
                if(key.compareTo(node.data[index].getKey()) > 0)
                    index++;
            }
            add(node.children[index], key, element);
        }
    }

    //Method that performs root partitioning
    private void rootSplit(Node<Pair<T, E>> newRoot) {
        Node<Pair<T, E>> oldRoot = root;
        root = newRoot;
        newRoot.leaf = false;
        newRoot.children[0] = oldRoot;
        split(newRoot, oldRoot);
    }

    //Method that performs node partitioning
    private void split(Node<Pair<T, E>> parent, Node<Pair<T, E>> current) {
        Node<Pair<T, E>> newNode = new Node<>(t);
        newNode.leaf = current.leaf;
        newNode.dataSize = t - 1;
        for(int i = 0; i < t - 1; i++)
            newNode.data[i] = current.data[i + t];

        if(!current.leaf)
            for(int i = 0; i < t; i++)
                newNode.children[i] = current.children[i + t];
        current.dataSize = t - 1;

        int index = binarySearch(parent, current.data[t - 1].getKey());
        for(int i = parent.dataSize + 1; i > index; i--)
            parent.children[i] = parent.children[i - 1];
        parent.children[index + 1] = newNode;

        for(int i = parent.dataSize; i > index; i--)
            parent.data[i] = parent.data[i - 1];
        parent.data[index] = current.data[t - 1];
        parent.dataSize++;
    }

    //Key search method
    public boolean contains(T key) throws Exception{
        if(root == null)
            throw new Exception("Your map is empty!");
        return contains(root, key);
    }

    private boolean contains(Node<Pair<T, E>> current, T key) {
        int index = binarySearch(current, key);
        if(index < current.dataSize && key.compareTo(current.data[index].getKey()) == 0)
            return true;

        if(current.leaf)
            return false;
        else
            return contains(current.children[index], key);
    }

    //Method that changes data by key
    public void changeByKey(T key, E data) throws Exception{
        if(root == null)
            throw new Exception("Your map is empty!");
        changeByKey(root, key, data);
    }

    private void changeByKey(Node<Pair<T, E>> current, T key, E data) {
        int index = binarySearch(current, key);
        if(index < current.dataSize && key.compareTo(current.data[index].getKey()) == 0)
            current.data[index] = new Pair<>(key, data);

        if(!current.leaf)
            changeByKey(current.children[index], key, data);
    }

    //Method that returns data by key
    public E get(T key) {
        if(root == null)
            return null;
        return get(root, key);
    }

    private E get(Node<Pair<T, E>> current, T key) {
        int index = binarySearch(current, key);
        if(index < current.dataSize && key.compareTo(current.data[index].getKey()) == 0)
            return current.data[index].getData();

        if(!current.leaf)
            return get(current.children[index], key);
        return null;
    }

    //Check that BTree is empty
    public boolean isEmpty() {
        return root == null;
    }

    //Method that prints all elements
    public void print() {
        if(root == null)
            return;
        printNode(root);
        System.out.println();
    }

    private void printNode(Node<Pair<T, E>> node) {
        if(node == null)
            return;

        System.out.print("[");
        for (int i = 0; i < node.dataSize; i++)
            System.out.print(node.data[i] + "; ");
        System.out.print("] | ");
        if (!node.leaf) {
            for (int i = 0; i < node.dataSize + 1; i++)
                printNode(node.children[i]);
        }
    }
}

/*Template class "Node" containing an array with pairs, an array with pointers to child structures,
  the size of an array with pairs */

class Node<T extends Comparable<T>> {

    public T[] data;
    public Node<T>[] children;
    public int dataSize;

    //A flag indicating whether this node is a leaf
    boolean leaf;

    //Constructor
    @SuppressWarnings("unchecked")
    public Node(int t) {
        data = (T[]) new Pair[2 * t - 1];
        children = new Node[2 * t];
        dataSize = 0;
        leaf = true;
    }
}

//Class describing a key-data pair
class Pair <T extends Comparable<T>, E> implements Comparable<Pair<T, E>> {
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
        return "(" + getKey() + ", " + getData() + ")";
    }
}