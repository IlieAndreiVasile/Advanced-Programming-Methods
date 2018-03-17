package Model.Files;

import Exceptions.FileException;
import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;

import java.io.IOException;

public class CloseRFile implements IStatement
{
    private IExpression exp;

    public CloseRFile(IExpression e)
    {
        exp = e;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        try
        {
            IFileTable<Integer, FileData> ft = state.getFileTable();
            int id = exp.Eval(state.getSymbolT(), state.getHeap());
            if (ft.contains(id))
            {
                ft.get(id).getReader().close();
                ft.remove(id);
            }
            else
                throw new FileException("Can't close because it does not exist!\n");
        }
        catch (IOException e)
        {
            throw new FileException("Can't close the file!\n");
        }
        return null;
    }

    @Override
    public String toString()
    {
        return "close(" + exp + ")";
    }
}
