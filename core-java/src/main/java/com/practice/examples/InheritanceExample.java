package com.practice.examples;

/**
 * Created by Intel on 6/28/2015.
 */
public class InheritanceExample {

    public static void main(String[] args) {

        A a = new A();
        System.out.println(a.a);
        a.message();
//        a.dummy();

        A aref;

        System.out.println("\nNow with reference");
        aref = new B();
//        aref.dummy();
        System.out.println(aref.a);
        aref.message();

        System.out.println("\n" +
                "B object");
        B b = new B();
        System.out.println(b.a);
        b.message();
        b.dummy();
    }
}

class A{

    int a = 10;

    public void message(){
        System.out.println("a");
    }

}

class B extends A{

    int a = 20;

    @Override
    public void message() {
        System.out.println("b");
    }

    public void dummy(){
        System.out.println("dummy");
    }
}
