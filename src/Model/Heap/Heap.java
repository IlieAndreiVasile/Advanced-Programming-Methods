package Model.Heap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Heap<K, V> implements IHeap<K, V>
{
    private Map<K, V> heap;

    public Heap()
    {
        heap = new HashMap<K, V>();
    }

    @Override
    public void add(K key, V value)
    {
        heap.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        heap.put(key, value);
    }

    @Override
    public boolean contains(K key)
    {
        return heap.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        return heap.get(key);
    }

    @Override
    public Iterable<K> getAll()
    {
        return heap.keySet();
    }

    @Override
    public void setContent(Map<K, V> m)
    {
        heap = m;
    }

    public Set<Map.Entry<K, V>> entrySet()
    {
        return heap.entrySet();
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Heap: \n");
        for (Map.Entry<K, V> e : heap.entrySet())
        {
            buff.append("<");
            buff.append(e.getKey());
            buff.append(", ");
            buff.append(e.getValue());
            buff.append(">;\n");
        }
        return buff.toString();
    }
}
