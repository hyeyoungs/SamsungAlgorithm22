package SamsungAlgorithm22.Practice.MemorySystem;

import java.util.*;

class UserSolution {
    static class Memory implements Comparable<Memory> {
        int startAddr;
        int endAddr;
        int size;

        public Memory(int startAddr, int endAddr, int size) {
            this.startAddr = startAddr;
            this.endAddr = endAddr;
            this.size = size;
        }

        @Override
        public int compareTo(Memory memory) {
            if (this.size == memory.size)   return this.startAddr - memory.startAddr;
            return this.size - memory.size;
        }
    }

    static HashMap<Integer, Memory> start2usingMemory;
    static HashMap<Integer, Memory> start2emptyMemory;
    static HashMap<Integer, Memory> end2emptyMemory;
    static TreeSet<Memory> empty;


    public void init(int N) {
        //사용하는 메모리 초기화
        start2usingMemory = new HashMap<>();
        //빈 메모리 초기화
        start2emptyMemory = new HashMap<>();
        end2emptyMemory = new HashMap<>();
        empty = new TreeSet<>();

        //빈 메모리 할당
        Memory memory = new Memory(0, N - 1, N);
        empty.add(memory);
        start2emptyMemory.put(0, memory);
        end2emptyMemory.put(N - 1, memory);
    }

    public int allocate(int mSize) {
        if (empty.tailSet(new Memory(0, 0, mSize)).size() == 0) {
            return -1;
        }

        //할당된 메모리
        Memory allocatedMemory = empty.tailSet(new Memory(0, 0, mSize)).first();
        //할당된 메모리 중 mSize만큼 사용하는 메모리
        Memory newUsingMemory = new Memory(allocatedMemory.startAddr, allocatedMemory.startAddr + mSize - 1, mSize);

        //mSize 새롭게 사용하는 메모리 추가
        start2usingMemory.put(newUsingMemory.startAddr, newUsingMemory);

        //빈 메모리에서 할당했던 메모리 제거
        empty.remove(allocatedMemory);
        start2emptyMemory.remove(allocatedMemory.startAddr);
        end2emptyMemory.remove(allocatedMemory.endAddr);

        //할당된 메모리에서 사용하고 남은 메모리는 다시 빈 메모리에 추가
        if (mSize < allocatedMemory.size) {
            int remainStartAddr = allocatedMemory.startAddr + mSize;
            int remainEndAddr = allocatedMemory.endAddr;

            Memory remainMemory = new Memory(remainStartAddr, remainEndAddr, remainEndAddr - remainStartAddr + 1);
            empty.add(remainMemory);
            start2emptyMemory.put(remainMemory.startAddr, remainMemory);
            end2emptyMemory.put(remainMemory.endAddr, remainMemory);
        }

        return allocatedMemory.startAddr;
    }

    public int release(int mAddr) {
        Memory releasedMemory = start2usingMemory.get(mAddr);
        if (releasedMemory == null) return -1;

        start2usingMemory.remove(releasedMemory.startAddr);

        int emptySize = releasedMemory.size;
        int emptyStart = releasedMemory.startAddr;
        int emptyEnd = releasedMemory.endAddr;

        // 왼쪽 공간 합치기
        Memory leftEmptyMemory = end2emptyMemory.get(releasedMemory.startAddr - 1);
        if (leftEmptyMemory != null) {
            emptySize += leftEmptyMemory.size;
            emptyStart = leftEmptyMemory.startAddr;
            start2emptyMemory.remove(leftEmptyMemory.startAddr);
            end2emptyMemory.remove(leftEmptyMemory.endAddr);
            empty.remove(leftEmptyMemory);
        }
        // 오른쪽 공간 합치기
        Memory rightEmptyMemory = start2emptyMemory.get(releasedMemory.endAddr + 1);
        if (rightEmptyMemory != null) {
            emptySize += rightEmptyMemory.size;
            emptyEnd = rightEmptyMemory.endAddr;
            start2emptyMemory.remove(rightEmptyMemory.startAddr);
            end2emptyMemory.remove(rightEmptyMemory.endAddr);
            empty.remove(rightEmptyMemory);

        }
        // 새로운 빈 공간 할당
        Memory newMemory = new Memory(emptyStart, emptyEnd, emptySize);
        empty.add(newMemory);
        start2emptyMemory.put(emptyStart, newMemory);
        end2emptyMemory.put(emptyEnd, newMemory);

        return releasedMemory.size;
    }
}
