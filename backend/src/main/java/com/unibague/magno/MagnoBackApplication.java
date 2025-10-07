package com.unibague.magno;

import com.unibague.magno.application.dto.request.*;
import com.unibague.magno.application.dto.request.integra.IntegraUserRequest;
import com.unibague.magno.domain.model.enums.AcademicProgramType;
import com.unibague.magno.domain.model.enums.JSONIntegraType;
import com.unibague.magno.domain.model.enums.LineOfResearch;
import com.unibague.magno.domain.model.enums.SeedbedRole;
import com.unibague.magno.domain.model.integra.IntegraAcademicProgram;
import com.unibague.magno.infrastructure.input.rest.*;
import com.unibague.magno.infrastructure.output.jpa.adapter.integra.IntegraUserClient;
import com.unibague.magno.infrastructure.output.jpa.entity.*;
import com.unibague.magno.infrastructure.output.jpa.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@RequiredArgsConstructor
public class MagnoBackApplication implements CommandLineRunner {

    private final IAcademicPeriodRepository academicPeriodRepository;
    private final IInvestigationGroupRepository investigationGroupRepository;
    private final IResearchSeedbedRepository researchSeedbedRepository;
    private final IRoleRepository roleRepository;
    private final IUserRepository userRepository;
    private final IAcademicProgramRepository academicProgramRepository;
    private final ResearchSeedbedStudentProfileRestController researchSeedbedStudentProfileRestController;
    private final ResearchSeedbedProfileRestController researchSeedbedProfileRestController;
    private final InvestigationGroupProfileRestController investigationGroupProfileRestController;
    private final IntegraUserClient integraUserClient;
    private final StudentProfileRestController studentProfileRestController;
    private final FunctionaryProfileRestController functionaryProfileRestController;
    private final UserRestController userRestController;

