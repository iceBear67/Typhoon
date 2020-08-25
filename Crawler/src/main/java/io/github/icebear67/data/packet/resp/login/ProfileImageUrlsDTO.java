package io.github.icebear67.data.packet.resp.login;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileImageUrlsDTO implements Serializable {

    @SerializedName("px_16x16")
    private String px16x16;

    @SerializedName("px_50x50")
    private String px50x50;

    @SerializedName("px_170x170")
    private String px170x170;
}