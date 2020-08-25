package io.github.icebear67.data.packet.resp.recommend;

import com.google.gson.annotations.SerializedName;
import io.github.icebear67.data.IllustResp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class RecommendResponseDTO implements Serializable {

    @SerializedName("illusts")
    private List<IllustResp> illusts = new ArrayList<>();

    @SerializedName("contest_exists")
    private boolean contestExists;

    @SerializedName("privacy_policy")
    private PrivacyPolicyDTO privacyPolicy;

    @SerializedName("next_url")
    private String nextUrl;

    public List<IllustResp> getIllusts() {
        return illusts;
    }

    public void setIllusts(List<IllustResp> illusts) {
        this.illusts = illusts;
    }

    public boolean isContestExists() {
        return contestExists;
    }

    public void setContestExists(boolean contestExists) {
        this.contestExists = contestExists;
    }

    public PrivacyPolicyDTO getPrivacyPolicy() {
        return privacyPolicy;
    }

    public void setPrivacyPolicy(PrivacyPolicyDTO privacyPolicy) {
        this.privacyPolicy = privacyPolicy;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}