/**
 * Centralizes all component imports for use in the application's router.
 * Uses Vue's dynamic import function for code splitting and lazy loading.
 */

/**
 * Object containing functions that return dynamic imports for Vue components.
 * This pattern allows for efficient lazy loading of routes.
 * @type {Readonly<Object<string, function(): Promise<any>>>}
 */
export const components = {
  /** Main layout wrapper with sidebar and navbar */
  LAYOUT_DEFAULT: () => import('@/layouts/default.vue'),
  /** Home dashboard view */
  HOME_INDEX: () => import('@/views/home/index.vue'),
  /** Principal view for reports management */
  REPORTS_INDEX: () => import('@/views/reports/index.vue'),
  /** Entry point for academic periods management */
  ACADEMIC_PERIODS_INDEX: () => import('@/views/academic-periods/index.vue'),
  /** View for listing existing academic periods */
  PERIODS_LIST: () => import('@/views/academic-periods/listPeriods.vue'),
  /** Form to add a new academic period */
  ADD_PERIOD: () => import('@/views/academic-periods/addPeriod.vue'),
  /** Form to edit an existing academic period */
  EDIT_PERIOD: () => import('@/views/academic-periods/editPeriod.vue'),
  /** Index for investigation group profiles by period */
  INVESTIGATION_GROUPS_PROFILES_INDEX: () => import('@/views/academic-periods/investigation-groups/index.vue'),
  /** List of investigation group profiles associated with a period */
  INVESTIGATION_GROUPS_PROFILES_LIST: () => import('@/views/academic-periods/investigation-groups/listGroupsByPeriod.vue'),
  /** Form to add a group profile to a specific period */
  ADD_GROUP_BY_PERIOD: () => import('@/views/academic-periods/investigation-groups/addGroupByPeriod.vue'),
  /** Form to edit a group profile in a specific period */
  EDIT_GROUP_BY_PERIOD: () => import('@/views/academic-periods/investigation-groups/editGroupByPeriod.vue'),
  /** Index for seedbeds belonging to a specific group */
  SEEDBEDS_BY_GROUP_INDEX: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/index.vue'),
  /** List of seedbeds associated with a group and period */
  SEEDBEDS_BY_GROUP_LIST: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/listSeedbedsByGroup.vue'),
  /** Form to link a seedbed to a group/period */
  ADD_SEEDBED_BY_GROUP: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/addSeedbedByGroup.vue'),
  /** Form to edit a seedbed link in a group/period */
  EDIT_SEEDBED_BY_GROUP: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/editSeedbedByGroup.vue'),
  /** Seedbed "Lounge" view for detailed management */
  LOUNGE_INDEX: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/index.vue'),
  /** View for managing seedbed lounge members (coordinator, tutor, students) */
  LOUNGE_MEMBERS: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/members.vue'),
  /** Specialized form for editing the seedbed coordinator */
  LOUNGE_EDIT_COORDINATOR: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/editCoordinator.vue'),
  /** Specialized form for editing the seedbed tutor */
  LOUNGE_EDIT_TUTOR: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/editTutor.vue'),
  /** Component for bulk uploading students to a seedbed */
  LOUNGE_UPLOAD_STUDENT: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/uploadStudent.vue'),
  /** Form for manually adding a single student to a seedbed */
  LOUNGE_ADD_STUDENT: () => import('@/views/academic-periods/investigation-groups/research-seedbeds/lounge/addStudent.vue'),
  /** Global research seedbeds management index */
  SEEDBEDS_INDEX: () => import('@/views/research-seedbeds/index.vue'),
  /** List of all registered research seedbeds */
  SEEDBEDS_LIST: () => import('@/views/research-seedbeds/listSeedbeds.vue'),
  /** Global user management index */
  USERS_INDEX: () => import('@/views/users/index.vue'),
  /** View for listing system users */
  LIST_USER: () => import('@/views/users/listUser.vue'),
  /** Form to create new users */
  ADD_USER: () => import('@/views/users/addUser.vue'),
  /** Global functionaries management index */
  FUNCTIONARIES_INDEX: () => import('@/views/functionaries/index.vue'),
  /** List of all functionaries (professors/staff) */
  FUNCTIONARIES_LIST: () => import('@/views/functionaries/listFunctionaries.vue'),
  /** Form to add a new functionary */
  ADD_FUNCTIONARY: () => import('@/views/functionaries/addFunctionary.vue'),
  /** Basic students management index */
  STUDENTS_INDEX: () => import('@/views/students/index.vue'),
  /** External allies/partners management index */
  EXTERNALS_INDEX: () => import('@/views/externals/index.vue'),
  /** List of external allies */
  EXTERNALS_LIST: () => import('@/views/externals/listExternals.vue'),
  /** List of students in the system */
  STUDENTS_LIST: () => import('@/views/students/listStudents.vue'),
  /** Detailed view for a specific student */
  DETAIL_STUDENT: () => import('@/views/students/detailStudent.vue'),
  /** Blank layout for pages without navigation (e.g. Login) */
  LAYOUT_BLANK: () => import('@/layouts/blank.vue'),
  /** Login view component */
  LOGIN_INDEX: () => import('@/views/login/index.vue'),
  /** List of investigation groups */
  GROUPS_LIST: () => import('@/views/investigation-groups/listGroups.vue'),
  /** Roles and permissions management index */
  ROLES_INDEX: () => import('@/views/roles/index.vue'),
  /** View for listing and managing roles */
  ROLES_LIST: () => import('@/views/roles/listRoles.vue'),
  /** Functionary profiles management index */
  FUNCTIONARY_PROFILES_INDEX: () => import('@/views/functionaries/functionary-profiles/index.vue'),
  /** List of detailed functionary profiles */
  FUNCTIONARY_PROFILES_LIST: () => import('@/views/functionaries/functionary-profiles/listFunctionaryProfiles.vue'),
  /** Student profiles management index */
  STUDENT_PROFILES_INDEX: () => import('@/views/students/student-profiles/index.vue'),
  /** List of detailed student profiles */
  STUDENT_PROFILES_LIST: () => import('@/views/students/student-profiles/listStudentProfiles.vue'),
  /** External profiles management index */
  EXTERNALSS_PROFILES_INDEX: () => import('@/views/externals/external-profiles/index.vue'),
  /** List of detailed external partner profiles */
  EXTERNALS_PROFILES_LIST: () => import('@/views/externals/external-profiles/listExternalProfiles.vue'),
  /** User's own profile settings view */
  PROFILE_VIEW: () => import('@/views/profile/index.vue'),
  /** DIRI (Dirección de Investigaciones) management index */
  DIRI_INDEX: () => import('@/views/diri/index.vue'),
  /** List of DIRI members/profiles */
  DIRI_LIST: () => import('@/views/diri/listDiri.vue'),
  /** System activity logs and audit trail */
  LOG_INDEX: () => import('@/views/logs/index.vue'),
  /** Custom 404 Not Found error page */
  NOT_FOUND: () => import('@/views/errors/NotFound.vue'),
}
