package io.github.icebear67.data.packet.resp.bookmark;

import com.google.gson.annotations.SerializedName;
import io.github.icebear67.data.IllustResp;

import java.util.ArrayList;
import java.util.List;

public class BookmarkResp {
    public List<IllustResp> illusts = new ArrayList<>();
    @SerializedName("next_url")
    public String nextUrl;
}
