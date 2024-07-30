package me.kcj.test.sec11;

import com.google.common.util.concurrent.Uninterruptibles;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.kcj.models.sec06.*;
import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class Lec07LoadBalancingDemoTest {
    private static final Logger log = LoggerFactory.getLogger(Lec06KeepAliveDemoTest.class);

    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected BankServiceGrpc.BankServiceStub bankStub;
    protected ManagedChannel channel;


    @BeforeAll
    public void setup() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8585)
                .usePlaintext()
                .build();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
        this.bankStub = BankServiceGrpc.newStub(channel);

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

    @Test
    public void streamingLoadBalancingDemo(){
        var responseObserver = ResponseObserver.<AccountBalance>create();
        var requestObserver  = this.bankStub.deposit(responseObserver);

        //initial message - account number
        requestObserver.onNext(DepositRequest.newBuilder().setAccountNumber(5).build());
        IntStream.rangeClosed(1,10)
                .mapToObj(i-> Money.newBuilder().setAmount(10).build())
                .map(m-> DepositRequest.newBuilder().setMoney(m).build())
                .forEach(d-> {
                    Uninterruptibles.sleepUninterruptibly(1, TimeUnit.SECONDS);
                    requestObserver.onNext(d);
                });

        //notifying the server that we are done
        requestObserver.onCompleted();

        //at this point out response observer should receive a response
        responseObserver.await();
    }


    @AfterAll
    public void stopChannel() throws InterruptedException {
        this.channel.shutdownNow();
    }
}
