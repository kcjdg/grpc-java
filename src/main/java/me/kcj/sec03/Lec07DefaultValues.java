package me.kcj.sec03;

import me.kcj.models.sec03.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Lec07DefaultValues {
    private static final Logger log = LoggerFactory.getLogger(Lec07DefaultValues.class);

    public static void main(String[] args) {
        final var school = School.newBuilder()
                .setAddress(Address.newBuilder().setCity("Atlanta").build())
                .build();

        log.info("{}", school.getId());
        log.info("{}", school.getName());
        log.info("{}", school.getAddress().getCity()); //print an empty instead of null

        log.info("is default? : {}", school.getAddress().equals(Address.getDefaultInstance()));
        //has
        log.info("has address? {}", school.hasAddress());

        final var lib = Library.newBuilder().build();
        log.info("{}", lib.getBooksList());

        final var dealer = Dealer.newBuilder().build();
        log.info("{}", dealer.getInventoryMap());


        //enum
        final var car = Car.newBuilder().build();
        log.info("{}", car.getBodyStyle());


    }

}
