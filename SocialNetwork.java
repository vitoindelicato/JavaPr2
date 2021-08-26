import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SocialNetwork {
   


    //REQUIRES: ps != null
    // THROWS: NullPointerException se followers == null || follower[i] == null;
    // EFFECTS: ritorna la rete sociale derivante da una lista di post
    public Map<String, Set<String>> guessFollowers(List<Post> ps) throws NullPointerException; 


    //REQUIRES: follower != null, forEach i <followers.size() followers[i] != null
    // THROWS: NullPointerException se followers == null || follower[i] == null;
    // EFFECTS: ritorna una lista di stringhe contenenti gli usernames, ordinati in maniera decrescente per  numero di followers
    public List<String> influencers (Map<String, Set<String>> followers) throws NullPointerException; 
    


    // EFFECTS: ritorna un set di utenti che hanno scritto almeno un post
    public Set<String> getMentionedUsers();


    //REQUIRES: ps != null && forAll i < ps.size() ps[i] != null
    // THROWS: NullPointerException se ps == null || ps[i] == null;
    // EFFECTS: ritorna un set di utenti che sono gli autori dei post passati come parametro 
    public Set<String> getMentionedUsers(List<Post> ps)throws NullPointerException;//controllo ogni post, e aggiungo l'user al set, no duplicati
    

    //REQUIRES: username != null
    // THROWS: NullPointerException se username == null
    // EFFECTS: ritorna una lista di post, scritti dall'utente il cui username è passato per parametro
    public List<Post> writtenBy(String username)throws NullPointerException;


    //REQUIRES: ps != null, username != null
    //THROWS:   NullPointerException se username == null, se ps == null, se forAll i < ps.size() ps[i] == null
    // EFFECTS: se il parametro username è l'autore di uno o più post in ps, questi post saranno aggiunti alla lista di ritorno  
    public List<Post> writtenBy(List<Post> ps, String username)throws NullPointerException;

    //REQUIRES: words != null, forAll i < words.size() words[i] != null
    //THROWS:   NullPointerException se words == null, se forAll i < words.size() words[i] == null
    //EFFECTS:  ritorna una lista di post che contengono almeno una delle parole in words
    public List<Post> containing(List<String> words);
    
}
