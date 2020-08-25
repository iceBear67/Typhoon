package io.github.icebear67.typhoon;

import io.github.icebear67.Crawler;
import io.github.icebear67.typhoon.conf.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initializer {
    private static final Logger logger = LoggerFactory.getLogger("Main");

    public static void main(String[] args) {
        Config.inst = (Config) new Config().saveDefaultOrLoad();
        if (Config.inst.firstRun) {
            Config.inst.firstRun = false;
            Config.inst.saveConfig();
            logger.info("Edit ./config.json first.");
            return;
        }
        Crawler crawler = new Crawler();
        crawler.setSettings(Config.inst.crawlerSettings);

    }
}
