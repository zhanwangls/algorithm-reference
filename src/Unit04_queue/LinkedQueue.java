package Unit04_queue;

/**
 * 链式队列: 用链表实现的队列
 * 队空判断条件：head == null
 * 不会队满
 */
public class LinkedQueue {

    private Node head = null;
    private Node tail = null;

    private void enqueue(String str) {
        Node newNode= new Node(str,null);
        if (tail == null) {
            head = tail = newNode;
        } else {
            tail.next = newNode;
            tail = newNode;
        }
        System.out.println("入队成功，为："+str);
    }

    private void dequeue() {
        if (head == null) {
            System.out.println("出队失败，当前队列为空");
            return;
        }
        String dequeue = head.data;
        head = head.next;
        // 队列为空了记得清空指针
        if (head == null) {
            tail = null;
        }
        System.out.println("出队成功，为："+dequeue);
    }

    public static void main(String[] args) {
        LinkedQueue queue = new LinkedQueue();

        queue.dequeue();
        queue.enqueue("01");
        queue.enqueue("02");
        queue.dequeue();
    }


    private static class Node {
        String data;
        Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
