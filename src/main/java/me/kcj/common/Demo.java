package me.kcj.common;

import me.kcj.sec06.BankService;
import me.kcj.sec06.TransferService;
import me.kcj.sec07.FlowControlService;
import me.kcj.sec08.GuessNumberService;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(new GuessNumberService())
                .start()
                .await();
    }
}
