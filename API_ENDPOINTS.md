# API Endpoints - Productos Backend

Guía rápida de endpoints para integración con React/Axios.

**Base URL:** `http://localhost:8080/api/v1`

---

## 🔐 Autenticación

Los endpoints marcados con 🔒 requieren token JWT en el header:
```javascript
headers: {
  'Authorization': `Bearer ${token}`
}
```

---

## 📦 PRODUCTOS

### GET `/productos`
**🔓 Público**

Obtiene todos los productos del catálogo.

**Axios:**
```javascript
const response = await axios.get('http://localhost:8080/api/v1/productos');
```

**Respuesta:**
```json
[
  {
    "idProducto": 1,
    "nombreProducto": "Tomate Cherry Orgánico",
    "categoria": {
      "idCategoria": 1,
      "nombreCategoria": "Verduras"
    },
    "descripcionProducto": "Tomates cherry frescos",
    "precioProducto": 2500,
    "stockProducto": 150,
    "paisOrigen": {
      "idPais": 1,
      "nombre": "Chile"
    },
    "imagenUrl": "https://ejemplo.com/tomate.jpg"
  }
]
```

---

### GET `/productos/{id}`
**🔓 Público**

Obtiene un producto específico por su ID.

**Axios:**
```javascript
const response = await axios.get(`http://localhost:8080/api/v1/productos/${id}`);
```

**Respuesta:** Objeto producto individual (igual estructura que arriba).

---

### GET `/productos/categoria/{id}`
**🔓 Público**

Obtiene todos los productos de una categoría específica.

**Uso:** Para mostrar productos filtrados por categoría (ej: "Verduras", "Frutas").

**Axios:**
```javascript
const categoriaId = 1;
const response = await axios.get(`http://localhost:8080/api/v1/productos/categoria/${categoriaId}`);
```

**Respuesta:** Array de productos de esa categoría.

---

### GET `/productos/precio?min={min}&max={max}`
**🔓 Público**

Obtiene productos dentro de un rango de precio.

**Uso:** Para filtros de precio en tu UI.

**Axios:**
```javascript
const response = await axios.get('http://localhost:8080/api/v1/productos/precio', {
  params: {
    min: 1000,
    max: 5000
  }
});
```

**Respuesta:** Array de productos en ese rango de precio.

---

### POST `/productos`
**🔒 Requiere ADMIN**

Crea un nuevo producto.

**Uso:** Panel de administración para agregar productos al catálogo.

**Axios:**
```javascript
const nuevoProducto = {
  nombreProducto: "Lechuga Hidropónica",
  categoria: { idCategoria: 1 },
  descripcionProducto: "Lechuga fresca cultivada en sistema hidropónico",
  precioProducto: 1500,
  stockProducto: 80,
  paisOrigen: { idPais: 1 },
  imagenUrl: "https://ejemplo.com/lechuga.jpg"
};

