package me.kcj.test.sec06;

import com.google.protobuf.Empty;
import me.kcj.models.sec06.BalanceCheckRequest;
import me.kcj.sec01.SimpleProtoDemo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryBlockingClient extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(SimpleProtoDemo.class);

    @Test
    public void getBalanceTest(){
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        final var balance = this.bankBlockingStub.getAccountBalance(request);
        log.info("unary balance received: {}", balance);
        Assertions.assertEquals(100, balance.getBalance());
    }

    @Test
    public void allAccountTest(){
        var allAccounts = this.bankBlockingStub.getAllAccounts(Empty.getDefaultInstance());
        log.info("all accounts size : {}", allAccounts.getAccountsCount());
        Assertions.assertEquals(10, allAccounts.getAccountsCount());

    }

}
