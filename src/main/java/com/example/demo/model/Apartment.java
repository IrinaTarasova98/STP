package com.example.demo.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

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
	// параметры квартиры
    int Values[];

    // конструктор
    public Apartment(int id)
    {
    	Values = new int[9];
    	this.id = id;
    }
        
    // получить параметры квартиры в массив
    public int[] getValues()
    {
		try {
			// открыть файл для чтения
			FileInputStream fileIn = new FileInputStream(this.id + "-Apartment.txt");
			// получить все значения
			for (int i = 1; i < 9; i++) {
					Values[i] = fileIn.read();
			}
			// закрыть файл
			fileIn.close();
		}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        } 
    	return Values;
    }    
    
    // сохранить параметры в файл
    public void setValues(int Values[]) {
    	try {
    		// открыть файл для записи
    		FileOutputStream fileOut = new FileOutputStream(this.id + "-Apartment.txt");
			// записать в файл значения
			for (int i = 0; i < 9; i++)
			{
				fileOut.write(Values[i]);
			}
			// закрыть файл
			fileOut.close();
    	}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        } 
    }
        
}