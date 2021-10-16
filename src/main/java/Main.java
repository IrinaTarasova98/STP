import java.util.Scanner;
import entity.*;

public class Main {

    public static void main(String[] args) {
        // показать список домов
        showHouses();
    }

    // показать список домов
    public static void showHouses() {

        // переменная для таблицы из БД
        DbContent houses = new DbContent();

        // запрос к БД и получение таблицы в "houses"
        houses.getContent("FROM HOUSE");

        System.out.println("ВЫБОР ДОМА\n");
        System.out.println(" 'Exit'    - выход\n");
        System.out.println(" '+'       - добавить дом");

        // если множество домов не пусто
        if (houses.content != null) {
            System.out.println(" '-'       - удалить дом");
            System.out.println(" 'Edit'    - редактировать дом\n");

            System.out.println("Список домов:");

            // вывести список записей
            for (int i = 0; i < houses.content.size(); i++) {
                // строка с номером, адресом дома
                System.out.println(" " + i + ". - " + ((House) houses.content.get(i)).getStreet() + " "
                        + ((House) houses.content.get(i)).getNumber());
            }
        }

        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");

            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.nextLine();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("+"): { // добавление записи
                    System.out.println("Введите улицу:");
                    String street = keyboard.nextLine();
                    System.out.println("Введите номер дома:");
                    String num = keyboard.nextLine();

                    System.out.println(street + " " + num);

                    // экземпляр House для добавления в БД
                    House house = new House();
                    house.setStreet(street);
                    house.setNumber(num);

                    // добавление новой записи
                    houses.setContent(house);
                    showHouses();
                    break;
                }
                case ("-"): { // удалить дом
                    System.out.println("Введите номер удаляемого дома:");
                    String houseId = keyboard.nextLine();
                    // DELETE FROM HOUSE WHERE houseId = houseId
                    houses.delContent("HOUSE", "houseId", houseId);
                    showHouses();
                    break;
                }
                case ("Edit"): { // редактировать дом
                    System.out.println("Введите номер редактируемого дома:");
                    String houseId = keyboard.nextLine();
                    System.out.println("Выберите поле для редактирования:");
                    System.out.println("1. Улица");
                    System.out.println("2. Номер дома");
                    String field = keyboard.nextLine();
                    // выяснить имя редактируемого поля
                    if (field == "1")  field = "street";
                    else if (field == "2") field = "number";
                    else break;
                    System.out.println("Введите новое значение:");
                    String val = keyboard.nextLine();
                    // попытка обновить запись
                    houses.updateContent("HOUSE", field, val, "houseId", houseId);
                    showHouses();
                    break;
                }
                default: {
                    int id = Integer.parseInt(resp);
                    if (id >= 0 && id <= houses.content.size()) {
                        // показать список комнат выбраного дома
                        showRooms(((House)houses.content.get(id)));
                        break;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }

    }

    // показать список комнат выбраного дома
    public static void showRooms(House house) {
        // переменная для таблицы из БД
        DbContent<Room> rooms =  new DbContent<>();

        // запрос к БД и получение комнат дома с №id
        rooms.getContent("FROM ROOM WHERE ИД_Дома = " + house.getId());

        System.out.println("ВЫБОР КОМНАТЫ\n");
        System.out.println(" 'Exit'    - выход");
        System.out.println(" '<-'      - назад к домам\n");
        System.out.println(" '+'       - добавить комнату");

        if (rooms.content != null) {
            System.out.println(" '-'       - удалить комнату");
            System.out.println(" 'Edit'    - редактировать комнату\n");
            System.out.println("Комнаты:");

            // вывести список записей
            for (int i = 0; i < rooms.content.size(); i++) {
                // строка с названием комнаты
                System.out.println(" " + i + ". - " + rooms.content.get(i).getName() + " "
                        + rooms.content.get(i).getId());
            }
        }

        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            keyboard.nextLine();
            String resp = keyboard.nextLine();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("<-"): { // назад
                    showHouses();
                    break;
                }
                case ("-"): { // удалить комнату
                    System.out.println("Введите номер удаляемой комнаты:");
                    String roomId = keyboard.nextLine();
                    rooms.delContent("ROOM", "roomId", roomId);
                    showRooms(house);
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Название комнаты:");
                    String roomName = keyboard.nextLine();

                    // экземпляр Room для добавления в таблицу
                    Room room = new Room();
                    room.setName(roomName);
                    house.addRooms(room);

                    // добавление новой записи
                    rooms.setContent(room);
                    showRooms(house);
                    break;
                }
                case ("Edit"): { // редактировать комнату
                    System.out.println("Введите номер редактируемой комнаты:");
                    String roomId = keyboard.nextLine();
                    System.out.println("Введите новое имя комнаты:");
                    String val = keyboard.nextLine();
                    // попытка обновить запись
                    rooms.updateContent("ROOM", "name", val, "roomId", roomId);
                    showRooms(house);
                    break;
                }
                default: {
                    int roomId = Integer.parseInt(resp);
                    if (roomId > 0 && roomId <= rooms.content.size()) {
                        // показать список датчиков выбраной комнаты
                        showSensors(rooms.content.get(roomId), house);
                        return;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }
    }

    // показать список датчиков выбраной комнаты
    public static void showSensors(Room room, House house) {
        // переменная для таблицы из БД
        DbContent<Sensor> sensors =  new DbContent<>();

        // запрос к БД и получение датчиков в комнате
        sensors.getContent("FROM SENSOR WHERE roomId = " + room.getId());

        System.out.println("ВЫБОР ДАТЧИКА\n");
        System.out.println(" 'Exit'    - выход");
        System.out.println(" '<-'      - назад к комнатам\n");
        System.out.println(" '+'       - добавить датчик");

        if (sensors.content != null) {
            System.out.println(" '-'       - удалить датчик");
            System.out.println(" 'Edit'    - редактировать датчик\n");
            System.out.println("Датчики:");

            // вывести список записей
            for (int i = 0; i < sensors.content.size(); i++) {
                // строка с данными датчика
                System.out.println(" " + i + ". - " + sensors.content.get(i).getId() + " "
                        + sensors.content.get(i).getParameter());
            }
        }

        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.nextLine();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить датчик
                    System.out.println("Введите номер удаляемого датчика:");
                    String sensorId = keyboard.nextLine();
                    sensors.delContent("SENSOR", "sensorId", sensorId);
                    showSensors(room, house);
                    break;
                }
                case ("<-"): { // назад
                    showRooms(house);
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Физическая величина датчика:");
                    String sensorParam = keyboard.nextLine();

                    // экземпляр Sensor для добавления в БД
                    Sensor sensor = new Sensor();
                    sensor.setParameter(sensorParam);
                    room.addSensors(sensor);

                    // добавление новой записи
                    sensors.setContent(sensor);
                    showSensors(room, house);
                    break;
                }
                case ("Edit"): { // редактировать датчик
                    System.out.println("Введите номер редактируемого сенсора:");
                    String sensorId = keyboard.nextLine();
                    System.out.println("Введите новую физическую велечину:");
                    String param = keyboard.nextLine();
                    // попытка обновить запись
                    sensors.updateContent("SENSOR", "parameter", param, "sensorId", sensorId);
                    showSensors(room, house);
                    break;
                }
                default: {
                    int sensorId = Integer.parseInt(resp);
                    if (sensorId > 0 && sensorId <= sensors.content.size()) {
                        // показать список значений выбранного датчика
                        showValues(house, room, sensors.content.get(sensorId));
                        return;
                    } else System.out.println("Неправильная опция.");
                }
            }
        }
    }

    // показать список показателей выбранного датчика
    public static void showValues(House house, Room room, Sensor sensor) {
        // переменная для таблицы из БД
        DbContent<Value> values =  new DbContent<>();

        // запрос к БД и получение комнат дома с №id
        values.getContent("FROM VALUE WHERE sensorId = " + sensor.getId());

        System.out.println("ВЫБОР ПОКАЗАНИЯ\n");
        System.out.println(" 'Exit' - выход");
        System.out.println(" '<-'   - назад к датчикам\n");
        System.out.println(" '+'    - добавить показание");

        if (values.content != null) {
            System.out.println(" '-'    - удалить показание");
            System.out.println(" 'Edit'    - редактировать датчик\n");
            System.out.println("Показания:");

            // вывести список записей
            for (int i = 0; i < values.content.size(); i++) {
                // строка с показанием
                System.out.println(" " + i + ". - " + values.content.get(i).getDate() + " "
                        + values.content.get(i).getTime() + " " + values.content.get(i).getReading());
            }
        }

        // пока не введено корректное действие
        while (true) {
            System.out.println("Введите действие:");
            Scanner keyboard = new Scanner(System.in);
            String resp = keyboard.nextLine();

            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить комнату
                    System.out.println("Введите номер удаляемого значения:");
                    String valueId = keyboard.nextLine();
                    values.delContent("VALUE", "valueId", valueId);
                    showValues(house, room, sensor);
                }
                case ("<-"): { // назад
                    showSensors(room, house);
                    break;
                }
                case ("+"): { // добавление записи
                    System.out.println("Дата:");
                    String date = keyboard.nextLine();

                    System.out.println("Время:");
                    String time = keyboard.nextLine();

                    System.out.println("Показание:");
                    String reading = keyboard.nextLine();

                    // экземпляр Value для добавления в БД
                    Value value = new Value();
                    value.setDate(date);
                    value.setTime(time);
                    value.setReading(Float.parseFloat(reading));
                    sensor.addValues(value);
                    // добавление новой записи
                    values.setContent(value);
                    showValues(house, room, sensor);
                    return;
                }
                case ("Edit"): { // редактировать показание
                    System.out.println("Введите номер редактируемого значения:");
                    String valueId = keyboard.nextLine();
                    System.out.println("Выберите поле для редактирования:");
                    System.out.println("1. Дата");
                    System.out.println("2. Время");
                    System.out.println("3. Показание");
                    String field = keyboard.nextLine();
                    // выяснить имя редактируемого поля
                    if (field == "1")  field = "date";
                    else if (field == "2") field = "time";
                    else if (field == "3") field = "reading";
                    else break;

                    System.out.println("Введите новое значение:");
                    String val = keyboard.nextLine();
                    // попытка обновить запись
                    values.updateContent("VALUE", field, val, "valueId", valueId);
                    showValues(house, room, sensor);
                    break;
                }
                default: { }
            }
        }
    }

}