package me.kcj.sec06;

import com.google.protobuf.Empty;
import io.grpc.stub.StreamObserver;
import me.kcj.models.sec06.AccountBalance;
import me.kcj.models.sec06.AllAccountsResponse;
import me.kcj.models.sec06.BalanceCheckRequest;
import me.kcj.models.sec06.BankServiceGrpc;
import me.kcj.sec06.repository.AccountRepository;

import java.util.List;

public class BankService extends BankServiceGrpc.BankServiceImplBase {

    @Override
    public void getAccountBalance(BalanceCheckRequest request, StreamObserver<AccountBalance> responseObserver) {
        var accountNumber = request.getAccountNumber();
        var balance = AccountRepository.getBalance(accountNumber);
        var accountBalance = AccountBalance.newBuilder()
                .setAccountNumber(accountNumber)
                .setBalance(balance)
                .build();
        responseObserver.onNext(accountBalance);
        responseObserver.onCompleted();
    }

    @Override
    public void getAllAccounts(Empty request, StreamObserver<AllAccountsResponse> responseObserver) {
         var accountBalances = AccountRepository.getAllAccounts()
                .entrySet()
                .stream()
                .map(e -> AccountBalance.newBuilder()
                        .setAccountNumber(e.getKey())
                        .setBalance(e.getValue()).build()
                ).toList();

        final var response = AllAccountsResponse.newBuilder().addAllAccounts(accountBalances).build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
