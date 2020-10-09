package Unit06_search;

/**
 * 二分查找的变形问题
 *
 * 有一个说法：“十个二分九个错”。二分查找虽然原理极其简单，但是想要写出没有 Bug 的二分查找并不容易。
 * 唐纳德·克努特（Donald E.Knuth）在《计算机程序设计艺术》的第 3 卷《排序和查找》中说到：
 * “尽管第一个二分查找算法于 1946 年出现，然而第一个完全正确的二分查找算法实现直到 1962 年才出现。”
 * 而很多时候觉得难是因为过于追求完美简洁的写法，但是代码的易读性、没bug也很重要。
 *
 * 4中常见的变形问题
 * 1. 查找第一个值等于给定值的元素
 * 2. 查找最后一个值等于给定值的元素
 * 3. 查找第一个大于等于给定值的元素
 * 4. 查找最后一个小于等于给定值的元素
 *
 *
 *
 */
public class BinarySearchTransform {

    private final int[] arr;

    public BinarySearchTransform() {
        // 变形，有序集合有重复元素
        this.arr = new int[]{0,1,2,3,4,5,5,6,7,8,9,10};
    }

    /**
     * 查找第一个值等于给定值的元素
     */
    private int transV1(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (searchValue < this.arr[mid]) {
                high = mid - 1;
            } else if (searchValue > this.arr[mid]) {
                low = mid + 1;
            } else {
                if (mid == 0 || searchValue != this.arr[mid - 1]) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 查找最后一个值等于给定值的元素
     */
    private int transV2(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (searchValue < this.arr[mid]) {
                high = mid - 1;
            } else if (searchValue > this.arr[mid]) {
                low = mid + 1;
            } else {
                if (mid == this.arr.length-1 || searchValue != this.arr[mid + 1]) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    /**
     * 查找第一个大于等于给定值的元素
     */
    private int transV3(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (this.arr[mid] < searchValue) {
                low = mid + 1;
            } else {
                if (mid == 0 || this.arr[mid - 1] < searchValue) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }

    /**
     * 查找最后一个小于等于给定值的元素
     */
    private int transV4(int searchValue) {
        int low = 0;
        int high = this.arr.length - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            if (this.arr[mid] > searchValue) {
                high = mid - 1;
            } else {
                if (mid == this.arr.length-1 || this.arr[mid + 1] > searchValue) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }

        return -1;
    }

    /**
     * TODO  应用：如果快速定位一个ip地址归属地
     * 地址库与ip段对应关系如下：
     * [202.102.133.0, 202.102.133.255]  山东东营市
     * [202.102.135.0, 202.102.136.255]  山东烟台
     * [202.102.156.34, 202.102.157.255] 山东青岛
     * [202.102.48.0, 202.102.48.255] 江苏宿迁
     * [202.102.49.15, 202.102.51.251] 江苏泰州
     * [202.102.56.0, 202.102.56.255] 江苏连云港
     * 思路：
     * 如果 IP 区间与归属地的对应关系不经常更新，我们可以先预处理这 12 万条数据，让其按照起始 IP 从小到大排序。
     * 我们知道，IP 地址可以转化为 32 位的整型数。所以，我们可以将起始地址，按照对应的整型值的大小关系，从小到大进行排序。
     * 然后，这个问题就可以转化为第四种变形问题“在有序数组中，查找最后一个小于等于某个给定值的元素”了。
     */

    public static void main(String[] args) {
        BinarySearchTransform bs = new BinarySearchTransform();
        System.out.println(bs.transV1(5));
        System.out.println(bs.transV2(5));
        System.out.println(bs.transV3(5));
        System.out.println(bs.transV4(5));
    }
}
