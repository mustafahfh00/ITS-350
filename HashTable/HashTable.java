public class HashTable
{
    Entry [] hashtable;
    int items;
    Entry deleted;
    
    public HashTable(int size)
    {
        hashtable = new Entry [size];
        
    }
    public void put(int key, int val)
    {
        if(items < hashtable.lengh)
        {
            Entry element = new Entry (key, val);
            int index = hashFunction(key);
            
            while (hashtable[index] !=null && hashtable[index] != deleted)
            {
                index = hashFunction(index +1);
            }

            hashtable[index] = element;
            items++;
        }

    }
    public boolean delete(int key)
    {
        if(items > 0)
        {
            int index = hashFunction(key);

            while(hashtable[index] != null)
            {
                if (hashtable[index] != deleted && hashtable[index].key ==key)
                {
                    hashtable[index] = deleted;
                    items --;
                    return true;
                }
                index = hashFunction(index +1);
            }
           
        }
    }
    public void display()
    {
        for(i=0; i< hashtable.length; i++)
        if(hashtable[i] !=null; && hashtable[i] !=deleted)
        {
            System.out.print("Index " + i + ": Key = " + hashtable[i].key + ", Value = " + hashtable[i].val)
        }
        else
        {
            System.out.print("Index"+ i + ": Empty")
        }
    }
    public int hashFunction(int key)
    {
        return key % hashtable.length;
    }
    public static void main(String[] args)
    {
        HashTable hashtable = new HashTable(11)

        hashtalbe.put(4,2)
    }
}