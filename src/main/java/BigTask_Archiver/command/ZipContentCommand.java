package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;
import BigTask_Archiver.FileProperties;
import BigTask_Archiver.ZipFileManager;

import java.util.List;

public class ZipContentCommand extends ZipCommand {
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("Просмотр содержимого архива.");
        ZipFileManager zipFileManager = getZipFileManager();
        ConsoleHelper.writeMessage("Содержимое архива:");

        List <FileProperties> fileProperties = zipFileManager.getFilesList();

        for (FileProperties file:fileProperties){
            ConsoleHelper.writeMessage(file.toString());
        }
        ConsoleHelper.writeMessage("Содержимое архива прочитано!");
    }
}
