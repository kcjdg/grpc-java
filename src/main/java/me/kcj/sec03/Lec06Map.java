package me.kcj.sec03;

import me.kcj.models.sec03.BodyStyle;
import me.kcj.models.sec03.Car;
import me.kcj.models.sec03.Dealer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class Lec06Map {
    private static final Logger log = LoggerFactory.getLogger(Lec06Map.class);

    public static void main(String[] args) {
        final var car1 = Car.newBuilder()
                .setMake("Honda")
                .setModel("Civic")
                .setYear(2000)
                .setBodyStyle(BodyStyle.COUPE)
                .build();

        final var car2 = Car.newBuilder()
                .setMake("Honda")
                .setModel("accord")
                .setYear(2002)
                .setBodyStyle(BodyStyle.SEDAN)

                .build();

        final var dealer = Dealer.newBuilder()
                .putAllInventory(Map.of(car1.getYear(), car1, car2.getYear(), car2));

        log.info("{}", dealer);
        log.info("2002 ? : {}"  , dealer.containsInventory(2002));
        log.info("2003 ? : {}"  , dealer.containsInventory(2003));
        log.info("2003 ? : {}"  , dealer.getInventoryOrThrow(2002).getModel());
        log.info("2003 ? : {}"  , dealer.getInventoryOrThrow(2002).getBodyStyle());

    }

}
