import java.util.ArrayList;

public class Customer
{
    private BookCollection cart;
    private String username;
    private String password;
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

    public static Customer getInstance(String username, String password)
    {
        Customer thisCustomer = new Customer(username,password);
        return customer;
    }

    public static Customer getInstance()
    {
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

    public String getUsername() {
        return username;
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

