package Unit05_sort;

/**
 * 计数排序
 * 计数排序其实是桶排序的一种特殊情况。
 * 当要排序的 n 个数据，所处的范围并不大的时候，比如最大值是 k，我们就可以把数据划分成 k 个桶。
 * 每个桶内的数据值都是相同的，省掉了桶内排序的时间。
 *
 * eg：高考查分数的时候，系统会显示我们的成绩以及所在省的排名。如果你所在的省有 50 万考生，如何通过成绩快速排序得出名次呢？
 * 考生的满分是 900 分，最小是 0 分，这个数据的范围很小，所以我们可以分成 901 个桶，对应分数从 0 分到 900 分。
 * 根据考生的成绩，我们将这 50 万考生划分到这 901 个桶里。桶内的数据都是分数相同的考生，所以并不需要再进行排序。
 * 我们只需要依次扫描每个桶，将桶内的考生依次输出到一个数组中，就实现了 50 万考生的排序。
 *
 * 时间复杂度： O(n)  只涉及扫描遍历操作
 *
 * 局限性：
 * 1. 只能用在数据范围不大的场景中
 * 2. 计数排序只能给非负整数排序，如果要排序的数据是其他类型的，要将其在不改变相对大小的情况下，转化为非负整数。
 * eg：如果考生成绩精确到小数后一位，我们就需要将所有的分数都先乘以 10，转化成整数，然后再放到 9010 个桶内。
 * eg：如果要排序的数据中有负数，数据的范围是[-1000, 1000]，那我们就需要先对每个数据都加 1000，转化成非负整数。
 *
 */
public class CountSort {

    private int[] arr;

    public CountSort() {
        this.arr = new int[]{2,5,3,7,2,3,7,-1,3};
    }

    // 假设数组都是整数
    private void asc() {
        // 分桶，存储该值出现的次数（计算最小值相当于对负数进行移位）
        int min = arr[0];
        int max = arr[0];
        for (int v:this.arr) {
            if (v < min) {
                min = v;
            } else if (v > max) {
                max = v;
            }
        }
        int bucketCount = max - min + 1;
        int[] tmpBucket = new int[bucketCount];
        for (int v:this.arr) {
            tmpBucket[v-min]++;
        }
        // 2 3 0 1 0 2
        // 顺序求和，求出小于等于当前值的个数和
        for (int i = 1;i<tmpBucket.length;i++) {
            tmpBucket[i] = tmpBucket[i] + tmpBucket[i-1];
        }
        // 2 5 5 6 6 8
        // 排序，扫描到某个值时，对应sumBucket中的值，就是这个值的位置，将值放回到数组中相应的位置，sumBucket因为取出了一个，所以要减1
        int[] newArr = new int[this.arr.length];
        for (int i = this.arr.length-1;i>=0;i--) { // 注意，从后向前扫描才是稳定排序
            int value = this.arr[i];
            int position = tmpBucket[value - min] - 1;
            newArr[position] = value;
            tmpBucket[value - min]--;
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
        CountSort countSort = new CountSort();
        countSort.asc();
        countSort.printAll();
    }
}
