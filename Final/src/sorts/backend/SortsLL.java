package sorts.backend;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class SortsLL<T extends Comparable<T>>
{
  private int size;
  private RefUnsortedList<T> values = new RefUnsortedList<>();
  private RefUnsortedList<T> preSorted = new RefUnsortedList<>();
  private int numElementsAccessed = 0;

  public SortsLL(ArrayList<T> list)
  {
      setLLValues(values,list);
      setLLValues(preSorted, list);
      size = values.size();
  }
  
  public void resetLL()
  {
      values.clear();
      preSorted.reset();
      
      for (int i = 0; i < preSorted.size(); i++)
      {
          values.add(preSorted.getNext());
      }
  }
  
  public int getSize()
  {
      return size;
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
  
  public void setLLValues(RefUnsortedList llToFill, ArrayList<T> newLL)
  {
      if (llToFill != null)
        llToFill.clear();
      for (T element : newLL)
        llToFill.add(element);
  }
  
  public boolean isSorted()
  // Returns true if the array values are sorted and false otherwise.
  {
    boolean sorted = true;
    values.reset();
    for (int index = 0; index < (size - 1); index++)
    {
      if (((Comparable)values.getNext()).compareTo(values.getNext()) > 0)
        sorted = false;
    }
    return sorted;
  }

  public void printValues()
  // Prints all the values integers.
  {
    values.reset();
    T value;
    System.out.println("The values LL is:");
    for (int index = 0; index < size; index++)
    {
      value = values.getNext();
      if (((index + 1) % 10) == 0)
        System.out.println(value);
      else
        System.out.print(value + " ");
    }
    System.out.println();
  }
  
  public boolean linearSearch(T element)
  {
      values.reset();
      for (int i = 0; i < size; i++)
      {
          numElementsAccessed++;
          if (values.getNext().equals(element))
          {
              return true;
          }
      }
      return false;
  }
  
  private LLNode<T> middle(LLNode start, LLNode last)
  {
      if (start == null)
          return null;
      
      LLNode slow = start;
      LLNode fast = start.getLink();
      
      while (fast != last)
      {
          numElementsAccessed++;
          fast = fast.getLink();
          if (fast != last)
          {
              slow = slow.getLink();
              fast = fast.getLink();
          }
      }
      return slow;
  }
  
  public boolean binarySearch(T element)
  {
      values.reset();
      LLNode<T> start = values.getHead();
      LLNode<T> last = null;
      
      do
      {
          numElementsAccessed++;
          
          if (start == last && start != element)
              return false;
          
          LLNode<T> mid = middle(start, last);
          
          if (mid == null)
              return false;
          
          else if (mid.getInfo().compareTo(element) < 0)
              start = mid.getLink();
          
          else if (mid.getInfo().compareTo(element) > 0)
              last = mid;
          
          else return true;
          
      } while (last == null || last.getLink() != start);
      
      return false;
  }
  
  public void shortBubble()
  {
      numElementsAccessed = values.shortBubble();
  }
  
  public void insertionSort()
  {
      numElementsAccessed = values.insertionSort();
  }
  
  public void mergeSort()
  {
      numElementsAccessed = values.mergeSort();
  }
  
  public void quickSort()
  {
      numElementsAccessed = values.quickSort();
  }

//  /////////////////////////////////////////////////////////////////
//  //
//  //  Insertion Sort
//
//  static void insertItem(int startIndex, int endIndex)
//  // Upon completion, values[0]..values[endIndex] are sorted.
//  {
//    boolean finished = false;
//    int current = endIndex;
//    boolean moreToSearch = true;
//    while (moreToSearch && !finished)
//    {
//      numElementsAccessed++;
//      if (values[current] < values[current - 1])
//      {
//        swap(current, current - 1);
//        current--;
//        moreToSearch = (current != startIndex);
//      }
//      else
//        finished = true;
//    }
//  }
//
//  static void insertionSort()
//  // Sorts the values array using the insertion sort algorithm.
//  {
//   for (int count = 1; count < SIZE; count++)
//      insertItem(0, count);
//  }
//
//
//  /////////////////////////////////////////////////////////////////
//  //
//  //  Merge Sort
//
//  static void merge (int leftFirst, int leftLast, int rightFirst, int rightLast)
//  // Preconditions: values[leftFirst]..values[leftLast] are sorted.
//  //                values[rightFirst]..values[rightLast] are sorted.
//  // 
//  // Sorts values[leftFirst]..values[rightLast] by merging the two subarrays.
//  {
//    int[] tempArray = new int [SIZE];
//    int index = leftFirst;
//    int saveFirst = leftFirst;  // to remember where to copy back
//
//    while ((leftFirst <= leftLast) && (rightFirst <= rightLast))
//    {
//      numElementsAccessed++;
//      if (values[leftFirst] < values[rightFirst])
//      {
//        tempArray[index] = values[leftFirst];
//        leftFirst++;
//      }
//      else
//      {
//        tempArray[index] = values[rightFirst];
//        rightFirst++;
//      }
//      index++;
//    }
//
//    while (leftFirst <= leftLast)
//    // Copy remaining items from left half.
//
//    {
//      numElementsAccessed++;  
//      tempArray[index] = values[leftFirst];
//      leftFirst++;
//      index++;
//    }
//
//    while (rightFirst <= rightLast)
//    // Copy remaining items from right half.
//    {
//      numElementsAccessed++;
//      tempArray[index] = values[rightFirst];
//      rightFirst++;
//      index++;
//    }
//
//    for (index = saveFirst; index <= rightLast; index++)
//    {
//      values[index] = tempArray[index];
//    }
//  }
//
//  static void mergeSort(int first, int last)
//  // Sorts the values array using the merge sort algorithm.
//  {
//    if (first < last)
//    {
//      int middle = (first + last) / 2;
//      mergeSort(first, middle);
//      mergeSort(middle + 1, last);
//      merge(first, middle, middle + 1, last);
//    }
//  }
//
//
//  /////////////////////////////////////////////////////////////////
//  //
//  //  Quick Sort
//
//  static int split(int first, int last)
//  {
//    int splitVal = values[first];
//    int saveF = first;
//    boolean onCorrectSide;
//
//    first++;
//    do
//    {
//      onCorrectSide = true;
//      while (onCorrectSide)             // move first toward last
//      {
//        numElementsAccessed++;
//        if (values[first] > splitVal)
//          onCorrectSide = false;
//        else
//        {
//          first++;
//          onCorrectSide = (first <= last);
//        }
//      }
//
//      onCorrectSide = (first <= last);
//      while (onCorrectSide)             // move last toward first
//      {
//        numElementsAccessed++;
//        if (values[last] <= splitVal)
//          onCorrectSide = false;
//        else
//         {
//          last--;
//          onCorrectSide = (first <= last);
//         }
//      }
//
//      if (first < last)                
//      {
//        swap(first, last);
//        first++;
//        last--;
//      }
//    } while (first <= last);
//
//    swap(saveF, last);
//    return last;
//  }
//
//  static void quickSort(int first, int last)
//  {
//    if (first < last)
//    {
//      int splitPoint;
//
//      splitPoint = split(first, last);
//      // values[first]..values[splitPoint - 1] <= splitVal
//      // values[splitPoint] = splitVal
//      // values[splitPoint+1]..values[last] > splitVal
//
//      quickSort(first, splitPoint - 1);
//      quickSort(splitPoint + 1, last);
//    }
//  }
}
