public class InvalidUsername extends Exception{
     public InvalidUsername(String message)throws NullPointerException{
        super(message, null);
        if(message == null){throw new NullPointerException();}
    }
}
