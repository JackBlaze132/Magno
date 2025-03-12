class Formatter{

  private static instance: Formatter;

  public constructor(){}

  public static getInstance(): Formatter{
    if (!Formatter.instance) {
      Formatter.instance = new Formatter();
    }
    return Formatter.instance;
  }
  public externalFormatter(isExternalUser: boolean){
    return isExternalUser? 'externo' : 'interno';
  }

  public genderFormatter(){

  }

  public periodActivityFormatter(isActive: boolean){
    return isActive? 'Activo' : 'inactivo';
  }
}

export default Formatter.getInstance();




