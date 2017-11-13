import com.sun.xml.internal.ws.api.ha.StickyFeature;

public class Customer {
    private String accountName;
    private BookCollection cart;
    private String username;
    private String password;
    private BookCollection history;

    public Customer(String accountName, String username,String password)
    {
        this.accountName=accountName;
        this.username=username;
        this.password=password;
        cart = new BookCollection("cart");
    }

    public boolean removeFromCart(int index)
    {
        cart.removeBook(index);
    }

    public void clearCart()
    {
        cart.clearCollection();
    }

    public void addToCart(Book target)
    {
        boolean reValue= cart.keepBook(target);
        if(reValue==true)
            System.out.println("added");
        else
            System.out.println("error");
    }
}
