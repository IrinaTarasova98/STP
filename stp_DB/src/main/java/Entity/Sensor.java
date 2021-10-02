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
public class Sensor {

    // ИД датчика
    @Id
    @GeneratedValue
    private Long id;

    // Измеряемая велечина
    private String parameter;

    // Один ко многим (Комната - Датчики) ИД комнаты
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Room room;

    // Многие к одному (Датчик - Показания) ИД датчика
    @OneToMany(mappedBy = "value", cascade = CascadeType.ALL)
    private Set<Value> values = new HashSet<>();

    public Set<Value> getValues() {
        return values;
    }

    public void setValues(Set<Value> values) {
        this.values = values;

        for(Value v : values) {
            v.setSensor(this);
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

    public String getParameter()
    {
        return parameter;
    }

    public void setParameter(String parameter)
    {
        this.parameter = parameter;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}