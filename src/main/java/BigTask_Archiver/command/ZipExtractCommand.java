package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.ZipFileManager;
import BigTask_Archiver.exception.PathIsNotFoundException;

import java.nio.file.Path;
import java.nio.file.Paths;

public class ZipExtractCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        try {
            ConsoleHelper.writeMessage("Распаковка архива!");

            ZipFileManager zipFileManager = getZipFileManager();

            ConsoleHelper.writeMessage("Введите полное имя файла или директории для разархивации!");
            Path path = Paths.get(ConsoleHelper.readString());
            zipFileManager.extractAll(path);

            ConsoleHelper.writeMessage("Архив разархивирован!");
        } catch (PathIsNotFoundException exception) {
            ConsoleHelper.writeMessage("Вы неверно указали имя файла или директории.");
        }
    }
}
