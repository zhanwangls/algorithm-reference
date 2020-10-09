package Unit05_sort;

/**
 * 归并排序
 * 如果要排序一个数组，我们先把数组从中间分成前后两部分，然后对前后两部分分别排序，再将排好序的两部分合并在一起，这样整个数组就都有序了。
 * 归并排序使用的就是分治思想。分治，顾名思义，就是分而治之，将一个大问题分解成小的子问题来解决。小的子问题解决了，大问题也就解决了。
 * 分治算法一般都是用递归来实现的。分治是一种解决问题的处理思想，递归是一种编程技巧。
 *
 * 归并排序分解图
 *                 11  8  3  9  7  1  2  5
 * 分解-->     [11  8  3  9]      [7  1  2  5]
 * 分解-->     [11  8]  [3  9]   [7  1]  [2  5]
 * 分解-->     [11] [8] [3] [9] [7] [1] [2] [5]
 * 合并-->     [8  11]  [3  9]   [1  7]  [2  5]
 * 合并-->     [3  8  9  11]      [1  2  5  7]
 * 合并-->          1  2  3  5  7  8  9  11
 *
 * 是稳定排序算法么？  是    mergeSegment函数，如果左右两边的段有相等的值，先放左边的值就可以保证稳定
 *
 * 时间复杂度？  O(nlogn)
 * 我们假设对 n 个元素进行归并排序需要的时间是 T(n)，那分解成两个子数组排序的时间都是 T(n/2)。
 * 我们知道，merge() 函数合并两个有序子数组的时间复杂度是 O(n)。
 * 所以，归并排序的时间复杂度的计算公式就是：（实现方式可以用递归公式表示，复杂度计算也可以用递归公式表示）
 * T(1) = C；   n=1时，只需要常量级的执行时间，所以表示为C。
 * T(n) = 2*T(n/2) + n； n>1
 * 求解T(n)过程：
 * T(n) = 2*T(n/2) + n
 * = 2*(2*T(n/4) + n/2) + n = 4*T(n/4) + 2*n
 * = 4*(2*T(n/8) + n/4) + 2*n = 8*T(n/8) + 3*n
 * = 8*(2*T(n/16) + n/8) + 3*n = 16*T(n/16) + 4*n
 * ......
 * = 2^k * T(n/2^k) + k * n
 * ......
 * 我们可以得到 T(n) = 2^kT(n/2^k)+kn。
 * 当 T(n/2^k)=T(1) 时，也就是 n/2^k=1，我们得到 k=log2n 。
 * 我们将 k 值代入上面的公式，得到 T(n)=Cn+nlog2n 。
 * 如果我们用大 O 标记法来表示的话，T(n) 就等于 O(nlogn)。
 * 所以归并排序的时间复杂度是 O(nlogn)。
 * 经代码分析，归并排序的执行效率与要排序的原始数组的有序程度无关，所以其时间复杂度是非常稳定的，
 * 不管是最好情况、最坏情况，还是平均情况，时间复杂度都是 O(nlogn)。
 *
 * 空间复杂度：
 * O(n)  不是原地排序
 * 递归代码的空间复杂度并不能像时间复杂度那样累加。
 * 因为尽管每次合并操作都需要申请额外的内存空间，但在合并完成之后，临时开辟的内存空间就被释放掉了。
 * 在任意时刻，CPU 只会有一个函数在执行，也就只会有一个临时的内存空间在使用。
 * 临时内存空间最大也不会超过 n 个数据的大小，所以空间复杂度是 O(n)。
 *
 */
public class MergeSort {

    private final int[] arr;

    public MergeSort() {
        this.arr = new int[]{7,5,6,10,3,9,1};
    }

    private void asc() {
        segment(0,arr.length-1);
    }

    private void segment(int startIndex,int endIndex) {
        int midIndex = startIndex + (endIndex - startIndex) / 2;
        if (midIndex >= endIndex) {
            return;
        }

        segment(startIndex,midIndex);
        segment(midIndex+1,endIndex);

        mergeSegment(startIndex,endIndex,midIndex);
    }

    private void mergeSegment(int startIndex,int endIndex,int midIndex) {
        // 数据长度比原数据多一个，为了存放哨兵
        int leftLen = midIndex-startIndex+1;
        int[] leftArr = new int[leftLen+1];
        int rightLen = endIndex-midIndex;
        int[] rightArr = new int[rightLen+1];

        System.arraycopy(arr,startIndex,leftArr,0,leftLen);
        System.arraycopy(arr,startIndex+leftLen,rightArr,0,rightLen);

        // 添加哨兵
        leftArr[leftLen] = Integer.MAX_VALUE;
        rightArr[rightLen] = Integer.MAX_VALUE;

        // 比较
        int l = 0;
        int r = 0;
        int k = startIndex;
        while (k<=endIndex) {
            // 当左边数组到达哨兵值时，i不再增加，直到右边数组读取完剩余值，同理右边数组也一样
            if (leftArr[l] <= rightArr[r]) { // 小于等于保证排序稳定
                arr[k++] = leftArr[l++];
            } else {
                arr[k++] = rightArr[r++];
            }
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
        MergeSort mergeSort = new MergeSort();
        mergeSort.asc();
        mergeSort.printAll();
    }

}
