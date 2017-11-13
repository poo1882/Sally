import java.util.ArrayList;

public class BookManager
{
    public static HashMap booksByName;
    public static HashMap booksByWriter;
    public static HashMap booksByGenre;

    public BookManager()
    {
        
    }

    public static BookCollection searchByName (String keyword)
    {
        hm.put("Adventure", Book1);
        BookCollection adw = hm.get("Drama");
        adw.books.add
        BookCollection matchBooks = hm.get(keyword);
    }

    public static BookCollection searchByWriter (String keyword)
    {
        
    }

    public static BookCollection searchbyGenre (String Genre)
    {

    }

    public static void checkOut()
    {
        int totalPrice = 0;
        CUSTOMER.cart.viewAllBooks();
        System.out.println("Total:  ");
        for(int i=0;i<CUSTOMER.cart.size();i++)
        {
            totalPrice = totalPrice+CUSTOMER.cart.get(i).price;
        }
        System.out.print(totalPrice);
    }

    public static void initializeBooks()
    {

    }

}