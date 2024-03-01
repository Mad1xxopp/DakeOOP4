import javax.swing.plaf.nimbus.State;
import java.security.PublicKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CarDAOJDBC implements CarDAO{
    private Connection connection;

    // Конструктор, который получает соединение с базой данных
    public CarDAOJDBC(Connection connection) {
        this.connection = connection;
    }
    @Override
    public void createCars(Connection con, String Cars){
        Statement st;
        try{
            String query = "create table if not exists " + Cars + "(id SERIAL, brand varchar(200), model varchar(200), year varchar(20000), price varchar(1000000));";
            st = con.createStatement();
            st.executeUpdate(query);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }


    @Override
    public void addCar(Car car) {
        try (PreparedStatement statement = connection.prepareStatement("INSERT INTO cars (brand, model, year, price) VALUES (?, ?, ?, ?)")) {
            statement.setString(1, car.getBrand());
            statement.setString(2, car.getModel());
            statement.setInt(3, car.getYear());
            statement.setDouble(4, car.getPrice());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeCar(String brand, String model) {
        try (PreparedStatement statement = connection.prepareStatement("DELETE FROM cars WHERE brand = ? AND model = ?")) {
            statement.setString(1, brand);
            statement.setString(2, model);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCarPrice(String brand, double newPrice) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE cars SET price = ? WHERE brand = ?")) {
            statement.setDouble(1, newPrice);
            statement.setString(2, brand);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT * FROM cars")) {
            while (resultSet.next()) {
                String brand = resultSet.getString("brand");
                String model = resultSet.getString("model");
                int year = resultSet.getInt("year");
                double price = resultSet.getDouble("price");
                cars.add(new Car(brand, model, year, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }
}