const response = await axios.post(
  'http://localhost:8080/api/v1/productos',
  nuevoProducto,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

**Respuesta:** Producto creado con su ID asignado (status 201).

---

### PUT `/productos/{id}`
**🔒 Requiere ADMIN**

Actualiza TODOS los campos de un producto existente.

**Uso:** Edición completa de producto en panel admin.

**Nota:** Debes enviar todos los campos, incluso los que no cambies.

**Axios:**
```javascript
const productoActualizado = {
  nombreProducto: "Tomate Cherry Premium",
  categoria: { idCategoria: 1 },
  descripcionProducto: "Descripción actualizada",
  precioProducto: 2800,
  stockProducto: 200,
  paisOrigen: { idPais: 1 },
  imagenUrl: "https://ejemplo.com/tomate-premium.jpg"
};

const response = await axios.put(
  `http://localhost:8080/api/v1/productos/${id}`,
  productoActualizado,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### PATCH `/productos/{id}`
**🔒 Requiere ADMIN**

Actualiza SOLO los campos que envíes.

**Uso:** Actualización parcial (ej: solo cambiar precio o stock).

**Ventaja:** No necesitas enviar todos los campos.

**Axios:**
```javascript
// Solo actualizar precio y stock
const response = await axios.patch(
  `http://localhost:8080/api/v1/productos/${id}`,
  {
    precioProducto: 3000,
    stockProducto: 100
  },
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### DELETE `/productos/{id}`
**🔒 Requiere ADMIN**

Elimina un producto del catálogo.

**Uso:** Remover productos descontinuados.

**Axios:**
```javascript
await axios.delete(`http://localhost:8080/api/v1/productos/${id}`, {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});
```

**Respuesta:** Status 204 (No Content) si fue exitoso.

---

## 🏷️ CATEGORÍAS

### GET `/categorias`
**🔓 Público**

Obtiene todas las categorías disponibles.

**Uso:** Para mostrar menú de categorías, filtros, selectores.

**Axios:**
```javascript
const response = await axios.get('http://localhost:8080/api/v1/categorias');
```

**Respuesta:**
```json
[
  {
    "idCategoria": 1,
    "nombreCategoria": "Verduras",
    "descripcionCategoria": "Verduras frescas y orgánicas"
  },
  {
    "idCategoria": 2,
    "nombreCategoria": "Frutas",
    "descripcionCategoria": "Frutas de temporada"
  }
]
```

---

### GET `/categorias/{id}`
**🔓 Público**

Obtiene una categoría específica.

**Axios:**
```javascript
const response = await axios.get(`http://localhost:8080/api/v1/categorias/${id}`);
```

---

### POST `/categorias`
**🔒 Requiere ADMIN**

Crea una nueva categoría.

**Uso:** Panel admin para agregar nuevas categorías de productos.

**Axios:**
```javascript
const nuevaCategoria = {
  nombreCategoria: "Legumbres",
  descripcionCategoria: "Legumbres secas y envasadas"
};

const response = await axios.post(
  'http://localhost:8080/api/v1/categorias',
  nuevaCategoria,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### PUT `/categorias/{id}`
**🔒 Requiere ADMIN**

Actualiza una categoría completa.

**Axios:**
```javascript
const categoriaActualizada = {
  nombreCategoria: "Verduras Orgánicas",
  descripcionCategoria: "Verduras certificadas orgánicas sin pesticidas"
};

const response = await axios.put(
  `http://localhost:8080/api/v1/categorias/${id}`,
  categoriaActualizada,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### PATCH `/categorias/{id}`
**🔒 Requiere ADMIN**

Actualiza solo campos específicos de una categoría.

**Axios:**
```javascript
// Solo actualizar la descripción
const response = await axios.patch(
  `http://localhost:8080/api/v1/categorias/${id}`,
  {
    descripcionCategoria: "Nueva descripción actualizada"
  },
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### DELETE `/categorias/{id}`
**🔒 Requiere ADMIN**

Elimina una categoría.

**Axios:**
```javascript
await axios.delete(`http://localhost:8080/api/v1/categorias/${id}`, {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});
```

---

## 🌎 PAÍSES DE ORIGEN

### GET `/paises`
**🔓 Público**

Obtiene todos los países de origen disponibles.

**Uso:** Para selectores en formularios, filtros por origen.

**Axios:**
```javascript
const response = await axios.get('http://localhost:8080/api/v1/paises');
```

**Respuesta:**
```json
[
  {
    "idPais": 1,
    "nombre": "Chile"
  },
  {
    "idPais": 2,
    "nombre": "Perú"
  }
]
```

---

### GET `/paises/{id}`
**🔓 Público**

Obtiene un país específico.

**Axios:**
```javascript
const response = await axios.get(`http://localhost:8080/api/v1/paises/${id}`);
```

---

### POST `/paises`
**🔒 Requiere ADMIN**

Crea un nuevo país de origen.

**Uso:** Panel admin para agregar países disponibles.

**Axios:**
```javascript
const nuevoPais = {
  nombre: "Argentina"
};

const response = await axios.post(
  'http://localhost:8080/api/v1/paises',
  nuevoPais,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### PUT `/paises/{id}`
**🔒 Requiere ADMIN**

Actualiza un país completo.

**Axios:**
```javascript
const paisActualizado = {
  nombre: "República de Chile"
};

const response = await axios.put(
  `http://localhost:8080/api/v1/paises/${id}`,
  paisActualizado,
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### PATCH `/paises/{id}`
**🔒 Requiere ADMIN**

Actualiza solo el nombre del país.

**Axios:**
```javascript
const response = await axios.patch(
  `http://localhost:8080/api/v1/paises/${id}`,
  {
    nombre: "Bolivia"
  },
  {
    headers: {
      'Authorization': `Bearer ${token}`
    }
  }
);
```

---

### DELETE `/paises/{id}`
**🔒 Requiere ADMIN**

Elimina un país.

**Axios:**
```javascript
await axios.delete(`http://localhost:8080/api/v1/paises/${id}`, {
  headers: {
    'Authorization': `Bearer ${token}`
  }
});
```

---

## 🚨 Manejo de Errores

Todas las respuestas de error tienen este formato:

```json
{
  "timestamp": "2025-11-11T14:30:00",
  "message": "Producto no encontrado con id: 999",
  "status": 404
}
```

### Códigos de Estado Comunes

| Código | Significado | Cuándo ocurre |
|--------|-------------|---------------|
| 200 | OK | Operación exitosa |
| 201 | Created | Recurso creado (POST) |
| 204 | No Content | Recurso eliminado (DELETE) |
| 400 | Bad Request | Datos inválidos (validación) |
| 401 | Unauthorized | Token no válido o ausente |
| 403 | Forbidden | No tienes permisos (no eres ADMIN) |
| 404 | Not Found | Recurso no existe |
| 500 | Server Error | Error interno del servidor |

### Ejemplo de Manejo en React

```javascript
try {
  const response = await axios.get('http://localhost:8080/api/v1/productos');
  setProductos(response.data);
} catch (error) {
  if (error.response) {
    // El servidor respondió con un código de error
    switch (error.response.status) {
      case 401:
        // Redirigir al login
        navigate('/login');
        break;
      case 403:
        // Mostrar mensaje de "No autorizado"
        alert('No tienes permisos para realizar esta acción');
        break;
      case 404:
        // Mostrar mensaje de "No encontrado"
        alert(error.response.data.message);
        break;
      default:
        alert('Error: ' + error.response.data.message);
    }
  } else {
    // Error de red o servidor no responde
    alert('Error de conexión con el servidor');
  }
}
```

---

## 💡 Tips para React

### 1. Crear un servicio de API centralizado

```javascript
// services/api.js
import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api/v1';

// Crear instancia de axios con configuración base
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
});

