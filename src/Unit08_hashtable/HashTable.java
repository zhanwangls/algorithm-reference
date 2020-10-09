package Unit08_hashtable;

/**
 * 散列表
 */
public class HashTable<K,V> {

    private static final int INIT_CAPACITY = 8;
    private static final float LOAD_FACTOR = 0.75f;

    /**
     * 散列表
     * 每个槽位是一个链表
     */
    private Entry<K,V>[] table;

    /**
     * 实际元素数量
     */
    private int useSize = 0;

    HashTable() {
        table = new Entry[INIT_CAPACITY];
    }

    /**
     * 插入操作
     */
    public void put(K key,V value) {
        int index = hash(key);
        // 位置未被引用，创建哨兵
        if (table[index] == null) {
            table[index] = new Entry<>(null,null);
        }

        Entry<K,V> entry = table[index];
        if (entry.next == null) {
            // 如果没有冲突直接将节点放上去
            entry.next = new Entry<>(key,value);
            useSize++;
            // 扩容
            if (useSize >= table.length * LOAD_FACTOR) {
                resize();
            }
        } else {
            // 如果有冲突，链表法解决
            // 插入到头部遍历时的顺序是按照key hash的顺序，如果插入到尾部，并且key相同的旧数据也移到尾部，遍历属性就是操作顺序
            while (entry.next != null) {
                // key相同覆盖旧的数据
                if (entry.next.key == key) {
                    entry.next.value = value;
                    return;
                }
                entry = entry.next;
            }
            // key不同，插入到链表头部
            Entry<K,V> temp = table[index].next;
            table[index] = new Entry<>(key,value);
            table[index].next = temp;
            useSize++;
        }
    }

    /**
     * 删除操作
     */
    public void remove(K key) {
        int index = hash(key);
        Entry<K,V> entry = table[index];
        if (entry == null) {
            return;
        }
        Entry<K,V> pre;
        while (entry.next != null) {
            pre = entry;
            entry = entry.next;
            if (entry.key == key) {
                useSize--;
                pre.next = entry.next;
                Entry<K,V> headNode = table[index];
                return;
            }
        }
    }

    /**
     * 查找操作
     */
    public V get(K key) {
        int index = hash(key);
        Entry<K,V> entry = table[index];
        if (entry == null) {
            return null;
        }
        while (entry.next != null) {
            entry = entry.next;
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    /**
     * 扩容
     */
    private void resize() {
        // 先复制一份，慢慢搬移，这样put时，如果没有搬移完不会冲突
        Entry<K,V>[] oldTable = table;
        table = new Entry[table.length * 2];
        useSize = 0;
        for (Entry<K,V> entry:oldTable) {
            if (entry == null) {
                continue;
            }
            while (entry.next != null) {
                entry = entry.next;
                int index = hash(entry.key);
                if (table[index] == null) {
                    // 创建哨兵
                    table[index] = new Entry<>(null,null);
                }
                useSize++;
                table[index].next = entry;
            }
        }
    }

    /**
     * 散列函数
     * 参考HashMap
     */
    private int hash(K key) {
        int h;
        return (key == null) ? 0 : ((h = key.hashCode()) ^ (h >>> 16)) % table.length;
    }

    /**
     * 链表
     * 用于链表法解决散列冲突
     */
    private static class Entry<K,V> {
        private K key;
        private V value;
        Entry<K,V> next;

        Entry(K key,V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }

    private void printAll() {
        for (Entry<K,V> entry:table) {
            while (entry != null) {
                System.out.println(String.format("k=%s,v=%s",entry.key,entry.value));
                entry = entry.next;
            }
        }
    }

    public static void main(String[] args) {

        HashTable<Integer,String> hashTable = new HashTable<>();
        hashTable.put(1,"1");
        hashTable.put(2,"2");
        hashTable.put(3,"3");
        hashTable.put(4,"4");
        hashTable.put(4,"4-1"); // 覆盖掉4
        hashTable.put(5,"5");
        hashTable.put(6,"6");
        hashTable.put(7,"7");
        hashTable.put(8,"8");
        hashTable.put(9,"9");
        hashTable.put(10,"10");
        hashTable.remove(5);
        hashTable.put(5,"5"); // 遍历时的输入顺序，依然在4的后面

        hashTable.printAll();

        String getHash = hashTable.get(3);
        System.out.println(getHash);

    }

}
