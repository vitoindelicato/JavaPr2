import java.util.*;

public class MicroBlog implements SocialNetwork{

    private static List<Post> postList = null;
    private static Map<String, Set<String>> sn = null;

    public static Map<String, Set<String>> getSn(){return sn;}
    public static List<Post> getPostList(){return postList;}


     /*
    OVERVIEW: tipo di dato che rappresenta un Social Network

    AF: {postList[0],....,postList[postList.length()-1]} sn con sn: K->V | forAll sn[k] belongTo V

    IR: sn != null
        forAll i.  <0i<sn.size() ===> sn[i] != null && forAll j. 0<j<sn[i].size() | sn[i][j] != null 

    */    

    
    //EFFECTS: crea oggetto MicroBlog e ne inizializza i campi
    public MicroBlog(){
        postList = new ArrayList<Post>();
        sn = new HashMap<String, Set<String>>();
      
    } //Creo un solo oggetto di tipo SocialNetwork, da usare per chiamare le funzioni, la classe avrà strutture statiche di supporto per rendere più elegante e meno complesso il codice

    

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
    //MODIFIES: MicroBlog.sn
    //EFFECTS: Inserisce un utente nella map usata come struttura di implementazione, la funzione viene chiamata alla creazione di un nuovo utente, il valore sarà una lista vuota
    public static void registerUser(MyUser user){//ogni nuovo user viene inserito nella map come chiave, ed una lista vuota come set
        sn.put(user.getUsername(), MyUser.getUsernames(user.getIFollow()));
      
    }

    //REQUIRES: post != null
    //MODIFIES: MicroBlog.postList
    //EFFECTS:  aggiunge un nuovo post alla struttura di MicroBlog 
    public static void registerPost(Post post){
        postList.add(post);
    }

    //REQUIRES: user != null followed != null
    //MODIFIES: MicroBlog.sn
    //EFFECTS:  Aggiorna la map, inserendo nel set un utente seguito dal parametro user
    public static void UpdateMap(String user, String followed){
        if(sn.containsKey(user)){
            Set<String> tmp = sn.get(user);
            tmp.add(followed);
        }
        else{
            Set<String> tmp = new HashSet<String>();
            tmp.add(followed);
            sn.put(user, tmp);

        }

    }

}
