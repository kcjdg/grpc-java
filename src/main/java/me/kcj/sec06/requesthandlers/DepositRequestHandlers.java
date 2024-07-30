package me.kcj.sec06.requesthandlers;

import io.grpc.stub.StreamObserver;
import me.kcj.models.sec06.AccountBalance;
import me.kcj.models.sec06.DepositRequest;
import me.kcj.sec06.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DepositRequestHandlers implements StreamObserver<DepositRequest> {
    private static final Logger log = LoggerFactory.getLogger(DepositRequestHandlers.class);

    private final StreamObserver<AccountBalance> responseObserver;
    private int accountNumber;

    public DepositRequestHandlers(StreamObserver<AccountBalance> responseObserver) {
        this.responseObserver = responseObserver;
    }

    @Override
    public void onNext(DepositRequest depositRequest) {
        log.info("received deposit {}", depositRequest);
        switch (depositRequest.getRequestCase()) {
            case ACCOUNTNUMBER -> this.accountNumber = depositRequest.getAccountNumber();
            case MONEY -> AccountRepository.addAmount(this.accountNumber, depositRequest.getMoney().getAmount());
        }

    }

    @Override
    public void onError(Throwable throwable) {
        log.info("client error: {}", throwable);
    }

    @Override
    public void onCompleted() {
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(this.accountNumber)
                .setBalance(AccountRepository.getBalance(this.accountNumber))
                .build();
        this.responseObserver.onNext(accountBalance);
        this.responseObserver.onCompleted();
    }
}
