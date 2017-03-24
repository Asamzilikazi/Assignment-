package za.ac.mzilikazi;

/**
 * Created by Asavela on 11
 * March 2017
 *
 */
import com.google.common.io.Files;
import java.util.*;
import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class App
{
    public static void main( String[] args ) throws IOException
    {
        DateFormat dateFormat = new SimpleDateFormat(" yyyy MMMM dd HH:mm ");
        Date date = new Date();
        Scanner scanner = new Scanner(System.in);
        String directory, duplicate = "";
        FileWriter fileWriter;
        BufferedWriter bufferedWriter;

        File file = getFile();
        System.out.println("Enter directory to be scanned [1 to exit]");
        directory = scanner.nextLine();

        //validate directory
        while (!directory.equalsIgnoreCase("1"))
        {
            while (!new File((directory)).isDirectory())
            {
                System.out.println("invalid directory");
                directory = scanner.next();
                System.out.flush();
                if ( directory.equalsIgnoreCase("1")){ System.exit(0);}
            }
            final  File folder = new File(directory);
            File[] files = folder.listFiles();
            fileWriter = new FileWriter(file.getAbsoluteFile(),true);
            bufferedWriter  = new BufferedWriter(fileWriter);

            //checking duplicates
            for (int a = 0; a < files.length;a++){
                for (int b = a + 1; b < files.length;b++ ) {
                    if (files[a].isFile() && files[b].isFile()) {
                        if (Files.asByteSource(files[a]).contentEquals(Files.asByteSource(files[b]))) {
                            duplicate += files[a].getName().toUpperCase() + " is a duplicate of " + files[b].getName().toUpperCase() + "\n";
                        }
                    }
                }
            }
            //leave file blank if there are no duplicates
            if (!duplicate.equalsIgnoreCase("")){
                bufferedWriter.write("Path: " + new File(directory).getAbsoluteFile() + "\n\t\t\t" + dateFormat.format(date) + "\n" + duplicate + "\n");
                duplicate = "";
            }
            System.out.println("Enter directory to be scanned [1 to exit]");
            directory = scanner.next();

            if (directory.equals("1"))
                bufferedWriter.close();

        }
    }

    private static File getFile() throws IOException {

        File file = new File("List of duplicates.txt");
        if (file.exists()){
            file.createNewFile();
        }
        return file;
    }

}