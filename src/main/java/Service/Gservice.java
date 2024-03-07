package Service;

import java.util.List;

public interface Gservice <E> {
    void add(E e);
    void delete(E e);
    void update(E e);
    List<E> readAll();
    E readById(int id);
}
