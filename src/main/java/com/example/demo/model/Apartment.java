package com.example.demo.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.stereotype.Component;

//----------------------------------------------------//
//      Система управления светом и температурой      // 
//----------------------------------------------------//
//                 Свет             Температура       //
// Прихожая    |---|-------| 31%   |-----|-----| 50%  //
//                                                    //
// Кухня       |----|------| 41%   |----------|| 99%  //
//                                                    //
// Ванная      |-------|---| 79%   |-------|---| 80%  //
//                                                    //
// Комната     |------|----| 62%   |-|---------| 10%  //
//                                                    //
// Балкон      |---|-------| 33%   [ Сохранить ]      //
//----------------------------------------------------//

@Component
public class Apartment {
	// номер квартиры
	public int id;
	// параметры
    int values[];

    // конструктор
    public Apartment(int id)
    {
    	values = new int[] {0, 20, 0, 20, 0, 20, 0, 20, 0};
    	this.id = id;
    }
        
    // получить параметры квартиры в массив
    public int[] getValues()
    {
		try {
			// открыть файл для чтения
			Scanner scanner = new Scanner(new File("src/main/resources/appartments/" + this.id));
			// получить все значения
			int i = 0;
			while (scanner.hasNext()) {
					values[i++] = scanner.nextInt();
			}
			scanner.close();
		}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        } 
    	return values;
    }    
    
    public void setValues(int[] set) {
    	values = set;
    }
    
    // сохранить параметры в файл
    public void writeValues() {
        try(FileWriter writer = new FileWriter("src/main/resources/appartments/" + this.id, false))
        {
        	for(int i = 0; i < 9; i++) {
	            writer.write(Integer.toString(values[i]));
	            writer.append(System.lineSeparator());
        	} 
            writer.close();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
    	
    }
        
}