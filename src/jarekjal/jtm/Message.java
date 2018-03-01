package jarekjal.jtm;

public class Message {
    public String command;
    public Object[] params;

    public Message(String com, Object[] par){
        command = com;
        params = par;

    }
}
