package me.kcj.sec03;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.InvalidProtocolBufferException;
import me.kcj.models.sec03.Person;
import me.kcj.sec01.SimpleProtoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03PerformanceTest {
    private static final Logger log = LoggerFactory.getLogger(Lec03PerformanceTest.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        final var protoPerson = Person.newBuilder()
                .setLastName("Sam")
                .setAge(32)
                .setEmail("someemail@gmail.com")
                .setEmployed(false)
                .setSalary(100_000_00.22)
                .setBankAccountNumber(123456789012L)  
                .setBalance(-10_000)
                .build();
        final var jsonPerson = new JsonPerson("Sam", 32, "someemail@gmail.com", false, 100_000_00.22, 123456789012L, -10_000);

        json(jsonPerson);
        proto(protoPerson);

//        for (int i =0; i< 5; i++){
//            runTest("json", ()-> json(jsonPerson));
//            runTest("proto", ()-> proto(protoPerson));
//        }

    }


    private static void proto(Person person){
        try {
            var bytes = person.toByteArray();
            log.info("proto bytes length: {}", bytes.length);
            Person.parseFrom(bytes);
        } catch (InvalidProtocolBufferException e) {
            throw new RuntimeException(e);
        }
    }

    private static void json(JsonPerson person){
        final byte[] bytes;
        try {
            bytes = mapper.writeValueAsBytes(person);
            log.info("json bytes length: {}", bytes.length);
           mapper.readValue(bytes, JsonPerson.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void runTest(String testName, Runnable runnable){
        var start = System.currentTimeMillis();
        for (int i=0; i< 5_000_000; i++) {
            runnable.run();
        }
        var end = System.currentTimeMillis();
        log.info("time take for {} - {} ms", testName, (end - start));

    }
}
