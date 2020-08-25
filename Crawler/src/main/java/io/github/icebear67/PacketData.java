package io.github.icebear67;

public interface PacketData {

    enum Result {
        SUCCESS,
        EXPIRED_TOKEN,
        LOGIN_FAILED,
        IO_ERROR
    }
}
