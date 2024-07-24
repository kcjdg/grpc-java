package me.kcj.sec03;

import me.kcj.models.sec03.Person;
import me.kcj.sec01.SimpleProtoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Lec02Serialization {
    private static final Logger log = LoggerFactory.getLogger(Lec02Serialization.class);
    public static final Path PATH = Path.of("person.out");

    public static void main(String[] args) throws IOException{

        final var person = Person.newBuilder()
                .setLastName("Sam")
                .setAge(32)
                .setEmail("someemail@gmail.com")
                .setEmployed(false)
                .setSalary(100_000_00.22)
//                .setBankAccountNumber(123456789012L)   //It will not print null, everything is optiona;
                .setBalance(-10_000)
                .build();

        serialize(person);
        log.info("{}", deserialize());
        log.info("equals {}", person.equals(deserialize()));
        log.info("bytes length {}", person.toByteArray().length);
    }

    public static void serialize(Person person) throws IOException {
        try (var stream = Files.newOutputStream(PATH)){
            person.writeTo(stream);
        }
    }

    public static Person deserialize() throws IOException{
        try (var stream = Files.newInputStream(PATH)){
            return Person.parseFrom(stream);
        }
    }
}
