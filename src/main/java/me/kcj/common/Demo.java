package me.kcj.common;

import me.kcj.sec06.BankService;
import me.kcj.sec06.TransferService;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(new BankService(), new TransferService())
                .start()
                .await();
    }
}
