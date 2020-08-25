package io.github.icebear67.data.packet.resp.recommend;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PrivacyPolicyDTO implements Serializable {

    @SerializedName("version")
    private String version;

    @SerializedName("message")
    private String message;

    @SerializedName("url")
    private String url;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}