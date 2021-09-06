package Adding_a_file_to_the_archive;

//Заебался писать, добавление в архив файла с сохранением предыдущих и заменой, если такой есть в дирректрории /new

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipError;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Add_to_archive {
    public static void main(String[] args) throws IOException {
        String arc = "D:\\Test1.zip";
        String file = "D:\\Test2.txt";

        Map<String, ByteArrayOutputStream> map = getdata(arc);
        File filename = new File(file);

        FileOutputStream outputStream = new FileOutputStream(arc);

        try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(arc))) {

            ZipEntry zipEntry = new ZipEntry("new/" + filename.getName());
            zip.putNextEntry(zipEntry);
            Files.copy(filename.toPath(), zip);

            for (Map.Entry<String, ByteArrayOutputStream> entry : map.entrySet()) {
                if (entry.getKey().substring(entry.getKey().lastIndexOf("/") + 1).equals(filename.getName())) continue;
                zip.putNextEntry(new ZipEntry(entry.getKey()));
                zip.write(entry.getValue().toByteArray());
            }
        }
    }

    private static Map<String, ByteArrayOutputStream> getdata(String arc) throws IOException {
        Map<String, ByteArrayOutputStream> map = new HashMap<>();

        int lenght = 0;
        ZipEntry entry;
        try (ZipInputStream zipin = new ZipInputStream(new FileInputStream(arc));) {
            while ((entry = zipin.getNextEntry()) != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                while ((lenght = zipin.read(buffer)) != -1) {
                    byteArrayOutputStream.write(buffer, 0, lenght);
                }
                map.put(entry.getName(), byteArrayOutputStream);
            }
        }
        return map;
    }
}