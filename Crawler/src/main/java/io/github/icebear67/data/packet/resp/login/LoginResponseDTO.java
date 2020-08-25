package io.github.icebear67.data.packet.resp.login;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class LoginResponseDTO {

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("expires_in")
    private int expiresIn;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("scope")
    private String scope;

    @SerializedName("refresh_token")
    private String refreshToken;

    @SerializedName("user")
    private UserDTO user;

    @SerializedName("device_token")
    private String deviceToken;

    @SerializedName("response")
    private LoginResponseDTO response;
    private boolean has_error;
    private String error;
}