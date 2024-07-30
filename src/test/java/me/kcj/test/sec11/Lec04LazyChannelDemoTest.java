package me.kcj.test.sec11;

import me.kcj.common.GrpcServer;
import me.kcj.models.sec11.BalanceCheckRequest;
import me.kcj.models.sec11.BankServiceGrpc;
import me.kcj.sec11.DeadlineBankService;
import me.kcj.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec04LazyChannelDemoTest extends AbstractChannelTest {
    private static final Logger log = LoggerFactory.getLogger(Lec04LazyChannelDemoTest.class);

    private final GrpcServer grpcServer = GrpcServer.create(new DeadlineBankService());
    private BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;


    @BeforeAll
    public void setup() {
        this.grpcServer.start();
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @Test
    public void lazyChannelDemo() {
        final var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        final var response = this.bankBlockingStub.getAccountBalance(request);
        log.info("{}", response);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
    }
}
