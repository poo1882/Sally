import java.util.Scanner;

public class Customer
{
    private BookCollection cart;
    private String username = null;
    private String password = null;
    private BookCollection history;
    private static Customer customer=null;


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
        if (username == null)
            this.username = username;
        else
            System.out.println("Error, username has already defined.");
    }

    private void setPassword(String password)
    {
        if (password == null)
            this.password = password;
        else
            System.out.println("Error, password has already defined.");
    }

    public String getUsername()
    {
        return username;
    }

    public void createAccount()
    {
        boolean creatingSuccess = false;
        while (creatingSuccess == false)
        {
            System.out.print("Enter username: ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
            if (FileManager.getInstance().findPassword(username).equals("0") == true)
            {
                String password;
                boolean passwordValid = false;
                while (passwordValid == false)
                {
                    System.out.print("Enter password: ");
                    scan = new Scanner(System.in);
                    password = scan.next();
                    if(password.length() >= 5)
                    {
                        passwordValid = true;
                    }
                }
                Customer.getInstance(username,password);
            }
            else
                System.out.println("This username is already used.");
        }
    }

    public void viewBuyingHistory()
    {
        //FileManager.getInstance().getHistory(username);
    }

}
