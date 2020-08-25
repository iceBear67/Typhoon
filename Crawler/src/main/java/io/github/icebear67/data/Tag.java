package io.github.icebear67.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Tag {
    public final String name;
    public String translated_name;

    public boolean include(String tag) {
        return name.contains(tag) || translated_name.contains(tag);
    }
}
