package Model.Files;

import Exceptions.FileException;
import Model.Expressions.IExpression;
import Model.Heap.IHeap;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;
import Model.Utils.IDictionary;

import java.io.IOException;

public class ReadFile implements IStatement
{
    private IExpression exp;
    private String varName;

    public ReadFile(IExpression e, String vn)
    {
        exp = e;
        varName = vn;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IDictionary<String, Integer> st = state.getSymbolT();
        IHeap<Integer, Integer> heap = state.getHeap();
        IFileTable<Integer, FileData> ft = state.getFileTable();
        if (! ft.contains(exp.Eval(st, heap)))
            throw new FileException("This ID is not in the file table!\n");
        else
            try
            {
                String line = ft.get(exp.Eval(st, heap)).getReader().readLine();
                if (line == null)
                {
                    st.add(varName, 0);
                    return null;
                }
                st.add(varName, Integer.parseInt(line));
                return null;
            }
            catch (IOException e)
            {
                throw new FileException("Can't read line!\n");
            }
    }

    @Override
    public String toString()
    {
        return "read(" + exp + ", " + varName + ")";
    }
}
