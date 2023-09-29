def selectionSort(arr):

    # Time Complexity: O(n^2)

    for i in range(len(arr)):
        minIndex = i
        for j in range(i+1, len(arr)):
            if arr[j] < arr[minIndex]:
                minIndex = j
        arr[minIndex], arr[i] = arr[i], arr[minIndex]
    return arr


arr = [12, 11, 13, 5, 6, 7]
print(selectionSort(arr))