    public static void main(String[] args) {
        SpringApplication.run(MagnoBackApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /**
        createAcademicPeriods();
        createInvestigationGroups();
        createResearchSeedbeds();
        createAllUsers();
        createRoles();
        createAllAcademicPrograms();
        createAllProfiles();
        createInvestigationGroupProfiles();
        createResearchSeedbedProfiles();
        inserStudentsIntoResearchSeedbeds();*/
    }

    private void inserStudentsIntoResearchSeedbeds() {
        int seedbedsPeriod1 = 5;
        int seedbedsPeriod2 = 5;

        int totalProfiles = 160;

        for (int studentProfileId = 1; studentProfileId <= totalProfiles; studentProfileId += 2) {
            int researchSeedbedId = ((studentProfileId / 2) % seedbedsPeriod1) + 1;

            ResearchSeedbedStudentProfileRequest req = ResearchSeedbedStudentProfileRequest.builder()
                    .researchSeedbedProfileId((long) researchSeedbedId)
                    .studentProfileId((long) studentProfileId)
                    .wasActive(true)
                    .isLeader(false)
                    .build();

            researchSeedbedStudentProfileRestController.createResearchSeedbedStudentProfile(req);
            System.out.println("Periodo 1 -> " + req);
        }

        for (int studentProfileId = 2; studentProfileId <= totalProfiles; studentProfileId += 2) {
            int researchSeedbedId = ((studentProfileId / 2) % seedbedsPeriod2) + 6;

            ResearchSeedbedStudentProfileRequest req = ResearchSeedbedStudentProfileRequest.builder()
                    .researchSeedbedProfileId((long) researchSeedbedId)
                    .studentProfileId((long) studentProfileId)
                    .wasActive(true)
                    .isLeader(false)
                    .build();

            researchSeedbedStudentProfileRestController.createResearchSeedbedStudentProfile(req);
        }
    }


    private void createResearchSeedbedProfiles() {
        ResearchSeedbedProfileRequest rs1 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(1L)
                .researchSeedbedId(1L)
                .academicPeriodId(1L)
                .coordinatorId(1L)
                .tutorId(3L)
                .wasActive(true)
                .build();

        ResearchSeedbedProfileRequest rs2 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(1L)
                .researchSeedbedId(2L)
                .academicPeriodId(1L)
                .coordinatorId(5L)
                .wasActive(true)
                .build();

        ResearchSeedbedProfileRequest rs3 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(1L)
                .researchSeedbedId(3L)
                .academicPeriodId(1L)
                .coordinatorId(7L)
                .tutorId(9L)
                .wasActive(true)
                .build();

        ResearchSeedbedProfileRequest rs4 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(2L)
                .researchSeedbedId(4L)
                .academicPeriodId(1L)
                .coordinatorId(11L)
                .tutorId(13L)
                .wasActive(true)
                .build();

        ResearchSeedbedProfileRequest rs5 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(2L)
                .researchSeedbedId(5L)
                .academicPeriodId(1L)
                .coordinatorId(15L)
                .tutorId(17L)
                .wasActive(false)
                .build();

        ResearchSeedbedProfileRequest rs6 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(3L)
                .researchSeedbedId(1L)
                .academicPeriodId(2L)
                .coordinatorId(2L)
                .tutorId(4L)
                .wasActive(false)
                .build();

        ResearchSeedbedProfileRequest rs7 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(3L)
                .researchSeedbedId(2L)
                .academicPeriodId(2L)
                .coordinatorId(6L)
                .wasActive(false)
                .build();

        ResearchSeedbedProfileRequest rs8 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(3L)
                .researchSeedbedId(3L)
                .academicPeriodId(2L)
                .coordinatorId(8L)
                .tutorId(10L)
                .wasActive(false)
                .build();

        ResearchSeedbedProfileRequest rs9 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(4L)
                .researchSeedbedId(4L)
                .academicPeriodId(2L)
                .coordinatorId(12L)
                .tutorId(14L)
                .wasActive(false)
                .build();

        ResearchSeedbedProfileRequest rs10 = ResearchSeedbedProfileRequest.builder()
                .investigationGroupProfileId(4L)
                .researchSeedbedId(5L)
                .academicPeriodId(2L)
                .coordinatorId(16L)
                .tutorId(18L)
                .wasActive(false)
                .build();

        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs1);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs2);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs3);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs4);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs5);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs6);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs7);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs8);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs9);
        researchSeedbedProfileRestController.createResearchSeedbedProfile(rs10);
    }

    private void createInvestigationGroupProfiles() {
        InvestigationGroupProfileRequest ig1 = InvestigationGroupProfileRequest.builder()
                .investigationGroupId(1L)
                .academicPeriodId(1L)
                .coordinatorId(1L)
                .build();

        InvestigationGroupProfileRequest ig2 = InvestigationGroupProfileRequest.builder()
                .investigationGroupId(2L)
                .academicPeriodId(1L)
                .coordinatorId(3L)
                .build();

        InvestigationGroupProfileRequest ig3 = InvestigationGroupProfileRequest.builder()
                .investigationGroupId(1L)
                .academicPeriodId(2L)
                .coordinatorId(2L)
                .build();

        InvestigationGroupProfileRequest ig4 = InvestigationGroupProfileRequest.builder()
                .investigationGroupId(2L)
                .academicPeriodId(2L)
                .coordinatorId(4L)
                .build();

        investigationGroupProfileRestController.createInvestigationGroupProfile(ig1);
        investigationGroupProfileRestController.createInvestigationGroupProfile(ig2);
        investigationGroupProfileRestController.createInvestigationGroupProfile(ig3);
        investigationGroupProfileRestController.createInvestigationGroupProfile(ig4);
    }

    private void createAllAcademicPrograms() {
        List<IntegraAcademicProgram> aps = integraUserClient.getAllAcademicPrograms();
        aps.forEach(ap -> {
            AcademicProgramEntity entity = AcademicProgramEntity.builder()
                    .name(ap.getProgramName())
                    .programCode(ap.getProgramCode())
                    .type(AcademicProgramType.PREGRADO)
                    .build();
            academicProgramRepository.save(entity);
        });
        academicProgramRepository.save(AcademicProgramEntity.builder()
                .name("CURSOS LIBRES POSGRADOS -Ser")
                .programCode("92")
                .type(AcademicProgramType.POSGRADO)
                .build());

        academicProgramRepository.save(AcademicProgramEntity.builder()
                .name("MAESTRIA EN ANALITICA DE DATOS PARA LA TOMA DE DECISIONES")
                .programCode("1G")
                .type(AcademicProgramType.POSGRADO)
                .build());
    }

    private void createAllProfiles() {
        userRepository.findAll().forEach(userEntity -> {
            Long userId = userEntity.getId();
            Long ap1 = 1L;
            Long ap2 = 2L;
            Set<Long> set = Set.of(1L);
            if (userEntity.getEmail().endsWith("@estudiantesunibague.edu.co")) {
                StudentProfileRequest sp = StudentProfileRequest.builder().userId(userId).academicPeriodId(ap1).roleId(1L).build();
                studentProfileRestController.createStudentProfile(sp);
                sp.setAcademicPeriodId(ap2);
                studentProfileRestController.createStudentProfile(sp);
            } else {
                FunctionaryProfileRequest fp = FunctionaryProfileRequest.builder().userId(userId).academicPeriodId(ap1).roleId(1L).build();
                functionaryProfileRestController.createFunctionaryProfile(fp);
                fp.setAcademicPeriodId(ap2);
                functionaryProfileRestController.createFunctionaryProfile(fp);
            }
        });
    }

    private void createRoles() {
        RoleEntity estudianteRole = RoleEntity.builder()
                .name(SeedbedRole.ESTUDIANTE)
                .description("Rol para estudiantes participantes en los semilleros.")
                .build();

        RoleEntity estudianteLiderRole = RoleEntity.builder()
                .name(SeedbedRole.ESTUDIANTE_LIDER)
                .description("Rol para estudiantes líderes que coordinan actividades del semillero.")
                .build();

        RoleEntity tutorSemilleroRole = RoleEntity.builder()
                .name(SeedbedRole.TUTOR_DE_SEMILLERO)
                .description("Rol para tutores encargados de guiar a los semilleros.")
                .build();

        RoleEntity coordinadorSemilleroRole = RoleEntity.builder()
                .name(SeedbedRole.COORDINADOR_DE_SEMILLERO)
                .description("Rol para coordinadores responsables de la gestión de un semillero.")
                .build();

        RoleEntity coordinadorGrupoRole = RoleEntity.builder()
                .name(SeedbedRole.COORDINADOR_DE_GRUPO_DE_INVESTIGACION)
                .description("Rol para coordinadores de grupos de investigación.")
                .build();

        RoleEntity diriRole = RoleEntity.builder()
                .name(SeedbedRole.DIRI)
                .description("Rol con todos los permisos dentro del sistema.")
                .build();

        roleRepository.save(estudianteRole);
        roleRepository.save(estudianteLiderRole);
        roleRepository.save(tutorSemilleroRole);
        roleRepository.save(coordinadorSemilleroRole);
        roleRepository.save(coordinadorGrupoRole);
        roleRepository.save(diriRole);
    }

    private void createAllUsers() {
        List<IntegraUserRequest> functionaryUsers = createFunctionaryUsers();
        List<IntegraUserRequest> studentUsers = createStudentUsers();

        functionaryUsers.forEach(userRestController::createUser);
        studentUsers.forEach(userRestController::createUser);
    }

    private List<IntegraUserRequest> createFunctionaryUsers() {
        List<String> identifications = Arrays.asList(
                "93399818",
                "38360085",
                "79265542",
                "14235500",
                "93405815",
                "93390998",
                "1110555722",
                "1005690138",
                "1122782835"
        );

        return identifications.stream()
                .map(id -> IntegraUserRequest.builder()
                        .identification(id)
                        .type(JSONIntegraType.FUNCIONARIO)
                        .build())
                .toList();
    }

    private List<IntegraUserRequest> createStudentUsers() {
        List<String> identifications = Arrays.asList(
                "1234640514",
                "1007372117",
                "1006117097",
                "1234645494",
                "1005754917",
                "1026299537",
                "1193236265",
                "1006121691",
                "1105692072",
                "1000776897",
                "1006120183",
                "1002696243",
                "1006100885",
                "1005714894",
                "1110550847",
                "1006120184",
                "1005716319",
                "1005691869",
                "1006128855",
                "1005839535",
                "1007831937",
                "1037618210",
                "1034299515",
                "1104546101",
                "1087992073",
                "1104940918",
                "1006120647",
                "1104939355",
                "1104938562",
                "1006005636",
                "1083864926",
                "1105460339",
                "1006126187",
                "1104940818",
                "1105676934",
                "1000379865",
                "1005716752",
                "1007310411",
                "1007811930",
                "1104547269",
                "1005753020",
                "1125085617",
                "1193402227",
                "1000255055",
                "1110591868",
                "1234646234",
                "5821407",
                "1193549616",
                "1032679106",
                "1111042525",
                "1004799527",
                "1117489542",
                "1110599079",
                "1043434885",
                "1126595179",
                "1111123613",
                "1039684337",
                "1106227080",
                "1031801377",
                "1004961092",
                "1005703012",
                "1006507265",
                "1104545811",
                "1098071241",
                "1005691071",
                "1137624101",
                "1110447434",
                "1110469199",
                "1106226931",
                "1110449397",
                "1110595686",
                "1192793914",
                "1025322176",
                "1075219660",
                "1076500686",
                "1104544138",
                "1005910658",
                "1104544040",
                "1104544083",
                "1007524087"
        );

        return identifications.stream()
                .map(id -> IntegraUserRequest.builder()
                        .identification(id)
                        .type(JSONIntegraType.ESTUDIANTE)
                        .build())
                .toList();
    }

    private void createResearchSeedbeds() {
        ResearchSeedbedEntity r1 = ResearchSeedbedEntity.builder()
                .name("LUN")
                .mission("Desarrollar técnicas de procesamiento de imágenes, reconocimiento de patrones y análisis computacional.")
                .vision("Constituirse en un semillero reconocido a nivel internacional en el área de visión por computador.")
                .researchProposalDescription("Desarrollo y evaluación de diferentes técnicas aplicadas al procesamiento de imágenes y señales.")
                .creationDate(java.time.LocalDate.of(2020, 1, 15))
                .lineOfResearch(LineOfResearch.BIOTECNOLIGIA_INDUSTRIAL)
                .build();

        ResearchSeedbedEntity r2 = ResearchSeedbedEntity.builder()
                .name("Diseño")
                .mission("Impulsar la creatividad aplicada al diseño gráfico, industrial y digital para resolver problemas sociales y productivos.")
                .vision("Ser un referente en innovación de diseño con impacto en el desarrollo sostenible.")
                .researchProposalDescription("Exploración de metodologías de diseño centradas en el usuario y prototipado ágil.")
                .creationDate(java.time.LocalDate.of(2019, 3, 10))
                .lineOfResearch(LineOfResearch.CIENCIAS_DE_LA_EDUCACION)
                .build();

        ResearchSeedbedEntity r3 = ResearchSeedbedEntity.builder()
                .name("Internet de las cosas")
                .mission("Investigar y desarrollar soluciones IoT para la integración de sistemas inteligentes en distintos sectores.")
                .vision("Convertirse en un líder académico en proyectos IoT con aplicaciones en ciudades inteligentes y salud.")
                .researchProposalDescription("Diseño de arquitecturas IoT y desarrollo de prototipos de dispositivos conectados.")
                .creationDate(java.time.LocalDate.of(2021, 5, 22))
                .lineOfResearch(LineOfResearch.CIENCIAS_FISICAS)
                .build();

        ResearchSeedbedEntity r4 = ResearchSeedbedEntity.builder()
                .name("EnMiBus")
                .mission("Analizar y proponer soluciones tecnológicas para mejorar el transporte público y la movilidad urbana.")
                .vision("Ser un semillero referente en movilidad sostenible y transporte inteligente en Latinoamérica.")
                .researchProposalDescription("Implementación de sistemas de monitoreo y análisis de rutas de transporte público.")
                .creationDate(java.time.LocalDate.of(2022, 8, 5))
                .lineOfResearch(LineOfResearch.ECONOMIA_Y_NEGOCIOS)
                .build();

        ResearchSeedbedEntity r5 = ResearchSeedbedEntity.builder()
                .name("Diseño sistémico")
                .mission("Promover el pensamiento sistémico para abordar problemas complejos mediante enfoques interdisciplinarios.")
                .vision("Consolidarse como un espacio de referencia en la aplicación del diseño sistémico en proyectos sociales y tecnológicos.")
                .researchProposalDescription("Aplicación de marcos de diseño sistémico en contextos educativos, organizacionales y ambientales.")
                .creationDate(java.time.LocalDate.of(2018, 11, 30))
                .lineOfResearch(LineOfResearch.CIENCIAS_DE_LA_EDUCACION)
                .build();

        researchSeedbedRepository.save(r1);
        researchSeedbedRepository.save(r2);
        researchSeedbedRepository.save(r3);
        researchSeedbedRepository.save(r4);
        researchSeedbedRepository.save(r5);
    }

    private void createInvestigationGroups() {
        InvestigationGroupEntity ig1 = InvestigationGroupEntity.builder()
                .name("D+TEC")
                .linesOfResearch(Set.of(
                        LineOfResearch.BIOTECNOLIGIA_INDUSTRIAL,
                        LineOfResearch.CIENCIAS_DE_LA_EDUCACION))
                .build();

        InvestigationGroupEntity ig2 = InvestigationGroupEntity.builder()
                .name("MYSCO")
                .linesOfResearch(Set.of(
                        LineOfResearch.CIENCIAS_DE_LA_EDUCACION,
                        LineOfResearch.ECONOMIA_Y_NEGOCIOS))
                .build();
        investigationGroupRepository.save(ig1);
        investigationGroupRepository.save(ig2);
    }

    private void createAcademicPeriods() {
        AcademicPeriodEntity ap1 = AcademicPeriodEntity.builder()
                .name("2025-A")
                .startDate(java.time.LocalDate.of(2025, 1, 1))
                .endDate(java.time.LocalDate.of(2025, 6, 30))
                .isCurrent(false)
                .build();
        AcademicPeriodEntity ap2 = AcademicPeriodEntity.builder()
                .name("2025-B")
                .startDate(java.time.LocalDate.of(2025, 7, 1))
                .endDate(java.time.LocalDate.of(2025, 12, 31))
                .isCurrent(true)
                .build();

        academicPeriodRepository.save(ap1);
        academicPeriodRepository.save(ap2);
    }
}
