package me.kcj.test.sec07;

import me.kcj.common.GrpcServer;
import me.kcj.models.sec07.FlowControlServiceGrpc;
import me.kcj.sec07.FlowControlService;
import me.kcj.test.common.AbstractChannelTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FlowControlTest extends AbstractChannelTest {

    private final GrpcServer server = GrpcServer.create(new FlowControlService());
    private FlowControlServiceGrpc.FlowControlServiceStub stub;

    @BeforeAll
    public void setup() {
        this.server.start();
        this.stub = FlowControlServiceGrpc.newStub(channel);
    }

    @Test
    public void flowControlDemo() {
        var responseServer = new ResponseHandler();
        var requestObserver = this.stub.getMessages(responseServer);
        responseServer.setRequestObserver(requestObserver);
        responseServer.start();
        responseServer.await();
    }


    @AfterAll
    public void stop() {
        this.server.stop();
    }
}
