package me.kcj.test.sec11;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.kcj.models.sec06.BalanceCheckRequest;
import me.kcj.models.sec06.BankServiceGrpc;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec07LoadBalancingDemoTest {
    private static final Logger log = LoggerFactory.getLogger(Lec06KeepAliveDemoTest.class);

    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected ManagedChannel channel;


    @BeforeAll
    public void setup() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }


    @Test
    public void loadBalancingDemo() {
        for (int i = 1; i <=10; i++) {
            final var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(i)
                    .build();
            var response = this.bankBlockingStub.getAccountBalance(request);
            log.info("{}", response);
        }
    }


    @AfterAll
    public void stopChannel() throws InterruptedException {
        this.channel.shutdownNow();
    }
}
