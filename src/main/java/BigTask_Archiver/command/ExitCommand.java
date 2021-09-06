package BigTask_Archiver.command;

import BigTask_Archiver.ConsoleHelper;

public class ExitCommand implements Command{
    @Override
    public void execute() throws Exception {
        ConsoleHelper.writeMessage("До встречи!");
    }
}
