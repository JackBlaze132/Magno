import { RouteLocationNormalized } from 'vue-router'
import { paths as P } from './paths'
import { components } from './components'

export const routes = [
  {
    path: P.RAIZ,
    redirect: P.LOGIN,
  },
  {
    path: P.RAIZ,
    component: components.LAYOUT_DEFAULT,
    children: [
      {
        name: 'inicio',
        path: P.INICIO,
        component: components.HOME_INDEX,
      },
      {
        name: 'informes',
        path: P.INFORMES,
        component: components.REPORTS_INDEX,
      },
      {
        path: P.PERIODOS,
        redirect: P.PERIODOS + '/' + P.LISTAR_PERIODOS,
        component: components.ACADEMIC_PERIODS_INDEX,
        children: [
          {
            name: 'listar-periodos',
            path: P.LISTAR_PERIODOS,
            component: components.LIST_PERIODS,
          },
          {
            name: 'agregar-periodo',
            path: P.AGREGAR_PERIODO,
            component: components.ADD_PERIOD,
          },
          {
            name: 'editar-preiodo',
            path: P.EDITAR_PERIODO_DINAMICO,
            component: components.EDIT_PERIOD,
          },
          {
            name: 'grupos-investigacion',
            path: P.GRUPOS_INVESTIGACION_DINAMICO,
            redirect: (to: RouteLocationNormalized) => {
              return { name: 'listar-grupos', params: { idPeriodo: to.params.idPeriodo } }
            },
            component: components.RESEARCH_GROUPS_INDEX,
            children: [
              {
                name: 'listar-grupos',
                path: P.LISTAR_GRUPOS,
                component: components.LIST_GROUPS_BY_PERIOD,
              },
              {
                name: 'agregar-grupo',
                path: P.AGREGAR_GRUPO,
                component: components.ADD_GROUP_BY_PERIOD,
              },
              {
                name: 'editar-grupo',
                path: P.EDITAR_GRUPO_DINAMICO,
                component: components.EDIT_GROUP_BY_PERIOD,
              },
              {
                name: 'semilleros',
                path: P.SEMILLEROS_DINAMICO,
                redirect: (to: RouteLocationNormalized) => {
                  return {
                    name: 'listar-semilleros',
                    params: {
                      idPeriodo: to.params.idPeriodo,
                      idGrupo: to.params.idGrupo,
                    },
                  }
                },
                component: components.SEEDBEDS_INDEX,
                children: [
                  {
                    name: 'listar-semilleros',
                    path: P.LISTAR_SEMILLEROS,
                    component: components.LIST_SEEDBEDS_BY_GROUP,
                  },
                  {
                    name: 'agregar-semillero',
                    path: P.AGREGAR_SEMILLERO,
                    component: components.ADD_SEEDBED_BY_GROUP,
                  },
                  {
                    name: 'editar-semillero',
                    path: P.EDITAR_SEMILLERO_DINAMICO,
                    component: components.EDIT_SEEDBED_BY_GROUP,
                  },
                  {
                    path: P.SEMILLERO_DINAMICO,
                    redirect: (to: RouteLocationNormalized) => {
                      return {
                        name: 'detalles',
                        params: {
                          idPeriodo: to.params.idPeriodo,
                          idGrupo: to.params.idGrupo,
                          idSemillero: to.params.idSemillero,
                        },
                      }
                    },
                    component: components.LOUNGE_INDEX,
                    children: [
                      {
                        name: 'detalles',
                        path: P.DETALLES_NOMBRE,
                        component: components.LOUNGE_MEMBERS,
                      },
                      {
                        name: 'editar-coordinador',
                        path: P.EDITAR_COORDINADOR,
                        component: components.LOUNGE_EDIT_COORDINATOR,
                      },
                      {
                        name: 'editar-tutor',
                        path: P.EDITAR_TUTOR,
                        component: components.LOUNGE_EDIT_TUTOR,
                      },
                      {
                        name: 'subir-estudiantes',
                        path: P.SUBIR_ESTUDIANTES,
                        component: components.LOUNGE_UPLOAD_STUDENT,
                      },
                      {
                        name: 'agregar-estudiante',
                        path: P.AGREGAR_ESTUDIANTE,
                        component: components.LOUNGE_ADD_STUDENT,
                      },
                    ],
                  },
                ],
              },
            ],
          },
        ],
      },
      {
        name: 'grupos',
        path: P.GROUPS_PATH,
        component: components.LIST_GROUPS,
      },
      {
        name: 'semilleros',
        path: P.SEMILLEROS_PATH,
        component: components.SEEDBEDS_LIST,
      },
      {
        name: 'usuarios',
        path: P.USUARIOS_PATH,
        redirect: P.USUARIOS_PATH + '/' + P.USUARIOS_LISTAR,
        component: components.USERS_INDEX,
        children: [
          {
            path: P.USUARIOS_LISTAR,
            component: components.LIST_USER,
          },
          {
            path: P.USUARIOS_AGREGAR,
            component: components.ADD_USER,
          },
        ],
      },
      {
        name: 'roles',
        path: P.ROLES_PATH,
        redirect: P.ROLES_PATH + '/' + P.ROLES_LISTAR,
        component: components.ROLES_INDEX,
        children: [
          {
            path: P.ROLES_LISTAR,
            component: components.LIST_ROLES,
          },
        ]
      },
      {
        name: 'funcionarios',
        path: P.FUNCIONARIOS_PATH,
        component: components.FUNCTIONARY_INDEX,
        redirect: (to: RouteLocationNormalized) => {
          return { name: 'listar-funcionarios' }
        },
        children: [
          {
            name: 'listar-funcionarios',
            path: P.FUNCIONARIOS_LISTAR,
            component: components.LIST_FUNCTIONARIES,
          },
          {
            path: P.FUNCIONARIOS_AGREGAR,
            name: 'agregar-funcionarios',
            component: components.ADD_FUNCTIONARY,
          },
        ],
      },
      {
        path: P.ESTUDIANTES_PATH,
        component: components.STUDENTS_INDEX,
        redirect: (to: RouteLocationNormalized) => {
          return { name: 'listar-estudiantes' }
        },
        children: [
          {
            path: P.ESTUDIANTES_LISTAR,
            name: 'listar-estudiantes',
            component: components.LIST_STUDENTS,
          },
          {
            path: P.ESTUDIANTE_DETALLES_DINAMICO,
            name: 'detalles-estudiante',
            component: components.DETAIL_STUDENT,
          },
        ],
      },
      {
        path: P.EXTERNALS_PATH,
        component: components.EXTERNALS_INDEX,
        redirect: (to: RouteLocationNormalized) => {
          return { name: 'listar-aliados-externos' }
        },
        children: [
          {
            path: P.EXTERNALS_LIST,
            name: 'listar-aliados-externos',
            component: components.EXTERNALS_LIST,
          },
        ],
      }
    ],
  },
  {
    path: P.RAIZ,
    component: components.LAYOUT_BLANK,
    children: [
      {
        path: P.LOGIN.replace('/', ''), // "login"
        component: components.LOGIN_INDEX,
      },
    ],
  },
]
