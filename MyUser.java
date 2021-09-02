import java.util.*;

public class MyUser implements User{

    private String username;
    private List<String> iFollow; 
    private List<String> followMe; 

    /*
    OVERVIEW: tipo di dato che rappresenta un user di un social network, con tutti i suoi attributi:
    userList = Lista statica che memorizza TUTTI gli utenti creati

    username = username idenitificativo dell'utente
    iFollow = struttura di supporto che memorizza tutti gli utenti che this segue
    followMe = struttura di supporto che memorizza tutti gli utenti che seguono this


    AF: <username,{iFollow[0],....,iFollow[iFollow.length()-1]}
                  {followMe[0],....,followMe[followMe.length()-1]}
                  
        >
        
    
    IR : username != null && iFollow != null && followMe != null 
    
    (forall i. 0 <= i < iFollow.size() => iFollow[i] != null)
    (forall i. 0 <= i < followMe.size() => followMe[i] != null)
    

    
    */



    //REQUIRES: username != null
    //THROWS: InvalidUsername se il nome è vuoto, NullPointerException se è null
    //MODIFIES: this
    //EFFECTS: crea l'oggetto user e ne inizializza i campi
    public MyUser(String username, MicroBlog network)throws InvalidUsername, NullPointerException, IllegalArgumentException {
        if (username == null){
            throw new NullPointerException("Nome utente non valido!");
        }

        if(username.trim().isEmpty()){
            throw new InvalidUsername("Nome utente non valido!");
        }

        if (network.getSn().containsKey(username)){
            throw new InvalidUsername("Username già utilizzata\t utente non creato");
        }
        this.username = username;
        this.iFollow = new Vector<String>();
        this.followMe = new Vector<String>();
        network.addEntryMap(this.username);//aggiungo il nuovo utente alla lista struttura del social network

    }



    //getters

    public String getUsername(){
        return this.username;
    }

    public List<String> getFollowMe(){
        return this.followMe;
    }

    public List<String> getIFollow(){
        return this.iFollow;
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

    

}
