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

    // открытие сессии для работы с БД
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

    // основная функция программы
    public static void main(String[] args) {
        try{
            session = createSessionFactory().openSession();
        } catch (Exception e) {
            System.out.print("Сессия не создана: " + e.getMessage());
            System.exit(0);
        }
        // показать список домов
        manageHouses();
        session.close();
    }

    //=============== Функции для ввода данных ===============//

    // ввод и проверка числовых данных (номера объекта)
    public static Integer readInt(String message, String messageError, int size) {
        int id;
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
                System.out.println(messageError);
            }
        }
        return id;
    }

    // ввод и проверка строковых данных (названия)
    public static String readString(String message) {
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
                System.out.println("Данные введены неверно: " + e.getMessage());
            }
        }
        return st;
    }

    // ввод и проверка даты и времени
    public static Date readDate(String message, String format) {
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

    //============= Функции для работы с данными =============//

    // общая функция вывода меню
    public static <T> DbContent<T> menu(String tableName, String query) {
        // запрос к БД и получение таблицы
        DbContent<T> table = new DbContent<>();
        table.getContent(query, session);
        System.out.println("\n\n--- " + tableName + " ---");
        System.out.println(" Доступные действия:");
        System.out.println("   Exit    - выход");
        System.out.println("   +       - добавить");
        if (!tableName.equals("ДОМА")) {
            System.out.println("   <-      - назад");
        }
        // если множество записей не пусто
        if (table.content.size() > 0) {
            System.out.println("   -       - удалить");
            System.out.println("   Edit    - редактировать\n");
        }
        return table;
    }

    // общая функция получения номера удаляемого элемента
    public static int delElement(DbContent<?> table, String tableName) {
        if (table.content.size() == 0) {
            System.out.println("Элементов для удаления нет.");
        }
        return readInt((" Введите номер удаляемого " + tableName + ": "),
                "Элемента с таким номером нет.",
                table.content.size());
    }

    // проверяет наличие элемента с заданным номером
    public static int checkElement(DbContent<?> table, String resp) {
        try {
            int id = Integer.parseInt(resp);
            if (id >= 0 && id < table.content.size()) return id;
            else System.out.println("Такой опции нет.");
        } catch (Exception e) {
            System.out.println("Такой опции нет.");
        }
        return -1;
    }

    //=============== Функции создания объектов ===============//

    // функция создания нового дома
    public static House newHouse() {
        String street = readString(" Введите улицу: ");
        String num = readString(" Введите номер дома: ");
        // экземпляр House для добавления в БД
        House house = new House();
        house.setStreet(street);
        house.setNumber(num);
        return house;
    }

    // функция создания новой комнаты
    public static Room newRoom(House house) {
        String roomName = readString(" Введите название комнаты: ");
        Room room = new Room();
        room.setName(roomName);
        room.setHouse(house);
        return room;
    }

    // функция создания нового датчика
    public static Sensor newSensor(Room room) {
        Sensor sensor = new Sensor();
        String sensorParam = readString(" Укажите физическую величину датчика: ");
        String sensorPhys = readString(" Укажите единицу измерения датчика: ");
        sensor.setParameter(sensorParam);
        sensor.setPhys(sensorPhys);
        sensor.setRoom(room);
        return sensor;
    }

    // функция создания нового показания
    public static Value newValue(Sensor sensor) {
        Date date = readDate("Дата в формате dd.mm.yyyy: ", "dd.MM.yyyy");
        Date time = readDate("Время в формате hh:mm: ", "hh:mm");
        String reading = readString("Показание:");
        Value value = new Value();
        value.setDate(date);
        value.setTime(time);
        value.setReading(Float.parseFloat(reading));
        value.setSensor(sensor);
        return value;
    }

    //============ Функции редактирования объектов ============//

    // функция редактирования дома
    public static House editHouse(DbContent<House> houses) {
        if (houses.content.size() == 0) {
            System.out.println("Домов нет.");
            return null;
        }
        Integer houseId = readInt(" Введите номер редактируемого дома: ",
                "Дома с таким номером нет.",
                houses.content.size());
        House temp = houses.content.get(houseId);
        while (true) {
            String field = readString(" Выберите поле для редактирования:\n" +
                    "   1    - Улица\n" +
                    "   2    - Номер дома\n" + "   back - Отмена\n" + " Ввод: ");
            switch (field) {
                case "1": // Улица
                    String street = readString(" Введите новую улицу:");
                    temp.setStreet(street);
                    return temp;
                case "2": // Номер дома
                    String num = readString(" Введите новый номер дома:");
                    temp.setNumber(num);
                    return temp;
                case "back":
                    return null;
                default:
                    System.out.println("Такого параметра нет");
            }
        }
    }

    // функция редактирования комнаты
    public static Room editRoom(DbContent<Room> rooms) {
        if (rooms.content.size() == 0) {
            System.out.println("Комнат нет.");
            return null;
        }
        Integer roomId = readInt(" Введите номер редактируемой комнаты: ",
                "Комнаты с таким номером нет.",
                rooms.content.size());
        String val = readString(" Введите новое название комнаты (или back для отмены): ");
        if (val.equals("back")) return null;
        Room temp = rooms.content.get(roomId);
        temp.setName(val);
        return temp;
    }

    // функция редактирования датчика
    public static Sensor editSensor(DbContent<Sensor> sensors) {
        if (sensors.content.size() == 0) {
            System.out.println("Датчиков нет.");
            return null;
        }
        Integer sensorId = readInt(" Введите номер редактируемого датчика: ",
                "Датчика с таким номером нет.",
                sensors.content.size());
        while (true) {
            String field = readString(" Выберите поле для редактирования:\n" +
                    "   1    - Физическая величина\n" +
                    "   2    - Единица измерения\n" +
                    "   back - Отмена\n" + " Ввод: ");
            Sensor temp = sensors.content.get(sensorId);
            switch (field) {
                case "1": // физ. велечина
                    String param = readString(" Введите новое значение:");
                    temp.setParameter(param);
                    return temp;
                case "2": // единица измерения
                    String phys = readString(" Введите новое значение:");
                    temp.setPhys(phys);
                    return temp;
                case "back":
                    return null;
                default:
                    System.out.println("Такого параметра нет");
            }
        }
    }

    // функция редактирования показания
    public static Value editValue(DbContent<Value> values) {
        if (values.content.size() == 0) {
            System.out.println("Показаний нет.");
            return null;
        }
        Integer valueId = readInt(" Введите номер редактируемого значения:",
                "Показания с таким номером нет",
                values.content.size());
        while (true) {
            String field = readString(" Выберите поле для редактирования:\n" +
                    "   1    - Дата\n" + "   2    - Время\n" +
                    "   3    - Показание\n" + "   back - Отмена\n" +
                    " Ввод: ");
            Value temp = values.content.get(valueId);
            switch (field) {
                case "1": // дата
                    Date date = readDate("Дата в формате dd.mm.yyyy: ", "dd.MM.yyyy");
                    temp.setDate(date);
                    return temp;
                case "2": // время
                    Date time = readDate("Время в формате hh:mm: ", "HH:mm");
                    temp.setTime(time);
                    return temp;
                case "3": // показание
                    String val = readString(" Введите новое значение: ");
                    temp.setReading(Integer.parseInt(val));
                    return temp;
                case "back": // отмена
                    return null;
            }
        }
    }

    //=========== Функции вывода списков в консоль ===========//

    // функция вывода на экран список домов
    public static void showHouses(DbContent<House> houses) {
        // если записи есть
        if (houses.content.size() == 0) {
            System.out.println("\nЗаписей нет.");
            return;
        }
        System.out.println(" Список домов:");
        for (int i = 0; i < houses.content.size(); i++) {
            // [номер] - Ул. [улица] Дом [номер дома]
            System.out.println("   " + i + " - Ул. " +
                    houses.content.get(i).getStreet() + " Дом "
                    + houses.content.get(i).getNumber());
        }
    }

    // функция вывода на экран список комнат
    public static void showRooms(DbContent<Room> rooms) {
        if (rooms.content.size() == 0) {
            System.out.println("\nЗаписей нет.");
            return;
        }
        System.out.println(" Список комнат:");
        for (int i = 0; i < rooms.content.size(); i++) {
            // [номер комнаты] - [название комнаты] [id комнаты]
            System.out.println("   " + i + " - " +
                    rooms.content.get(i).getName() + " (id "
                    + rooms.content.get(i).getId() + ")");
        }
    }

    // функция вывода на экран список датчиков
    public static void showSensors(DbContent<Sensor> sensors) {
        if (sensors.content.size() == 0) {
            System.out.println("\nЗаписей нет.");
            return;
        }
        System.out.println(" Список датчиков:");
        for (int i = 0; i < sensors.content.size(); i++) {
            // [номер датчика] - [измеряемый параметр] (id: [id датчика])
            System.out.println("   " + i + " - " +
                    sensors.content.get(i).getParameter() +
                    " (id: " + sensors.content.get(i).getId() + ") ");
        }
    }

    // функция вывода на экран список покзаний
    public static void showValues(DbContent<Value> values, String phys) {
        if (values.content.size() == 0) {
            System.out.println("\nЗаписей нет.");
            return;
        }
        System.out.println(" Список показаний:");
        for (int i = 0; i < values.content.size(); i++) {
            // [номер показания] - [дата] ([время]) [показание] [единица измерения]
            System.out.println("   " + i + " - " +
                    values.content.get(i).getDate() +
                    " (" + values.content.get(i).getTime() + ") " +
                    values.content.get(i).getReading() +
                    " " + phys);
        }
    }

    //========= Функции для работы с уровнями модели =========//

    // управление домами
    public static void manageHouses() {
        while (true) {
            int f = 0;
            // переменная для таблицы из БД
            DbContent <House> houses = menu("ДОМА", "FROM entity.House");
            showHouses(houses); // вывести список домов
            // пока не введено корректное действие
            while (f == 0) {
                f = 1;
                String resp = readString(" Введите действие или номер дома:");
                switch (resp) {
                    case ("Exit"): // выход
                        System.exit(0);
                    case ("+"): // добавление записи
                        houses.setContent(newHouse(), session);
                        break;
                    case ("-"): // удалить дом
                        houses.delContent(houses.content.get(delElement(houses, "дома")), session);
                        break;
                    case ("Edit"): // редактировать дом
                        houses.updateContent(editHouse(houses), session);
                        break;
                    default:
                        int i = checkElement(houses, resp);
                        if (i >= 0) manageRooms(houses.content.get(i));
                        else f = 0;
                        break;
                }
            }
        }
    }

    // управление комнатами
    public static void manageRooms(House house) {
        while (true) {
            int f = 0;
            // переменная для таблицы из БД
            DbContent<Room> rooms = menu(
                    "КОМНАТЫ ДОМА ПО АДРЕСУ: " +
                            "улица " + house.getStreet() + " дом " + house.getNumber()
                    , "FROM entity.Room WHERE houseId = " + house.getId());
            showRooms(rooms); // вывести список комнат
            // пока не введено корректное действие
            while (f == 0) {
                f = 1;
                String resp = readString(" Введите действие или номер комнаты:");
                switch (resp) {
                    case ("Exit"): // выход
                        System.exit(0);
                    case ("<-"): // назад
                        return;
                    case ("-"): // удалить комнату
                        rooms.delContent(rooms.content.get(delElement(rooms, "комнаты")), session);
                        break;
                    case ("+"): // добавление записи
                        rooms.setContent(newRoom(house), session);
                        break;
                    case ("Edit"): // редактировать комнату
                        rooms.updateContent(editRoom(rooms), session);
                        break;
                    default:
                        int i = checkElement(rooms, resp);
                        if (i >= 0) manageSensors(house, (rooms.content.get(i)));
                        else f = 0;
                        break;
                }
            }
        }
    }

    // управление датчиками
    public static void manageSensors(House house, Room room) {
        while (true) {
            int f = 0;
            // переменная для таблицы из БД
            DbContent<Sensor> sensors = menu(
                    "ДАТЧИКИ КОМНАТЫ '" + room.getName() +
                            "' ДОМА ПО АДРЕСУ: улица " + house.getStreet() +
                            " дом " + house.getNumber()
                    , "FROM entity.Sensor WHERE roomId = " + room.getId());
            showSensors(sensors);
            // пока не введено корректное действие
            while (f == 0) {
                f = 1;
                String resp = readString(" Введите действие или номер датчика:");
                switch (resp) {
                    case ("Exit"): // выход
                        System.exit(0);
                    case ("<-"): // назад
                        return;
                    case ("-"): // удалить датчик
                        sensors.delContent(sensors.content.get(delElement(sensors, "датчика")), session);
                        break;
                    case ("+"): // добавление записи
                        sensors.setContent(newSensor(room), session);
                        break;
                    case ("Edit"): // редактировать датчик
                        sensors.updateContent(editSensor(sensors), session);
                        break;
                    default:
                        int i = checkElement(sensors, resp);
                        if (i >= 0) manageValues(house, room, sensors.content.get(i));
                        else f = 0;
                        break;
                }
            }
        }
    }

    // управление показаниями
    public static void manageValues(House house, Room room, Sensor sensor) {
        while (true) {
            // переменная для таблицы из БД
            DbContent<Value> values = menu(
                    "ПОКАЗАНИЯ ДАТЧИКА id " + sensor.getId() +
                            " (" + sensor.getParameter() + ") КОМНАТЫ '"
                            + room.getName() + "' ДОМА ПО АДРЕСУ: улица "
                            + house.getStreet() + " дом " + house.getNumber()
                    , "FROM entity.Value WHERE sensorId = " + sensor.getId());
            showValues(values, sensor.getPhys());
            String resp = readString(" Введите действие или номер показания:");
            switch (resp) {
                case ("Exit"): // выход
                    System.exit(0);
                case ("<-"): // назад
                    return;
                case ("-"): // удалить показание
                    values.delContent(values.content.get(delElement(values, "показания")), session);
                    break;
                case ("+"): // добавление записи
                    values.setContent(newValue(sensor), session);
                    break;
                case ("Edit"): // редактировать датчик
                    values.updateContent(editValue(values), session);
                    break;
                default:
                    System.out.println("Неправильная опция.");
                    break;
            }
        }
    }
}
