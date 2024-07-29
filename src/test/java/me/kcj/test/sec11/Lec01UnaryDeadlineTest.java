package me.kcj.test.sec11;

import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import me.kcj.models.sec11.AccountBalance;
import me.kcj.models.sec11.BalanceCheckRequest;
import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec01UnaryDeadlineTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryDeadlineTest.class);


    @Test
    public void blockingDeadlineTest(){
        var ex = Assertions.assertThrows(StatusRuntimeException.class, ()->{
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(1)
                    .build();
            var response = this.bankBlockingStub
                    .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                    .getAccountBalance(request);
            log.info("{}", response);
        });
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, ex.getStatus().getCode());

    }


    @Test
    public void asyncInputValidationTest() {
        var request =BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.bankStub
                .withDeadline(Deadline.after(2, TimeUnit.SECONDS))
                .getAccountBalance(request,observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(Status.Code.DEADLINE_EXCEEDED, ((StatusRuntimeException) observer.getThrowable()).getStatus().getCode()) ;
    }

}
