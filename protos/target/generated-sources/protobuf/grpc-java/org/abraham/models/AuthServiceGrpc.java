package org.abraham.models;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 * <pre>
 * ===================================
 *   SERVICE APIS
 * ===================================
 * </pre>
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.72.0)",
    comments = "Source: UserService.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class AuthServiceGrpc {

  private AuthServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "AuthService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.abraham.models.RegisterUserRequest,
      org.abraham.models.User> getRegisterUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "registerUser",
      requestType = org.abraham.models.RegisterUserRequest.class,
      responseType = org.abraham.models.User.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.RegisterUserRequest,
      org.abraham.models.User> getRegisterUserMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.RegisterUserRequest, org.abraham.models.User> getRegisterUserMethod;
    if ((getRegisterUserMethod = AuthServiceGrpc.getRegisterUserMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getRegisterUserMethod = AuthServiceGrpc.getRegisterUserMethod) == null) {
          AuthServiceGrpc.getRegisterUserMethod = getRegisterUserMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.RegisterUserRequest, org.abraham.models.User>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "registerUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.RegisterUserRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.User.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("registerUser"))
              .build();
        }
      }
    }
    return getRegisterUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.abraham.models.LoginRequest,
      org.abraham.models.LoginResponse> getLoginUserMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "loginUser",
      requestType = org.abraham.models.LoginRequest.class,
      responseType = org.abraham.models.LoginResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.LoginRequest,
      org.abraham.models.LoginResponse> getLoginUserMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.LoginRequest, org.abraham.models.LoginResponse> getLoginUserMethod;
    if ((getLoginUserMethod = AuthServiceGrpc.getLoginUserMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getLoginUserMethod = AuthServiceGrpc.getLoginUserMethod) == null) {
          AuthServiceGrpc.getLoginUserMethod = getLoginUserMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.LoginRequest, org.abraham.models.LoginResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "loginUser"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.LoginRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.LoginResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("loginUser"))
              .build();
        }
      }
    }
    return getLoginUserMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.abraham.models.VerifyMfaCodeRequest,
      org.abraham.models.VerifyMfaCodeResponse> getVerifyMfaCodeMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "verifyMfaCode",
      requestType = org.abraham.models.VerifyMfaCodeRequest.class,
      responseType = org.abraham.models.VerifyMfaCodeResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.VerifyMfaCodeRequest,
      org.abraham.models.VerifyMfaCodeResponse> getVerifyMfaCodeMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.VerifyMfaCodeRequest, org.abraham.models.VerifyMfaCodeResponse> getVerifyMfaCodeMethod;
    if ((getVerifyMfaCodeMethod = AuthServiceGrpc.getVerifyMfaCodeMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getVerifyMfaCodeMethod = AuthServiceGrpc.getVerifyMfaCodeMethod) == null) {
          AuthServiceGrpc.getVerifyMfaCodeMethod = getVerifyMfaCodeMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.VerifyMfaCodeRequest, org.abraham.models.VerifyMfaCodeResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "verifyMfaCode"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.VerifyMfaCodeRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.VerifyMfaCodeResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("verifyMfaCode"))
              .build();
        }
      }
    }
    return getVerifyMfaCodeMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.abraham.models.VerifyEmailTokenRequest,
      org.abraham.models.VerifyEmailTokenResponse> getVerifyEmailTokenMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "verifyEmailToken",
      requestType = org.abraham.models.VerifyEmailTokenRequest.class,
      responseType = org.abraham.models.VerifyEmailTokenResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.VerifyEmailTokenRequest,
      org.abraham.models.VerifyEmailTokenResponse> getVerifyEmailTokenMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.VerifyEmailTokenRequest, org.abraham.models.VerifyEmailTokenResponse> getVerifyEmailTokenMethod;
    if ((getVerifyEmailTokenMethod = AuthServiceGrpc.getVerifyEmailTokenMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getVerifyEmailTokenMethod = AuthServiceGrpc.getVerifyEmailTokenMethod) == null) {
          AuthServiceGrpc.getVerifyEmailTokenMethod = getVerifyEmailTokenMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.VerifyEmailTokenRequest, org.abraham.models.VerifyEmailTokenResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "verifyEmailToken"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.VerifyEmailTokenRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.VerifyEmailTokenResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("verifyEmailToken"))
              .build();
        }
      }
    }
    return getVerifyEmailTokenMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.abraham.models.ForgotPasswordRequest,
      org.abraham.models.ForgotPasswordResponse> getForgotPasswordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "forgotPassword",
      requestType = org.abraham.models.ForgotPasswordRequest.class,
      responseType = org.abraham.models.ForgotPasswordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.ForgotPasswordRequest,
      org.abraham.models.ForgotPasswordResponse> getForgotPasswordMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.ForgotPasswordRequest, org.abraham.models.ForgotPasswordResponse> getForgotPasswordMethod;
    if ((getForgotPasswordMethod = AuthServiceGrpc.getForgotPasswordMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getForgotPasswordMethod = AuthServiceGrpc.getForgotPasswordMethod) == null) {
          AuthServiceGrpc.getForgotPasswordMethod = getForgotPasswordMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.ForgotPasswordRequest, org.abraham.models.ForgotPasswordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "forgotPassword"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.ForgotPasswordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.ForgotPasswordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("forgotPassword"))
              .build();
        }
      }
    }
    return getForgotPasswordMethod;
  }

  private static volatile io.grpc.MethodDescriptor<org.abraham.models.ResetPasswordRequest,
      org.abraham.models.ResetPasswordResponse> getResetPasswordMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "resetPassword",
      requestType = org.abraham.models.ResetPasswordRequest.class,
      responseType = org.abraham.models.ResetPasswordResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.abraham.models.ResetPasswordRequest,
      org.abraham.models.ResetPasswordResponse> getResetPasswordMethod() {
    io.grpc.MethodDescriptor<org.abraham.models.ResetPasswordRequest, org.abraham.models.ResetPasswordResponse> getResetPasswordMethod;
    if ((getResetPasswordMethod = AuthServiceGrpc.getResetPasswordMethod) == null) {
      synchronized (AuthServiceGrpc.class) {
        if ((getResetPasswordMethod = AuthServiceGrpc.getResetPasswordMethod) == null) {
          AuthServiceGrpc.getResetPasswordMethod = getResetPasswordMethod =
              io.grpc.MethodDescriptor.<org.abraham.models.ResetPasswordRequest, org.abraham.models.ResetPasswordResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "resetPassword"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.ResetPasswordRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.abraham.models.ResetPasswordResponse.getDefaultInstance()))
              .setSchemaDescriptor(new AuthServiceMethodDescriptorSupplier("resetPassword"))
              .build();
        }
      }
    }
    return getResetPasswordMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static AuthServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceStub>() {
        @java.lang.Override
        public AuthServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceStub(channel, callOptions);
        }
      };
    return AuthServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports all types of calls on the service
   */
  public static AuthServiceBlockingV2Stub newBlockingV2Stub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingV2Stub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingV2Stub>() {
        @java.lang.Override
        public AuthServiceBlockingV2Stub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingV2Stub(channel, callOptions);
        }
      };
    return AuthServiceBlockingV2Stub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static AuthServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceBlockingStub>() {
        @java.lang.Override
        public AuthServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceBlockingStub(channel, callOptions);
        }
      };
    return AuthServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static AuthServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<AuthServiceFutureStub>() {
        @java.lang.Override
        public AuthServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new AuthServiceFutureStub(channel, callOptions);
        }
      };
    return AuthServiceFutureStub.newStub(factory, channel);
  }

  /**
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public interface AsyncService {

    /**
     */
    default void registerUser(org.abraham.models.RegisterUserRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.User> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRegisterUserMethod(), responseObserver);
    }

    /**
     */
    default void loginUser(org.abraham.models.LoginRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.LoginResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getLoginUserMethod(), responseObserver);
    }

    /**
     */
    default void verifyMfaCode(org.abraham.models.VerifyMfaCodeRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.VerifyMfaCodeResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyMfaCodeMethod(), responseObserver);
    }

    /**
     */
    default void verifyEmailToken(org.abraham.models.VerifyEmailTokenRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.VerifyEmailTokenResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getVerifyEmailTokenMethod(), responseObserver);
    }

    /**
     */
    default void forgotPassword(org.abraham.models.ForgotPasswordRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.ForgotPasswordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getForgotPasswordMethod(), responseObserver);
    }

    /**
     */
    default void resetPassword(org.abraham.models.ResetPasswordRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.ResetPasswordResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getResetPasswordMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service AuthService.
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public static abstract class AuthServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return AuthServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service AuthService.
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public static final class AuthServiceStub
      extends io.grpc.stub.AbstractAsyncStub<AuthServiceStub> {
    private AuthServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceStub(channel, callOptions);
    }

    /**
     */
    public void registerUser(org.abraham.models.RegisterUserRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.User> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void loginUser(org.abraham.models.LoginRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.LoginResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void verifyMfaCode(org.abraham.models.VerifyMfaCodeRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.VerifyMfaCodeResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyMfaCodeMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void verifyEmailToken(org.abraham.models.VerifyEmailTokenRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.VerifyEmailTokenResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getVerifyEmailTokenMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void forgotPassword(org.abraham.models.ForgotPasswordRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.ForgotPasswordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getForgotPasswordMethod(), getCallOptions()), request, responseObserver);
    }

    /**
     */
    public void resetPassword(org.abraham.models.ResetPasswordRequest request,
        io.grpc.stub.StreamObserver<org.abraham.models.ResetPasswordResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getResetPasswordMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service AuthService.
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public static final class AuthServiceBlockingV2Stub
      extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingV2Stub> {
    private AuthServiceBlockingV2Stub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingV2Stub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingV2Stub(channel, callOptions);
    }

    /**
     */
    public org.abraham.models.User registerUser(org.abraham.models.RegisterUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.LoginResponse loginUser(org.abraham.models.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.VerifyMfaCodeResponse verifyMfaCode(org.abraham.models.VerifyMfaCodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyMfaCodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.VerifyEmailTokenResponse verifyEmailToken(org.abraham.models.VerifyEmailTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyEmailTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.ForgotPasswordResponse forgotPassword(org.abraham.models.ForgotPasswordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getForgotPasswordMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.ResetPasswordResponse resetPassword(org.abraham.models.ResetPasswordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResetPasswordMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do limited synchronous rpc calls to service AuthService.
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public static final class AuthServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<AuthServiceBlockingStub> {
    private AuthServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public org.abraham.models.User registerUser(org.abraham.models.RegisterUserRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRegisterUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.LoginResponse loginUser(org.abraham.models.LoginRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getLoginUserMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.VerifyMfaCodeResponse verifyMfaCode(org.abraham.models.VerifyMfaCodeRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyMfaCodeMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.VerifyEmailTokenResponse verifyEmailToken(org.abraham.models.VerifyEmailTokenRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getVerifyEmailTokenMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.ForgotPasswordResponse forgotPassword(org.abraham.models.ForgotPasswordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getForgotPasswordMethod(), getCallOptions(), request);
    }

    /**
     */
    public org.abraham.models.ResetPasswordResponse resetPassword(org.abraham.models.ResetPasswordRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getResetPasswordMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service AuthService.
   * <pre>
   * ===================================
   *   SERVICE APIS
   * ===================================
   * </pre>
   */
  public static final class AuthServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<AuthServiceFutureStub> {
    private AuthServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected AuthServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new AuthServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.User> registerUser(
        org.abraham.models.RegisterUserRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRegisterUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.LoginResponse> loginUser(
        org.abraham.models.LoginRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getLoginUserMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.VerifyMfaCodeResponse> verifyMfaCode(
        org.abraham.models.VerifyMfaCodeRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyMfaCodeMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.VerifyEmailTokenResponse> verifyEmailToken(
        org.abraham.models.VerifyEmailTokenRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getVerifyEmailTokenMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.ForgotPasswordResponse> forgotPassword(
        org.abraham.models.ForgotPasswordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getForgotPasswordMethod(), getCallOptions()), request);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<org.abraham.models.ResetPasswordResponse> resetPassword(
        org.abraham.models.ResetPasswordRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getResetPasswordMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_REGISTER_USER = 0;
  private static final int METHODID_LOGIN_USER = 1;
  private static final int METHODID_VERIFY_MFA_CODE = 2;
  private static final int METHODID_VERIFY_EMAIL_TOKEN = 3;
  private static final int METHODID_FORGOT_PASSWORD = 4;
  private static final int METHODID_RESET_PASSWORD = 5;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_REGISTER_USER:
          serviceImpl.registerUser((org.abraham.models.RegisterUserRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.User>) responseObserver);
          break;
        case METHODID_LOGIN_USER:
          serviceImpl.loginUser((org.abraham.models.LoginRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.LoginResponse>) responseObserver);
          break;
        case METHODID_VERIFY_MFA_CODE:
          serviceImpl.verifyMfaCode((org.abraham.models.VerifyMfaCodeRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.VerifyMfaCodeResponse>) responseObserver);
          break;
        case METHODID_VERIFY_EMAIL_TOKEN:
          serviceImpl.verifyEmailToken((org.abraham.models.VerifyEmailTokenRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.VerifyEmailTokenResponse>) responseObserver);
          break;
        case METHODID_FORGOT_PASSWORD:
          serviceImpl.forgotPassword((org.abraham.models.ForgotPasswordRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.ForgotPasswordResponse>) responseObserver);
          break;
        case METHODID_RESET_PASSWORD:
          serviceImpl.resetPassword((org.abraham.models.ResetPasswordRequest) request,
              (io.grpc.stub.StreamObserver<org.abraham.models.ResetPasswordResponse>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getRegisterUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.RegisterUserRequest,
              org.abraham.models.User>(
                service, METHODID_REGISTER_USER)))
        .addMethod(
          getLoginUserMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.LoginRequest,
              org.abraham.models.LoginResponse>(
                service, METHODID_LOGIN_USER)))
        .addMethod(
          getVerifyMfaCodeMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.VerifyMfaCodeRequest,
              org.abraham.models.VerifyMfaCodeResponse>(
                service, METHODID_VERIFY_MFA_CODE)))
        .addMethod(
          getVerifyEmailTokenMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.VerifyEmailTokenRequest,
              org.abraham.models.VerifyEmailTokenResponse>(
                service, METHODID_VERIFY_EMAIL_TOKEN)))
        .addMethod(
          getForgotPasswordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.ForgotPasswordRequest,
              org.abraham.models.ForgotPasswordResponse>(
                service, METHODID_FORGOT_PASSWORD)))
        .addMethod(
          getResetPasswordMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.abraham.models.ResetPasswordRequest,
              org.abraham.models.ResetPasswordResponse>(
                service, METHODID_RESET_PASSWORD)))
        .build();
  }

  private static abstract class AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    AuthServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.abraham.models.UserServiceOuterClass.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("AuthService");
    }
  }

  private static final class AuthServiceFileDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier {
    AuthServiceFileDescriptorSupplier() {}
  }

  private static final class AuthServiceMethodDescriptorSupplier
      extends AuthServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    AuthServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (AuthServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new AuthServiceFileDescriptorSupplier())
              .addMethod(getRegisterUserMethod())
              .addMethod(getLoginUserMethod())
              .addMethod(getVerifyMfaCodeMethod())
              .addMethod(getVerifyEmailTokenMethod())
              .addMethod(getForgotPasswordMethod())
              .addMethod(getResetPasswordMethod())
              .build();
        }
      }
    }
    return result;
  }
}
