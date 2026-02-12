/**
 * Singleton Formatter class for formatting and transforming data for display purposes.
 * Provides utility methods for formatting booleans, dates, text cases, and other data types.
 *
 * @class Formatter
 * @example
 * import Formatter from '@/utils/formatter';
 *
 * // Format external user status
 * const status = Formatter.externalFormatter(true); // Returns 'externo'
 *
 * // Format date
 * const formattedDate = Formatter.dateFormatter('2024-01-15');
 */
class Formatter{

  /**
   * Singleton instance of the Formatter class.
   * @private
   * @type {Formatter}
   */
  private static instance: Formatter;

  /**
   * Public constructor for the Formatter class.
   * @public
   */
  public constructor(){}

  /**
   * Gets the singleton instance of the Formatter class.
   * Creates a new instance if one doesn't exist.
   *
   * @public
   * @returns {Formatter} The singleton Formatter instance
   * @example
   * const formatter = Formatter.getInstance();
   */
  public static getInstance(): Formatter{
    if (!Formatter.instance) {
      Formatter.instance = new Formatter();
    }
    return Formatter.instance;
  }
  /**
   * Formats a boolean value to indicate whether a user is external or internal.
   *
   * @public
   * @param {boolean} isExternalUser - Boolean indicating if user is external
   * @returns {string | null} 'externo' if true, 'interno' if false, null if input is null/undefined
   * @example
   * Formatter.externalFormatter(true);  // Returns 'externo'
   * Formatter.externalFormatter(false); // Returns 'interno'
   * Formatter.externalFormatter(null);  // Returns null
   */
  public externalFormatter(isExternalUser: boolean){
    if (isExternalUser === null || isExternalUser === undefined) {
      return null;
    }
    return isExternalUser? 'externo' : 'interno';
  }

  /**
   * Formats gender information.
   * Implementation pending
   * @public
   */
  public genderFormatter(){

  }

  /**
   * Formats a boolean value to indicate whether an academic period is active.
   *
   * @public
   * @param {boolean} isActive - Boolean indicating if period is active
   * @returns {string} 'Activo' if true, 'inactivo' if false
   * @example
   * Formatter.periodActivityFormatter(true);  // Returns 'Activo'
   * Formatter.periodActivityFormatter(false); // Returns 'inactivo'
   */
  public periodActivityFormatter(isActive: boolean){
    return isActive? 'Activo' : 'inactivo';
  }

  /**
   * Formats a date string to a Date object with timezone adjustment.
   * Adds 'T05:00:00.000Z' to prevent the date from displaying as the previous day.
   *
   * @public
   * @param {string} date - Date string in format 'YYYY-MM-DD'
   * @returns {Date} Date object with timezone correction
   * @example
   * Formatter.dateFormatter('2024-01-15'); // Returns Date object for 2024-01-15
   */
  public dateFormatter(date: string){
    //'T05:00:00.000Z' is added to the date to avoid the date to be displayed as the previous day
    return new Date(date + 'T05:00:00.000Z');
  }

  /**
   * Formats a boolean value to display a star emoji for leaders.
   *
   * @public
   * @param {boolean} isLeader - Boolean indicating if user is a leader
   * @returns {string} '⭐' if true, empty string if false
   * @example
   * Formatter.leaderFormatter(true);  // Returns '⭐'
   * Formatter.leaderFormatter(false); // Returns ''
   */
  public leaderFormatter(isLeader: boolean){
    return isLeader? '⭐' : '';
  }

  /**
   * Converts snake_case text to Title Case.
   * Each word is capitalized after splitting by underscores.
   *
   * @public
   * @param {string} text - Text in snake_case format
   * @returns {string} Text in Title Case format, empty string if input is falsy
   * @example
   * Formatter.snakeCaseToTitleCase('hello_world');        // Returns 'Hello World'
   * Formatter.snakeCaseToTitleCase('USER_PROFILE_DATA'); // Returns 'User Profile Data'
   */
  public snakeCaseToTitleCase(text: string): string {
    if (!text) return '';

    return text
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  /**
   * Converts snake_case text to UPPER CASE with spaces.
   * All letters are converted to uppercase and underscores replaced with spaces.
   *
   * @public
   * @param {string} text - Text in snake_case format
   * @returns {string} Text in UPPER CASE format, empty string if input is falsy
   * @example
   * Formatter.snakeCaseToUpperCase('hello_world');  // Returns 'HELLO WORLD'
   * Formatter.snakeCaseToUpperCase('user_profile'); // Returns 'USER PROFILE'
   */
  public snakeCaseToUpperCase(text: string): string {
    if (!text) return '';

    return text
      .toUpperCase()
      .split('_')
      .join(' ');
  }

  /**
   * Converts snake_case text to Natural Title Case following Spanish grammar rules.
   * The first word is always capitalized, while common Spanish prepositions and articles
   * remain lowercase unless they begin the string.
   *
   * @public
   * @param {string} text - Text in snake_case format
   * @returns {string} Text in Natural Title Case format, empty string if input is falsy
   * @example
   * Formatter.snakeCaseToNaturalTitleCase('linea_de_investigacion');
   * // Returns 'Linea de Investigacion'
   *
   * Formatter.snakeCaseToNaturalTitleCase('estudiante_con_beca');
   * // Returns 'Estudiante con Beca'
   */
  public snakeCaseToNaturalTitleCase(text: string): string {
    if (!text) return '';

    // Words that should remain lowercase in natural title case
    const lowercaseWords = new Set(['de', 'con', 'para', 'en', 'el', 'la', 'los', 'las', 'un', 'una', 'y', 'o', 'a', 'del', 'al']);

    return text
      .toLowerCase()
      .split('_')
      .map((word, index) => {
        // Always capitalize the first word
        if (index === 0) {
          return word.charAt(0).toUpperCase() + word.slice(1);
        }
        // Keep certain words lowercase, capitalize others
        if (lowercaseWords.has(word)) {
          return word;
        }
        return word.charAt(0).toUpperCase() + word.slice(1);
      })
      .join(' ');
  }
}

export default Formatter.getInstance();


