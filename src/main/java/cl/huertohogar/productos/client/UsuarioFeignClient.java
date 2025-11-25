package cl.huertohogar.productos.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import cl.huertohogar.productos.dto.UsuarioDTO;

@FeignClient(name = "usuarios-service", url = "https://hh-usuario-backend-efp2p.ondigitalocean.app")
public interface UsuarioFeignClient {

    @GetMapping("/api/v1/public/usuarios/{idUsuario}/nombre")
    UsuarioDTO getUsuarioById(@PathVariable Integer idUsuario);
}
