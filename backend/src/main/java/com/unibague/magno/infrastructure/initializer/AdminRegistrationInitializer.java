package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(3)
public class AdminRegistrationInitializer implements CommandLineRunner {

    private final IAcademicPeriodServicePort academicPeriodServicePort;
    private final IUserServicePort userServicePort;
    private final IRoleServicePort roleServicePort;
    private final IDependencyServicePort dependencyServicePort;
    private final IFunctionaryProfileServicePort functionaryProfileServicePort;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n\n\n---------------------------------------\n\n\n");
        log.info("¿Desea registrar un usuario administrador? (S/N): ");

        Scanner scanner = new Scanner(System.in);
        try {
            String respuesta = scanner.nextLine().trim().toUpperCase();

            if (respuesta.equals("S")) {
                log.info("Escriba el correo del usuario administrador a registrar: ");
                String adminEmail = scanner.nextLine().trim();
                createAdmin(adminEmail);
                log.info("Usuario administrador registrado exitosamente con email: {}", adminEmail);
            } else {
                log.info("No se registrará un usuario administrador.");
            }
        }
        catch (Exception e) {
            log.error("Error al leer la entrada del usuario: {}", e.getMessage());
        }

    }

    private void createAdmin(String adminEmail) {

        User adminUser;
        try {
            adminUser = userServicePort.findByEmail(adminEmail);
        }
        catch (UserNotFoundException e) {
            log.info("No se encontró a un usuario con ese correo, intentelo de nuevo.");
            return;
        }


        List<AcademicPeriod> academicPeriods = academicPeriodServicePort.findAll();
        AcademicPeriod ap;
        if (academicPeriods.isEmpty()) {
            ap = academicPeriodServicePort.save(
                    new AcademicPeriod(
                            null, "Periodo para registrar admins",LocalDate.now(),
                            LocalDate.now().plusDays(1), true));

        }
        else {
            ap = academicPeriods.getFirst();
        }

        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);

        Dependency dependency = dependencyServicePort.findByName("DIRECCION DE INVESTIGACIONES");
        functionaryProfileServicePort.save(new FunctionaryProfile(
                null, adminUser.getId(), ap.getId(), dependency.getId(), diriRole.getId()
                )
        );

    }
}
