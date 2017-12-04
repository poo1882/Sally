import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

public class Customer
{
    private BookCollection cart;
    private String username = null;
    private String password = null;
    private BookCollection history;
    private static Customer customer=null;

    public Customer(String username,String password)
    {
        this.username=username;
        this.password=password;
        cart = new BookCollection();
        history = new BookCollection();
    }

    public BookCollection getHistory() {
        return history;
    }

    private Customer()
    {

    }


    public static Customer getInstance()
    {
        if(customer==null)
            return new Customer();
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

    /*
    public int createAccount()
    {
        boolean creatingSuccess = false;
        while (creatingSuccess == false)
        {
            System.out.print("Enter username: ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
            if (FileManager.getInstance().findPassword(username).equals("0") == true)
            {
                String enteredPassword = null;
                boolean passwordValid = false;
                while (passwordValid == false)
                {
                    System.out.print("Enter password: ");
                    scan = new Scanner(System.in);
                    enteredPassword = scan.next();
                    if(enteredPassword.length() >= 5)
                    {
                        passwordValid = true;
                    }
                }
                Customer.getInstance(username,enteredPassword);
            }
            else
                System.out.println("This username is already used.");
        }
    }*/

    public void viewBuyingHistory()
    {

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

        int i=0;
        while(i<readHis.size())
        {
            history.keepBook(BookManager.searchById(readHis.get(i)));
            i++;
        }

    }

    public void printBuyingHis()
    {
        history.viewAllBook();
    }

}
