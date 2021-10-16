package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "SENSOR")
public class Sensor {

    // ИД датчика
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "sensorId")
    private int id;

    // Измеряемая велечина
    @Column(name = "parameter")
    private String parameter;

    // Один ко многим (Комната - Датчики) ИД комнаты
    private Room room;

    @ManyToOne
    @JoinColumn(name = "roomId")
    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    // Многие к одному (Датчик - Показания) ИД датчика
    private Set<Value> values = new HashSet<>();

    @Column(name = "values")
    @OneToMany(mappedBy = "SENSOR", cascade = CascadeType.ALL)
    public Set<Value> getValues() {
        return values;
    }

    public void addValues(Value value) {
        value.setSensor(this);
        this.values.add(value);
    }

    public void setValues(Set<Value> values) { this.values = values; }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getParameter()
    {
        return parameter;
    }

    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }

}