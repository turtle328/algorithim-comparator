package sorts.database;

import java.io.*;
import java.util.*;

public class SortsTextFile<T extends Comparable<T>>
{
    private ArrayList<T> listFromFile = null;
    private File listInfo = null;
    private int size;
    
    private final String FIELD_SEP = "\t";
    
    public SortsTextFile(String fileName, int size)
    {
        listInfo = new File(fileName);
        this.size = size;
        listFromFile = getList();
    }
    
    public ArrayList<T> getList()
    {
        if (listFromFile != null) return copyList();
        
        checkFile();
        
        try (BufferedReader in = new BufferedReader(
                                 new FileReader(listInfo)))
        {
            listFromFile = new ArrayList<>();
            
            String line = in.readLine();
            int numElements = 0;
            
            grabElementsLoop:
            while (line != null)
            {
                String[] columns = line.split(FIELD_SEP);
                
                for (int i = 0; i < columns.length; i++)
                {
                    if (numElements >= size)
                        break grabElementsLoop;
                    
                    try {listFromFile.add((T) Integer.valueOf(columns[i]));}
                    catch(NumberFormatException e)
                    {listFromFile.add((T) columns[i]);}
                    
                    numElements++;
                }
                
                line = in.readLine();
            }
            in.close();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
        
        return copyList();
    }
    
    public static void appendResults(String log)
    {
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter("Results.txt", true))))
        {
            String[] rows = log.split("\n");
            
            out.println();
            for (String s : rows)
                out.println(s);
            
            out.close();
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void createRevereseTextFile(String fileName)
    {
        if (listInfo == null)
            getList();
        
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(fileName))))
        {
            for (int i = listFromFile.size() - 1; i >= 0; i--)
                out.println(listFromFile.get(i));
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void createDuplicateTextFile(String fileName, int size)
    {
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(fileName))))
        {
            for (int i = 0; i < size; i++)
            {
                Random rand = new Random();
                int randomNum = rand.nextInt(2);
                
                if (randomNum == 1)
                    out.println("Flibbertigibbet");
                else
                    out.println("Nudiustertian");
            }
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void createSortedTextFile(String fileName)
    {
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(fileName))))
        {
            for (int i = 0; i < 10000; i++)
            {
                out.println(i);
            }
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    public void createNearSortedTextFile(String fileName)
    {
        if (listInfo == null)
            getList();
        
        try (PrintWriter out = new PrintWriter(
                               new BufferedWriter(
                               new FileWriter(fileName))))
        {
            Random rand = new Random();
            int randNum;
            
            for (int i = 0; i < listFromFile.size(); i++)
            {
                randNum = rand.nextInt(100) + 1;                
                if (randNum > 99)
                {
                    randNum = rand.nextInt(i);
                    Collections.swap(listFromFile, i, randNum);
                }
            }
            
            for (int i = 0; i < listFromFile.size(); i++)
            {
                out.println(listFromFile.get(i));
            }
        }
        
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    
    private void checkFile()
    {
        try {if (!listInfo.exists()) listInfo.createNewFile();}
        catch (IOException e) {System.err.println(e);}
    }
    
    private ArrayList<T> copyList()
    {
        ArrayList<T> listCopy = new ArrayList<>();
        for (T element : listFromFile) listCopy.add(element);
        return listCopy;
    }
}
