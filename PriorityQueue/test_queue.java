public class test_queue {
    public static void test_1(){
        System.out.println("----------------------------------------");
        System.out.println("1000000 items add and remove time test");
        Priority_Queue<Integer> queue = new Priority_Queue<>();

        long start = System.currentTimeMillis();
        for(int i = 0; i < 1000000; i++)
            queue.add((int) (Math.random() * 1000001));
        long finish = System.currentTimeMillis();
        System.out.println("Time to add 1000000 random elements to the queue: " + (finish - start) + " ms");
        System.out.println("Number of elements in queue: " + queue.size());
        try {
            System.out.println("The maximum queue element: " + queue.getMax());
        } catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
        System.out.println();

        start = System.currentTimeMillis();
        try {
            for (int i = 0; i < 1000000; i++)
                queue.remove();
        } catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
        finish = System.currentTimeMillis();
        System.out.println("Time to remove 1000000 elements from the queue: " + (finish - start) + " ms");
        System.out.println("Checking that queue is empty: " + queue.isEmpty());
        System.out.println("Number of elements in queue: " + queue.size());
        System.out.println("----------------------------------------");
    }

    public static void test_2(){
        System.out.println("----------------------------------------");
        System.out.println("Test that checks the correctness of adding and removing elements of the queue");
        Priority_Queue<Integer> queue = new Priority_Queue<>();

        System.out.println("Add the following elements in order: 1, 2, 5, 7, 9, 3, 8, 10");
        queue.add(1);
        System.out.println(queue);

        queue.add(2);
        System.out.println(queue);

        queue.add(5);
        System.out.println(queue);

        queue.add(7);
        System.out.println(queue);

        queue.add(9);
        System.out.println(queue);

        queue.add(3);
        System.out.println(queue);

        queue.add(8);
        System.out.println(queue);

        queue.add(10);
        System.out.println(queue);

        System.out.println("Removing three elements:");
        try {
            for(int i = 0; i < 3; i++) {
                queue.remove();
                System.out.println(queue);
            }
        } catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }

        System.out.println("Checking that queue is empty: " + queue.isEmpty());
        try {
            System.out.println("The maximum queue element: " + queue.getMax());
        } catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        //1000000 items add and remove time test
        test_1();
        //Test that checks the correctness of adding and removing elements of the queue
        test_2();
    }
}
