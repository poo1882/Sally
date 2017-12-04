import java.util.ArrayList;

public class BookCollection {
    private ArrayList<Book> books = null;

    public String getBookIDByIndex(int index)
    {
        return books.get(index).getBookId();
    }

    public BookCollection() {
        books = new ArrayList<Book>();
    }

    public float calPrice()
    {
        float price=0;
        for (int i=0;i<books.size();i++)
        {
            price += books.get(i).getPrice();
        }
        return price;
    }

    public boolean keepBook(Book newBook)
    {
        if(newBook!= null)
        {
            books.add(newBook);
            return true;
        }
        else
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
            System.out.println((i+1)+". "+books.get(i).getName());
            i++;
        }
    }

    public int getLength()
    {
        return books.size();
    }

    public boolean removeBook(int index)
    {
        if(books.get(index)!=null)
        {
            books.remove(index);
            return true;
        }
        return false;
    }

    public void clearCollection()
    {
        books = null;
    }

    public boolean isInCollection(String bookId)
    {
        for(int i =0;i<books.size();i++)
        {
            if(books.get(i).getBookId().equals(bookId))
                return true;
        }
        return false;
    }



}