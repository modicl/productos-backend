package cl.huertohogar.productos.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.huertohogar.productos.model.PaisOrigen;
import cl.huertohogar.productos.repository.PaisOrigenRepository;
import cl.huertohogar.productos.service.PaisOrigenService;



@RestController
@RequestMapping("/api/v1/paises")
public class PaisOrigenController {

    private final PaisOrigenRepository paisOrigenRepository;

    @Autowired
    PaisOrigenService paisOrgenService;

    public PaisOrigenController(PaisOrigenRepository paisOrigenRepository) {
        this.paisOrigenRepository = paisOrigenRepository;
    }

    // GET - Obtiene todos los paises de origen
    @GetMapping("")
    public ResponseEntity<List<PaisOrigen>> getAllPaises() {
        return ResponseEntity.ok(paisOrgenService.findAll());
    }

    // GET - Obtiene pais por ID

    @GetMapping("/{id}")
    public ResponseEntity<PaisOrigen> getPaisById(@PathVariable Integer id) {
        return ResponseEntity.ok(paisOrgenService.findById(id));
    }
    

    // POST - Crea un nuevo pais de origen
    @PostMapping("")
    public ResponseEntity<PaisOrigen> createPais(@RequestBody PaisOrigen pais) {
        return ResponseEntity.ok(paisOrgenService.save(pais));
    }

    // PUT - Actualiza un pais completo
    @PutMapping("/{id}")
    public ResponseEntity<PaisOrigen> updatePais(@PathVariable Integer id, @RequestBody PaisOrigen pais) {
        return ResponseEntity.ok(paisOrgenService.update(id, pais));
    }

    // PATCH - Actualiza parcialmente un pais
    @PatchMapping("/{id}")
    public ResponseEntity<PaisOrigen> patchPais(@PathVariable Integer id, @RequestBody PaisOrigen pais) {
        return ResponseEntity.ok(paisOrgenService.patch(id, pais));
    }

    // DELETE - Elimina un pais por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePais(@PathVariable Integer id) {
        paisOrigenRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
