import java.util.*;

public class Tests {

    public static void main(String[] args)throws Exception {

        MicroBlog network = new MicroBlog();

        //Inizializzo fuori dal try per evitare unresolved variable error per il contesto
        MyUser usr = null;
        Post pst = null;

        MyUser usr2 = null;
        Post pst2 = null;

        MyUser usr3 = null;
        Post pst3 = null;

        MyUser usr4 = null;
        Post pst4 = null;

        MyUser usr6 = null;

        String username = null;

        MyUser usr5 = null;
        Post pst5 = null;

        try {
            
            usr = new MyUser("Giorgio", network);
            pst = new Post(usr, "Ciao! Sono Giorgio!");
            network.registerPost(pst);

            usr2 = new MyUser("Alessandro", network);
            pst2 = new Post(usr2, "Ciao! Sono Alessandro");
            network.registerPost(pst2);

            usr3 = new MyUser("Maria", network);
            pst3 = new Post(usr3, "Ciao! sono Maria");
            network.registerPost(pst3);

            usr4 = new MyUser("Angelica", network);
            pst4 = new Post(usr4, "Ciao! Sono Angelica");
            network.registerPost(pst4);

            usr6 = new MyUser("Tommaso", network);
            System.out.println(network.getSn() + " ---> Printing network.sn1\n");


            usr5 = new MyUser("", network); //Dovrebbe tirare eccezione InvalidUsername
            pst5 = new Post(usr5, "Io sono nessuno");//questo post non verrà mai creato
        }
        catch (Exception e){
            System.out.println(e.toString() + " ---> Test Ecxception InvalidUsername passed!\n");
        }
        
        try{
            usr5 = new MyUser(username, network); //Dovrebbe tirare eccezione NullPointerException

        }
        catch (Exception e){
            System.out.println(e.toString() + " ---> Test Ecxception NullPointerException passed!\n");
        }

         try{
            network.follow(usr, usr); //mi seguo da solo
        }
        catch (Exception e){
            System.out.println(e.toString() + " ---> Test Ecxception IllegalAction passed!\n");
        }

        network.follow(usr, usr2);//Giorgio segue Alessandro
        network.follow(usr, usr3);//Giorgio segue Maria
        network.follow(usr, usr4);//Giorgio segue Angelica
        System.out.println(network.getSn() + " ---> Printing network.sn2\n");


        try{
            network.follow(usr, usr3);
        }
        catch (Exception e){
            System.out.println(e.toString() + " ---> Test Ecxception IllegalAction passed!\n");
        }


        //System.out.println(MicroBlog.getSn() + "\n");

        

        //System.out.println("Usr2.getFollowMe: "+ usr2.getFollowMe());
        network.placeLike(pst2, usr);

        network.placeLike(pst2, usr3); //cosi Maria segue Alessandro per la regola dell'autofollow
        //System.out.println("Us2r.getFollowMe: "+ usr2.getFollowMe());

        System.out.println(network.getSn() + " ---> Printing network.sn3\n");


        List<Post> lst = new ArrayList<Post>();
        lst.add(pst);
        lst.add(pst2);
        lst.add(pst3);
        lst.add(pst4);
        //lst.add(pst5);


        //System.out.println("Printing guess followers" + network.guessFollowers(lst));

        //Costruisco manualmente l'oggetto che guessFollowers dovrebbe restituire, e lo comparo con il risultato della funzione
        Map<String, Set<String>> comparator = new HashMap<String, Set<String>>();
        comparator.put(usr.getUsername(), MyUser.getUsernames(usr.getIFollow()));
        comparator.put(usr2.getUsername(), MyUser.getUsernames(usr2.getIFollow()));
        comparator.put(usr4.getUsername(), MyUser.getUsernames(usr4.getIFollow()));
        comparator.put(usr3.getUsername(), MyUser.getUsernames(usr3.getIFollow()));
        
        assert comparator.equals(network.guessFollowers(lst)) == true : "Guess Follower assert! ---> Guess follower Test fallito";
        System.out.println("Guess Follower assert superato! ---> Guess follower Test passato\n"); //se non supera l'assert questo messaggio non viene printato



        Map<String, Set<String>> testMap = new HashMap<String, Set<String>>();
        testMap.put(usr.getUsername(), MyUser.getUsernames(usr.getFollowMe()));
        testMap.put(usr2.getUsername(), MyUser.getUsernames(usr2.getFollowMe()));
        testMap.put(usr3.getUsername(), MyUser.getUsernames(usr3.getFollowMe()));

        List<String> list_comparator = new ArrayList<String>();

        list_comparator.add("Alessandro"); //Alessandro ha 2 followers
        list_comparator.add("Maria"); //Maria ne ha 1
        list_comparator.add("Giorgio"); // Giorgio non ha followers

        //System.out.println(list_comparator);
        //System.out.println("Influencers():  " + network.influencers(testMap));


        assert list_comparator.equals(network.influencers(testMap)) == true : "Influencers assert! ---> Influencers Test fallito";
        System.out.println("Influencers assert superato! ---> Influencers Test passato\n");


        Set<String> set_comparator = new HashSet<String>();
        set_comparator.add("Giorgio");
        set_comparator.add("Alessandro");
        set_comparator.add("Angelica");
        set_comparator.add("Maria");
        //nel comparatore non c'è Tommaso che non ha scritto nessun post
        //System.out.println(set_comparator);
        //System.out.println("Get Mentioned User:" + network.getMentionedUsers());
        assert set_comparator.equals(network.getMentionedUsers()) == true : "GetMentionedUsers() assert! ---> GetMentionedUsers() Test fallito";
        System.out.println("GetMentionedUsers() assert superato! ---> GetMentionedUsers() Test passato\n");

        List<Post> PostList = new ArrayList<Post>();
        PostList.add(pst);
        PostList.add(pst2);

        set_comparator.remove("Angelica"); //Questi due utenti non hanno scritto post appartenenti a PostList
        set_comparator.remove("Maria");//quindi non devono essere nel risultato

        //System.out.println("Get Mentioned User:" + network.getMentionedUsers(PostList));
        //System.out.println(set_comparator);

        assert set_comparator.equals(network.getMentionedUsers(PostList)) == true : "GetMentionedUsers(PostList) assert! ---> GetMentionedUsers(PostList) Test fallito";
        System.out.println("GetMentionedUsers(PostList) assert superato! ---> GetMentionedUsers()PostList Test passato\n");


        List<Post> list_comparator2 = new ArrayList<Post>();
        for(Post iterator : network.getPostList()){
            if (iterator.getAuthorUsername() == "Alessandro"){
                list_comparator2.add(iterator);
            }
        }

        assert list_comparator2.equals(network.writtenBy("Alessandro")) == true : "writtenBy('Alessandro') assert! ---> writtenBy('Alessandro') Test fallito";
        System.out.println("writtenBy('Alessandro') assert superato! ---> writtenBy('Alessandro') Test passato\n");

        //System.out.println("Written By:\t" + network.writtenBy("Alessandro")); //funziona in tutti e due i casi
        //System.out.println("Written By:\t" + network.writtenBy(usr2.getUsername()));
        //System.out.println("Written By:\t" + network.writtenBy(PostList, "Angelica")); //da lista vuota come risultato

        list_comparator2.clear(); //Lista vuota come risultato perche angelica non ha scritto nessun post in postList
        assert list_comparator2.equals(network.writtenBy(PostList, "Angelica")) == true : "writtenBy(PostList, 'Angelica') assert! ---> writtenBy(PostList, 'Angelica') Test fallito";
        System.out.println("writtenBy(PostList, 'Angelica') assert superato! ---> writtenBy(PostList, 'Angelica') Test passato\n");


        list_comparator2.clear();
        List<String> words = new ArrayList<String>();
        words.add("Alessandro");
        words.add("Giorgio");
        words.add("Michele");
        

        //System.out.println("Containing:\t" + network.containing(words));
        list_comparator2.add(pst); //Aggiungo il post contenente la parola alessandro
        list_comparator2.add(pst2); //Aggiungo il post contenente la parola giorgio
        //poi basta perche nel risultato della funzione non dovrebbe esserci nessun post che contiene la parola 'michele'


        assert list_comparator2.equals(network.containing(words)) == true : "Containing(words) assert! ---> Containing(words) Test fallito";
        System.out.println("Containing(words) assert superato! ---> Containing(words) Test passato\n");
        
       
    }    
}
