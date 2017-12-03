import java.util.HashMap;
import java.util.Scanner;

public class AccountManager
{
    private static AccountManager instance = null;
    public static AccountManager getInstance()
    {

        if(instance==null)
        {
            instance = new FileManager();
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
            if (password.equals(FileManager.getInstance().getPassword(username).get(username)) == true)
            {
                isValid = true;
            }
        }
    }

    public void createAccount ()
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

            if(FileManager.validAccount.equals("0"))
            {
                FileManager.getInstance().addAccount(username,password);
                Customer.getInstance(username,password);
                System.out.println("Your account has been created.");
                login();
            }
        }
    }

    private boolean isNotExistingAccount (String username)
    {
        if(FileManager.getInstance().getAccount(username).get(username) != null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
}
