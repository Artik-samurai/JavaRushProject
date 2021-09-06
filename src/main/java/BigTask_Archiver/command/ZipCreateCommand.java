package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.ZipFileManager;
import BigTask_Archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipCreateCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Создание архива.");
            ZipFileManager zip = getZipFileManager();
            ConsoleHelper.writeMessage("Введите полное имя файла или директории для архивации!");
            Path path = Paths.get(ConsoleHelper.readString());
            zip.createZip(path);
            ConsoleHelper.writeMessage("Архив создан!");
        } catch (PathIsNotFoundException exception){
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }


    }
}
