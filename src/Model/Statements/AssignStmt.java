package Model.Statements;

import Model.Expressions.IExpression;
import Model.ProgramState.PrgState;
import Model.Utils.IDictionary;

public class AssignStmt implements IStatement
{
    private String varName;
    private IExpression expr;

    public AssignStmt(String VarName, IExpression Expr)
    {
        varName = VarName;
        expr = Expr;
    }

    @Override
    public PrgState execute(PrgState state)
    {
        IDictionary<String, Integer> st = state.getSymbolT();
        int intr = expr.Eval(st, state.getHeap());
        if (st.contains(varName))
            st.update(varName, intr);
        else
            st.add(varName, intr);
        return null;
    }

    @Override
    public String toString()
    {
        return varName + " = " + expr + "; ";
    }
}
