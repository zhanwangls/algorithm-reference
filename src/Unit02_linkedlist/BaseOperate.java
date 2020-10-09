package Unit02_linkedlist;

/**
 * 单链表5个基本操作
 * 1. 单向链表反转
 * 2. 单向链表中环的检测：判断链表中是否存在环的结构
 * 3. 两个有序的链表合并
 * 4. 删除链表倒数第 n 个结点
 * 5. 求链表的中间结点
 *
 * 总结：链表常见解决问题方式，使用两个指针
 */
public class BaseOperate {

    /**
     * 1. 单向链表反转
     * 思路：将一个节点的后指针变成前指针
     */
    private static Node revert(Node linkedList) {
        Node curr = linkedList;
        Node pre = null;
        while (curr != null) {
            Node next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }

    /**
     * 2. 单向链表中 环的检测
     * 思路：快慢指针，判断他们是否会相遇   快指针：一次一个节点   慢指针：一次两个节点
     */
    private static boolean checkCircle(Node linkedList) {
        Node slow = linkedList;
        Node fast = linkedList;
        while (fast != null && fast.next != null && slow != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 3. 有序链表合并：两个链表升序，合并后仍然有序，可以有两个值相同的节点 LeetCode21
     * 思路：表1与表2一次取一个节点相比较，小的放入新链表中，指针向后移一位，大的不动，再次取出比较
     */
    private static Node mergeTwoLists(Node linkedList1, Node linkedList2) {
        Node list1 = linkedList1;
        Node list2 = linkedList2;
        Node solider = new Node(0,null); // 利用哨兵减少null判断
        Node curr = solider;

        while (list1 != null && list2 != null) {
            if (list1.data < list2.data) {
                curr.next = list1;
                list1 = list1.next;
            } else {
                curr.next = list2;
                list2 = list2.next;
            }
            curr = curr.next;
        }

        // 考虑一个链表走完了，另一个没走完的情况
        if (list1 != null) {
            curr.next = list1;
        } else if (list2 != null) {
            curr.next = list2;
        }
        return solider.next;
    }

    /**
     * 4. 删除倒数第k个节点
     * 思路：快慢指针中间相差k个结点，快指针移到最后的位置，慢指针指向的节点就是要删除的节点
     */
    private static Node deleteLastKth(Node linkedList, int k) {
        // 快指针指向正数第k个节点
        Node fast = linkedList;
        int i = 1;
        while (i<k) {
            fast = fast.next;
            ++i;
        }

        // 没有第k个节点
        if (fast == null) {
            return linkedList;
        }

        // 快慢指针一起向后移，直到快指针到最后一个节点
        Node slow = linkedList;
        Node pre = null;
        while (fast.next != null) {
            fast = fast.next;
            pre = slow;
            slow = slow.next;
        }

        if (pre == null) {
            // 当前慢直接节点就是第k个节点
            linkedList = linkedList.next;
        } else {
            pre.next = pre.next.next;
        }
        return linkedList;
    }

    /**
     * 5. 求中间节点
     * 思路：快慢指针，二倍速的快指针走到头的时候，一倍速的慢指针就正好在中间节点了，如果有两个中间节点，要考虑取前还是后
     */
    private static Node findMiddleNode(Node linkedList) {
        // 取后节点
        Node fast = linkedList;
        // 取前节点
        //Node fast = linkedList.next;
        Node slow = linkedList;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }

    public static void main(String[] args) {
        Node linkedList = init(0,5);
        printAll(linkedList);

        Node revert = revert(linkedList);
        printAll(revert);

        Node linkedListCircle = initCircle(6);
        boolean checkCircle = checkCircle(linkedListCircle);
        System.out.println(checkCircle);

        Node mergeList1 = init(0,5);
        Node mergeList2 = init(3,9);
        Node merged = mergeTwoLists(mergeList1,mergeList2);
        printAll(merged);

        Node kthList = init(0,10);
        Node deletedKthList = deleteLastKth(kthList,6);
        printAll(deletedKthList);

        Node middleList = init(0,11);
        Node middle = findMiddleNode(middleList);
        System.out.println(middle.data);


    }

    /**
     * 生成指定数量节点的链表
     */
    private static Node init(int from, int to) {

        Node firstNode = new Node(from,null);
        Node curr = firstNode;
        for (int i = from+1;i<=to;i++) {
            Node newNode = new Node(i,null);
            curr.next = newNode;
            curr = newNode;
        }
        return firstNode;
    }

    private static Node initCircle(int count) {
        if (count <= 0) {
            return null;
        }
        Node firstNode = new Node(0,null);
        Node curr = firstNode;
        for (int i = 1;i<count;i++) {
            Node newNode = new Node(i,null);
            curr.next = newNode;
            curr = newNode;
        }
        curr.next = firstNode;
        return firstNode;
    }

    /**
     * 依次打印链表上的节点
     */
    private static void printAll(Node list) {
        Node node = list;
        while (node != null) {
            System.out.print(node.data + "\t");
            node = node.next;
        }
        System.out.println();
    }

    /**
     * 定义一个节点
     */
    private static class Node {
        private final int data;
        private Node next;

        public Node(int data, Node next) {
            this.data = data;
            this.next = next;
        }
    }
}
