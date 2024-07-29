package me.kcj.test.sec10;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import io.grpc.protobuf.ProtoUtils;
import me.kcj.models.sec10.AccountBalance;
import me.kcj.models.sec10.BalanceCheckRequest;
import me.kcj.models.sec10.ErrorMessage;
import me.kcj.models.sec10.ValidationCode;
import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec01UnaryInputValidationTest extends AbstractTest {
    private static final Logger log = LoggerFactory.getLogger(Lec01UnaryInputValidationTest.class);


    @Test
    public void blockingInputValidationTest() {
        var ex = Assertions.assertThrows(StatusRuntimeException.class, () -> {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(11)
                    .build();
            this.bankBlockingStub.getAccountBalance(request);
        });

        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(ex));

    }

    @Test
    public void asyncInputValidationTest() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(11)
                .build();
        var observer = ResponseObserver.<AccountBalance>create();
        this.bankStub.getAccountBalance(request,observer);
        observer.await();

        Assertions.assertTrue(observer.getItems().isEmpty());
        Assertions.assertNotNull(observer.getThrowable());
        Assertions.assertEquals(ValidationCode.INVALID_ACCOUNT, getValidationCode(observer.getThrowable()));

    }
}
