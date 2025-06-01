public class Hashtable_DoubleHashing {

    Entry[] hashtable;
    int items;
    Entry deleted;

    public Hashtable_DoubleHashing(int size) {
        hashtable = new Entry[size];
        deleted = new Entry(-1, 0); // Tombstone
    }

    public void put(int key, int val) {
        if (items < hashtable.length) {
            Entry element = new Entry(key, val);
            int hashedKey = hashFunction(key);
            int step = secondHash(key);
            int index = hashedKey;

            while (hashtable[index] != null && hashtable[index] != deleted) {
                index = (index + step) % hashtable.length;
            }

            hashtable[index] = element;
            items++;
        }
    }

    public boolean delete(int key) {
        if (items > 0) {
            int hashedKey = hashFunction(key);
            int step = secondHash(key);
            int index = hashedKey;

            while (hashtable[index] != null) {
                if (hashtable[index] != deleted && hashtable[index].key == key) {
                    hashtable[index] = deleted;
                    items--;
                    return true;
                }
                index = (index + step) % hashtable.length;
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

    public int secondHash(int key) {
        return 7 - (key % 7); // You can change 7 to another prime less than table size
    }

    public static void main(String[] args) {
        Hashtable_DoubleHashing ht = new Hashtable_DoubleHashing(11);
        ht.put(10, 100);
        ht.put(21, 200);
        ht.put(32, 300);
        ht.display();

        System.out.println("Deleting key 21: " + ht.delete(21));
        ht.display();
    }
}
