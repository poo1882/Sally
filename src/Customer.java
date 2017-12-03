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

<<<<<<< HEAD
    public void viewBuyingHistory()
    {
        //FileManager.getInstance().getHistory(username);
    }

}
=======
}

>>>>>>> 851cecb37abb929d652679bb557ad12df5a0eeee
