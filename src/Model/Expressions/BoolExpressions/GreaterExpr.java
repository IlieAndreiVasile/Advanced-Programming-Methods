package Model.Expressions.BoolExpressions;

import Model.Expressions.IExpression;
import Model.Heap.IHeap;
import Model.Utils.IDictionary;

public class GreaterExpr implements IExpression
{
    private IExpression left, right;

    public GreaterExpr(IExpression l, IExpression r)
    {
        left = l;
        right = r;
    }

    @Override
    public int Eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h)
    {
        if (left.Eval(d, h) > right.Eval(d, h))
            return 1;
        return 0;
    }

    @Override
    public String toString()
    {
        return "(" + left + ">" + right + ")";
    }
}
