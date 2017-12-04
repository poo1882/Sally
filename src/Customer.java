<<<<<<< HEAD
import java.util.Scanner;
=======
import java.util.ArrayList;
>>>>>>> c8d727fa5da3a2f7139e79a6345f6c8b1454bed9

public class Customer
{
    private BookCollection cart;
    private String username = null;
    private String password = null;
    private BookCollection history;
    private static Customer customer=null;

<<<<<<< HEAD
=======
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
>>>>>>> c8d727fa5da3a2f7139e79a6345f6c8b1454bed9

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

<<<<<<< HEAD
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
=======

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
>>>>>>> c8d727fa5da3a2f7139e79a6345f6c8b1454bed9
    {
        history.viewAllBook();
    }

}
<<<<<<< HEAD
=======

>>>>>>> c8d727fa5da3a2f7139e79a6345f6c8b1454bed9
