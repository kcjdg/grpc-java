package me.kcj.sec06;

import io.grpc.stub.StreamObserver;
import me.kcj.models.sec06.TransferRequest;
import me.kcj.models.sec06.TransferResponse;
import me.kcj.models.sec06.TransferServiceGrpc;
import me.kcj.sec06.requesthandlers.TransferRequestHandler;

public class TransferService extends TransferServiceGrpc.TransferServiceImplBase {


    @Override
    public StreamObserver<TransferRequest> transfer(StreamObserver<TransferResponse> responseObserver) {
        return new TransferRequestHandler(responseObserver);
    }
}
