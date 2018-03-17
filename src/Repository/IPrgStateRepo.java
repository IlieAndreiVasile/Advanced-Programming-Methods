package Repository;

import Model.ProgramState.PrgState;

import java.util.List;

public interface IPrgStateRepo
{
    public void addPrgState(PrgState state);
    public List<PrgState> getPrgList();
    public void setPrgList(List<PrgState> list);
    public void logPrgStateExec(PrgState state);
}
