package Unit06_search;


/**
 * 二分查找
 * 是一种针对有序集合的查找算法。
 * 查找思想有点类似分治思想。每次都通过跟区间的中间元素对比，将待查找的区间缩小为之前的一半，直到找到要查找的元素，或者区间被缩小为 0。
 *
 * 时间复杂度：O(logn)
 * 假设数据大小是 n，每次查找后数据都会缩小为原来的一半。最坏情况下，直到查找区间被缩小为空，才停止。
 * 被查找区间大小的变化：n、n/2、n/4......n/2^k
 * 当n/2^k = 1时，k值就是总共缩小的次数，每一次缩小操作只涉及两个数据的大小比较，经过了k次操作，时间复杂度就为O(k)，
 * 通过 n/2k=1，我们可以求得 k=log2n，所以时间复杂度就是 O(logn)。
 *
 * 适用场景：
 * 1. 二分查找依靠顺序表结构，就是数组：
 * 因为需要按照下标随机访问元素，如果使用链表，查找的时间复杂度就变成了O(n)
 * 2. 只适用于有序数组：
 * 如果无序，需要先排序。但每次查找都要先排序，成本很高，所以适合插入、删除不频繁，一次排序多次查找的场景。
 * 3. 小数据量时不需要用二分查找：
 * 小数据量时，遍历和二分查找没多大区别
 * 例外是，比较特别耗时的话，推荐二次查找，因为可以减少比较次数，比如：数组中存储的都是长度超过300的字符串。
 * 4. 数据量太大也不适合二分查找
 * 二分查找需要依赖数组这种存储结构，而数组需要连续内存空间，对内存空间要求比较大。很可能会不够用。
 *
 * 其实，凡是用二分查找能解决的，绝大部分我们更倾向于用散列表或者二叉查找树。即便是二分查找在内存使用上更节省，但是毕竟内存如此紧缺的情况并不多。
 * 二分查找更适合用在“近似”查找问题，在这类问题上，二分查找的优势更加明显。比如二分查找的几种变体问题，用其他数据结构，比如散列表、二叉树，就比较难实现了。
 *
 */
public class BinarySearch {

    private final int[] arr;

    public BinarySearch() {
        // 最简单的情况，假设有序集合没有重复元素
        this.arr = new int[]{0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20};
    }


    /**
     * 实现方法1：用递归
     */
    private int searchV1(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;


        return search(searchValue,low,high);
    }

    private int search(int searchValue,int low,int high) {
        if (low > high) {
            return -1;
        }
        int mid = low + (high - low) / 2;
        if (searchValue == this.arr[mid]) {
            return mid;
        }
        if (searchValue < this.arr[mid]) {
            high = mid - 1;
        } else {
            low = mid + 1;
        }
        return search(searchValue,low,high);
    }

    /**
     * 实现方法2：不用递归
     */
    private int searchV2(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;

        while (low <= high) {  // 注意1：退出条件 <=
            int mid = low + (high - low) / 2; // 注意2：mid=(low+high)/2的写法求和可能会溢出  low+((high-low)>>1)位运算会快很多
            int nowValue = this.arr[mid];
            if (searchValue == nowValue) {
                return mid;
            }
            if (searchValue < nowValue) {
                high = mid - 1; // 注意3：+-1 当high=low=mid 并且arr[mid]!=searchValue时，就会死循环
            } else {
                low = mid + 1;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        BinarySearch binarySearch = new BinarySearch();
        int index1 = binarySearch.searchV1(17);
        System.out.println(index1);

        int index2 = binarySearch.searchV1(55);
        System.out.println(index2);

        int index3 = binarySearch.searchV2(17);
        System.out.println(index3);

        int index4 = binarySearch.searchV2(55);
        System.out.println(index4);
    }
}
