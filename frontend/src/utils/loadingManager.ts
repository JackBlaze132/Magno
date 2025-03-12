class LoadingManager {
  private static instance: LoadingManager;
  private componentsLoaded: number;
  private totalComponents: number;

  public constructor() {
    this.componentsLoaded = 0;
    this.totalComponents = 3; // Ajusta este valor según sea necesario
  }

  public static getInstance(): LoadingManager {
    if (!LoadingManager.instance) {
      LoadingManager.instance = new LoadingManager();
    }
    return LoadingManager.instance;
  }

  public setTotalComponents(count: number): void {
    this.totalComponents = count;
  }

  public reset() {
    this.componentsLoaded = 0;
  }

  public onChildLoaded(): void {
    this.componentsLoaded += 1;
    if (this.componentsLoaded === this.totalComponents) {
      console.log('Todos los componentes han cargado');
      // Aquí puedes realizar cualquier acción que necesites cuando todos los componentes hayan cargado
    }
  }

  public allComponentsLoaded(): boolean {
    return this.componentsLoaded === this.totalComponents;
  }
}

export default LoadingManager.getInstance();
