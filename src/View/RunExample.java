package View;

import Controller.*;
import Exceptions.*;

public class RunExample extends Command
{
    private Controller ctrl;

    public RunExample(String key, String description, Controller c)
    {
        super(key, description);
        this.ctrl = c;
    }

    @Override
    public void execute()
    {
        //ctrl.executeAll();
        try
        {
            ctrl.AllSteps();
        }
//        catch (RuntimeException e)
//        {
//            System.out.println("Runtime error!");
//        }
        catch (EmptyStackException es)
        {
            System.out.println(es);
        }
        catch(DivisionByZeroException div)
        {
            System.out.println(div);
        }
        catch(InexistentSymbolException is)
        {
            System.out.println(is);
        }
        catch(UndefinedVariableException uv)
        {
            System.out.println(uv);
        }
        catch(FileException f)
        {
            System.out.println(f);
        }
        catch(HeapException h)
        {
            System.out.println(h);
        }
    }
}
