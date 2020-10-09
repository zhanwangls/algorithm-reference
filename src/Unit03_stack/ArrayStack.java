package Unit03_stack;

/**
 * 顺序栈：用数组实现的栈
 * 空间复杂度：O(1)
 * 时间复杂度：O(1)
 */
public class ArrayStack {

    String[] arr; // 定义一个栈
    int count; // 栈中元素的大小
    int length; // 数组(栈)的大小

    private static final int DEFAULT_LENGTH = 8;

    public ArrayStack() {
        this.length = DEFAULT_LENGTH;
        this.arr = new String[this.length];
        this.count = 0;
    }

    public String pop() {
        if (count <= 0) {
            return null;
        }
        String pop = arr[count-1];
        --count;
        return pop;
    }

    public Boolean push(String str) {
        if (count>=length) {
            return false;
        }
        arr[count] = str;
        ++count;
        return true;
    }

    /**
     * 支持动态扩容的栈
     * 当栈中有空闲空间时，时间复杂度: O(1)
     * 当空间不够时，需要重新申请内存和数据搬移，时间复杂度: O(n)
     * 均摊时间复杂度: O(1)
     */
    public void pushDynamic(String str) {
        if (count>=length) {
            int newLength = length * 2;
            String[] newArr = new String[newLength];
            System.arraycopy(arr, 0, newArr, 0, length);
            arr = newArr;
            length = newLength;
        }
        arr[count] = str;
        ++count;
    }

    public void clear() {
        this.length = DEFAULT_LENGTH;
        this.arr = new String[this.length];
        this.count = 0;
    }

    private static void printAll(ArrayStack stack) {
        StringBuilder out = new StringBuilder();
        int i = 0;
        while (i < stack.count) {
            out.append(stack.arr[i]).append("\t");
            ++i;
        }
        System.out.println(out);
    }

    public static void main(String[] args) {
        ArrayStack arrayStack = new ArrayStack();

        arrayStack.push("01");
        arrayStack.push("02");
        printAll(arrayStack);

        System.out.println(arrayStack.pop());
        printAll(arrayStack);

        arrayStack.pushDynamic("03");
        arrayStack.pushDynamic("04");
        arrayStack.pushDynamic("05");
        arrayStack.pushDynamic("06");
        arrayStack.pushDynamic("07");
        arrayStack.pushDynamic("08");
        arrayStack.pushDynamic("09");
        arrayStack.pushDynamic("10");
        printAll(arrayStack);
    }
}
