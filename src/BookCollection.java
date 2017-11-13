import java.awt.print.Book;
import java.util.ArrayList;

public class BookCollection {
    private ArrayList<Book> books;
    private String genre;


    BookCollection(String genreType)
    {
        genre=genreType;
    }

    public boolean keepBook(Book newBook)
    {
        if(newBook!= null)
        {
            books.add(newBook);
            return true;
        }
        return false;
    }

    public boolean deleteBook(Book target)
    {
        int i=0;
        while(i<books.size())
        {
            if(books.get(i)==target)
            {
                books.remove(i);
                return true;
            }
        }
        return false;

    }

    public void viewAllBook()
    {
        int i=0;
        while (i<books.size())
        {
            System.out.println(books.get(i).getName);
        }
    }



}
