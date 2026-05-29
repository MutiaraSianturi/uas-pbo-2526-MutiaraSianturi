// NAMA     : MUTIARA Y.H. SIANTURI
// NIM      : 12S24045

package pbo.f01.Model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity Area Parkir.
 * Satu area bisa menampung banyak kendaraan (One-to-Many ke Vehicle).
 */
@Entity
@Table(name = "parking_area")
public class ParkingArea {

    // Primary key = nama area (unik per soal)
    @Id
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    // Kapasitas maksimal kendaraan
    @Column(name = "capacity", nullable = false)
    private int capacity;

    // Tipe kendaraan yang boleh parkir: "car" atau "motorcycle"
    @Column(name = "allowed_type", nullable = false)
    private String allowedType;

    // Daftar kendaraan yang parkir di area ini, urut A-Z by plate
    @OneToMany(mappedBy = "parkingArea", 
               cascade = CascadeType.ALL, 
               fetch = FetchType.LAZY)
    @OrderBy("plateNumber ASC")
    private List<Vehicle> vehicles = new ArrayList<>();

    public ParkingArea() {}

    public ParkingArea(String name, int capacity, String allowedType) {
        this.name = name;
        this.capacity = capacity;
        this.allowedType = allowedType;
    }

    // Cek apakah masih ada slot kosong
    public boolean hasAvailableSlot() {
        return vehicles.size() < capacity;
    }

    public String getName() { return name; }
    public int getCapacity() { return capacity; }
    public String getAllowedType() { return allowedType; }
    public List<Vehicle> getVehicles() { return vehicles; }
    public void setVehicles(List<Vehicle> vehicles) { this.vehicles = vehicles; }
}