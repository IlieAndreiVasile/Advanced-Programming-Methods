package Model.Files;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class FileTable<K, V> implements IFileTable<K, V>
{
    private HashMap<K, V> dict;

    public FileTable()
    {
        dict = new HashMap<>();
    }

    @Override
    public void add(K key, V value)
    {
        dict.put(key, value);
    }

    @Override
    public void remove(K key)
    {
        dict.remove(key);
    }

    @Override
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return dict.get(key);
    }

    @Override
    public Iterable<K> getKeys()
    {
        return dict.keySet();
    }

    @Override
    public Iterable<V> getValues()
    {
        return dict.values();
    }

    @Override
    public Set<Map.Entry<K, V>> getAll()
    {
        return dict.entrySet();
    }

    @Override
    public String toString()
    {
        return "FileTable{" + "dict=" + dict + '}';
    }
}
