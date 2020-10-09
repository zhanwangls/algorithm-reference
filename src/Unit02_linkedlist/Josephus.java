package Unit02_linkedlist;

import java.util.ArrayList;
import java.util.List;

/**
 * 约瑟夫问题
 * N个人围成一圈，从第一个开始报数，第M个将被杀掉，最后剩下一个，其余人都将被杀掉。
 * 数据结构：循环链表
 *
 * TODO: 目前是自己的实现，看下别人的更优实现
 */
public class Josephus {

    public static void main(String[] args) {
        int peopleCount = 6;
        int m = 5;
        int count = 0;
        int aliveCount = peopleCount;
        List<People> peoples = init(peopleCount);
        People people = peoples.get(0);
        while (true) {
            if (people.alive) {
                if (aliveCount <= 1) {
                    System.out.println("活下来的是第"+people.getNum()+"个人");
                    break;
                }
                ++count;
            }
            if (count == m) {
                people.setAlive(false);
                --aliveCount;
                count = 0;
            }
            people = people.next;
        }

    }

    private static List<People> init(int n) {
        List<People> peoples = new ArrayList<>(n);
        for (int i = 0;i<n;i++) {
            People p = new People();
            p.setAlive(true);
            p.setNum(i + 1);
            peoples.add(i,p);
        }
        for (int i = 0;i<n;i++) {
            People p = peoples.get(i);
            if (i == n-1) {
                p.setNext(peoples.get(0));
            } else {
                p.setNext(peoples.get(i + 1));
            }
        }
        return peoples;
    }

    private static class People {
        private int num;
        private boolean alive;
        private People next;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public void setAlive(boolean alive) {
            this.alive = alive;
        }

        public void setNext(People next) {
            this.next = next;
        }
    }

}
