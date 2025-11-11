# Script para construir y probar Docker localmente (Windows PowerShell)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  Build y Test de Docker - Productos   " -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Variables
$IMAGE_NAME = "productos-backend"
$TAG = "latest"
$CONTAINER_NAME = "productos-test"

# Función para verificar si Docker está corriendo
function Test-Docker {
    try {
        docker info | Out-Null
        return $true
    } catch {
        Write-Host "❌ Error: Docker no está corriendo" -ForegroundColor Red
        Write-Host "Por favor, inicia Docker Desktop" -ForegroundColor Yellow
        return $false
    }
}

# Verificar Docker
if (-not (Test-Docker)) {
    exit 1
}

Write-Host "✅ Docker está corriendo" -ForegroundColor Green
Write-Host ""

# Menú
Write-Host "Selecciona una opción:" -ForegroundColor Yellow
Write-Host "1. Build de la imagen Docker"
Write-Host "2. Run del contenedor (requiere variables de entorno)"
Write-Host "3. Build + Run"
Write-Host "4. Stop y remove del contenedor"
Write-Host "5. Ver logs del contenedor"
Write-Host "6. Verificar salud del contenedor"
Write-Host ""

$option = Read-Host "Opción (1-6)"

switch ($option) {
    "1" {
        Write-Host ""
        Write-Host "📦 Construyendo imagen Docker..." -ForegroundColor Cyan
        docker build -t ${IMAGE_NAME}:${TAG} .
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Imagen construida exitosamente" -ForegroundColor Green
            Write-Host ""
            Write-Host "Detalles de la imagen:" -ForegroundColor Yellow
            docker images ${IMAGE_NAME}:${TAG}
        } else {
            Write-Host "❌ Error al construir la imagen" -ForegroundColor Red
        }
    }
    
    "2" {
        Write-Host ""
        Write-Host "🚀 Iniciando contenedor..." -ForegroundColor Cyan
        Write-Host ""
        Write-Host "⚠️  Asegúrate de configurar las variables de entorno:" -ForegroundColor Yellow
        Write-Host "   DB_URL, DB_USERNAME, DB_PASSWORD" -ForegroundColor Yellow
        Write-Host ""
        
        # Solicitar variables de entorno
        $DB_URL = Read-Host "DB_URL (jdbc:postgresql://...)"
        $DB_USERNAME = Read-Host "DB_USERNAME"
        $DB_PASSWORD = Read-Host "DB_PASSWORD" -AsSecureString
        $DB_PASSWORD_PLAIN = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($DB_PASSWORD))
        
        docker run -d `
            --name $CONTAINER_NAME `
            -p 8080:8080 `
            -e DB_URL="$DB_URL" `
            -e DB_USERNAME="$DB_USERNAME" `
            -e DB_PASSWORD="$DB_PASSWORD_PLAIN" `
            -e SPRING_PROFILES_ACTIVE="prod" `
            ${IMAGE_NAME}:${TAG}
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Contenedor iniciado exitosamente" -ForegroundColor Green
            Write-Host ""
            Write-Host "📍 Endpoints disponibles:" -ForegroundColor Cyan
            Write-Host "   http://localhost:8080/api/v1/productos"
            Write-Host "   http://localhost:8080/swagger-ui.html"
            Write-Host "   http://localhost:8080/actuator/health"
            Write-Host ""
            Write-Host "Para ver logs: docker logs -f $CONTAINER_NAME" -ForegroundColor Yellow
        } else {
            Write-Host "❌ Error al iniciar el contenedor" -ForegroundColor Red
        }
    }
    
    "3" {
        Write-Host ""
        Write-Host "📦 Build + Run..." -ForegroundColor Cyan
        
        # Build
        Write-Host ""
        Write-Host "Paso 1: Construyendo imagen..." -ForegroundColor Yellow
        docker build -t ${IMAGE_NAME}:${TAG} .
        
        if ($LASTEXITCODE -ne 0) {
            Write-Host "❌ Error en el build" -ForegroundColor Red
            exit 1
        }
        
        Write-Host "✅ Build exitoso" -ForegroundColor Green
        
        # Run
        Write-Host ""
        Write-Host "Paso 2: Iniciando contenedor..." -ForegroundColor Yellow
        
        $DB_URL = Read-Host "DB_URL"
        $DB_USERNAME = Read-Host "DB_USERNAME"
        $DB_PASSWORD = Read-Host "DB_PASSWORD" -AsSecureString
        $DB_PASSWORD_PLAIN = [Runtime.InteropServices.Marshal]::PtrToStringAuto([Runtime.InteropServices.Marshal]::SecureStringToBSTR($DB_PASSWORD))
        
        docker run -d `
            --name $CONTAINER_NAME `
            -p 8080:8080 `
            -e DB_URL="$DB_URL" `
            -e DB_USERNAME="$DB_USERNAME" `
            -e DB_PASSWORD="$DB_PASSWORD_PLAIN" `
            -e SPRING_PROFILES_ACTIVE="prod" `
            ${IMAGE_NAME}:${TAG}
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Todo listo!" -ForegroundColor Green
        }
    }
    
    "4" {
        Write-Host ""
        Write-Host "🛑 Deteniendo y removiendo contenedor..." -ForegroundColor Cyan
        docker stop $CONTAINER_NAME
        docker rm $CONTAINER_NAME
        
        if ($LASTEXITCODE -eq 0) {
            Write-Host "✅ Contenedor removido" -ForegroundColor Green
        }
    }
    
    "5" {
        Write-Host ""
        Write-Host "📋 Logs del contenedor (Ctrl+C para salir):" -ForegroundColor Cyan
        docker logs -f $CONTAINER_NAME
    }
    
    "6" {
        Write-Host ""
        Write-Host "🏥 Verificando salud..." -ForegroundColor Cyan
        Write-Host ""
        
        try {
            $response = Invoke-WebRequest -Uri "http://localhost:8080/actuator/health" -UseBasicParsing
            $health = $response.Content | ConvertFrom-Json
            
            if ($health.status -eq "UP") {
                Write-Host "✅ La aplicación está saludable" -ForegroundColor Green
                Write-Host ""
                Write-Host "Detalles:" -ForegroundColor Yellow
                $health | ConvertTo-Json -Depth 10
            } else {
                Write-Host "⚠️  Estado: $($health.status)" -ForegroundColor Yellow
            }
        } catch {
            Write-Host "❌ No se pudo conectar al health endpoint" -ForegroundColor Red
            Write-Host "¿El contenedor está corriendo?" -ForegroundColor Yellow
        }
    }
    
    default {
        Write-Host "Opción inválida" -ForegroundColor Red
    }
}

Write-Host ""
