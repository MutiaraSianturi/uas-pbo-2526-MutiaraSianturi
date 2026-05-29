// NAMA     : MUTIARA Y.H. SIANTURI
// NIM      : 12S24045

package pbo.f01;

import jakarta.persistence.*;
import pbo.f01.Model.ParkingArea;
import pbo.f01.Model.Vehicle;

import java.util.List;
import java.util.Scanner;

/**
 * Entry point aplikasi Park-IT.
 * 
 * Perintah yang tersedia:
 *   area-add#<name>#<capacity>#<allowed_type>
 *   vehicle-add#<plate>#<owner>#<type>
 *   park#<plate>#<area_name>
 *   display-all
 */
public class App {

    // EntityManager: koneksi utama ke database via JPA
    private static EntityManager em;

    public static void main(String[] args) {

        // Buka koneksi ke database (nama unit sesuai persistence.xml)
        EntityManagerFactory emf = 
            Persistence.createEntityManagerFactory("parkit-pu");
        em = emf.createEntityManager();

        Scanner scanner = new Scanner(System.in);

        // Baca input baris per baris sampai habis
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            // Pisah berdasarkan '#'
            String[] parts = line.split("#");
            String command = parts[0].trim();

            switch (command) {
                case "area-add"     -> handleAreaAdd(parts);
                case "vehicle-add"  -> handleVehicleAdd(parts);
                case "park"         -> handlePark(parts);
                case "display-all"  -> handleDisplayAll();
            }
        }

        scanner.close();
        em.close();
        emf.close();
    }

    /**
     * Tambah area parkir baru ke database.
     * Format input: area-add#<name>#<capacity>#<allowed_type>
     */
    private static void handleAreaAdd(String[] parts) {
        String name        = parts[1];
        int    capacity    = Integer.parseInt(parts[2].trim());
        String allowedType = parts[3].trim();

        ParkingArea area = new ParkingArea(name, capacity, allowedType);

        em.getTransaction().begin();
        em.persist(area);
        em.getTransaction().commit();
    }

    /**
     * Tambah kendaraan baru ke database.
     * Format input: vehicle-add#<plate>#<owner>#<type>
     */
    private static void handleVehicleAdd(String[] parts) {
        String plate = parts[1];
        String owner = parts[2];
        String type  = parts[3].trim();

        Vehicle vehicle = new Vehicle(plate, owner, type);

        em.getTransaction().begin();
        em.persist(vehicle);
        em.getTransaction().commit();
    }

    /**
     * Parkir kendaraan ke area tertentu.
     * Format input: park#<plate>#<area_name>
     * 
     * Validasi (jika gagal → skip, tidak ada output):
     *  1. Kendaraan harus terdaftar
     *  2. Area parkir harus ada
     *  3. Tipe kendaraan harus sesuai tipe area
     *  4. Kapasitas area belum penuh
     */
    private static void handlePark(String[] parts) {
        String plate    = parts[1];
        String areaName = parts[2];

        // Cari kendaraan dan area di database
        Vehicle vehicle = em.find(Vehicle.class, plate);
        ParkingArea area = em.find(ParkingArea.class, areaName);

        // Validasi 1: kendaraan harus terdaftar
        if (vehicle == null) return;

        // Validasi 2: area harus ada
        if (area == null) return;

        // Validasi 3: tipe kendaraan harus cocok dengan area
        if (!vehicle.getType().equals(area.getAllowedType())) return;

        // Muat ulang area agar jumlah kendaraan terkini
        em.refresh(area);

        // Validasi 4: kapasitas belum penuh
        if (!area.hasAvailableSlot()) return;

        // Semua validasi lolos → simpan relasi ke database
        vehicle.setParkingArea(area);
        em.getTransaction().begin();
        em.merge(vehicle);
        em.getTransaction().commit();
    }

    /**
     * Tampilkan semua area parkir (A-Z by name)
     * dan kendaraan di dalamnya (A-Z by plate).
     * 
     * Format area    : <name> <allowed_type> <capacity>|<jumlah_parkir>
     * Format kendaraan: <plate> <owner> <type>
     */
    private static void handleDisplayAll() {
        // Query semua area diurutkan A-Z
        List<ParkingArea> areas = em.createQuery(
            "SELECT a FROM ParkingArea a ORDER BY a.name ASC",
            ParkingArea.class
        ).getResultList();

        for (ParkingArea area : areas) {
            // Refresh agar data kendaraan paling terbaru
            em.refresh(area);

            int parkedCount = area.getVehicles().size();

            // Cetak baris area
            System.out.println(
                area.getName() + " " +
                area.getAllowedType() + " " +
                area.getCapacity() + "|" + parkedCount
            );

            // Cetak kendaraan di area ini (sudah urut A-Z)
            for (Vehicle v : area.getVehicles()) {
                System.out.println(
                    v.getPlateNumber() + " " +
                    v.getOwner() + " " +
                    v.getType()
                );
            }
        }
    }
}