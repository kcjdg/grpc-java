package me.kcj.sec03;

import me.kcj.models.sec03.Credentials;
import me.kcj.models.sec03.Email;
import me.kcj.models.sec03.Phone;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec08OneOf {
    private static final Logger log = LoggerFactory.getLogger(Lec08OneOf.class);

    public static void main(String[] args) {

        final var email = Email.newBuilder().setAddress("sam@gmail.com").setPassword("admin").build();
        final var  phone = Phone.newBuilder().setNumber(123456789).setCode(123).build();

        login(Credentials.newBuilder().setEmail(email).build());
        login(Credentials.newBuilder().setPhone(phone).build());
        login(Credentials.newBuilder().setEmail(email).setPhone(phone).build()); //either of. it will override the other
    }

    private static void login(Credentials credentials){
        switch (credentials.getLoginTypeCase()){
            case EMAIL -> log.info("email - {}", credentials.getEmail());
            case PHONE -> log.info("phone - {}", credentials.getPhone());
        }
    }
}
