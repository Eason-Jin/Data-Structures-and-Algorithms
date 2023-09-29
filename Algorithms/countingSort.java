public class countingSort {
  public static void main(String[] args) {
    byte[] arr = {1, 4, 1, 2, 8, 5, 2};
    byte[] sortedArr = countingSortMethod(arr);
    for (int i = 0; i < sortedArr.length; i++) {
      System.out.print(sortedArr[i] + " ");
    }
  }

  public static byte[] countingSortMethod(byte[] arr) {
    // Array to store the count of each element
    int[] count = new int[256];
    for (int x : arr) {
      count[x & 0xFF]++;
    }

    int index = 0;  // index for sorted array (since there are duplicates)
    for (int i = 0; i < count.length; i++) {
      while (count[i] > 0) {
        arr[index++] = (byte) i;
        count[i]--;
      }
    }
    return arr;
  }
}
