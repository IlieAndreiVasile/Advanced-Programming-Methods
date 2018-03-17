package Repository;

import Exceptions.FileException;
import Model.Files.FileData;
import Model.Files.IFileTable;
import Model.Heap.IHeap;
import Model.ProgramState.PrgState;
import Model.Statements.IStatement;
import Model.Utils.IDictionary;
import Model.Utils.IExeStack;
import Model.Utils.IList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PrgStateRepo implements IPrgStateRepo
{
    private List<PrgState> myList = new ArrayList<>();
    private String fileName;

    public PrgStateRepo(String fn)
    {
        myList = new ArrayList<>();
        fileName = fn;
    }

    public void addPrgState(PrgState state)
    {
        myList.add(state);
    }

    public List<PrgState> getPrgList()
    {
        return myList;
    }

    public void setPrgList(List<PrgState> list)
    {
        myList = list;
    }

    public void logPrgStateExec(PrgState state)
    {
        try (PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true))))
        {
            IExeStack<IStatement> stack = state.getExeStack();
            IDictionary<String, Integer> symbolTable = state.getSymbolT();
            IList<Integer> out = state.getList();
            IFileTable<Integer, FileData> fileTable = state.getFileTable();
            IHeap<Integer, Integer> heap = state.getHeap();

            logFile.println("\nPrgState ID: " + state.getId());

            logFile.println("\nExeStack:");
            for (IStatement S: stack.getAll())
                logFile.println("" + S);

            logFile.println("\nSymTable:");
            for (String key: symbolTable.getKeys())
                logFile.println(key + "->" + symbolTable.get(key) + '\n');

            logFile.println("\nOut:");
            for (Integer i: out.getAll())
                logFile.println("" + i + "\n" );

            logFile.println("\nFileTable:");
            for (Integer i: fileTable.getKeys())
                logFile.println(i + "->" + fileTable.get(i).getFileName() + "\n");

            logFile.println("\nHeap:");
            for (Integer key: heap.getAll())
                logFile.println(key + "->" + heap.get(key) + "\n");

            logFile.println("\n");
        }
        catch (IOException e)
        {
            throw new FileException("IO exception at PrintWriter");
        }
    }
}
















//    @Override
//    public void logPrgStateExec(PrgState state)
//    {
//        try(PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(fileName, true)))){
//            logFile.println("PRGSTATE ID:" + state.getId());
//            logFile.println("ExeStack:");
//            IExeStack<IStatement> exeStack = state.getExeStack();
//            for(IStatement s : exeStack.getAll())
//            {
//                logFile.println(s);
//            }
//            logFile.println("SymTable:");
//            IDictionary<String, Integer> symTable = state.getSymbolT();
//            for(Map.Entry<String, Integer> entry : symTable.getAll())
//            {
//                logFile.println(entry.getKey() + "-->" + entry.getValue());
//            }
//            logFile.println("Output:");
//            IList<Integer> l = state.getList();
//            for(Integer i : l.getAll())
//            {
//                logFile.println(i);
//            }
//            logFile.println("FileTable:");
//            IFileTable<Integer, FileData> f = state.getFileTable();
//            for(Map.Entry<Integer, FileData> entry : f.getAll())
//            {
//                logFile.println(entry.getKey() + "-->" + entry.getValue().getFileName());
//            }
//            logFile.println("Heap:");
//            IHeap<Integer, Integer> h = state.getHeap();
//            for(Map.Entry<Integer, Integer> entry : h.entrySet())
//            {
//                logFile.println(entry.getKey() + "-->" + entry.getValue());
//            }
//            logFile.println("\n");
//        }
//        catch(IOException e)
//        {
//            throw new FileException("Could not open log file");
//        }
//    }