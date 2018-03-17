package Model.Statements;

import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Utils.IExeStack;

public class IfStmt implements IStatement
{
    private IExpression cond;
    private IStatement thenS, elseS;

    public IfStmt(IExpression ex, IStatement th, IStatement el)
    {
        cond = ex;
        thenS = th;
        elseS = el;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IExeStack<IStatement> stack = state.getExeStack();
        int res = cond.Eval(state.getSymbolT(), state.getHeap());
        if (res == 0)
            stack.push(elseS);
        else
            stack.push(thenS);
        return null;
    }

    @Override
    public String toString()
    {
        return "IF(" + cond + ") THEN (" + thenS + "); ELSE(" + elseS + ");";
    }
}
