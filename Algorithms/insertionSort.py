def insertionSort(arr):

    # Time Complexity: O(n^2)

    for i in range(1, len(arr)):
        currentNum = arr[i]
        j = i - 1
        while j >= 0 and currentNum < arr[j]:
            arr[j+1] = arr[j]  # Shifting the numbers
            j -= 1
        arr[j+1] = currentNum
    return arr


arr = [12, 11, 13, 5, 6, 7]
print(insertionSort(arr))
