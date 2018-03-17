package Model.Utils;

public interface IExeStack<T>
{
    public void push(T el);
    public T pop();
    public boolean isEmpty();
    public String toString();
    public Iterable<T> getAll();
}
