
class API{


  //private readonly API_BASE_URL: string = '/api/';
  private readonly API_BASE_URL: string = 'http://localhost:8080/api/';

  public readonly HEADER_TEST: string='hello/header';
  // ya se encuentra registrada en el archivo vite.config.mts
  //----[ENDPOINTS]----
  //----[GET]----
  public readonly GET_ACADEMIC_PERIODS: string = 'academic-periods/';
  public readonly GET_FUNCTIONARY_PROFILES: string = 'getFunctionaryProfiles';
  public readonly GET_STUDENT_PROFILES: string = 'getStudentProfiles';
  public readonly GET_INVESTIGATION_GROUPS: string = 'investigation-groups/';
  public readonly GET_RESEARCH_SEEDBEDS: string = 'getResearchSeedbeds';
  public readonly GET_USERS: string = 'getUsers';
  public readonly GET_INVESTIGATION_GROUP_BY_ACADEMIC_PERIOD: string = 'getInvestigationGroupsByAssesmentPeriodId/';
  public readonly GET_RESEARCH_SEEDBED_BY_GROUP_ID:string='getResearchSeedbedsByInvestigationGroupId/';
  public readonly GET_COORDINATOR_BY_RESEARCH_SEEDBED_ID: string = 'getCoordinatorByResearchseedbedId/';
  public readonly GET_EXTERNAL_FUNCTIONARY_PROFILE_BY_SEEDBED_ID: string='getExternalFunctionaryProfilesByResearchSeedbedId/';
  public readonly GET_STUDENT_PROFLIE_BY_RESEARCH_SEEDBED_ID: string='getStudentProfilesByResearchSeedbedId/';
  public readonly GET_TUTOR_BY_RESEARCH_SEEDBED_ID: string='getTutorByResearchseedbedId/';
  public readonly GET_RESEARCH_SEEDBED_BY_ID: string='getResearchSeedbedById/';
  public readonly GET_FUNNCTIONARY_PROFILES_BY_ASSESMENT_PERIOD_ID:string='getFunctionaryProfileByAssesmentPeriodId/';
  public readonly GET_STUDENT_SEEDBEDS: string='getStudentProfilesResearchSeedbed/';

  //----[POST]----
  public readonly POST_INVESTIGATION_GROUP:string='addInvestigationGroup';
  public readonly POST_ACADEMIC_PERIOD:string='academic-periods/';
  public readonly POST_RESEARCH_SEEDBED:string='addResearchSeedbed';
  public readonly POST_STUDENT_PROFILE:string='addStudentProfile';
  public readonly POST_USER:string='addUser';
  public readonly POST_STUDENT_PROFILE_BY_EXCEL:string='addStudentProfileByExcel/';
  public readonly POST_FUNCTIONARY_PROFILE: string='addFunctionaryProfile';

  //----[PATCH]----
  public readonly PATCH_RESEARCH_SEEDBED_FUNCTIONARY:string='updateResearchSeedbedFunctionary';
  public readonly PATCH_INVESTIGATION_GROUP_NAME:string='updateInvestigationGroupName';
  public readonly PATCH_RESEARCH_SEEDBED_NAME:string='updateResearchSeedbedName';
  public readonly PUT_ACADEMIC_PERIOD:string='academic-periods/';


  //----[DELETE]----
  public readonly DELETE_STUDENT_PROFILE_FROM_RESEARCH_SEEDBED:string='deleteStudentProfileFromAResearchSeedbed';
  public readonly DELETE_RESEARCH_SEEDBED:string='deleteResearchSeedbed/';
  public readonly DELETE_INVESTIGATION_GROUP: string='deleteInvestigationGroup/';
  public readonly DELETE_ACADEMIC_PERIOD: string='academic-periods/';



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

  /**public async get(endpoint: string) {
    try {
      const response = await fetch(this.API_BASE_URL + `${endpoint}`);
      const data = await response.json();
      return Array.isArray(data) ? data : [data];
    } catch (error) {
      console.error(`Error fetching ${endpoint}:`, error);
      throw error;
    }
  }**/

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



