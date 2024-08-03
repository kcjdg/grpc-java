package me.kcj.common;

import me.kcj.sec06.TransferService;
import me.kcj.sec07.FlowControlService;
import me.kcj.sec08.GuessNumberService;
import me.kcj.sec06.BankService;
import me.kcj.sec12.interceptors.ApiKeyValidationInterceptor;

public class Demo {
    public static void main(String[] args) {
        GrpcServer.create(6565, serverBuilder -> {
            serverBuilder.addService(new me.kcj.sec12.BankService())
                    .intercept(new ApiKeyValidationInterceptor());
        }).start().await();
    }


//    private static class BankInstance1{
//        public static void main(String[] args) {
//            GrpcServer.create(6565, new BankService())
//                    .start()
//                    .await();
//        }
//    }
//
//    private static class BankInstance2{
//        public static void main(String[] args) {
//            GrpcServer.create(7575, new BankService())
//                    .start()
//                    .await();
//        }
//    }
}
