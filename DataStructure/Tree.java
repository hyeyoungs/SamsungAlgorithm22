package SamsungAlgorithm22.DataStructure;

import java.util.*;

class Tree {
    /*** 기본 ***/
    static final int MAX_CHILD_NUM = 2;

    //TreeNode
    class TreeNode {
        //부모노드의 숫자를 저장
        int parent;
        //자식노드의 숫자를 저장
        int []child = new int[MAX_CHILD_NUM];
        public TreeNode(int parent) {
            this.parent = parent;
            for (int i = 0; i < MAX_CHILD_NUM; i++) {
                child[i] = -1;
            }
        }
    }

    TreeNode [] treenode;
    int nodeNum;

    //자료구조 트리 (TreeNode 구성)
    public Tree(int nodeNum) {
        this.nodeNum = nodeNum;
        treenode = new TreeNode[nodeNum+1];
        //모든 TreeNode 부모 -1로 초기화
        for (int i = 0; i <= nodeNum; i++) {
            treenode[i] = new TreeNode(-1);
        }
    }

    /*** 삽입 ***/
    //자식노드 추가로 트리 구성
    public void addChild(int parent, int child) {
        int found = -1;
        for (int i = 0; i < MAX_CHILD_NUM; i++) {
            if (treenode[parent].child[i] == -1) {
                found = i;
                break;
            }
        }

        if (found == -1) return;

        treenode[parent].child[found] = child;
        treenode[child].parent = parent;
    }

    /*** 탐색 ***/
    //루트노드 리턴
    public int getRoot() {
        for (int i = 1; i < nodeNum; i++) {
            if (treenode[i].parent == -1) {
                return i;
            }
        }
        return -1;
    }

    /*** 순회 ***/
    //순회
    public void preOrder(int root) {
        System.out.printf("%d ", root);

        for (int i = 0; i < MAX_CHILD_NUM; i++) {
            int child = treenode[root].child[i];
            if (child != -1) {
                preOrder(child);
            }
        }
    }

}

class Solution {

    public static void main(String arg[]) throws Exception {
        Scanner sc = new Scanner(System.in);

        int T = sc.nextInt();

        for (int test_case = 1; test_case <= T; ++test_case) {
            /*** 초기화 ***/
            int node = sc.nextInt();
            int edge = sc.nextInt();

            //트리 객체 생성
            Tree tree = new Tree(node);

            //edge 트리 구성
            for (int i = 0; i < edge; i++) {
                int parent = sc.nextInt();
                int child = sc.nextInt();
                tree.addChild(parent, child);
            }

            //Root 가져오기
            int root = tree.getRoot();
            System.out.printf("#%d ", test_case);
            tree.preOrder(root);
            System.out.printf("\n");
        }

        sc.close();
    }
}
