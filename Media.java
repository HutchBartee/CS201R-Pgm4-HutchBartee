public class Media{

    protected char type;
    protected String title;
    protected String name;
    protected int rating;
    protected String genre;
    protected int length;
    protected int yearReleased;
    public static int[] mediaCounts = {0,0,0};

    //default constructor
    public Media(){
        this.type = ' ';
        this.title = " ";
        this.name = " ";
        this.rating = -1;
        this.genre = " ";
        this.length = -1;
        this.yearReleased = -1;
    }

    //overloaded constructor
    public Media(char type, String title, String name, int rating, String genre, int length, int yearReleased){
        this.type = type;
        this.title = title;
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.length = length;
        this.yearReleased = yearReleased;
    }

    //getters (accessors)
    public char getType(){return type;}
    public String getTitle(){return title;}
    public String getName(){return name;}
    public int getRating(){return rating;}
    public String getGenre(){return genre;}
    public int getLength(){return length;}
    public int getYearReleased(){return yearReleased;}

    //setters (mutators)
    public void setType(char newType){this.type = newType;}
    public void setTitle(String newTitle){this.title = newTitle;}
    public void setName(String newName){this.name = newName;}
    public void setRating(int newRating){this.rating = newRating;}
    public void setGenre(String newGenre){this.genre = newGenre;}
    public void setLength(int newLength){this.length = newLength;}
    public void setYearReleased(int newYearReleased){this.yearReleased = newYearReleased;}

    public String toString(){
        String out = String.format("%-37s%10d%10d     %-23s%7s\n",title, yearReleased, rating, genre, type);
        return out;
    }
}