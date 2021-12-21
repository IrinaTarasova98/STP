package controllers;

import classes.DataContainer;
import entity.Sensor;
import entity.Value;
import classes.ModelInf;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Controller
public class ValuesController {

    // Получение показаний датчика "sensorId"
    /*
        - Обращается к БД и получает записи из таблицы "Value"
        - Записывает в "model":
            tableName   - имя таблицы
            params      - список параметров элементов
            content     - информация об элементах
     */
    @GetMapping(value = "/get/Value")
    public String getValues(
            Model model,
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId
    )
    {
        // массив для элементов
        ArrayList<ModelInf> array = new ArrayList<>();
        // запрос к БД и получение таблицы в "VALUE"
        ArrayList<Value> values = DataContainer.getList(
                "FROM entity.Value WHERE sensorId = " + sensorId, Value.class);
        // записать информацию из БД в массив array
        for (Value value : values) {
            ModelInf temp = new ModelInf();
            temp.setId((value.getId()));
            temp.setInf(value.getDate() +
                    " (" + value.getTime() + ") " +
                    value.getReading());
            array.add(temp);
        }
        // внести данные в модель
        model.addAttribute("title", "Список показаний датчика номер " + sensorId);
        model.addAttribute("tableName","Value");
        model.addAttribute("content", array);
        model.addAttribute("params", new String[] {"Время", "Дата", "Показание"});
        model.addAttribute("elInf", new String[] {"", "", "", ""});
        model.addAttribute("house", houseId);
        model.addAttribute("room", roomId);
        model.addAttribute("sensor", sensorId);
        return "home";
    }

    // Добавление записи
    /*
        - Получает параметры новой записи   "time", "date", "reading"
        - Обращается к БД для добавления новой записи
     */
    @RequestMapping(value = "/add/Value")
    @ResponseBody
    public String addValue(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId,
            @RequestParam(name = "time") String timeB,
            @RequestParam(name = "date") String dateB,
            @RequestParam(name = "read") String read
    )
    {
        Date dateA = null, timeA = null;
        try {
            dateA = new SimpleDateFormat("dd.MM.yyyy").parse(dateB);
            timeA = new SimpleDateFormat("hh:mm").parse(timeB);
            if (dateA == null || timeA == null) throw new NullPointerException("Type error");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Value value = new Value();
        value.setDate(dateA);
        value.setTime(timeA);
        value.setReading(Float.parseFloat(read));
        // получить дом, в котором находится квартира
        Sensor sensor = (Sensor) DataContainer.getElement(Integer.parseInt(sensorId), Sensor.class);
        value.setSensor(sensor);
        DataContainer.addElement(value);
        return "/main?house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId;
    }

    // Удаление записи
    /*
        - Получает id удаляемого элемента   "valueId"
        - Получает параметры, для возврата  "houseId", "roomId", "sensorId"
        - Обращается к БД с запросом на удаление
     */
    @RequestMapping(value = "/del/Value")
    @ResponseBody
    public String delValue(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId,
            @RequestParam(name = "value") String valueId
    )
    {
        DataContainer.delElement(Integer.parseInt(valueId), Value.class);
        return "/main?house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId;
    }

    // Получение данных о записи из БД
    /*
        - Получает id изменяемого элемента  "valueId"
        - Получает из БД параметр записи    "time", "date", "reading"
        - Записывает в "model" данные полученной записи
     */
    @RequestMapping(value = "/getElement/Value")
    @ResponseBody
    public String getValueElement(
            Model model,
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId,
            @RequestParam(name = "value") String valueId
    )
    {
        Value value = (Value)DataContainer.getElement(Integer.parseInt(valueId), Value.class);
        JSONObject o = new JSONObject();
        try {
            o.put("code", "200");
            o.put("params", new String[]{value.getTime(), value.getDate(), Float.toString(value.getReading())});
            o.put("url", "/main?house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    // Изменение записи
    /*
        - Получает id изменяемого элемента  "valueId"
        - Получает параметр записи          "time", "date", "reading"
        - Обращается к БД с запросом на обновление записи
     */
    @RequestMapping(value = "/updateElement/Value")
    @ResponseBody
    public String updateValue(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "room") String roomId,
            @RequestParam(name = "sensor") String sensorId,
            @RequestParam(name = "value") String valueId,
            @RequestParam(name = "time") String timeB,
            @RequestParam(name = "date") String dateB,
            @RequestParam(name = "read") String read
    )
    {
        Date dateA = null, timeA = null;
        try {
            dateA = new SimpleDateFormat("dd.MM.yyyy").parse(dateB);
            timeA = new SimpleDateFormat("hh:mm").parse(timeB);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Value value = (Value)DataContainer.getElement(Integer.parseInt(valueId), Value.class);
        assert value != null;
        value.setReading(Float.parseFloat(read));
        value.setDate(dateA);
        value.setTime(timeA);
        DataContainer.updateElement(value);
        return "/main?house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId;
    }
}
