package org.example;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        for(int i = 0; i < args.length; i++) {
            System.out.println("ARGS: " + args[i]);
        }
        System.out.println("Properties: " + System.getProperties()); // в частности properties из POM
        System.out.println("ENV: " +  System.getenv("PATH"));
    }
}
