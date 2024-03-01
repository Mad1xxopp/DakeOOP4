import javax.xml.crypto.Data;
import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

public class CarsApplication {
    private static final CarDAO carDAO = new CarDAOJDBC(DatabaseConnection.getConnection());

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            cars();
            System.out.println("1. Show all cars");
            System.out.println("2. Add a car");
            System.out.println("3. Remove a car");
            System.out.println("4. Update a car price");
            System.out.println("5. Exit");

            System.out.print("Choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Очистка буфера

            switch (choice) {
                case 1:
                    showAllCars();
                    break;
                case 2:
                    addCar();
                    break;
                case 3:
                    removeCar();
                    break;
                case 4:
                    updateCarPrice();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice!");
            }
        }
    }

    private static void showAllCars() {
        List<Car> cars = carDAO.getAllCars();
        for (Car car : cars) {
            System.out.println(car);
        }
    }
    private static void cars(){
        DatabaseConnection db = new DatabaseConnection();
        Connection con = db.getConnection();
        CarDAOJDBC cr = new CarDAOJDBC(con);
        cr.createCars(con, "Cars");

    }

    private static void addCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the brand of the car:");
        String brand = scanner.nextLine();

        System.out.println("Enter the model of the car:");
        String model = scanner.nextLine();

        System.out.println("Enter the year of the car:");
        int year = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        System.out.println("Enter the price of the car:");
        double price = scanner.nextDouble();

        Car car = new Car(brand, model, year, price);
        carDAO.addCar(car);
        System.out.println("Car successfully added!");
    }

    private static void removeCar() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the brand of the car you want to remove:");
        String brand = scanner.nextLine();

        System.out.println("Enter the model of the car you want to remove:");
        String model = scanner.nextLine();

        carDAO.removeCar(brand, model);
        System.out.println("Car successfully removed!");
    }

    private static void updateCarPrice() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the brand of the car you want to update:");
        String brand = scanner.nextLine();

        System.out.println("Enter the new price:");
        double newPrice = scanner.nextDouble();

        carDAO.updateCarPrice(brand, newPrice);
        System.out.println("Car price updated successfully!");
    }
}

