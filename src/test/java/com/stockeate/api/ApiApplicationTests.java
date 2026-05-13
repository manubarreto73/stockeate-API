package com.stockeate.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class ApiApplicationTests {

	@Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void generarPasswords() {
        System.out.println("SuperAdmin123 -> " + passwordEncoder.encode("SuperAdmin123"));
        System.out.println("Admin123      -> " + passwordEncoder.encode("Admin123"));
        System.out.println("Gerente123    -> " + passwordEncoder.encode("Gerente123"));
        System.out.println("Empleado123   -> " + passwordEncoder.encode("Empleado123"));
    }

}
