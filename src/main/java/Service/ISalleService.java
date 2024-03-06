package service;

import java.util.List;
import Entity.Salle;

public interface ISalleService <T>{
    void add(T t);
    //void delete(T t);
    void delete(int id);
    void update(T t,int id );
    void updato(T t);

    List<T> readAll();
    T readById(int id);


}