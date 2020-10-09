package Unit08_hashtable;

/**
 * LRU：最近最少访问淘汰策略（散列表+链表实现）
 * 借助散列表+链表，我们可以把 LRU 缓存淘汰算法的时间复杂度降低为 O(1)。
 * 散列表+链表的结合是很常用的方式，如LRU、Redis有序集合、LinkedHashMap。
 * LinkedHashMap 是通过双向链表和散列表这两种数据结构组合实现的。LinkedHashMap 中的“Linked”实际上是指的是双向链表，并非指用链表法解决散列冲突。
 * 按照访问时间排序的 LinkedHashMap 本身就是一个支持 LRU 缓存淘汰策略的缓存系统
 *
 * 通过链表实现 LRU 缓存淘汰算法时：
 * 我们需要维护一个按照访问时间从小到大有序排列的链表结构。因为缓存大小有限，当缓存空间不够，需要淘汰一个数据的时候，我们就直接将链表头部的结点删除。
 * 当要缓存某个数据的时候，先在链表中查找这个数据。如果没有找到，则直接将数据放到链表的尾部；
 * 如果找到了，我们就把它移动到链表的尾部。因为查找数据需要遍历链表，所以单纯用链表实现的 LRU 缓存淘汰算法的时间复杂很高，是 O(n)。
 * 一个缓存（cache）系统主要包含：添加、删除、查找数据。这三个操作都要涉及查找，如果用链表时间复杂度都是O(n)。
 * 如果我们将散列表和链表两种数据结构组合使用，可以将这三个操作的时间复杂度都降低到 O(1)。
 *
 * 数据模型参考：《散列表+链表数据模型.jpg》
 */
public class LRU<K,V> {

    private final HashTable<K,DNode<K,V>> table; // 散列表存储key，value
    // 双链表头尾节点，哨兵节点，put的数据都在头尾之间
    private final DNode<K,V> head;
    private final DNode<K,V> tail;
    private final static int DEFAULT_CAPACITY = 10; // 链表默认容量
    private int length; // 链表长度

    public LRU() {
        this.length = 0;
        this.head = new DNode<>();
        this.tail = new DNode<>();

        this.head.next = this.tail;
        this.tail.pre = this.head;
        this.table = new HashTable<>();
    }

    public void put(K key,V value) {
        DNode<K,V> node = table.get(key);
        if (node == null) {
            // 直接放到链表头部
            DNode<K,V> newNode = new DNode<>(key, value);
            addNode(newNode);
            table.put(key,newNode);
            // 淘汰
            if (++length > DEFAULT_CAPACITY) {
                DNode<K,V> tailNode = popTail();
                table.remove(tailNode.key);
                --length;
            }

        } else {
            node.value = value;
            moveToHead(node);
        }
    }

    public V get(K key) {
        DNode<K,V> node = table.get(key);
        if (node == null) {
            return null;
        } else {
            moveToHead(node);
            return node.value;
        }
    }

    public void remove(K key) {
        DNode<K,V> node = table.get(key);
        if (node == null) {
            return;
        }
        removeNode(node);
        length--;
        table.remove(key);
    }

    private DNode<K,V> popTail() {
        DNode<K,V> tailNode = tail.pre;
        removeNode(tailNode);
        return tailNode;
    }

    private void addNode(DNode<K,V> node) {
        node.next = head.next;
        node.pre = head;
        head.next.pre = node;
        head.next = node;
    }

    private void moveToHead(DNode<K,V> node) {
        removeNode(node);
        addNode(node);
    }

    private void removeNode(DNode<K,V> node) {
        node.pre.next = node.next;
        node.next.pre = node.pre;
    }


    /**
     * 双向链表
     */
    private static class DNode<K,V> {
        private K key;
        private V value;
        private DNode<K,V> pre;
        private DNode<K,V> next;

        DNode(){ }

        DNode(K key,V value) {
            this.key = key;
            this.value = value;
        }
    }

    private void printAll() {
        DNode<K,V> node = this.head.next;
        while (node.next != null) {
            System.out.println(String.format("k=%s,v=%s",node.key,node.value));
            node = node.next;
        }
    }

    public static void main(String[] args) {

        LRU<Integer,String> lru = new LRU<>();
        lru.put(1,"1");
        lru.put(2,"2");
        lru.put(3,"3");
        lru.put(4,"4");
        lru.put(5,"5");
        lru.put(6,"6");
        lru.put(7,"7");
        lru.put(8,"8");
        lru.put(9,"9");
        lru.put(10,"10");
        lru.put(11,"11");
        lru.printAll();

        System.out.println("====================");

        String value = lru.get(7);
        System.out.println(value);

        System.out.println("====================");

        lru.remove(7);
        lru.printAll();


/*        HashMap<Integer, Integer> m = new LinkedHashMap<>(10, 0.75f,true);
        m.put(3, 11);
        m.put(1, 12);
        m.put(5, 23);
        m.put(2, 22);
        m.put(3, 26);
        m.get(5);

        for (Map.Entry e : m.entrySet()) {
            System.out.println(e.getKey());
        }*/
    }

}
