package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utils.IDictionary;

public interface IExpression
{
    public int Eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h);
}