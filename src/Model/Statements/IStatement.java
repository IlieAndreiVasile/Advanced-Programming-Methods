package Model.Statements;

import Model.ProgramState.PrgState;

public interface IStatement
{
    public PrgState execute(PrgState state);
}
