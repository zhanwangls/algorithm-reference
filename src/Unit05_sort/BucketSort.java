package Unit05_sort;

/**
 * 桶排序
 * 核心思想是将要排序的数据分到几个有序的桶里，每个桶里的数据再单独进行排序。
 * 桶内排完序之后，再把每个桶里的数据按照顺序依次取出，组成的序列就是有序的了。
 *
 * 桶排序分解图：
 * 在0-50之间的订单桶排序， 22 5 11 41 45 26 29 10 7 8 30 27 42 43 40
 * 分桶：
 * 0-9：5 7 8
 * 10-19：10 11
 * 20-29：22 26 27 29
 * 30-39：30
 * 40-49：40 41 42 43 45
 *
 * 时间复杂度：O(n)
 * 如果要排序的数据有 n 个，我们把它们均匀地划分到 m 个桶内，每个桶里就有 k=n/m 个元素。
 * 每个桶内部使用快速排序，时间复杂度为 O(k * logk)。
 * m 个桶排序的时间复杂度就是 O(m * k * logk)。
 * 因为 k=n/m，所以整个桶排序的时间复杂度就是 O(n*log(n/m))。
 * 当桶的个数 m 接近数据个数 n 时，log(n/m) 就是一个非常小的常量，这个时候桶排序的时间复杂度接近 O(n)。
 *
 * 桶排序对要排序数据的要求是非常苛刻的：
 * 1. 要排序的数据需要很容易就能划分成 m 个桶，并且，桶与桶之间有着天然的大小顺序。
 *    这样每个桶内的数据都排序完之后，桶与桶之间的数据不需要再进行排序。
 * 2. 数据在各个桶之间的分布是比较均匀的。
 *    如果数据经过桶的划分之后，有些桶里的数据非常多，有些非常少，很不平均，那桶内数据排序的时间复杂度就不是常量级了。
 *    在极端情况下，如果数据都被划分到一个桶里，那就退化为 O(nlogn) 的排序算法了。
 *
 * 适用场景：外部排序
 * （所谓的外部排序就是数据存储在外部磁盘中，数据量比较大，内存有限，无法将数据全部加载到内存中。）
 * eg：有 10GB 的订单数据，我们希望按订单金额（假设金额都是正整数）进行排序，但是我们的内存有限，只有几百 MB，没办法一次性把 10GB 的数据都加载到内存中。怎样解决呢？
 * 可以先扫描一遍文件，看订单金额所处的数据范围。假设经过扫描之后我们得到，订单金额最小是 1 元，最大是 10 万元。
 * 我们将所有订单根据金额划分到 100 个桶里，第一个桶我们存储金额在 1 元到 1000 元之内的订单，第二桶存储金额在 1001 元到 2000 元之内的订单，以此类推。
 * 每一个桶对应一个文件，并且按照金额范围的大小顺序编号命名（00，01，02…99）。
 * 理想的情况下，如果订单金额在 1 到 10 万之间均匀分布，那订单会被均匀划分到 100 个文件中，每个小文件中存储大约 100MB 的订单数据，
 * 我们就可以将这 100 个小文件依次放到内存中，用快排来排序。
 * 等所有文件都排好序之后，我们只需要按照文件编号，从小到大依次读取每个小文件中的订单数据，并将其写入到一个文件中，那这个文件中存储的就是按照金额从小到大排序的订单数据了。
 * 不过，订单按照金额在 1 元到 10 万元之间并不一定是均匀分布的 ，所以 10GB 订单数据是无法均匀地被划分到 100 个文件中的。
 * 有可能某个金额区间的数据特别多，划分之后对应的文件就会很大，没法一次性读入内存。
 * 针对这些划分之后还是比较大的文件，我们可以继续划分。
 * 比如，订单金额在 1 元到 1000 元之间的比较多，我们就将这个区间继续划分为 10 个小区间，1 元到 100 元，101 元到 200 元，201 元到 300 元…901 元到 1000 元。
 * 如果划分之后，101 元到 200 元之间的订单还是太多，无法一次性读入内存，那就继续再划分，直到所有的文件都能读入内存为止。
 *
 */
public class BucketSort {

    private final int[] arr;

    private final int capacity = 5; // 假设每个数组容量为5，可以根据情况调整

    public BucketSort() {
        this.arr = new int[]{66,57,49,33,100,95,85,79,71,25,90,82,17,8,0,38,35,32,31,30,36,37,28,10,1,2,5};
    }

    private void asc() {
        // 找到数组中，金额区间
        int min = arr[0];
        int max = arr[0];
        for (int i = 1;i<arr.length;i++){
            int value = arr[i];
            if (value < min) {
                min = arr[i];
            } else if (value > max){
                max = arr[i];
            }
        }

        // 分桶
        int bucketCount = (max - min) / capacity + 1;
        int[][] bucket = new int[bucketCount][this.capacity];
        int[] indexArr = new int[bucketCount]; // 当前桶存放的数量

        // 将数据分到桶里
        for (int value : arr) {
            int bucketIndex = (value - min) / capacity;
            // 扩容（扩容算法也可以有多种，比如2倍扩容。这里考虑到题目中文件大小，所以不一直扩充桶的容量）
            if (indexArr[bucketIndex] >= this.capacity) {
                int[][] newBucket = new int[bucket.length + 1][this.capacity];
                for (int j = bucket.length - 1; j >= 0; j--) {
                    if (j > bucketIndex) {
                        newBucket[j + 1] = bucket[j];
                    } else {
                        newBucket[j] = bucket[j];
                    }
                }
                bucket = newBucket;
                int[] newIndexArr = new int[indexArr.length + 1];
                for (int j = indexArr.length - 1; j >= 0; j--) {
                    if (j > bucketIndex) {
                        newIndexArr[j + 1] = indexArr[j];
                    } else {
                        newIndexArr[j] = indexArr[j];
                    }
                }
                indexArr = newIndexArr;
                bucketIndex++;
            }
            bucket[bucketIndex][indexArr[bucketIndex]++] = value;
        }

        // 桶内快速排序，依次取出放回数组（注意空桶，和未满的桶，int[]初始化都是0）
        int index = 0;
        for (int i = 0;i<bucket.length;i++) {
            if (indexArr[i] == 0) {
                continue;
            }
            int[] value = bucket[i];
            quickSort(value,0,indexArr[i]-1);

            for (int j = 0;j<indexArr[i];j++) {
                arr[index++] = value[j];
            }
        }

    }

    private void quickSort(int[] value,int startIndex, int endIndex) {
        if (startIndex>=endIndex) {
            return;
        }
        int point = quickSortPart(value,startIndex,endIndex);
        quickSort(value,startIndex,point-1);
        quickSort(value,point+1,endIndex);
    }

    private int quickSortPart(int[] value, int startIndex, int endIndex) {
        int i = startIndex; // 已分区和未分区的临界点
        int j = i; // 当前正在取的index
        int baseValue = value[endIndex];

        while (j<endIndex) {
            if (value[j]>baseValue) {
                j++;
            } else if (i == j) {
                i++;
                j++;
            } else {
                int temp = value[j];
                value[j++] = value[i];
                value[i++] = temp;
            }
        }
        value[j] = value[i];
        value[i] = baseValue;
        return i;
    }

    private void printAll() {
        StringBuilder sb = new StringBuilder();
        for (int i:arr) {
            sb.append(i).append("\t");
        }
        System.out.println(sb.toString());
    }

    public static void main(String[] args) {
        BucketSort bucketSort = new BucketSort();
        bucketSort.asc();
        bucketSort.printAll();
    }


}
