import { Pipe, PipeTransform } from "@angular/core";
import { PartModel } from '../models/part.model';

@Pipe({name: 'filter'})
export class Filter implements PipeTransform {
    
    transform(value: PartModel[], status: string): PartModel[] {
        return value.filter(value => value.status !== status)
    }
}