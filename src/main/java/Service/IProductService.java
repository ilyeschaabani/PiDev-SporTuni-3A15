package Service;
import java.sql.SQLException;
import java.util.List;

public interface IProductService<T> {
    void add(T t);

    void delete(T t) throws SQLException;

    void update(T t);

    List<T> readAll();

    T readById(int id);
}
