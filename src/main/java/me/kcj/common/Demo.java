package me.kcj.common;

import me.kcj.sec06.BankService;
import me.kcj.sec06.TransferService;
import me.kcj.sec07.FlowControlService;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(new FlowControlService())
                .start()
                .await();
    }
}
