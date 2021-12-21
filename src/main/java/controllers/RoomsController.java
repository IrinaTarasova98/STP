package controllers;

import classes.DataContainer;
import classes.ModelInf;
import entity.House;
import entity.Room;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;

@Controller
public class RoomsController {

    // Получение записей о комнатах дома "houseId"
    /*
        - Обращается к БД и получает записи из таблицы "Room"
        - Записывает в "model":
            tableName   - имя таблицы
            params      - список параметров элементов
            content     - информация об элементах
     */
    @GetMapping(value = "/get/Room")
    public String getRoomList(
            Model model,
            @RequestParam(name = "house") String houseId)
    {
        // массив для элементов
        ArrayList<ModelInf> array = new ArrayList<>();
        // запрос к БД и получение таблицы в "ROOM"
        ArrayList<Room> rooms = DataContainer.getList(
                "FROM entity.Room WHERE houseId = " + houseId, Room.class);
        // записать информацию из БД в массив array
        for (Room room : rooms) {
            ModelInf temp = new ModelInf();
            temp.setId((room.getId()));
            temp.setInf(room.getName());
            array.add(temp);
        }
        // внести данные в модель
        model.addAttribute("title", "Список комнат дома номер " + houseId);
        model.addAttribute("tableName","Room");
        model.addAttribute("content", array);
        model.addAttribute("params", new String[] {"Название комнаты"});
        model.addAttribute("house", houseId);
        return "home";
    }

    // Добавление записи
    /*
        - Получает параметры новой записи               "name"
        - Обращается к БД для добавления новой записи
        (После обновления записи страница обновляется)
     */
    @RequestMapping(value = "add/Room")
    @ResponseBody
    public String addRoom(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "name") String name
    )
    {
        Room room = new Room();
        room.setName(name);
        // получить дом, в котором находится квартира
        House house = (House)DataContainer.getElement(Integer.parseInt(houseId), House.class);
        room.setHouse(house);
        DataContainer.addElement(room);
        return "/main?house=" + houseId;
    }

    // Удаление записи
    /*
        - Получает id удаляемого элемента               "roomId"
        - Получает id дома, для возврата на страницу    "houseId"
        - Обращается к БД с запросом на удаление
        (После обновления записи страница обновляется)
     */
    @RequestMapping(value = "/del/Room")
    @ResponseBody
    public String delRoom(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId
    )
    {
        DataContainer.delElement(Integer.parseInt(roomId), Room.class);
        return "/main?house=" + houseId;
    }

    // Получение данных о записи из БД
    /*
        - Получает id изменяемого элемента              "roomId"
        - Получает из БД параметр записи                "name"
        - Записывает в "model" данные полученной записи
     */
    @RequestMapping(value = "/getElement/Room")
    @ResponseBody
    public String getElementRoom(
            Model model,
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId
    )
    {
        Room room = (Room)DataContainer.getElement(Integer.parseInt(roomId), Room.class);
        JSONObject o = new JSONObject();
        try {
            o.put("code", "200");
            o.put("params", new String[]{room.getName()});
            o.put("url", "/main?house=" + houseId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    // Изменение записи
    /*
        - Получает id изменяемого элемента              "roomId"
        - Получает параметр записи                      "name"
        - Обращается к БД с запросом на обновление записи
     */
    @RequestMapping(value = "/updateElement/Room")
    @ResponseBody
    public String updateRoom(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "name") String name
    )
    {
        Room room = (Room)DataContainer.getElement(Integer.parseInt(roomId), Room.class);
        assert room != null;
        room.setName(name);
        DataContainer.updateElement(room);
        return "/main?house=" + houseId;
    }
}
