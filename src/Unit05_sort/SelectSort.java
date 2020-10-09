package Unit05_sort;

/**
 * 选择排序
 * 选择排序算法的实现思路有点类似插入排序，也分已排序区间和未排序区间。
 * 但是选择排序每次会从未排序区间中找到最小的元素，将其放到已排序区间的末尾。
 * 选择排序包含两个操作原子，查找和交换。
 *
 * 是原地排序算法么？是  不需要额外存储空间，空间复杂度O(1)
 * 是稳定排序算法么？否  比如5、8、5、2、9这样一组数据，第一次找到最小元素 2，与第一个 5 交换位置，那第一个 5 和中间的 5 顺序就变了。
 * 时间复杂度？
 * 最好时间复杂度  O(n^2)
 * 最快时间复杂度  O(n^2)
 * 平均时间复杂度  O(n^2)
 *
 * 因为排序算法是不稳定的，所以相对于冒泡和插入算法稍微逊色了。
 */
public class SelectSort {

    private final int[] arr;

    public SelectSort() {
        this.arr = new int[]{7,5,6,10,3,9,1};
    }

    public void asc() {
        for (int i = 0;i<arr.length-1;i++) {
            // 找到未排区间最小的
            int min = i;
            for (int j = i+1;j<arr.length;j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }
            // 交换
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
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
        SelectSort selectSort = new SelectSort();
        selectSort.asc();
        selectSort.printAll();
    }

}
