package Model.Statements;

import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Utils.IExeStack;

public class WhileStmt implements IStatement
{
    private IExpression cond;
    private IStatement stmt;

    public WhileStmt(IExpression c, IStatement s)
    {
        cond = c;
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IExeStack<IStatement> stack = state.getExeStack();
        int res = cond.Eval(state.getSymbolT(), state.getHeap());
        if (res == 0)
            return null;
        stack.push(stmt);
        return null;
    }

    @Override
    public String toString()
    {
        return "WHILE(" + cond + "): " + stmt + "";
    }
}
