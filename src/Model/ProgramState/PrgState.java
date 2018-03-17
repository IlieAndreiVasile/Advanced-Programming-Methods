package Model.ProgramState;

import Exceptions.EmptyStackException;
import Model.Files.FileData;
import Model.Files.IFileTable;
import Model.Heap.IHeap;
import Model.Statements.IStatement;
import Model.Utils.*;


public class PrgState
{
    private static int idStatic = 0;
    private int id;
    private IExeStack<IStatement> execStack;
    private IDictionary<String, Integer> symbolT;
    private IList<Integer> list;
    private IStatement rootP;
    private IFileTable<Integer, FileData> fileTable;
    private IHeap<Integer, Integer> heap;

    public PrgState(int i, IExeStack<IStatement> s, IDictionary<String, Integer> d, IList<Integer> l, IFileTable<Integer, FileData> ft, IHeap<Integer, Integer> h)
    {
        id = i;
        execStack = s;
        symbolT = d;
        list = l;
        //rootP = st;
        fileTable = ft;
        heap = h;
    }

    public PrgState(IExeStack<IStatement> s, IDictionary<String, Integer> d, IList<Integer> l, IStatement st, IFileTable<Integer, FileData> ft, IHeap<Integer, Integer> h)
    {
        id = idStatic++;
        execStack = s;
        execStack.push(st);
        symbolT = d;
        list = l;
        rootP = st;
        fileTable = ft;
        heap = h;
    }

    public int getId()
    {
        return id;
    }

    public IExeStack<IStatement> getExeStack()
    {
        return execStack;
    }

    public IDictionary<String, Integer> getSymbolT()
    {
        return symbolT;
    }

    public IList<Integer> getList()
    {
        return list;
    }

    public IStatement getStatement()
    {
        return rootP;
    }

    public IFileTable<Integer, FileData> getFileTable()
    {
        return fileTable;
    }

    public IHeap<Integer, Integer> getHeap()
    {
        return heap;
    }

    public void setExeStack(IExeStack<IStatement> es)
    {
        execStack = es;
    }

    public void setSymbolT(IDictionary<String, Integer> s)
    {
        symbolT = s;
    }

    public void setMessages(IList<Integer> l)
    {
        list = l;
    }

    public void setRootP(IStatement s)
    {
        rootP = s;
    }

    public void setFileTable(IFileTable<Integer, FileData> ft)
    {
        fileTable = ft;
    }

    public void setHeap(IHeap<Integer, Integer> h)
    {
        heap = h;
    }

    public boolean isNotCompleted()
    {
        return !execStack.isEmpty();
    }

    public PrgState executeOneStep()
    {
        if (execStack.isEmpty())
            throw new EmptyStackException("The Stack is empty!\n");
        IStatement crtStmt = execStack.pop();
        return crtStmt.execute(this);
    }

    @Override
    public String toString()
    {
        StringBuffer buff = new StringBuffer();
        buff.append("Program State:\n");
        buff.append(id);
        buff.append(execStack);
        buff.append(symbolT);
        buff.append(list);
        buff.append(rootP);
        buff.append(fileTable);
        buff.append(heap);
        buff.append("\n");
        return buff.toString();
    }
}



//    @Override
//    public String toString() {
//        StringBuffer buff = new StringBuffer();
//
//        buff.append("\nExeStack:\n");
//        for (IStatement S: execStack.getAll())
//            buff.append("" +  S + '\n');
//
//        buff.append("\nSymTable:\n");
//        for (String key: symbolT.getAll())
//            buff.append(key + "->" + symbolT.get(key) + '\n');
//
//        buff.append("\nOut:\n");
//        for (Integer i: list.getAll())
//            buff.append("" + i + "\n" );
//
//
//        buff.append("\nFileTable:\n");
//        for (Integer i: fileTable.getAll())
//            buff.append(i + "->" + fileTable.get(i).getFileName() + "\n");
//
//        buff.append("\nHeap:\n");
//        for (Integer key: heap.getAll())
//            buff.append(key + "->" + heap.get(key) + "\n");
//
//        buff.append("\n");
//
//        return buff.toString();
//    }
//}