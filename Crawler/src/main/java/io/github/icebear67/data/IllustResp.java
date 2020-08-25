package io.github.icebear67.data;

import com.google.gson.annotations.SerializedName;
import io.github.icebear67.data.database.Illust;
import lombok.AccessLevel;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class IllustResp extends Illust {
    private List<Tag> tags;
    @SerializedName("total_bookmark")
    private long likes;
    @SerializedName("page_count")
    private int pageCount;
    //Single pages only
    @Getter(AccessLevel.PRIVATE)
    private URLSet meta_single_page;
    @Getter(AccessLevel.PRIVATE)
    private List<URLSet> meta_pages = new ArrayList<>();

    public void process(CrawlerSetting settings) {
        if (meta_pages.size() > 0) {

            switch (settings.getImageSize()) {
                case LARGE:
                    meta_pages.forEach(e -> super.addUrl(e.image_urls.large));
                    break;
                case MEDIUM:
                    meta_pages.forEach(e -> super.addUrl(e.image_urls.medium));
                    break;
                case SQUARE_MEDIUM:
                    meta_pages.forEach(e -> super.addUrl(e.image_urls.square_medium));
                    break;
                case ORIGINAL:
                    meta_pages.forEach(e -> super.addUrl(e.image_urls.original));
                    break;
            }
            return;
        }
        super.addUrl(meta_single_page.original_image_url);
    }
}
