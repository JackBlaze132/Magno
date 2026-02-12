/**
 * Singleton API class for managing HTTP requests to the backend.
 * Provides methods for GET, POST, PUT, DELETE operations and handles authentication.
 * All endpoints are defined as public properties for easy access throughout the application.
 *
 * @class API
 * @example
 * import API from '@/utils/api';
 *
 * // GET request
 * const users = await API.get(API.USERS, {'API-VERSION': '1'});
 *
 * // POST request
 * const newUser = await API.post(API.USERS, userData);
 */
class API{


  /**
   * Base URL for all API requests.
   * Uses environment variable VITE_API_BASE_URL or defaults to localhost in development.
   * @private
   * @readonly
   * @type {string}
   */
  private readonly API_BASE_URL: string = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api/';

  /**
   * Test endpoint for header validation.
   * @public
   * @readonly
   * @type {string}
   */
  public readonly HEADER_TEST: string='hello/header';

  // ya se encuentra registrada en el archivo vite.config.mts
  //----[ENDPOINTS]----
  //----[ENUMS]----
  /** Endpoint to get lines of research by investigation group ID. Requires group ID as parameter. */
  public readonly LINES_OF_RESEARCH_BY_INVESTIGATION_GROUP: string='enums/get-lines-of-research-by-investigation-group-id/';
  /** Endpoint to get lines of research by research seedbed ID. Requires seedbed ID as parameter. */
  public readonly LINES_OF_RESEARCH_BY_RESEARCH_SEEDBED: string='enums/get-lines-of-research-by-research-seedbed-id/'
  /** Endpoint to get all line of research enum values. */
  public readonly LINES_OF_RESEARCH_BY_INVESTIGATION_VALUES: string='enums/LineOfResearch/values';
  /** Endpoint to get Integra user type enum values. */
  public readonly INTEGRA_USER_TYPES: string='enums/JSONIntegraType/values';
  /** Endpoint to get external user type enum values. */
  public readonly EXTERNAL_USER_TYPES: string='enums/TypeOfExternalUser/values';
  /** Endpoint to get sex enum values. */
  public readonly SEX_VALUES: string='enums/Sex/values';

  //----[ACADEMIC PERIODS]----
  /** Endpoint for academic periods CRUD operations. */
  public readonly ACADEMIC_PERIODS:string='academic-periods/';
  /** Endpoint to get the current active academic period. */
  public readonly ACTIVE_ACADEMIC_PERIOD:string='academic-periods/active';
  /** Endpoint to get the visible academic periods. */
  public readonly VISIBLE_ACADEMIC_PERIODS:string='academic-periods/visible';
  /** Endpoint to get the not visible academic periods. */
  public readonly NOT_VISIBLE_ACADEMIC_PERIODS:string='academic-periods/not-visible';

  //----[ACADEMIC PROGRAMS]----
  /** Endpoint for academic programs CRUD operations. */
  public  readonly ACADEMIC_PROGRAMS:string='academic-programs/';

  //----[DEPENDENCIES]----
  /** Endpoint for dependencies CRUD operations. */
  public readonly DEPENDENCIES:string='dependencies/';

  //----[EXTERNAL USER PROFILES]----
  /** Endpoint for external user profiles CRUD operations. */
  public readonly EXTERNAL_USER_PROFILES:string='external-user-profiles/';
  /** Endpoint to get all assigned external user profiles. */
  public readonly EXTERNAL_USER_PROFILEs_ASIGNED:string='external-user-profiles/find-all-profiles/';
  /** Endpoint to get external user profiles by research seedbed. Requires research seedbed ID as parameter. */
  public readonly EXTERNAL_USER_PROFILES_BY_RESEARCH_SEEDBED:string='external-user-profiles/research-seedbed-profile/';

