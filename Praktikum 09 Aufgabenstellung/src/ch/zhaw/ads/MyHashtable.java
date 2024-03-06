package ch.zhaw.ads;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

@SuppressWarnings("unchecked")
public class MyHashtable<K, V> implements Map<K, V> {
    private K[] keys;
    private V[] values;
    private int size = 0;
    private int mapSize;

    private int hash(Object k) {
        int h = Math.abs(k.hashCode());
        return h % keys.length;
    }

    public MyHashtable(int size) {
        mapSize = size;
        keys = (K[]) new Object[size];
        values = (V[]) new Object[size];
    }

    // Removes all mappings from this map (optional operation).
    public void clear() {
        size = 0;
        keys = (K[])new Object[mapSize];
        values = (V[]) new Object[mapSize];
    }

    // Associates the specified value with the specified key in this map (optional operation).
    public V put(K key, V value) {
        if(get(key) != null){
            return null;
        }
        int hash = hash(key);
        if(size >= keys.length){
            resizeMap();
        }
        if(keys[hash] == null) {
            keys[hash] = key;
            values[hash] = value;
            size++;
            return value;
        } else {
            if(keys[hash] == key && values[hash] == value) {
                return null;
            }
            int count = hash;
            do{
                count++;
                if(count > mapSize - 1){
                    count = 0;
                }
                if(count == hash){
                    return null;
                }
            } while(keys[count] != null);
            keys[count] = key;
            values[count] = value;
            size++;
            return value;
        }
    }

    private void resizeMap() {
        K[] keysTemp = keys.clone();
        V[] valuesTemp = values.clone();
        keys = (K[])new Object[mapSize * 2];
        values = (V[]) new Object[mapSize * 2];
        size = 0;
        mapSize = mapSize * 2;
        for(int i = 0; i < keysTemp.length; i++){
            put(keysTemp[i], valuesTemp[i]);
        }
    }

    // Returns the value to which this map maps the specified key.
    public V get(Object key) {
        int hash = hash(key);
        if(keys[hash] == key) {
            return values[hash];
        } else {
            int start = hash;
            int count = hash;
            do{
                count++;
                if(count > mapSize - 1){
                    count = 0;
                }
                if(count == start){
                    return null;
                }
            } while(keys[count] != key);
            return values[count];
            //return null;
        }
    }

    // Removes the mapping for this key from this map if present (optional operation).
    public V remove(Object key) {
        int hash = hash(key);
        V valueToRemove;
        if(keys[hash] == key) {
            valueToRemove = values[hash];
            values[hash] = null;
            keys[hash] = null;
            size--;
            return valueToRemove;
        } else {
            int start = hash;
            int count = hash;
            do{
                count++;
                if(count > mapSize - 1){
                    count = 0;
                }
                if(count == start){
                    return null;
                }
            } while(keys[count] != key);
            valueToRemove = values[count];
            values[count] = null;
            keys[count] = null;
            size--;
            return valueToRemove;
        }
    }

    // Returns the number of key-value mappings in this map.
    public int size() {
        return size;
    }

    // UnsupportedOperationException ===================================================================
    // Returns a collection view of the values contained in this map.
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains a mapping for the specified key.
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map maps one or more keys to the specified value.
    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the mappings contained in this map.
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    // Compares the specified object with this map for equality.
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    // Copies all of the mappings from the specified map to this map (optional operation).
    public void putAll(Map<? extends K, ? extends V> t) {
        throw new UnsupportedOperationException();
    }

    // Returns the hash code value for this map.
    public int hashCode() {
        throw new UnsupportedOperationException();
    }

    // Returns true if this map contains no key-value mappings.
    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    // Returns a set view of the keys contained in this map.
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }
}
