import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class BookManager
{
    private static HashMap<Character, ArrayList<Book>> booksByName = new HashMap<>();
    private static HashMap<Character, ArrayList<Book>> booksByWriter = new HashMap<>();
    private static HashMap<String, ArrayList<Book>> booksByGenre = new HashMap<>();

    public BookManager()
    {

    }

    public static void search(String keyword, int mode)
    {
        if(mode == 1 || mode == 0)
            searchByName(keyword).viewAllBook();
        else if (mode == 2 || mode == 0)
            searchByWriter(keyword).viewAllBook();
        else if (mode == 3 || mode == 0)
            searchByGenre(keyword).viewAllBook();
    }

    private static BookCollection searchByName (String keyword)
    {
        BookCollection matchedBooks = new BookCollection();
        String keywords[] = keyword.split(" ");

        for (int i = 0;i<booksByName.get(keywords[0]).size(); i++)
        {
            int found = -1;
            int j;
            for (j=0;keywords[j]!= null;j++)
            {
                Book thisBook = new Book() ;
                thisBook.getName();
                if (booksByName.get(keywords[0]).get(i).getName().toLowerCase().contains(keywords[j].toLowerCase()))
                {
                    found++;
                }
            }
            if (found == j)
            {
                boolean reval = matchedBooks.keepBook(booksByName.get(keywords[0]).get(i));
            }
        }
        return matchedBooks;
    }

    private static BookCollection searchByWriter (String keyword)
    {
        BookCollection matchedBooks = new BookCollection();
        String keywords[] = keyword.split(" ");

        for (int i = 0;i<booksByWriter.get(keywords[0]).size(); i++)
        {
            int found = -1;
            int j;
            for (j=0;keywords[j]!= null;j++)
            {
                Book thisBook = new Book() ;
                thisBook.getName();
                if (booksByWriter.get(keywords[0]).get(i).getWriter().toLowerCase().contains(keywords[j].toLowerCase()))
                {
                    found++;
                }
            }
            if (found == j)
            {
                boolean reval = matchedBooks.keepBook(booksByWriter.get(keywords[0]).get(i));
            }
        }
        return matchedBooks;
    }

    private static BookCollection searchByGenre (String genre)
    {
        BookCollection matchedBooks =  new BookCollection();
        for (int i=0;i<booksByGenre.get(genre).size();i++)
        {
            boolean reval = matchedBooks.keepBook(booksByGenre.get(genre).get(i));
        }
        return matchedBooks;
    }

    public static boolean checkOut(Customer newCustomer)
    {
        newCustomer.getCart().viewAllBook();
        System.out.println("Total price: "+newCustomer.getCart().calPrice());
        System.out.println("Confirm cart? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String confirm = scanner.nextLine();
        if(confirm == "Y")
            return true;
        else
            return false;
    }

    public static void initializeBooks()
    {
        FileManager manager = new FileManager();
        Book newBook = manager.readBookFile();
        while (newBook != null)
        {
            insertByName(newBook);
            insertByWriter(newBook);
            insertByGenre(newBook);
            newBook = manager.readBookFile();
        }

    }

    private static void insertByName(Book newBook)
    {
        int i = 0;
        String name = newBook.getName();
        String fields[] = name.split(" ");
        //ArrayList<String> names = new ArrayList<>();
        while (fields[i] != null)
        {
            if (booksByName.get(fields[i].charAt(0)) == null)
            {
                booksByName.put(fields[i].charAt(0),new ArrayList<>());
                booksByName.get(fields[i].charAt(0)).add(newBook);
            }
            else
            {
                booksByName.get(fields[i].charAt(0)).add(newBook);
            }
            i++;
        }
    }

    private static void insertByWriter(Book newBook)
    {
        int i = 0;
        String writer = newBook.getWriter();
        String fields[] = writer.split(" ");

        while (fields[i] != null)
        {
            if (booksByWriter.get(fields[i].charAt(0)) == null)
            {
                booksByWriter.put(fields[i].charAt(0),new ArrayList<>());
                booksByWriter.get(fields[i].charAt(0)).add(newBook);
            }
            else
            {
                booksByWriter.get(fields[i].charAt(0)).add(newBook);
            }
            i++;
        }
    }

    private static void insertByGenre(Book newBook)
    {
        int i = 0;
        ArrayList<String> genres = newBook.getGenre();


        for (i=0;i<genres.size();i++)
        {
            if (booksByGenre.get(genres.get(i)) == null)
            {
                booksByGenre.put(genres.get(i),new ArrayList<>());
                booksByGenre.get(genres.get(i)).add(newBook);
            }
            else
            {
                booksByGenre.get(genres.get(i)).add(newBook);
            }

        }
    }

    public static void main(String[] args)
    {
        Customer thisCustomer = new Customer("Bobby","1234");
        ArrayList<String> genres = "aaa",
        Book book1 = new Book("Operating System",ArrayList<String> genres = )
        search("Operating System",0);

    }

}