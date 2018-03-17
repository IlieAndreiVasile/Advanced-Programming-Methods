package Model.Statements;

import Model.ProgramState.PrgState;
import Model.Utils.IExeStack;

public class CompStmt implements IStatement
{
    private IStatement first, second;

    public CompStmt(IStatement f, IStatement s)
    {
        first = f;
        second = s;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IExeStack<IStatement> stack = state.getExeStack();
        stack.push(second);
        stack.push(first);
        return null;
    }

    @Override
    public String toString()
    {
        return first + " , " + second;
    }
}
