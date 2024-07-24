package me.kcj.sec03;

import me.kcj.models.sec03.Address;
import me.kcj.models.sec03.School;
import me.kcj.models.sec03.Student;
import me.kcj.sec01.SimpleProtoDemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04Composition {
    private static final Logger log = LoggerFactory.getLogger(Lec04Composition.class);

    public static void main(String[] args) {
        //create student
        final var address = Address.newBuilder()
                .setStreet("123 main st")
                .setCity("Quezon City")
                .setState("QC")
                .build();

        //create school
        final var student = Student.newBuilder()
                .setName("UP")
                .setAddress(address)
                .build();
        final var school = School.newBuilder()
                .setId(1)
                .setName("High School")
                .setAddress(address.toBuilder().setStreet("124 main str"))
                .build();

        log.info("school: {}", school);
        log.info("student: {}", student);

    }
}
