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

    private static Book searchById (String keyword)
    {
        return booksById.get(keyword);
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

    private static int subMenu()
    {
        boolean isValid = false;
        int choice = 0;
        while(isValid == false)
        {
            System.out.println("1. Search for books");
            System.out.println("2. Get recommendation");
            System.out.println("3. View cart and check out");
            System.out.println("4. View buying history");
            System.out.println("5. Exit");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            choice = Integer.parseInt(option);
            if (choice >= 1 && choice <= 5)
            {
                isValid = true;
            }
        }
        return choice;
    }

    private static void viewBuyingHistory()
    {
        ArrayList<String> setOfBookId = FileManager.getInstance().getHistory(Customer.getInstance().getUsername());

    }

    private static boolean selectBook(BookCollection rangeOfBooks,String targetId)
    {
        if(rangeOfBooks.isInCollection(searchById(targetId).getBookId()) == true )
            return true;
        else
            return false;
    }


    public static void main(String[] args)
    {
        initializeBooks();
        FileManager.getInstance();

        /* Main Menu */
        if(mainMenu() == 1)
            AccountManager.getInstance().createAccount();
        else
            AccountManager.getInstance().login();

        /* Sub menu */
        int choice = subMenu();

        if (choice == 1)
            search();
        else if (choice == 2)
        {
            if (getReccMode() == 1)
            {
                System.out.println("Genre:");
                System.out.println("\tFantasy");
                System.out.println("\tAction");
                System.out.println("\tScience");
                System.out.println("\tHorror");
                System.out.println("\tDrama");
                System.out.println("\tGuide");
                System.out.println("\tBiography");
                System.out.println("\tMystery");
                System.out.println("\tHealth");
                System.out.println("\tInvestigation");
                System.out.print("Select genre: ");
                Scanner scan = new Scanner(System.in);
                String genre = scan.next();
                BookCollection rangeOfBooks = searchByGenre(genre);
                System.out.print("Enter book's id: ");
                scan = new Scanner(System.in);
                String targetId = scan.next();
                if(targetId.equals("-1") == false)
                {
                    if (selectBook(searchByGenre(genre),targetId) == true)
                        Customer.getInstance().addToCart(searchById(targetId));
                }

            }
        }
        else if (choice == 3)
        {
            checkOut(Customer.getInstance());
        }
        else if (choice == 4)
        {
            viewBuyingHistory();
        }
        else
        {
            System.out.println("Exiting program...");
        }

    }

}