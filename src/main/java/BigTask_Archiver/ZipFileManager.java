package BigTask_Archiver;

import BigTask_Archiver.command.ZipCommand;
import BigTask_Archiver.exception.PathIsNotFoundException;
import BigTask_Archiver.exception.WrongZipFileException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFileManager {
    private Path zipFile;

    public ZipFileManager(Path zipFile) {
        this.zipFile = zipFile;
    }

    public void createZip(Path source) throws Exception {
        Path zipFileParentParent = zipFile.getParent();

        if (Files.notExists(zipFileParentParent)){
            Files.createDirectories(zipFileParentParent);
        }

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(zipFile))){

            if (Files.isDirectory(source)){
                FileManager manager = new FileManager(source);
                List <Path> list = manager.getFileList();

                for (Path path: list){
                    addNewZipEntry(zipOutputStream, source, path);
                }
            } else if (Files.isRegularFile(source)){
                addNewZipEntry(zipOutputStream, source.getParent(), source.getFileName());
            } else throw new PathIsNotFoundException();

        }
    }

    private void addNewZipEntry(ZipOutputStream zipOutputStream, Path filePath, Path fileName) throws Exception{
        Path fullName = filePath.resolve(fileName);
        try (InputStream in = Files.newInputStream(fullName)){
            ZipEntry zip = new ZipEntry(fileName.toString());
            zipOutputStream.putNextEntry(zip);

            copyData(in, zipOutputStream);

            zipOutputStream.closeEntry();
        }
    }

    private void copyData(InputStream in, OutputStream out) throws Exception{
        byte [] buffer = new byte[1024];
        int lenght;
        while ((lenght = in.read(buffer)) > 0){
            out.write(buffer, 0, lenght);
        }
    }

    public List<FileProperties> getFilesList() throws Exception{
        if (!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        List <FileProperties> filePropertiesList = new ArrayList<>();

        try (ZipInputStream zip = new ZipInputStream(Files.newInputStream(zipFile))){
            ZipEntry entry = zip.getNextEntry();
            while (entry != null){
                //Поток для выгрузки с инпута для чего непонятно, просто чтобы что-то уменьшалось
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                copyData(zip, byteArrayOutputStream);

                FileProperties fileProperties = new FileProperties(entry.getName(), entry.getSize(), entry.getCompressedSize(), entry.getMethod());
                filePropertiesList.add(fileProperties);
                entry = zip.getNextEntry();
            }
            return filePropertiesList;
        }
    }

    public void extractAll(Path outputFolder) throws Exception {
        // Проверяем существует ли zip файл
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))) {
            // Создаем директорию вывода, если она не существует
            if (Files.notExists(outputFolder))
                Files.createDirectories(outputFolder);

            // Проходимся по содержимому zip потока (файла)
            ZipEntry zipEntry = zipInputStream.getNextEntry();

            while (zipEntry != null) {
                String fileName = zipEntry.getName();
                Path fileFullName = outputFolder.resolve(fileName);

                // Создаем необходимые директории
                Path parent = fileFullName.getParent();
                if (Files.notExists(parent))
                    Files.createDirectories(parent);

                try (OutputStream outputStream = Files.newOutputStream(fileFullName)) {
                    copyData(zipInputStream, outputStream);
                }
                zipEntry = zipInputStream.getNextEntry();
            }
        }
    }

    public void removeFiles(List<Path> pathList) throws Exception{
        //Проверяем есть ли файл архива
        if (!Files.isRegularFile(zipFile)) {
            throw new WrongZipFileException();
        }
        // Создаем временный файл архива, куда будем копировать
        Path tempZipFile = Files.createTempFile(null, null);
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))){
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))){
                ZipEntry zipEntry = zipInputStream.getNextEntry();

                while (zipEntry != null){
                    Path path = Paths.get(zipEntry.getName());

                    if (!pathList.contains(path)){
                        String name = zipEntry.getName();
                        zipOutputStream.putNextEntry(new ZipEntry(name));
                        copyData(zipInputStream, zipOutputStream);
                        zipOutputStream.closeEntry();
                        zipInputStream.closeEntry();
                    } else {
                        ConsoleHelper.writeMessage(String.format("Файл '%s' удален из архива.", path.toString()));
                    }
                    zipEntry = zipInputStream.getNextEntry();
                }

                Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }

    public void removeFile(Path path) throws Exception{
        removeFiles(Collections.singletonList(path));
    }

    public void addFiles(List<Path> absolutePathList) throws Exception{
        if (!Files.isRegularFile(zipFile)){
            throw new WrongZipFileException();
        }

        Path tempZipFile = Files.createTempFile(null, null);

        for (Path path : absolutePathList){
            if (Files.notExists(path)) throw new PathIsNotFoundException();
        }

        List <Path> listOldPath = new ArrayList<>();

        try (ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(tempZipFile))){
            try (ZipInputStream zipInputStream = new ZipInputStream(Files.newInputStream(zipFile))){
                ZipEntry zipEntry = zipInputStream.getNextEntry();
                while (zipEntry != null){
                        String name = zipEntry.getName();
                        listOldPath.add(Paths.get(name));

                        zipOutputStream.putNextEntry(new ZipEntry(name));
                        copyData(zipInputStream, zipOutputStream);

                        zipOutputStream.closeEntry();
                        zipInputStream.closeEntry();

                    zipEntry = zipInputStream.getNextEntry();
                }
            }

            for (Path path:absolutePathList){

                if (Files.isRegularFile(path)) {
                    if (listOldPath.contains(path.getFileName())){
                        ConsoleHelper.writeMessage(String.format("Файл '%s' уже существует в архиве.", path.toString()));
                    } else {
                        addNewZipEntry(zipOutputStream, path.getParent(), path.getFileName());
                        ConsoleHelper.writeMessage(String.format("Файл '%s' добавлен в архиве.", path.toString()));
                    }
                } else {
                    throw new PathIsNotFoundException();
                }
            }

            Files.move(tempZipFile, zipFile, StandardCopyOption.REPLACE_EXISTING);
        }
    }

    public void addFile(Path absolutePath) throws Exception{
        addFiles(Collections.singletonList(absolutePath));
    }

}
