package BigTaskChat.client;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import BigTaskChat.ConsoleHelper;
import java.io.IOException;

public class BotClient extends Client {
    @Override
    protected SocketThread getSocketThread() {
        return new BotSocketThread();
    }

    @Override
    protected boolean shouldSendTextFromConsole() {
        // Бот не должен отправлять текст введенный с консоли
        return false;
    }

    @Override
    protected String getUserName() {
        // Каждый раз генерируем новое имя бота на случай, если к серверу подключится несколько ботов
        return "date_bot_" + (int) (Math.random() * 100);
    }

    public static void main(String[] args) {
        // Создаем и запускаем бот клиента
        Client client = new BotClient();
        client.run();
    }

    public class BotSocketThread extends SocketThread {
        @Override
        protected void clientMainLoop() throws IOException, ClassNotFoundException {
            BotClient.this.sendTextMessage("Привет чатику. Я бот. Понимаю команды: дата, день, месяц, год, время, час, минуты, секунды.");
            super.clientMainLoop();
        }

        @Override
        protected void processIncomingMessage(String message) {
            ConsoleHelper.writeMessage(message);

            String [] mas = message.split(": ");
            if(mas.length != 2) return;

            String name = message.split(": ")[0];
            String text = message.split(": ")[1];

            String format = null;
            switch (text) {
                case "дата":
                    format = "d.MM.YYYY";
                    break;
                case "день":
                    format = "d";
                    break;
                case "месяц":
                    format = "MMMM";
                    break;
                case "год":
                    format = "YYYY";
                    break;
                case "время":
                    format = "H:mm:ss";
                    break;
                case "час":
                    format = "H";
                    break;
                case "минуты":
                    format = "m";
                    break;
                case "секунды":
                    format = "s";
                    break;
            }
                if (format != null){
                    Date newdate = Calendar.getInstance().getTime();
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
                    String str = simpleDateFormat.format(newdate);
                    BotClient.this.sendTextMessage("Информация для " + name + ": " + str);
                }
        }
    }
}
