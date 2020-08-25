package io.github.icebear67.util;

import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PixivUtil {
    @NotNull
    private final String salt = "28c1fdd170a5204386cb1313c7077b34f83e4aaf4aa829ce78c231e05b0bae2c";

    @NotNull
    public final String getSalt() {
        return this.salt;
    }

    private String encode(String text) {
        try {
            MessageDigest var10000 = MessageDigest.getInstance("MD5");
            Intrinsics.checkExpressionValueIsNotNull(var10000, "MessageDigest.getInstance(\"MD5\")");
            MessageDigest instance = var10000;
            Charset var5 = Charsets.UTF_8;
            boolean var6 = false;
            if (text == null) {
                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            } else {
                byte[] var16 = text.getBytes(var5);
                Intrinsics.checkExpressionValueIsNotNull(var16, "(this as java.lang.String).getBytes(charset)");
                byte[] var12 = var16;
                var16 = instance.digest(var12);
                Intrinsics.checkExpressionValueIsNotNull(var16, "instance.digest(text.toByteArray())");
                byte[] digest = var16;
                StringBuffer sb = new StringBuffer();
                byte[] var7 = digest;
                int var8 = digest.length;

                for (int var15 = 0; var15 < var8; ++var15) {
                    byte b = var7[var15];
                    int i = b & 255;
                    String hexString = Integer.toHexString(i);
                    if (hexString.length() < 2) {
                        hexString = '0' + hexString;
                    }

                    sb.append(hexString);
                }

                String var17 = sb.toString();
                Intrinsics.checkExpressionValueIsNotNull(var17, "sb.toString()");
                return var17;
            }
        } catch (NoSuchAlgorithmException var13) {
            var13.printStackTrace();
            return "";
        }
    }

    @NotNull
    public final String getHash(String isoDate) {
        return this.encode(isoDate + this.salt);
    }
}
