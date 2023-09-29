import java.util.Random;

public class quickSort {
  public static void main(String[] args) {
    int[] arr = {5, 4, 3, 2, 1};
    quickSortWithPartition(arr, 0, arr.length - 1);
    for (int i : arr) {
      System.out.print(i + " ");
    }
  }

  public static void quickSortWithPartition(int[] arr, int start, int end) {
    if (start >= end) return;

    // Randomize the pivot (optional)
    Random random = new Random();
    int randomIndex = random.nextInt(start, end);
    int temp = arr[randomIndex];
    arr[randomIndex] = arr[end];
    arr[end] = temp;

    int pivot = partition(arr, start, end);
    quickSortWithPartition(arr, start, pivot - 1);
    quickSortWithPartition(arr, pivot + 1, end);
  }

  private static int partition(int[] arr, int start, int end) {
    int q = end;
    int temp;
    for (int i = end - 1; i >= start; i--) {
      if (arr[q] < arr[i]) {
        if (i != q - 1) {
          temp = arr[q - 1];
          arr[q - 1] = arr[i];
          arr[i] = temp;
        }
        temp = arr[q - 1];
        arr[q - 1] = arr[q];
        arr[q] = temp;
        q--;
      }
    }
    return q;
  }
}
