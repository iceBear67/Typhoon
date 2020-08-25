package io.github.icebear67.data.packet;

import io.github.icebear67.util.InternalUseOnly;

public class Login {
    public String username = "";
    public String password = "";

    @InternalUseOnly
    public Login() {

    }

    public Login(String userName, String password) {
        username = userName;
        this.password = password;
    }
}
