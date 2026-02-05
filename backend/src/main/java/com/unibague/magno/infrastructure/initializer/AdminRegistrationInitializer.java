package com.unibague.magno.infrastructure.initializer;

import com.unibague.magno.domain.api.*;
import com.unibague.magno.domain.exception.user.UserNotFoundException;
import com.unibague.magno.domain.model.*;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.util.SystemConstants;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.*;

/**
 * Initializer that provides an interactive prompt at application startup to register
 * an administrator (DIRI) user.
 * <p>
 * This component allows the system administrator to assign the DIRI role to an existing
 * user during the application startup process. It requires user interaction via the console
 * to provide the administrator's email address.
 * </p>
 * <p>
 * <strong>Timeout:</strong> Each prompt has a 30-second timeout. If no input is received
 * within this period, the registration process is automatically skipped. This prevents
 * the application from hanging indefinitely when running in background mode or in
 * environments without an interactive console (e.g., production deployments).
 * </p>
 * <p>
 * <strong>Execution order:</strong> {@code @Order(3)} - Runs after {@link UserDataInitializer}
 * to ensure user data is synchronized before attempting to assign admin roles.
 * </p>
 */
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

    private static final int TIMEOUT_SECONDS = 30;

    @Override
    public void run(String... args) throws Exception {
        log.info("\n\n\n---------------------------------------\n\n\n");
        log.info("¿Desea registrar un usuario administrador? (S/N): ");
        log.info("Tiene {} segundos para responder. Si no responde, se omitirá este paso.", TIMEOUT_SECONDS);

        ExecutorService executor = Executors.newSingleThreadExecutor();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            String respuesta = readLineWithTimeout(executor, reader, TIMEOUT_SECONDS);

            if (respuesta == null) {
                log.info("Tiempo de espera agotado. No se registrará un usuario administrador.");
                return;
            }

            respuesta = respuesta.trim().toUpperCase();

            if (respuesta.equals("S")) {
                log.info("Escriba el correo del usuario administrador a registrar: ");
                log.info("Tiene {} segundos para responder.", TIMEOUT_SECONDS);

                String adminEmail = readLineWithTimeout(executor, reader, TIMEOUT_SECONDS);

                if (adminEmail == null || adminEmail.trim().isEmpty()) {
                    log.info("Tiempo de espera agotado o correo vacío. No se registrará un usuario administrador.");
                    return;
                }

                createAdmin(adminEmail.trim());
                log.info("Usuario administrador registrado exitosamente con email: {}", adminEmail.trim());
            } else {
                log.info("No se registrará un usuario administrador.");
            }
        } catch (Exception e) {
            log.error("Error al leer la entrada del usuario: {}", e.getMessage());
        } finally {
            executor.shutdownNow();
        }
    }

    private String readLineWithTimeout(ExecutorService executor, BufferedReader reader, int timeoutSeconds) {
        Future<String> future = executor.submit(() -> {
            try {
                return reader.readLine();
            } catch (Exception e) {
                return null;
            }
        });

        try {
            return future.get(timeoutSeconds, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            future.cancel(true);
            return null;
        } catch (Exception e) {
            return null;
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
                            null, SystemConstants.ADMIN_REGISTRATION_ACADEMIC_PERIOD_NAME, LocalDate.of(1900, 1, 1),
                            LocalDate.of(3000, 12, 31), true, false));

        }
        else {
            ap = academicPeriods.getFirst();
        }

        Role diriRole = roleServicePort.findByName(SeedbedRole.DIRI);

        Dependency dependency = dependencyServicePort.findByName(SystemConstants.DIRI_DEPENDENCY_NAME);
        // Use saveIgnoringPeriodVisibility because DIRI users are created in a special
        // academic period that may not be visible
        functionaryProfileServicePort.saveIgnoringPeriodVisibility(new FunctionaryProfile(
                null, adminUser.getId(), ap.getId(), dependency.getId(), diriRole.getId()
                )
        );

    }
}
