package Model.Files;

import Exceptions.FileException;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;
import Model.Utils.IDictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class OpenRFile implements IStatement
{
    private String fileName;
    private String varName;

    public OpenRFile(String fn, String vn)
    {
        fileName = fn;
        varName = vn;
    }

    private boolean isOpen(PrgState prg)
    {
        for (FileData crt : prg.getFileTable().getValues())
            if (crt.getFileName().equals(fileName))
                return true;
        return false;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        if (isOpen(state))
            throw new FileException("The file is already opened in the file table!\n");
        else
            try
            {
                BufferedReader br = new BufferedReader(new FileReader(fileName));
                FileData fd = new FileData(fileName, br);
                int id = IDGenerator.generate_ID();
                state.getFileTable().add(id, fd);

                IDictionary<String, Integer> st = state.getSymbolT();
                if (st.contains(varName))
                    st.update(varName, id);
                else st.add(varName, id);
            }
            catch(IOException ex)
            {
                throw new FileException("File not found");
            }
        return null;
    }

    @Override
    public String toString()
    {
        return "open(" + varName + ", " + fileName + ')';
    }
}
