import java.util.*;
import java.io.*;

public class Main{
    public static void main(String args[]) {
        System.out.println("~ MEDIA LIBRARY ~");
        ArrayList<Media> library = new ArrayList<>(); // create new Media ArrayList

        Scanner scanner = new Scanner(System.in); // set scanner + files
        String inputFile = "mediaList.txt";
        String errorFile = "error.txt";
        String reportFile = "report.txt";

        readFile(inputFile, errorFile, library);
        System.out.println("Media loaded: " + library.size() + " items.");

        try(PrintWriter writer = new PrintWriter(new FileWriter(reportFile))){
            String choice = " ";
            while(choice.charAt(0) != 'Q'){
                choice = scanner.nextLine();
                
                readInput(choice, library, writer);
            }
            scanner.close();
            System.exit(0);
        }
        catch(IOException e){
            System.err.println("Could not write report: " + e.getMessage());
        }

    }

    public static void readFile(String inputPath, String errorPath, ArrayList<Media> library){ // code lifted + edited from lab 8
        try(BufferedReader reader = new BufferedReader(new FileReader(inputPath));
        BufferedWriter errorWriter = new BufferedWriter(new FileWriter(errorPath))){
            String line;
            while((line = reader.readLine()) != null){ // iterates until end of file
                String[] tokens = line.split(","); // splits items into list entries
                try{
                    if(tokens.length < 7) throw new IllegalArgumentException("Incomplete record"); // throws error if not all relevant items are present
                    else{ // attempts to convert entries into proper variables
                        char type = tokens[0].charAt(0);
                        String title = tokens[1];
                        String name = tokens[2];
                        int rating = Integer.parseInt(tokens[3]);
                        String genre = tokens[4];
                        int length = Integer.parseInt(tokens[5]);
                        int yearReleased = Integer.parseInt(tokens[6]);

                        // handle errors
                        if(type != 'M' && type != 'B' && type != 'S') throw new IllegalArgumentException("Invalid media type");
                        if(rating < 1 || rating > 10) throw new IllegalArgumentException("Rating must be from 1 to 10");
                        if(yearReleased < 1920 || yearReleased > 2024) throw new IllegalArgumentException("Year must be from 1920 to 2024");

                        Media i = new Media(type, title, name, rating, genre, length, yearReleased);
                        library.add(i);
                    }
                }
                catch(NumberFormatException e){
                    errorWriter.write("Error: " + e.getMessage() + " is not an integer — " + line + "\n"); // writes invalid line to error file
                }
                catch(Exception e){
                    errorWriter.write("Error: " + e.getMessage() + " — " + line + "\n"); // writes invalid line to error file
                }
            }
        }
        catch(IOException e){
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    public static void readInput(String input, ArrayList<Media> library, PrintWriter writer){
        String[] items = input.split(","); // splits input into list
        char typeChoice = items[0].charAt(0);
        if(typeChoice == 'M' || typeChoice == 'B' || typeChoice == 'S'){ // checks medium
            if(items.length == 1){
                listMedia(library, writer, typeChoice, "null", -1);
            }
            else if(items.length == 2){
                boolean isInt; // check if second entry is string or integer
                try{
                    Integer.parseInt(items[1]);
                    isInt = true;
                }
                catch(NumberFormatException e){
                    isInt = false;
                }
                if(isInt == false){
                    String genre = items[1];
                    listMedia(library, writer, typeChoice, genre, -1);
                }
                if(isInt == true){
                    int rating = Integer.parseInt(items[1]);
                    listMedia(library, writer, typeChoice, "null", rating);
                }
            }
            else if(items.length >= 7){ // check to see if adding entry
                try{ // check if each input is correct type
                    char type = items[0].charAt(0);
                    String title = items[1];
                    String name = items[2];
                    int rating = Integer.parseInt(items[3]);
                    String genre = items[4];
                    int length = Integer.parseInt(items[5]);
                    int yearReleased = Integer.parseInt(items[6]);

                    FileWriter mlWriter = new FileWriter("mediaList.txt", true);
                    mlWriter.write("\n" + input);
                    mlWriter.close();
                }
                catch(Exception e){
                    System.out.println("Invalid input.\n");
                }
            }
        }
        else if(typeChoice == 'Q');
        else{
            System.out.println("Invalid input.\n");
        }
    }

    public static void listMedia(ArrayList<Media> library, PrintWriter writer, Character userType, String userGenre, int userRating){
        writer.write("LIBRARY\n");
        writer.write(String.format("%-37s%10s%10s     %-23s%7s%n","TITLE","YEAR","RATING","GENRE","MEDIUM"));

        boolean exists = false; // keeps track of if entries exist
        if(userGenre == "null" && userRating == -1){ // grab medium
            for(Media i : library){
                char type = i.getType();
                if(type == userType){
                    exists = true;
                    writer.print(i);
                }
            }
        }
        else if(userRating == -1){ // grab genre IF IT FUNCTIONED PROPERLY
            for(Media i : library){
                char type = i.getType();
                String genre = i.getGenre();
                if(type == userType && genre.equals(userGenre)){
                    exists = true;
                    writer.print(i);
                }
            }
        }
        else if(userGenre == "null"){ // grab ratings
            for(Media i : library){
                char type = i.getType();
                int rating = i.getRating();
                if(type == userType && rating >= userRating){
                    exists = true;
                    writer.print(i);
                }
            }
        }
        if(exists == false) System.out.println("No existing entries.");
        writer.close();
    }
}