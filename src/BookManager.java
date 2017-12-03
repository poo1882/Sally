import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BookManager
{
    private static HashMap<Character, ArrayList<Book>> booksByName = new HashMap<>();
    private static HashMap<Character, ArrayList<Book>> booksByWriter = new HashMap<>();
    private static HashMap<String, ArrayList<Book>> booksByGenre = new HashMap<>();
    private static HashMap<String, Book> booksById = new HashMap<>();

    public BookManager()
    {

    }

    public static void search(String keyword, int mode)
    {
        if(mode == 1 || mode == 0)
        {
            if(searchByName(keyword) != null)
            {
                searchByName(keyword).viewAllBook();
                System.out.println("size: "+searchByName(keyword).getLength());
            }
            else
            {
                System.out.println("No matched books in name");
            }
        }

        else if (mode == 2 || mode == 0)
        {
            if(searchByWriter(keyword) != null)
            {
                searchByWriter(keyword).viewAllBook();
            }
            else
            {
                System.out.println("No matched books in writer");
            }
        }
        else if (mode == 3 || mode == 0)
        {
            if(searchByGenre(keyword) != null)
            {
                searchByGenre(keyword).viewAllBook();
            }
            else
            {
                System.out.println("No matched books in genre");
            }
        }
    }

    private static BookCollection searchByName (String keyword)
    {
        BookCollection matchedBooks = new BookCollection();
        String keywords[] = keyword.split(" ");
        System.out.println("poo");

        if (booksByName.get(keywords[0].toLowerCase().charAt(0))!=null)
        {
            for (int i = 0;i<booksByName.get(keywords[0].toLowerCase().charAt(0)).size();i++)
            {

                int found = 0;
                int j;
                for (j=0;j<keywords.length;j++)
                {
                    //System.out.println("name: "+booksByName.get(keywords[0].toLowerCase().charAt(0)).get(i).getName());
                    //System.out.println("key:"+ keywords[j]);
                    if (booksByName.get(keywords[0].toLowerCase().charAt(0)).get(i).getName().toLowerCase().contains(keywords[j]))
                    {
                        found++;
                    }
                }
                //System.out.println("found = "+found);
                //System.out.println("j = "+j);
                if (found == j)
                {

                    boolean reval = matchedBooks.keepBook(booksByName.get(keywords[0].toLowerCase().charAt(0)).get(i));
                }
            }
            if (matchedBooks.getLength() == 0)
            {
                return null;
            }
            return matchedBooks;
        }
        else
        {
            return null;
        }
    }

    private static BookCollection searchByWriter (String keyword)
    {
        BookCollection matchedBooks = new BookCollection();
        String keywords[] = keyword.split(" ");

        if (booksByWriter.get(keywords[0].charAt(0))!=null)
        {
            for (int i = 0;i<booksByWriter.get(keywords[0].charAt(0)).size(); i++)
            {
                int found = 0;
                int j;
                for (j=0;j<keywords.length;j++)
                {
                    if (booksByWriter.get(keywords[0]).get(i).getWriter().toLowerCase().contains(keywords[j].toLowerCase())==true)
                    {
                        found++;
                    }
                }
                if (found == j)
                {
                    boolean reval = matchedBooks.keepBook(booksByWriter.get(keywords[0].charAt(0)).get(i));
                }
            }
            return matchedBooks;
        }
        else
        {
            matchedBooks = null;
            return matchedBooks;
        }

    }

    private static BookCollection searchByGenre (String keyword)
    {
        BookCollection matchedBooks =  new BookCollection();
        if (booksByGenre.get(keyword)!=null)
        {
            for (int i=0;i<booksByGenre.get(keyword).size();i++)
            {
                boolean reval = matchedBooks.keepBook(booksByGenre.get(keyword).get(i));
            }
            return matchedBooks;
        }

        else
        {
            matchedBooks = null;
            return matchedBooks;
        }

    }

    public static boolean checkOut(Customer newCustomer)
    {
        newCustomer.getCart().viewAllBook();
        System.out.println("Total price: "+newCustomer.getCart().calPrice());
        System.out.println("Confirm cart? (Y/N)");
        Scanner scanner = new Scanner(System.in);
        String confirm = scanner.nextLine();
        if(confirm.equals("Y"))
            return true;
        else
            return false;
    }

    public static void initializeBooks()
    {
        FileManager manager = FileManager.getInstance();
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
        while (i<fields.length)
        {
            if (booksByName.get(fields[i].toLowerCase().charAt(0)) == null)
            {
                booksByName.put(fields[i].toLowerCase().charAt(0),new ArrayList<>());
                booksByName.get(fields[i].toLowerCase().charAt(0)).add(newBook);
            }
            else
            {
                if(newBook.equals(booksByName.get(fields[i].toLowerCase().charAt(0)).get(booksByName.get(fields[i].toLowerCase().charAt(0)).size()-1)) == false)
                {
                    booksByName.get(fields[i].toLowerCase().charAt(0)).add(newBook);
                }
            }
            i++;
        }
    }

    private static void insertByWriter(Book newBook)
    {
        int i = 0;
        String writer = newBook.getWriter();
        String fields[] = writer.split(" ");

        while (i<fields.length)
        {
            if (booksByWriter.get(fields[i].toLowerCase().charAt(0)) == null)
            {
                booksByWriter.put(fields[i].toLowerCase().charAt(0),new ArrayList<>());
                booksByWriter.get(fields[i].toLowerCase().charAt(0)).add(newBook);
            }
            else
            {
                if(newBook.equals(booksByWriter.get(fields[i].toLowerCase().charAt(0)).get(booksByWriter.get(fields[i].toLowerCase().charAt(0)).size()-1)) == false)
                {
                    booksByWriter.get(fields[i].toLowerCase().charAt(0)).add(newBook);
                }
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

    private static int mainMenu()
    {
        boolean isValid = false;
        int choice = 0;
        while(isValid == false)
        {
            System.out.println("1. Create account");
            System.out.println("2. Log in");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            choice = Integer.parseInt(option);
            if (choice == 1 || choice == 2)
            {
                isValid = true;
            }
        }
        return choice;
    }

    public static void main(String[] args)
    {
        initializeBooks();

        int choice = mainMenu();

        if(choice == 1)
        {

            AccountManager.getInstance().createAccount();
            AccountManager.getInstance().login();
        }

        else
        {

        }

        Customer thisCustomer = new Customer("Bobby","1234");
        ArrayList<String> genres = new ArrayList<String>(Arrays.asList("Horror","Drama","Comedy"));
        Book book1 = new Book("Operating System",genres,"Stephen",220.5f,"000");
        insertByWriter(book1);
        insertByName(book1);
        insertByGenre(book1);
        search("america".toLowerCase(),0);
    }

}