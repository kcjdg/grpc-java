package me.kcj.test.sec06;

import me.kcj.models.sec06.Money;
import me.kcj.models.sec06.WithdrawRequest;
import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec03ServerStreamingClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec03ServerStreamingClientTest.class);


    @Test
    public void blockingClientWithdrawTest() {
        final var request = WithdrawRequest.newBuilder()
                .setAccountNumber(2)
                .setAmount(20)
                .build();
        var iterator = this.bankBlockingStub.withdraw(request);
        int count = 0;
        while (iterator.hasNext()) {
            log.info("received money : {}", iterator.next());
            count++;
        }
        Assertions.assertEquals(2, count);
    }

    @Test
    public void asyncClientWithdrawTest(){
        final var request = WithdrawRequest.newBuilder()
                .setAccountNumber(2)
                .setAmount(20)
                .build();

        var observer = ResponseObserver.<Money>create();
        this.bankStub.withdraw(request, observer);
        observer.await();
        Assertions.assertEquals(2, observer.getItems().size());
        Assertions.assertEquals(10, observer.getItems().getFirst().getAmount());
        Assertions.assertNull(observer.getThrowable());
    }
}
