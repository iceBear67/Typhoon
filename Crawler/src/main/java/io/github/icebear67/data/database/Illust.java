package io.github.icebear67.data.database;

import io.github.icebear67.Crawler;
import io.github.icebear67.util.InternalUseOnly;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Illust {
    private long id;
    private boolean checked = false;
    private String site;
    private List<String> urls = new ArrayList<>();

    public Illust(String site) {
        this.site = site;
    }

    /**
     * Only for ORM use
     */
    @InternalUseOnly
    public Illust() {
        //DO NOTHING HERE
    }

    public void addUrl(String url) {
        Crawler.getLogger().info("add url: {}", url);
        if (url == null) {
            return;
        }
        if (url.contains("pximg")) {
            this.site = "Pixiv";
        }
        this.urls.add(url);
    }
}
