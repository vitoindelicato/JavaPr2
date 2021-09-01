import java.util.*;

public class MicroBlog implements SocialNetwork{

    private List<Post> postList = null;
    private Map<String, Set<String>> sn = null;

    public Map<String, Set<String>> getSn(){return sn;}
    public List<Post> getPostList(){return this.postList;}


     /*
    OVERVIEW: tipo di dato che rappresenta un Social Network

    AF: {postList[0],....,postList[postList.length()-1]} sn con sn: K->V | forAll sn[k] belongTo V

    IR: sn != null
        forAll i.  <0i<sn.size() ===> sn[i] != null && forAll j. 0<j<sn[i].size() | sn[i][j] != null 

    */    

    
    //EFFECTS: crea oggetto MicroBlog e ne inizializza i campi
    public MicroBlog(){
        this.postList = new ArrayList<Post>();
        this.sn = new HashMap<String, Set<String>>();
      
    } 

    

    //TEXT FUNCTIONS =========================================== 
    //LE CLAUSOLE REQUIRES, THROWS, EFFECTS, MODIFIES sono definite nell'interfaccia per leggibilità

    public Map<String, Set<String>> guessFollowers(List<Post> ps){ //restituisce la rete sociale derivante da una lista di post
        if(ps == null){
            throw  new NullPointerException();
        }
        Map<String, Set<String>> result = new HashMap<String,Set<String>>();
        for(Post postIterator: ps){ 
            if(postIterator == null) {
                throw new NullPointerException("Elemento nullo o non valido in lista!");
            }
            if(!result.containsKey(postIterator.getAuthorUsername())){ //vedo se l'autore è contenuto nella struttura che devo ritornare, in modo da fare apparire un autore una e una sola volta
                MyUser userIterator = postIterator.getAuthor();
                result.put(userIterator.getUsername(), MyUser.getUsernames(userIterator.getIFollow()));//metto nella struttura [username : [lista di chi segue]]
            }
        }
        return result;  
    }

   
    public List<String> influencers (Map<String, Set<String>> followers) throws NullPointerException{ //ricevo una rete sociale, ne stampo gli utenti in ordine decrescente di followers
        if(followers == null){
            throw  new NullPointerException();
        }

        Map<String, Integer> intermediate = new HashMap<String, Integer>();//trasformo la mappa che ho come parametro in una mappa contenente {username : #followers}
        for(Map.Entry<String, Set<String>> entry : followers.entrySet()){
            intermediate.put(entry.getKey(), entry.getValue().size());            
        } 
        //una volta creata la mappa intermedia, la ordino per valore, usando le funzioni lambda.
        LinkedHashMap<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        intermediate.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedMap.put(x.getKey(), x.getValue()));
        
        //System.out.println("Sorted Map:  " + sortedMap); debug purposes
        
        
        ArrayList<String> result = new ArrayList<String>();
        for(Map.Entry<String, Integer> iterator : sortedMap.entrySet()){
            result.add(iterator.getKey());
        }

