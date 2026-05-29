// NAMA     : MUTIARA Y.H. SIANTURI
// NIM      : 12S24045

package pbo.f01.Model;

import jakarta.persistence.*;

/**
 * Entity Kendaraan (mobil atau motor).
 * Setiap kendaraan bisa ditempatkan di satu area parkir.
 */
@Entity
@Table(name = "vehicle")
public class Vehicle {

    // Primary key = nomor polisi (unik per soal)
    @Id
    @Column(name = "plate_number", nullable = false, unique = true)
    private String plateNumber;

    // Nama pemilik
    @Column(name = "owner", nullable = false)
    private String owner;

    // Tipe kendaraan: "car" atau "motorcycle"
    @Column(name = "type", nullable = false)
    private String type;

    // Relasi ke area parkir (null = belum diparkir)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parking_area_name", nullable = true)
    private ParkingArea parkingArea;

    public Vehicle() {}

    public Vehicle(String plateNumber, String owner, String type) {
        this.plateNumber = plateNumber;
        this.owner = owner;
        this.type = type;
    }

    public String getPlateNumber() { return plateNumber; }
    public String getOwner() { return owner; }
    public String getType() { return type; }
    public ParkingArea getParkingArea() { return parkingArea; }
    public void setParkingArea(ParkingArea parkingArea) { 
        this.parkingArea = parkingArea; 
    }
}