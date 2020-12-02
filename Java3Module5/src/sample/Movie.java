package sample;

public class Movie {
    private int id;
    private String name;
    private int rating;
    private String description;

    public Movie(int id, String name, int rating, String description){
        setID(id);
        setName(name);
        setRating(rating);
        setDescription(description);

    }

    public void setID(int id) { this.id = id; }
    public Integer getID(){ return id; }

    public void setName(String name) { this.name = name; }
    public String getName() { return name; }

    public void setRating(int rating) { this.rating = rating; }
    public int getRating() { return rating; }

    public void setDescription(String description) { this.description = description; }
    public String getDescription() { return description; }

    @Override
    public String toString(){return getRating()+", "+getName();}
}
