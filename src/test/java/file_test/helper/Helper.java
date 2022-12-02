package file_test.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class Helper {
    public static InputStream getFileStreamByName(ZipFile zipFile, String fileName) throws IOException {
        Enumeration<? extends ZipEntry> entries = zipFile.entries();

        while (entries.hasMoreElements()) {
            ZipEntry entry = entries.nextElement();
            if (entry.getName().equals(fileName)) {
                return zipFile.getInputStream(entry);
            }
        }

        return InputStream.nullInputStream();
    }
}
