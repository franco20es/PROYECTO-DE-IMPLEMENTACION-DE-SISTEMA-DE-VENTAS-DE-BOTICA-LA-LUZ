document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("formLogin");
    const mensaje = document.getElementById("mensaje");
  
    form.addEventListener("submit", async (e) => {
      e.preventDefault();
  
      const email = document.getElementById("email").value.trim();
      const password = document.getElementById("password").value.trim();
  
      if (!email || !password) {
        mensaje.textContent = "Por favor complete todos los campos.";
        return;
      }
  
      try {
     
        
  
        // 🔹 Conexión real con backend Spring Boot
        
        const response = await fetch("http://localhost:8082/login", {
          method: "POST",
          headers: { "Content-Type": "application/json" },
          body: JSON.stringify({ email, password })
        });
  
        if (response.ok) {
          const data = await response.json();
          localStorage.setItem("usuario", JSON.stringify(data)); // Guarda el usuario logueado
          window.location.href = "../../html/paginas/dashboard.html";
        } else {
          mensaje.textContent = "Credenciales incorrectas. Intente nuevamente.";
        }
  
      } catch (error) {
        console.error("Error en el login:", error);
        mensaje.textContent = "Error al conectar con el servidor.";
      }
    });
  });
  