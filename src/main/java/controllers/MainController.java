package controllers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Apartment;

@Controller
public class MainController {

	// при открытии главной страницы:
	// создать экземпляр класса "House"
	// создать список объектов типа "Apartment"
	// получить в экземпляр класса "House" список номеров квартир (House.getNumbers())
	// вернуть список номеров квартир int[] на страницу 
	@GetMapping(value = "/main")
	public String GetHouse(Model model) {
		// массив квартир
		ArrayList<Apartment> apartments = new ArrayList<>();
		try {
			// открытие файла для чтения
			@SuppressWarnings("resource")
			BufferedReader reader = new BufferedReader(new FileReader ("C:\\stp\\src\\main\\resources\\templates\\House"));
			// строка для чтения
			String         line = null;
			// чтение номеров 
		    while((line = reader.readLine()) != null) {
		    	// запись номера в массив
		    	Apartment apartment = new Apartment(Integer.parseInt(line));
		    	apartments.add(apartment);
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
		// поместить массив в модель
		model.addAttribute("house", apartments);
		// поместить в модель название
		model.addAttribute("title", "Главная страница");
		return "house"; 
	}
	

	// при переходе на страницу квартиры
	// получить номер квартиры
	// получить параметры квартиры из файла (Apartment.getValues())
	// вернуть параметры int[] на страницу
	@GetMapping(value = "/apartments")
	public String getApartment(
			@RequestParam(name = "name", defaultValue = "0") String name, Model model
			) {
		model.addAttribute("name", name);
		return "apartments";
	}
 
	// при нажатии кнопки "Добавить" на странице "/"
	// получить id новой квартиры 
	// создать экземпляр класса "Apartment"
	// добавить новый номер в файл "House" (House.addNumber())
	@PostMapping(value="/tmain1")
	public void delApart(@RequestParam() int id)
	{   
	   
	}
	
	// при нажатии кнопки "Удалить" на странице "/"
	// получить id удаляемой квартиры 
	// удалить номер из файла "House" (House.delNumber())
	@PostMapping(value="/tmain2")
	public void addApart(@RequestParam() int id)
	{   
	   
	}
	
	// при нажатии кнопки "Назад" на странице "/apartment"
	// получить id квартиры
	// записать новые значения в файл с номером id (Apartment.setValuse())
	@PostMapping(value="/tapartments")
	public void setParams()
	{
		
	}
}