package View;

import Controller.Controller;
import Model.Expressions.*;
import Model.Expressions.BoolExpressions.*;
import Model.Files.*;
import Model.Heap.*;
import Model.ProgramState.PrgState;
import Model.Statements.*;
import Model.Utils.*;
import Repository.IPrgStateRepo;
import Repository.PrgStateRepo;


public class Interpreter
{
    public static void main(String[] args)
    {
        //a = (250*4);  , b = (a/25);  , print(b);
        IStatement ex14 = new AssignStmt("a",new ArithmeticExpr('*', new ConstExpr(250), new ConstExpr(4)));
        IStatement ex13 = new AssignStmt("b", new ArithmeticExpr('/', new VarExpr("a"), new ConstExpr(25)));
        IStatement ex12 = new CompStmt(ex14, ex13);
        IStatement ex11 = new PrintStmt(new VarExpr("b"));
        IStatement ex10 = new CompStmt(ex12, ex11);


        //a = (2-2);  , IF(a) THEN (v = 2; ); ELSE(v = 3; ); , print(v);
        IStatement ex24 = new AssignStmt("a", new ArithmeticExpr('-', new ConstExpr(2), new ConstExpr(2)));
        IStatement ex23 = new IfStmt(new VarExpr("a"), new AssignStmt("v", new ConstExpr(2)), new AssignStmt("v", new ConstExpr(3)));
        IStatement ex22 = new CompStmt(ex24, ex23);
        IStatement ex21 = new PrintStmt(new VarExpr("v"));
        IStatement ex20 = new CompStmt(ex22, ex21);


        //open(a, a.txt) , read(a, b) , print(b); , close(a)
        IStatement ex30 = new CompStmt(
                                        new CompStmt(
                                                    new OpenRFile("a.txt", "a"),
                                                    new ReadFile(new VarExpr("a"), "b")
                                                    ),
                                        new CompStmt(
                                                    new PrintStmt(new VarExpr("b")),
                                                    new CloseRFile(new VarExpr("a"))
                                                    )
                                        );


        //open(var_f, test.in) , read(var_f, var_c) , print(var_c); , IF(var_c) THEN (read(var_f, var_c) , print(var_c);); ELSE(print(0);); , close(var_f)
        IStatement ex40 = new CompStmt(
                                        new CompStmt(
                                                    new OpenRFile("test.in", "var_f"),
                                                    new CompStmt(
                                                                new ReadFile(new VarExpr("var_f"), "var_c"),
                                                                new PrintStmt(new VarExpr("var_c"))
                                                                )
                                                    ),
                                        new CompStmt(
                                                    new IfStmt(
                                                                new VarExpr("var_c"),
                                                                new CompStmt(
                                                                            new ReadFile(new VarExpr("var_f"), "var_c"),
                                                                            new PrintStmt(new VarExpr("var_c"))
                                                                            ),

                                                                new PrintStmt(new ConstExpr(0))
                                                                ),
                                                    new CloseRFile(new VarExpr("var_f"))
                                                    )
                                    );


        //open(var_f, test.in) , read((var_f+2), var_c) , print(var_c); , IF(var_c) THEN (read(var_f, var_c) , print(var_c);); ELSE(print(0);); , close(var_f)
        //error
        IStatement ex50 = new CompStmt(
                                        new CompStmt(
                                                    new OpenRFile("test.in", "var_f"),
                                                    new CompStmt(
                                                                new ReadFile(
                                                                            new ArithmeticExpr('+',
                                                                                                new VarExpr("var_f"),
                                                                                                new ConstExpr(2)
                                                                                               ),
                                                                            "var_c"
                                                                            ),
                                                                new PrintStmt(new VarExpr("var_c"))
                                                                )
                                                    ),
                                        new CompStmt(
                                                    new IfStmt(
                                                                new VarExpr("var_c"),
                                                                new CompStmt(
                                                                        new ReadFile(new VarExpr("var_f"), "var_c"),
                                                                        new PrintStmt(new VarExpr("var_c"))
                                                                            ),
                                                                new PrintStmt(new ConstExpr(0))
                                                              ),
                                                    new CloseRFile(new VarExpr("var_f"))
                                                    )
                                    );


        //v = 10;  , newH(v, 20) , newH(a, 22) , wH(a, 30) , print(a); , print(rH(a)); , a = 0;
        IStatement e69 = new AssignStmt("v", new ConstExpr(10));
        IStatement e68 = new HAllocation("v", new ConstExpr(20));
        IStatement e67 = new HAllocation("a", new ConstExpr(22));
        IStatement e66 = new HWrite("a", new ConstExpr(30));
        IStatement e65 = new PrintStmt(new VarExpr("a"));
        IStatement e64 = new PrintStmt(new HRead("a"));
        IStatement e63 = new AssignStmt("a", new ConstExpr(0));
        IStatement e62 = new CompStmt(e69, e68);
        IStatement e61 = new CompStmt(e67, e66);
        IStatement e60 = new CompStmt(e65, e64);
        IStatement ex60 = new CompStmt(new CompStmt(e62, e61), new CompStmt(e60, e63));


        //x = (10+(2<6));
        IStatement ex70 = new AssignStmt("x", new ArithmeticExpr('+', new ConstExpr(10), new LessExpr(new ConstExpr(2), new ConstExpr(6))));


        //v = 6;  , WHILE((v-4)): print(v); , v = (v-1);  , print(v);
        IStatement ex83 = new AssignStmt("v", new ConstExpr(6));
        IStatement ex82 = new WhileStmt(new ArithmeticExpr('-', new VarExpr("v"), new ConstExpr(4)),
                                        new CompStmt(
                                                    new PrintStmt(new VarExpr("v")),
                                                    new AssignStmt("v", new ArithmeticExpr('-', new VarExpr("v"), new ConstExpr(1)))
                                                    )
                                        );
        IStatement ex81 = new PrintStmt(new VarExpr("v"));
        IStatement ex80 = new CompStmt(new CompStmt(ex83, ex82), ex81);


        //v=10;new(a,22); fork(wH(a,30);v=32;print(v);print(rH(a)));   print(v);print(rH(a))
        IStatement ex90 = new CompStmt(new AssignStmt("v",new ConstExpr(10)),
                new CompStmt(new HAllocation("a",new ConstExpr(22)),
                        new CompStmt(new ForkStmt(new CompStmt(new HWrite("a",new ConstExpr(30)),
                                new CompStmt(new AssignStmt("v",new ConstExpr(32)),
                                        new CompStmt(new PrintStmt(new VarExpr("v")),
                                                new PrintStmt(new HRead("a")))))),
                                new CompStmt(new PrintStmt(new VarExpr("v")),
                                        new PrintStmt(new HRead("a"))))));


        IExeStack<IStatement> stack = new ExeStack<>();
        IDictionary<String, Integer> st = new Dictionary<>();
        IList<Integer> l = new outputList<>();
        IFileTable<Integer, FileData> ft = new FileTable<>();
        IHeap<Integer, Integer> heap = new Heap<>();

        stack.push(ex90);
        PrgState prg = new PrgState(1, stack, st, l, ft, heap);

        IPrgStateRepo repo = new PrgStateRepo("text.txt");
        Controller ctrl = new Controller(repo);
        repo.addPrgState(prg);

        TextMenu menu = new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex90.toString(), ctrl));
        menu.show();
    }
}