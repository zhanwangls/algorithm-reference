package Unit05_sort;

/**
 * 插入排序
 *
 * 先来看一个问题。一个有序的数组，我们往里面添加一个新的数据后，如何继续保持数据有序呢？
 * 我们只要遍历数组，找到数据应该插入的位置将其插入即可。
 * 这是一个动态排序的过程，即动态地往有序集合中添加数据，我们可以通过这种方法保持集合中的数据一直有序。
 * 而对于一组静态数据，我们也可以借鉴上面讲的插入方法，来进行排序，于是就有了插入排序算法。
 *
 * 插入排序将数组中的数据分为两个区间，已排序区间和未排序区间。
 * 初始已排序区间只有一个元素，就是数组的第一个元素。
 * 插入算法的核心思想是取未排序区间中的元素，在已排序区间中找到合适的插入位置将其插入，并保证已排序区间数据一直有序。
 * 重复这个过程，直到未排序区间中元素为空，算法结束。
 * 冒泡排序包含两个操作原子，比较和移动。
 *
 * 是原地排序算法么？是  不需要额外存储空间，空间复杂度O(1)
 * 是稳定排序算法么？是  值相同的元素可以选择插入到后面
 * 时间复杂度？
 * 最好时间复杂度  O(n)    数据已经在有序的
 * 最快时间复杂度  O(n^2)  数据是倒序的
 * 平均时间复杂度  O(n^2)  数组中插入一个元素的平均复杂度O(n)，循环执行n次
 */
public class InsertSort {

    private int[] arr;

    public InsertSort() {
        this.arr = new int[]{7,5,6,10,3,9,1};
    }

    private void asc() {
        // i = 2  j = 1
        for (int i = 1;i<arr.length;i++) {
            int value = arr[i]; // 未排区间取出一个
            int j = i - 1; // 已排区间界限
            for (;j>=0;j--) {
                if (arr[j] >= value) {
                    arr[j+1] = arr[j]; // 数据移动，从后往前挪
                } else {
                    break;
                }
            }
            arr[j+1] = value;
        }
    }

    private void printAll() {
        StringBuilder sb = new StringBuilder();
        for (int i:arr) {
            sb.append(i).append("\t");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        InsertSort insertSort = new InsertSort();
        insertSort.asc();
        insertSort.printAll();
    }

}
