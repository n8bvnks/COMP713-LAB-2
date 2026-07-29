package nz.ac.aut.comp713.lifecycle;

//Application layer - called by servlet

public class GreetingService {


    public String createMessage(String name){

        return "Hello, " + name;

    }


    public int calculateLength(String name){

        return name.length();

    }

}
