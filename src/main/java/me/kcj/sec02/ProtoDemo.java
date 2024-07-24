package me.kcj.sec02;

import me.kcj.models.sec02.Person;
import me.kcj.models.sec02.PersonOuterClass;
import me.kcj.sec01.SimpleProtoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProtoDemo {
    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {
        final var person = createPerson();
        final var person2 = createPerson();


        //compare
        log.info("equals {}", person.equals(person2));
        log.info("== {}", person == person2);

        //mutable? No

        //create another instance with diff values
        final var person3 = person.toBuilder().setName("Mike").build();
        log.info("equals {}", person.equals(person3));
        log.info("== {}", person == person3);

        //null? will throw NullPointer use clear methods
        final var person4 = person.toBuilder().clearName().build();
        log.info("equals {}", person.equals(person4));
        log.info("== {}", person == person4);

    }


    private static Person createPerson(){
       return Person.newBuilder()
                .setName("Ken")
                .setAge(12)
                .build();
    }
}