  //----[FUNCTIONARY PROFILES]----
  /** Endpoint for functionary profiles CRUD operations. */
  public readonly FUNCTIONARY_PROFILES:string='functionary-profiles/';
  /** Endpoint to get all assigned functionary profiles. Requires research seedbed ID as parameter. */
  public readonly FUNCTIONARY_PROFILES_ASSIGNED:string='functionary-profiles/find-all-profiles/';
  /** Endpoint to get functionary profiles by academic period. Requires academic period ID as parameter. */
  public readonly FUNCTIONARY_PROFILES_BY_ACADEMIC_PERIOD:string='functionary-profiles/find-all-profiles-by-academic-period/';

  //----[STUDENT PROFILES]----
  /** Endpoint for student profiles CRUD operations. */
  public readonly STUDENT_PROFILES:string='student-profiles/';
  /** Endpoint to get all assigned student profiles. Requires research seedbed ID as parameter. */
  public readonly STUDENT_PROFILES_ASSIGNED:string='student-profiles/find-all-profiles/';
  /** Endpoint to get student profiles by academic period. Requires academic period ID as parameter. */
  public readonly STUDENT_PROFILES_BY_ACADEMIC_PERIOD:string='student-profiles/academic-period/';

  //----[INVESTIGATION GLOBAL GROUPS]----
  /** Endpoint for investigation groups CRUD operations. */
  public readonly INVESTIGATION_GROUPS:string='investigation-groups/';

  //----[INVESTIGATION GROUPS PROFILES]----
  /** Endpoint for investigation group profiles CRUD operations. */
  public readonly INVESTIGATION_GRUOPS_PROFILES:string='investigation-group-profiles/';
  /** Endpoint to get investigation group profiles by academic period. Requires academic period ID as parameter. */
  public readonly INVESTIGATION_GROUPS_PROFILES_BY_ACADEMIC_PERIOD:string='investigation-group-profiles/get-all-by-academic-period-id/';

  //----[ROLES]----
  /** Endpoint for roles CRUD operations. */
  public readonly ROLES:string='roles/';

  //----[RESEARCH SEEDBEDS]----
  /** Endpoint for research seedbeds CRUD operations. */
  public readonly RESEARCH_SEEDBEDS:string='research-seedbeds/';
  /** Endpoint to get research seedbeds by user ID. Requires user ID as parameter. */
  public readonly RESEARCH_SEEDBEDS_BY_USER_ID:string='research-seedbeds/seedbeds-by-user-id/';

  //----[RESEARCH SEEDBEDS PROFILES]----
  /** Endpoint for research seedbed profiles CRUD operations. */
  public readonly RESEARCH_SEEDBEDS_PROFILES:string='research-seedbed-profiles/';
  /** Endpoint to get research seedbed profiles by investigation group profile. Requires investigation group profile ID as parameter. */
  public readonly RESEARCH_SEEDBEDS_PROFILES_BY_INVESTIGATION_GROUP_PROFILE:string='research-seedbed-profiles/get-all-by-investigation-group-profile-id/';
  /** Endpoint to get research seedbed members. Requires research seedbed ID as parameter. */
  public readonly RESEARCH_SEEDBEDS_MEMBERS:string='research-seedbed-student-profile/';

  //----[RESEARCH SEEDBED STUDENT PROFILES]----
  /** Endpoint to get student profiles for a research seedbed profile. */
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES:string='research-seedbed-student-profile/research-seedbed-profile/';
  /** Endpoint to upload student profiles via Excel file. Requires research seedbed ID as parameter. */
  public readonly RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL:string='research-seedbed-student-profile/add-all-by-excel/';

  //----[USERS_PATH]----
  /** Endpoint for users CRUD operations. */
  public readonly USERS:string='users/';
  /** Endpoint to get the current authenticated user information. */
  public readonly USERS_ME:string='users/me';
  /** Endpoint to get Integra users. */
  public readonly USERS_INTEGRA:string='users/integra-user';
  /** Endpoint to get all registered functionary users. */
  public readonly USERS_FUNCTIONARY:string='users/all-functionaries-registered';
  /** Endpoint to get all registered student users. */
  public readonly USERS_STUDENTS:string='users/all-students-registered';
  /** Endpoint to get all registered internal users. */
  public readonly USERS_INTERNAL: string ='users/all-internal-users-registered';
  /** Endpoint to get all registered external users. */
  public readonly USERS_EXTERNAL:string='users/all-external-users-registered';
  /** Endpoint to get DIRI users. */
  public readonly USERS_DIRI:string='users/diri-users';

