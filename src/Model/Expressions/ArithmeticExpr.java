package Model.Expressions;

import Exceptions.DivisionByZeroException;
import Exceptions.InexistentSymbolException;
import Model.Heap.IHeap;
import Model.Utils.IDictionary;


public class ArithmeticExpr implements IExpression
{
    private char operator;
    private IExpression left;
    private IExpression right;

    public ArithmeticExpr(char op, IExpression l, IExpression r)
    {
        operator = op;
        left = l;
        right = r;
    }

    @Override
    public int Eval(IDictionary<String, Integer> d, IHeap<Integer, Integer> h)
    {
            int l = left.Eval(d, h);
            int r = right.Eval(d, h);
            if (operator == '+')
                return l + r;
            if (operator == '-')
                return l - r;
            if (operator == '*')
                return l * r;
            if (operator == '/')
                if (r == 0)
                    throw new DivisionByZeroException("You can't divide by 0!\n");
                else
                    return l / r;
            throw new InexistentSymbolException("Symbol does not exist! Available symbols: + - * /\n");
    }

    @Override
    public String toString()
    {
        return "(" + left + operator + right + ")";
    }
}
