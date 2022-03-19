import java.util.Iterator;
import java.util.NoSuchElementException;

public class Map<T extends Comparable<T>, E> implements Iterable<Node<Pair<T, E>>>{
    private Node<Pair<T, E>> root;

    //Iterator
    public Iterator<Node<Pair<T, E>>> iterator() {
        return new myIterator(this);
    }

    class myIterator implements Iterator<Node<Pair<T,E>>> {
        private Map<T, E> map;
        private Node<Pair<T, E>> current;

        public myIterator(Map<T, E> map) {
            this.map = map;
            current = map.root;
            while (current.leftSon != null)
                current = current.leftSon;
        }

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Node<Pair<T, E>> next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Node<Pair<T, E>> next = current;

            //if the right subtree is non-empty,find the minimum element in it
            if(current.rightSon != null) {
                current = current.rightSon;
                while (current.leftSon != null)
                    current = current.leftSon;
                return next;
            }

            while(true) {
                if(current.parent == null) {
                    current = null;
                    return next;
                }
                if(current.parent.leftSon == current) {
                    current = current.parent;
                    return next;
                }
                current = current.parent;
            }
        }

        @Override
        public void remove() {
            T key = current.getData().getKey();
            next();
            map.remove(key);
        }
    }

    //Constructor
    public Map() {
    }

    //Copy constructor
    public Map(Map<T, E> tree) {
        root = new Node<>(tree.root);
        root.parent = null;
        copyNode(this.root, tree.root);
    }

    private void copyNode(Node<Pair<T, E>> currentNode, Node<Pair<T, E>> copy) {
        if(copy == null)
            return;
        if(copy.leftSon != null) {
            currentNode.leftSon = new Node<>(copy.leftSon);
            currentNode.leftSon.parent = currentNode;
            copyNode(currentNode.leftSon, copy.leftSon);
        }
        if(copy.rightSon != null) {
            currentNode.rightSon = new Node<>(copy.rightSon);
            currentNode.rightSon.parent = currentNode;
            copyNode(currentNode.rightSon, copy.rightSon);
        }
    }

    //Check that AVLTree is empty
    public boolean isEmpty() {
        return root == null;
    }

    //Method that returns the height of the node (non-empty tree leaf has height 1)
    public int getHeight(Node<Pair<T, E>> node) {
        if(node == null)
            return 0;
        else
            return node.height;
    }

    //Method to update node height after performing rotations and balancing
    public void updateHeight(Node<Pair<T, E>> node) {
        if (getHeight(node.leftSon) > getHeight(node.rightSon)) {
            node.height = 1;
            if(node.leftSon != null)
                node.height += node.leftSon.height;
        }
        else {
            node.height = 1;
            if(node.rightSon != null)
                node.height += node.rightSon.height;
        }
    }

    //Method that returns "balance" of the node
    private int getBalance(Node<Pair<T, E>> node) {
        return getHeight(node.rightSon) - getHeight(node.leftSon);
    }

    //Method that adds a new Key-Data pair
    public void add(T key, E data) {
        if(root == null) {
            root = new Node<>(null, new Pair<>(key, data));
            return;
        }

        root = add(root, null, key, data);
    }

    private Node<Pair<T, E>> add(Node<Pair<T, E>> current, Node<Pair<T, E>> parent, T key, E data) {
        if(current == null)
            return new Node<>(parent, new Pair<>(key, data));
        if(key.compareTo(current.getData().getKey()) == 0)
            current.setData(new Pair<>(key, data));
        else if(key.compareTo(current.getData().getKey()) < 0)
            current.leftSon = add(current.leftSon, current, key, data);
        else
            current.rightSon = add(current.rightSon, current, key, data);
        return balanceTree(current);
    }

    //Method that returns data by key
    public E get(T key) {
        if(root == null)
            return null;

        Node<Pair<T, E>> current = root;
        while(current != null) {
            if(current.getData().getKey().compareTo(key) != 0) {
                if(current.getData().getKey().compareTo(key) > 0)
                    current = current.leftSon;
                else
                    current = current.rightSon;
            }
            else
                return current.getData().getData();
        }
        return null;
    }

    //Method that changes data by key
    public void changeByKey(T key, E data) throws Exception {
        if(root == null)
            throw new Exception("Your map is empty!");

        Node<Pair<T, E>> current = root;
        while(current != null) {
            if(current.getData().getKey().compareTo(key) != 0) {
                if(current.getData().getKey().compareTo(key) > 0)
                    current = current.leftSon;
                else
                    current = current.rightSon;
            }
            else {
                current.getData().setData(data);
                return;
            }
        }
        throw new Exception("Data with key " + key + " doesn't exist!");
    }

    //Method that removes all elements
    public void clear() {
        if(root == null)
            return;
        clear(root.leftSon);
        clear(root.rightSon);
        root = null;
    }

    private void clear(Node<Pair<T, E>> node) {
        if(node == null)
            return;
        clear(node.leftSon);
        clear(node.rightSon);
        node = null;
    }

    //Key search method
    public boolean contains(T key) {
        if(root == null)
            return false;

        Node<Pair<T, E>> currentNode = root;
        while(currentNode != null) {
            if(currentNode.getData().getKey().compareTo(key) != 0) {
                if(key.compareTo(currentNode.getData().getKey()) < 0)
                    currentNode = currentNode.leftSon;
                else
                    currentNode = currentNode.rightSon;
            }
            else
                return true;
        }
        return false;
    }

    //Method that performs right turn
    private Node<Pair<T, E>> rightRotate(Node<Pair<T, E>> nodeA) {
        Node<Pair<T, E>> nodeB = nodeA.leftSon;
        Node<Pair<T, E>> nodeC = nodeB.rightSon;
        nodeB.rightSon = nodeA;
        nodeB.parent = nodeA.parent;
        nodeA.parent = nodeB;
        nodeA.leftSon = nodeC;

        if(nodeC != null)
            nodeC.parent = nodeA;
        updateHeight(nodeA);
        updateHeight(nodeB);
        return nodeB;
    }

    //Method that performs left turn
    private Node<Pair<T, E>> leftRotate(Node<Pair<T, E>> nodeA) {
        Node<Pair<T, E>> nodeB = nodeA.rightSon;
        Node<Pair<T, E>> nodeC = nodeB.leftSon;
        nodeB.leftSon = nodeA;
        nodeB.parent = nodeA.parent;
        nodeA.parent = nodeB;
        nodeA.rightSon = nodeC;
        if(nodeC != null)
            nodeC.parent = nodeA;
        updateHeight(nodeA);
        updateHeight(nodeB);
        return nodeB;
    }

    //Method that performs tree balancing
    private Node<Pair<T, E>> balanceTree(Node<Pair<T, E>> node) {
        updateHeight(node);
        if(getBalance(node) == 2) {
            if(getBalance(node.rightSon) < 0)
                node.rightSon = rightRotate(node.rightSon);
            return leftRotate(node);
        }

        if(getBalance(node) == -2) {
            if(getBalance(node.leftSon) > 0)
                node.leftSon = leftRotate(node.leftSon);
            return rightRotate(node);
        }

        return node;
    }

    //------------
    //Method that prints all elements
    public void print() {
        System.out.print("[ ");
        printNode(root);
        System.out.print("]");
        System.out.println();
    }

    private void printNode(Node<Pair<T, E>> node) {
        if(node == null)
            return;
        printNode(node.leftSon);
        System.out.print(node + ", p:" + printParent(node) + ", h:" + getHeight(node) + " | ");
        printNode(node.rightSon);
    }

    private String printParent(Node<Pair<T, E>> node) {
        if(node.parent == null)
            return "null";
        else
            return node.parent.getData().getKey().toString();
    }
    //------------

    //Method that removes data by key
    public void remove(T key) {
        if(root == null)
            return;

        root = remove(root, key);
    }

    private Node<Pair<T, E>> remove(Node<Pair<T, E>> node, T key) {
        if(node == null)
            return null;

        if(key.compareTo(node.getData().getKey()) < 0)
            node.leftSon = remove(node.leftSon, key);
        else if(key.compareTo(node.getData().getKey()) > 0)
            node.rightSon = remove(node.rightSon, key);
        else {
            Node<Pair<T, E>> left = node.leftSon;
            Node<Pair<T, E>> right = node.rightSon;
            Node<Pair<T, E>> par = node.parent;
            node = null;
            if(left == null && right == null)
                return null;
            if(right == null) {
                left.parent = par;
                return left;
            }
            Node<Pair<T, E>> min = findMinNode(right);

            min.rightSon = removeMin(right);
            right.parent = min;
            min.parent = par;
            min.leftSon = left;
            if(left != null)
                left.parent = min;
            return balanceTree(min);
        }
        return balanceTree(node);
    }

    //Method that finds the minimum element of tree with the root "node"
    private Node<Pair<T, E>> findMinNode(Node<Pair<T, E>> node) {
        if(node.leftSon == null)
            return node;

        return findMinNode(node.leftSon);
    }

    private Node<Pair<T, E>> removeMin(Node<Pair<T, E>> node) {
        if(node.leftSon == null)
            return node.rightSon;
        node.leftSon = removeMin(node.leftSon);
        return balanceTree(node);
    }
}

/*Template class "Node" containing a field for storing a value of an arbitrary
  type and 3 fields for storing pointers to a parent and 2 children         */
class Node<T extends Comparable<T>> implements Comparable<Node<T>>{
    private T data;
    public Node<T> parent;
    public Node<T> leftSon;
    public Node<T> rightSon;

    //We need to use the "height" parameter, because this increases the speed of the methods
    public int height;

    //Constructors
    public Node(Node<T> node){
        this.data = node.data;
        this.height = node.height;
    }

    public Node(Node<T> parent, T data){
        this.data = data;
        this.parent = parent;
        height = 1;
    }

    @Override
    public String toString(){
        return data.toString();
    }

    public T getData(){
        return data;
    }

    public void setData(T data){
        this.data = data;
    }

    //Method that compares 2 nodes with each other
    @Override
    public int compareTo(Node<T> node) {
        return this.data.compareTo(node.data);
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

    public void setData(E data) {
        this.data = data;
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