
package cl.huertohogar.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.productos.exception.PaisOrigenNotFoundException;
import cl.huertohogar.productos.exception.PaisOrigenNotValidException;
import cl.huertohogar.productos.model.PaisOrigen;
import cl.huertohogar.productos.repository.PaisOrigenRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaisOrigenService {
    @Autowired
    PaisOrigenRepository paisOrigenRepository;

    // CREATE
    public PaisOrigen save(PaisOrigen paisOrigen) {
        if (paisOrigen == null) {
            throw new PaisOrigenNotValidException("El país de origen no puede ser nulo");
        }
        if (paisOrigen.getNombre() == null || paisOrigen.getNombre().trim().isEmpty()) {
            throw new PaisOrigenNotValidException("El nombre del país es obligatorio");
        }
        return paisOrigenRepository.save(paisOrigen);
    }

    // READ
    public List<PaisOrigen> findAll() {
        List<PaisOrigen> paises = paisOrigenRepository.findAll();
        if (paises.isEmpty()) {
            throw new PaisOrigenNotFoundException("No se encontraron países de origen");
        }
        return paises;
    }

    // READ
    public PaisOrigen findById(Integer id) {
        return paisOrigenRepository.findById(id)
                .orElseThrow(() -> new PaisOrigenNotFoundException("País de origen no encontrado con id: " + id));
    }

    // UPDATE
    public PaisOrigen update(Integer id, PaisOrigen paisActualizado) {
        PaisOrigen paisExistente = findById(id);

        if (paisActualizado.getNombre() == null || paisActualizado.getNombre().trim().isEmpty()) {
            throw new PaisOrigenNotValidException("El nombre del país es obligatorio");
        }

        paisExistente.setNombre(paisActualizado.getNombre());
        return paisOrigenRepository.save(paisExistente);
    }

    // PATCH -
    public PaisOrigen patch(Integer id, PaisOrigen paisActualizado) {
        PaisOrigen paisExistente = findById(id);

        if (paisActualizado.getNombre() != null) {
            if (paisActualizado.getNombre().trim().isEmpty()) {
                throw new PaisOrigenNotValidException("El nombre del país no puede estar vacío");
            }
            paisExistente.setNombre(paisActualizado.getNombre());
        }

        return paisOrigenRepository.save(paisExistente);
    }

    // DELETE
    public void delete(Integer id) {
        PaisOrigen paisExistente = findById(id);
        paisOrigenRepository.delete(paisExistente);
    }

}
