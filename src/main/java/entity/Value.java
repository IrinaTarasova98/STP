package entity;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Date time; // Time

    // дата записи
    @Column(name = "date")
    private Date date; // Date

    // показание
    @Column(name = "reading")
    private float reading;

    public String getTime()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        return dateFormat.format(time);
    }

    public void setTime(Date time) { this.time = time; }

    public String getDate()
    {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    public void setDate(Date date)
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

    // многие к одному (показания - сенсор)
    @ManyToOne
    @JoinColumn(name = "sensorId", nullable=false)
    private Sensor sensor;
    public Sensor getSensor() { return sensor; }

    public void setSensor(Sensor sensor) { this.sensor = sensor; }

}