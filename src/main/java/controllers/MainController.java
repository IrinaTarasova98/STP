package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

	// Получение данных из БД
	/*
		- Получает параметры нужной страницы ("houseId", "roomId", "sensorId")
		- Определяет таблицу по полученным параметрам
		- Перенаправляет запрос в контроллер нужной таблицы
	*/
	@GetMapping(value = "/main")
	public String mainPage(
			Model model,
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		// если введен номер какого-то дома
		if (!houseId.equals("null")) {
			// если введен номер какой-то комнаты
			if (!roomId.equals("null")) {
				// если введен номер какого-то датчика
				if (!sensorId.equals("null")) {
					// если указан номер датчика
					// получить список показаний
					return "redirect:/get/Value?" +
							"house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId;
				}
				// если указана комната
				// получить список датчиков
				else return "redirect:/get/Sensor?" +
						"house=" + houseId + "&room=" + roomId;
			}
			// если указан дом
			// получить список комнат
			else return "redirect:/get/Room?" +
					"house=" + houseId;
		}
		// если ничего не указанно
		// получить список домов
		return "redirect:/get/House";
	}

	// Добавление записи в БД
	/*
		- Получает название текущей таблицы 		"tableName"
		- Получает id записей всего пути 			"house", "room", "sensor"
		- Получает параметры для добавления записи	"params"
		- Перенаправляет запрос в контроллер текущей таблицы
	 */
	@PostMapping(path = "/add")
	public String addEl(
			@RequestParam(name = "tableName", defaultValue = "null") String tableName,
			@RequestParam(name = "setParams[]", defaultValue = "null") String[] params,
			/* (Для возврата на страницу) */
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		switch (tableName) {
			case "House":
				return "redirect:/add/House?" +
					"street=" + params[0] + "&num=" + params[1];
			case "Room":
				return "redirect:/add/Room?" +
					"house=" + houseId +
					"&name=" + params[0];
			case "Sensor":
				return "redirect:/add/Sensor?" +
					"house=" + houseId + "&room=" + roomId +
					"&phys=" + params[0] + "&param=" + params[1];
			default:
				return "redirect:/add/Value?" +
					// путь к странице
					"house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId +
					// параметры для вставки
					"&time=" + params[0] + "&date=" + params[1] + "&read=" + params[2];
		}
	}

	// Удаление записи из БД
	/*
		- Получает строку с названием текущей таблицы 	"tableName"
		- Получает id удаляемого элемента				"id"
		- Перенапрявляет запрос в контроллер текущей таблицы
	*/
	@PostMapping(value = "/del")
	public String delEl(
			@RequestParam(name = "tableName", defaultValue = "null") String tableName,
			@RequestParam(name = "id", defaultValue = "null") String id,
			/* (Для возврата на страницу) */
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		switch (tableName) {
			case "House":
				return "redirect:/del/House?" +
					"house=" + id;
			case "Room":
				return "redirect:/del/Room?" +
					"house=" + houseId +
					"&room=" + id;
			case "Sensor":
				return "redirect:/del/Sensor?" +
					"house=" + houseId + "&room=" + roomId +
					"&sensor=" + id;
			case "Value":
				return "redirect:/del/Value?" +
					"house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId +
					"&value=" + id;
			default:
				return "redirect:/main";
		}
	}

	// Заполнение формы изменения
	/*
		- Получает название текущей таблицы 		"tableName"
		- Получает id изменяемой записи				"id"
		- Перенаправляет запрос в контроллер текущей таблицы
	 */
	@GetMapping(path = "/getElement")
	public String getEl(
			@RequestParam(name = "tableName", defaultValue = "null") String tableName,
			@RequestParam(name = "id", defaultValue = "null") String id,
			/* (Для возврата на страницу) */
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		switch (tableName) {
			case "House":
				return "redirect:/getElement/House?" +
					"house=" + id;
			case "Room":
				return "redirect:/getElement/Room?" +
					"house=" + houseId +
					"&room=" +	id;
			case "Sensor":
				return "redirect:/getElement/Sensor?" +
					"house=" + houseId + "&room=" + roomId +
					"&sensor=" + id;
			default:
				return "redirect:/getElement/Value?" +
					"house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId +
					"&value=" + id;
		}
	}

	// Обновление записи в БД
	/*
		- Получает название текущей таблицы 		"tableName"
		- Получает id изменяемой записи				"id"
		- Получает параметры для обновления записи	"params"
		- Перенаправляет запрос в контроллер текущей таблицы
	 */
	@PostMapping(path = "/updateElement")
	public String updateEl(
			@RequestParam(name = "tableName", defaultValue = "null") String tableName,
			@RequestParam(name = "id", defaultValue = "null") String id,
			@RequestParam(name = "setParams[]", defaultValue = "null") String[] params,
			/* (Для возврата на страницу) */
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		System.out.println("Table name = " + tableName);
		System.out.println("house = " + houseId);
		System.out.println("room = " + roomId);
		switch (tableName) {
			case "House":
				return "redirect:/updateElement/House?" +
					"house=" + id + "&street=" + params[0] + "&num=" + params[1];
			case "Room":
				return "redirect:/updateElement/Room?" +
					"house=" + houseId +
					"&room=" +	id + "&name=" + params[0];
			case "Sensor":
				return "redirect:/updateElement/Sensor?" +
					"house=" + houseId + "&room=" + roomId +
					"&sensor=" + id + "&phys=" + params[0] + "&param=" + params[1];
			default:
				return "redirect:/updateElement/Value?" +
					"house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId +
					"&value=" + id + "&time=" + params[0] + "&date=" + params[1] + "&read=" + params[2];
		}
	}


	// Перейти к станице на уровень выше
	/*
		- Получает параметры страницы ("houseId", "roomId", "sensorId")
		- Получает id новой страницы
		- Перенаправляет запрос на страницу с соответсвующими параметрами
	*/
	@RequestMapping(value = "/next")
	@ResponseBody
	public String nextPage(
			Model model,
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId,
			// id выбраной записи
			@RequestParam(name = "id", defaultValue = "null") String id
	)
	{
		// если введен номер какого-то дома
		if (!houseId.equals("null")) {
			// если введен номер какой-то комнаты
			if (!roomId.equals("null")) {
				// если введен номер какого-то датчика
				if (!sensorId.equals("null")) {
					// дальше покзаний нет страницы
					return "/main?" + "house=" + houseId + "&room=" + roomId + "&sensor=" + sensorId;
				}
				// следующая страница - показания
				else {
					model.addAttribute("sensor", sensorId);
					return "/main?" + "house=" + houseId + "&room=" + roomId + "&sensor=" + id;
				}
			}
			// следующая страница - датчики
			else {
				model.addAttribute("room", roomId);
				return "/main?" + "house=" + houseId + "&room=" + id;
			}
		}
		// следующая страница - комнаты
		else {
			model.addAttribute("house", houseId);
			return "/main?house=" + id;
		}
	}

	// Открыть страницу на уровень ниже
	/*
		- Получает параметры текущей страницы ("houseId", "roomId", "sensorId")
		- Обнуляет последний значимый параметр
		- Перенаправляет на страницу с обновленными параметрами
	 */
	@RequestMapping(value = "/back")
	@ResponseBody
	public String backPage(
			Model model,
			@RequestParam(name = "house", defaultValue = "null") String houseId,
			@RequestParam(name = "room", defaultValue = "null") String roomId,
			@RequestParam(name = "sensor", defaultValue = "null") String sensorId
	)
	{
		// если введен номер какого-то дома
		if (!houseId.equals("null")) {
			// если введен номер какой-то комнаты
			if (!roomId.equals("null")) {
				// если введен номер какого-то датчика
				if (!sensorId.equals("null")) {
					model.addAttribute("sensor", "null");
					// перейти на страницу датчиков
					return "/main?" + "house=" + houseId + "&room=" + roomId;
				}
				// перейти на страницу комнат
				else {
					model.addAttribute("room", "null");
					return "/main?" + "house=" + houseId;
				}
			}
			// перейти на страницу домов
			else {
				model.addAttribute("house", "null");
				return "/main";
			}
		}
		// остаться на странце домов
		return "/main";
	}
}
