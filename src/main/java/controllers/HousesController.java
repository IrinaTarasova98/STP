package controllers;

import classes.DataContainer;
import classes.ModelInf;
import entity.House;
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
public class HousesController {

    // Получение записей о домах
    /*
        - Обращается к БД и получает записи из таблицы "House"
        - Записывает в "model":
            tableName   - имя таблицы
            params      - список параметров элементов
            content     - информация об элементах
     */
    @GetMapping(value = "/get/House")
    public String getHouseList(Model model) {
        // массив для элементов
        ArrayList<ModelInf> array = new ArrayList<>();
        // запрос к БД и получение таблицы в "House"
        ArrayList<House> houses = DataContainer.getList("FROM entity.House", House.class);
        // если информация не получена
        if (houses == null) return "/main";
        // записать информацию из БД в массив array
        for (House house : houses) {
            ModelInf temp = new ModelInf();
            temp.setId(house.getId());
            temp.setInf("Ул. " + house.getStreet() + " дом " + house.getNumber());
            array.add(temp);
        }
        // внести данные в модель
        model.addAttribute("title", "Список домов");
        model.addAttribute("tableName","House");
        model.addAttribute("content", array);
        model.addAttribute("params", new String[] {"Улица", "Номер дома"});
        model.addAttribute("elInf", new String[] {"", "", ""});
        return "home";
    }

    // Добавление записи
    /*
        - Получает параметры новой записи   "street", "num"
        - Обращается к БД для добавления новой записи
        (После обновления записи страница обновляется)
     */
    @RequestMapping(value = "/add/House")
    @ResponseBody
    public String addHouse(
            @RequestParam(name = "street") String street,
            @RequestParam(name = "num") String num
    )
    {
        House house = new House();
        house.setStreet(street);
        house.setNumber(num);
        DataContainer.addElement(house);
		return "/main";
    }

    // Удаление записи
    /*
        - Получает id удаляемого элемента "houseId"
        - Обращается к БД с запросом на удаление
        (После обновления записи страница обновляется)
     */
    @RequestMapping(value = "/del/House")
    @ResponseBody
    public String delHouse(
            @RequestParam(name = "house") String houseId
    )
    {
        DataContainer.delElement(Integer.parseInt(houseId), House.class);
        return "/main";
    }

    // Получение данных о записи из БД
    /*
        - Получает id изменяемого элемента  "houseId"
        - Получает из БД параметры записи   "street", "num"
        - Записывает в "model" данные полученной записи
        (После обновления записи страница НЕ обновляется)
     */
    @RequestMapping(value = "/getElement/House")
    @ResponseBody
    public String getElementHouse(
            Model model,
            @RequestParam(name = "house") String houseId
    )
    {
        House house = DataContainer.getElement(Integer.parseInt(houseId), House.class);

        JSONObject o = new JSONObject();
        try {
            o.put("code", "200");
            o.put("params", new String[]{house.getStreet(), house.getNumber()});
            o.put("url", "/main");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return o.toString();
    }

    // Изменение записи
    /*
        - Получает id изменяемого элемента  "houseId"
        - Получает параметры записи         "street", "num"
        - Обращается к БД с запросом на обновление записи
        (После обновления записи страница обновляется)
     */
    @RequestMapping(value = "/updateElement/House")
    @ResponseBody
    public String updateHouse(
            @RequestParam(name = "house") String houseId,
            @RequestParam(name = "street") String street,
            @RequestParam(name = "num") String num
    )
    {
        House house = (House)DataContainer.getElement(Integer.parseInt(houseId), House.class);
        assert house != null;
        house.setNumber(num);
        house.setStreet(street);
        DataContainer.updateElement(house);
        return "/main";
    }

}
