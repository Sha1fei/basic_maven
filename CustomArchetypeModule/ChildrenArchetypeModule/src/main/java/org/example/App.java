package org.example;


public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Children archetype called!" );
        TestThirdPartyDependency testThirdPartyDependency = new TestThirdPartyDependency();
        testThirdPartyDependency.call();
    }
}
