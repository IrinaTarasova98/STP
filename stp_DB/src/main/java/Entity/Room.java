package Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Room {

    // ИД комнаты
    @Id
    @GeneratedValue
    private Long id;

    // Название комнаты
    private String name;

    // Один ко многим (Дом - Комнаты) ИД дома
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private House house;

    // Многие к одному (Комната - Датчики) ИД комнаты
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL)
    private Set<Sensor> sensors = new HashSet<>();

    public Set<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(Set<Sensor> sensors) {
        this.sensors = sensors;

        for(Sensor s : sensors) {
            s.setRoom(this);
        }
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
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

    public House getHouse() {
        return house;
    }

    public void setHouse(House house) {
        this.house = house;
    }


}