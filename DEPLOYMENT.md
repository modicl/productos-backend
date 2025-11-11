# 🚀 Guía de Despliegue - DigitalOcean App Platform

Guía paso a paso para desplegar el microservicio de Productos en DigitalOcean App Platform.

---

## 📋 Prerrequisitos

✅ Cuenta de DigitalOcean  
✅ Repositorio GitHub con el código (`modicl/productos-backend`)  
✅ Base de datos PostgreSQL configurada (DigitalOcean Managed Database o externa)  
✅ JWT Secret configurado (debe ser el mismo que el microservicio de usuarios)

---

## 🔧 Método 1: Despliegue desde GitHub (Recomendado)

### Paso 1: Crear nueva App en App Platform

1. Inicia sesión en [DigitalOcean](https://cloud.digitalocean.com)
2. Ve a **App Platform** en el menú lateral
3. Haz clic en **"Create App"**

### Paso 2: Conectar repositorio

1. Selecciona **GitHub** como fuente
2. Autoriza a DigitalOcean a acceder a tu GitHub
3. Selecciona el repositorio: `modicl/productos-backend`
4. Selecciona la rama: `main`
5. Marca **"Autodeploy"** para despliegues automáticos en cada push

### Paso 3: Configurar el servicio

App Platform detectará automáticamente el `Dockerfile`. Si no:

1. **Source Directory:** `/`
2. **Type:** Dockerfile
3. **Dockerfile Path:** `Dockerfile`

### Paso 4: Configurar recursos

**Plan recomendado para empezar:**
- **Basic (512 MB RAM | $5/mes)**

Para producción con más tráfico:
- **Professional (1 GB RAM | $12/mes)** o superior

### Paso 5: Variables de entorno

En la sección **Environment Variables**, agrega:

| Variable | Valor | Tipo |
|----------|-------|------|
| `DB_URL` | `jdbc:postgresql://tu-host:25060/productos?sslmode=require` | SECRET |
| `DB_USERNAME` | Tu usuario de PostgreSQL | SECRET |
| `DB_PASSWORD` | Tu contraseña de PostgreSQL | SECRET |
| `JWT_SECRET` | `profesorsaavedraporfavorpongame-un-7-en-el-examenporfavorgracias` | SECRET |
| `JWT_EXPIRATION` | `86400000` | NORMAL |
| `SPRING_PROFILES_ACTIVE` | `prod` | NORMAL |

**Ejemplo de DB_URL con DigitalOcean Managed Database:**
```
jdbc:postgresql://db-postgresql-huertohogar-do-user-12345.db.ondigitalocean.com:25060/productos?sslmode=require
```

### Paso 6: Configurar Health Check

- **HTTP Path:** `/actuator/health`
- **Port:** `8080`
- **Initial Delay:** `60` segundos
- **Period:** `30` segundos
- **Timeout:** `5` segundos

### Paso 7: Revisar y crear

1. Revisa la configuración
2. Dale un nombre a tu app: `productos-backend`
3. Selecciona la región (ej: `New York 3`)
4. Haz clic en **"Create Resources"**

### Paso 8: Esperar el despliegue

El proceso tarda aproximadamente **5-10 minutos**:

1. ⏳ Building (compilando el Dockerfile)
2. ⏳ Deploying (desplegando la imagen)
3. ✅ Running (aplicación lista)

---

## 🔧 Método 2: Despliegue Manual con doctl CLI

### Instalación de doctl

**Windows (PowerShell):**
```powershell
# Descargar e instalar doctl
choco install doctl
```

**Mac:**
```bash
brew install doctl
```

**Linux:**
```bash
cd ~
wget https://github.com/digitalocean/doctl/releases/download/v1.98.1/doctl-1.98.1-linux-amd64.tar.gz
tar xf ~/doctl-1.98.1-linux-amd64.tar.gz
sudo mv ~/doctl /usr/local/bin
```

### Autenticación

```bash
# Generar API token en: https://cloud.digitalocean.com/account/api/tokens
doctl auth init

# Verificar autenticación
doctl account get
```

### Crear App desde archivo YAML

```bash
# Desde la raíz del proyecto
doctl apps create --spec .do/app.yaml
```

### Verificar despliegue

```bash
# Listar apps
doctl apps list

# Ver logs
doctl apps logs <APP_ID> --follow

# Ver detalles
doctl apps get <APP_ID>
```

---

## 🔍 Verificación del Despliegue

### 1. Verificar el Health Check

Una vez desplegada, App Platform te dará una URL como:
```
https://productos-backend-xxxxx.ondigitalocean.app
```

Verifica que la app está corriendo:

```bash
curl https://productos-backend-xxxxx.ondigitalocean.app/actuator/health
```

**Respuesta esperada:**
```json
{
  "status": "UP",
  "components": {
    "db": {
      "status": "UP"
    },
    "ping": {
      "status": "UP"
    }
  }
}
```

### 2. Verificar Swagger UI

Accede a la documentación interactiva:
```
https://productos-backend-xxxxx.ondigitalocean.app/swagger-ui.html
```

### 3. Probar un endpoint público

```bash
curl https://productos-backend-xxxxx.ondigitalocean.app/api/v1/productos
```

### 4. Ver logs en tiempo real

En el dashboard de App Platform:
- Ve a tu app
- Haz clic en la pestaña **"Runtime Logs"**
- Filtra por nivel de log si es necesario

---

## 🌐 Configurar Dominio Personalizado (Opcional)

### Paso 1: En App Platform

1. Ve a tu app en App Platform
2. Haz clic en **"Settings"**
3. En la sección **"Domains"**, haz clic en **"Add Domain"**
4. Ingresa tu dominio: `api.huertohogar.cl`

### Paso 2: Configurar DNS

App Platform te dará un CNAME o A record. En tu proveedor de DNS:

**Ejemplo con CNAME:**
```
Type: CNAME
Name: api
Value: productos-backend-xxxxx.ondigitalocean.app
TTL: 3600
```

### Paso 3: Esperar propagación

La propagación DNS puede tomar hasta 48 horas, pero generalmente es en minutos.

Verifica con:
```bash
nslookup api.huertohogar.cl
```

---

## 🔐 Conectar con Base de Datos Managed

Si usas DigitalOcean Managed Database:

### Paso 1: Crear base de datos

1. Ve a **Databases** en DigitalOcean
2. Crea un nuevo cluster PostgreSQL
3. Selecciona el plan (mínimo: $15/mes)
4. Crea una base de datos llamada `productos`

### Paso 2: Obtener credenciales

En el panel de la base de datos, encontrarás:
- **Host:** `db-postgresql-xxxxx.db.ondigitalocean.com`
- **Port:** `25060`
- **User:** `doadmin`
- **Password:** (generada automáticamente)
- **Database:** `productos`

### Paso 3: Configurar variables de entorno

Construye la URL JDBC:
```
jdbc:postgresql://db-postgresql-xxxxx.db.ondigitalocean.com:25060/productos?sslmode=require
```

Agrégala como variable de entorno en App Platform.

### Paso 4: Agregar a Trusted Sources

En la base de datos:
1. Ve a **Settings** → **Trusted Sources**
2. Agrega tu App: `productos-backend`

---

## 📊 Monitoreo y Logs

### Ver métricas en App Platform

1. Ve a tu app
2. Pestaña **"Insights"**
3. Verás:
   - CPU Usage
   - Memory Usage
   - Request Rate
   - Response Time

### Acceder a logs

```bash
# Via doctl
doctl apps logs <APP_ID> --type run --follow

# O en el dashboard: Pestaña "Runtime Logs"
```

### Configurar alertas

1. Ve a **Monitoring** en DigitalOcean
2. Crea una **Alert Policy**
3. Selecciona métricas:
   - CPU > 80%
   - Memory > 90%
   - Response time > 1s

---

## 🔄 Actualizar la Aplicación

### Despliegue automático (si configuraste Autodeploy)

```bash
# Hacer cambios en el código
git add .
git commit -m "Actualización de productos"
git push origin main
```

App Platform detectará el push y desplegará automáticamente.

### Despliegue manual

En App Platform:
1. Ve a tu app
2. Haz clic en **"Actions"** → **"Force Rebuild and Deploy"**

---

## 🐛 Troubleshooting

### La app no inicia

**Verifica los logs:**
```bash
doctl apps logs <APP_ID> --type build
doctl apps logs <APP_ID> --type run
```

**Errores comunes:**

#### 1. Error de conexión a la base de datos
```
Could not open JDBC Connection
```

**Solución:**
- Verifica que `DB_URL`, `DB_USERNAME`, `DB_PASSWORD` sean correctos
- Asegúrate de que la app está en **Trusted Sources** de la DB
- Verifica que el puerto sea `25060` y `sslmode=require`

#### 2. Error de memoria (OOM)
```
java.lang.OutOfMemoryError: Java heap space
```

**Solución:**
- Aumenta el plan de recursos (de Basic a Professional)
- Ajusta `JAVA_OPTS` en variables de entorno:
  ```
  -Xmx400m -Xms256m
  ```

#### 3. Health check fallando
```
Health check failed
```

**Solución:**
- Verifica que `/actuator/health` responde
- Aumenta el `initial_delay_seconds` a 90 o 120
- Revisa que Spring Boot Actuator esté configurado

### Ver estado de la base de datos

```bash
curl https://tu-app.ondigitalocean.app/actuator/health
```

---

## 💰 Costos Estimados

### Configuración Básica (Desarrollo/Testing)

| Recurso | Plan | Costo Mensual |
|---------|------|---------------|
| App Platform | Basic (512 MB) | $5 |
| Database | DO Managed PostgreSQL (1 GB) | $15 |
| **Total** | | **$20/mes** |

### Configuración Producción

| Recurso | Plan | Costo Mensual |
|---------|------|---------------|
| App Platform | Professional (1 GB) | $12 |
| Database | DO Managed PostgreSQL (2 GB) | $25 |
| Dominio | .cl (opcional) | ~$10/año |
| **Total** | | **$37/mes** |

---

## 📝 Checklist de Despliegue

Antes de desplegar, verifica:

- [ ] Dockerfile creado y probado localmente
- [ ] `.dockerignore` configurado
- [ ] Variables de entorno definidas
- [ ] Base de datos PostgreSQL configurada
- [ ] JWT Secret coincide con microservicio de usuarios
- [ ] Spring Boot Actuator habilitado
- [ ] Código pusheado a GitHub
- [ ] Health check endpoint funcionando
- [ ] Puerto 8080 expuesto
- [ ] CORS configurado (si es necesario)

---

## 🔗 Enlaces Útiles

- [DigitalOcean App Platform Docs](https://docs.digitalocean.com/products/app-platform/)
- [doctl CLI Reference](https://docs.digitalocean.com/reference/doctl/)
- [App Platform Pricing](https://www.digitalocean.com/pricing/app-platform)
- [Managed Databases](https://docs.digitalocean.com/products/databases/)

---

## 📞 Soporte

**Problemas con el código:** [GitHub Issues](https://github.com/modicl/productos-backend/issues)  
**Soporte DigitalOcean:** [Support Tickets](https://cloud.digitalocean.com/support)

---

**Última actualización:** Noviembre 2025
