package com.example.FARMACIA.Controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.FARMACIA.Model.Usuarios;
import com.example.FARMACIA.Repository.UsuarioRepository;

 @RestController
@RequestMapping("/login")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class LoginController{
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData) {
        String email = loginData.get("email");
        String password = loginData.get("password");
        Usuarios usuario = usuarioRepository.findByEmail(email);
        if (usuario != null && usuario.getPassword().equals(password)) {
            usuario.setPassword(null); // No enviar la contraseña
            return ResponseEntity.ok(usuario);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
    }
    
}
