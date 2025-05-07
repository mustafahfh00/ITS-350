public class HashTable
{
    Entry hashtable[];
    int items;

    public HashTable(int size)
    {
    hashtable = new Entry[size];
    }

    public void put(int key, int val)
    {
        if(items<hashtable.length)
        Entry element = new Entry(key, val);
        int hashedKey = hashFunction(key); //hashedKey = index
        while(hashtable[hashedKey] !=null )
        {
            //Linear propping
            hashedKey++;
            hashedKey = hashFunction(hashedKey);
        }
        hashtable[hashedKey] = element;
        items++;
        
    }
    public boolean delete(int key)
    {
        if(items>0)
        {
            int hashedKey = hashFunction(key);
            while(hashtable[hashedKey]!=null)
            {
                if(hashtable[hashedKey].key ==key)
                {
                    hashtable[hashedKey]= null;
                    items--;
                    return true;
                }
                else
                {
                    hashedKey++;
                    hashedKey = hashFunction(hashedKey);
                }
            }
        } return false;
    }
    public void display()
    {
        for(int i =0; i<=hashtable.length; i++)
        {

        }
    }
    public int hashFunction(int key)
    {
        return key%hashtable.length;
    }
    public static void main(String args[])
    {
        Hashtable hashtable = new HashTable(11);
        hashtable.put(2,12);
        hashtable.put(13,0);
    }
}