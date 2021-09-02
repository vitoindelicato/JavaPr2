import java.util.*;

public class Post{
    /*
    OVERVIEW: tipo di dato che rappresenta un post di un social network, con tutti i suoi attributi:
    id = identificatore univoco
    author = oggetto di tipo user, indica l'account di chi ha creato il post
    text = contenuto del post
    timestamp = data e ora di publicazione del post
    likes = lista di oggetti di tipo user, contenente gli utenti che hanno messo like al post


    AF: istanceCounter <id,author,text,timestamp,{likes[0],....,likes[likes.length()-1]}>

    
    IR : id != null && id >= 0 && author != null && text != null 
    && timestamp != null && likes != null &&
    (forall i. 0 <= i < likes.size() => likes[i] != null)

    
*/
    private static int istanceCounter = 0;
    private Integer id;
    private MyUser author;
    private String text;
    private Date timestamp;
    private List<String> likes;


    //REQUIRES: author != null, 0<text.length()<=140
    //THROWS: IllegalArgumentException se 0<text.length()<=140, NullPointerException se author == null
    //MODIFIES: this
    //EFFECTS: crea l'oggetto post e ne inizializza i campi
    public Post(MyUser author, String text)throws IllegalArgumentException, NullPointerException{
        if(text.trim().isEmpty() || text.length()>140 || text.length()<= 0){
            throw new IllegalArgumentException("La lunghezza del post deve essere compresa tra gli 0 e i 140 caratteri!");
        }
        if(author == null){
            throw new NullPointerException("Autore del post nullo o non valido!");
        }
        this.id = istanceCounter;
        istanceCounter++;

        this.author = author; 
        this.text = text;
        this.timestamp = new Date();
        this.likes = new Vector<String>();
    }


    //EFFECTS: ritorna this.id
    public int getId(){
        return this.id;
    }

    //EFFECTS: ritorna l'username dell'autore del post
    public String getAuthorUsername(){
        return this.author.getUsername();
    }
    //EFFECTS: ritorna l'oggetto user, autore del post
    public MyUser getAuthor(){
        return this.author;
    }


    //EFFECTS: ritorna il testo del post
    public String getText(){
        return this.text;
    }

    //REQUIRES: text != null     0<text.length()<=140
    //THROWS: IllegalArgumentException se 0<text.length()<=140
    //MODIFIES: this.text, this.timestamp
    //EFFECTS: permette di modificare il testo di un post
    public void setText(String text){
        if(text.length() > 0 && text.length() <= 140){
            this.text = text;
            this.timestamp = new Date();
        }
        else{
            throw new IllegalArgumentException();
        }
    }

    //other functions

    //EFFECTS: ritorna la lista degli user che hanno messo like al post
    public List<String> getLikes(){
        return this.likes;
    }

    public String toString(){
        String message = ("[id: " + this.id  + "  Author: " + this.author.toString() + "  Text: " + this.text +"  Timestamp:  " + this.timestamp + "]\n");
        return message;
    }

}
