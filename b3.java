import java.util.*;
import java.util.stream.*;

enum Status {
    Pending, Shipped, Delivered
}

class Order {
    private static int autoId = 1;
    private int orderId;
    private String customerName;
    private String phoneNumber;
    private String address;
    private double orderAmount;
    private Status status;

    public Order() {
        this.orderId = autoId++;
        this.status = Status.Pending;
    }

    public int getOrderId() {
        return orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getOrderAmount() {
        return orderAmount;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status s) {
        status = s;
    }

    public void inputData(Scanner sc) {
        while (true) {
            System.out.print("Ten khach hang: ");
            customerName = sc.nextLine();
            if (customerName.length() >= 6 && customerName.length() <= 100) break;
        }

        while (true) {
            System.out.print("So dien thoai: ");
            phoneNumber = sc.nextLine();
            if (phoneNumber.matches("0[0-9]{9}")) break;
        }

        while (true) {
            System.out.print("Dia chi: ");
            address = sc.nextLine();
            if (!address.isBlank()) break;
        }

        while (true) {
            try {
                System.out.print("Gia tri don hang: ");
                orderAmount = Double.parseDouble(sc.nextLine());
                if (orderAmount > 0) break;
            } catch (Exception e) {
            }
        }
    }

    public String toString() {
        return orderId + " | " + customerName + " | " + phoneNumber + " | " + address + " | " + orderAmount + " | " + status;
    }
}

public class Main {
    static ArrayList<Order> list = new ArrayList<>();

    static void add(Scanner sc) {
        Order o = new Order();
        o.inputData(sc);
        list.add(o);
    }

    static void show() {
        list.stream()
                .sorted(Comparator.comparing(Order::getOrderAmount).reversed())
                .forEach(System.out::println);
    }

    static void updateStatus(Scanner sc) {
        System.out.print("Nhap ma don hang: ");
        int id = Integer.parseInt(sc.nextLine());
        Optional<Order> opt = list.stream().filter(o -> o.getOrderId() == id).findFirst();
        opt.ifPresentOrElse(o -> {
            if (o.getStatus() == Status.Pending) o.setStatus(Status.Shipped);
            else if (o.getStatus() == Status.Shipped) o.setStatus(Status.Delivered);
        }, () -> System.out.println("Khong tim thay"));
    }

    static void delete(Scanner sc) {
        System.out.print("Nhap ma don hang: ");
        int id = Integer.parseInt(sc.nextLine());
        Optional<Order> opt = list.stream().filter(o -> o.getOrderId() == id).findFirst();
        opt.ifPresentOrElse(o -> {
            if (o.getStatus() == Status.Pending) list.remove(o);
            else System.out.println("Chi xoa duoc don Pending");
        }, () -> System.out.println("Khong tim thay"));
    }

    static void search(Scanner sc) {
        System.out.print("Nhap ten khach hang: ");
        String key = sc.nextLine().toLowerCase();
        List<Order> rs = list.stream()
                .filter(o -> o.getCustomerName().toLowerCase().contains(key))
                .toList();
        if (rs.isEmpty()) System.out.println("Khong tim thay");
        else rs.forEach(System.out::println);
    }

    static void statTotal() {
        System.out.println("Tong so don hang: " + list.size());
    }

    static void statRevenue() {
        double sum = list.stream()
                .filter(o -> o.getStatus() == Status.Delivered)
                .mapToDouble(Order::getOrderAmount)
                .sum();
        System.out.println("Tong doanh thu Delivered: " + sum);
    }

    static void statByStatus() {
        Map<Status, Long> map = list.stream()
                .collect(Collectors.groupingBy(Order::getStatus, Collectors.counting()));
        map.forEach((k, v) -> System.out.println(k + ": " + v));
    }

    static void maxOrder() {
        Optional<Order> o = list.stream()
                .max(Comparator.comparing(Order::getOrderAmount));
        o.ifPresentOrElse(System.out::println, () -> System.out.println("Danh sach rong"));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.Them don hang");
            System.out.println("2.Hien thi danh sach don hang");
            System.out.println("3.Cap nhat trang thai");
            System.out.println("4.Xoa don hang");
            System.out.println("5.Tim don theo ten khach hang");
            System.out.println("6.Thong ke tong so don");
            System.out.println("7.Thong ke doanh thu Delivered");
            System.out.println("8.Thong ke theo trang thai");
            System.out.println("9.Tim don gia tri lon nhat");
            System.out.println("10.Thoat");
            int c = Integer.parseInt(sc.nextLine());
            switch (c) {
                case 1 -> add(sc);
                case 2 -> show();
                case 3 -> updateStatus(sc);
                case 4 -> delete(sc);
                case 5 -> search(sc);
                case 6 -> statTotal();
                case 7 -> statRevenue();
                case 8 -> statByStatus();
                case 9 -> maxOrder();
                case 10 -> System.exit(0);
            }
        }
    }
}
