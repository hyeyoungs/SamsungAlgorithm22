package SamsungAlgorithm22.DataStructure;

public class MergeSort {
    
    public static void main(String[] args) {
        int[] arr = {3, 9, 4, 7, 5, 0, 1, 6, 8, 2};
        printArray(arr);
        mergeSort(arr);
        printArray(arr);
    }

    static void mergeSort(int[] arr) {
        //임시 저장소
        int [] temp = new int [arr.length];
        //정렬할 배열, 임시 저장소, 시작, 끝 인덱스 넘기기
        mergeSort(arr, temp, 0, arr.length - 1);
    }

    static void mergeSort(int[] arr, int[] temp, int start, int end) {
        //시작 인덱스가 끝 인덱스 보다 작은 경우에만 호
        if (start < end) {
            //가운데 index
            int mid = (start + end) / 2;
            //배열 앞부분 호출
            mergeSort(arr, temp, start, mid);
            //배열 뒷부분 호출
            mergeSort(arr, temp, mid + 1, end);
            //두개로 나눈 배열을 병합
            merge(arr, temp, start, mid, end);
        }
    }

    static void merge(int[] arr, int[] temp, int start, int mid, int end) {
        //배열과 정렬된 결과를 반복적으로 저장해주는 배열 포인터, 임시 저장소, 시작 인덱스, 파티션, 끝 인덱스
        for ( int i = start; i <= end; i++) {
            //임시 저장소에 정렬된 배열을 필요한 만큼 복사
            temp[i] = arr[i];
        }

        //첫번째 방 시작 인덱스
        int part1 = start;
        //두번째 방 시작 인덱스
        int part2 = mid + 1;
        //다음에 저장할 곳 기억하는 인덱스
        int index = start;

        //첫번째 배열이 끝까지 가거나 두번째 배열이 끝까지 갈 때까지 반복문을 돌린다.
        while (part1 <= mid && part2 <= end) {
            //앞의 것이 작으면 앞으로 옮기고 앞쪽 포인터를 하나 옮긴다.
            if (temp[part1] <= temp[part2]) {
                arr[index] = temp[part1];
                part1++;
            }
            //뒤의 것이 작으면 앞으로 옮기고 뒤쪽 포인터를 하나 옮긴다.
            else {
                arr[index] = temp[part2];
                part2++;
            }
            //저장할 곳을 기억하는 인덱스 증가
            index++;
        }

        //만약에 뒤쪽 배열이 비었고 앞쪽 배열에 데이터가 남아있는 경우
        //그런 경우를 대비해서 앞쪽 포인터가 배열에 끝에서 남은 만큼을 돌면서 최종적으로 저장할 배열에 남은 값들을 붙여준다.
        //만약에 앞쪽 배열이 비었고 뒤쪽 배열에 데이터가 남아있는 경우
        //뒤쪽 배열은 최종 배열의 뒤쪽에 이미 자리하고 있기 때문에 뒤쪽에 남은 데이터는 신경을 안 쓰고 그냥 놔두면 그자리에 있게 되는 거니까 뒤쪽 데이터가 남는 것은 신경 안 써도 된다.
        for (int i = 0; i <= mid - part1; i++) {
            arr[index + i] = temp[part1 + i];
        }

    }

    static void printArray(int[] arr) {
        for (int data : arr) {
            System.out.print(data + ", ");
        }
        System.out.println();
    }

}
