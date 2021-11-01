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

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getStreet() { return street; }

    public void setStreet(String street) { this.street = street; }

    public String getNumber() { return number; }

    public void setNumber(String number) { this.number = number; }

    // Один ко многим (дом - комнаты)
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private Set<Room> rooms = new HashSet<>();
    public Set<Room> getRooms() { return rooms; }

    public void setRooms(Set<Room> rooms) { this.rooms = rooms; }

    public void addRooms(Room room) { this.rooms.add(room); }

    public void delRooms(Room room) { rooms.remove(room); }
}