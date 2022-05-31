package terraIncognita.Utils;

import java.io.InputStream;

public class ResourceLoader {
    public InputStream getInputStreamOf(String relativePath) {
        return getClass().getClassLoader().getResourceAsStream(relativePath);
    }
}
