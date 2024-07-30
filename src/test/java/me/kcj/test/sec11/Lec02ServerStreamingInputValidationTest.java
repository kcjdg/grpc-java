package me.kcj.test.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.Deadline;
import me.kcj.models.sec11.WithdrawRequest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec02ServerStreamingInputValidationTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec02ServerStreamingInputValidationTest.class);

    @Test
    public void blockingDeadlineTest() {
        try {
            var request = WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(50).build();
            var iterator = this.bankBlockingStub
                    .withDeadline(Deadline.after(3, TimeUnit.SECONDS))
                    .withdraw(request);

            while (iterator.hasNext()) {
                log.info("{}", iterator.next());
            }
        }catch (Exception e){
            log.info("error");
        }
        Uninterruptibles.sleepUninterruptibly(10, TimeUnit.SECONDS);
    }

}
