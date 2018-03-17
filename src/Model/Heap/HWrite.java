package Model.Heap;

import Exceptions.HeapException;
import Exceptions.UndefinedVariableException;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;
import Model.Utils.IDictionary;

public class HWrite implements IStatement
{
    private String name;
    private IExpression expr;

    public HWrite(String n, IExpression e)
    {
        name = n;
        expr = e;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IDictionary<String, Integer> st = state.getSymbolT();
        IHeap<Integer, Integer> heap = state.getHeap();
        if(! st.contains(name))
            throw new UndefinedVariableException("Variable is not defined in symbol table!\n");
        if(! heap.contains(st.get(name)))
            throw new HeapException("This heap location is not allocated!\n");
        int r = expr.Eval(st, heap);
        heap.update(st.get(name), r);
        return null;
    }

    @Override
    public String toString()
    {
        return "wH(" + name + ", " + expr + ")";
    }
}
