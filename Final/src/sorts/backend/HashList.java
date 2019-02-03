package sorts.backend;

import java.util.ArrayList;

public class HashList<T extends Comparable<T>>
{
    private final int SIZE = 1000;
    private ArrayList<RefUnsortedList<T>> hashTable = new ArrayList<>(SIZE);
    private boolean isInt = true;
    private int numOperations = 0;

    public HashList(ArrayList<T> list)
    {
        if (list.get(0) instanceof String)
            isInt = false;
        
        for (int i = 0; i < SIZE; i++)
            hashTable.add(new RefUnsortedList<>());
        
        for (T element : list)
        {
            numOperations++;
            addHash(element);
        }
    }
    
    public int getNumOperations()
    {
        int temp = numOperations;
        numOperations = 0;
        return temp;
    }
    
    private int hash(T element)
    {
        if (isInt)
            return (Integer) element % SIZE;
        
        else
        {
            String s = (String) element;
            int intLength = s.length() / 4;
            long sum = 0;
            
            for (int j = 0; j < intLength; j++) 
            {
                char c[] = s.substring(j * 4, (j * 4) + 4).toCharArray();
                long mult = 1;
                for (int k = 0; k < c.length; k++) 
                {
                    sum += c[k] * mult;
                    mult *= 256;
                }
            }

            char c[] = s.substring(intLength * 4).toCharArray();
            long mult = 1;
            
            for (int k = 0; k < c.length; k++) 
            {
                sum += c[k] * mult;
                mult *= 256;
            }
        return (int) (Math.abs(sum) % SIZE);
        }
   }
    
    public boolean findElement(T element)
    {
        int location = hash(element);
        RefUnsortedList<T> locationLL = hashTable.get(location);
        
        locationLL.reset();
        
        for (int i = 0; i < locationLL.size(); i++)
        {
            numOperations++;
            if (locationLL.getNext().equals(element))
                return true;
        }
        return false;
    }
    
    private void addHash (T element)
    {
        int location = hash(element);
        hashTable.get(location).add(element);
    }
}
