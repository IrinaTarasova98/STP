package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String house(Model model) {
		model.addAttribute("title", "Главная страница");
		return "house";
	}
	
   @RequestMapping(value = "/apartments")
   public String helloWorldController(@RequestParam(name = "name", required = false, defaultValue = "0") String name, Model model) {
       model.addAttribute("name", name);
		return "apartments";
   }
}