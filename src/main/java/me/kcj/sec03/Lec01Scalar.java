package me.kcj.sec03;

import me.kcj.models.sec03.Person;
import me.kcj.sec01.SimpleProtoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Scalar {
    private static final Logger log = LoggerFactory.getLogger(Lec01Scalar.class);


    public static void main(String[] args) {

        final var person = Person.newBuilder()
                .setLastName("Sam")
                .setAge(32)
                .setEmail("someemail@gmail.com")
                .setEmployed(false)
                .setSalary(100_000_00.22)
//                .setBankAccountNumber(123456789012L)   //It will not print null, everything is optiona;
                .setBalance(-10_000)
                .build();
        log.info("{}", person);

    }
}