  //----[REPORTS]----
  /** Endpoint to generate half-year reports for investigation groups. */
  public readonly SINGLE_PERIOD_REPORTS_INVESTIGATION_GROUPS:string = 'investigation-group-profiles/generate-investigation-group-half-year-report';
  /** Endpoint to generate half-year reports for active research seedbeds. */
  readonly SINGLE_PERIOD_REPORTS_ACTIVE_RESEARCH_SEEDBEDS:string = 'investigation-group-profiles/generate-active-seedbeds-half-year-report';
  /** Endpoint to generate annual reports for investigation groups. */
  readonly CONSOLIDATE_REPORTS_INVESTIGATION_GROUPS:string = 'investigation-group-profiles/generate-investigation-group-annual-year-report';
  /** Endpoint to generate annual reports for active research seedbeds. */
  readonly CONSOLIDATE_REPORTS_ACTIVE_RESEARCH_SEEDBEDS:string = 'investigation-group-profiles/generate-active-seedbeds-annual-year-report';
  /** Endpoint to generate annual reports for research seedbed students. */
  readonly ANUAL_REPORTS_RESEARCH_SEEDBEDS_STUDENTS:string = 'research-seedbed-profiles/generate-seedbed-report';
  /** Endpoint to generate certificates for students in seedbeds. */
  readonly GENERATE_CERTIFICATES:string = 'users/student-seedbed-certificate';

  //----[LOGS]----
  /** Endpoint for action logs retrieval and management. */
  public readonly ACTION_LOGS: string = 'action-logs/';
  /** Endpoint for cronjob logs retrieval and management. */
  public readonly CRONJOB_LOGS: string = 'cronjob-logs/';
  /** Endpoint for error logs retrieval and management. */
  public readonly ERROR_LOGS: string = 'error-logs/';


  //----[COUNTRIES]----
  /** Endpoint to get all available countries. */
  public readonly COUNTRIES:string='users/all-countries';

  //----[GOOGLE]----
  /** Endpoint to get authenticated user data from Google. */
  public readonly GOOGLE_DATA:string='security/me';
  /** Endpoint to logout from Google authentication. */
  public readonly GOOGLE_LOGOUT:string='logout';
  /** Endpoint to initiate Google OAuth2 login flow. */
  public readonly GOOGLE_LOGIN:string='oauth2/authorization/google';

  /**
   * Singleton instance of the API class.
   * @private
   * @type {API}
   */
  private static instance: API;

  /**
   * Private constructor to enforce singleton pattern.
   * @private
   */
  private constructor() {}

  /**
   * Gets the singleton instance of the API class.
   * Creates a new instance if one doesn't exist.
   *
   * @public
   * @returns {API} The singleton API instance
   * @example
   * const api = API.getInstance();
   */
  public static getInstance(): API {
    if (!API.instance) {
      API.instance = new API();
    }
    return API.instance;
  }

  /**
   * Performs a GET request to the specified endpoint.
   * Automatically handles JSON and text responses.
   *
   * @public
   * @param {string} endpoint - The API endpoint to request (without base URL)
   * @param {Record<string, string>} [headers={}] - Optional custom headers to include in the request
   * @returns {Promise<any>} Response data as an array (for JSON) or text
   * @throws {Error} If the request fails
   * @example
   * const users = await API.get(API.USERS, {'API-VERSION': '1'});
   */
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


  /**
   * Performs a GET request for downloading files.
   * Returns the raw Response object for blob/file handling.
   *
   * @public
   * @param {string} endpoint - The API endpoint to download from
   * @param {Record<string, string>} [headers={}] - Optional custom headers
   * @returns {Promise<Response>} The raw fetch Response object
   * @example
   * const response = await API.download(API.GENERATE_CERTIFICATES + '123');
   * const blob = await response.blob();
   */
  public async download(endpoint: string, headers: Record<string, string> = {}): Promise<Response> {
    return await fetch(this.API_BASE_URL + `${endpoint}`, {
      method: 'GET',
      credentials: 'include',
      headers: {
        ...headers
      }
    });
  }


