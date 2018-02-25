package jarekjal.jtm;

public class Message {
    public String command;
    public String[] params;

    public Message(String com, String[] par){
        command = com;
        params = par;

    }
}
