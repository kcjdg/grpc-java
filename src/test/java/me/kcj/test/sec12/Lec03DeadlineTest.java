package me.kcj.test.sec12;

import io.grpc.ClientInterceptor;
import io.grpc.Deadline;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import me.kcj.models.sec12.AccountBalance;
import me.kcj.models.sec12.BalanceCheckRequest;
import me.kcj.models.sec12.Money;
import me.kcj.models.sec12.WithdrawRequest;
import me.kcj.test.common.ResponseObserver;
import me.kcj.test.sec12.interceptors.DeadlineInterceptor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Lec03DeadlineTest extends AbstractInterceptorTest {


    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(new DeadlineInterceptor(Duration.ofSeconds(2)));
    }

    @Test
    public void blockingDeadlineTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub
                .getAccountBalance(request);


    }


    @Test
    public void overrideInterceptorDemo() {
        var observer = ResponseObserver.<Money>create();
        var request = WithdrawRequest.newBuilder()
                .setAccountNumber(1)
                .setAmount(50)
                .build();
        this.bankStub
                .withDeadline(Deadline.after(6, TimeUnit.SECONDS))
                .withdraw(request, observer);
        observer.await();
    }
}
