import java.util.*;

public class MyUser implements User{

    private static List<String> userList = new ArrayList<String>();
    private String username;
    private List<Post> createdPosts;
    private List<String> iFollow; 
    private List<String> followMe; 

    /*
    OVERVIEW: tipo di dato che rappresenta un user di un social network, con tutti i suoi attributi:
    userList = Lista statica che memorizza TUTTI gli utenti creati

    username = username idenitificativo dell'utente
    createdPost = lista dei post scritti dall'utente
    iFollow = struttura di supporto che memorizza tutti gli utenti che this segue
    followMe = struttura di supporto che memorizza tutti gli utenti che seguono this


    AF: <username,{iFollow[0],....,iFollow[iFollow.length()-1]}
                  {followMe[0],....,followMe[followMe.length()-1]}
                  {createdPosts[0],....,createdPosts[createdPosts.length()-1]}          
        >
        {userList[0],....,userList[userList.length()-1]}
    
    IR : username != null && createdPosts != null && iFollow != null && followMe != null 
    (forall i. 0 <= i < createdPosts.size() => createdPost[i] != null)
    (forall i. 0 <= i < iFollow.size() => iFollow[i] != null)
    (forall i. 0 <= i < followMe.size() => followMe[i] != null)
    (forall i. 0 <= i < userList.size() => userList[i] != null)

    
    */



    //REQUIRES: username != null
    //THROWS: InvalidUsername se il nome è vuoto, NullPointerException se è null
    //MODIFIES: this
    //EFFECTS: crea l'oggetto user e ne inizializza i campi
    public MyUser(String username)throws InvalidUsername, NullPointerException, IllegalArgumentException {
        if (username == null){
            throw new NullPointerException("Nome utente non valido!");
        }

        if(username.trim().isEmpty()){
            throw new InvalidUsername("Nome utente non valido!");
        }

        /*for(MyUser iterator : userList){//purtroppo devo accedere ogni elemento di tipo user per verificarne il campo username
            if(iterator.getUsername().equals(username)){
                throw new InvalidUsername("questa username non è disponibile\t utente non creato");
            }
        }*/
        if (userList.contains(username)){
            throw new InvalidUsername("Username già utilizzata\t utente non creato");
        }
        this.username = username;
        this.createdPosts = new Vector<Post>();
        this.iFollow = new Vector<String>();
        this.followMe = new Vector<String>();
        MicroBlog.registerUser(this); //registro automaticamente un utente sulla struttura statica di SocialNetwork al momento della sua creazione
        userList.add(this.getUsername());//aggiungo il nuovo utente alla variabile di istanza della classe

    }



    //getters

    public String getUsername(){
        return this.username;
    }

    public List<Post> getPosts(){
        return this.createdPosts;
    }

    public List<String> getFollowMe(){
        return this.followMe;
    }

    public List<String> getIFollow(){
        return this.iFollow;
    }


    public void updatePostList(Post post){
        this.createdPosts.add(post);
    }

    public static List<String> getUserList() {
        return userList;
    }


    


    //other functions ===> Cosa può fare un user?

    public void placeLike(Post post)throws IllegalAction, NullPointerException{
        if(post == null){
            throw new NullPointerException("Post non valido!");
        }
        else if(this.equals(post.getAuthor())){
            throw new IllegalAction("Un utente non può mettersi like da solo!");
        }
        else if(!iFollow.contains(post.getAuthorUsername())){ //se metto like ad un post di cui non seguo l'autore, lo seguiro in automatico
            follow(post.getAuthor());
        }
        post.updateLikes(this);

    }


    public void follow(MyUser user)throws IllegalAction{
        if(this.equals(user)){
            throw new IllegalAction("Un utente non può seguirsi da solo!");
        }

        if(this.iFollow.contains(user.getUsername())){
            throw new IllegalAction("Non è consentito seguire un utente più di una volta");
        }
        else{
            this.iFollow.add(user.getUsername());
            MicroBlog.UpdateMap(this.getUsername(), user.getUsername());
            user.followMe.add(this.getUsername());
        }
        
    }



    //REQUIRES: lst != null, forAll i < lst.size() lst[i] != null
    //THROWS: NullPointerException se lst o un suo elemento == null
    //EFFECTS: data una lista di User, ritorna un set con i loro usernames
    public static Set<String> getUsernames(List<String>lst){
        Set<String> result = new HashSet<String>();
        for(String iterator : lst){
            if(iterator == null){
                throw new NullPointerException();
            }
            result.add(iterator);
        }
        return result;
    }



    public String toString(){
        String message = ("{"+ "Username: " + this.username + "}");
        return message;
    }

    //REQUIRES: username != null
    //EFFECTS: data una stringa contenente una username ritorna l'oggetto

    /*public static MyUser stringToUser(String username){
        for(MyUser iterator : userList){
            if(username.equals(iterator.getUsername())){
                return iterator;
            }
        }
        return null;
    }*/

}
