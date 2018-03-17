package Model.Statements;

import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;

public class PrintStmt implements IStatement
{
    private IExpression expr;

    public PrintStmt(IExpression ex)
    {
        expr = ex;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        int r = expr.Eval(state.getSymbolT(), state.getHeap());
        state.getList().add(r);
        return null;
    }

    @Override
    public String toString()
    {
        return "print(" + expr + ");";
    }
}
