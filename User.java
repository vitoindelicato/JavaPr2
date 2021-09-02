import java.util.List;

public interface User {

    //EFFECTS: ritorna this.username
    public String getUsername();

    //EFFECTS: ritorna this.createdPosts
    public List<Post> getPosts();

    //EFFECTS: ritorna this.followMe
    public List<String> getFollowMe();

    //EFFECTS: ritorna this.iFollow
    public List<String> getIFollow();

    
    //REQUIRES: post != null
    //MODIFIES: this.createdPosts
    //EFFECTS: Aggiunge un post creato da un utente alla lista di tutti i post creati da quell'utente
    public void updatePostList(Post post);


    public String toString();



    
}
