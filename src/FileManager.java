import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class FileManager extends TextFileReader
{


    private TextFileReader bookFile = new TextFileReader();


    private ArrayList<String> randomGenre =new ArrayList<String>();


    public void initializeReader()
    {
        bookFile.open("bookdatanew.txt");
        randomGenre.add("Fantasy");
        randomGenre.add("Action");
        randomGenre.add("Horror");
        randomGenre.add("Investigation");
        randomGenre.add("Guide");
        randomGenre.add("Health");
        randomGenre.add("Mystery");
        randomGenre.add("Science");
        randomGenre.add("Biography");
        randomGenre.add("Drama");

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

    public static void main(String[] args)
    {
        FileManager fileManager = new FileManager();
        fileManager.initializeReader();
        Book book;
        int i=0;


        while (i<999)
        {
            book = fileManager.readBookFile();
            book.printDetail();
            System.out.println(i);
            System.out.println();

            i++;
        }



        System.out.println(i);


    }
}