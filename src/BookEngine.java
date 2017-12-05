import java.util.ArrayList;
import java.util.HashMap;

public class BookEngine {

    private static HashMap<String, ArrayList<ArrayList<String>>> booksByCom;



    private FileManager fileManager=null;
    private static BookEngine instance;
    private BookEngine()
    {
        createHistoryHash(Customer.getInstance());
    }



    public static BookEngine getInstance()
    {
        if(instance==null)
        {
            instance = new BookEngine();
            return instance;
        }
        else
        {
            return instance;
        }
    }


    private void createHistoryHash(Customer customer)
    {
        booksByCom =   new HashMap<>();
        ArrayList<String> readHis = new ArrayList<String>();
        fileManager=FileManager.getInstance();
        readHis = fileManager.readHistory();
        /*
        int i=0;
        while (i<readHis.size())
        {
            System.out.println(readHis.get(i));
            i++;
        }
        */
        int i=0;

        while(readHis!=null)
        {

            while (i<readHis.size())
            {
                if(booksByCom.get(readHis.get(i))==null)
                {
                    booksByCom.put(readHis.get(i),new ArrayList<>());
                    booksByCom.get(readHis.get(i)).add(readHis);
                }
                else
                {
                    booksByCom.get(readHis.get(i)).add(readHis);
                }



                i++;
            }

            readHis = fileManager.readHistory();
            i=0;
        }
    }

    public ArrayList<String> searchHash(String input)
    {
        ArrayList<ArrayList<String>> books=booksByCom.get(input);
        ArrayList<String> reBooks = new ArrayList<String>();

        int i=0;
        while (i<books.size())
        {
            int j=0;
            while (j<books.get(i).size())
            {
                if(Customer.getInstance().checkBoughtBooks(books.get(i).get(j))==true)
                {
                    reBooks.add(books.get(i).get(j));
                }
                j++;
            }
            i++;
        }



        return reBooks;
    }

    public String filterHashResult(ArrayList<String> input)
    {
        ArrayList<Integer> countDup = new ArrayList<Integer>();
        ArrayList<String> key = new ArrayList<String>();



        int flag=0;
        int word=0;
        int i =0;

        while (word < input.size())
        {
            while (i < key.size())
            {

                if (key.get(i).equals(input.get(word)) == true)
                {

                    countDup.set(i, countDup.get(i) + 1);
                    flag = 1;
                    break;
                }
                i++;
            }
            if (flag == 0)
            {

                key.add(input.get(word));
                countDup.add(1);
            }
            word++;
            i=0;
            flag=0;
        }
        i=0;
        /*
        while (i<countDup.size())
        {
            System.out.print(key.get(i)+"+"+countDup.get(i)+" ");
            i++;
        }
        System.out.println();
        */

        int maxIndex=0;
        int max=0;
        i=0;
        while (i<countDup.size())
        {
            if(max<countDup.get(i))
            {
                max=countDup.get(i);
                maxIndex=i;
            }
            i++;
        }

        return key.get(maxIndex);

    }




    public static void main(String[] args)
    {/*
        ArrayList<String> hashResult = new ArrayList<String>();
        Customer customer = new Customer("aaa","aaa");
        BookEngine engine=null;
        engine=BookEngine.getInstance();
        engine.createHistoryHash(customer);
        hashResult = engine.searchHash("527");
        int i=0;
        while (hashResult.size()>i)
        {
            System.out.print(hashResult.get(i)+" ");
            i++;
        }
        System.out.println();

        System.out.println(engine.filterHashResult(hashResult));
        */
    }


}

