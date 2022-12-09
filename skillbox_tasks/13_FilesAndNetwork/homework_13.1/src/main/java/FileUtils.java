import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileUtils {

    public static long calculateFolderSize(String path) throws NullPointerException, IOException {
        File file = new File(path);

        long fileSize = Files.walk(file.toPath()).
                    map(l -> l.toFile()).
                    filter(f -> f.isFile()).
                    mapToLong(f -> f.length()).
                    sum();

        return fileSize;

    }
}



