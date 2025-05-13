public class HashTable {

    Entry[] hashtable;
    int items;
    Entry deleted;

    public HashTable(int size) {
        hashtable = new Entry[size];
        deleted = new Entry(-1, 0); // Tombstone entry
    }

    public void put(int key, int val) {
        if (items < hashtable.length) {
            Entry element = new Entry(key, val);
            int hashedKey = hashFunction(key);

            // Linear probing to find an empty or deleted slot
            while (hashtable[hashedKey] != null && hashtable[hashedKey] != deleted) {
                hashedKey = hashFunction(hashedKey + 1);
            }

            hashtable[hashedKey] = element;
            items++;
        }
    }

    public boolean delete(int key) {
        if (items > 0) {
            int hashedKey = hashFunction(key);

            // Linear probing to find the matching key
            while (hashtable[hashedKey] != null) {
                if (hashtable[hashedKey] != deleted && hashtable[hashedKey].key == key) {
                    hashtable[hashedKey] = deleted;
                    items--;
                    return true;
                }
                hashedKey = hashFunction(hashedKey + 1);
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
    hashtable.display();

    System.out.println("Deleting key 13: " + hashtable.delete(13));
    hashtable.display();
}

}



