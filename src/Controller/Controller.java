package Controller;

import Model.ProgramState.*;
import Repository.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;


public class Controller
{
    private IPrgStateRepo ipsr;
    private ExecutorService executor;

    public Controller(IPrgStateRepo ip)
    {
        ipsr = ip;
    }

//    public Map<Integer, Integer> conservativeGarbageCollector(Collection<Integer> symTableValues, IHeap<Integer, Integer> heap)
//    {
//        return heap.entrySet().stream()
//                .filter(e -> symTableValues.contains(e.getKey()))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
//    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList)
    {
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList)
    {
        prgList.forEach(prg->ipsr.logPrgStateExec(prg));

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>)(() -> {return p.executeOneStep();}))
                .collect(Collectors.toList());

        try
        {
            List<PrgState> newPrgList = executor.invokeAll(callList).stream()
                    .map(future ->
                    {
                        try
                        {
                            return future.get();
                        }
                        catch (Exception ex1)
                        {
                            System.out.println(ex1);
                        }
                        return null;
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());

            prgList.addAll(newPrgList);

            prgList.forEach(prg -> ipsr.logPrgStateExec(prg));

            ipsr.setPrgList(prgList);
        }
        catch (Exception ex2)
        {
            System.out.println(ex2);
        }
    }

    public void AllSteps()
    {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> prgList = removeCompletedPrg(ipsr.getPrgList());

        while (prgList.size() > 0)
        {
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(ipsr.getPrgList());
        }

        executor.shutdownNow();

        ipsr.setPrgList(prgList);
    }
}
