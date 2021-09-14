import org.junit.Assert;
import org.junit.Test;
import tech.note.model.MessageBuilder;

public class MessageBuilderTest {

    @Test
    public void testMessageBuilder(){
        String message = "This is a line.";
        MessageBuilder bob = new MessageBuilder();

        bob.add(message);
        // Needs to add the \n chars as the Message Builder will directly add it when using the add method.
        Assert.assertEquals(message + "\n", bob.getMessage());

        bob.reset();
        // the length will be equals to 1 when reset.
        Assert.assertEquals(1, bob.getMessage().length());
        Assert.assertTrue(bob.isEmpty());
    }
}
