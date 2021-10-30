import entity.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Main {

    public static SessionFactory sessionFactory = null;
    public static Session session = null;

    // создание сессии
    public static SessionFactory createSessionFactory() {
        StandardServiceRegistry registry = null;
        try {
            registry = new StandardServiceRegistryBuilder().configure().build();
            MetadataSources sources = new MetadataSources(registry);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            if (registry != null) {
                StandardServiceRegistryBuilder.destroy(registry);
            }
        }
        return sessionFactory;
    }

    // ввод и проверка числовых данных (номера объекта)
    public static Integer readInt(String message, String messageError, int size)
    {
        int id = 0;
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.println(message);
            try {
                id = Integer.parseInt(keyboard.nextLine());
                if (id >= 0 && id < size) break;
                System.out.println(messageError);
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return id;
    }

    // ввод и проверка строковых данных (названия)
    public static String readString(String message)
    {
        String st;
        Scanner keyboard = new Scanner(System.in);
        while(true) {
            System.out.println(message);
            try {
                st = keyboard.nextLine();
                break;
            }
            catch (Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
        return st;
    }

    // ввод и проверка даты и времени
    public static Date readDate(String message, String format)
    {
        Date date;
        while(true) {
            System.out.println(message);
            Scanner keyboard = new Scanner(System.in);
            String d = keyboard.nextLine();
            try {
                date = new SimpleDateFormat(format).parse(d);
                break;
            } catch (ParseException e) {
                System.out.println("Неверный формат: "+ e.getMessage());
            }
        }
        return date;
    }

    public static void main(String[] args) {
        // показать список домов
        showHouses();
        session.close();
    }

    // показать список домов
    public static void showHouses() {
        // переменная для таблицы из БД
        DbContent houses = new DbContent();
        if (session == null) {
            try{
                session = createSessionFactory().openSession();
            } catch (Exception e) {
                System.out.print("Сессия не создана: " + e.getMessage());
            }
        }
        // запрос к БД и получение таблицы в "houses"
        houses.getContent("FROM entity.House", session);
        System.out.println("--- ДОМА ---");
        System.out.println(" Доступные действия:");
        System.out.println("   Exit    - выход");
        System.out.println("   +       - добавить дом");
        // если множество домов не пусто
        if (houses.content.size() != 0) {
            System.out.println("   -       - удалить дом");
            System.out.println("   Edit    - редактировать дом\n");
            System.out.println(" Список домов:");
            // вывести список записей
            for (int i = 0; i < houses.content.size(); i++) {
                // строка с номером, адресом дома
                System.out.println("   " + i + " - Ул. " + ((House) houses.content.get(i)).getStreet() + " Дом "
                        + ((House) houses.content.get(i)).getNumber());
            }
        }
        // пока не введено корректное действие
        while (true) {
            String resp = readString(" Введите действие или номер дома:");
            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("+"): { // добавление записи
                    String street = readString(" Введите улицу: ");
                    String num = readString(" Введите номер дома: ");
                    // экземпляр House для добавления в БД
                    House house = new House();
                    house.setStreet(street);
                    house.setNumber(num);
                    // добавление новой записи
                    houses.setContent(house, session);
                    showHouses();
                    break;
                }
                case ("-"): { // удалить дом
                    if (houses.content.size() == 0) {
                        System.out.println("Домов нет.");
                        break;
                    }
                    Integer houseId = readInt(" Введите номер удаляемого дома: ",
                            "Дома с таким номером нет.",
                            houses.content.size());
                    houses.delContent(houses.content.get(houseId), session);
                    showHouses();
                    break;
                }
                case ("Edit"): { // редактировать дом
                    if (houses.content.size() == 0) {
                        System.out.println("Домов нет.");
                        break;
                    }
                    Integer houseId = readInt(" Введите номер редактируемого дома: ",
                            "Дома с таким номером нет.",
                            houses.content.size());
                    String field = readString(" Выберите поле для редактирования:\n" +
                            "   1    - Улица\n" +
                            "   2    - Номер дома\n" +
                            "   back - Отмена\n" +
                            " Ввод: ");
                    // изменить значение
                    switch (field) {
                        case "1": { // Улица
                            String val = readString(" Введите новую улицу:");
                            House temp = (House)houses.content.get(houseId);
                            temp.setStreet(val);
                            houses.updateContent(temp, session);
                            break;
                        }
                        case "2": { // Номер дома
                            String val = readString(" Введите новый номер дома:");
                            House temp = (House)houses.content.get(houseId);
                            temp.setNumber(val);
                            houses.updateContent(temp, session);
                            break;
                        }
                        case "back": { break; }
                        default:
                            System.out.println("Такого параметра нет");
                    }
                    showHouses();
                    break;
                }
                default: {
                    try {
                        int id = Integer.parseInt(resp);
                        if (id >= 0 && id < houses.content.size()) {
                            // показать список комнат выбраного дома
                            showRooms(((House) houses.content.get(id)));
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Такой опции нет.");
                    }
                }
            }
        }
    }

    // показать список комнат выбраного дома
    public static void showRooms(House house) {
        // переменная для таблицы из БД
        DbContent<Room> rooms =  new DbContent<>();
        // запрос к БД и получение комнат дома с №id
        rooms.getContent("FROM entity.Room WHERE houseId = " + house.getId(), session);
        System.out.println("--- КОМНАТЫ ДОМА по адресу: " +
                "улица " + house.getStreet() + " дом " + house.getNumber() + " ---");
        System.out.println(" Доступные действия:");
        System.out.println("   Exit    - выход");
        System.out.println("   <-      - назад к домам");
        System.out.println("   +       - добавить комнату");
        if (rooms.content.size() != 0) {
            System.out.println("   -       - удалить комнату");
            System.out.println("   Edit    - редактировать комнату\n");
            System.out.println(" Список комнат:");

            // вывести список записей
            for (int i = 0; i < rooms.content.size(); i++) {
                // строка с названием комнаты
                System.out.println("   " + i + " - " + rooms.content.get(i).getName() + " (id "
                        + rooms.content.get(i).getId() + ")");
            }
        }
        // пока не введено корректное действие
        while (true) {
            String resp = readString(" Введите действие или номер комнаты:");
            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("<-"): { // назад
                    showHouses();
                    break;
                }
                case ("-"): { // удалить комнату
                    if (rooms.content.size() == 0) {
                        System.out.println("Комнат нет.");
                        break;
                    }
                    Integer roomId = readInt(" Введите номер удаляемой комнаты: ",
                            "Комнаты с таким номером нет.",
                            rooms.content.size());
                    house.delRooms(rooms.content.get(roomId));
                    rooms.delContent(rooms.content.get(roomId), session);
                    showRooms(house);
                    break;
                }
                case ("+"): { // добавление записи
                    String roomName = readString(" Введите название комнаты: ");
                    // экземпляр Room для добавления в таблицу
                    Room room = new Room();
                    room.setName(roomName);
                    room.setHouse(house);
                    // добавление новой записи
                    rooms.setContent(room, session);
                    house.addRooms(room);
                    showRooms(house);
                    break;
                }
                case ("Edit"): { // редактировать комнату
                    if (rooms.content.size() == 0) {
                        System.out.println("Комнат нет.");
                        break;
                    }
                    Integer roomId = readInt(" Введите номер редактируемой комнаты: ",
                            "Комнаты с таким номером нет.",
                            rooms.content.size());
                    String val = readString(" Введите новое название комнаты (или back для отмены): ");
                    if (val.equals("back")) break;
                    Room temp = rooms.content.get(roomId);
                    temp.setName(val);
                    rooms.updateContent(temp, session);
                    showRooms(house);
                    break;
                }
                default: {
                    try {
                        int roomId = Integer.parseInt(resp);
                        if (roomId >= 0 && roomId < rooms.content.size()) {
                            // показать список датчиков выбраной комнаты
                            showSensors(rooms.content.get(roomId), house);
                            break;
                        }
                    } catch (Exception e) {
                        System.out.println("Такой опции нет.");
                    }
                }
            }
        }
    }

    // показать список датчиков выбраной комнаты
    public static void showSensors(Room room, House house) {
        // переменная для таблицы из БД
        DbContent<Sensor> sensors =  new DbContent<>();
        // запрос к БД и получение датчиков в комнате
        sensors.getContent("FROM entity.Sensor WHERE roomId = " + room.getId(), session);
        System.out.println("--- ДАТЧИКИ КОМНАТЫ '" + room.getName() +
                "' ДОМА ПО АДРЕСУ: улица " + house.getStreet() + " дом " + house.getNumber() + " ---");
        System.out.println(" Доступные действия");
        System.out.println("   Exit    - выход");
        System.out.println("   <-      - назад к комнатам");
        System.out.println("   +       - добавить датчик");
        if (sensors.content.size() != 0) {
            System.out.println("   -       - удалить датчик");
            System.out.println("   Edit    - редактировать датчик\n");
            System.out.println(" Список датчиков:");
            // вывести список записей
            for (int i = 0; i < sensors.content.size(); i++) {
                // строка с данными датчика
                System.out.println("   " + i + " - " + sensors.content.get(i).getParameter() +
                        " (id: " + sensors.content.get(i).getId() + ") ");
            }
        }
        // пока не введено корректное действие
        while (true) {
            String resp = readString(" Введите действие или номер датчика:");
            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить датчик
                    if (sensors.content.size() == 0) {
                        System.out.println("Датчиков нет.");
                        break;
                    }
                    Integer sensorId = readInt(" Введите номер удаляемого датчика",
                            "Датчика с таким номером нет.",
                            sensors.content.size());
                    room.delSensor(sensors.content.get(sensorId));
                    sensors.delContent(sensors.content.get(sensorId), session);
                    showSensors(room, house);
                    break;
                }
                case ("<-"): { // назад
                    showRooms(house);
                    break;
                }
                case ("+"): { // добавление записи
                    String sensorParam = readString(" Укажите физическую величину датчика: ");
                    String sensorPhys = readString(" Укажите единицу измерения датчика: ");
                    // экземпляр Sensor для добавления в БД
                    Sensor sensor = new Sensor();
                    sensor.setParameter(sensorParam);
                    sensor.setPhys(sensorPhys);
                    sensor.setRoom(room);
                    // добавление новой записи
                    sensors.setContent(sensor, session);
                    room.addSensor(sensor);
                    showSensors(room, house);
                    break;
                }
                case ("Edit"): { // редактировать датчик
                    if (sensors.content.size() == 0) {
                        System.out.println("Датчиков нет.");
                        break;
                    }
                    Integer sensorId = readInt(" Введите номер редактируемого датчика",
                            "Датчика с таким номером нет.",
                            sensors.content.size());

                    String field = readString(" Выберите поле для редактирования:\n" +
                            "   1    - Физическая величина\n" +
                            "   2    - Единица измерения\n" +
                            "   back - Отмена" +
                            " Ввод: ");
                    String val = readString(" Введите новое значение:");
                    Sensor temp = (Sensor)sensors.content.get(sensorId);
                    // изменить значение
                    switch (field) {
                        case "1": { // физ. велечина
                            temp.setParameter(val);
                            sensors.updateContent(temp, session);
                            break;
                        }
                        case "2": { // единица измерения
                            temp.setPhys(val);
                            sensors.updateContent(temp, session);
                            break;
                        }
                        case "back": {
                            break;
                        }
                        default: {
                            System.out.println("Такого параметра нет");
                        }
                    }
                    showSensors(room, house);
                    break;
                }
                default: {
                    try {
                        int sensorId = Integer.parseInt(resp);
                        if (sensorId >= 0 && sensorId < sensors.content.size()) {
                            // показать список значений выбранного датчика
                            showValues(house, room, sensors.content.get(sensorId));
                            return;
                        }
                    } catch (Exception e) {
                        System.out.println("Такой опции нет.");
                    }
                }
            }
        }
    }

    // показать список показателей выбранного датчика
    public static void showValues(House house, Room room, Sensor sensor) {
        // переменная для таблицы из БД
        DbContent<Value> values =  new DbContent<>();
        // запрос к БД и получение комнат дома с №id
        values.getContent("FROM entity.Value WHERE sensorId = " + sensor.getId(), session);
        System.out.println("--- ПОКАЗАНИЯ ДАТЧИКА id " + sensor.getId() + " (" + sensor.getParameter() +
                ") КОМНАТЫ '" + room.getName() +
                "' ДОМА ПО АДРЕСУ: улица " + house.getStreet() + " дом " + house.getNumber() + " ---");
        System.out.println(" Список доступных действий: ");
        System.out.println("   Exit - выход");
        System.out.println("   <-   - назад к датчикам");
        System.out.println("   +    - добавить показание");
        if (values.content.size() != 0) {
            System.out.println("   -    - удалить показание");
            System.out.println("   Edit    - редактировать датчик\n");
            System.out.println("Список показаний:");
            // вывести список записей
            for (int i = 0; i < values.content.size(); i++) {
                // строка с показанием
                System.out.println("   " + i + " - " + values.content.get(i).getDate() +
                        " (" + values.content.get(i).getTime() + ") " + values.content.get(i).getReading() +
                        " " + sensor.getPhys());
            }
        }
        // пока не введено корректное действие
        while (true) {
            String resp = readString(" Введите действие или номер показания: ");
            switch (resp) {
                case ("Exit"): { // выход
                    System.exit(0);
                }
                case ("-"): { // удалить комнату
                    if (values.content.size() == 0) {
                        System.out.println("Показаний нет.");
                        break;
                    }
                    Integer valueId = readInt(" Введите номер удаляемого значения: ",
                            "Значения с таким номером нет.",
                            values.content.size());
                    sensor.delValue(values.content.get(valueId));
                    values.delContent(values.content.get(valueId), session);
                    showValues(house, room, sensor);
                }
                case ("<-"): { // назад
                    showSensors(room, house);
                    break;
                }
                case ("+"): { // добавление записи
                    Date date = readDate("Дата в формате dd.mm.yyyy: ", "dd.MM.yyyy");
                    Date time = readDate("Время в формате hh:mm: ", "hh:mm");
                    String reading = readString("Показание:");
                    // экземпляр Value для добавления в БД
                    Value value = new Value();
                    value.setDate(date);
                    value.setTime(time);
                    value.setReading(Float.parseFloat(reading));
                    value.setSensor(sensor);
                    values.setContent(value, session);
                    sensor.addValue(value);
                    // добавление новой записи
                    values.setContent(value, session);
                    showValues(house, room, sensor);
                    return;
                }
                case ("Edit"): { // редактировать показание
                    if (values.content.size() == 0) {
                        System.out.println("Показаний нет.");
                        break;
                    }
                    Integer valueId = readInt(" Введите номер редактируемого значения:",
                            "Показания с таким номером нет",
                            values.content.size());
                    String field = readString(" Выберите поле для редактирования:\n" +
                            "   1    - Дата\n" +
                            "   2    - Время\n" +
                            "   3    - Показание\n" +
                            "   back - Отмена\n" +
                            " Ввод: ");
                    Value temp = values.content.get(valueId);
                    switch (field) {
                        case "1": { // дата
                            Date date = readDate("Дата в формате dd.mm.yyyy: ","dd.MM.yyyy");
                            temp.setDate(date);
                        }
                        case "2": { // время
                            Date time = readDate("Время в формате hh:mm: ", "HH.mm");
                            temp.setTime(time);
                        }
                        case "3": { // показание
                            String val = readString(" Введите новое значение: ");
                            temp.setReading(Integer.parseInt(val));
                        }
                        case "back": { // отмена
                            break;
                        }
                    }
                    values.updateContent(temp, session);
                    showValues(house, room, sensor);
                    break;
                }
                default: System.out.println("Такой опции нет.");
            }
        }
    }
}