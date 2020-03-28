export class HateosResponseModel<T> {
  constructor(public _links: any, public page: { size: number, totalElements: number, totalPages: number, number: number}, public _embedded?: T) {

  }
}