// Interceptor para agregar token automáticamente
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

export default api;
```

### 2. Uso en componentes

```javascript
// Importar el servicio
import api from './services/api';

// En tu componente
const obtenerProductos = async () => {
  try {
    const response = await api.get('/productos');
    setProductos(response.data);
  } catch (error) {
    console.error('Error:', error);
  }
};

const crearProducto = async (producto) => {
  try {
    const response = await api.post('/productos', producto);
    alert('Producto creado exitosamente');
  } catch (error) {
    alert(error.response?.data?.message || 'Error al crear producto');
  }
};
```

### 3. Hooks personalizados

```javascript
// hooks/useProductos.js
import { useState, useEffect } from 'react';
import api from '../services/api';

export const useProductos = () => {
  const [productos, setProductos] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchProductos = async () => {
      try {
        const response = await api.get('/productos');
        setProductos(response.data);
      } catch (err) {
        setError(err.response?.data?.message || 'Error al cargar productos');
      } finally {
        setLoading(false);
      }
    };

    fetchProductos();
  }, []);

  return { productos, loading, error };
};
```

---

## 📌 Resumen Rápido

### Endpoints Públicos (sin token)
- `GET /productos` - Listar productos
- `GET /productos/{id}` - Ver producto
- `GET /productos/categoria/{id}` - Filtrar por categoría
- `GET /productos/precio?min=X&max=Y` - Filtrar por precio
- `GET /categorias` - Listar categorías
- `GET /categorias/{id}` - Ver categoría
- `GET /paises` - Listar países
- `GET /paises/{id}` - Ver país

### Endpoints Protegidos (requieren token ADMIN)
- `POST /productos` - Crear producto
- `PUT /productos/{id}` - Actualizar producto completo
- `PATCH /productos/{id}` - Actualizar producto parcial
- `DELETE /productos/{id}` - Eliminar producto
- `POST /categorias` - Crear categoría
- `PUT /categorias/{id}` - Actualizar categoría
- `PATCH /categorias/{id}` - Actualizar categoría parcial
- `DELETE /categorias/{id}` - Eliminar categoría
- `POST /paises` - Crear país
- `PUT /paises/{id}` - Actualizar país
- `PATCH /paises/{id}` - Actualizar país parcial
- `DELETE /paises/{id}` - Eliminar país

---

**Documentación Swagger Interactiva:**  
http://localhost:8080/swagger-ui.html

**Última actualización:** Noviembre 2025
