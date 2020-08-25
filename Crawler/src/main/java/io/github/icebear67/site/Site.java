package io.github.icebear67.site;

import io.github.icebear67.PacketData;
import io.github.icebear67.data.IllustResp;
import io.github.icebear67.data.Tag;
import io.github.icebear67.data.packet.Login;
import okhttp3.Request;

import java.util.List;

public interface Site {
    PacketData.Result doLogin(Login cred);

    boolean checkLoginStatus();

    List<IllustResp> search(List<Tag> tags, int page);

    List<IllustResp> getRecommend(int maxpages);

    List<IllustResp> getBookmarked();

    PacketData.Result refreshSession();

    Request.Builder getAuthBuilder();
}
