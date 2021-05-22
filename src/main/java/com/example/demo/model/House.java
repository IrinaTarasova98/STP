package com.example.demo.model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Component;

@Component
public class House {
	// номера созданых квартир
	public ArrayList<Integer> numbers;
		
	// конструктор
	public House() {
		numbers = new ArrayList<>();
	}
	
	// добавить запись в файл 
	public void addNumbers(int id)
	{
		numbers.add(id);
		try {
			FileOutputStream fileOut = new FileOutputStream("House.txt");
			// добавить все номера в файл 
			for (int i = 0; i < numbers.size(); i++)
			{
				fileOut.write(numbers.get(i));
			}
			fileOut.close();
		}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        }
	}
	
	// получить список номеров квартир
	public ArrayList<Integer> getNumbers()
	{
		numbers.clear();
		try {
			// открыть файл для чтения
			FileInputStream fileIn = new FileInputStream("House.txt");
			while (true) {
				int temp = fileIn.read();
				if (temp == 0) break;
				numbers.add(temp);
			}
			fileIn.close();
		}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        } 
		return numbers;
	}
	
	// удалить номер квартиры из файла 
	public void delNumbers(int id)
	{
		// удалить номер
		numbers.remove(id);
		try {
			FileOutputStream fileOut = new FileOutputStream("House.txt");
			// добавить все номера в файл
			for (int i = 0; i < numbers.size(); i++)
			{
				fileOut.write(numbers.get(i));
			}
			fileOut.close();
		}
        catch(IOException ex){
        	System.out.println(ex.getMessage());
        }
	}
}