package tech.note.model;

public class MessageBuilder {
    private final StringBuilder message = new StringBuilder();

    public MessageBuilder() {
    }

    /**
     * Will append the message you want to add to the StringBuilder and append a \n at the end.
     * @param line is the message you want to add.
     */
    public void add(String line){
        message.append(line).append("\n");
    }

    /**
     * Will print directly your message using println().
     */
    public void print(){
        System.out.println(message);
    }

    public void oneTimePrint() {
        System.out.println(message);
        reset();
    }

    /**
     * Will delete all chars in the StringBuilder.
     * The length will be 1, and not 0.
     */
    public void reset(){
        message.delete(0, message.length() == 0 ? 0 : message.length() - 1);
    }

    /**
     *
     * @return the StringBuilder as a String.
     */
    public String getMessage(){
        return message.toString();
    }


    /**
     *
     * @return the StringBuilder.
     */
    public StringBuilder getRawMessage(){
        return message;
    }

    /**
     * As a reset-ed MessageBuilder has a length of 1, we need to state equals or lesser to 1.
     * @return true if the length of the String of the StringBuilder is equal or lesser to 1.
     */
    public boolean isEmpty(){
        return message.toString().length() <= 1;
    }


}
