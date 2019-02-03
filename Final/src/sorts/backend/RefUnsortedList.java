//----------------------------------------------------------------------------
// RefUnsortedList.java          by Dale/Joyce/Weems                 Chapter 6
//
// Implements the ListInterface using references (a linked list).
//
// Null elements are not permitted on a list.
//
// One constructor is provided, one that creates an empty list.
//----------------------------------------------------------------------------

package sorts.backend;

import sorts.backend.LLNode;

public class RefUnsortedList<T> implements ListInterface<T>
{

  protected int numElements;      // number of elements in this list
  protected LLNode<T> currentPos; // current position for iteration

  // set by find method
  protected boolean found;        // true if element found, else false
  protected LLNode<T> location;   // node containing element, if found
  protected LLNode<T> previous;   // node preceeding location

  protected LLNode<T> list;       // first node on the list
  protected LLNode<T> last;       // last node on the list
  
  protected int numComparisons = 0;

  public RefUnsortedList()
  {
    numElements = 0;
    list = null;
    last = null;
    currentPos = null;
  }
  
  protected LLNode<T> getHead()
  {
      return list;
  }
  
  private int getNumComparisons()
  {
      int hold = numComparisons;
      numComparisons = 0;
      return hold;
  }
  
  public void clear()
  {
      list = null;
      last = null;
      numElements = 0;
  }
  
  public void add(T element)
  // Adds element to this list.
  {
    LLNode<T> newNode = new LLNode<T>(element);
    if (last == null)
    {
        list = newNode;
        last = list;
    }
    else
    {
        last.setLink(newNode);
        last = newNode;
    }
    numElements++;
  }

  protected void find(T target)
  // Searches list for an occurence of an element e such that
  // e.equals(target). If successful, sets instance variables
  // found to true, location to node containing e, and previous
  // to the node that links to location. If not successful, sets 
  // found to false.
  {
    location = list;
    found = false;

    while (location != null) 
    {
      if (location.getInfo().equals(target))  // if they match
      {
       found = true;
       return;
      }
      else
      {
        previous = location;
        location = location.getLink();
      }
    }
  }

  public int size()
  // Returns the number of elements on this list. 
  {
    return numElements;
  }

  public boolean contains (T element)
  // Returns true if this list contains an element e such that 
  // e.equals(element); otherwise, returns false.
  {
    find(element);
    return found;
  }

  public boolean remove (T element)
  // Removes an element e from this list such that e.equals(element)
  // and returns true; if no such element exists, returns false.
  {
    find(element);
    if (found)
    {
      if (list == location)
      {
        list = list.getLink();    // remove first node
      }
      else
        previous.setLink(location.getLink());  // remove node at location

      numElements--;
    }
    return found;
  }

  public T get(T element)
  // Returns an element e from this list such that e.equals(element);
  // if no such element exists, returns null.
  {
    find(element);    
    if (found)
      return location.getInfo();
    else
      return null;
  }
  
  public String toString()
  // Returns a nicely formatted string that represents this list.
  {
    LLNode<T> currNode = list;
    String listString = "List:\n";
    while (currNode != null)
    {
      listString = listString + "  " + currNode.getInfo() + "\n";
      currNode = currNode.getLink();
    }
    return listString;
  }  

  public void reset()
  // Initializes current position for an iteration through this list,
  // to the first element on this list.
  {
    currentPos  = list;
  }

  public T getNext()
  // Preconditions: The list is not empty
  //                The list has been reset
  //                The list has not been modified since most recent reset
  //
  // Returns the element at the current position on this list.
  // If the current position is the last element, then it advances the value 
  // of the current position to the first element; otherwise, it advances
  // the value of the current position to the next element.
  {
    T next = currentPos.getInfo();
    if (currentPos.getLink() == null)
      currentPos = list;
    else
      currentPos = currentPos.getLink();
    return next;
  }
  
  //==========================================================================
  //                                SORTS
  //==========================================================================
  
  //variables
  private LLNode sorted;
  private int counter = 0;

  //test variables
  //public int numTimesRun = 0;
  //public int numTimesRun2 = 0;
  
  protected int shortBubble() 
  {
        if (numElements > 1) 
        {
            boolean wasChanged;
            int completePortion = 0;
            int curElement;

            do 
            {
                LLNode current = list;
                LLNode previous = null;
                LLNode next = list.getLink();
                wasChanged = false;
                curElement = 0;

                while (curElement < (numElements - 1) - completePortion) 
                {
                    numComparisons++;
                    if (((Comparable) current.getInfo()).compareTo(next.getInfo()) >= 0) 
                    {
                        wasChanged = true;

                        if (previous != null) 
                        {
                            LLNode hold = next.getLink();

                            previous.setLink(next);
                            next.setLink(current);
                            current.setLink(hold);
                        }
                        
                        else 
                        {
                            LLNode hold = next.getLink();

                            list = next;
                            next.setLink(current);
                            current.setLink(hold);
                        }

                        previous = next;
                        next = current.getLink();
                    }
                    
                    else 
                    { 
                        previous = current;
                        current = next;
                        next = next.getLink();
                    }
                    curElement++;
                }
                completePortion++;
            }
            while (wasChanged);
        }
        return getNumComparisons();
    }
  
