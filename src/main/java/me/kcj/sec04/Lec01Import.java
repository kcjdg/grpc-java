package me.kcj.sec04;


import me.kcj.models.common.Address;

import me.kcj.models.common.BodyStyle;
import me.kcj.models.common.Car;
import me.kcj.models.sec04.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01Import {

    private static final Logger log = LoggerFactory.getLogger(Lec01Import.class);

    public static void main(String[] args) {

        final var address = Address.newBuilder().setCity("Atlanta").build();
        final var car = Car.newBuilder().setBodyStyle(BodyStyle.COUPE).build();
        final var person = Person.newBuilder()
                .setName("Sam")
                .setAge(12)
                .setAddress(address)
                .setCar(car)
                .build();


        log.info("{}", person);

    }
}
