import java.util.Random;

public class quickSelect {
  public static void main(String[] args) {
    int[] arr = {4, 5, 1, 2, 3};
    int k = 3;
    System.out.println(quickSelectWithPartition(arr, k));
  }

  public static int quickSelectWithPartition(int A[], int k) {
    // Like binary search
    int left = 0;
    int right = A.length - 1;

    while (left < right) {

      // Randomize the pivot (optional)
      Random random = new Random();
      int randomIndex = random.nextInt(left, right);
      int temp = A[randomIndex];
      A[randomIndex] = A[right];
      A[right] = temp;

      // Make all elements on the left of pivot smaller than pivot
      // and all elements on the right of pivot larger than pivot
      int q = partition(A, left, right);
      // If pivot is the kth smallest element, return it
      if (q == k - 1) {
        return A[q];
      } else if (q < k - 1) {
        // If pivot is smaller than kth smallest element, search right
        left = q + 1;
      } else {
        // If pivot is larger than kth smallest element, search left
        right = q - 1;
      }
    }
    return A[left];
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
