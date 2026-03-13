package AuthorList;

import java.util.ArrayList;

public interface AuthorDataDao {
    public int addAuthor(String name) ;
    public  ArrayList<Integer> search_author(String name) ;
    public  String search_author(int num);
    public void verifyFileIntegrity(int n);
}