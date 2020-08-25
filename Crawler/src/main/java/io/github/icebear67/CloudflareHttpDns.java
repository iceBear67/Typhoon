package io.github.icebear67;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.SneakyThrows;
import okhttp3.Dns;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CloudflareHttpDns implements Dns {
    private OkHttpClient httpClient = new OkHttpClient();
    private Map<String, Long> TTL = new HashMap<>();
    private Map<String, List<InetAddress>> cache = new HashMap<>();

    @SneakyThrows
    @NotNull
    @Override
    public List<InetAddress> lookup(@NotNull String s) {
        if (TTL.containsKey(s)) {
            if (TTL.get(s) > System.currentTimeMillis()) {
                return cache.get(s);
            }
        }
        Request req = new Request.Builder()
                .addHeader("accept", "application/dns-json")
                .url(String.format("https://cloudflare-dns.com/dns-query?name=%s&type=%s", s, "A"))
                .build();
        Response response = httpClient.newCall(req).execute();
        if (response.code() != 200) {
            return Dns.SYSTEM.lookup(s);
        }
        JsonElement resp = JsonParser.parseString(response.body().string());
        List<InetAddress> addresses = new ArrayList<>();
        resp.getAsJsonObject().getAsJsonArray("Answer").forEach(ele -> {
            JsonObject jsonObject = ele.getAsJsonObject();
            int ttl = jsonObject.get("TTL").getAsInt();
            String result = jsonObject.get("data").getAsString();
            TTL.put(result, System.currentTimeMillis() + ttl * 1000);
            try {
                addresses.add(InetAddress.getByName(result));
            } catch (Exception ignored) {

            }
        });
        cache.put(s, addresses);
        return addresses;
    }
}
