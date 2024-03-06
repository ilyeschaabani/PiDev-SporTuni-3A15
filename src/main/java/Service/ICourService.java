package Service;

import java.sql.SQLException;
import java.util.List;

public interface ICourService <T>{

    void add(T t);
    //void delete(T t);
    void delete(int id);
    void update(T t,int id );
    void updato(T t) throws SQLException;

    List<T> readAll();
    T readById(int id);

}
