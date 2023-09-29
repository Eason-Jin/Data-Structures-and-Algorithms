public class PriorityQueue {
  DynamicArray data;

  public PriorityQueue() {
    data = new DynamicArray();
  }

  public void add(int val) {
    data.add(val);
    int index = data.size;
    while (index >= 0) {
      int parent;
      if (index % 2 == 0) {
        // right child
        parent = (index - 2) / 2;
      } else {
        // left child
        parent = (index - 1) / 2;
      }
      if (data.arr[parent] < data.arr[index]) {
        int temp = data.arr[parent];
        data.arr[parent] = data.arr[index];
        data.arr[index] = temp;
        index = parent;
      } else {
        break;
      }
    }
  }

  public int findMax() {
    return data.arr[0];
  }

  public void removeMax() {
    int temp = data.arr[0];
    data.arr[0] = data.arr[data.size - 1];
    data.arr[data.size - 1] = temp;
    data.remove();
    // fix the tree
    for (int i = data.size / 2 - 1; i >= 0; i--) {
      int largest = i;
      int left = 2 * i + 1;
      int right = 2 * i + 2;

      if (left < data.size && data.arr[left] > data.arr[largest]) {
        largest = left;
      }
      if (right < data.size && data.arr[right] > data.arr[largest]) {
        largest = right;
      }
      if (i != largest) {
        int temp2 = data.arr[i];
        data.arr[i] = data.arr[largest];
        data.arr[largest] = temp2;
      }
    }
  }
}
