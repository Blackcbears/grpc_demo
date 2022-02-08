package com.example.grpc_demo.service.impl;

import com.example.grpc_lib.HelloReply;
import com.example.grpc_lib.HelloRequest;
import com.example.grpc_lib.SimpleGrpc;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

/**
 * <br>
 *
 * @author cuijing
 * @date 2022-02-08 11:27
 */
@GrpcService
public class MyServiceImpl extends SimpleGrpc.SimpleImplBase {

    @Override
    public void sayHello(HelloRequest request, StreamObserver<HelloReply> responseObserver) {
        HelloReply reply = HelloReply.newBuilder()
                .setMessage("Hello ==> " + request.getName())
                .build();
        responseObserver.onNext(reply);
        responseObserver.onCompleted();
    }

}
