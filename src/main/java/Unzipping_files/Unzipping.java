package Unzipping_files;

// Сбор и разархивация файла по его кусочкам

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Unzipping {
    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        Set<String> sourse = new TreeSet<>();
        sourse.addAll(Arrays.asList(args).subList(1, args.length));
        List<FileInputStream> str = new ArrayList<>();

        for (String way : sourse) {
            str.add(new FileInputStream(way));
        }

        try (ZipInputStream zipInputStream = new ZipInputStream(new SequenceInputStream(Collections.enumeration(str)))) {
            byte[] buffer = new byte[2048];
            while (zipInputStream.getNextEntry() != null){
                int lenght;
                while ((lenght = zipInputStream.read(buffer,0,buffer.length)) > -1){
                    try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))){
                        outputStream.write(buffer,0,lenght);
                    }
                }
            }
        }
    }
}



