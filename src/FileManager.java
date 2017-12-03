import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


public class FileManager extends TextFileReader
{
    private static HashMap<String, ArrayList<ArrayList<String>>> hisMap = new HashMap<>();
    private static FileManager instance;

    private TextFileReader bookFile = new TextFileReader();
    private TextFileReader hisFile = new TextFileReader();
    private TextFileReader idFile = new TextFileReader();

    private ArrayList<String> randomGenre =new ArrayList<String>();

    private FileManager()
    {

    }



    public void writeBuyingHistory(ArrayList<String> boughtBooks)
    {

    }

    private void createHisMap()
    {
        ArrayList<String> curHis = new ArrayList<String>();
        idFile.open("history.txt");
        String line=idFile.getNextLine();
        while (line!=null)
        {
            String[] fields = line.split(",");
            int i=0;
            String[] fields2 = fields[1].split("%");
            if(fields2.length==1)
            {
                curHis.add(fields2[0]);
            }
            while (i<fields2.length)
            {
                curHis.add(fields2[i]);
                i++;
            }

            if(hisMap.get(hisMap.get(fields[0]))==null)
            {
                hisMap.put(fields[0],new ArrayList<>());
                hisMap.get(fields[0]).add(curHis);
            }
            else
            {
                hisMap.get(fields[0]).add(curHis);
            }
            curHis.clear();
            line=idFile.getNextLine();
        }
    }

    public static FileManager getInstance()
    {
        if(instance==null)
        {
            instance = new FileManager();
            instance.initializeReader();
            return instance;
        }
        else
        {
            return instance;
        }
    }

    private void initializeReader()
    {
        bookFile.open("bookdatanew.txt");
        hisFile.open("history.txt");
    }

    public Book readBookFile()
    {
        String name="";
        String author ;
        ArrayList<String> genre = new ArrayList<String>();
        float price=0;
        String bookId;
        String fields2[];
        String line;

        line = bookFile.getNextLine();
        if(line==null)
        {
            return null;
        }
        String fields[] = line.split(" by ");
        if(fields.length>2)
        {
            int i=0;
            while (i<fields.length-1)
            {
                name+=fields[i];
                if(i<fields.length-2)
                {
                    name+= " by ";
                }
                i++;
            }
            fields2 = fields[fields.length-1].split("%");
        }
        else
        {
            name = fields[0];
            fields2 = fields[1].split("%");
        }

        author = fields2[0];
        price=Float.parseFloat(fields2[1]);
        bookId=fields2[2];
        genre.add(fields2[3]);
        genre.add(fields2[4]);
        return new Book(name,genre,author,price,bookId);


    }

    private boolean checkSameHis(int[] array,int toCheck)
    {
        int i=0;
        while (i<array.length)
        {
            if(toCheck==array[i])
            {
                return false;
            }
            i++;
        }

        return true;
    }



    public ArrayList<String> readHistory()
    {
        ArrayList<String> bookHistory = new ArrayList<String>();
        String line;
        line=hisFile.getNextLine();
        if(line==null)
        {
            return null;
        }
        String fields[] = line.split(",");
        String fields2[] = fields[1].split("%");
        if(fields2.length==1)
        {
            bookHistory.add(fields2[0]);

            return bookHistory;
        }
        else
        {
            int i=0;
            while (i<fields2.length)
            {
                bookHistory.add(fields2[i]);
                i++;
            }

            return bookHistory;
        }



    }

    public static void main(String[] args)
    {
        FileManager fileManager = FileManager.getInstance();
        fileManager.createHisMap();

        /*
        fileManager.initializeReader();
        Book book;
        int i=0;
        fileManager.randomlyGenHis();
        System.out.println(i);
        */

    }

    /*
    public void randomlyGenHis()
    {
        Random rn = new Random();
        int i=0;
        int[] bookId=new int[5];
        while (i<100)
        {
            String line="";
            line+=getSaltString()+",";
            int j=0;
            while (j<5)
            {
                int temp=rn.nextInt(998 - 0 + 1) + 0;
                while(checkSameHis(bookId,temp)==false)
                {
                    temp=rn.nextInt(998 - 0 + 1) + 0;
                }
                bookId[j]=temp;
                if(j==0)
                {
                    if (temp < 10) {
                        line += "" + "00" + bookId[j];
                    } else if (temp < 100) {
                        line += "" + "0" + bookId[j];
                    } else if (temp < 1000) {
                        line += "" + bookId[j];
                    }
                }
                else
                {
                    if (temp < 10) {
                        line += "%" + "00" + bookId[j];
                    } else if (temp < 100) {
                        line += "%" + "0" + bookId[j];
                    } else if (temp < 1000) {
                        line += "%" + bookId[j];
                    }
                }
                j++;
            }
            System.out.println(line);
            i++;
        }
    }
    private String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 15) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;
    }
    public void randomlyGenIDpass()
    {
        int i=0;
        String line = new String();
        while (i<20)
        {
            line += getSaltString()+","+getSaltString();
            System.out.println(line);
            i++;
            line="";
        }
    }
    public void randomlyGenCat()
    {
        String line = new String();
        Random rn = new Random();
        line = bookFile.getNextLine();
        int i=0;
        while(line!=null)
        {
            int price=rn.nextInt(1000 - 100 + 1) + 100;
            line+="%"+price;
            if(i<10)
            {
                line+="%"+"00"+i;
            }
            else if(i<100)
            {
                line+="%"+"0"+i;
            }
            else if(i<1000)
            {
                line+="%"+i;
            }
            i++;
            int genre1=rn.nextInt(9 - 0 + 1) + 0;
            int genre2=rn.nextInt(9 - 0 + 1) + 0;
            while(genre2==genre1)
            {
                genre2=rn.nextInt(9 - 0 + 1) + 0;
            }
            line+="%"+randomGenre.get(genre1)+"%"+randomGenre.get(genre2);
            System.out.println(line);
            line = bookFile.getNextLine();
        }
    }
    */
}