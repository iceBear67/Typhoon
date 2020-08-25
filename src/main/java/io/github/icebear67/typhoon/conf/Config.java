package io.github.icebear67.typhoon.conf;

import cc.sfclub.util.common.JsonConfig;
import io.github.icebear67.data.CrawlerSetting;
import io.github.icebear67.data.packet.Login;

public class Config extends JsonConfig {
    public static Config inst;
    public Login loginCred = new Login();
    public CrawlerSetting crawlerSettings = new CrawlerSetting();
    public String siteName = "pixiv";
    public boolean firstRun = true;

    public Config() {
        super(".");
    }
}
