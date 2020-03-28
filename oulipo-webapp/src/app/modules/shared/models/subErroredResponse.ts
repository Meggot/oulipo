export class SubErroredResponse {
  constructor(public message: string, public subErrors: SubErrors[]) {
  }


  public flattenSubErrors(): string {
    let returnString = '';
      this.subErrors.forEach(element => {
        // returnString += '[' + element.rejectedValue + '] ' + element.message + ' : ' + element.field + '\n';
        returnString += ' ' + element.message
      });
      // returnString = returnString.substring(0, returnString.length - 1);
      return returnString;
  }
}


export class SubErrors {
  constructor(public field: string, public rejectedValue: string, public message: string) {
  }
}
