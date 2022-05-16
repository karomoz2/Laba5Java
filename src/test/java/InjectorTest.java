import Reflex.Injector;
import Reflex.SomeBean;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import static org.junit.Assert.*;

public class InjectorTest {

    private ByteArrayOutputStream output = new ByteArrayOutputStream();

    public void setUpStreams() {

        System.setOut(new PrintStream(output));
    }
    public void cleanUpStreams() {

        System.setOut(null);
    }



    //Для AC
    @Test
    public void inject() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        setUpStreams();
        SomeBean someBean =(new Injector("src/main/resources/config.properties")).inject(new SomeBean());
        someBean.foo();
        assertEquals("A"+System.lineSeparator()+"C"+System.lineSeparator(),output.toString());
        cleanUpStreams();

    }

    //Для BC
    @Test
    public void inject2() throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException{
        setUpStreams();
        SomeBean someBean2 =(new Injector("src/main/resources/config2.properties")).inject(new SomeBean());
        someBean2.foo();
        assertEquals("B"+System.lineSeparator()+"C"+System.lineSeparator(),output.toString());
        cleanUpStreams();
    }
}