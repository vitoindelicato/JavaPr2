public class IllegalAction extends Exception{
    public IllegalAction(String message)throws NullPointerException{
        super(message, null);
        if(message == null){throw new NullPointerException();}
    }
    
}
