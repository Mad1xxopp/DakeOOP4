import java.sql.Connection;
import java.util.List;

public interface CarDAO {
    void createCars(Connection con, String Cars);
    void addCar(Car car);
    void removeCar(String brand, String model);
    void updateCarPrice(String brand, double newPrice);
    List<Car> getAllCars();
}