
  const tabla = document.getElementById("tablaCategorias");
  const form = document.getElementById("formCategoria");
  const mensaje = document.getElementById("mensaje");
  const btnMostrarForm = document.getElementById("btnMostrarForm");
  const btnCancelar = document.getElementById("btnCancelar");

  // Mostrar y ocultar formulario
  btnMostrarForm.onclick = () => {
    form.style.display = "block";
  };
  btnCancelar.onclick = () => {
    form.style.display = "none";
    mensaje.textContent = "";
  };

  // Cargar categorías desde el backend
  async function cargarCategorias() {
    tabla.innerHTML = "";
    try {
      const response = await fetch("http://localhost:8082/categorias");
      const categorias = await response.json();
      categorias.forEach(c => {
        const fila = `<tr>
          <td>${c.id}</td>
          <td>${c.nombre}</td>
        </tr>`;
        tabla.innerHTML += fila;
      });
    } catch {
      tabla.innerHTML = "<tr><td colspan='2'>No se pudieron cargar las categorías</td></tr>";
    }
  }

  form.addEventListener("submit", async (e) => {
    e.preventDefault();
    const nombre = document.getElementById("nombre").value.trim();
    try {
      const response = await fetch("http://localhost:8082/categorias", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ nombre })
      });
      if (response.ok) {
        mensaje.textContent = "Categoría registrada correctamente.";
        mensaje.style.color = "green";
        form.reset();
        form.style.display = "none";
        cargarCategorias();
      } else {
        mensaje.textContent = "Error al registrar categoría.";
        mensaje.style.color = "red";
      }
    } catch {
      mensaje.textContent = "Error al conectar con el servidor.";
      mensaje.style.color = "red";
    }
  });

  cargarCategorias();
