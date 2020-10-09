package Unit05_sort;

/**
 * 基数排序
 *
 * eg：我们有 10 万个手机号码，希望将这 10 万个手机号码从小到大排序，有什么比较快速的排序方法呢？
 * 规律：比较两个手机号码 a，b 的大小，如果在前面几位中，a 手机号码已经比 b 手机号码大了，那后面的几位就不用看了。
 * 实现方法：
 * 借助稳定排序算法，先按照最后一位来排序手机号码，然后，再按照倒数第二位重新排序，以此类推，最后按照第一位重新排序。
 * 经过 11 次排序之后，手机号码就都有序了。
 *
 * eg：数据不等长的情况呢，比如英文单词排序？
 * 可以把所有的单词补齐到相同长度，位数不够的可以在前面补“0”，
 * 因为根据ASCII 值，所有字母都大于“0”，所以补“0”不会影响到原有的大小顺序。
 * 这样就可以继续用基数排序了。
 *
 * 适用场景：
 * 1. 对要排序的数据需要可以分割出独立的“位”来比较，而且位之间有递进的关系，如果 a 数据的高位比 b 数据大，那剩下的低位就不用比较了。
 * 2. 每一位的数据范围不能太大，要可以用线性排序算法来排序，因为每一位的排序需要借助桶排序或者计数排序。
 *    如果要排序的数据有 k 位，那我们就需要 k 次桶排序或者计数排序，总的时间复杂度是 O(k*n)。
 *    当 k 不大的时候，比如手机号码排序的例子，k 最大就是 11，所以基数排序的时间复杂度就近似于 O(n)。
 *
 * 时间复杂度：O(n)
 *
 */
public class RadixSort {

    private int[] arr;

    public RadixSort() {
        this.arr = new int[]{1467,2904,1873,101};
    }

    // 假设都是正整数
    public void asc() {

        // 求最大值，以便获取最大位数
        int max = this.arr[0];
        for (int v: this.arr){
            if (v > max){
                max = v;
            }
        }

        // 对每一位依次进行排序
        for (int exp = 1;max/exp>0;exp*=10) {
            countSort(arr,exp);
        }

    }

    /**
     * @param arr
     * @param exp 位数   各位、百位、千位......
     */
    private void countSort(int[] arr,int exp) {

        // 分桶、计数  范围是0-9（因为数组初始化都是0，所以不足的位已经补0了）
        int[] countBucket = new int[10];
        for (int item : arr) {
            int value = item / exp % 10;
            countBucket[value]++;
        }

        // 求和
        for (int i = 1;i<countBucket.length;i++) {
            countBucket[i] += countBucket[i-1];
        }

        // 排序
        int[] newArr = new int[arr.length];
        for (int i = arr.length-1;i>=0;i--) {
            int value = arr[i] / exp % 10;
            int position = countBucket[value]-1;
            newArr[position] = arr[i];
            countBucket[value]--;
        }
        this.arr = newArr;
    }

    private void printAll() {
        StringBuilder sb = new StringBuilder();
        for (int i:arr) {
            sb.append(i).append("\t");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        RadixSort radixSort = new RadixSort();
        radixSort.asc();
        radixSort.printAll();
    }
}
