package me.kcj.sec01;

import me.kcj.models.sec01.PersonOuterClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SimpleProtoDemo {

    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    public static void main(String[] args) {
        var person = PersonOuterClass.Person.newBuilder()
                .setName("Ken")
                .setAge(12)
                .build();
         log.info("{}", person);
    }

}
