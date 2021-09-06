package DeleteDirectoryForVitya;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class FileVisitor extends SimpleFileVisitor <Path> {

    private static String directory;

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        if (file.toString().matches(".*\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}.*")){
            Files.delete(file);
        }
        return super.visitFile(file, attrs);
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (dir.getFileName().toString().matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}")){
            Files.delete(dir);
        }
        return super.postVisitDirectory(dir, exc);
    }
}
