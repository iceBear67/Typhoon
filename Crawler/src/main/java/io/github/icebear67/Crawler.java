package io.github.icebear67;

import com.google.gson.Gson;
import io.github.icebear67.data.CrawlerSetting;
import io.github.icebear67.data.IllustResp;
import io.github.icebear67.data.Tag;
import io.github.icebear67.site.Pixiv;
import io.github.icebear67.site.Site;
import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Crawler {
    @Getter
    private static final Gson gson = new Gson();
    @Getter
    private static final Logger logger = LoggerFactory.getLogger(Crawler.class);
    @Getter
    private static OkHttpClient httpClient;
    @Getter
    private final Map<String, Site> sites = new HashMap<>();
    @Getter
    @Setter
    private CrawlerSetting settings = new CrawlerSetting();

    public Crawler() {
        if (settings.isDebug()) {
            httpClient = new HttpUtils().getTrustAllClient();
        } else {
            httpClient = new OkHttpClient.Builder().dns(new CloudflareHttpDns()).build();
        }
        sites.put("pixiv", new Pixiv(settings));

    }

    public Optional<Site> getSiteClient(String siteName) {
        return Optional.ofNullable(sites.get(siteName.toLowerCase()));
    }

    public Optional<Map<Tag, Integer>> getTagFrequencies(Site siteClient) {
        if (siteClient.checkLoginStatus()) {
            return Optional.empty();
        }
        Map<Tag, Integer> tags = new HashMap<>();
        for (IllustResp illustResp : siteClient.getBookmarked()) {
            illustResp.getTags().forEach(e -> {
                tags.put(e, tags.getOrDefault(e, 0) + 1);
            });
        }
        List<Map.Entry<Tag, Integer>> list = new ArrayList<>(tags.entrySet());
        list.sort((o1, o2) -> o2.getValue().compareTo(o1.getValue()));
        tags.clear();
        for (Map.Entry<Tag, Integer> mapping : list) {
            tags.put(mapping.getKey(), mapping.getValue());
        }
        return Optional.of(tags);
    }
}
