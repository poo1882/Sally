public class Customer {
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
        boolean reValue= cart.keepBook(target);
        if(reValue==true)
            System.out.println("added");
        else
            System.out.println("error");
    }
    public BookCollection getCart()
    {
        return cart;
    }

    public String getUsername() {
        return username;
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 8c5391b9752c3feee99739e2ba97abaf2635ef38
