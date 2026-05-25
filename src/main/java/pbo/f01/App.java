package pbo.f01;

import java.util.Arrays;

/**
 * Driver class utama
 * Nama: [Mutiara Y.H. Sianturi]
 * Nim: [12S24045]
 */
public class App {
    public static void main(String[] args) {

        System.out.println("========================");
        System.out.println(" SISTEM PENATAAN PARKIR IT DEL  ");
        System.out.println(" KARYAWAN PARKIR  ");
        System.out.println("========================");

        // RECORD — Park IT
        Departemen it = new Departemen("D01","IT","parkiran 1");
        Departemen hr = new Departemen("D02","HRD","parkiran 2");

        // ENUM — Jabatan
        // KARYAWAN — Inheritance + Polymorphism
        Karyawan k1 = new Karyawan("K001", "Budi",
            JabatanKaryawan.MANAGER, 8000000, it);
        Karyawan k2 = new Karyawan("K002", "Sari",
            JabatanKaryawan.STAFF, 4000000, hr);
            

        // GENERICS Repository
        Repository<Karyawan> repo = new Repository<>();
        repo.tambah(k1);
        repo.tambah(k2);
    

        System.out.println("\n=== DAFTAR KARYAWAN ===");
        repo.tampilkanSemua(); // POLYMORPHISM

        // OVERLOADING — hitung gaji
        System.out.println("Gaji 1 bulan : Rp" +
            k1.hitungTotalGaji());
        System.out.println("Gaji 12 bulan: Rp" +
            k1.hitungTotalGaji(12));

        // NESTED CLASS
        Perusahaan p = new Perusahaan("PT Del");
        p.tambahKaryawan(k1);
        p.tambahKaryawan(k2);

        Perusahaan.LaporanKaryawan lap =
            p.new LaporanKaryawan();
        lap.cetakLaporan();

        Perusahaan.InfoPerusahaan info =
            new Perusahaan.InfoPerusahaan();
        info.tampilkan();

        // JDBC — Database
        System.out.println("\n=== DATABASE ===");
        KaryawanDAO dao = new KaryawanDAO();
        dao.tambah("K001","Budi","MANAGER",8000000,"D01");
        dao.tampilkanSemua();
        dao.updateGaji("K001", 9000000);
        dao.hapus("K001");
    }
}





