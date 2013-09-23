public class InsertionSort
{
    public void sort(Comparable[] a, int size)
    {
       int i, j;
       
       for (i = 0; i < size; i++)
          for (j = i; j > 0; j--)
          {
             if ( a[j].compareTo(a[j-1]) < 0 )
                swap(a, j, j-1);
             else
                break;
          }
    }
    
    private void swap(Comparable[] a, int i, int j)
    {
       Comparable t = a[i];
       a[i] = a[j];
       a[j] = t;
    }
}