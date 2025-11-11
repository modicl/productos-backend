package cl.huertohogar.productos.config;

import cl.huertohogar.productos.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class RoleCheckInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequireRole roleAnnotation = handlerMethod.getMethodAnnotation(RequireRole.class);

        // Si no tiene anotación @RequireRole, permitir
        if (roleAnnotation == null) {
            return true;
        }

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token no proporcionado\"}");
            return false;
        }

        String token = authHeader.substring(7);
        
        // Validar que el token sea válido
        if (!jwtUtil.isTokenValid(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Token inválido o expirado\"}");
            return false;
        }

        // Extraer el rol del token
        String userRol = null;
        try {
            userRol = jwtUtil.extractRol(token);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No se pudo extraer el rol del token\"}");
            return false;
        }

        String[] requiredRoles = roleAnnotation.value();

        // Verificar si el rol del usuario está en los roles requeridos
        if (!Arrays.asList(requiredRoles).contains(userRol)) {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"No tienes permiso para acceder a este recurso\"}");
            return false;
        }

        // Guardar el rol en el request para usarlo en el controller si es necesario
        request.setAttribute("userRol", userRol);
        request.setAttribute("usuarioId", jwtUtil.extractUsuarioId(token));

        return true;
    }
}

