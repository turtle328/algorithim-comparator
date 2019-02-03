package sorts.frontend;

import java.util.ArrayList;
import java.util.Scanner;
import sorts.backend.HashList;
import sorts.backend.SortsArray;
import sorts.backend.SortsLL;
import sorts.database.SortsTextFile;


public class SortsTestController<T extends Comparable<T>>
{
    private SortsTextFile<T> sortsTextFile = null;
    private ArrayList<T> arrayFromFile = null;
    private SortsArray<T> arrayTest = null;
    private SortsLL<T> llTest = null;
    private Scanner input = new Scanner(System.in);
    private Class<T> type;
    
    private int llComparisons = 0;
    private int aryComparisons = 0;
    private int extraAryComparisons = 0;
    private int extraLLComparisons = 0;
    private boolean found = false;
    private int listType;
    private int numElements;
    private int algorithim;
    private T valueToSearch = null;
    
    public static final int SORTED = 0;
    public static final int REVERSED = 1;
    public static final int RANDOM = 2;
    public static final int NEAR_SORTED = 3;
    public static final int DUPLICATES = 4;
    
    public static final int BUBBLE_SORT = 0;
    public static final int INSERTION_SORT = 1;
    public static final int MERGE_SORT = 2;
    public static final int QUICK_SORT = 3;
    public static final int HEAP_SORT = 4;
    public static final int LINEAR_SEARCH = 5;
    public static final int BINARY_SEARCH = 6;
    public static final int HASH_SEARCH = 7;
    
    public SortsTestController(Class<T> type)
    {
        this.type = type;
    }
    
    public String getList(int listType, int numElements)
    {
        this.listType = listType;
        this.numElements = numElements;
        String file;
        
        if (listType == SORTED)
            file = "Sorted";
        
        else if (listType == REVERSED)
            file = "Reverse";
        
        else if (listType == RANDOM)
            file = "Random";
        
        else if (listType == NEAR_SORTED)
            file = "NearSorted";
        
        else
            file = "Duplicate";
        
        if (type == Integer.class)
            file += "Int.txt";
        else
            file += "Str.txt";

        sortsTextFile = new SortsTextFile<T>(file, numElements);
        arrayFromFile = sortsTextFile.getList();

        arrayTest = new SortsArray<>(new ArrayList<>(arrayFromFile));
        llTest = new SortsLL<>(new ArrayList<>(arrayFromFile));
        
        String list = "";
        
        for (T element : arrayFromFile)
        {
            list += element + "\n";
        }
        
        return list;
    }
    
    public String[] sort(int algorithim)
    {
        this.algorithim = algorithim;
        arrayTest.resetArray();
        llTest.resetLL();
        
        if (algorithim == BUBBLE_SORT)
        {
            arrayTest.shortBubble();
            llTest.shortBubble();
        }

        if (algorithim == INSERTION_SORT)
        {
            arrayTest.insertionSort();
            llTest.insertionSort();
        }

        if (algorithim == MERGE_SORT)
        {
            arrayTest.mergeSort();
            llTest.mergeSort();
        }

        if (algorithim == QUICK_SORT)
        {
            arrayTest.quickSort();
            llTest.quickSort();
        }

        if (algorithim == HEAP_SORT)
        {
            arrayTest.heapSort();
        }

        aryComparisons = arrayTest.getNumElementsAccessed();
        llComparisons = llTest.getNumElementsAccessed();
        
        String[] resultsOutput = new String[2];
        
        String output = "";
        
        for (T element : arrayTest.getValuesArray())
        {
            output += element + "\n";
        }
        
        resultsOutput[0] = appendResults();
        resultsOutput[1] = output;
        
        return resultsOutput;
    }
    
    public String[] search(int algorithim, T valueToSearch)
    {
        this.algorithim = algorithim;
        this.valueToSearch = valueToSearch;
        arrayTest.resetArray();
        llTest.resetLL();        
        
        if (algorithim == LINEAR_SEARCH)
        {
            found = arrayTest.linearSearch(valueToSearch);
            llTest.linearSearch(valueToSearch);

            aryComparisons = arrayTest.getNumElementsAccessed();
            llComparisons = llTest.getNumElementsAccessed();
        }
        if (algorithim == BINARY_SEARCH)
        {
            if (!arrayTest.isSorted())
            {
                arrayTest.heapSort();
                llTest.mergeSort();
                extraAryComparisons = arrayTest.getNumElementsAccessed();
                extraLLComparisons = llTest.getNumElementsAccessed();
            }

            found = arrayTest.binarySearch(valueToSearch);
            llTest.binarySearch(valueToSearch);

            aryComparisons = arrayTest.getNumElementsAccessed();
            llComparisons = llTest.getNumElementsAccessed();
        }
        if (algorithim == HASH_SEARCH)
        {
            HashList<T> hashTable = new HashList<>(arrayTest.getValuesArray());

            extraAryComparisons = hashTable.getNumOperations();

            found = hashTable.findElement(valueToSearch);

            aryComparisons = hashTable.getNumOperations();
        }
        
        String[] resultsOutput = new String[2];
        
        String output = "";
        
        for (T element : arrayTest.getValuesArray())
        {
            output += element + "\n";
        }
        
        resultsOutput[0] = appendResults();
        resultsOutput[1] = output;
        
        return resultsOutput;
    }
    
    private String appendResults()
    {
        String log = "List type: " + getListType() +
                     "\nList size: " + numElements +
                     "\nData type: " + getDataType() +
                     "\nAlgorithim: " + getAlgorithim() +
                     "\nArray num comparisons: " + aryComparisons;
        
        if (llComparisons != 0)
            log += "\nLL num comparisons: " + llComparisons;
        
        else
            log += "\nLL num comparisons: N/A";
        
        if (valueToSearch != null)
            log += "\nValue to search: " + valueToSearch +
                   "\nValue found: " + found;
        
        if (extraAryComparisons != 0)
        {
            log += "\nExtra operations - Array: " + extraAryComparisons;
        
            if (extraLLComparisons != 0)
                log += "\nExtra operations - LL: " + extraLLComparisons;
            else
                log += "\nExtra operations - LL: N/A";
        }
        
        SortsTextFile.appendResults(log);
        
        aryComparisons = 0;
        llComparisons = 0;
        valueToSearch = null;
        extraAryComparisons = 0;
        extraLLComparisons = 0;
        
        return log;
    }
    
    private String getListType()
    {
        if (listType == SORTED)
            return "Sorted";
        else if (listType == REVERSED)
            return "Reversed";
        else if (listType == RANDOM)
            return "Random";
        else if (listType == NEAR_SORTED)
            return "Near Sorted";
        else
            return "Duplicates";
    }
    
    private String getDataType()
    {
        if (type == Integer.class)
            return "Integer";
        else
            return "String";
    }
    
    private String getAlgorithim()
    {
        if (algorithim == BUBBLE_SORT)
            return "Bubble Sort";
        else if (algorithim == INSERTION_SORT)
            return "Insertion Sort";
        else if (algorithim == MERGE_SORT)
            return "Merge Sort";
        else if (algorithim == QUICK_SORT)
            return "Quick Sort";
        else if (algorithim == HEAP_SORT)
            return "Heap Sort";
        else if (algorithim == LINEAR_SEARCH)
            return "Linear Search";
        else if (algorithim == BINARY_SEARCH)
            return "Binary Search";
        else
            return "Hash Search";
    }
    
    public void setListType(int listType)
    {
        this.listType = listType;
    }
    
    public void setAlgorithim(int algorithim)
    {
        this.algorithim = algorithim;
    }
}
