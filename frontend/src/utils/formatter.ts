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

  public leaderFormatter(isLeader: boolean){
    return isLeader? '⭐' : '';
  }

  public snakeCaseToTitleCase(text: string): string {
    if (!text) return '';

    return text
      .toLowerCase()
      .split('_')
      .map(word => word.charAt(0).toUpperCase() + word.slice(1))
      .join(' ');
  }

  public snakeCaseToUpperCase(text: string): string {
    if (!text) return '';

    return text
      .toUpperCase()
      .split('_')
      .join(' ');
  }

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


