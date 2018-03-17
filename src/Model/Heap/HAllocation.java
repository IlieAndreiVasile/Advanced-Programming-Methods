package Model.Heap;

import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;
import Model.Utils.IDictionary;

public class HAllocation implements IStatement
{
    private String var;
    private IExpression expr;

    public HAllocation(String v, IExpression exp)
    {
        var = v;
        expr = exp;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IDictionary<String, Integer> st = state.getSymbolT();
        IHeap<Integer, Integer> heap = state.getHeap();
        int id = HIDGenerator.getID();
        heap.add(id, expr.Eval(st, heap));
        if (st.contains(var))
            st.update(var, id);
        else
            st.add(var, id);
        return null;
    }

    @Override
    public String toString()
    {
        return "newH(" + var + ", " + expr + ")";
    }

}
