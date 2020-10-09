package Unit04_queue;

/**
 * 顺序队列: 用数组实现的队列
 * 队空判断条件：head == tail
 * 队满判断条件：tail == length
 */
public class ArrayQueue {

    private String[] arr;
    private int head; // 队列头下标
    private int tail; // 队列尾下标
    private final int length; // 队列长度

    private final static int DEFAULT_CAPACITY = 8;

    public ArrayQueue() {
        this.arr = new String[DEFAULT_CAPACITY];
        this.head = this.tail = 0;
        this.length = DEFAULT_CAPACITY;
    }

    /**
     * 问题：随着不停地进行入队、出队操作，head 和 tail 都会持续往后移动。
     * 当 tail 移动到最右边，即使数组中还有空闲空间，也无法继续往队列中添加数据了
     */
    private void enqueue(String str) {
        if (tail == length) {
            System.out.println("队列容量已满，入队失败");
            return;
        }
        arr[tail] = str;
        ++tail;
        System.out.println("enqueue成功，为"+str);
    }

    /**
     * 优化版：解决上面的问题
     * 入队时如果尾下标走到了最后，但是队列还没满，就集中进行一次数据搬移操作。
     * 如果在出队时进行数据搬移，那么每次出队都要搬移，时间复杂度就高了。
     * TODO 时间复杂度
     */
    private void enqueueV2(String str) {
        if (tail == length) {
            if (head == 0) {
                System.out.println("队列容量已满，入队失败");
                return;

            }
            String[] newArr = new String[DEFAULT_CAPACITY];
            int size = length-head;
            System.arraycopy(arr,head,newArr,0,size);
            arr = newArr;
            head = 0;
            tail = size;
        }
        arr[tail] = str;
        ++tail;
        System.out.println("enqueue成功，为"+str);
    }

    private String dequeue() {
        if (head == tail) {
            System.out.println("队列为空，出队失败");
            return null;
        }
        String popStr = arr[head];
        ++head;
        System.out.println("dequeue成功，为"+popStr);
        return popStr;
    }

    public static void main(String[] args) {
        ArrayQueue queue = new ArrayQueue();

        queue.enqueue("00");
        queue.enqueue("01");
        queue.enqueue("02");
        queue.enqueue("03");
        queue.enqueue("04");
        queue.enqueue("05");
        queue.enqueue("06");
        queue.enqueue("07");
        queue.enqueue("08");

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
        queue.dequeue();

        queue.enqueueV2("09");

        queue.dequeue();
        queue.dequeue();
        queue.dequeue();
    }
}
