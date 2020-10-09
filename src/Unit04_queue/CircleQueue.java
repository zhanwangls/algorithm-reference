package Unit04_queue;

/**
 * 循环队列：
 * 比顺序队列时间复杂度更低，因为顺序队列队满时需要搬移数据。
 * 循环队列比链式队列应用更加广泛。因为利用 CAS 原子操作，可以实现非常高效的并发队列。
 *
 * 比如：队列的大小为 8，当前 head=4，tail=7。
 * 当有一个新的元素 a 入队时，我们放入下标为 7 的位置。
 * 但这个时候，我们并不把 tail 更新为 8，而是将其在环中后移一位，到下标为 0 的位置。
 * 当再有一个元素 b 入队时，我们将 b 放入下标为 0 的位置，然后 tail 加 1 更新为 1。
 *
 * 当队列满时，tail 指向的位置实际上是没有存储数据的，所以，循环队列会浪费一个数组的存储空间。
 * 最关键的是，确定好队空和队满的判定条件
 * 队空判断条件：head == tail
 * 队满判断条件：(tail+1)%n=head   多画几张图总结规律
 */
public class CircleQueue {

    private final String[] arr;
    private int head;
    private int tail;
    private final int length;

    private final static int DEFAULT_CAPACITY = 8;

    public CircleQueue() {
        this.arr = new String[DEFAULT_CAPACITY];
        this.head = this.tail = 0;
        this.length = DEFAULT_CAPACITY;
    }

    private void enqueue(String str) {
        if ((tail + 1) % length == head) {
            System.out.println("入队失败，当前队列已满");
            return;
        }
        arr[tail] = str;
        /*if (tail == length - 1) {
            tail = 0;
        } else {
            ++tail;
        }*/
        tail = (tail + 1) % length;
        System.out.println("入队成功，为："+str);
    }

    private void dequeue() {
        if (head == tail) {
            System.out.println("出队失败，当前队列为空");
            return;
        }
        String dequeue = arr[head];
        /*if (head == length - 1) {
            head = 0;
        } else {
            ++head;
        }*/
        head = (head + 1) % length;
        System.out.println("出队成功，为："+dequeue);
    }

    public static void main(String[] args) {

        CircleQueue queue = new CircleQueue();

        queue.dequeue();

        queue.enqueue("00");
        queue.enqueue("01");
        queue.enqueue("02");
        queue.enqueue("03");
        queue.enqueue("04");
        queue.enqueue("05");
        queue.enqueue("06");
        queue.enqueue("07");

        queue.dequeue();
        queue.enqueue("08");

    }

}
