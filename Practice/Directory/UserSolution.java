package SamsungAlgorithm22.Practice.Directory;

import java.util.*;

class UserSolution {

    static class Node {
        String name;
        Node parent;
        int count;
        List<Node> children = new LinkedList<>();

        public Node(String name) {
            this.name = name;
            //해당 노드를 포함한 서브 트리의 총 노드 개수
            this.count = 1;
        }
    }

    private final static int NAME_MAXLEN	= 6;
    private final static int PATH_MAXLEN	= 1999;

    static Node root;

    void init(int n) {
        root = new Node("/");
    }

    static String arrToString(char[] arr) {
        return String.valueOf(arr, 0, arr.length - 1);
    }

    Node getDirNode(char[] path) {
        StringTokenizer st = new StringTokenizer(arrToString(path), "/");
        Node currNode = root;

        while (st.hasMoreTokens()) {
            String nextDirName = st.nextToken();
            for (Node child : currNode.children) {
                if (child.name.equals(nextDirName)) {
                    currNode = child;
                    break;
                }
            }
        }
        return currNode;
    }

    void connect(Node parent, Node child) {
        parent.children.add(child);
        child.parent = parent;

        while (parent != null) {
            parent.count += child.count;
            parent = parent.parent;
        }
    }

    void cmd_mkdir(char[] path, char[] name) {
        Node parent = getDirNode(path);
        Node newNode = new Node(arrToString(name));
        connect(parent, newNode);
    }

    void cmd_rm(char[] path) {
        Node removeDir = getDirNode(path);
        Node removeParentDir = removeDir.parent;
        removeParentDir.children.remove(removeDir);

        Node temp = removeDir;
        while (temp.parent != null) {
            temp.parent.count -= removeDir.count;
            temp = temp.parent;
        }
    }

    void cmd_cp(char[] srcPath, char[] dstPath) {
        Node srcDir = getDirNode(srcPath);
        Node dstDir = getDirNode(dstPath);
        Node newDir = new Node(srcDir.name);

        copyTree(srcDir, newDir);
        connect(dstDir, newDir);
    }

    void copyTree(Node parent, Node copyParent) {
        copyParent.count = parent.count;

        for (Node child : parent.children) {
            Node temp = new Node(child.name);
            temp.parent = copyParent;
            copyParent.children.add(temp);
            copyTree(child, temp);
        }
    }

    void cmd_mv(char[] srcPath, char[] dstPath) {
        Node srcDir = getDirNode(srcPath);
        Node dstDir = getDirNode(dstPath);

        srcDir.parent.children.remove(srcDir);

        Node temp = srcDir.parent;
        while (temp != null) {
            temp.count -= srcDir.count;
            temp = temp.parent;
        }
        connect(dstDir, srcDir);
    }

    int cmd_find(char[] path) {
        return getDirNode(path).count - 1;
    }

}

