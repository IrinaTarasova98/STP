package controllers;

import classes.DataContainer;
import entity.Room;
import entity.Sensor;
import classes.ModelInf;
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
public class SensorsController {

    // Получение записей о датчиках комнаты "roomId"
    /*
        - Обращается к БД и получает записи из таблицы "Sensor"
        - Записывает в "model":
            tableName   - имя таблицы
            params      - список параметров элементов
            content     - информация об элементах
     */
    @GetMapping(value = "/get/Sensor")
    public String getSensorList(
            Model model,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "house") String houseId
    )
    {
        // массив для элементов
        ArrayList<ModelInf> array = new ArrayList<>();
        // запрос к БД и получение таблицы в "SENSOR"
        ArrayList<Sensor> sensors = DataContainer.getList(
                "FROM entity.Sensor WHERE roomId = " + roomId, Sensor.class);
        // записать информацию из БД в массив array
        for (Sensor sensor : sensors) {
            ModelInf temp = new ModelInf();
            temp.setId((sensor.getId()));
            temp.setInf("Датчик, измеряющий: " + sensor.getPhys() + " (" +
                    sensor.getParameter() + ")");
            array.add(temp);
        }
        // внести данные в модель
        model.addAttribute("title", "Список датчиков комнаты номер " + roomId);
        model.addAttribute("tableName","Sensor");
        model.addAttribute("content", array);
        model.addAttribute("params", new String[] {"Измеряемая велечина", "Единица измерения"});
        model.addAttribute("elInf", new String[] {"", "", ""});
        model.addAttribute("house", houseId);
        model.addAttribute("room", roomId);
        return "home";
    }

    // Добавление записи
    /*
        - Получает параметры новой записи   "phys", "param"
        - Обращается к БД для добавления новой записи
     */
    @RequestMapping(value = "/add/Sensor")
    @ResponseBody
    public String addSensor(
            @RequestParam(name = "phys") String phys,
            @RequestParam(name = "param") String param,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "house") String houseId
    )
    {
        Sensor sensor = new Sensor();
        sensor.setParameter(param);
        sensor.setPhys(phys);
        // получить комнату, в которой находится датчик
        Room room = (Room)DataContainer.getElement(Integer.parseInt(roomId), Room.class);
        sensor.setRoom(room);
        DataContainer.addElement(sensor);
        return "/main?house=" + houseId + "&room=" + roomId;
    }

    // Удаление записи
    /*
        - Получает id удаляемого элемента             "sensorId"
        - Получает параметры, для возврата            "houseId", "roomId"
        - Обращается к БД с запросом на удаление
     */
    @RequestMapping(value = "/del/Sensor")
    @ResponseBody
    public String delSensor(
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "sensor") String sensorId
    )
    {
        DataContainer.delElement(Integer.parseInt(sensorId), Sensor.class);
        return "/main?house=" + houseId + "&room=" + roomId;
    }

    // Получение данных о записи из БД
    /*
        - Получает id изменяемого элемента  "sensorId"
        - Получает из БД параметр записи    "phys", "param"
        - Записывает в "model" данные полученной записи
     */
    @RequestMapping(value = "/getElement/Sensor")
    @ResponseBody
    public String getSensorElement(
            Model model,
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId
    )
    {
        Sensor sensor = (Sensor) DataContainer.getElement(Integer.parseInt(sensorId), Sensor.class);
        JSONObject o = new JSONObject();
        try {
            o.put("code", "200");
            o.put("params", new String[]{sensor.getPhys(), sensor.getParameter()});
            o.put("url", "/main?house=" + houseId + "&room=" + roomId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    // Изменение записи
    /*
        - Получает id изменяемого элемента  "sensorId"
        - Получает параметр записи          "phys", "param"
        - Обращается к БД с запросом на обновление записи
     */
    @RequestMapping(value = "/updateElement/Sensor")
    @ResponseBody
    public String updateSensor(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId,
            @RequestParam(name = "phys") String phys,
            @RequestParam(name = "param") String param
    )
    {
        Sensor sensor = (Sensor)DataContainer.getElement(Integer.parseInt(sensorId), Sensor.class);
        assert sensor != null;
        sensor.setPhys(phys);
        sensor.setParameter(param);
        DataContainer.updateElement(sensor);
        return "/main?house=" + houseId + "&room=" + roomId;
    }
}
