import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class BookManager
{
    private static BookManager bookManager ;

    private static HashMap<Character, ArrayList<Book>> booksByName ;
    private static HashMap<Character, ArrayList<Book>> booksByWriter ;
    private static HashMap<String, ArrayList<Book>> booksByGenre ;
    private static HashMap<String, Book> booksById ;

    private BookManager()
    {

    }

    public static BookManager getInstance()
    {
        if(bookManager==null)
        {
            bookManager= new BookManager();
            return bookManager;
        }
        return bookManager;
    }



    private static int getSearchMode ()
    {
        boolean isValid = false;
        int mode = 0;
        while(isValid == false)
        {
            System.out.println("1. Search from book's name");
            System.out.println("2. Search from writer's name");
            System.out.println("Enter \"0\" to search from all above...");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            mode = Integer.parseInt(option);
            if (mode >= 0 && mode <= 2)
            {
                isValid = true;
            }
        }
        return mode;
    }

    private static int getReccMode()
    {
        boolean isValid = false;
        int mode = 0;
        while(isValid == false)
        {
            System.out.println("1. Get recommendation based on content");
            System.out.println("2. Get recommendation based on community");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            mode = Integer.parseInt(option);
            if (mode == 1 || mode == 2)
            {
                isValid = true;
            }
        }
        return mode;
    }

    public static void search()
    {
        int mode = getSearchMode();
        System.out.print("Enter keyword:");
        Scanner scan = new Scanner(System.in);
        String keyword = scan.next();

        if(mode == 1 || mode == 0)
        {
            if(searchByName(keyword) != null)
            {
                System.out.println("search1");
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

    public static Book searchById (String keyword)
    {

        return booksById.get(keyword);
    }

    public static boolean checkOut()
    {
        Customer.getInstance().getCart().viewAllBook();
        System.out.println("Total price: "+Customer.getInstance().getCart().calPrice());
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
        booksByGenre= new HashMap<>();
        booksByWriter= new HashMap<>();
        booksById= new HashMap<>();
        booksByName= new HashMap<>();
        FileManager manager = FileManager.getInstance();
        Book newBook = manager.readBookFile();

        while (newBook != null)
        {
            insertByName(newBook);
            insertByWriter(newBook);
            insertByGenre(newBook);
            insertById(newBook);
            newBook = manager.readBookFile();
        }

    }

    private static void insertById (Book newBook)
    {
        booksById.put(newBook.getBookId(),newBook);
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
                if(!newBook.equals(booksByWriter.get(fields[i].toLowerCase().charAt(0)).get(booksByWriter.get(fields[i].toLowerCase().charAt(0)).size() - 1)))
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
        while(true)
        {
            while (true)
            {
                System.out.println("1. Create account");
                System.out.println("2. Log in");
                System.out.println("3. Exit");
                Scanner scan = new Scanner(System.in);
                String option = scan.next();
                if (option.equals("3"))
                    return -1;
                else if (option.equals("1"))
                    return 1;
                else if (option.equals("2"))
                    return 2;
                else
                    System.out.println("Error: please select 1-3");
            }
        }
    }

    public BookCollection getRecByCom()
    {
        BookCollection reBooks = new BookCollection();
        ArrayList<String> readBooksString=new ArrayList<>();
        ArrayList<ArrayList<String>> recString = new ArrayList<>();
        Customer customer = Customer.getInstance();
        BookEngine engine = BookEngine.getInstance();
        int i=0;
        while(i<customer.getHistory().getLength())
        {
            readBooksString.add(customer.getHistory().getBookIDByIndex(i));
            i++;
        }
        i=0;
        while (i<readBooksString.size())
        {
            recString.add(engine.searchHash(readBooksString.get(i)));
            i++;
        }
        i=0;

        while (i<0)
        {

            String bookId=engine.filterHashResult(recString.get(i));
            reBooks.keepBook(booksById.get(bookId));
            i++;
        }
        return reBooks;
    }

    public void pickCartMenu(BookCollection collection)
    {
        collection.viewAllBook();
        while(true)
        {
            System.out.println("Pick to cart (0 to go back)");
            Scanner scan = new Scanner(System.in);
            String index = scan.next();
            if(Integer.parseInt(index)==0)
                break;
            else if(Integer.parseInt(index)<collection.getLength())
                Customer.getInstance().addToCart(collection.getBookByIndex(Integer.parseInt(index)));
            else
                System.out.println("Error input");
        }
    }

    private static int subMenu()
    {

        while(true)
        {
            System.out.println("1. Search for books");
            System.out.println("2. Get recommendation");
            System.out.println("3. View cart and check out");
            System.out.println("4. View buying history");
            System.out.println("5. Log out");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            if (option.equals("1"))
                getSearchMode();
            else if(option.equals("2"))
                getReccMode();
            else if(option.equals("3"))
                checkOut();
            else if(option.equals("4"))
            {
                Customer.getInstance().printBuyingHis();
            }

            else
            {
                Customer.getInstance().logout();
                return -1;
            }
        }
    }


    public static void main(String[] args) throws IOException
    {
        initializeBooks();
        FileManager.getInstance();

        int mainMenu = 0;

        while (true)
        {
            mainMenu = mainMenu();
            if (mainMenu == -1)
            {
                System.out.println("Exiting program...");
                System.exit(0);
            }

            else if ( mainMenu == 1)
            {
                Customer.getInstance().createAccount();
            }
            else
            {
                FileManager.getInstance().createIDMap();
                if(Customer.getInstance().login() == 1)
                {
                    while (true)
                    {
                        int subMenu = subMenu();
                        if (subMenu == -1)
                            break;
                    }
                }
            }
        }
    }
}
