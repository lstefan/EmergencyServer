package message;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int AUTHENTICATE_USER = 101;

    public static final int AUDIO_FILE = 501;
    public static final int PICTURE_FILE = 502;

    private int type;

    public byte[] picture;

    public void setType(int type) {
        this.type = type;
    }
    public int getType() {
        return this.type;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
