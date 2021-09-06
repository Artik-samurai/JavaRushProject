package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.ZipFileManager;
import BigTask_Archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipAddCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Добавление файлов к архиву!");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или директории для добавления!");
            Path path = Paths.get(ConsoleHelper.readString());
            zipFileManager.addFile(path);

            ConsoleHelper.writeMessage("Файлы добавлены!");

        } catch (PathIsNotFoundException exception) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