  /**
   * Performs a POST request to create or submit data.
   * Automatically handles JSON and FormData payloads.
   *
   * @public
   * @param {string} endpoint - The API endpoint to post to
   * @param {any} [data] - The data to send (JSON object or FormData)
   * @param {Record<string, string>} [headers={}] - Optional custom headers
   * @returns {Promise<any>} Response data or Response object for non-JSON responses
   * @throws {Error} API Error with response data and status code if request fails
   * @example
   * const newUser = await API.post(API.USERS, {name: 'John', email: 'john@example.com'});
   *
   * // With FormData
   * const formData = new FormData();
   * formData.append('file', file);
   * await API.post(API.RESEARCH_SEEDBED_STUDENT_PROFILES_UPLOAD_BY_EXCEL + '123', formData);
   */
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

  /**
   * Performs a PUT request to update existing data.
   * Handles 204 No Content responses and JSON/text responses.
   *
   * @public
   * @param {string} endpoint - The API endpoint to update
   * @param {any} data - The data to send as JSON
   * @param {Record<string, string>} [headers={}] - Optional custom headers
   * @returns {Promise<any>} Response data, empty object for 204 status
   * @throws {Error} API Error with response data and status code if request fails
   * @example
   * await API.put(API.USERS + '123', {name: 'John Updated'});
   */
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

      // Handle 204 No Content
      if (response.status === 204) {
        return {};
      }

      const contentType = response.headers.get('content-type');
      let responseData: any = null;

      if (contentType && contentType.includes('application/json')) {
        responseData = await response.json();
      } else {
        responseData = await response.text();
      }

      // If status is not ok, throw an error with the response data
      if (!response.ok) {
        const error: any = new Error('API Error');
        error.response = { data: responseData, status: response.status };
        throw error;
      }

      return responseData;
    } catch (error) {
      console.error(`Error putting to ${endpoint}:`, error);
      throw error;
    }
  }

  /**
   * Performs a DELETE request to remove data.
   * Handles 204 No Content responses and JSON/text responses.
   *
   * @public
   * @param {string} endpoint - The API endpoint to delete from
   * @param {Record<string, string>} [headers={}] - Optional custom headers
   * @returns {Promise<any>} Response data, empty object for 204 status
   * @throws {Error} API Error with response data and status code if request fails
   * @example
   * await API.delete(API.USERS + '123');
   */
  public async delete(endpoint: string, headers: Record<string, string> = {}){
    try{
      const response = await fetch(this.API_BASE_URL + `${endpoint}`, {
        method: 'DELETE',
        credentials: "include",
        headers: {
          ...headers
        },
      });

      // Handle 204 No Content
      if (response.status === 204) {
        return {};
      }

      const contentType = response.headers.get('content-type');
      let responseData: any = null;

      if (contentType && contentType.includes('application/json')) {
        responseData = await response.json();
      } else {
        responseData = await response.text();
      }

      if (!response.ok) {
        const error: any = new Error('API Error');
        error.response = { data: responseData, status: response.status };
        throw error;
      }

      return responseData;
    }catch(error){
      console.error(`Error deleting to ${endpoint}:`, error);
      throw error
    }
  }

  /**
   * Redirects the user to the Google OAuth2 login page.
   *
   * @public
   * @returns {void}
   * @example
   * API.login();
   */
  public login() {
    window.location.href = this.API_BASE_URL + this.GOOGLE_LOGIN;
  }

  /**
   * Logs out the user and redirects to the logout endpoint.
   *
   * @public
   * @returns {void}
   * @example
   * API.logout();
   */
  public logout() {
    window.location.href = this.API_BASE_URL + this.GOOGLE_LOGOUT;
  }
}

export default API.getInstance()
