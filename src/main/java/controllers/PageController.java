package controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Apartment;

@Controller
public class PageController {

	Apartment apartment;
	
	// получить список квартир
	@GetMapping(value = "/")
	public String GetHouse(Model model) {
		
		// массив квартир
		ArrayList<Apartment> house = new ArrayList<>();
		
		// чтение номеров квартир из папки 
		try {
			File folder = new File("src/main/resources/appartments");
			File[] listOfFiles = folder.listFiles();
	
			// добавляем в дом квартиры с номерами из файла
			for (int i = 0; i < listOfFiles.length; i++) {
			    house.add(new Apartment(Integer. parseInt(listOfFiles[i].getName())));
			}
			
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
		}
		
		// поместить массив в модель
		model.addAttribute("house", house);		
		 
		// поместить в модель название
		model.addAttribute("title", "Главная страница");
		
		// вернуть шаблон "house.html"
		return "house"; 
	}
	
	// добавить файл квартиры 
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	private String addApart() {
		File folder = new File("src/main/resources/appartments");
		File[] listOfFiles = folder.listFiles();
		// найти свободный id 
		int id = 1;
		int f = 0;
		while(true)
		{
			// добавляем в дом квартиры с номерами из файла
			for (int i = 0; i < listOfFiles.length; i++) {
				if (Integer.parseInt(listOfFiles[i].getName()) == id)
				{
			    		f = 1;
				    	break;
				}
			}
			if (f == 0) break;
			f = 0;
			id++;
		}
		// создание нового файла 
		String path = "src/main/resources/appartments/" + id;
		File file = new File(path);
		try {
			file.getParentFile().mkdirs(); 
			file.createNewFile();
			System.out.println("Создали квартиру №" + id);
		}
	catch(IOException ex){
		System.out.println(ex.getMessage());
	} 
		return "redirect:/";
	}
	
	// удалить файл квартиры
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	private String deleteApart(@RequestParam String id){
		File file = new File("src/main/resources/appartments/" + id);
		if (file.delete()) System.out.println("Удилили квартиру №" + id);
		return "redirect:/";
	}
}