package io.github.icebear67.data.packet.resp.login;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserDTO implements Serializable {

    @SerializedName("profile_image_urls")
    private ProfileImageUrlsDTO profileImageUrls;

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("account")
    private String account;

    @SerializedName("mail_address")
    private String mailAddress;

    @SerializedName("is_premium")
    private boolean isPremium;

    @SerializedName("x_restrict")
    private int xRestrict;

    @SerializedName("is_mail_authorized")
    private boolean isMailAuthorized;

    @SerializedName("require_policy_agreement")
    private boolean requirePolicyAgreement;
}