package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.ZipFileManager;
import BigTask_Archiver.exception.PathIsNotFoundException;

import java.io.Console;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipRemoveCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Удаление архива!");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или директории для удаления!");
            Path path = Paths.get(ConsoleHelper.readString());
            zipFileManager.removeFile(path);

            ConsoleHelper.writeMessage("Файлы удалены!");

        } catch (PathIsNotFoundException exception) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
