package io.github.icebear67.data.packet.resp.recommend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MetaSinglePageDTO implements Serializable {

    @SerializedName("original_image_url")
    private String originalImageUrl;

    public String getOriginalImageUrl() {
        return originalImageUrl;
    }

    public void setOriginalImageUrl(String originalImageUrl) {
        this.originalImageUrl = originalImageUrl;
    }
}