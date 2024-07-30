package me.kcj.test.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.Deadline;
import me.kcj.common.GrpcServer;
import me.kcj.models.sec11.BankServiceGrpc;
import me.kcj.models.sec11.WithdrawRequest;
import me.kcj.sec11.DeadlineBankService;
import me.kcj.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class Lec03WaitForReadyTest extends AbstractChannelTest {
    private static final Logger log = LoggerFactory.getLogger(Lec03WaitForReadyTest.class);

    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;


    @BeforeAll
    public void setup() {

        Runnable runnable = ()->{
            Uninterruptibles.sleepUninterruptibly(5, TimeUnit.SECONDS);
            this.grpcServer.start();
        };
        Thread.ofVirtual().start(runnable);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }

    @Test
    public void blockingDeadlineTest() {
        log.info("sending the request");
        var request = WithdrawRequest.newBuilder().setAccountNumber(1).setAmount(50).build();
        var iterator = this.bankBlockingStub
                .withWaitForReady()
                .withDeadline(Deadline.after(8,TimeUnit.SECONDS))
                .withdraw(request);

        while (iterator.hasNext()) {
            log.info("{}", iterator.next());
        }

    }

}
