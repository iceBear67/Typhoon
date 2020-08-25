package io.github.icebear67.site;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.icebear67.Crawler;
import io.github.icebear67.PacketData;
import io.github.icebear67.data.CrawlerSetting;
import io.github.icebear67.data.IllustResp;
import io.github.icebear67.data.Tag;
import io.github.icebear67.data.packet.Login;
import io.github.icebear67.data.packet.resp.bookmark.BookmarkResp;
import io.github.icebear67.data.packet.resp.login.LoginResponseDTO;
import io.github.icebear67.data.packet.resp.recommend.RecommendResponseDTO;
import io.github.icebear67.util.PixivUtil;
import lombok.Getter;
import lombok.NonNull;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Pixiv implements Site {
    private static final String USER_AGENT = "PixivAndroidApp/5.0.157 (Android 7.1.1; Mi)";
    private static final String REFERER = "https://app-api.pixiv.net/";
    private static final String CLIENT_ID = "MOBrBDS8blbauoSck0ZfDbtuzpyT";
    private static final String CLIENT_SECRET = "lsACyCD94FhDUtGTXi3QzcFE2uU1hqtDaKeqrdwj";
    private static final PixivUtil util = new PixivUtil();
    @Getter
    private String bearerToken;
    @Getter
    private long userId;
    private Login cred;
    private long expiredTime;
    private CrawlerSetting setting;

    public Pixiv(CrawlerSetting a) {
        setting = a;
    }

    public static Request.Builder getPreConfiguredBuilder() {
        SimpleDateFormat ISO8601DATETIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ", Locale.SIMPLIFIED_CHINESE);
        String isoDate = ISO8601DATETIMEFORMAT.format(new Date());
        return new Request.Builder()
                .addHeader("User-Agent", USER_AGENT)
                .addHeader("Referer", REFERER)
                .addHeader("App-OS", "Android")
                .addHeader("App-OS-Version", "7.1.1")
                .addHeader("App-Version", "5.0.157")
                .addHeader("Accept-Language", "zh_CN")
                .addHeader("X-Client-Time", isoDate)
                .addHeader("X-Client-Hash", util.getHash(isoDate));
    }

    @Override
    public PacketData.Result doLogin(Login cred) {
        this.cred = cred;
        return refreshSession();
    }

    @Override
    public boolean checkLoginStatus() {
        Request req = getAuthBuilder().url("https://app-api.pixiv.net/v1/user/detail?filter=for_android&user_id=" + userId).build();
        try {
            Response resp = Crawler.getHttpClient().newCall(req).execute();
            JsonObject jsonObject = JsonParser.parseString(resp.body().string()).getAsJsonObject();
            return jsonObject.has("profile");
        } catch (IOException e) {
            Crawler.getLogger().error("Failed to getstatus: {}", e.getMessage());
            return false;
        }
    }

    @Override
    public List<IllustResp> search(List<Tag> tags, int page) {
        Request request = getAuthBuilder()
                .url("https://app-api.pixiv.net/v1/search/illust?filter=" +
                        "for_android&" +
                        "include_translated_tag_results=true&" +
                        "merge_plain_keyword_results=true&" +
                        "word=" + listToStr(tags) + "&" +
                        "sort=date_desc&" +
                        "search_target=partial_match_for_tags&" +
                        "offset=" + page * 30).build();
        try {
            Response resp = Crawler.getHttpClient().newCall(request).execute();
            JsonElement jsonElement = JsonParser.parseString(resp.body().string());
            List<IllustResp> result = new ArrayList<>();
            for (JsonElement illust : jsonElement.getAsJsonObject().getAsJsonArray("illusts")) {
                IllustResp illustResp = Crawler.getGson().fromJson(illust.getAsJsonObject(), IllustResp.class);
                illustResp.process(setting);
                result.add(illustResp);
            }
            if (result.isEmpty()) {
                return null;
            }
            return result;
        } catch (IOException e) {
            return null;
        }

    }

    private String listToStr(List<Tag> tags) {
        StringBuilder sb = new StringBuilder();
        for (Tag tag : tags) {
            sb.append(tag.name).append(" ");
        }
        return sb.toString();
    }

    @NonNull
    @Override
    public List<IllustResp> getRecommend(int maxPages) {
        int page_now = 0;
        String nextUrl = "https://app-api.pixiv.net/v1/illust/recommended?filter=for_android&include_ranking_illusts=true&include_privacy_policy=true";
        List<IllustResp> illustResps = new ArrayList<>();
        while (true) {
            try {
                page_now++;
                Request req = getAuthBuilder().url(nextUrl).build();
                Response resp = Crawler.getHttpClient().newCall(req).execute();
                RecommendResponseDTO recommends = Crawler.getGson().fromJson(resp.body().string(), RecommendResponseDTO.class);
                illustResps.addAll(recommends.getIllusts());
                nextUrl = recommends.getNextUrl();
                if (nextUrl == null || nextUrl.isEmpty()) {
                    illustResps.forEach(e -> e.process(setting));
                    return illustResps;
                }
                if (page_now >= maxPages) {
                    illustResps.forEach(e -> e.process(setting));
                    return illustResps;
                }
            } catch (IOException exception) {
                illustResps.forEach(e -> e.process(setting));
                return illustResps;
            }
        }
    }

    @Override
    public List<IllustResp> getBookmarked() {
        List<IllustResp> illustResps = new ArrayList<>();
        int page = 0;
        String next = "https://app-api.pixiv.net/v1/user/bookmarks/illust?user_id=" + userId + "&restrict=public";
        try {
            boolean hasNext = true;
            while (hasNext) {
                page++;
                long start = System.currentTimeMillis();
                Request req = getAuthBuilder().url(next).build();
                Response resp = Crawler.getHttpClient().newCall(req).execute();
                String raw = resp.body().string();
                BookmarkResp bookmarkResp = Crawler.getGson().fromJson(raw, BookmarkResp.class);
                if (bookmarkResp.nextUrl == null || bookmarkResp.illusts.isEmpty()) {
                    hasNext = false;
                }
                illustResps.addAll(bookmarkResp.illusts);
                Crawler.getLogger().info("Bookmark:: page {},takes {}ms", page, System.currentTimeMillis() - start);
            }
            illustResps.forEach(e -> e.process(setting));
        } catch (IOException e) {
            illustResps.forEach(l -> l.process(setting));
            return illustResps;
        }
        return illustResps;
    }

    @Override
    public PacketData.Result refreshSession() {
        if (cred == null) return PacketData.Result.LOGIN_FAILED;
        Request req = getPreConfiguredBuilder()
                .url("https://oauth.secure.pixiv.net/auth/token")
                .post(RequestBody.create(
                        "client_id=" + CLIENT_ID +
                                "&client_secret=" + CLIENT_SECRET +
                                "&grant_type=password&" +
                                "username=" + cred.username + "&" +
                                "password=" + cred.password +
                                "&device_token=64f91d4416c45e61397046d6623b590b" +
                                "&get_secure_url=true" +
                                "&include_policy=true"
                        , MediaType.parse("application/x-www-form-urlencoded")))
                .build();
        try {
            Response response = Crawler.getHttpClient().newCall(req).execute();
            String a = response.body().string();
            LoginResponseDTO loginResp = Crawler.getGson().fromJson(a, LoginResponseDTO.class);
            if (loginResp.isHas_error()) {
                Crawler.getLogger().warn("Failed to login: {}", loginResp.getError());
                Crawler.getLogger().warn(a);
                return PacketData.Result.LOGIN_FAILED;
            }
            if (loginResp.getResponse() != null) {
                loginResp = loginResp.getResponse();
            }
            Crawler.getLogger().info("RefreshToken:: User: {} (is premium:{})", loginResp.getUser().getName(), loginResp.getUser().isPremium());
            bearerToken = loginResp.getAccessToken();
            userId = Long.parseLong(loginResp.getUser().getId());
            expiredTime = System.currentTimeMillis() + loginResp.getExpiresIn() * 1000;
            return PacketData.Result.SUCCESS;
        } catch (IOException e) {
            Crawler.getLogger().error("Failed to login: {}", e.getMessage());
            return PacketData.Result.IO_ERROR;
        }

    }

    @Override
    public Request.Builder getAuthBuilder() {
        if (System.currentTimeMillis() > expiredTime) {
            if (refreshSession() != PacketData.Result.SUCCESS) {
                Crawler.getLogger().warn("getAuthBuilder:: bearerToken EXPIRED and FAILED TO REFRESH");
            }
        }
        return getPreConfiguredBuilder().addHeader("Authorization", "Bearer " + bearerToken);
    }
}
