def binarySearch(arr, num):

    # Time Complexity: O(logn)

    l = 0
    r = len(arr)
    while l < r:
        m = l + (r-l)//2
        if arr[m] == num:
            return m
        elif arr[m] < num:
            l = m + 1
        else:
            r = m
    return -1


def binarySearchFirst(arr, num):
    l = 0
    r = len(arr) - 1
    while (r-l) > 1:
        m = (l+r)//2
        if num <= arr[m]:
            r = m
        else:
            l = m+1


def binarySearchLast(arr, num):
    l = 0
    r = len(arr) - 1
    while (r-l) > 1:
        m = (l+r)//2
        if num >= arr[m]:
            l = m
        else:
            r = m-1
    return r


# arr = [1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9, 10]
arr = [-1, -1, 0, 1, 1, 1, 1, 2, 2, 2, 2, 3, 4]
print(binarySearchFirst(arr, 2))
print(binarySearchLast(arr, 1))
