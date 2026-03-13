package ItemList;
import java.util.*;

public class Book extends Item
{
    public Book(String physicalId, String isbnId, String title, List<String> author, String isRegistered)
    {
        this.physicalId = physicalId;
        this.isbnId = isbnId;
        this.title = title;
        this.author = author;
        this.isRegistered = isRegistered;
    }

    public Book( String title,List<String> author)
    {

        this.title = title;
        this.author= author;

    }
    public Book(String isbnId, String title,List<String> author)
    {
        this.isbnId = isbnId;
        this.title = title;
        this.author= author;

    }
    @Override
    public String getPhysicalId()
    {
        return physicalId;
    }

    @Override
    public String getIsbnId()
    {
        return isbnId;
    }

    @Override
    public String getTitle()
    {
        return title;
    }

    @Override
    public List<String> getAuthor() {
        return author;
    }

    @Override
    public String getIsRegistered()
    {
        return isRegistered;
    }
}