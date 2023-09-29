def mergeSort(arr):
  if len(arr) <= 1:
    return arr

  left = arr[:len(arr)//2]
  right = arr[len(arr)//2:]

  leftSorted = mergeSort(left)
  rightSorted = mergeSort(right)
  combined = merge(leftSorted, rightSorted)

  return combined

def merge(arr1, arr2):
  merged = []
  p1, p2 = 0, 0

  for i in range(len(arr1) + len(arr2)):
    if p1 >= len(arr1):
      # arr1 is done, append arr2
      merged.append(arr2[p2])
      p2 += 1
    elif p2 >= len(arr2):
      # arr2 is done, append arr1
      merged.append(arr1[p1])
      p1 += 1
    elif arr1[p1] <= arr2[p2]:
      # arr1 has the smaller element, append it
      merged.append(arr1[p1])
      p1 += 1
    else:
      # arr2 has the smaller or equal element, append it
      merged.append(arr2[p2])
      p2 += 1

  return merged

print(mergeSort([1, 3, 2, 4, 5, 6, 7, 8, 9, 10]))