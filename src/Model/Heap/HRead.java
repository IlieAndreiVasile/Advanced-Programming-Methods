package Model.Heap;

import Exceptions.HeapException;
import Exceptions.UndefinedVariableException;
import Model.Expressions.IExpression;
import Model.Utils.IDictionary;

public class HRead implements IExpression
{
    private String var;

    public HRead(String v)
    {
        var = v;
    }

    @Override
    public int Eval(IDictionary<String, Integer> st, IHeap<Integer, Integer> heap)
    {
        if(! st.contains(var))
            throw new UndefinedVariableException("Variable is not defined in symbol table!\n");
        int val = st.get(var);
        if(! heap.contains(val))
            throw new HeapException("This heap location is not allocated!\n");
        return heap.get(val);
    }

    @Override
    public String toString()
    {
        return "rH(" + var + ")";
    }
}