        return result; //il risultato sarà una lista di stringhe, ovvero usernames messi in ordine decrescente di followers.
    }


    
    public Set<String> getMentionedUsers(){ //vedo tutti gli user della rete, se hanno scritto almeno un post li aggiungo al set
        Set<String> result = new HashSet<String>();
        for(Post iterator : postList){
            if (iterator == null){
                throw new NullPointerException();
            }
            if(!result.contains(iterator.getAuthorUsername())){
                result.add(iterator.getAuthorUsername());
            }
        }
        return result;
    }


    public Set<String> getMentionedUsers(List<Post> ps)throws NullPointerException{//controllo ogni post, e aggiungo l'user al set, no duplicati
        if(ps == null){
            throw  new NullPointerException();
        }

        Set<String> result = new HashSet<String>();
        for (Post postIterator : ps){
            if (!result.contains(postIterator.getAuthorUsername())){
                result.add(postIterator.getAuthorUsername());
            }
        }

        return result;

    }



    public List<Post> writtenBy(String username)throws NullPointerException{
        if(username == null){
            throw  new NullPointerException();
        }
        List<Post> result = new ArrayList<Post>();
        //MyUser iterator = MyUser.stringToUser(username);
        //result = iterator.getPosts();
        for (Post iterator : postList){
            if(iterator.getAuthorUsername().equals(username)){
                result.add(iterator);
            }
        }
        return result;

    }



    public List<Post> writtenBy(List<Post> ps, String username)throws NullPointerException{
        if(ps == null || username == null){
            throw  new NullPointerException();
        }
        List<Post> result = new ArrayList<Post>();
        for(Post iterator : ps){
            if(iterator == null){
                throw new NullPointerException();
            }
            if(iterator.getAuthorUsername().equals(username)){
                result.add(iterator);
            }
        }
        return result;

    }


    public List<Post> containing(List<String> words)throws NullPointerException{
        if(words == null){
            throw  new NullPointerException();
        }
        List<Post> result = new ArrayList<Post>();
        for(Post postIterator : postList){
            for(String wordIterator : words){
                if(wordIterator == null){
                    throw new NullPointerException();
                }
                if(postIterator.getText().contains(wordIterator)){
                    result.add(postIterator);
                    break;
                }
            }
        }
        return result;
    }



    // ADDITIONAL FUNCTIONS ===========================================


    //REQUIRES: user != null
    //MODIFIES: this.sn
    //EFFECTS: Inserisce un utente nella map usata come struttura di implementazione, la funzione viene chiamata alla creazione di un nuovo utente, il valore sarà una lista vuota
   // public void registerUser(MyUser user){//ogni nuovo user viene inserito nella map come chiave, ed una lista vuota come set
    //    this.getSn().put(user.getUsername(), MyUser.getUsernames(user.getIFollow()));
      
    //}

    //REQUIRES: post != null
    //MODIFIES: this.postList, this.sn
    //EFFECTS:  aggiunge un nuovo post alla struttura di MicroBlog, se l'utente che ha creato il post non è nella Map viene aggiunto
    public void registerPost(Post post){
        this.postList.add(post);
        if(!this.getSn().containsKey(post.getAuthorUsername())){
            this.addEntryMap(post.getAuthorUsername());
        }

    }

    //REQUIRES: user != null followed != null
    //MODIFIES: MicroBlog.sn
    //EFFECTS:  Aggiorna la map, inserendo nel set un utente seguito dal parametro user
    public void UpdateMap(String follower, String followed){
        if(this.getSn().containsKey(follower)){
            Set<String> tmp = this.getSn().get(follower);
            tmp.add(followed);
        }
        else{
            Set<String> tmp = new HashSet<String>();
            tmp.add(followed);
            sn.put(follower, tmp);

        }

    }
    
    public void addEntryMap(String username){
        Set<String> tmp = new HashSet<String>();
            sn.put(username, tmp);

    }


    //REQUIRES: post != null, user != null
    //THROWS: IllegalAction se un utente mette like a se stesso, o se mette like due volte allo stesso post(propagazione), NullPointerException se post == null
    //MODIFIES: post.likes, 
            //user.iFollow se metto like ad un autore che non seguo,
            // post.getUser().followMe per la stessa situazione, aggiornando la lista followMe dell'autore del post
    //EFFECTS: realizza l'azione di mettere like, e ne gestisce tutte le varie conseguenze
    public void placeLike(Post post, MyUser user)throws IllegalAction, NullPointerException{
        if(post == null){
            throw new NullPointerException("Post non valido!");
        }
        else if(user.equals(post.getAuthor())){
            throw new IllegalAction("Un utente non può mettersi like da solo!");
        }
        else if(!user.getIFollow().contains(post.getAuthorUsername())){ //se metto like ad un post di cui non seguo l'autore, lo seguiro in automatico
            this.follow(user, post.getAuthor());
        }
        post.updateLikes(user); //questa funzione aggiunge l'username del parametro user alla lista 'likes'

    }


    //REQUIRES: follower != null, followed != null
    //THROWS: IllegalAction se un utente segue se stesso, o se segue due volte lo stesso user, NullPointerException se user == null
    //MODIFIES: //follower.iFollow
                //followed.followMe
                // SocialNetwork.sn
    //EFFECTS: realizza l'azione di seguire un utente, ne gestisce le varie conseguenze
    public void follow(MyUser follower, MyUser followed)throws IllegalAction{
        if(follower == null || followed == null){
            throw new NullPointerException();
        }
        if(follower.equals(followed)){
            throw new IllegalAction("Un utente non può seguirsi da solo!");
        }

        if(follower.getIFollow().contains(followed.getUsername())){
            throw new IllegalAction("Non è consentito seguire un utente più di una volta");
        }
        else{
            follower.getIFollow().add(followed.getUsername());
            this.UpdateMap(follower.getUsername(), followed.getUsername());
            followed.getFollowMe().add(follower.getUsername());
        }
        
    }


}
