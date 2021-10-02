package Entity;

import java.sql.Date;
import java.sql.Time;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class Value {

    // ИД записи
    @Id
    @GeneratedValue
    private Long id;

    // время записи
    private String time; // Time

    // дата записи
    private String date; // Date

    // показание
    private float reading;

    // Один ко многим (Датчик - Показания) ИД Датчика
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Sensor sensor;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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