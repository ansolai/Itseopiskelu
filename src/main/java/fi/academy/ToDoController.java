package fi.academy;

import fi.academy.dao.ToDoDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/tehtavat")
public class ToDoController {
    private ToDoDao dao;

    @Autowired
    public ToDoController(ToDoDao dao){
        this.dao=dao;
    }

    @GetMapping("")
    public List<ToDo> listaaTehtavat(){
        List<ToDo> kaikki = dao.haeKaikki();
        System.out.printf(" (ToDoController:) Haetaan lista, jossa alkioita: %d kpl", kaikki.size());
        return kaikki;
    }

    @PostMapping("")
    public ResponseEntity<?>luoUusi(@RequestBody ToDo uusi) {
        int id = 0;
        try {
            id = dao.lisaa(uusi);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
        System.out.println("(ToDoController:) luotu uusi tehtava");
        return ResponseEntity.created(location).body(uusi);
    }

    @DeleteMapping("/{id}")
    public int poista(@PathVariable int id) throws SQLException {
        dao.poista(id);
        return id;
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<?>muokkaa(@RequestBody ToDo tiedot,
//                                    @PathVariable ("id") int id) {
//        boolean muuttuiko = dao.muuta(id, tiedot);
//        if (muuttuiko) {
//            tiedot.setId(id);
//            System.out.printf("(ToDoController: ) Tehtävää %d on muokattu");
//            return ResponseEntity.ok(tiedot);
//        }
//        return ResponseEntity
//                .status(HttpStatus.NOT_FOUND)
//                .body(new Virheviesti(String.format("Ei voida muokata, sillä Id:tä %d ei ole olemassa", id)));
//    }
    //    @GetMapping("/{id}")
//    public ResponseEntity<?> etsiTietty(@PathVariable(name = "id", required = true), int id){
//        var haettu = dao.haeIdlla(id);
//        if (!haettu.isPresent()){
//            return ResponseEntity
//                    .status(HttpStatus.NOT_FOUND)
//                    .body(new Virheviesti(String.format("Id:tä %d ei löytynyt", id)));
//        }
//        return ResponseEntity.ok(haettu.get());
//    }
}
