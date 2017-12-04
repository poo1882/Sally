import java.util.ArrayList;

public class Book
{
    private String name;
    private ArrayList<String> genre;
    private String writer;
    private float price;
    private  String bookId;
    public Book (String name, ArrayList<String> genre, String writer, float price,String bookId)
    {
        this.name =  name;
        this.genre = genre;
        this.writer = writer;
        this.price = price;
        this.bookId = bookId;
    }

    public Book()
    {

    }

    public void printDetail()
    {
        System.out.println("Name: "+name);
        System.out.print("Genre: ");
        for (int i=0;i<genre.size();i++)
        {
            if ( i != 0)
            {
                System.out.print(",");
            }
            System.out.print(genre.get(i));
        }
        System.out.println("\nWriter: "+writer);
        System.out.println("Price: "+price);
        System.out.println("BookId: "+bookId);
    }

    public String getName()
    {
        return name;
    }

    public ArrayList<String> getGenre()
    {
        return genre;
    }

    public float getPrice()
    {
        return price;
    }

    public String getWriter()
    {
        return writer;
    }

    public String getBookId()

    {
        return bookId;
    }
}
