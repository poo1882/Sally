import java.util.ArrayList;

public class FileManager extends TextFileReader
{
    String bookFile = new String("bookdata.txt");

    public boolean readBook()
    {
        String line;
        Book curBook;
        if(open(bookFile)==false)
        {
            System.out.println("error opening book data file");
            return false;
        }
        line=getNextLine();
        while (line!=null)
        {
            ArrayList<String> genre = new ArrayList<>();
            String fields[] = line.split("|");          //yung mai nae jai
            String fields2[] = line.split(",");
            int i=0;
            while (fields2[i]!=null)
            {
                genre.add(fields2[i]);
            }

            curBook= new Book(fields[0],genre,fields[2],Float.parseFloat(fields[3]));

            line=getNextLine();
        }


        close(bookFile);
    }
}
