import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A class to represents customer and contains significant data
 */
public class Customer
{
    private BookCollection cart;
    private String username ;
    private String password ;
    private BookCollection history;
    /** instance of customer class (singleton)*/
    private static Customer customer;


    /**
     * Function that check if customer already purchased an input book
     * @param bookID bookID to check
     * @return true if customer haven't bought the book
     * @return false if bookID is found in customer  history
     */
    public boolean checkBoughtBooks(String bookID)
    {
        int i=0;
        while(i<history.getLength())
        {
            if(history.getBookIDByIndex(i).equals(bookID))
            {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * getter function of customer history
     * @return purchased BookCollection
     */
    public BookCollection getHistory() {
        return history;
    }

    /**
     * private constructor
     */
    private Customer()
    {
        cart = new BookCollection();
    }

    /**
     * A class that create and/or return instance of customer
     * @return instance of Customer class
     */
    public static Customer getInstance()
    {
        if(customer==null) {
            customer = new Customer();
            return customer;
        }
        return customer;
    }

    /**
     * A function that remove specific item in cart
     * @param index item
     * @return true on success
     * @return false if input index is not found
     */
    public boolean removeFromCart(int index)
    {
        cart.removeBook(index);
        return true;
    }

    /**
     * function that clears customer's cart
     */
    public void clearCart()
    {
        cart.clearCollection();
    }

    /**
     * A function that add a book to customer's cart
     * @param target
     */
    public void addToCart(Book target)
    {
        if(history != null)
        {
            boolean hasNotBought = history.isInCollection(target.getBookId());
            if (!hasNotBought)
            {
                if(!cart.isInCollection(target.getBookId()))
                {
                    cart.keepBook(target);
                    System.out.println("Adding success.");
                }
                else
                    System.out.println("Error: this book is already in your cart");

            }
            else
            {
                System.out.println("Error: you have bought this book already");
            }

        }
        else
        {
            if(!cart.isInCollection(target.getBookId()))
            {
                cart.keepBook(target);
                System.out.println("Adding success.");
            }
            else
                System.out.println("Error: this book is already in your cart");
        }

    }

    /**
     * A getter function of customer's cart
     * @return customer's cart
     */
    public BookCollection getCart()
    {
        return cart;
    }

    /**
     * setter function to set customer's username
     * @param username
     */
    private void setUsername(String username)
    {
        this.username = username;
        history = new BookCollection();
    }

    /**
     * setter function to set customer's password
     * @param password
     */
    private void setPassword(String password)
    {
        this.password = password;
    }

    /**
     * getter function of customer's username
     * @return customer's username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * create an account menu for customer
     * @return 1 on success -1 on failure
     * @throws IOException
     */
    public int createAccount() throws IOException
    {
        while (true)
        {
            while (true)
            {
                String username;
                while (true)
                {
                    boolean validUsername = false;
                    System.out.print("Enter username(-1 to return): ");
                    Scanner scan = new Scanner(System.in);
                    username = scan.next().toLowerCase();
                    if (username.equals("-1"))
                        return -1;
                    String invalidCharacters = "!@#$%^&*()_+-={}:[];\"'<,>.?/";
                    for (int i=0;i<invalidCharacters.length();i++)
                    {
                       if(username.contains(String.valueOf(invalidCharacters.charAt(i))))
                           break;
                       else
                       {
                           if(i == invalidCharacters.length()-1)
                               validUsername = true;
                       }
                    }
                    if(validUsername)
                        break;
                    else
                        System.out.println("Error: username cannot contain invalid characters.");
                }


                if (FileManager.getInstance().findPassword(username).equals("0"))
                {
                    while (true)
                    {
                        System.out.print("Enter password(-1 to return): ");
                        Scanner scan = new Scanner(System.in);
                        String password = scan.next();

                        if (password.equals("-1"))
                            return -1;
                        if (password.length() >= 5)
                        {
                            Customer.getInstance().setUsername(username);
                            Customer.getInstance().setPassword(password);
                            System.out.println("Welcome! Your account has been created.");
                            FileManager.getInstance().writeIDfile(username,password);
                            return 1;
                        }
                        else
                            System.out.println("Error: password requires at least 5 characters.");
                    }
                }
                else
                {
                    System.out.println("Error: this username is already used.");
                }

            }
        }
    }

    /**
     * login menu for main program
     * @return -1 on failure 1 on login success
     */
    public int login()
    {
        while (true)
        {
            System.out.print("Enter username(-1 to return): ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next().toLowerCase();
            if (username.equals("-1"))
                return -1;
            else if (!FileManager.getInstance().findPassword(username).equals("0"))
            {
                while (true)
                {
                    System.out.print("Enter password(-1 to return): ");
                    scan = new Scanner(System.in);
                    String password = scan.next();
                    if (password.equals("-1"))
                        return -1;
                    if (FileManager.getInstance().findPassword(username).equals(password))
                    {
                        Customer.getInstance().setUsername(username);
                        Customer.getInstance().setPassword(password);
                        Customer.getInstance().createBuyingHistory();
                        FileManager.getInstance();
                        System.out.println("Login success!");
                        return 1;
                    }

                    else
                        System.out.println("Error: incorrect password");
                }

            }
            else
            {
                System.out.println(FileManager.getInstance().findPassword(username));
                System.out.println("Error: incorrect username");
            }


        }
    }

    /**
     * Set buying history in customer class
     */
    public void createBuyingHistory()
    {
        ArrayList<String> readHis = FileManager.getInstance().getCurCusHis(username);
        if(readHis==null)
        {
            System.out.println("check");
            history.clearCollection();
        }
        else
        {
            history.clearCollection();
            int i=0;
            while(i<readHis.size())
            {
                history.keepBook(BookManager.searchById(readHis.get(i)));
                i++;
            }
        }
    }

    /**
     * Shows buying history
     */
    public void printBuyingHis()
    {
        createBuyingHistory();
        if (history.getLength() > 0)
        {
            System.out.println("You have bought: ");
            history.viewAllBook();
        }
        else
            System.out.println("Your history is empty.");

    }

    /**
     * clear variable in customer class when logout
     */
    public void logout()
    {
        this.username = null;
        this.password =null;
        this.cart = null;
        this.history = null;
    }

    /**
     * view customer's cart and checkout
     * @return 1 on successful checkout
     * @return -1 to go previous menu
     * @return 0 on clear cart
     * @throws IOException
     */
    public int viewCart() throws IOException
    {

        if((cart != null) && (cart.getLength() > 0))
        {
            System.out.println("Your cart has:");
            cart.viewAllBook();
            System.out.println("Total price: "+cart.calPrice()+"$\n");
            System.out.println("1. Confirm order");
            System.out.println("2. Continue shopping");
            System.out.println("3. Clear cart");
            Scanner scan = new Scanner(System.in);
            String option = scan.next();
            while (true)
            {
                if(option.equals("1"))
                {
                    ArrayList<String> boughtBooks = new ArrayList<>();
                    for(int i =0;i<cart.getLength();i++)
                    {
                        boughtBooks.add(cart.getBookByIndex(i).getBookId());
                    }
                    FileManager.getInstance().writeBuyingHistory(boughtBooks);
                    //FileManager.getInstance().writeBuyingHistory(boughtBooks);
                    System.out.println("Purchasing success, THANK YOU <3 !");
                    return 1;
                }
                else if (option.equals("2"))
                    return -1;
                else if (option.equals("3"))
                {
                    cart.clearCollection();
                    System.out.println("Your cart has been cleared.");
                    return 0;
                }




            }
        }
        else
        {
            System.out.println("Your cart is empty.");
            return -1;
        }

    }

}
