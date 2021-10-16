package entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.*;

@Entity
@Table(name = "VALUE")
public class Value {

    // ИД записи
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "valueID")
    private int id;

    // время записи
    @Column(name = "time")
    private String time; // Time

    // дата записи
    @Column(name = "date")
    private String date; // Date

    // показание
    @Column(name = "reading")
    private float reading;

    // Один ко многим (Датчик - Показания) ИД Датчика
    private Sensor sensor;

    @ManyToOne
    @JoinColumn(name = "sensorId")
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getTime()
    {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public float getReading()
    {
        return reading;
    }

    public void setReading(float reading)
    {
        this.reading = reading;
    }

    public Sensor getSensor() {
        return sensor;
    }

    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }
}