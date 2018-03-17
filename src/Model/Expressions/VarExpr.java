package Model.Expressions;

import Model.Heap.IHeap;
import Model.Utils.IDictionary;

public class VarExpr implements IExpression
{
    private String x;

    public VarExpr(String _x)
    {
        x = _x;
    }

    @Override
    public int Eval(IDictionary<String, Integer> st, IHeap<Integer, Integer> h)
    {
        //if (st.contains(x))
        return st.get(x);
        //throw new UndefinedVariableException("Variable is not defined in symbol table!\n");
    }

    @Override
    public String toString()
    {
        return x;
    }
}
