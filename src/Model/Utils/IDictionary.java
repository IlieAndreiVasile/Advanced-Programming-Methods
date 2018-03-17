package Model.Utils;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface IDictionary<K, V>
{
    public void add(K key, V value);
    public void update(K key, V value);
    public boolean contains(K key);
    public V get(K key);
    public Iterable<K> getKeys();
    public Collection<V> getValues();
    public Set<Map.Entry<K, V> > getAll();
    public IDictionary<K, V> deepCopy();
}
