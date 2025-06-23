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
        path: P.HOME,
        component: components.HOME_INDEX,
      },
      {
        name: 'informes',
        path: P.REPORTS,
        component: components.REPORTS_INDEX,
      },
      {
        path: P.PERIODS,
        redirect: P.PERIODS + '/' + P.PERIODS_LIST,
        component: components.ACADEMIC_PERIODS_INDEX,
        children: [
          {
            name: 'listar-periodos',
            path: P.PERIODS_LIST,
            component: components.PERIODS_LIST,
          },
          {
            name: 'grupos-investigacion',
            path: P.INVESTIGATION_GROUPS_BY_PERIOD,
            redirect: (to: RouteLocationNormalized) => {
              return { name: 'listar-grupos', params: { idPeriodo: to.params.idPeriodo } }
            },
            component: components.INVESTIGATION_GROUPS_PROFILES_INDEX,
            children: [
              {
                name: 'listar-grupos',
                path: P.INVESTIGATION_GROUPS_LIST,
                component: components.INVESTIGATION_GROUPS_PROFILES_LIST,
              },
              {
                name: 'semilleros-investigacion',
                path: P.RESEARCH_SEEDBED_BY_GROUP,
                redirect: (to: RouteLocationNormalized) => {
                  return {
                    name: 'listar-semilleros',
                    params: {
                      idPeriodo: to.params.idPeriodo,
                      idGrupo: to.params.idGrupo,
                    },
                  }
                },
                component: components.SEEDBEDS_BY_GROUP_INDEX,
                children: [
                  {
                    name: 'listar-semilleros',
                    path: P.RESEARCH_SEEDBEDS_LIST,
                    component: components.SEEDBEDS_BY_GROUP_LIST,
                  },
                  {
                    path: P.RESEARCH_SEEDBED_LOUNGE,
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
                        path: P.DETAILS,
                        component: components.LOUNGE_MEMBERS,
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
        component: components.GROUPS_LIST,
      },
      {
        name: 'semilleros',
        path: P.RESEARCH_SEEDBEDS_PATH,
        component: components.SEEDBEDS_LIST,
      },
      {
        name: 'usuarios',
        path: P.USERS_PATH,
        redirect: P.USERS_PATH + '/' + P.USERS_LIST,
        component: components.USERS_INDEX,
        children: [
          {
            path: P.USERS_LIST,
            component: components.LIST_USER,
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
            component: components.ROLES_LIST,
          },
        ]
      },
      {
        name: 'funcionarios',
        path: P.FUNCTIONARIES_PATH,
        component: components.FUNCTIONARIES_INDEX,
        redirect: (to: RouteLocationNormalized) => {
          return { name: 'listar-funcionarios' }
        },
        children: [
          {
            name: 'listar-funcionarios',
            path: P.FUNCTIONARIES_LIST,
            component: components.FUNCTIONARIES_LIST,
          },
          {
            path: ':idFunctionary' + P.PROFILES_PATH,
            name: 'perfiles-funcionario',
            component: components.FUNCTIONARY_PROFILES_INDEX,
            redirect: (to: RouteLocationNormalized) => {
              return { name: 'listar-perfiles-funcionario', params: { idFunctionary: to.params.idFunctionary } }
            },
            children: [
              {
                name: 'listar-perfiles-funcionario',
                path: P.PROFILES_LIST,
                component: components.FUNCTIONARY_PROFILES_LIST,
              },
            ],
          }
        ],
      },
      {
        path: P.STUDENTS_PATH,
        component: components.STUDENTS_INDEX,
        redirect: (to: RouteLocationNormalized) => {
          return { name: 'listar-estudiantes' }
        },
        children: [
          {
            path: P.STUDENTS_LIST,
            name: 'listar-estudiantes',
            component: components.STUDENTS_LIST,
          },
          {
            path: ':idStudent' + P.PROFILES_PATH,
            name: 'perfiles-estudiante',
            component: components.FUNCTIONARY_PROFILES_INDEX,
            redirect: (to: RouteLocationNormalized) => {
              return { name: 'listar-perfiles-estudiante', params: { idStudent: to.params.idStudent } }
            },
            children: [
              {
                name: 'listar-perfiles-estudiante',
                path: P.PROFILES_LIST,
                component: components.STUDENT_PROFILES_LIST,
              },
            ],
          }
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
