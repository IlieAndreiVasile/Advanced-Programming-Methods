package Exceptions;

public class InexistentSymbolException extends RuntimeException
{
    public InexistentSymbolException(String message)
    {
        super(message);
    }
}
