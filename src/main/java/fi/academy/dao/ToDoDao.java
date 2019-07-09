package fi.academy.dao;

import fi.academy.ToDo;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ToDoDao {
    List<ToDo> haeKaikki();
    Optional<ToDo> haeIdlla(int id);
    int lisaa(ToDo tehtava) throws SQLException;
    int poista(int id) throws SQLException;
    boolean muuta (int id, ToDo tiedot);
}
