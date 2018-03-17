package Model.Utils;

import java.util.ArrayDeque;
import java.util.Deque;

public class ExeStack<T> implements IExeStack<T>
{
    private Deque<T> stack;

    public ExeStack()
    {
        stack = new ArrayDeque<>();
    }

    @Override
    public void push(T el)
    {
        stack.push(el);
    }

    @Override
    public T pop()
    {
        return stack.pop();
    }

    @Override
    public boolean isEmpty()
    {
        return stack.isEmpty();
    }

//    @Override
//    public boolean isEmpty()
//    {
//        return stack.size() == 0;
//    }

    @Override
    public Iterable<T> getAll()
    {
        return stack;
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Stack:\n");
        for (T i : stack)
        {
            buff.append(i);
            buff.append("\n");
        }
        return buff.toString();
    }
}
