import java.util.ArrayList;

public class Book
{
    private String name;
    private ArrayList<String> genre;
    private String writer;
    private float price;

    public Book (String name, ArrayList<String> genre, String writer, float price)
    {
        this.name = name;
        this.genre = genre;
        this.writer = writer;
        this.price = price;
    }

    public void printDetail()
    {
        System.out.println("Name: "+name);
        System.out.println("Genre: ");
        for (int i=0;i<genre.size();i++)
        {
            if ( i != 0)
            {
                System.out.print(",");
            }
            System.out.print(genre.get(i));
        }
        System.out.println("Writer: "+writer);
        System.out.println("Price: "+price); 
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
}