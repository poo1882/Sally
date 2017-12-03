import java.util.HashMap;
import java.util.Scanner;

public class AccountManager
{
    private static AccountManager instance;
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

    public boolean login ()
    {
        System.out.print("Enter username: ");
        Scanner scan = new Scanner(System.in);
        String username = scan.next();
        System.out.print("Enter password: ");
        scan = new Scanner(System.in);
        String password = scan.next();

        if (password.equals(FileManager.getInstance().getAccount(username).get(username)) == true)
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean createAccount ()
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
            checkNotExistingAccount = isNotExistingAccount(username);
            if(checkNotExistingAccount == true)
            {
                FileManager.getInstance().addAccount(username,password);
                Customer.getInstance(username,password);
            }
        }
        return checkNotExistingAccount;
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
