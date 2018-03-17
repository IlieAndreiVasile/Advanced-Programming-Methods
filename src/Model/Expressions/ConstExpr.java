package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utils.IDictionary;

public class ConstExpr implements IExpression
{
    private int value;

    public ConstExpr(int val)
    {
        value = val;
    }

    @Override
    public int Eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h)
    {
        return value;
    }

    @Override
    public String toString()
    {
        return "" + value;
    }
}
