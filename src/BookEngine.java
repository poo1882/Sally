import java.util.ArrayList;
import java.util.HashMap;

/**
 * A class that helps to generate a recommendation for user by their purchasing history
 */
public class BookEngine {

    /**
     * A hashmap that map one bookID to many bookIDs (all books which are purchased by the customers who bought the key bookID)
     */
    private static HashMap<String, ArrayList<ArrayList<String>>> booksByCom;



    private FileManager fileManager=null;
    private static BookEngine instance;

    /**
     * singleton constructor
     */
    private BookEngine()
    {
        createHistoryHash();
    }


    /**
     * a function to create and/or return instance of Bookengine class
     * @return instance of Bookengine class
     */
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

    /**
     * initialize a buying history hashmap to help generating recommendation
     */
    private void createHistoryHash()
    {
        booksByCom =   new HashMap<>();
        ArrayList<String> readHis = new ArrayList<String>();
        fileManager=FileManager.getInstance();
        readHis = fileManager.readHistory();
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

    /**
     * get all bookIDs in the hashmap which key == input
     * @param input key to search in hashmap
     * @return ArrayList of bookIDs
     */
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
                //if(Customer.getInstance().checkBoughtBooks(books.get(i).get(j)))
                if(!Customer.getInstance().getHistory().isInCollection(books.get(i).get(j)))
                {
                    reBooks.add(books.get(i).get(j));
                }
                j++;
            }
            i++;
        }



        return reBooks;
    }

    /**
     * this function collects the most duplicate bookID from input
     * @param input ArrayList of BookIDs
     * @return most duplicate bookID
     */
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
}

