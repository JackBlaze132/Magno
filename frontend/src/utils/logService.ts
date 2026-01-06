import api from './api';

export type LogType = 'ACTION' | 'CRONJOB' | 'ERROR';

export interface LogFilters {
  userId?: string | number;
  startDate?: string;
  endDate?: string;
  date?: string;
  jobName?: string;
  status?: string;
}

class LogService {
  headers ={
    'API-VERSION': '1'
  }
  private static instance: LogService;
  private constructor() {}

  public static getInstance(): LogService {
    if (!LogService.instance) {
      LogService.instance = new LogService();
    }
    return LogService.instance;
  }

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
