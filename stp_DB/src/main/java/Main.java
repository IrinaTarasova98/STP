import java.util.Scanner;
import Entity.*;

public class Main {

    public static void main(String[] args) {
        // показать список домов
        showHouses();
    }

    // показать список домов
    public static void showHouses() {

        // переменная для таблицы из БД
        DB_content<House> houses =  new DB_content<>();

        // запрос к БД и получение таблицы в "houses"
        houses.get_content("SELECT * FROM Дом", House.class);

        // если информация не получена
        if (houses.content == null) return;

        System.out.println("ВЫБОР ДОМА\n");
        System.out.println(" 'Exit' - выход");
        System.out.println(" '-'    - удалить дом");
        System.out.println(" '+'    - добавить дом\n");
        System.out.println("Список домов:");

        // вывести список записей
        for (int i = 0; i < houses.content.size(); i++) {
            // строка с номером, адресом дома
            System.out.println(" " + i + ". - " + houses.content.get(i).getStreet() + " "
                    + houses.content.get(i).getNumber());
        }
        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.toString();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("+"): { // добавление записи
                    System.out.println("Введите улицу:");
                    String street = keyboard.toString();
                    System.out.println("Введите номер дома:");
                    String num = keyboard.toString();

                    // экземпляр House для добавления в БД
                    House house = new House();
                    house.setStreet(street);
                    house.setNumber(num);

                    // добавление новой записи
                    houses.set_content(house);
                    showHouses();
                    break;
                }
                case ("-"): { // удалить дом
                    System.out.println("Введите номер удаляемого дома:");
                    String house_id = keyboard.toString();
                    houses.del_content("УДАЛИТЬ Дом WHERE ИД_Дома = " + house_id);
                    showHouses();
                    break;
                }
                default: {
                    int id = Integer.parseInt(resp);
                    if (id > 0 && id <= houses.content.size()) {
                        // показать список комнат выбраного дома
                        showRooms(houses.content.get(id - 1).getId());
                        break;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }
    }

    // показать список комнат выбраного дома
    public static void showRooms(long house_id) {
        // переменная для таблицы из БД
        DB_content<Room> rooms =  new DB_content<>();

        // запрос к БД и получение комнат дома с №id
        rooms.get_content("SELECT * FROM Комната WHERE ИД_Дома = " + house_id, Room.class);

        System.out.println("ВЫБОР КОМНАТЫ\n");
        System.out.println(" 'Exit' - выход");
        System.out.println(" '-'    - удалить комнату\n");
        System.out.println(" '+'    - добавить комнату\n");
        System.out.println(" '<-'   - назад к домам\n");
        System.out.println("Комнаты:");

        // вывести список записей
        for (int i = 0; i < rooms.content.size(); i++) {
            // строка с названием комнаты
            System.out.println(" " + i + ". - " + rooms.content.get(i).getName() + " "
                    + rooms.content.get(i).getId());
        }
        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.toString();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить комнату
                    System.out.println("Введите номер удаляемой комнаты:");
                    String room_id = keyboard.toString();
                    rooms.del_content("УДАЛИТЬ Комната WHERE ИД_Комнаты = " + room_id);
                    showRooms(house_id);
                    break;
                }
                case ("<-"): { // назад
                    showHouses();
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Название комнаты:");
                    String room_name = keyboard.toString();

                    // экземпляр Room для добавления в БД
                    Room room = new Room();
                    room.setName(room_name);

                    // room.setHouse( ЭКЗЕМПЛЯР ДОМА?); !!!

                    // добавление новой записи
                    rooms.set_content(room);
                    showRooms(house_id);
                    break;
                }
                default: {
                    int room_id = Integer.parseInt(resp);
                    if (room_id > 0 && room_id <= rooms.content.size()) {
                        // показать список датчиков выбраной комнаты
                        showSensors(rooms.content.get(room_id - 1).getId(), house_id);
                        return;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }
    }

    // показать список датчиков выбраной комнаты
    public static void showSensors(long room_id, long house_id) {
        // переменная для таблицы из БД
        DB_content<Sensor> sensors =  new DB_content<>();

        // запрос к БД и получение комнат дома с №id
        sensors.get_content("SELECT * FROM Комната WHERE ИД_Дома = " + room_id, Sensor.class);

        System.out.println("ВЫБОР ДАТЧИКА\n");
        System.out.println(" 'Exit' - выход");
        System.out.println(" '-'    - удалить датчик\n");
        System.out.println(" '+'    - добавить датчик\n");
        System.out.println(" '<-'   - назад к комнатам\n");
        System.out.println("Датчики:");

        // вывести список записей
        for (int i = 0; i < sensors.content.size(); i++) {
            // строка с данными датчика
            System.out.println(" " + i + ". - " + sensors.content.get(i).getId() + " "
                    + sensors.content.get(i).getParameter());
        }
        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.toString();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить датчик
                    System.out.println("Введите номер удаляемого датчика:");
                    String sensor_id = keyboard.toString();
                    sensors.del_content("УДАЛИТЬ Датчик WHERE ИД_Датчика = " + sensor_id);
                    showSensors(room_id, house_id);
                    break;
                }
                case ("<-"): { // назад
                    showRooms(house_id);
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Физическая величина датчика:");
                    String sensor_param = keyboard.toString();

                    // экземпляр Sensor для добавления в БД
                    Sensor sensor = new Sensor();
                    sensor.setParameter(sensor_param);

                    // sensor.setRoom( ЭКЗЕМПЛЯР КОМНАТЫ?); !!!

                    // добавление новой записи
                    sensors.set_content(sensor);
                    showSensors(room_id, house_id);
                    break;
                }
                default: {
                    int sensor_id = Integer.parseInt(resp);
                    if (sensor_id > 0 && sensor_id <= sensors.content.size()) {
                        // показать список значений выбранного датчика
                        showValues(house_id, room_id, sensors.content.get(sensor_id - 1).getId());
                        return;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }
    }

    // показать список показателей выбранного датчика
    public static void showValues(long house_id, long room_id, long sensor_id) {
        // переменная для таблицы из БД
        DB_content<Value> values =  new DB_content<>();

        // запрос к БД и получение комнат дома с №id
        values.get_content("SELECT * FROM Значение WHERE ИД_Датчика = " + sensor_id, Value.class);

        System.out.println("ВЫБОР ПОКАЗАНИЯ\n");
        System.out.println(" 'Exit' - выход");
        System.out.println(" '-'    - удалить показание");
        System.out.println(" '+'    - добавить показание");
        System.out.println(" '<-'   - назад к датчикам\n");
        System.out.println("Показания:");

        // вывести список записей
        for (int i = 0; i < values.content.size(); i++) {
            // строка с показанием
            System.out.println(" " + i + ". - " + values.content.get(i).getDate() + " "
                    + values.content.get(i).getTime() + " " + values.content.get(i).getReading());
        }
        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.toString();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить комнату
                    System.out.println("Введите номер удаляемого значения:");
                    String value_id = keyboard.toString();
                    values.del_content("УДАЛИТЬ Значение WHERE ИД_Значения = " + value_id);
                    showSensors(room_id, sensor_id);
                }
                case ("<-"): { // назад
                    showSensors(room_id, house_id);
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Дата:");
                    String date = keyboard.toString();

                    System.out.println("Время:");
                    String time = keyboard.toString();

                    System.out.println("Показание:");
                    String reading = keyboard.toString();

                    // экземпляр Value для добавления в БД
                    Value value = new Value();
                    value.setDate(date);
                    value.setTime(time);
                    value.setReading(Float.parseFloat(reading));

                    // value.setSensor(); !!!

                    // добавление новой записи
                    values.set_content(value);

                    return;
                }
                default: { }
            }
        }
    }
}