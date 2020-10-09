package Unit09_tree;

/**
 * 二叉查找树
 */
public class BinarySearchTree {

    private Node tree;

    /**
     * 插入节点
     */
    public void insert(int data) {
        Node newNode = new Node(data);
        if (tree == null) {
            tree = newNode;
            return;
        }

        Node node = tree;
        while (node != null) {
            Node left = node.left;
            Node right = node.right;
            if (data < node.data) {
                if (left == null) {
                    node.left = newNode;
                }
                node = left;
            } else {
                if (right == null) {
                    node.right = newNode;
                }
                node = right;
            }
        }
    }

    /**
     * 删除节点
     */
    public void delete(int data) {
        Node node = tree;
        Node pNode = null; //  父节点
        while (node != null) {
            if (data == node.data) {
                break;
            }
            pNode = node;
            if (data < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }

        // 没找到要删除的节点
        if (node == null) {
            return;
        }

        // 有两个子节点
        if (node.right != null && node.left != null) {
            // 找到右子树中最小的节点(左子树最大的也可以)
            Node min = node.right;
            Node pMin = node; // 最小值的父节点
            while (min.left != null) {
                pMin = min;
                min = min.left;
            }
            // 最小值放在待删除的节点
            node.data = min.data;
            // 利用下面的流程删除原先最小值节点。最小值节点肯定不会有左节点，但可能有右节点
            node = min;
            pNode = pMin;
        }

        // 根节点/叶子节点/只有一个子节点
        Node child;
        if (node.right != null) {
            child = node.right;
        } else if (node.left != null) {
            child = node.left;
        } else {
            child = null;
        }

        if (pNode == null) {
            tree = child;
        } else if (pNode.right == node) {
            pNode.right = child;
        } else {
            pNode.left = child;
        }
    }

    /**
     * 寻找最大值
     */
    public Node findMax() {
        Node node = tree;
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    /**
     * 寻找最小值
     */
    public Node findMin() {
        Node node = tree;
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 寻找某个值
     */
    public Node find(int data) {
        Node node = tree;
        while (node != null) {
            if (data == node.data) {
                return node;
            }
            if (data < node.data) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        System.out.println("=======前序遍历======");
        preOrderRecursion(tree);
        System.out.println();
    }

    private void preOrderRecursion(Node node) {
        if (node == null) {
            return;
        }
        int data = node.data;
        System.out.print(data + "\t");
        if (node.left != null) {
            preOrderRecursion(node.left);
        }
        if (node.right != null) {
            preOrderRecursion(node.right);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        System.out.println("=======中序遍历======");
        inOrderRecursion(tree);
        System.out.println();
    }

    private void inOrderRecursion(Node node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            inOrderRecursion(node.left);
        }
        int data = node.data;
        System.out.print(data + "\t");
        if (node.right != null) {
            inOrderRecursion(node.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        System.out.println("=======后序遍历======");
        postOrderRecursion(tree);
        System.out.println();
    }

    private void postOrderRecursion(Node node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            postOrderRecursion(node.left);
        }
        if (node.right != null) {
            postOrderRecursion(node.right);
        }
        int data = node.data;
        System.out.print(data + "\t");
    }

    private static class Node{
        private int data;
        private Node left;
        private Node right;

        Node(int data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        BinarySearchTree bTree = new BinarySearchTree();
        bTree.insert(10);
        bTree.insert(5);
        bTree.insert(4);
        bTree.insert(13);
        bTree.insert(7);
        bTree.insert(12);

        bTree.inOrder();
        bTree.preOrder();
        bTree.postOrder();

        Node max = bTree.findMax();
        System.out.println(max == null ? null : max.data);

        Node min = bTree.findMin();
        System.out.println(min == null ? null : min.data);

        Node find1 = bTree.find(5);
        System.out.println(find1 == null ? null : find1.data);
        Node find2 = bTree.find(17);
        System.out.println(find2 == null ? null : find2.data);

        // 删除根节点
        /*bTree.delete(10);
        bTree.inOrder();*/

        // 删除叶子节点
        /*bTree.delete(7);
        bTree.inOrder();*/

        // 删除只有一个子节点的节点
        /*bTree.delete(13);
        bTree.inOrder();*/

        // 删除有两个子节点的节点
        bTree.delete(5);
        bTree.inOrder();

    }

}
