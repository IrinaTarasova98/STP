package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.Apartment;

// Управляет данными квартиры
@Controller
public class ApartController
{
	Apartment apart;
	
	// получить параметры квартиры
	@GetMapping(path = "/apartments")
	public String getValues(
			// получить id квартиры из строки
			@RequestParam(name = "name", defaultValue = "0") String name, 
			Model model ) 
	{
		apart = new Apartment(Integer.parseInt(name));
		model.addAttribute("name", name);
    	model.addAttribute("values", apart.getValues());
		return "apartments";
	}
	
	// сохранение параметров квартиры в файл
	@RequestMapping(path = "/save")
	private String saveValues(@RequestParam("values") int[] set ){
		apart.setValues(set);
		apart.writeValues();
	    return "redirect:/apartments?name=" + apart.id;
	}
}