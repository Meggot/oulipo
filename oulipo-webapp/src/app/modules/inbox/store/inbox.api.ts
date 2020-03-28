import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { SessionService } from 'src/app/services/session.service';
import { MessageModel } from '../../shared/models/message.model';
import { ApiResponse } from '../../shared/models/api-response.model';
import { map, catchError } from 'rxjs/operators';
import { of, Observable } from 'rxjs';
import { Injectable } from '@angular/core';
import { AccountModel } from '../../shared/models/account.model';

@Injectable()
export class InboxApi {
     headers: HttpHeaders;

    constructor(private http: HttpClient,
        private sessionService: SessionService) {
            this.headers = new HttpHeaders({
                'Content-Type': 'application/json'
            });
        }


    sendMessage(to: string, content: string): Observable<ApiResponse<MessageModel>> {
        const form = new FormData();
        form.append('value', content);
        return this.http.post(this.sessionService.hostname + "/users/messages/user/" + to + "/message",form)
        .pipe(
            map((response: MessageModel) => {
                return new ApiResponse<MessageModel>(
                    response,
                    200,
                    'Sent Message Successfully'
                )
            },
            catchError((error: HttpErrorResponse) => {
                return of(new ApiResponse<MessageModel>(
                    null,
                    error.status,
                    'Failure to send a message'
                ))
            })
        ))
    }

    refreshInbox() {
        return this.http.get(this.sessionService.hostname + "/users/accounts/thisAccount", { headers: this.headers })
        .pipe(
            map((response: AccountModel) => {
                return new ApiResponse<AccountModel>(
                    response,
                    200,
                    'This Account loaded successfully'
                )
            }),
            catchError((error: HttpErrorResponse) => {
                return of(new ApiResponse<AccountModel>(
                    null,
                    error.status,
                    'This Account failed to load'
                ))
            }
            ))
    }
}