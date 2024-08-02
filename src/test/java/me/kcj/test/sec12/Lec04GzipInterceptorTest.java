package me.kcj.test.sec12;

import io.grpc.ClientInterceptor;
import me.kcj.models.sec12.BalanceCheckRequest;
import me.kcj.test.sec12.interceptors.GzipRequestInterceptor;
import org.junit.jupiter.api.Test;

import java.util.List;

public class Lec04GzipInterceptorTest extends AbstractInterceptorTest {


    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new GzipRequestInterceptor());
    }

    @Test
    public void gzipDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub.getAccountBalance(request);

    }
}
