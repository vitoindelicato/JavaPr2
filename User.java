import java.util.List;

public interface User {

    //EFFECTS: ritorna this.username
    public String getUsername();

    //EFFECTS: ritorna this.followMe
    public List<String> getFollowMe();

    //EFFECTS: ritorna this.iFollow
    public List<String> getIFollow();


    public String toString();



    
}
