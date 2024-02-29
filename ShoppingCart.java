import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

class Product {
    private String name;
    private double price;
    private int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }
}

public class ShoppingCart {
    private static final Scanner scanner = new Scanner(System.in);
    private static ArrayList<Product> products = new ArrayList<>();
    private static ArrayList<CartItem> cartItems = new ArrayList<>();

    public static void main(String[] args) {
        initializeProducts();
        runShopping();
    }

    private static void initializeProducts() {
        // Populate sample products
        products.add(new Product("Shirt", 19.99, 10));
        products.add(new Product("Pants", 29.99, 5));
        products.add(new Product("Hat", 14.99, 8));
    }

    private static void runShopping() {
        int choice;
        do {
            displayMenu();
            choice = getIntegerInput("Enter your choice: ");

            switch (choice) {
                case 1:
                    displayProducts();
                    break;
                case 2:
                    addProductToCart();
                    break;
                case 3:
                    viewCart();
                    break;
                case 4:
                    checkout();
                    break;
                case 5:
                    System.out.println("Exiting the application...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);
    }

    private static void displayMenu() {
        System.out.println("\n** Shopping Cart Menu **");
        System.out.println("1. View Products");
        System.out.println("2. Add Product to Cart");
        System.out.println("3. View Cart");
        System.out.println("4. Checkout");
        System.out.println("5. Exit");
    }

    private static void displayProducts() {
        System.out.println("\n** Available Products **");
        System.out.printf("%-20s %-10s %-10s\n", "Name", "Price", "Quantity");
        for (Product product : products) {
            System.out.printf("%-20s $%.2f %d\n", product.getName(), product.getPrice(), product.getQuantity());
        }
    }

    private static void addProductToCart() {
        displayProducts();
        int productId = getIntegerInput("Enter product ID to add: ");
        int quantity = getIntegerInput("Enter quantity to add: ");

        Product product = getProductById(productId);
        if (product != null) {
            int availableQuantity = product.getQuantity();
            if (availableQuantity >= quantity) {
                CartItem cartItem = new CartItem(product, quantity);
                cartItems.add(cartItem);
                product.setQuantity(availableQuantity - quantity);
                System.out.println(quantity + " " + product.getName() + "(s) added to cart.");
            } else {
                System.out.println("Insufficient quantity. Only " + availableQuantity + " available.");
            }
        } else {
            System.out.println("Invalid product ID.");
        }
    }

    private static void viewCart() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty.");
            return;
        }

        System.out.println("\n** Your Shopping Cart **");
        System.out.printf("%-20s %-10s %-10s %-10s\n", "Name", "Price", "Quantity", "Subtotal");
        double total = 0.0;
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            double subtotal = product.getPrice() * cartItem.getQuantity();
            total += subtotal;
            System.out.printf("%-20s $%.2f %d $%.2f\n", product.getName(), product.getPrice(), cartItem.getQuantity(), subtotal);
        }
        System.out.printf("%-20s %-20s\n", "Total:", "$" + String.format("%.2f", total));
    }

    private static void checkout() {
        if (cartItems.isEmpty()) {
            System.out.println("\nYour cart is empty. Please add items before checking out.");
            return;
        }

        System.out.println("\n** Checkout **");
        System.out.println("Please confirm your order:");
        viewCart();
        System.out.println("1. Confirm");
        System.out.println("2. Cancel");

        int choice = getIntegerInput("Enter choice: ");
        if (choice == 1) {
            System.out.println("Thank you for your purchase!");
            cartItems.clear();
        } else {
            System.out.println("Order canceled.");
        }
    }

    private static int getIntegerInput(String message) {
        while (true) {
            try {
                System.out.print(message);
                return scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer.");
                scanner.nextLine(); // Consume newline left by nextInt()
            }
        }
    }

    private static Product getProductById(int id) {
        for (Product product : products) {
            if (products.indexOf(product) + 1 == id) {
                return product;
            }
        }
        return null;
    }
}
