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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    // Многие к одному (комнаты - дом)

    @ManyToOne
    @JoinColumn(name = "houseId", nullable=false)
    private House house;
    public House getHouse() { return house; }

    public void setHouse(House house) { this.house = house; }

    // Один ко многим (комната - датчики)
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Sensor> sensors = new HashSet<>();
    public Set<Sensor> getSensors() { return sensors; }

    public void setSensors(Set<Sensor> sensors) { this.sensors = sensors; }

    public void addSensor(Sensor sensor) {
        this.sensors.add(sensor);
    }

    public void delSensor(Sensor sensor) { sensors.remove(sensor); }

}