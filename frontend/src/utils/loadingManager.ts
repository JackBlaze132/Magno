/**
 * Singleton LoadingManager class for tracking the loading state of multiple components.
 * Manages component loading lifecycle and provides methods to track when all components are ready.
 *
 * @class LoadingManager
 * @example
 * import LoadingManager from '@/utils/loadingManager';
 *
 * // Set total components to track
 * LoadingManager.setTotalComponents(5);
 *
 * // In each component's mounted/loaded hook
 * LoadingManager.onChildLoaded();
 *
 * // Check if all loaded
 * if (LoadingManager.allComponentsLoaded()) {
 *   console.log('All components ready!');
 * }
 */
class LoadingManager {
  /**
   * Singleton instance of the LoadingManager class.
   * @private
   * @static
   * @type {LoadingManager}
   */
  private static instance: LoadingManager;

  /**
   * Counter for the number of components that have finished loading.
   * @private
   * @type {number}
   */
  private componentsLoaded: number;

  /**
   * Total number of components expected to load.
   * @private
   * @type {number}
   */
  private totalComponents: number;

  /**
   * Initializes the LoadingManager with default values.
   * Sets componentsLoaded to 0 and totalComponents to 3.
   *
   * @public
   * @constructor
   */
  public constructor() {
    this.componentsLoaded = 0;
    this.totalComponents = 3; // Ajusta este valor según sea necesario
  }

  /**
   * Gets the singleton instance of the LoadingManager class.
   * Creates a new instance if one doesn't exist.
   *
   * @public
   * @static
   * @returns {LoadingManager} The singleton LoadingManager instance
   * @example
   * const manager = LoadingManager.getInstance();
   */
  public static getInstance(): LoadingManager {
    if (!LoadingManager.instance) {
      LoadingManager.instance = new LoadingManager();
    }
    return LoadingManager.instance;
  }

  /**
   * Sets the total number of components to track for loading.
   * Should be called before components start loading.
   *
   * @public
   * @param {number} count - The total number of components to track
   * @returns {void}
   * @example
   * LoadingManager.setTotalComponents(5);
   */
  public setTotalComponents(count: number): void {
    this.totalComponents = count;
  }

  /**
   * Resets the components loaded counter to 0.
   * Useful when reloading or reinitializing component tracking.
   *
   * @public
   * @returns {void}
   * @example
   * LoadingManager.reset();
   */
  public reset() {
    this.componentsLoaded = 0;
  }

  /**
   * Increments the loaded components counter.
   * Should be called when each child component finishes loading.
   * Logs a message when all components have loaded.
   *
   * @public
   * @returns {void}
   * @example
   * // In component's mounted or created hook
   * LoadingManager.onChildLoaded();
   */
  public onChildLoaded(): void {
    this.componentsLoaded += 1;
    if (this.componentsLoaded === this.totalComponents) {
      console.log('Todos los componentes han cargado');
      // Aquí puedes realizar cualquier acción que necesites cuando todos los componentes hayan cargado
    }
  }

  /**
   * Checks if all components have finished loading.
   *
   * @public
   * @returns {boolean} True if all components are loaded, false otherwise
   * @example
   * if (LoadingManager.allComponentsLoaded()) {
   *   // Proceed with operations that require all components
   * }
   */
  public allComponentsLoaded(): boolean {
    return this.componentsLoaded === this.totalComponents;
  }
}

export default LoadingManager.getInstance();
