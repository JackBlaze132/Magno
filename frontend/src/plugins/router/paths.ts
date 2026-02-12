/**
 * Defines all the path constants used for routing throughout the Magno application.
 * Using constant paths prevents magic strings and makes route changes easier to manage.
 */

/**
 * Object containing all the route path segments and full paths.
 * @type {Readonly<Object<string, string>>}
 */
export const paths = {
  /** Root path of the application */
  RAIZ: '/',
  /** Login page path */
  LOGIN: '/login',
  /** Base path for the home/dashboard */
  HOME: 'inicio',
  /** Path for the reports section */
  REPORTS: '/informes',
  /** Base path for academic period management */
  PERIODS: '/periodos',
  /** Sub-path for listing academic periods */
  PERIODS_LIST: 'listar-periodos',
  /** Dynamic path for investigation groups within a specific period */
  INVESTIGATION_GROUPS_BY_PERIOD: ':idPeriodo/grupos-investigacion',
  /** Sub-path for listing groups */
  INVESTIGATION_GROUPS_LIST: 'listar-grupos',
  /** Dynamic path for seedbeds within a specific group */
  RESEARCH_SEEDBED_BY_GROUP: ':idGrupo/semilleros',
  /** Sub-path for listing seedbeds */
  RESEARCH_SEEDBEDS_LIST: 'listar-semilleros',
  /** Dynamic path for a specific seedbed lounge/details */
  RESEARCH_SEEDBED_LOUNGE: ':idSemillero',
  /** Sub-path for specific details view */
  DETAILS: 'detalles',
  /** Generic path for research seedbeds */
  RESEARCH_SEEDBEDS_PATH: '/semilleros',
  /** Base path for user management */
  USERS_PATH: '/usuarios',
  /** Sub-path for listing users */
  USERS_LIST: 'listar-usuarios',
  /** Base path for functionary profiles */
  FUNCTIONARIES_PATH: '/funcionarios',
  /** Sub-path for listing functionaries */
  FUNCTIONARIES_LIST: 'listar-funcionarios',
  /** Base path for student profiles */
  STUDENTS_PATH: '/estudiantes',
  /** Sub-path for listing students */
  STUDENTS_LIST: 'listar-estudiantes',
  /** Base path for external allies */
  EXTERNALS_PATH: '/aliados-externos',
  /** Sub-path for listing allies */
  EXTERNALS_LIST: 'listar-aliados-externos',
  /** Base path for DIRI management */
  DIRI_PATH: '/diri',
  /** Sub-path for listing DIRI members */
  DIRI_LIST: 'listar-diri',
  /** Generic path for investigation groups */
  GROUPS_PATH: '/grupos',
  /** Base path for role management */
  ROLES_PATH: '/roles',
  /** Sub-path for listing roles */
  ROLES_LISTAR: 'listar-roles',
  /** Base path for profile management */
  PROFILES_PATH: '/perfiles',
  /** Sub-path for listing profiles */
  PROFILES_LIST: 'listar-perfiles',
  /** Path to view the current user's profile */
  PROFILE_VIEW: '/perfil',
  /** Path for system logs and audit */
  LOGS_PATH: '/logs',
  /** Path for the 404 error page */
  ERROR_404: '/404',
}
