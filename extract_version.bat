@echo off
:: %~dp0 obtiene la ruta absoluta de la carpeta donde está este .bat
set "ROOT_DIR=%~dp0"
set "README_PATH=%ROOT_DIR%README.md"

:: la ruta de salida debe apuntar a la carpeta de recursos de tu código fuente
set "OUTPUT_PATH=%ROOT_DIR%src\core\escutrack\resources\version.txt"

if not exist "%README_PATH%" (
    echo Unknown > "%OUTPUT_PATH%"
    exit /b
)

powershell -Command "$content = Get-Content '%README_PATH%' -Raw; if ($content -match '##\s+\[([0-9]+\.[0-9]+\.[0-9]+)\]') { $Matches[1] | Out-File -FilePath '%OUTPUT_PATH%' -Encoding ascii }"

for /f "tokens=*" %%g in (%OUTPUT_PATH%) do echo %%g> "%OUTPUT_PATH%"