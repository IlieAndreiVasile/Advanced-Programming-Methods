package Model.Files;

import java.io.BufferedReader;

public class FileData
{
    private String fileName;
    private BufferedReader reader;

    public FileData(String s, BufferedReader b)
    {
        fileName = s;
        reader = b;
    }

    public String getFileName()
    {
    return fileName;
    }

    public BufferedReader getReader()
    {
        return reader;
    }

    public void setFileName(String n)
    {
        this.fileName = n;
    }

    public void setReader(BufferedReader b)
    {
        this.reader = b;
    }

    @Override
    public String toString()
    {
        return fileName;
    }
}
