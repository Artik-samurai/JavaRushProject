package MyEnum;

import java.util.LinkedList;
import java.util.List;
import java.util.*;

//Интерфейс Columnable менять нельзя.
//        Класс Column должен реализовывать интерфейс Columnable.
//        Создавать дополнительные поля в классе Column нельзя.
//        Метод Column.configureColumns реализован. Менять его не нужно.
//        Метод Column.getVisibleColumns должен возвращать список отображаемых колонок в скофигурированом порядке.
//        Метод Column.getColumnName должен возвращать полное имя колонки.
//        Метод Column.isShown должен возвращать true, если колонка видимая, иначе false.
//        Метод Column.hide должен скрывать колонку и сдвигать индексы остальных отображаемых колонок.

public enum Column implements Columnable {
    Customer("Customer"),
    BankName("Bank Name"),
    AccountNumber("Account Number"),
    Amount("Available Amount");

    private String columnName;

    private static int[] realOrder;

    Column(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Задает новый порядок отображения колонок, который хранится в массиве realOrder.
     * realOrder[индекс в энуме] = порядок отображения; -1, если колонка не отображается.
     *
     * @param newOrder новая последовательность колонок, в которой они будут отображаться в таблице
     * @throws IllegalArgumentException при дубликате колонки
     */
    public static void configureColumns(Column... newOrder) {
        realOrder = new int[values().length];
        for (Column column : values()) {
            realOrder[column.ordinal()] = -1;
            boolean isFound = false;

            for (int i = 0; i < newOrder.length; i++) {
                if (column == newOrder[i]) {
                    if (isFound) {
                        throw new IllegalArgumentException("Column '" + column.columnName + "' is already configured.");
                    }
                    realOrder[column.ordinal()] = i;
                    isFound = true;
                }
            }
        }
    }

    /**
     * Вычисляет и возвращает список отображаемых колонок в сконфигурированом порядке (см. метод configureColumns)
     * Используется поле realOrder.
     *
     * @return список колонок
     */
    public static List<Column> getVisibleColumns() {
        List<Column> result = new LinkedList<>();

        int next = 0;
        for (int i = 0 ; i < realOrder.length; i++){
            boolean isOpen = true;
            for (int j = 0; j < realOrder.length; j++){
                if (realOrder [j] != -1 && isOpen && realOrder [j] == next){
                    result.add(values()[j]);
                    isOpen = false;
                }
            }
            next++;
        }


        return result;
    }


    @Override
    public String getColumnName() {
        return columnName;
    }

    @Override
    public boolean isShown() {
        return realOrder != null && realOrder[ordinal()] != -1;
    }

    @Override
    public void hide() {
        int index = ordinal();
        realOrder [index] = -1;
    }
}

