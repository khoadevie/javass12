import java.time.*;
import java.time.format.*;
import java.util.*;
import java.util.stream.*;

class Appointment {
    private String appointmentId;
    private String patientName;
    private String phoneNumber;
    private LocalDate appointmentDate;
    private String doctor;

    public Appointment() {
    }

    public String getAppointmentId() {
        return appointmentId;
    }

    public String getPatientName() {
        return patientName;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public String getDoctor() {
        return doctor;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public void setDoctor(String doctor) {
        this.doctor = doctor;
    }

    public void inputData(Scanner sc, List<Appointment> list) {
        while (true) {
            System.out.print("Ma lich hen (6 ky tu): ");
            appointmentId = sc.nextLine();
            boolean exists = list.stream().anyMatch(a -> a.appointmentId.equals(appointmentId));
            if (appointmentId.length() == 6 && !exists) break;
        }

        while (true) {
            System.out.print("Ten benh nhan: ");
            patientName = sc.nextLine();
            if (patientName.length() >= 10 && patientName.length() <= 50) break;
        }

        while (true) {
            System.out.print("So dien thoai: ");
            phoneNumber = sc.nextLine();
            if (phoneNumber.matches("0[0-9]{9}")) break;
        }

        DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        while (true) {
            try {
                System.out.print("Ngay hen (dd/MM/yyyy): ");
                appointmentDate = LocalDate.parse(sc.nextLine(), f);
                break;
            } catch (Exception e) {
            }
        }

        while (true) {
            System.out.print("Bac si: ");
            doctor = sc.nextLine();
            if (doctor.length() <= 200) break;
        }
    }

    public String toString() {
        return appointmentId + " | " + patientName + " | " + phoneNumber + " | " + appointmentDate + " | " + doctor;
    }
}

public class Main {
    static ArrayList<Appointment> list = new ArrayList<>();

    static void add(Scanner sc) {
        Appointment a = new Appointment();
        a.inputData(sc, list);
        list.add(a);
    }

    static void show() {
        list.stream()
                .sorted(Comparator.comparing(Appointment::getAppointmentDate))
                .forEach(System.out::println);
    }

    static void search(Scanner sc) {
        System.out.print("Nhap ten benh nhan: ");
        String key = sc.nextLine().toLowerCase();
        List<Appointment> rs = list.stream()
                .filter(a -> a.getPatientName().toLowerCase().contains(key))
                .toList();
        if (rs.isEmpty()) System.out.println("Khong tim thay");
        else rs.forEach(System.out::println);
    }

    static void update(Scanner sc) {
        System.out.print("Nhap ma lich hen: ");
        String id = sc.nextLine();
        Optional<Appointment> opt = list.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst();

        opt.ifPresentOrElse(a -> {
            System.out.print("Ten moi: ");
            a.setPatientName(sc.nextLine());
            System.out.print("So dt moi: ");
            a.setPhoneNumber(sc.nextLine());
            DateTimeFormatter f = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            while (true) {
                try {
                    System.out.print("Ngay moi: ");
                    a.setAppointmentDate(LocalDate.parse(sc.nextLine(), f));
                    break;
                } catch (Exception e) {
                }
            }
            System.out.print("Bac si moi: ");
            a.setDoctor(sc.nextLine());
        }, () -> System.out.println("Khong tim thay"));
    }

    static void delete(Scanner sc) {
        System.out.print("Nhap ma lich hen: ");
        String id = sc.nextLine();
        Optional<Appointment> opt = list.stream()
                .filter(a -> a.getAppointmentId().equals(id))
                .findFirst();

        opt.ifPresentOrElse(a -> {
            System.out.print("Xac nhan xoa (Y/N): ");
            String c = sc.nextLine();
            if (c.equalsIgnoreCase("Y")) list.remove(a);
        }, () -> System.out.println("Khong tim thay"));
    }

    static void stat() {
        System.out.println("Tong so lich hen: " + list.size());
        Map<String, Long> map = list.stream()
                .collect(Collectors.groupingBy(Appointment::getDoctor, Collectors.counting()));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.Them lich hen");
            System.out.println("2.Hien thi");
            System.out.println("3.Tim benh nhan");
            System.out.println("4.Cap nhat");
            System.out.println("5.Xoa");
            System.out.println("6.Thong ke");
            System.out.println("7.Thoat");
            int c = Integer.parseInt(sc.nextLine());
            switch (c) {
                case 1 -> add(sc);
                case 2 -> show();
                case 3 -> search(sc);
                case 4 -> update(sc);
                case 5 -> delete(sc);
                case 6 -> stat();
                case 7 -> System.exit(0);
            }
        }
    }
}
