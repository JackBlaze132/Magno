import api from './api';

/**
 * Type definition for the different types of logs available in the system.
 * @typedef {'ACTION' | 'CRONJOB' | 'ERROR'} LogType
 */
export type LogType = 'ACTION' | 'CRONJOB' | 'ERROR';

/**
 * Interface for filtering log queries.
 * All properties are optional and can be combined based on the log type.
 *
 * @interface LogFilters
 * @property {string | number} [userId] - Filter logs by user ID (ACTION and ERROR logs)
 * @property {string} [startDate] - Start date for date range or older-than filters (ISO format)
 * @property {string} [endDate] - End date for date range filters (ISO format)
 * @property {string} [date] - Alternative date parameter for older-than filters
 * @property {string} [jobName] - Filter CRONJOB logs by job name
 * @property {string} [status] - Filter CRONJOB logs by status ('SUCCESS' or 'FAILURE')
 * @example
 * const filters: LogFilters = {
 *   userId: '123',
 *   startDate: '2024-01-01T00:00:00',
 *   endDate: '2024-12-31T23:59:59'
 * };
 */
export interface LogFilters {
  userId?: string | number;
  startDate?: string;
  endDate?: string;
  date?: string;
  jobName?: string;
  status?: string;
}

/**
 * Singleton LogService class for fetching and managing application logs.
 * Provides methods to retrieve ACTION, CRONJOB, and ERROR logs with various filtering options.
 *
 * @class LogService
 * @example
 * import LogService from '@/utils/logService';
 *
 * // Fetch action logs by user ID
 * const logs = await LogService.fetchLogs('ACTION', { userId: '123' });
 *
 * // Fetch cronjob logs by date range
 * const cronLogs = await LogService.fetchLogs('CRONJOB', {
 *   startDate: '2024-01-01T00:00:00',
 *   endDate: '2024-12-31T23:59:59'
 * });
 */
class LogService {
  /**
   * Default headers for all log API requests.
   * @private
   * @type {Object}
   * @property {string} API-VERSION - API version header set to '1'
   */
  headers ={
    'API-VERSION': '1'
  }

  /**
   * Singleton instance of the LogService class.
   * @private
   * @static
   * @type {LogService}
   */
  private static instance: LogService;

  /**
   * Private constructor to enforce singleton pattern.
   * @private
   */
  private constructor() {}

  /**
   * Gets the singleton instance of the LogService class.
   * Creates a new instance if one doesn't exist.
   *
   * @public
   * @static
   * @returns {LogService} The singleton LogService instance
   * @example
   * const service = LogService.getInstance();
   */
  public static getInstance(): LogService {
    if (!LogService.instance) {
      LogService.instance = new LogService();
    }
    return LogService.instance;
  }

  /**
   * Fetches logs based on type and optional filters.
   * Constructs the appropriate endpoint based on log type and filter parameters.
   *
   * @public
   * @async
   * @param {LogType} type - The type of logs to fetch ('ACTION', 'CRONJOB', or 'ERROR')
   * @param {LogFilters} [filters={}] - Optional filters to apply to the log query
   * @returns {Promise<any>} Array of log entries matching the criteria
   * @throws {Error} If the API request fails
   *
   * @example
   * // Fetch all action logs
   * const actionLogs = await LogService.fetchLogs('ACTION');
   *
   * @example
   * // Fetch action logs by user ID
   * const userLogs = await LogService.fetchLogs('ACTION', { userId: '123' });
   *
   * @example
   * // Fetch cronjob logs by status
   * const failedJobs = await LogService.fetchLogs('CRONJOB', { status: 'FAILURE' });
   *
   * @example
   * // Fetch error logs by date range
   * const errorLogs = await LogService.fetchLogs('ERROR', {
   *   startDate: '2024-01-01T00:00:00',
   *   endDate: '2024-01-31T23:59:59'
   * });
   */
  public async fetchLogs(type: LogType, filters: LogFilters = {}) {
    let endpoint = '';

    switch (type) {
      case 'ACTION':
        endpoint = api.ACTION_LOGS;
        if (filters.userId) {
          endpoint += `by-user-id?userId=${filters.userId}`;
        } else if (filters.startDate && filters.endDate) {
          endpoint += `by-date-range?start=${filters.startDate}&end=${filters.endDate}`;
        } else if (filters.startDate || filters.date) {
          endpoint += `older-than?date=${filters.startDate || filters.date}`;
        }
        break;

      case 'CRONJOB':
        endpoint = api.CRONJOB_LOGS;
        if (filters.jobName) {
          endpoint += `job/${filters.jobName}`;
        } else if (filters.status) {
          endpoint += `status/${filters.status}`;
        } else if (filters.startDate && filters.endDate) {
          endpoint += `date-range?start=${filters.startDate}&end=${filters.endDate}`;
        } else if (filters.startDate) {
          endpoint += `older-than?date=${filters.startDate}`;
        }
        break;

      case 'ERROR':
        endpoint = api.ERROR_LOGS;
        if (filters.userId) {
          endpoint += `by-user-id?userId=${filters.userId}`;
        } else if (filters.startDate && filters.endDate) {
          endpoint += `by-date-range?start=${filters.startDate}&end=${filters.endDate}`;
        } else if (filters.startDate || filters.date) {
          endpoint += `older-than?date=${filters.startDate || filters.date}`;
        }
        break;
    }

    return await api.get(endpoint, this.headers);
  }
}

export default LogService.getInstance();
