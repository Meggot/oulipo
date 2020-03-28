import { Injectable } from "@angular/core";
import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { PostboxModel } from '../../shared/models/postbox.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { map, catchError } from 'rxjs/operators';
import { of } from 'rxjs';
import { SubscriptionModel } from '../../shared/models/subscription.model';

@Injectable()
export class NotificationApi {

    headers: HttpHeaders;

    constructor(private httpClient: HttpClient,
        private sessionService: SessionService) {
        this.headers = new HttpHeaders({
            "Content-Type": 'application/json'
        })
    }

    getPostBox() {
        return this.httpClient.get(this.sessionService.hostname + "/notify/postbox", { headers: this.headers })
            .pipe(
                map((response: PostboxModel) => {
                    return new ApiResponse<PostboxModel>(
                        response,
                        200,
                        'Retrieved postbox succesfully'
                    )
                }),
                catchError((error: HttpErrorResponse) => {
                    return of(new ApiResponse<any>(
                        null,
                        error.status,
                        error.error
                    )
                    )
                })
            )
    }
    
    subscribe(entityId: number, types: string[]) {
        let subscribeForm = new FormData();
        subscribeForm.append("entityId", entityId.toString())
        subscribeForm.append("types", types.join(","))
        return this.httpClient.post(this.sessionService.hostname + "/notify/postbox/subscribe", subscribeForm)
            .pipe(
                map((response: {_embedded: {content: SubscriptionModel[]}}) => {
                    return new ApiResponse<SubscriptionModel[]>(
                        response._embedded.content,
                        200,
                        'Subscribed succesfully'
                    )
                }),
            catchError((error: HttpErrorResponse) => {
                return of (new ApiResponse<SubscriptionModel[]>(
                    null,
                    error.status,
                    error.error
                ))
            })
            )
    }
}