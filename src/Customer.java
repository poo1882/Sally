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



    public BookCollection getHistory() {
        return history;
    }

    private Customer()
    {
        history = new BookCollection();
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
        boolean hasNotBought = history.isInCollection(target.getBookId());
        if (hasNotBought == true)
        {
            cart.keepBook(target);
            System.out.println("Adding success.");
        }
        else
            System.out.println("Adding failed");
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
                        Customer.getInstance().createBuyingHistory();
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
        System.out.println(readHis);
        System.out.println(BookManager.searchById(readHis.get(0)).getName());
        int i=0;

        if (readHis != null)
        {
            while(i<readHis.size())
            {
                history.keepBook(BookManager.searchById(readHis.get(i)));
                i++;
            }
        }
    }

    public void printBuyingHis()
    {
        System.out.println("You have bought: ");
        history.viewAllBook();
    }

    public void logout()
    {
        this.username = null;
        this.password =null;
        this.cart = null;
        this.history = null;
    }

}
