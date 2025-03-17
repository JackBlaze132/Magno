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

  public dateFormatter(date: string){
    //'T05:00:00.000Z' is added to the date to avoid the date to be displayed as the previous day
    return new Date(date + 'T05:00:00.000Z');
  }
}

export default Formatter.getInstance();




