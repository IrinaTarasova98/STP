package com.example.demo.model;

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
	// number
	int id;
    int Values[];

    // constructor
    public Apartment(int id)
    {
    	this.id = id;
    	Values = new int[9];
    }
    
    // get
    public int[] getValues()
    {
    	return Values;
    }    
    
    // saving in file
    public void saveValues() {
        try(FileWriter writer = new FileWriter("values/" + String.valueOf(id), false))
        {
        	for (int i = 0; i < Values.length; i++)
        	{
        		writer.write(String.valueOf(Values[i]));
                writer.append('\n');
        	}
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    }
    
    // load values from file
    public void loadValues() {
    	String path = "values/" + String.valueOf(id);
        Scanner sc = new Scanner(path);
    	for (int i = 0; i < Values.length; i++)
    	{
    		Values[i] = Integer.valueOf(sc.next());
    	}
    	sc.close();
    }
        
}