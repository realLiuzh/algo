package skiplist;

import java.util.Arrays;

/**
 * 跳表的一种实现
 * 带头
 *
 * @author 志昊的刘
 * @date 2022/5/23
 */
public class SkipList {

    public static final double FACTOR = 0.5f;
    public static final int MAX_LEVEL = 16;

    private int levelCount = 1;

    private Node head = new Node();


    {
        head.value = -1;
        head.maxLevel = MAX_LEVEL;
        head.forwards = new Node[MAX_LEVEL];
    }

    /**
     * 插入数据
     *
     * @param value 待插入的数
     */
    public void insert(int value) {
        // 新节点的随机层级
        int level = randomLevel();
        // 构建新节点
        Node newNode = new Node();
        newNode.value = value;
        newNode.maxLevel = level;
        newNode.forwards = new Node[level];
        // 用于每层保存要更新的节点，因为新节点的最高层级为level，所以数组长为level
        Node[] update = new Node[level];
        // 先将每层要更新的节点设成头节点
        Arrays.fill(update, head);

        // 找到每层真正要修改的节点
        // update[i]表示待要修改的第i层的节点
        // forwards[i]是该节点在第i层的后继节点
        // 用一个不知道是否恰当的比喻,可以把p看做一条泥鳅或者一个贪吃蛇,贯穿整个跳表
        Node p = head;
        for (int i = level - 1; i >= 0; i--) {
            //这里好像并没有起到跳表的作用.每一层都是从头遍历
            while (p.forwards[i] != null && p.forwards[i].value < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // 真正地插入节点
        for (int i = level - 1; i >= 0; i--) {
            newNode.forwards[i] = update[i].forwards[i];
            update[i].forwards[i] = newNode;
        }

        if (levelCount < level) levelCount = level;
    }

    /**
     * 删除指定数据
     *
     * @param value 待删除的数据
     */
    public void delete(int value) {
        // 我们不知道待删除的数据到底有多少层，所以我们要从最高层开始遍历
        // 我们可以像插入数据那样新建一个update数组，来保存要修改的数据
        Node[] update = new Node[levelCount];
        Arrays.fill(update, head);
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].value < value) {
                p = p.forwards[i];
            }
            update[i] = p;
        }

        // 真正地删除节点
        for (int i = levelCount - 1; i >= 0; i--) {
            if (update[i].forwards[i] != null && update[i].forwards[i].value == value) {
                update[i].forwards[i] = update[i].forwards[i].forwards[i];
            }
        }

        // 是否要修改levelCount
        while (levelCount > 0 && head.forwards[levelCount] == null) levelCount--;
    }

    /**
     * 查找指定数据的节点
     *
     * @param value 待查找的数据
     * @return 值为value的节点
     */
    public Node find(int value) {
        Node p = head;
        for (int i = levelCount - 1; i >= 0; i--) {
            while (p.forwards[i] != null && p.forwards[i].value < value) {
                p = p.forwards[i];
            }
        }
        if (p.forwards[0] != null && p.forwards[0].value == value) {
            return p.forwards[0];
        } else {
            return null;
        }
    }

    /**
     * 打印跳表
     */
    public void print() {
        Node p = head.forwards[0];
        while (p != null) {
            System.out.print(p + "  ");
            p = p.forwards[0];
        }
        System.out.println();
    }

    /**
     * 获取随机层级，用于维护跳表索引
     *
     * @return 一个1~SKIP_MAX_LEVEL的整数
     * 并且有以下规律：
     * 有1/2的概率返回 1
     * 有1/4的概率返回 2
     * 有1/8的概率返回 3
     * ....
     */
    private int randomLevel() {
        int randomLevel = 1;
        while (Math.random() < FACTOR && randomLevel <= MAX_LEVEL)
            randomLevel++;
        return randomLevel;
    }


    /**
     * 节点类
     */
    public static class Node {
        private int value;
        private Node[] forwards;
        private int maxLevel;

        @Override
        public String toString() {
            return "Node{" +
                    "value=" + value +
                    ", maxLevel=" + maxLevel +
                    '}';
        }
    }

    public static void main(String[] args) {
        SkipList skipList = new SkipList();
        skipList.insert(594);
        skipList.insert(594);
        skipList.print();
        skipList.delete(594);

//        skipList.print();
//        System.out.println(skipList.levelCount);
        Node node = skipList.find(594);
        System.out.println(node);
    }

}
