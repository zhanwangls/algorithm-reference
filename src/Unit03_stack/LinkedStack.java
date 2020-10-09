package Unit03_stack;

/**
 * 链式栈：用链表实现的栈
 * 空间复杂度：O(1)
 * 时间复杂度：O(1)
 */
public class LinkedStack {

    private Node top;

    public LinkedStack(Node node) {
        this.top = node;
    }

    private static class Node {
        private String data;
        private Node next;

        public Node(String data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private Node pop() {
        if (top == null) {
            return null;
        }
        Node curr = top;
        top = curr.next;
        curr.next = null;
        return curr;
    }

    private void push(String str) {
       Node pushNode = new Node(str,null);
        if (top != null) {
            pushNode.next = top;
        }
        top = pushNode;
    }

    private static void printAll(Node node) {
        while (node != null) {
            System.out.print(node.data + "\t");
            node = node.next;
        }
        System.out.println();
    }

    public static void main(String[] args) {
        LinkedStack linkedStack = new LinkedStack(null);

        linkedStack.push("01");
        linkedStack.push("02");
        printAll(linkedStack.top);

        printAll(linkedStack.pop());
        printAll(linkedStack.top);
    }
}
