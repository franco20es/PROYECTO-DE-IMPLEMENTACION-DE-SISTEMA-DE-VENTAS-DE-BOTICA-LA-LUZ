// Función para inicializar el modo oscuro
function inicializarModoOscuro() {
    const toggleBtn = document.getElementById('toggleDarkMode');
    
    // Cargar preferencia guardada
    const temaGuardado = localStorage.getItem('tema') || 'light';
    document.documentElement.setAttribute('data-theme', temaGuardado);
    
    // Actualizar texto del botón
    actualizarTextoBoton(temaGuardado);
    
    // Event listener para el toggle
    if (toggleBtn) {
        toggleBtn.addEventListener('click', () => {
            const temaActual = document.documentElement.getAttribute('data-theme');
            const nuevoTema = temaActual === 'dark' ? 'light' : 'dark';
            
            // Cambiar tema
            document.documentElement.setAttribute('data-theme', nuevoTema);
            
            // Guardar preferencia
            localStorage.setItem('tema', nuevoTema);
            
            // Actualizar texto del botón
            actualizarTextoBoton(nuevoTema);
        });
    }
}

function actualizarTextoBoton(tema) {
    const toggleBtn = document.getElementById('toggleDarkMode');
    if (toggleBtn) {
        if (tema === 'dark') {
            toggleBtn.textContent = '☀️ Modo Claro';
        } else {
            toggleBtn.textContent = '🌙 Modo Oscuro';
        }
    }
}

// Inicializar cuando cargue la página
document.addEventListener('DOMContentLoaded', inicializarModoOscuro);