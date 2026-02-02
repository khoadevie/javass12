import java.util.*;

class Product {
    private static int autoId = 1;
    private int productId;
    private String productName;
    private float price;
    private String category;
    private int quantity;

    public Product() {
        this.productId = autoId++;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public float getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void inputData(Scanner sc) {
        while (true) {
            System.out.print("Ten san pham: ");
            productName = sc.nextLine();
            if (productName.length() >= 10 && productName.length() <= 50) break;
        }

        while (true) {
            System.out.print("Gia: ");
            price = Float.parseFloat(sc.nextLine());
            if (price > 0) break;
        }

        while (true) {
            System.out.print("Danh muc: ");
            category = sc.nextLine();
            if (category.length() <= 200) break;
        }

        while (true) {
            System.out.print("So luong: ");
            quantity = Integer.parseInt(sc.nextLine());
            if (quantity >= 0) break;
        }
    }

    public String toString() {
        return "ID: " + productId + " | Name: " + productName + " | Price: " + price + " | Category: " + category + " | Quantity: " + quantity;
    }
}

public class Main {
    static ArrayList<Product> products = new ArrayList<>();

    static void add(Scanner sc) {
        Product p = new Product();
        p.inputData(sc);
        products.add(p);
    }

    static void show() {
        for (Product p : products) System.out.println(p);
    }

    static void update(Scanner sc) {
        System.out.print("Nhap ID: ");
        int id = Integer.parseInt(sc.nextLine());
        for (Product p : products) {
            if (p.getProductId() == id) {
                System.out.print("Ten moi: ");
                p.setProductName(sc.nextLine());
                System.out.print("Gia moi: ");
                p.setPrice(Float.parseFloat(sc.nextLine()));
                System.out.print("Danh muc moi: ");
                p.setCategory(sc.nextLine());
                System.out.print("So luong moi: ");
                p.setQuantity(Integer.parseInt(sc.nextLine()));
                return;
            }
        }
    }

    static void delete(Scanner sc) {
        System.out.print("Nhap ID: ");
        int id = Integer.parseInt(sc.nextLine());
        Iterator<Product> it = products.iterator();
        while (it.hasNext()) {
            if (it.next().getProductId() == id) {
                it.remove();
                return;
            }
        }
    }

    static void search(Scanner sc) {
        System.out.print("Nhap ten: ");
        String key = sc.nextLine().toLowerCase();
        for (Product p : products) {
            if (p.getProductName().toLowerCase().contains(key)) System.out.println(p);
        }
    }

    static void sortPrice() {
        products.sort(Comparator.comparing(Product::getPrice));
    }

    static void sortQuantity() {
        products.sort((a, b) -> b.getQuantity() - a.getQuantity());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("1.Them");
            System.out.println("2.Danh sach");
            System.out.println("3.Cap nhat");
            System.out.println("4.Xoa");
            System.out.println("5.Tim ten");
            System.out.println("6.Sap xep gia");
            System.out.println("7.Sap xep so luong");
            System.out.println("8.Thoat");
            int c = Integer.parseInt(sc.nextLine());
            switch (c) {
                case 1 -> add(sc);
                case 2 -> show();
                case 3 -> update(sc);
                case 4 -> delete(sc);
                case 5 -> search(sc);
                case 6 -> sortPrice();
                case 7 -> sortQuantity();
                case 8 -> System.exit(0);
            }
        }
    }
}
