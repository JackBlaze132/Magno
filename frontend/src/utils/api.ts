
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

  //----[ACADEMIC PERIODS]----
  public readonly ACADEMIC_PERIODS:string='academic-periods/';

  //----[ACADEMIC PROGRAMS]----
  public  readonly ACADEMIC_PROGRAMS:string='academic-programs/';

  //----[DEPENDENCIES]----
  public readonly DEPENDENCIES:string='dependencies/';

  //----[EXTERNAL USER PROFILES]----q
  public readonly EXTERNAL_USER_PROFILES:string='external-user-profiles/';

  //----[FUNCTIONARY PROFILES]----
  public readonly FUNCTIONARY_PROFILES:string='functionary-profiles/';

  //----[INVESTIGATION GLOBAL GROUPS]----
  public readonly INVESTIGATION_GROUPS:string='investigation-groups/';

  //----[INVESTIGATION GROUPS PROFILES]----
  public readonly INVESTIGATION_GRUOPS_PROFILES:string='investigation-groups-profiles/';
  public readonly INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD:string='investigation-group-profiles/get-all-by-academic-period-id/'; //<---- Requires academic period id

  //----[ROLES]----
  public readonly ROLES:string='roles/';

  //----[RESEARCH SEEDBEDS]----
  public readonly RESEARCH_SEEDBEDS:string='research-seedbeds/';

  //----[RESEARCH SEEDBEDS PROFILES]----
  public readonly RESEARCH_SEEDBEDS_PROFILES:string='research-seedbeds-profiles/';
  public readonly RESEARCH_SEEDBEDS_PROFILES_BY_INVESTIGATION_GROUP_PROFILE:string='research-seedbed-profiles/get-all-by-investigation-group-profile-id/'; //<---- Requires investigation group profile id

  //----[RESEARCH SEEDBED STUDENT PROFILES]----
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES:string='research-seedbed-student-profiles/';
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL:string='research-seedbed-student-profile/add-all-by-excel/'; //<---- Requires research seedbed id

  //----[STUDENT PROFILES]----
  public readonly STUDENT_PROFILES:string='student-profiles/';

  //----[USERS]----
  public readonly USERS:string='users/';
  public readonly USERS_INTEGRA:string='users/integra-user';
  public readonly USERS_FUNCTIONARY:string='users/all-functionaries-registered';
  public readonly USERS_STUDENTS:string='users/all-students-registered';
  public readonly USERS_EXTERNAL:string='users/all-external-users-registered';

  //----[COUNTRIES]----
  public readonly COUNTRIES:string='users/all-countries';

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
        headers: {
          'Content-Type': 'application/json',
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


  public async post(endpoint: string, data: any, headers: Record<string, string> = {}) {
    try {
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          ...headers
        },
        body: JSON.stringify(data)
      });
      return response.json();
    } catch (error) {
      console.error(`Error posting to ${endpoint}:`, error);
      throw error;
    }
  }

  public async put(endpoint: string, data: any, headers: Record<string, string> = {}) {
    try {
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
          ...headers
        },
        body: JSON.stringify(data)
      });
      return response.json();
    } catch (error) {
      console.error(`Error patching to ${endpoint}:`, error);
      throw error;
    }
  }

  public async delete(endpoint: string, headers: Record<string, string> = {}){
    try{
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'DELETE',
        headers: {
          ...headers
        },
      });
      if (response.ok && response.status === 204) {
        return {}; // or return null
      }
      return response.json();
    }catch(error){
      console.error(`Error deleting to ${endpoint}:`, error);
      throw error
    }
  }

}

export default API.getInstance()



