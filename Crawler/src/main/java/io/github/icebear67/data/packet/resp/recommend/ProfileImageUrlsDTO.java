package io.github.icebear67.data.packet.resp.recommend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProfileImageUrlsDTO implements Serializable {

    @SerializedName("medium")
    private String medium;

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }
}