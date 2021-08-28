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
    //THROWS: IllegalAction se un utente mette like a se stesso, o se mette like due volte allo stesso post(propagazione), NullPointerException se post == null
    //MODIFIES: post.likes, 
            //this.iFollow se metto like ad un autore che non seguo,
            // post.getUser().followMe per la stessa situazione, aggiornando la lista followMe dell'autore del post
    //EFFECTS: realizza l'azione di mettere like, e ne gestisce tutte le varie conseguenze
    public void placeLike(Post post)throws IllegalAction, NullPointerException;

    //REQUIRES: user != null
    //THROWS: IllegalAction se un utente segue se stesso, o se segue due volte lo stesso user, NullPointerException se user == null
    //MODIFIES: //this.iFollow
                //user.followMe
                // SocialNetwork.sn
    //EFFECTS: realizza l'azione di seguire un utente, ne gestisce le varie conseguenze
    public void follow(MyUser user) throws IllegalAction;
    

    //REQUIRES: post != null
    //MODIFIES: this.createdPosts
    //EFFECTS: Aggiunge un post creato da un utente alla lista di tutti i post creati da quell'utente
    public void updatePostList(Post post);


    public String toString();



    
}
