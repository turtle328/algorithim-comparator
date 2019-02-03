package sorts.backend;

import java.util.*;
import java.text.DecimalFormat;

public class SortsArray<T extends Comparable<T>>
{
  private int size;                     // size of array to be sorted
  private ArrayList<T> values = null;   // values to be sorted
  private ArrayList<T> preSorted = null;
  private int numElementsAccessed = 0; 

  public SortsArray(ArrayList<T> list)
  {
      values = list;
      preSorted = new ArrayList<T>(values);
      size = values.size();
  }
  
  public void resetArray()
  {
      values = new ArrayList<T>(preSorted);
  }
  
  public int getSize()
  {
      return size;
  }
  
  public ArrayList<T> getValuesArray()
  {
      return values;
  }
  
  public void setSize(int newSize)
  {
      size = newSize;
  }
  
  public int getNumElementsAccessed()
  {
      int temp = numElementsAccessed;
      numElementsAccessed = 0;
      return temp;
  }

  public void resetNumElementsAccessed()
  {
      numElementsAccessed = 0;
  }
  
  public void setValuesArray(ArrayList<T> newArray)
  {
      values = newArray;
  }

  public boolean isSorted()
  // Returns true if the array values are sorted and false otherwise.
  {
    boolean sorted = true;
    for (int index = 0; index < (size - 1); index++)
      if (((Comparable)values.get(index)).compareTo(values.get(index + 1)) > 0)
        sorted = false;
    return sorted;
  }

  public void swap(int index1, int index2)
  // Precondition: index1 and index2 are >= 0 and < SIZE.
  //
  // Swaps the integers at locations index1 and index2 of the values array. 
  {
    T temp = values.get(index1);
    values.set(index1, values.get(index2));
    values.set(index2, temp);
  }

  public void printValues()
  // Prints all the values integers.
  {
    T value;
    System.out.println("The values array is:");
    for (int index = 0; index < size; index++)
    {
      value = values.get(index);
      if (((index + 1) % 10) == 0)
        System.out.println(value);
      else
        System.out.print(value + " ");
    }
    System.out.println();
  }

  public boolean linearSearch(T element)
  {
      for (T item : values)
      {
          numElementsAccessed++;
          if (item.equals(element))
          {
              return true;
          }
      }
      return false;
  }
  
  public boolean binarySearch(T element)
  {
      int left = 0, right = size - 1;
      
      while (left <= right)
      {
          numElementsAccessed++;
          int middle = (left + right) / 2;
          
          if (values.get(middle).compareTo(element) < 0)
              left = middle + 1;
          
          else if (values.get(middle).compareTo(element) > 0)
              right = middle - 1;
          
          else return true;
      }
      return false;
  }
  
  private boolean bubbleUp2(int startIndex, int endIndex)
  // Switches adjacent pairs that are out of order 
  // between values[startIndex]..values[endIndex] 
  // beginning at values[endIndex].
  //
  // Returns false if a swap was made; otherwise, returns true.
  {
    boolean sorted = true;
    for (int index = endIndex; index > startIndex; index--)
    {
      numElementsAccessed++;
      if (((Comparable)values.get(index)).compareTo(values.get(index - 1)) < 0)
      {
        swap(index, index - 1);
        sorted = false;
      }
    }
    return sorted;
  }