  protected int insertionSort() 
    {
        sorted = null;
        LLNode current = list;
        while (current != null) 
        {
            LLNode next = current.getLink();
            sortedInsert(current);
            current = next;
        }
        list = sorted;
        
        return getNumComparisons();
    }
  
  private void sortedInsert(LLNode newNode)
    {
        // Special case for the head end
        numComparisons++;
        if (sorted == null || ((Comparable) sorted.getInfo()).compareTo(newNode.getInfo()) > 0) 
        {
            newNode.setLink(sorted);
            sorted = newNode;
        }
        else
        {
            LLNode current = sorted;
            // Locate the node before the point of insertion
            while (current.getLink() != null && ((Comparable) current.getLink().getInfo()).compareTo(newNode.getInfo()) < 0) 
            {
                numComparisons++;
                current = current.getLink();
            }
            newNode.setLink(current.getLink());
            current.setLink(newNode);
        }
    }
  
  protected int mergeSort()
  {
        list = mergeSort(list);
        return getNumComparisons();
  }
   
  private LLNode sortedMerge(LLNode a, LLNode b) 
  {
        numComparisons++;
        LLNode result = null;
        /* Base cases */
        if (a == null)
        {
            return b;
        }
        if (b == null)
        {
            return a;
        }
        /* Pick either a or b, and recur */
        if (((Comparable)a.getInfo()).compareTo(b.getInfo()) <= 0 )
        {
            result = a;
            result.setLink(sortedMerge(a.getLink(), b));
        } 
        else
        {
            result = b;
            result.setLink(sortedMerge(a, b.getLink()));
        }
        return result;
    }
  
  private LLNode mergeSort(LLNode h) 
    {
        //numTimesRun++;
        // Base case : if head is null
        if (h == null || h.getLink() == null)
        {
            return h;
        }
 
        // get the middle of the list

        LLNode middle = getMiddle(h);
        LLNode nextofmiddle = middle.getLink();
 
        // set the next of middle node to null
        middle.setLink(null);
 
        // Apply mergeSort on left list
        LLNode left = mergeSort(h);
 
        // Apply mergeSort on right list
        LLNode right = mergeSort(nextofmiddle);
 
        // Merge the left and right lists
        LLNode sortedlist = sortedMerge(left, right);
        
        return sortedlist;
    }
 
  // Utility function to get the middle of the linked list
  private LLNode getMiddle(LLNode h) 
    {
        //Base case
        if (h == null)
            return h;
        LLNode fastptr = h.getLink();
        LLNode slowptr = h;
         
        // Move fastptr by two and slow ptr by one
        // Finally slowptr will point to middle node
        while (fastptr != null)
        {
            //numTimesRun2++;
            fastptr = fastptr.getLink();
            if (fastptr != null)
            {
                slowptr = slowptr.getLink();
                fastptr=fastptr.getLink();
            }
        }
        return slowptr;
    }
  
  protected int quickSort()
  {
      list = quickSortRecur(list, last);
      return getNumComparisons();
  }
  
  // choose the last node as the pivot
  private LLNode quickSortRecur(LLNode head, LLNode tail) 
  {
    	if (head == tail) return head;
    	
    	LLNode pivot = tail, cur = head, pre = null, newhead = null;
    	
    	while (pivot != cur) 
        {
            numComparisons++;
            if (((Comparable)cur.getInfo()).compareTo(pivot.getInfo()) < 0) 
            {
                if (null == newhead) newhead = cur;
                pre = cur;
                cur = cur.getLink();
            } 
            
            else 
            {
                LLNode tmp1 = tail.getLink();
                LLNode tmp2 = cur.getLink();
                
                tail.setLink(cur);
                tail = cur;
                tail.setLink(tmp1);

                cur = tmp2;
                if (null != pre) pre.setLink(cur);
            }
        }
    	
        if (pre != null) 
        {
    		newhead = quickSortRecur(newhead, pre);
    		LLNode tmp = newhead;
    		while (pivot != tmp.getLink()) tmp = tmp.getLink();
    		tmp.setLink(pivot);
    	}
        
        else newhead = pivot;
        
    	if (pivot != tail) pivot.setLink(quickSortRecur(pivot.getLink(), tail));
        
    	return newhead;
    }
}