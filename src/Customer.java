import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Customer
{
    private BookCollection cart;
    private String username ;
    private String password ;
    private BookCollection history;
    private static Customer customer;



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

    public BookCollection getHistory() {
        return history;
    }

    private Customer()
    {
        history = new BookCollection();
        cart = new BookCollection();
    }


    public static Customer getInstance()
    {
        if(customer==null) {
            customer = new Customer();
            return customer;
        }
        return customer;
    }

    public boolean removeFromCart(int index)
    {
        cart.removeBook(index);
        return true;
    }

    public void clearCart()
    {
        cart.clearCollection();
    }

    public void addToCart(Book target)
    {
        if(history != null)
        {
            boolean hasNotBought = history.isInCollection(target.getBookId());
            System.out.println("hasNotBought"+hasNotBought);
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
    public BookCollection getCart()
    {
        return cart;
    }

    private void setUsername(String username)
    {
        if (this.username == null)
            this.username = username;
        else
            System.out.println("Error, username has already defined.");
    }

    private void setPassword(String password)
    {
        if (this.password == null)
            this.password = password;
        else
            System.out.println("Error, password has already defined.");
    }

    public String getUsername()
    {
        return username;
    }

    /* Create an account for a customer
        return 1 if creating success, -1 if get back
     */
    public int createAccount() throws IOException
    {
        while (true)
        {
            while (true)
            {
                System.out.print("Enter username(-1 to return): ");
                Scanner scan = new Scanner(System.in);
                String username = scan.next();
                if (username.equals("-1"))
                    return -1;
                if (FileManager.getInstance().findPassword(username).equals("0"))
                {
                    while (true)
                    {
                        System.out.print("Enter password(-1 to return): ");
                        scan = new Scanner(System.in);
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
                    System.out.println("Pass: "+FileManager.getInstance().findPassword(username));
                }

            }
        }
    }

    public int login()
    {
        while (true)
        {
            System.out.print("Enter username(-1 to return): ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
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
                System.out.print("Password: ");
                System.out.println(FileManager.getInstance().findPassword(username));
                System.out.println("Error: incorrect username");
            }


        }
    }

    public void createBuyingHistory()
    {
        ArrayList<String> readHis = FileManager.getInstance().getCurCusHis(username);
        history.clearCollection();
        int i=0;

        if (readHis != null)
        {
            while(i<readHis.size())
            {
                history.keepBook(BookManager.searchById(readHis.get(i)));
                i++;
            }
        }
        else
            history = null;
    }

    public void printBuyingHis()
    {
        createBuyingHistory();
        if (history != null)
        {
            System.out.println("You have bought: ");
            history.viewAllBook();
        }
        else
            System.out.println("Your history is empty.");

    }

    public void logout()
    {
        this.username = null;
        this.password =null;
        this.cart = null;
        this.history = null;
    }

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
