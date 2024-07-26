package me.kcj.test.sec06;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import me.kcj.models.sec06.AccountBalance;
import me.kcj.models.sec06.AllAccountsResponse;
import me.kcj.models.sec06.BalanceCheckRequest;

import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class Lec02UnaryASyncClientTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec02UnaryASyncClientTest.class);

    @Test
    public void getBalanceTest() throws InterruptedException {
        var request = BalanceCheckRequest.newBuilder().setAccountNumber(1).build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.stub.getAccountBalance(request, observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(100, observer.getItems().getFirst().getBalance());
        Assertions.assertNull(observer.getThrowable());
    }

    @Test
    public void allAccountsTest(){
        final var observer = ResponseObserver.<AllAccountsResponse>create();
        this.stub.getAllAccounts(Empty.getDefaultInstance(), observer);
        observer.await();
        Assertions.assertEquals(1, observer.getItems().size());
        Assertions.assertEquals(10, observer.getItems().getFirst().getAccountsCount());
        Assertions.assertNull(observer.getThrowable());

    }

}
