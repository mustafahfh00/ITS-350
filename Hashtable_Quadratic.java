public class Hashtable_Quadratic {

    Entry[] hashtable;
    int items;
    Entry deleted;

    public Hashtable_Quadratic(int size) {
        hashtable = new Entry[size];
        deleted = new Entry(-1, 0); // Tombstone entry
    }

    public void put(int key, int val) {
        if (items < hashtable.length) {
            Entry element = new Entry(key, val);
            int hashedKey = hashFunction(key);
            int indexQuad = hashedKey;
            int step = 1;

            // Linear probing to find an empty or deleted slot
            while (hashtable[indexQuad] != null && hashtable[indexQuad] != deleted) {
                indexQuad = hashedKey + quadratic(step);
                indexQuad =hashFunction(indexQuad);
                step++;
            }

            hashtable[indexQuad] = element;
            items++;
        }

    }
    public int quadratic(int step)
    {
        return step*step;
    }

    public boolean delete(int key) {
        if (items > 0) {
            int hashedKey = hashFunction(key);
            int indexQuad = hashedKey;
            int step = 1;
    
            // Quadratic probing to find the matching key
            while (hashtable[indexQuad] != null) {
                if (hashtable[indexQuad] != deleted && hashtable[indexQuad].key == key) {
                    hashtable[indexQuad] = deleted;
                    items--;
                    return true;
                }
    
                indexQuad = hashedKey + quadratic(step);
                indexQuad = hashFunction(indexQuad); // wrap around
                step++;
            }
        }
        return false;
    } 
    public void display() {
        for (int i = 0; i < hashtable.length; i++) {
            if (hashtable[i] != null && hashtable[i] != deleted) {
                System.out.println("Index " + i + ": Key = " + hashtable[i].key + ", Value = " + hashtable[i].val);
            } else {
                System.out.println("Index " + i + ": Empty");
            }
        }
    }

    public int hashFunction(int key) {
        return key % hashtable.length;
    }

    public static void main(String[] args) {
    HashTable hashtable = new HashTable(11);
    hashtable.put(2, 12);
    hashtable.put(13, 0);
    hashtable.put(24, 5);
    
}

}
// double hashing (probing) is homework

// separate chaining is homework