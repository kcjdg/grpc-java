package me.kcj.test.sec12;

import io.grpc.CallCredentials;
import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import me.kcj.common.GrpcServer;
import me.kcj.models.sec12.BalanceCheckRequest;
import me.kcj.models.sec12.Money;
import me.kcj.models.sec12.WithdrawRequest;
import me.kcj.sec12.BankService;
import me.kcj.sec12.Constants;
import me.kcj.sec12.UserRoleBankService;
import me.kcj.sec12.interceptors.UserRoleInterceptor;
import me.kcj.sec12.interceptors.UserTokenInterceptor;
import me.kcj.test.common.ResponseObserver;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executor;

public class Lec07UserRoleContextTest extends AbstractInterceptorTest{
    private static final Logger log = LoggerFactory.getLogger(Lec07UserRoleContextTest.class);


    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return Collections.emptyList();
    }


    @Override
    protected GrpcServer createServer() {
        return GrpcServer.create(6565, serverBuilder -> {
            serverBuilder.addService(new UserRoleBankService())
                    .intercept(new UserRoleInterceptor());
        });
    }

    private Metadata getApiKey() {
        var metadata = new Metadata();
        metadata.put(Constants.API_KEY, "bank-client-secret");
        return metadata;
    }

    @RepeatedTest(5)
    public void unaryUserCredentialsDemo() {
        for (int i = 1; i < 5; i++) {
            var request = BalanceCheckRequest.newBuilder()
                    .setAccountNumber(i)
                    .build();
            var response = this.bankBlockingStub
                    .withCallCredentials(new UserSessionToken("user-token-"+ i))
                    .getAccountBalance(request);
            log.info("{}", response);
        }
    }



    private static class UserSessionToken extends CallCredentials {


        private static final String TOKEN_FORMAT = "%s %s";
        private final String jwt;

        public UserSessionToken(String jwt) {
            this.jwt = jwt;
        }

        @Override
        public void applyRequestMetadata(RequestInfo requestInfo,
                                         Executor executor,
                                         MetadataApplier metadataApplier) {
            executor.execute(() -> {
                var metadata = new Metadata();
                metadata.put(Constants.USER_TOKEN_KEY, TOKEN_FORMAT.formatted(Constants.BEARER, jwt));
                metadataApplier.apply(metadata);
            });
        }
    }
}
