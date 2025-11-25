package cl.huertohogar.productos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.huertohogar.productos.client.UsuarioFeignClient;
import cl.huertohogar.productos.dto.ComentarioRequestDTO;
import cl.huertohogar.productos.dto.ComentarioResponseDTO;
import cl.huertohogar.productos.dto.UsuarioDTO;
import cl.huertohogar.productos.exception.ComentarioNotValidException;
import cl.huertohogar.productos.exception.ProductoNotFoundException;
import cl.huertohogar.productos.model.Comentario;
import cl.huertohogar.productos.model.Producto;
import cl.huertohogar.productos.repository.ComentarioRepository;
import cl.huertohogar.productos.repository.ProductoRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private UsuarioFeignClient usuarioFeignClient;

    @Autowired
    private ProductoRepository productoRepository;

    public Comentario postComentario(ComentarioRequestDTO comentarioDTO) {

        // Validar datos básicos
        if (comentarioDTO.getCalificacion() == null || comentarioDTO.getCalificacion() < 1 || comentarioDTO.getCalificacion() > 5) {
            throw new ComentarioNotValidException("La calificación debe estar entre 1 y 5");
        }
        if (comentarioDTO.getComentario() == null || comentarioDTO.getComentario().trim().isEmpty()) {
            throw new ComentarioNotValidException("El comentario no puede estar vacío");
        }
        if (comentarioDTO.getUsuarioId() == null) {
             throw new ComentarioNotValidException("El ID de usuario es obligatorio");
        }

        // Buscamos Producto pro ID
        Producto producto = productoRepository.findById(comentarioDTO.getIdProducto())
            .orElseThrow(() -> new ProductoNotFoundException("Producto no encontrado con id: " + comentarioDTO.getIdProducto()));

        // Crear el comentario
        Comentario comentario = new Comentario();

        // Enriquecer
        comentario.setCalificacion(comentarioDTO.getCalificacion());
        comentario.setComentario(comentarioDTO.getComentario());
        comentario.setFecha(comentarioDTO.getFecha());
        comentario.setProducto(producto);
        comentario.setUsuarioId(comentarioDTO.getUsuarioId());

        // Guardar

        return comentarioRepository.save(comentario);

    }

    public List<ComentarioResponseDTO> findComentarioByProductId(Integer idProducto) {

        // Obtenemos todos los comentarios del producto

        List<Comentario> comentarios = comentarioRepository.findComentarioByProductId(idProducto);

        // Enriquecer con datos de usuario

        List<ComentarioResponseDTO> comentariosDto = comentarios.stream()
                .map(comentario -> {

                    // Inicializamos el comentarioDTO
                    ComentarioResponseDTO comentarioDto = new ComentarioResponseDTO();

                    // Obtenemos nombre usuario
                    UsuarioDTO usuario = usuarioFeignClient.getUsuarioById(comentario.getUsuarioId());
                    System.out.println("Usuario obtenido: " + usuario); // Log para depurar

                    // Enriquecemos
                    comentarioDto.setCalificacion(comentario.getCalificacion());
                    comentarioDto.setComentario(comentario.getComentario());
                    comentarioDto.setFecha(comentario.getFecha());
                    comentarioDto.setIdComentario(comentario.getIdComentario());
                    comentarioDto.setUsuario(usuario);

                    return comentarioDto;
                })
                .toList();

        return comentariosDto;
    }
}
