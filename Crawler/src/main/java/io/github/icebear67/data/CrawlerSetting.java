package io.github.icebear67.data;

import lombok.Getter;

@Getter
public class CrawlerSetting {
    private ImageSize imageSize = ImageSize.LARGE;
    private boolean debug = false;

    public enum ImageSize {
        LARGE,
        MEDIUM,
        SQUARE_MEDIUM,
        ORIGINAL
    }
}
