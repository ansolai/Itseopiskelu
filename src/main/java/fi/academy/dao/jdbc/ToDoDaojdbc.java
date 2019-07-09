package fi.academy.dao.jdbc;

import fi.academy.ToDo;
import fi.academy.dao.ToDoDao;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ToDoDaojdbc implements ToDoDao {
    private Connection con;

    public ToDoDaojdbc()throws SQLException, ClassNotFoundException {
        Class.forName("org.postgresql.Driver");
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/uusitehtava", "postgres", "AcademyAnna");
        System.out.println("Yhteys on päällä (ToDoDaoJdbc)");
    }

    @Override
    public List<ToDo> haeKaikki() {
        List<ToDo>haetut = new ArrayList<>();
        try (PreparedStatement statement = con.prepareStatement("SELECT * FROM todotaulukko")) {
            for (ResultSet tulos = statement.executeQuery();
            tulos.next();) {
                ToDo tehtava = new ToDo();
                tehtava.setId(tulos.getInt("id"));
                tehtava.setTask(tulos.getString("task"));
                haetut.add(tehtava);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.EMPTY_LIST;
        }
        System.out.println("(ToDoDaoJdbc:) Taulukon todotaulukko sisältö: " + haetut);
        return haetut;
    }
//tulos.close(); statement.close(); con.close();

    @Override
    public int lisaa(ToDo tehtava) throws SQLException{
        String task = tehtava.getTask();
        String sql = "INSERT INTO todotaulukko (task) VALUES (?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, task);
        statement.executeUpdate();
        System.out.println("(ToDoDaoJdbc:) Tämä lisättiin: " + tehtava.getId());

        return tehtava.getId();
    }

    @Override
    public int poista(int id) throws SQLException{
        int poista = id;
        String sql = "DELETE FROM todotaulukko WHERE (id=?)";
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
        System.out.println("(ToDoDapJdbc: ) Tämä poistettiin " + id);

        return id;
    }

    @Override
    public Optional<ToDo> haeIdlla(int id) {
        return Optional.empty();
    }

    @Override
    public boolean muuta(int id, ToDo tiedot) {
        return false;
    }
}
