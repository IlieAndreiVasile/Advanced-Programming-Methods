package Model.Utils;

import java.util.ArrayList;
import java.util.List;

public class outputList<T> implements IList<T>
{
    private List<T> l;

    public outputList()
    {
        l = new ArrayList<>();
    }

    @Override
    public void add(T x)
    {
        l.add(x);
    }

    @Override
    public Iterable<T> getAll()
    {
        return l;
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("output list:\n");
        for(T i: l)
            buff.append(i + " ");

        return buff.toString();
    }
}
