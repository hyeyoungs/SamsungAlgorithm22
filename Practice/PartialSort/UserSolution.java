package SamsungAlgorithm22.Practice.PartialSort;

public class UserSolution {
    static final int MAX_USER = 100000;
    User[] heap;
    int heapSize;

    static class User {
        int uID;
        int income;

        public User(int uID, int income) {
            this.uID = uID;
            this.income = income;
        }
    }

    public void init() {
        heap = new User[MAX_USER];
        heapSize = 0;
    }

    /*** 삽입 ***/
    //1. 수입, 클수록 우선순위가 높다.
    //2. 만약 수입이 동일한 경우 uID가 작은 user의 우선순위가 높다.
    public void addUser(int uID, int income) {
        heap[heapSize] = new User(uID, income);
        int current = heapSize;


        while (current > 0) {
            // 부모의 수입이 더 크다면 종료
            if (heap[current].income < heap[(current - 1) / 2].income)
                break;
            // 수입이 같다면 부모의 uID가 작다면 종료
            if (heap[current].income == heap[(current - 1) / 2].income && (heap[current].uID > heap[(current - 1)/ 2].uID))
                break;
            // 그외는 부모와 자식을 바꾼다.
            User temp = heap[current];
            heap[current] = heap[(current - 1) / 2];
            heap[(current - 1) / 2] = temp;
            current = (current - 1) / 2;
        }
        heapSize ++;
    }

    /*** 10번 Pop, 10번 삽입 ***/
    //1. 수입, 클수록 우선순위가 높다.
    //2. 만약 수입이 동일한 경우 uID가 작은 user의 우선순위가 높다.
    int getTop10(int[] result) {
        int count = 0;
        User[] removeUser = new User[10];

        //10번 pop
        //heapSize가 0초과, count가 10미만이면 반복한다.
        while (heapSize > 0 && count < 10) {
            //제거할 User 추가
            removeUser[count++] = heap[0];
            //heapSize -= 1
            heapSize--;
            //맨 마지막 데이터를 루트 노드로 옮기고
            heap[0] = heap[heapSize];

            //힙 구조를 계속 유지하도록 자식과 값을 바꾸면서 내려간다.
            int current = 0;
            while(current * 2 + 1 < heapSize) {
                //자식과 값을 비교할 때는 둘 중 더 큰 값과 비교한다.
                int child;
                if (current * 2 + 2 == heapSize)    child = current * 2 + 1;
                else {
                    if (heap[current * 2 + 1].income > heap[current * 2 + 2].income)    child = current * 2 + 1;
                    else if (heap[current * 2 + 1].income == heap[current * 2 + 2].income)
                        child = heap[current * 2 + 1].uID < heap[current * 2 + 2].uID ? current * 2 + 1 : current * 2 + 2;
                    else    child = current * 2 + 2;
                }
                //부모가 자식보다 크면 종료한다.
                if (heap[current].income > heap[child].income)  break;
                //부모가 자식보다 작으면 바꾼다.
                User temp = heap[current];
                heap[current] = heap[child];
                heap[child] = temp;
                current = child;
            }

        }

        //pop한 만큼 삽입
        for (int i = 0; i < count; i++) {
            result[i] = removeUser[i].uID;
            addUser(removeUser[i].uID, removeUser[i].income);
        }

        return count;
    }
}
