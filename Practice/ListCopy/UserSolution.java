package SamsungAlgorithm22.Practice.ListCopy;

import java.util.*;

class UserSolution {

    static class Trie {

        static class Node {
            private int code = -1;
            private final Node[] children = new Node[26];
        }

        private int specialTrieCode = 0;
        private int normalTrieCode = 10;
        private final Node root = new Node();

        public int insertSpecial(char[] text) {
            Node node = insert(text);
            node.code = specialTrieCode++;
            return node.code;
        }

        public int insertNormal(char[] text) {
            Node node = insert(text);
            node.code = normalTrieCode++;
            return node.code;
        }

        public int getCode(char[] text) {
            Node curr = root;

            for (int i = 0; text[i] != '\0'; i++) {
                curr = curr.children[text[i] - 97];
            }

            return curr.code;
        }

        private Node insert(char[] text) {
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

            return curr;
        }
    }

    int MAX_IMMUTABLE_LIST = 10;
    int MAX_LIST = 5010;

    Trie trie;
    int[][] immutableList;
    boolean[] isDeepCopyImmutableList;

    int[] listReference;
    int[] historyReference;
    HashMap<Integer, Integer>[] updateHistoryMap;

    public void init() {
        trie = new Trie();
        immutableList = new int[MAX_IMMUTABLE_LIST][];
        isDeepCopyImmutableList = new boolean[MAX_IMMUTABLE_LIST];

        listReference = new int[MAX_LIST];
        historyReference = new int[MAX_LIST];
        updateHistoryMap = new HashMap[MAX_LIST];
    }

    public void makeList(char[] mName, int mLength, int[] mListValue) {
        int trieCode = trie.insertSpecial(mName);
        immutableList[trieCode] = new int[mLength];
        System.arraycopy(mListValue, 0, immutableList[trieCode], 0, mLength);

        updateHistoryMap[trieCode] = new HashMap<>();
        historyReference[trieCode] = trieCode;
        listReference[trieCode] = trieCode;
    }

    public void copyList(char[] mDest, char[] mSrc, boolean mCopy) {
        int dstTrieCode = trie.insertNormal(mDest);
        int srcTrieCode = trie.getCode(mSrc);

        if (mCopy) {
            int historyRef = historyReference[srcTrieCode];

            if (historyRef < MAX_IMMUTABLE_LIST) {
                isDeepCopyImmutableList[historyRef] = true;
            }

            HashMap<Integer, Integer> history = updateHistoryMap[historyRef];
            updateHistoryMap[dstTrieCode] = new HashMap<>(history);
            historyReference[dstTrieCode] = dstTrieCode;
        } else {
            historyReference[dstTrieCode] = historyReference[srcTrieCode];
        }

        listReference[dstTrieCode] = listReference[srcTrieCode];
    }

    public void updateElement(char[] mName, int mIndex, int mValue) {
        int trieCode = trie.getCode(mName);
        int historyRef = historyReference[trieCode];

        if (historyRef < MAX_IMMUTABLE_LIST && !isDeepCopyImmutableList[historyRef]) {
            immutableList[historyRef][mIndex] = mValue;
        } else {
            updateHistoryMap[historyRef].put(mIndex, mValue);
        }
    }

    public int element(char[] mName, int mIndex) {
        int trieCode = trie.getCode(mName);
        int historyRef = historyReference[trieCode];

        Integer value = updateHistoryMap[historyRef].get(mIndex);

        if (value != null) {
            return value;
        } else {
            return immutableList[listReference[trieCode]][mIndex];
        }
    }

}
