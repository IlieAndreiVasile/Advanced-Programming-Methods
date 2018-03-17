package Model.Files;

import java.util.Map;
import java.util.Set;

public interface IFileTable<K, V>
{
    public void add(K key, V val);
    public void remove(K key);
    public boolean contains(K key);
    public V get(K key);
    public Iterable<K> getKeys();
    public Iterable<V> getValues();
    public Set<Map.Entry<K, V>> getAll();
}
