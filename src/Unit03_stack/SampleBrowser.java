package Unit03_stack;

/**
 * 应用场景
 * 1. 函数调用栈
 * 操作系统给每个线程分配了一块独立的内存空间，这块内存被组织成“栈”这种结构, 用来存储函数调用时的临时变量。
 * 每进入一个函数，就会将临时变量作为一个栈帧入栈，当被调用函数执行完成，返回之后，将这个函数对应的栈帧出栈。
 *
 * 2. 在表达式中的应用
 * 比如：34+13*9+44-12/3。
 * 编译器就是通过两个栈来实现的。其中一个保存操作数的栈，另一个是保存运算符的栈。
 * 我们从左向右遍历表达式，当遇到数字，我们就直接压入操作数栈；当遇到运算符，就与运算符栈的栈顶元素进行比较。
 * 如果比运算符栈顶元素的优先级高，就将当前运算符压入栈；
 * 如果比运算符栈顶元素的优先级低或者相同，从运算符栈中取栈顶运算符，从操作数栈的栈顶取 2 个操作数，然后进行计算，再把计算完的结果压入操作数栈，继续比较。
 *
 * 3. 检查表达式中的括号是否匹配
 * 假设表达式中只包含三种括号，圆括号 ()、方括号[]和花括号{}，并且它们可以任意嵌套。比如，{[] ()[{}]}或[{()}([])]等都为合法格式，而{[}()]或[({)]为不合法的格式。
 * 我们用栈来保存未匹配的左括号，从左到右依次扫描字符串。
 * 当扫描到左括号时，则将其压入栈中；
 * 当扫描到右括号时，从栈顶取出一个左括号。如果能够匹配，比如“(”跟“)”匹配，“[”跟“]”匹配，“{”跟“}”匹配，则继续扫描剩下的字符串。
 * 如果扫描的过程中，遇到不能配对的右括号，或者栈中没有数据，则说明为非法格式。
 * 当所有的括号都扫描完成之后，如果栈为空，则说明字符串为合法格式；否则，说明有未匹配的左括号，为非法格式。
 *
 * 4. 浏览器前进后退功能
 * 使用两个栈，X 和 Y，
 * 我们把首次浏览的页面依次压入栈 X，当点击后退按钮时，再依次从栈 X 中出栈，并将出栈的数据依次放入栈 Y。
 * 当我们点击前进按钮时，我们依次从栈 Y 中取出数据，放入栈 X 中。
 * 当栈 X 中没有数据时，那就说明没有页面可以继续后退浏览了。
 * 当栈 Y 中没有数据，那就说明没有页面可以点击前进按钮浏览了。
 *
 */
public class SampleBrowser {

    String currentUrl = null;
    ArrayStack stackForward = new ArrayStack(); // 存放前进页面的栈
    ArrayStack stackBack = new ArrayStack(); // 存放后退页面的栈

    private void open(String url) {
        if (currentUrl != null) {
            stackBack.pushDynamic(currentUrl);
            // 无法按照原先前进后退，需要清空Y
            stackForward.clear();
        }
        currentUrl = url;
        System.out.println("open 当前所在页面："+currentUrl);
    }

    private void goForward() {
        if (!canForward()) {
            System.out.println("forward 当前已经是最新一个页面，无法继续前进");
            return;
        }
        String forward = stackForward.pop();
        stackBack.pushDynamic(currentUrl);
        currentUrl = forward;
        System.out.println("forward 当前所在页面："+currentUrl);
    }

    private void goBack() {
        if (!canBack()) {
            System.out.println("back 当前已经是最后一个页面，无法继续后退");
            return;
        }
        String back = stackBack.pop();
        stackForward.pushDynamic(currentUrl);
        currentUrl = back;
        System.out.println("back 当前所在页面："+currentUrl);

    }

    private boolean canBack() {
        return stackBack.count > 0;
    }

    private boolean canForward() {
        return stackForward.count > 0;
    }

    public static void main(String[] args) {

        SampleBrowser browser = new SampleBrowser();

        // 三次点击
        browser.open("https://demo1.com");
        browser.open("https://demo2.com");
        browser.open("https://demo3.com");

        // 后退一步
        browser.goBack();

        // 再后退一步
        browser.goBack();

        // 前进一步
        browser.goForward();

        // 又点击
        browser.open("https://demo4.com");

        // 前进一步
        browser.goForward();
    }
}
