package Reflex;

//import Reflex.AutoInjectable;

//import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

public class Injector
{
    FileReader fileReader = null;
    Properties properties = new Properties();
    public Injector(String name){
        try{
            fileReader = new FileReader(name);
            properties.load(fileReader);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public <T> T inject(T obj) throws InstantiationException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException
    {
        Class objClass=obj.getClass();
        Field[] allFields = objClass.getDeclaredFields();
        for(Field f:allFields)
        {
            Annotation fa = f.getDeclaredAnnotation(AutoInjectable.class);
            if(fa != null)
            {
                f.setAccessible(true);
                Class aClass=null;
                try {
                    String t = (String) properties.get(f.getType().getTypeName());
                    aClass = Class.forName(t);
                }
                catch (ClassNotFoundException e){
                    e.printStackTrace();
                }

                f.set(obj,aClass.newInstance());

            }
        }
        return obj;
    }
}
