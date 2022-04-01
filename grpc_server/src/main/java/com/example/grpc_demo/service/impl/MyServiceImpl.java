package com.example.grpc_demo.service.impl;

import com.example.grpc_lib.HelloReply;
import com.example.grpc_lib.HelloRequest;
import com.example.grpc_lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.server.service.GrpcService;
/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-08 11:27
 */
@GrpcService
@Slf4j
public class MyServiceImpl extends SimpleGrpc.SimpleImplBase {

    private StreamObserver<HelloReply> responseOb;
    @Override
    public StreamObserver<HelloRequest> sayHello(
            StreamObserver<HelloReply> responseObserver) {
        this.responseOb=responseObserver;

        return new StreamObserver<>() {
            @Override
            public void onNext(HelloRequest requestMessage) {
                log.info("[收到客户端消息]: " + requestMessage.getName());
            }

            @Override
            public void onError(Throwable throwable) {
                log.error(throwable.fillInStackTrace().toString());
                throwable.fillInStackTrace();
            }

            @Override
            public void onCompleted() {
                log.info("接受完成！");
                responseObserver.onNext(HelloReply.newBuilder().setMessage("hello client ,I'm Java grpc Server,your message '").build());
                responseObserver.onCompleted();
            }
        };
    }
    public StreamObserver<HelloReply> getResponseOb() {
        return responseOb;
    }

}
