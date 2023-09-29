import java.util.Arrays;

public class heapSort {
  public static void main(String[] args) {
    int[] arr = {1, 5, 3, 2, 6, 4, 7, 9, 8, 10};
    heapSortMethod(arr);
    System.out.println(Arrays.toString(arr));
  }

  public static void heapSortMethod(int[] arr) {
    // heapify the array
    for (int i = arr.length / 2 - 1; i >= 0; i--) {
      heapify(arr, arr.length, i);
    }

    // swap max and last
    for (int i = arr.length - 1; i > 0; i--) {
      int temp = arr[0];
      arr[0] = arr[i];
      arr[i] = temp;
      heapify(arr, i, 0);
    }
  }

  private static void heapify(int[] arr, int size, int i) {
    // root is i
    int largest = i;
    int left = 2 * i + 1;
    int right = 2 * i + 2;

    if (left < size && arr[left] > arr[largest]) {
      largest = left;
    }
    if (right < size && arr[right] > arr[largest]) {
      largest = right;
    }
    if (i != largest) {
      int temp = arr[i];
      arr[i] = arr[largest];
      arr[largest] = temp;
      heapify(arr, size, largest);
    }
  }
}
