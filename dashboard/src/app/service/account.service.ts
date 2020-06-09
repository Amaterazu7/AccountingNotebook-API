import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpClient } from '@angular/common/http';
import { log } from 'util';
import { catchError } from 'rxjs/operators';
import { Subject } from 'rxjs/internal/Subject';
import { Observable } from 'rxjs';
import { CommonService } from './common.service';

@Injectable({providedIn: 'root'})
export class AccountService extends CommonService {
    public subjectAdjustErrorDummy = new Subject<any>();
    public readonly accountURL: string;

    constructor(private http: HttpClient) {
        super(http);
        this.accountURL = '/accountingNotebook/api';
    }

    private handlerError(err) {
        log('ERROR ::: === >>>');
        // this.hideSpinner();
        // this.showErrorSnackBar('error message');
        console.table(`An error occurred: "${err.error}"`);
        console.table(`Backend returned code [${err.status}], body was ==>`, err);
    }
}
