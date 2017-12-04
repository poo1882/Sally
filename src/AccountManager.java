import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

public class AccountManager
{
    private static AccountManager instance = null;

    private AccountManager()
    {

    }

    public static AccountManager getInstance()
    {

        if(instance==null)
        {
            instance = new AccountManager();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    public void login ()
    {
        boolean isValid = false;

        while (isValid == false)
        {
            System.out.print("Enter username: ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
            System.out.print("Enter password: ");
            scan = new Scanner(System.in);
            String password = scan.next();
            System.out.println(password);
            if (password.equals(FileManager.getInstance().findPassword(username)) == true)
            {


                break;
                //isValid = true;
            }


        }
    }

    public void createAccount () throws IOException
    {
        boolean checkNotExistingAccount = false;

        while (checkNotExistingAccount == false)
        {
            System.out.print("Enter username: ");
            Scanner scan = new Scanner(System.in);
            String username = scan.next();
            System.out.print("Enter password: ");
            scan = new Scanner(System.in);
            String password = scan.next();

            if(FileManager.getInstance().findPassword(username).equals("0"))
            {
                FileManager.getInstance().writeIDfile(username,password);
                Customer.getInstance(username,password);
                System.out.println("Your account has been created.");
                FileManager.getInstance().createIDMap();
                login();
                break;
            }
        }
    }

    private boolean isNotExistingAccount (String username)
    {
        if(FileManager.getInstance().findPassword(username) == "0")
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
