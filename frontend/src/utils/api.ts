class API{


  //private readonly API_BASE_URL: string = '/api/';
  private readonly API_BASE_URL: string = 'http://localhost:8080/api/';

  public readonly HEADER_TEST: string='hello/header';

  // ya se encuentra registrada en el archivo vite.config.mts
  //----[ENDPOINTS]----
  //----[ENUMS]----
  public readonly LINES_OF_RESEARCH_BY_INVESTIGATION_GROUP: string='enums/get-lines-of-research-by-investigation-group-id/';
  public readonly LINES_OF_RESEARCH_BY_RESEARCH_SEEDBED: string='enums/get-lines-of-research-by-research-seedbed-id/'
  public readonly LINES_OF_RESEARCH_BY_INVESTIGATION_VALUES: string='enums/LineOfResearch/values';
  public readonly INTEGRA_USER_TYPES: string='enums/JSONIntegraType/values';
  public readonly EXTERNAL_USER_TYPES: string='enums/TypeOfExternalUser/values';
  public readonly SEX_VALUES: string='enums/Sex/values';

  //----[ACADEMIC PERIODS]----
  public readonly ACADEMIC_PERIODS:string='academic-periods/';

  //----[ACADEMIC PROGRAMS]----
  public  readonly ACADEMIC_PROGRAMS:string='academic-programs/';

  //----[DEPENDENCIES]----
  public readonly DEPENDENCIES:string='dependencies/';

  //----[EXTERNAL USER PROFILES]----q
  public readonly EXTERNAL_USER_PROFILES:string='external-user-profiles/';
  public readonly EXTERNAL_USER_PROFILEs_ASIGNED:string='external-user-profiles/find-all-profiles/';
  public readonly EXTERNAL_USER_PROFILES_BY_RESEARCH_SEEDBED:string='external-user-profiles/research-seedbed-profile/'; //<---- Requires academic period id

  //----[FUNCTIONARY PROFILES]----
  public readonly FUNCTIONARY_PROFILES:string='functionary-profiles/';
  public readonly FUNCTIONARY_PROFILES_ASSIGNED:string='functionary-profiles/find-all-profiles/'; //<---- Requires research seedbed id
  public readonly FUNCTIONARY_PROFILES_BY_ACADEMIC_PERIOD:string='functionary-profiles/find-all-profiles-by-academic-period/'; //<---- Requires academic period id

  //----[STUDENT PROFILES]----
  public readonly STUDENT_PROFILES:string='student-profiles/';
  public readonly STUDENT_PROFILES_ASSIGNED:string='student-profiles/find-all-profiles/'; //<---- Requires research seedbed id
  public readonly STUDENT_PROFILES_BY_ACADEMIC_PERIOD:string='student-profiles/academic-period/'; //<---- Requires academic period id

  //----[INVESTIGATION GLOBAL GROUPS]----
  public readonly INVESTIGATION_GROUPS:string='investigation-groups/';

  //----[INVESTIGATION GROUPS PROFILES]----
  public readonly INVESTIGATION_GRUOPS_PROFILES:string='investigation-group-profiles/';
  public readonly INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD:string='investigation-group-profiles/get-all-by-academic-period-id/'; //<---- Requires academic period id

  //----[ROLES]----
  public readonly ROLES:string='roles/';

  //----[RESEARCH SEEDBEDS]----
  public readonly RESEARCH_SEEDBEDS:string='research-seedbeds/';
  public readonly RESEARCH_SEEDBEDS_BY_USER_ID:string='research-seedbeds/seedbeds-by-user-id/'; //<---- Requires academic period id

  //----[RESEARCH SEEDBEDS PROFILES]----
  public readonly RESEARCH_SEEDBEDS_PROFILES:string='research-seedbed-profiles/';
  public readonly RESEARCH_SEEDBEDS_PROFILES_BY_INVESTIGATION_GROUP_PROFILE:string='research-seedbed-profiles/get-all-by-investigation-group-profile-id/'; //<---- Requires investigation group profile id
  public readonly RESEARCH_SEEDBEDS_MEMBERS:string='research-seedbed-student-profile/'; //<---- Requires research seedbed id

  //----[RESEARCH SEEDBED STUDENT PROFILES]----
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES:string='research-seedbed-student-profile/research-seedbed-profile/';
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL:string='research-seedbed-student-profile/add-all-by-excel/'; //<---- Requires research seedbed id

  //----[USERS_PATH]----
  public readonly USERS:string='users/';
  public readonly USERS_ME:string='users/me';
  public readonly USERS_INTEGRA:string='users/integra-user';
  public readonly USERS_FUNCTIONARY:string='users/all-functionaries-registered';
  public readonly USERS_STUDENTS:string='users/all-students-registered';
  public readonly USERS_INTERNAL: string ='users/all-internal-users-registered';
  public readonly USERS_EXTERNAL:string='users/all-external-users-registered';
  public readonly USERS_DIRI:string='users/diri-users';

  //----[REPORTS]----
  public readonly SINGLE_PERIOD_REPORTS_INVESTIGATION_GROUPS:string = 'investigation-group-profiles/generate-investigation-group-half-year-report';
  readonly SINGLE_PERIOD_REPORTS_ACTIVE_RESEARCH_SEEDBEDS:string = 'investigation-group-profiles/generate-active-seedbeds-half-year-report';
  readonly CONSOLIDATE_REPORTS_INVESTIGATION_GROUPS:string = 'investigation-group-profiles/generate-investigation-group-annual-year-report';
  readonly CONSOLIDATE_REPORTS_ACTIVE_RESEARCH_SEEDBEDS:string = 'investigation-group-profiles/generate-active-seedbeds-annual-year-report';
  readonly ANUAL_REPORTS_RESEARCH_SEEDBEDS_STUDENTS:string = 'research-seedbed-profiles/generate-seedbed-report';
  readonly GENERATE_CERTIFICATES:string = 'users/student-seedbed-certificate';

  //----[LOGS]----
  public readonly ACTION_LOGS: string = 'action-logs/';
  public readonly CRONJOB_LOGS: string = 'cronjob-logs/';
  public readonly ERROR_LOGS: string = 'error-logs/';


  //----[COUNTRIES]----
  public readonly COUNTRIES:string='users/all-countries';

  //----[GOOGLE]----
  public readonly GOOGLE_DATA:string='security/me';
  public readonly GOOGLE_LOGOUT:string='logout';
  public readonly GOOGLE_LOGIN:string='oauth2/authorization/google';

  private static instance: API;

  private constructor() {}

  public static getInstance(): API {
    if (!API.instance) {
      API.instance = new API();
    }
    return API.instance;
  }

  public async get(endpoint: string, headers: Record<string, string> = {}): Promise<any> {
    try {
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'GET',
        credentials: "include",
        headers: {
          'Content-Type': 'application/json',

          //'Authorization': `Bearer eyJhbGciOiJSUzI1NiIsImtpZCI6ImJhNjNiNDM2ODM2YTkzOWI3OTViNDEyMmQzZjRkMGQyMjVkMWM3MDAiLCJ0eXAiOiJKV1QifQ.eyJpc3MiOiJodHRwczovL2FjY291bnRzLmdvb2dsZS5jb20iLCJhenAiOiIxNzc2NDAyNTM0ODQtMDBobmE5ZGNndWhwOTBscTg2dG5yN25nMnVtbmNsa2kuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJhdWQiOiIxNzc2NDAyNTM0ODQtMDBobmE5ZGNndWhwOTBscTg2dG5yN25nMnVtbmNsa2kuYXBwcy5nb29nbGV1c2VyY29udGVudC5jb20iLCJzdWIiOiIxMDc2NTUxNjUwNDQ3MTY2Mjc4NzMiLCJoZCI6ImVzdHVkaWFudGVzdW5pYmFndWUuZWR1LmNvIiwiZW1haWwiOiIyMjIwMjExMDUyQGVzdHVkaWFudGVzdW5pYmFndWUuZWR1LmNvIiwiZW1haWxfdmVyaWZpZWQiOnRydWUsImF0X2hhc2giOiJiTmZYV0pSUW4zME9EUmhNNGhlN1JRIiwibm9uY2UiOiJvc3RvTUFNb1J1LUxmYlIzLUZ6LTRRM0NCenpiWm93UVhkOTJHeXh1MXB3IiwibmFtZSI6IkVERVIgREFOSUVMIE1BUlRJTkVaIENBTUFDSE8iLCJwaWN0dXJlIjoiaHR0cHM6Ly9saDMuZ29vZ2xldXNlcmNvbnRlbnQuY29tL2EvQUNnOG9jSlFXelloekEwbDZ2TlNxTXdNWDV5NWJ1Y0t5dGVTVlJqQ2tlNHRmc2JyTHZLaFl5ZVc9czk2LWMiLCJnaXZlbl9uYW1lIjoiRURFUiBEQU5JRUwiLCJmYW1pbHlfbmFtZSI6Ik1BUlRJTkVaIENBTUFDSE8iLCJpYXQiOjE3NTQ4ODAxNjMsImV4cCI6MTc1NDg4Mzc2M30.qpdp3aYGuvc6Ko5zJ-Gb1Km45Ua9BcBKaJiIINNlCm_BjydvJu5frKVlJE7vsXPRpcJYtKLVfkAsSB8rQQ1xkhfnZG6LE6jHlI7J0UpLaKRPow5OkKjGqj0QTAAc7J0kN0WoW8sMK9_xUSyjsu9grgDhlklPTSG1uzGvTn-Ynt0k25tpai8Fcs7GP_NejM-7LACwPlCDldoRTMNK6q-BbiC2bpZ2wriBhrIfXWsux4E6AIwJdru4gjPFrDqk27irU-X4EPVtbzkuoZ9uQaNhIdloTIRUYe5hVOwfh_qTQ-edJsPHatIAgsi2Q7gYlRwF36mHppOLL6jGC9B2KK8fow`, // Agregar token de autorización si existe
          ...headers, // Agregar headers personalizados si existen
        },
      });

      const contentType = response.headers.get('content-type');
      if (contentType && contentType.includes('application/json')) {
        const data = await response.json();
        return Array.isArray(data) ? data : [data];
      } else {
        const text = await response.text();
        return text;
      }
    } catch (error) {
      console.error(`Error fetching ${endpoint}:`, error);
      throw error;
    }
  }


  public async download(endpoint: string, headers: Record<string, string> = {}): Promise<Response> {
    return await fetch(this.API_BASE_URL + `${endpoint}`, {
      method: 'GET',
      credentials: 'include',
      headers: {
        ...headers
      }
    });
  }


  public async post(endpoint: string, data?: any, headers: Record<string, string> = {}) {
    try {
      const isFormData = data instanceof FormData;

      const fetchOptions: RequestInit = {
        method: 'POST',
        credentials: "include",
        headers: {
          ...(isFormData ? {} : { 'Content-Type': 'application/json' }),
          ...headers
        },
        body: isFormData ? data : JSON.stringify(data)
      };

      const response = await fetch(this.API_BASE_URL + `${endpoint}`, fetchOptions);

      // Check if response is JSON
      const contentType = response.headers.get('content-type');

      if (!response.ok) {
        if (contentType && contentType.includes('application/json')) {
          const responseData = await response.json();
          const error: any = new Error('API Error');
          error.response = { data: responseData, status: response.status };
          throw error;
        } else {
          throw new Error(`HTTP ${response.status}: ${response.statusText}`);
        }
      }

      // Handle successful responses
      if (contentType && contentType.includes('application/json')) {
        return await response.json();
      } else {
        // Return the response object for non-JSON data (files, etc.)
        return response;
      }
    } catch (error) {
      console.error(`Error posting to ${endpoint}:`, error);
      throw error;
    }
  }

  public async put(endpoint: string, data: any, headers: Record<string, string> = {}) {
    try {
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'PUT',
        credentials: "include",
        headers: {
          'Content-Type': 'application/json',
          ...headers
        },
        body: JSON.stringify(data)
      });
      const responseData = await response.json();

      // If status is not ok, throw an error with the response data
      if (!response.ok) {
        const error: any = new Error('API Error');
        error.response = { data: responseData, status: response.status };
        throw error;
      }

      return responseData;
    } catch (error) {
      console.error(`Error patching to ${endpoint}:`, error);
      throw error;
    }
  }

  public async delete(endpoint: string, headers: Record<string, string> = {}){
    try{
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'DELETE',
        credentials: "include",
        headers: {
          ...headers
        },
      });

      const responseData = await response.json();

      if (!response.ok) {
        const error: any = new Error('API Error');
        error.response = { data: responseData, status: response.status };
        throw error;
      }
      else if (response.ok && response.status === 204) {
        return {}; // or return null
      }
      return response.json();
    }catch(error){
      console.error(`Error deleting to ${endpoint}:`, error);
      throw error
    }
  }

  public login() {
    window.location.href = this.API_BASE_URL + this.GOOGLE_LOGIN;
  }

  public logout() {
    window.location.href = this.API_BASE_URL + this.GOOGLE_LOGOUT;
  }
}

export default API.getInstance()
