package Entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class House {

    // ИД дома
    @Id
    @GeneratedValue
    private Long id;

    // Улица
    private String street;

    // Номер дома
    private String number;

    // Многие к одному (Дом - Комнаты) ИД комнаты
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();

    public Set<Room> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Room> rooms) {
        this.rooms = rooms;

        for(Room r : rooms) {
            r.setHouse(this);
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

    public String getStreet()
    {
        return street;
    }

    public void setStreet(String street)
    {
        this.street = street;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }
}