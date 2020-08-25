package io.github.icebear67.data;

import io.github.icebear67.util.InternalUseOnly;

public class URLSet {
    @InternalUseOnly
    public URLSet image_urls; //meta_pages:[{image_urls:{URLSET}},{image_urls:{URLSET}}]

    public String large;
    public String medium;
    public String original;
    public String square_medium;

    /*Single page only*/
    public String original_image_url;
}
