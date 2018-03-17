package Model.Utils;

import Exceptions.UndefinedVariableException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Dictionary<K, V> implements IDictionary<K, V>
{
    private Map<K, V> dict;

    public Dictionary()
    {
        dict = new HashMap<>();
    }

//    public Dictionary()
//    {
//        dict = new HashMap<K, V>();
//    }

    public Dictionary(Map<K, V> d)
    {
        dict = d;
    }

    @Override
    public void add(K key, V value)
    {
        dict.put(key, value);
    }

    @Override
    public void update(K key, V value)
    {
        dict.put(key, value);
    }

    @Override
    public boolean contains(K key)
    {
        return dict.containsKey(key);
    }

    @Override
    public V get(K key)
    {
        if (! contains(key))
            throw new UndefinedVariableException("Variable is not defined in symbol table!\n");
        return dict.get(key);
    }

    @Override
    public Iterable<K> getKeys()
    {
        return dict.keySet();
    }

    @Override
    public Collection<V> getValues()
    {
        return dict.values();
    }

    @Override
    public Set<Map.Entry<K, V>> getAll()
    {
        return dict.entrySet();
    }

    @Override
    public IDictionary<K, V> deepCopy()
    {
        return new Dictionary<>(new HashMap<>(dict));
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Dictionary: \n");
        for (Map.Entry<K, V> d : dict.entrySet())
        {
            buff.append("<");
            buff.append(d.getKey());
            buff.append(",");
            buff.append(d.getValue());
            buff.append(">;\n");
        }
        return buff.toString();
    }
}
