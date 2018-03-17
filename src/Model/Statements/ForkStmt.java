package Model.Statements;

import Model.ProgramState.PrgState;
import Model.Utils.ExeStack;
import Model.Utils.IDictionary;
import Model.Utils.IExeStack;

public class ForkStmt implements IStatement
{
    private IStatement stmt;

    public ForkStmt(IStatement s)
    {
        stmt = s;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IExeStack stack = new ExeStack<>();
        IDictionary st = state.getSymbolT().deepCopy();
        stack.push(stmt);
        PrgState fork = new PrgState(state.getId() * 10, stack, st, state.getList(), state.getFileTable(), state.getHeap());
        return fork;
    }

    @Override
    public String toString()
    {
        return "fork(" + stmt + ")";
    }
}
