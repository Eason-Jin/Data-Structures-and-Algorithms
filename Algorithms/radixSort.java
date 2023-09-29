public class radixSort {
  public static void main(String[] args) {
    int[] arr = {1, 14, 1, 2, 1118, 5, 112};
    int[] sortedArr = radixSortMethod(arr);
    for (int i = 0; i < sortedArr.length; i++) {
      System.out.print(sortedArr[i] + " ");
    }
  }

  public static int[] radixSortMethod(int[] arr) {
    // Find the maximum number to know number of digits
    int max = arr[0];
    for (int i = 1; i < arr.length; i++) {
      if (arr[i] > max)
        max = arr[i];
    }

    // Do counting sort for every digit. Note that instead of passing digit
    // number, exp is passed. exp is 10^i where i is current digit number
    for (int exp = 1; max / exp > 0; exp *= 10) {
      arr = countingSortMethod(arr, exp);
    }
    return arr;
  }

  public static int[] countingSortMethod(int[] arr, int exp) {
    int[] output = new int[arr.length]; // output array
    int[] count = new int[10];
    for (int i = 0; i < arr.length; i++) {
      count[(arr[i] / exp) % 10]++;
    }

    // Change count[i] so that count[i] now contains actual position of this
    // digit in output[]
    for (int i = 1; i < 10; i++) {
      count[i] += count[i - 1];
    }

    // Build the output array
    for (int i = arr.length - 1; i >= 0; i--) {
      output[count[(arr[i] / exp) % 10] - 1] = arr[i];
      count[(arr[i] / exp) % 10]--;
    }

    // Copy the output array to arr[], so that arr[] now contains sorted
    // numbers according to current digit
    for (int i = 0; i < arr.length; i++) {
      arr[i] = output[i];
    }
    return arr;
  }
}
