import java.util.ArrayList;

public class Priority_Queue<T extends Comparable<T>>{
    private ArrayList<T> list;

    //Constructor
    public Priority_Queue(){
        list = new ArrayList<>();
    }

    //Method that checks if the queue is empty
    public boolean isEmpty(){
        return list.isEmpty();
    }

    //Method for getting the number of elements of the queue
    public int size(){
        return list.size();
    }

    //Method for getting the maximum value of the queue
    public T getMax() throws IndexOutOfBoundsException{
        if(size() == 0)
            throw new IndexOutOfBoundsException("Your queue is empty!");
        return list.get(0);
    }

    //Method that swaps 2 elements of the queue
    private void swap(int i, int j){
        T tmp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, tmp);
    }

    //Method that calculates the index of the parent of the current element in the binary heap
    private int getParentIndex(int currentIndex){
        return (int) Math.floor((currentIndex - 1) / 2);
    }

    //Method that adds an element to the queue
    public void add(T data){
        int currentIndex = list.size();
        list.add(data);
        int parentIndex = getParentIndex(currentIndex);
        while(data.compareTo(list.get(parentIndex)) > 0){
            swap(currentIndex, parentIndex);
            currentIndex = parentIndex;
            parentIndex = getParentIndex(currentIndex);
        }
    }

    private void toHeap(int heapSize, int currentIndex){
        int largest = currentIndex;
        int left = 2 * currentIndex + 1;
        int right = 2 * currentIndex + 2;

        if(left < heapSize && list.get(largest).compareTo(list.get(left)) < 0){
            largest = left;
        }

        if(right < heapSize && list.get(largest).compareTo(list.get(right)) < 0){
            largest = right;
        }

        if(largest != currentIndex){
            swap(largest, currentIndex);
            toHeap(heapSize, largest);
        }
    }

    //Method that removes an element of the queue
    public T remove() throws IndexOutOfBoundsException{
        if(size() == 0)
            throw new IndexOutOfBoundsException("The queue is empty!");

        T data = list.get(0);
        swap(0, size() - 1);
        list.remove(size() - 1);
        toHeap(size(), 0);
        return data;
    }

    public String toString(){
        return list.toString();
    }
}
