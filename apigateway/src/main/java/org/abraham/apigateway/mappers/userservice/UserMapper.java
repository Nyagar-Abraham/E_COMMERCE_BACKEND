package org.abraham.apigateway.mappers.userservice;

import org.abraham.apigateway.Utils.Utils;
import org.abraham.apigateway.dtos.userservice.*;
import org.abraham.apigateway.types.UserStatus;
import org.abraham.models.*;

public class UserMapper {

    public static UserDto userToDto(User user) {
        var userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEmail(user.getEmail());
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setStatus(UserStatus.valueOf(user.getStatus().name()));
        userDto.setCreatedAt(Utils.offsetDateTimeFromTimestamp(user.getCreatedAt()));
        userDto.setUpdatedAt(Utils.offsetDateTimeFromTimestamp(user.getUpdatedAt()));
        userDto.setEmailVerified(user.getEmailVerified());
        userDto.setPhoneVerified(user.getPhoneVerified());
        if (user.hasLastLogin()) userDto.setLastLoginAt(Utils.offsetDateTimeFromTimestamp(user.getLastLogin()));
        return userDto;
    }

    public static RegisterUserRequest registerInputDtoToRegisterUserRequest(RegisterInputDto inputDto) {
        return RegisterUserRequest.newBuilder()
                .setEmail(inputDto.getEmail())
                .setUsername(inputDto.getUsername())
                .setPassword(inputDto.getPassword())
                .setFirstName(inputDto.getFirstName())
                .setLastName(inputDto.getLastName())
                .setPhoneNumber(inputDto.getPhoneNumber())
                .setEnableMfa(inputDto.isEnableMfa())
                .build();
    }

    public static LoginRequest loginInputDtoToLoginRequest(LoginInputDto requestDto) {
        return LoginRequest.newBuilder()
                .setEmail(requestDto.getEmail())
                .setPassword(requestDto.getPassword())
                .build();
    }

    public static AuthPayloadDto loginResponseToAuthPayloadDto(LoginResponse response) {
        var authPayloadDto = new AuthPayloadDto();
        authPayloadDto.setAccessToken(response.getAccessToken());
        authPayloadDto.setRefreshToken(response.getRefreshToken());
        if (!response.getQrCodeImage().isEmpty()) authPayloadDto.setQrCodeImage(response.getQrCodeImage());

        return authPayloadDto;
    }

    public static VerifyMfaCodePayloadDto verifyMfaCodeResponseToDto(VerifyMfaCodeResponse response) {
        var verifyMfaCodePayloadDto = new VerifyMfaCodePayloadDto();
        verifyMfaCodePayloadDto.setMessage(response.getMessage());
        if (response.hasUser()) verifyMfaCodePayloadDto.setUser(userToDto(response.getUser()));
        return verifyMfaCodePayloadDto;
    };

    public static VerifyEmailResponseDto verifyEmailResponseToDto(VerifyEmailTokenResponse response) {
        return new VerifyEmailResponseDto(response.getSuccess(), response.getMessage());
    }


    public static ForgotPasswordPayloadDto forgotPasswordResponseToDTO(ForgotPasswordResponse response) {
        var  forgotPasswordPayloadDto = new ForgotPasswordPayloadDto();
        forgotPasswordPayloadDto.setMessage(response.getMessage());
        forgotPasswordPayloadDto.setSuccess(response.getSuccess());
        return forgotPasswordPayloadDto;
    }

    public static ResetPasswordPayloadDto resetPasswordResponseToDto(ResetPasswordResponse response) {
        var resetPasswordPayloadDto = new ResetPasswordPayloadDto();
        resetPasswordPayloadDto.setMessage(response.getMessage());
        resetPasswordPayloadDto.setSuccess(response.getSuccess());
        return resetPasswordPayloadDto;
    }
}
