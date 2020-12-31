import java.util.*;

public class Entry {
    private String key;
    private Calendar createdAt;
    private int ttl;

    public Entry() {
    }

    public Entry(String key, int ttl) {
        this.key = key;
        this.ttl = ttl;
        createdAt = Calendar.getInstance();
    }

    public String getKey() {
        return key;
    }

    public Calendar getCreatedAt() {
        return createdAt;
    }

    public int getTtl() {
        return ttl;
    }
}
