import java.io.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * A class to mange input and output file
 */

public class FileManager extends TextFileReader
{
    /** history purchasing hashmap */
    private static HashMap<String, ArrayList<String>> hisMap ;
    /** username password hashmap */
    private static HashMap<String, String> IDMap ;
    /** instance of Filemanager */
    private static FileManager instance;

    private TextFileReader bookFile = new TextFileReader();
    private TextFileReader hisFile = new TextFileReader();
    private TextFileReader idFile = new TextFileReader();

    /** line to write history in history.txt*/
    private int specificLine;

    /**
     *  constructor of FileManager initialize ystem
     */
    private FileManager()
    {
        bookFile.open("bookdata.txt");
        hisFile.open("history.txt");
        idFile.open("idpassword.txt");
        createIDMap();
        createHisMap(Customer.getInstance());
    }


    /**
     * create username password hashmap
     */
    public void createIDMap()
    {
        IDMap =  new HashMap<>();
        idFile.open("idpassword.txt");
        String line = idFile.getNextLine();



        while(line!=null) {
            String[] fields = line.split(",");
            IDMap.put(fields[0], fields[1]);

            line = idFile.getNextLine();

        }

    }

    /**
     * Write new username account to a data file
     * @param ID
     * @param password
     * @throws IOException
     */
    public void writeIDfile(String ID,String password)throws IOException
    {


        List<String> lines = Files.readAllLines(Paths.get("idpassword.txt"));
        /*write part*/

        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            int j=0;
            String content = "";
            while (j<lines.size())
            {
                content+= lines.get(j)+"\n";
                j++;
            }
            content+=ID+","+password;

            fw = new FileWriter("idpassword.txt");
            bw = new BufferedWriter(fw);
            bw.write(content);

            bw.close();
            fw.close();
            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }
    }

    /**
     * find password of an ID in username password hashmap
     * @param ID
     * @return 0 if not found
     * @return password
     */
    public String findPassword(String ID)
    {
        String password = "0";
        if(IDMap.get(ID)!=null)
        {
            password = IDMap.get(ID);
        }
        return password;
    }


    /**
     * write a history.txt after checking out
     * @param boughtBooks bookIDs to write in a file
     * @throws IOException
     */
    public void writeBuyingHistory(ArrayList<String> boughtBooks)throws IOException
    {
        String toWrite = new String();
        int i =0;
        if(hisMap.get(Customer.getInstance().getUsername())==null)
        {
            hisMap.put(Customer.getInstance().getUsername(),new ArrayList<String>());
        }
        else
        {
            i=0;
            while (i<boughtBooks.size())
            {
                hisMap.get(Customer.getInstance().getUsername()).add(boughtBooks.get(i));
                i++;
            }
        }


        i=0;

        toWrite+=Customer.getInstance().getUsername()+",";
        while (i<hisMap.get(Customer.getInstance().getUsername()).size()-boughtBooks.size())
        {
            toWrite+=hisMap.get(Customer.getInstance().getUsername()).get(i)+"%";
            i++;
        }
        ;
        i=0;
        while (i<boughtBooks.size())
        {
            toWrite+=boughtBooks.get(i);
            if(i!=boughtBooks.size()-1)
                toWrite+="%";
            i++;
        }




        List<String> lines = Files.readAllLines(Paths.get("history.txt"));
        if(specificLine>lines.size()-1)
        {
            lines.add(toWrite);
        }
        else {
            lines.set(specificLine, toWrite);
        }
        /*write part*/
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {
            int j=0;
            String content = "";
            while (j<lines.size())
            {
                content+= lines.get(j)+"\n";
                j++;
            }

            fw = new FileWriter("history.txt");
            bw = new BufferedWriter(fw);
            bw.write(content);
            System.out.println("Done");

        } catch (IOException e) {

            e.printStackTrace();

        } finally {

            try {

                if (bw != null)
                    bw.close();

                if (fw != null)
                    fw.close();

            } catch (IOException ex) {

                ex.printStackTrace();

            }

        }

    }

    /**
     * create history of each customer hashmap
     * @param customer instance of current customer
     */
    private void createHisMap(Customer customer)
    {
        hisMap=  new HashMap<>();
        idFile.open("history.txt");
        String line=idFile.getNextLine();
        String name = customer.getUsername();
        specificLine=-1;
        int i=0;
        while (line!=null)
        {
            int j=0;
            String[] fields = line.split(",");
            if(fields[0].equals(name))
            {
                specificLine=i;
            }

            String[] fields2 = fields[1].split("%");


            while (j<fields2.length)
            {
                if(hisMap.get(fields[0])==null)
                {
                    hisMap.put(fields[0],new ArrayList<>());
                    hisMap.get(fields[0]).add(fields2[j]);

                }
                else
                {
                    hisMap.get(fields[0]).add(fields2[j]);
                }
                j++;
            }

            line=idFile.getNextLine();
            i++;
        }
        if(specificLine== -1)
            specificLine=i;
    }

    /**
     * getInstance of FileManager class
     * @return instance of FilaManager
     */
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
            instance.initializeReader();
            return instance;
        }
    }


    private void initializeReader()
    {
        bookFile.open("bookdatanew.txt");
        hisFile.open("history.txt");
    }


    /**
     * read a single line of book data file
     * @return read Book
     */
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

    /*
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
    */


    /**
     * read a single line of history data of each customer
     * @return history bookIDs
     */
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

    /**
     * get current customer's history
     * @param name name of current user
     * @return ArrayList of bookIDs
     */
    public ArrayList<String> getCurCusHis(String name)
    {
        createHisMap(Customer.getInstance());
        return hisMap.get(name);
    }
    /*
    public void randomlyGenHis()
    {
        Random rn = new Random();
        int i=0;
        int[] bookId=new int[5];
        while (i<1000)
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
    */
    public static void main(String[] args)
    {


        /*
        Customer customer = new Customer("FVU6HNSUA08I638","1111");
        FileManager fileManager = FileManager.getInstance();
        fileManager.createHisMap(customer);
        ArrayList<String> boughtBooks = new ArrayList<String>();
        boughtBooks.add("test");
        boughtBooks.add("test2");
        boughtBooks.add("test3");

        try {
            // constructor may throw FileNotFoundException
            fileManager.writeBuyingHistory(boughtBooks,customer);
        } catch (FileNotFoundException e) {
            //do something clever with the exception
        } catch (IOException e) {
            //do something clever with the exception
        }

        try {
            // constructor may throw FileNotFoundException
            fileManager.writeIDfile("junior2341","5555");
        } catch (FileNotFoundException e) {
            //do something clever with the exception
        } catch (IOException e) {
            //do something clever with the exception
        }
        */
        /*
        fileManager.initializeReader();
        Book book;
        int i=0;
<<<<<<< HEAD
        fileManager.randomlyGenHis();
=======


        fileManager.randomlyGenHis();


>>>>>>> 8c5391b9752c3feee99739e2ba97abaf2635ef38
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
<<<<<<< HEAD
=======

>>>>>>> 8c5391b9752c3feee99739e2ba97abaf2635ef38
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
<<<<<<< HEAD
=======

>>>>>>> 8c5391b9752c3feee99739e2ba97abaf2635ef38
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
<<<<<<< HEAD
=======


>>>>>>> 8c5391b9752c3feee99739e2ba97abaf2635ef38
        }
    }
    */
}
