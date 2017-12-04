import java.util.ArrayList;
import java.util.HashMap;

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
            System.out.println("\t"+(i+1)+") "+books.get(i).getName());
            i++;
        }
        System.out.println();
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
        books.clear();
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

    public Book getBookByIndex(int index)
    {
        if(index<books.size())
        {
            return books.get(index);
        }
        return null;
    }


    public String getPreferredCat()
    {
        HashMap<String, Integer> booksTag = new HashMap<>();
        ArrayList<String> tags = new ArrayList<>();
        String highestHit = null;
        int hitCount=0;
        int i=0;
        while(i<books.size())           //create map of cat and count
        {
            int j=0;
            while (j<books.get(i).getGenre().size())
            {
                String genre = books.get(i).getGenre().get(j);
                tags.add(genre);
                if(booksTag.get(genre)==null)
                {
                    booksTag.put(genre,1);
                }
                else
                {
                    booksTag.put(genre,booksTag.get(genre)+1);
                }
                j++;
            }

            i++;
        }
        i=0;

        while (i<tags.size())           //compare
        {
            if(booksTag.get(tags.get(i))>hitCount)
            {
                hitCount=booksTag.get(tags.get(i));
                highestHit = tags.get(i);
            }
            i++;
        }

        return highestHit;
    }


}
