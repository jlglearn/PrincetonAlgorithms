import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

   private int minSize;
   private int nSize;
   private int nCount;
   private int iHead;
   private int iTail;
   private Item[] items;

   public Deque()                     // construct an empty deque
   {
      minSize = 32;
      nSize = minSize;
      nCount = 0;
      iHead = nSize/2;
      iTail = nSize/2;
      items = (Item[]) new Object[nSize];
   }
   
   public boolean isEmpty()           // is the deque empty?
   {
      return (nCount == 0);
   }
   
   public int size()                  // return the number of items on the deque
   {
      return nCount;
   }
   
   public void addFirst(Item item)    // insert the item at the front
   {
      if (item == null)
      {
         throw new java.lang.NullPointerException();
      }

      if (iHead == 0)
      {
         // reached the (front) end of the array, resize
         resize(nSize*2);
      }
      
      items[--iHead] = item;
      nCount++;
   }
   
   public void addLast(Item item)     // insert the item at the end
   {
      if (item == null)
      {
         throw new java.lang.NullPointerException();
      }
  
      if (iTail >= nSize)
      {
         // reached the (tail) end of the array, resize
         resize(nSize*2);
      }
      
      items[iTail++] = item;
      nCount++;
   }
   
   public Item removeFirst()          // delete and return the item at the front
   {
      if (iHead >= iTail)
      {
         throw new java.util.NoSuchElementException();
      }
      
      Item i = items[iHead];
      items[iHead] = null;
      iHead++;
      nCount--;
      
      if (((nSize/2) >= minSize) && (nCount <= (nSize/4)))
         resize(nSize / 2);
         
      return i;
   }
   
   public Item removeLast()           // delete and return the item at the end
   {
      if (iTail <= iHead)
      {
         throw new java.util.NoSuchElementException();
      }
      
      Item i = items[--iTail];
      items[iTail] = null;
      nCount--;
      
      if (((nSize/2) >= minSize) && (nCount <= (nSize/4)))
         resize(nSize / 2);
         
      return i;
   }
   
   public Iterator<Item> iterator()   // return an iterator over items in order from front to end
   {
      return new MyIterator();
   }
   
   private class MyIterator implements Iterator<Item>
   {
      private int current = 0;
      
      public boolean hasNext() { return current < nCount; }
      public Item next() 
      { 
         if (current >= nCount)
            throw new java.util.NoSuchElementException();
 
         int i = iHead + current;
         current++;

         return items[i]; 
   }
   
      public void remove() { throw new java.lang.UnsupportedOperationException(); }
   }
   
   private void resize(int newSize)
   {
      int i, j, newHead;
      Item[] newArray = (Item[]) new Object[newSize];
      
      newHead = (newSize-nCount)/2;
      
      for (i = newHead, j = iHead; i < newHead + nCount; i++, j++)
      {
         newArray[i] = items[j];
         items[j] = null;
      }
      
      iHead = newHead;
      iTail = newHead + nCount;
      items = null;
      items = newArray;  
      nSize = newSize;    
   }
}