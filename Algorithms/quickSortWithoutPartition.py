def quickSortWithoutPartition(arr):

    if len(arr) <= 1:
        return arr

    pivot = arr[len(arr)-1]
    left = []
    right = []

    for i in range(len(arr)-1):
        if arr[i] < pivot:
            left.append(arr[i])
        else:
            right.append(arr[i])

    leftSorted = quickSortWithoutPartition(left)
    rightSorted = quickSortWithoutPartition(right)

    return leftSorted + [pivot] + rightSorted

print(quickSortWithoutPartition([1, 3, 2, 4, 5, 6, 7, 8, 9, 10]))
