import java.util.Arrays;

class DynamicArray {
  int size;
  int arr[];

  DynamicArray() {
    size = 0;
    arr = new int[1];
  }

  void add(int val) {
    if (size == arr.length) {
      int maxSize = size * 2;
      arr = Arrays.copyOf(arr, maxSize);
    }
    arr[size++] = val;
  }

  void remove() {
    size--;
    // Shrink the list
    if (size <= arr.length / 4) {
      int maxSize = arr.length / 2;
      arr = Arrays.copyOf(arr, maxSize);
    }
  }
}
