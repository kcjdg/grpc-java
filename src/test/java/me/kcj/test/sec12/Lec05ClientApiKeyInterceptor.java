package me.kcj.test.sec12;

import io.grpc.ClientInterceptor;
import io.grpc.Metadata;
import io.grpc.stub.MetadataUtils;
import me.kcj.common.GrpcServer;
import me.kcj.models.sec12.BalanceCheckRequest;
import me.kcj.sec12.BankService;
import me.kcj.sec12.Constants;
import me.kcj.sec12.interceptors.ApiKeyValidationInterceptor;
import me.kcj.sec12.interceptors.GzipResponseInterceptor;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Lec05ClientApiKeyInterceptor extends AbstractInterceptorTest {


    private static final Logger log = LoggerFactory.getLogger(Lec05ClientApiKeyInterceptor.class);

    @Override
    protected List<ClientInterceptor> getClientInterceptors() {
        return List.of(
                MetadataUtils.newAttachHeadersInterceptor(getApiKey())


        );
    }

    @Override
    protected GrpcServer createServer(){
        return GrpcServer.create(6565, serverBuilder -> {
            serverBuilder.addService(new BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        });
    }

    private Metadata getApiKey(){
        var metadata = new Metadata();
        metadata.put(Constants.API_KEY, "bank-client-secret");
        return metadata;
    }

    @Test
    public void clientApiKeyDemo() {
        var request = BalanceCheckRequest.newBuilder()
                .setAccountNumber(1)
                .build();
        var response = this.bankBlockingStub
                .getAccountBalance(request);

        log.info("{}", response);


    }

}