  public void shortBubble()
  // Sorts the values array using the bubble sort algorithm.
  // The process stops as soon as values is sorted.
  {
    int current = 0;
    boolean sorted = false;
    while ((current < (size - 1)) && !sorted)
    {
      sorted = bubbleUp2(current, size - 1);
      current++;
    }
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Insertion Sort

  private void insertItem(int startIndex, int endIndex)
  // Upon completion, values[0]..values[endIndex] are sorted.
  {
    boolean finished = false;
    int current = endIndex;
    boolean moreToSearch = true;
    while (moreToSearch && !finished)
    {
      numElementsAccessed++;
      if (((Comparable)values.get(current)).compareTo(values.get(current - 1)) < 0)
      {
        swap(current, current - 1);
        current--;
        moreToSearch = (current != startIndex);
      }
      else
        finished = true;
    }
  }

  public void insertionSort()
  // Sorts the values array using the insertion sort algorithm.
  {
   for (int count = 1; count < size; count++)
      insertItem(0, count);
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Merge Sort

  private void merge (int leftFirst, int leftLast, int rightFirst, int rightLast)
  // Preconditions: values[leftFirst]..values[leftLast] are sorted.
  //                values[rightFirst]..values[rightLast] are sorted.
  // 
  // Sorts values[leftFirst]..values[rightLast] by merging the two subarrays.
  {
    Map<Integer, T> temp = new HashMap(size);
    
    int index = leftFirst;
    int saveFirst = leftFirst;  // to remember where to copy back

    while ((leftFirst <= leftLast) && (rightFirst <= rightLast))
    {
      numElementsAccessed++;
      if (((Comparable)values.get(leftFirst)).compareTo(values.get(rightFirst)) < 0)
      {
        temp.put(index, values.get(leftFirst));
        leftFirst++;
      }
      else
      {
        temp.put(index, values.get(rightFirst));
        rightFirst++;
      }
      index++;
    }

    while (leftFirst <= leftLast)
    // Copy remaining items from left half.

    {
      numElementsAccessed++;
      temp.put(index, values.get(leftFirst));
      leftFirst++;
      index++;
    }

    while (rightFirst <= rightLast)
    // Copy remaining items from right half.
    {
      numElementsAccessed++;
      temp.put(index, values.get(rightFirst));
      rightFirst++;
      index++;
    }

    for (index = saveFirst; index <= rightLast; index++)
    {
      values.set(index, temp.get(index));
    }
  }

  private void mergeSort(int first, int last)
  // Sorts the values array using the merge sort algorithm.
  {
    if (first < last)
    {
      int middle = (first + last) / 2;
      mergeSort(first, middle);
      mergeSort(middle + 1, last);
      merge(first, middle, middle + 1, last);
    }
  }
  
  public void mergeSort()
  {
      mergeSort(0, size - 1);
  }


  /////////////////////////////////////////////////////////////////
  //
  //  Quick Sort

  private int split(int first, int last)
  {
    T splitVal = values.get(first);
    int saveF = first;
    boolean onCorrectSide;

    first++;
    do
    {
      onCorrectSide = true;
      while (onCorrectSide)             // move first toward last
      {
        numElementsAccessed++;
        if (((Comparable)values.get(first)).compareTo(splitVal) > 0)
          onCorrectSide = false;
        else
        {
          first++;
          onCorrectSide = (first <= last);
        }
      }

      onCorrectSide = (first <= last);
      while (onCorrectSide)             // move last toward first
      {
        numElementsAccessed++;
        if (((Comparable)values.get(last)).compareTo(splitVal) <= 0)
          onCorrectSide = false;
        else
         {
          last--;
          onCorrectSide = (first <= last);
         }
      }

      if (first < last)                
      {
        swap(first, last);
        first++;
        last--;
      }
    } while (first <= last);

    swap(saveF, last);
    return last;
  }

  private void quickSort(int first, int last)
  {
    if (first < last)
    {
      int splitPoint;

      splitPoint = split(first, last);
      // values[first]..values[splitPoint - 1] <= splitVal
      // values[splitPoint] = splitVal
      // values[splitPoint+1]..values[last] > splitVal

      quickSort(first, splitPoint - 1);
      quickSort(splitPoint + 1, last);
    }
  }
  
  public void quickSort()
  {
      quickSort(0, size-1);
  }
  
  /////////////////////////////////////////////////////////////////
  //
  //  Heap Sort

  private int newHole(int hole, int lastIndex, T item)
  // If either child of hole is larger than item this returns the index
  // of the larger child; otherwise it returns the index of hole.
  {
    int left = (hole * 2) + 1;
    int right = (hole * 2) + 2;
    if (left > lastIndex)
      // hole has no children
      return hole;         
    else
    if (left == lastIndex)
    {
      // hole has left child only
      if (item.compareTo(values.get(left)) < 0)             
        // item < left child
        return left;
      else
        // item >= left child
        return hole;
    }
    else
    // hole has two children 
    if (values.get(left).compareTo(values.get(right)) < 0)
      // left child < right child
      if (values.get(right).compareTo(item) <= 0)
        // right child <= item
        return hole;
      else
        // item < right child
        return right;
    else
    // left child >= right child
    if (values.get(left).compareTo(item) <= 0)
      // left child <= item
      return hole;
    else
      // item < left child
      return left;
  }

  private void reheapDown(T item, int root, int lastIndex)
  // Precondition: Current root position is "empty".
  //
  // Inserts item into the tree and ensures shape and order properties.
  {
    int hole = root;   // current index of hole
    int newhole;       // index where hole should move to

    numElementsAccessed++;      
    newhole = newHole(hole, lastIndex, item);   // find next hole
    while (newhole != hole)
    {
      values.set(hole, values.get(newhole));
      hole = newhole;                     // move hole down
      numElementsAccessed++;
      newhole = newHole(hole, lastIndex, item);     // find next hole
    }
      //System.out.println(numElementsAccessed);
    values.set(hole, item);
  }

  public void heapSort()
  // Sorts the values array using the heap sort algorithm.
  {  
    int index;
    // Convert the array of values into a heap.
    for (index = size/2 - 1; index >= 0; index--)
    {
      reheapDown(values.get(index), index, size - 1);
    }
//     Sort the array.
    for (index = size - 1; index >=1; index--)
    {
      numElementsAccessed++;
      swap(0, index);
      reheapDown(values.get(0), 0, index - 1);
    }    
  }
}