package SamsungAlgorithm22.Practice.ListCopy;

class UserSolution2 {

    static class Trie {

        static class Node {
            int code = -1;
            Node[] children = new Node[26];
        }

        int nodeCount = 0;
        Node root = new Node();

        public int insert(char[]text) {
            Node curr = root;

            for (int i = 0; text[i] != '\0'; i++) {
                int chCode = text[i] - 97;

                Node next = curr.children[chCode];
                if (next == null) {
                    next = new Node();
                    curr.children[chCode] = next;
                }
                curr = next;
            }

            curr.code = nodeCount++;
            return curr.code;
        }

        public int getCode(char[] text) {
            Node curr = root;

            for (int i = 0; text[i] != '\0'; i++) {
                curr = curr.children[text[i] - 97];
            }

            return curr.code;
        }

    }

    static final int MAX_LIST = 5010;

    Trie trie;
    int[][] list;

    public void init() {
        trie = new Trie();
        list = new int[MAX_LIST][];
    }

    public void makeList(char[] mName, int mLength, int[] mListValue) {
        int[] newList = new int[mLength];
        System.arraycopy(mListValue, 0, newList, 0, mLength);
        list[trie.insert(mName)] = newList;
    }


    public void copyList(char[] mDest, char[] mSrc, boolean mCopy) {
        int[] srcList = list[trie.getCode(mSrc)];

        if (mCopy) {
            int[] newList = new int[srcList.length];

            System.arraycopy(srcList, 0, newList, 0, srcList.length);

            list[trie.insert(mDest)] = newList;
        } else {
            list[trie.insert(mDest)] = srcList;
        }
    }

    public void updateElement(char[] mName, int mIndex, int mValue) {
        list[trie.getCode(mName)][mIndex] = mValue;
    }

    public int element(char[] mName, int mIndex) {
        return list[trie.getCode(mName)][mIndex];
    }

}
