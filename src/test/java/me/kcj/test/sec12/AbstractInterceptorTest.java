package me.kcj.test.sec12;

import io.grpc.ClientInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import me.kcj.common.GrpcServer;
import me.kcj.models.sec12.BankServiceGrpc;
import me.kcj.sec12.BankService;
import me.kcj.sec12.interceptors.GzipResponseInterceptor;
import me.kcj.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

import java.util.List;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractInterceptorTest {
    private  GrpcServer grpcServer;
    protected ManagedChannel channel;
    protected BankServiceGrpc.BankServiceBlockingStub bankBlockingStub;
    protected BankServiceGrpc.BankServiceStub bankStub;

    protected abstract List<ClientInterceptor> getClientInterceptors();

    protected GrpcServer createServer(){
        return GrpcServer.create(6565, serverBuilder -> {
            serverBuilder.addService(new BankService())
                    .intercept(new GzipResponseInterceptor());
        });
    }

    @BeforeAll
    public void setup() {
        this.grpcServer  = createServer();
        this.grpcServer.start();
        this.channel = ManagedChannelBuilder.forAddress("localhost", 6565)
                .usePlaintext()
                .intercept(getClientInterceptors())
                .build();
        this.bankStub = BankServiceGrpc.newStub(channel);
        this.bankBlockingStub = BankServiceGrpc.newBlockingStub(channel);
    }

    @AfterAll
    public void stop() {
        this.grpcServer.stop();
        this.channel.shutdownNow();
    }
}
