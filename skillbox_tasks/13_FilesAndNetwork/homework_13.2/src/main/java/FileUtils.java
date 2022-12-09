import java.io.*;
import java.util.Arrays;

public class FileUtils {
    public static void copyFolder(String sourceDirectory, String destinationDirectory) throws IOException {
        // write code copy content of sourceDirectory to destinationDirectory
        File sourceDir = new File(sourceDirectory);
        File destDir = new File(destinationDirectory);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
            File[] listFilesSourceDir = sourceDir.listFiles();
            for (File file : listFilesSourceDir) {
                if (file.isFile()) {
                    int i;
                    try (FileInputStream s = new FileInputStream(file.toString());
                         FileOutputStream d = new FileOutputStream(destDir.toString() + "/" + file.getName())) {
                        do {
                            i = s.read();
                            if (i != -1) {
                                d.write(i);
                            }
                        } while (i != -1);
                    } catch (IOException ex) {
                        ex.getStackTrace();
                    }
                } else {
                    String source = file.toString();
                    String destination = destinationDirectory + "/" + file.getName();
                    copyFolder(source, destination);
                }
            }
        }
    }


