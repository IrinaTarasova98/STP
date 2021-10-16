package entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "HOUSE")
public class House {

    // ИД дома
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    @Column(name = "houseId")
    private int id;

    // Улица
    @Column(name = "street")
    private String street;

    // Номер дома
    @Column(name = "number")
    private String number;

    // Многие к одному (Дом - Комнаты) ИД комнаты
    private Set<Room> rooms = new HashSet<>();

    @OneToMany(mappedBy = "HOUSE", cascade = CascadeType.ALL)
    public Set<Room> getRooms() { return rooms; }

    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }

    public void addRooms(Room room) {
        room.setHouse(this);
        this.rooms.add(room);
    }


    public int getId()
    {
        return id;
    }

    public void setId(int id)
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