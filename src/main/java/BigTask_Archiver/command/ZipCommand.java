package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.ZipFileManager;

import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class ZipCommand implements Command {

    public ZipFileManager getZipFileManager() throws Exception{
        ConsoleHelper.writeMessage("Введите полный путь к файла архива!");
        String pathString = ConsoleHelper.readString();
        Path path = Paths.get(pathString);
        return new ZipFileManager(path);
    }

}
