import java.util.Iterator;

public class Deque<Item> implements Iterable<Item>  {
 
   private int iHead;
   private int iTail;
   private int cElements;
   private int nSize;
   private Item[] items;

   private class DequeIterator implements Iterator<Item>
   {
       public int current = iHead;
       public boolean hasNext() { return current < iTail; }
       public Item next() 
       {
           if ( current >= iTail )
           {
               throw new java.lang.IndexOutOfBoundsException();
           }
           
           Item i = items[current++];
           return i;
       }
       public void remove() { }
   }
   
   public Deque()                     // construct an empty deque
   {
       nSize = 512;
       iHead = nSize / 2;
       iTail = nSize / 2;
       cElements = 0;
       items = (Item[]) new Object[nSize];
   }
   
   public Iterator<Item> iterator()
   {
      return new DequeIterator();
   }
   
   public boolean isEmpty()           // is the deque empty?
   {
      return cElements == 0;
   }
   
   public int size()                  // return the number of items on the deque
   {
      return cElements;
   }
   
   public void addFirst(Item item)    // insert the item at the front
   {
       if (iHead == 0)
       {
           // reached the (head) end of the array, resize
           resize(cElements * 2);
       }
       
       items[--iHead] = item;
       cElements++;
   }
   
   public void addLast(Item item)     // insert the item at the end
   {
       if (iTail >= nSize )
       {
           // reached the (tail) end of the array, resize
           resize(cElements * 2);
       }
       
       items[iTail++] = item;
       cElements++;
   }
   
   public Item removeFirst()          // delete and return the item at the front
   {
       if ( iHead >= iTail )
       {
            throw new java.lang.IndexOutOfBoundsException();
       }
       
       Item i = items[iHead];
       items[iHead++] = null;
       cElements--;
       return i;
   }
   
   public Item removeLast()           // delete and return the item at the end
   {
       if ( iTail < iHead )
       {
           throw new java.lang.IndexOutOfBoundsException();
       }
       
       Item i = items[--iTail];
       items[iTail] = null;
       cElements--;
       return i;
   }
   
   private void resize(int newsize)
   {
       if ( newsize < 512 )
       {
           newsize = 512;
       }
       
       int start = (newsize - cElements) / 2;
       
       Item[] newItems = (Item[]) new Object[newsize];
       
       for (int i = 0; i < cElements; i++ )
       {
           newItems[i+start] = items[i+iHead];
       }
       
       iHead = start;
       iTail = iHead+cElements;
       items = null;
       items = newItems;
       nSize = newsize;
   }
}