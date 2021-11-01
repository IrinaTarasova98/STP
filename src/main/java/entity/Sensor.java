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

    // единица измерения
    @Column(name = "phys")
    private String phys;

    // Измеряемая велечина
    @Column(name = "parameter")
    private String parameter;

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

    public String getPhys()
    {
        return phys;
    }

    public void setPhys(String phys)
    {
        this.phys = phys;
    }

    // Многие к одному (сенсоры - комната)
    @ManyToOne
    @JoinColumn(name = "roomId", nullable=false)
    private Room room;
    public Room getRoom() { return room; }

    public void setRoom(Room room) { this.room = room; }

    // Один ко многим (сенсор - показания)
    @OneToMany(mappedBy = "sensor", cascade = CascadeType.ALL)
    private Set<Value> values = new HashSet<>();
    public Set<Value> getValues() { return values; }

    public void setValues(Set<Value> values) { this.values = values; }

    public void addValue(Value value) {
        this.values.add(value);
    }

    public void delValue(Value value) { values.remove(value); }
}