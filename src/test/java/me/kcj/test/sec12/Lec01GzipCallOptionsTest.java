package me.kcj.test.sec12;

import me.kcj.models.sec12.BalanceCheckRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01GzipCallOptionsTest extends AbstractTest{
    private static final Logger log = LoggerFactory.getLogger(Lec01GzipCallOptionsTest.class);



    @Test
    public void gzipDemo(){
        final var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);
        log.info("{}", response);

    }
}
