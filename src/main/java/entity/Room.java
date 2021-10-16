package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "ROOM")
public class Room {

    // ИД комнаты
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "roomId")
    private int id;

    // Название комнаты
    @Column(name = "name")
    private String name;

    // Один ко многим (Дом - Комнаты) ИД дома
    private House house;

    @ManyToOne
    @JoinColumn(name = "houseId")
    public House getHouse() {
        return house;
    }

    public void setHouse(House house) { this.house = house; }

    // Многие к одному (Комната - Датчики) ИД комнаты
    private Set<Sensor> sensors = new HashSet<>();

    @Column(name = "sensors")
    @OneToMany(mappedBy = "ROOM", cascade = CascadeType.ALL)
    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void addSensors(Sensor sensor) {
        sensor.setRoom(this);
        this.sensors.add(sensor);
    }

    public void setSensors(Set<Sensor> sensors) { this.sensors = sensors; }

    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}