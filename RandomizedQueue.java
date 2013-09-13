import java.util.Iterator;

public class RandomizedQueue<Item> implements Iterable<Item> {

   private Item[] items;
   private int minSize;
   private int nSize;
   private int nCount;
   private int iHead;
   private int iTail;

   public RandomizedQueue()           // construct an empty randomized queue
   {
      minSize = 32;
      nSize = minSize;
      nCount = 0;
      iHead = 0;
      iTail = 0;
      items = (Item[]) new Object[nSize];
   }
   
   public boolean isEmpty()           // is the queue empty?
   {
      return (nCount == 0);
   }
   
   public int size()                  // return the number of items on the queue
   {
      return nCount;
   }
   
   public void enqueue(Item item)     // add the item
   {
      if (item == null)
         throw new java.lang.NullPointerException();

      if ((iTail >= nSize) && (nCount < nSize))
         compact();
         
      if (nCount >= nSize)
         resize(nSize*2);
         
      items[iTail++] = item;
      nCount++;
   }
   
   public Item dequeue()              // delete and return a random item
   {
      if (nCount <= 0)
         throw new java.util.NoSuchElementException();
         
      int x = getRandomIndex();
      
      if (x != iHead)
         swapItems(x, iHead);
         
      Item i = items[iHead];
      items[iHead] = null;
      iHead++;
      nCount--;
      
      if (((nSize/2) >= minSize) && (nCount <= (nSize/4)))
         resize(nSize/2);
         
      return i;
   }
      
   public Item sample()               // return (but do not delete) a random item
   {
      if (nCount <= 0)
         throw new java.util.NoSuchElementException();
   
         int x = getRandomIndex();

         return items[x];
   }
   
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {
       return new MyIterator();
   }
   
   private void compact()
   {
      if (iHead == 0) return;
      
      int i;
      
      for (i = 0; i < nCount; i++)
      {
         items[i] = items[iHead+i];
      }
      
      iHead = 0;
      iTail = iHead + nCount;
   }
   
   
   private void resize(int newSize)
   {
      Item[] newArray = (Item[]) new Object[newSize];
      
      for (int i = 0; i < nCount; i++)
      {
         newArray[i] = items[iHead+i];
         items[iHead+i] = null;
      }
      
      iHead = 0;
      iTail = iHead + nCount;
      items = null;
      items = newArray;
      nSize = newSize;
   }
   
   private int getRandomIndex()
   {
       return iHead + (int) (Math.random()*nCount);
   }
   
   private void swapItems(int i, int j)
   {
      Item t = items[i];
      items[i] = items[j];
      items[j] = t;
   }
   
   private class MyIterator implements Iterator<Item>
   {
      private int current;
      private boolean[] served;
       
      public MyIterator()
      {
         current = 0;
         served = new boolean[nCount];
      }
       
       public boolean hasNext()
       {
          return current < nCount;
       }
       
       public Item next()
       {
          if (current >= nCount)
             throw new java.util.NoSuchElementException();
             
          int x = (int) (Math.random()*nCount);
          
          while (served[x % nCount])
          {
              x++;
          }
          
          served[x % nCount] = true;
          current++;
          return items[iHead + (x % nCount)];
       }
       
       public void remove() { throw new java.lang.UnsupportedOperationException(); }
    }
}