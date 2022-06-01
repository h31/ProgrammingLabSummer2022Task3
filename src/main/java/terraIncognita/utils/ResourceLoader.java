package terraIncognita.utils;

import org.jetbrains.annotations.NotNull;
import terraIncognita.utils.exceptions.ExceptionWrapper;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ResourceLoader {
    public InputStream getInputStreamOf(@NotNull String relativePath) {
        InputStream res =  getClass().getClassLoader().getResourceAsStream(relativePath);

        if (res == null) {
            throw new ExceptionWrapper(new FileNotFoundException("File \"" + relativePath + "\" was not found"));
        }

        return res;
    }
}
