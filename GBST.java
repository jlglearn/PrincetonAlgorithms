public class GBST<Key extends Comparable<Key>, Value>
{
    private Node root;
    private int nElements;
    
    public GBST()
    {
        nElements = 0;
        root = null;
    }
    
    public boolean isEmpty()
    {   return nElements == 0;  }
    
    public int size()
    {   return nElements; }
    
    public void put(Key key, Value value)
    {   root = put(root, key, value);  }        
    
    public Node put(Node p, Key key, Value value)
    {
        if (p == null)
        {
            nElements++;
            return new Node(key, value);        
        }
        
        int r = p.key.compareTo(key);
        if (r < 0) p.left = put(p.left, key, value);
        else if (r > 0) p.right = put(p.right, key, value);
        else p.value = value;
        return p;
    }
    
    public Value get(Key key)
    {
        if (isEmpty()) throw new java.lang.IndexOutOfBoundsException();
        
        Node s = root;
        
        while (s != null)
        {
            int r = s.key.compareTo(key);
            if (r < 0) s = s.left;
            else if (r > 0) s = s.right;
            else return s.value;
        }
        
        return null;
    }
    
    public void insert(Key key, Value value)
    {
        Node n = new Node(key, value);
        
        if (nElements == 0)
            root = n;
        else
        {
            Node p = null;
            Node q = root;
            int r = 0;
            
            while (q != null)
            {
                r = q.key.compareTo(key);
                p = q;
                
                if (r < 0) q = q.left;
                else if (r > 0) q = q.right;
                else
                {
                    n = null;
                    q.value = value;
                    return;
                }
            }
            
            if (r < 0) p.left = n;
            else p.right = n;
            
            n.parent = p;
        }
        
        nElements++;        
    }
        
    private class Node {
        Key key;
        Value value;
        Node left;
        Node right;
        Node parent;
        
        Node(Key key, Value value) 
        {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
            parent = null;
        }
        
    }
    
    
    
    public static void main(String argv[])
    {
        GBST<String, Integer> gbst = new GBST<String, Integer>();
        final int maxItems = 1024;
        
        for (int i = 0; i < maxItems; i++)
            gbst.put("S" + i, (Integer) i);
            
        for (int i = maxItems-1; i >= 0; i--)
            StdOut.println("[" + i + "]: " + (int) gbst.get("S"+i));
    }
    
    
    
    
}