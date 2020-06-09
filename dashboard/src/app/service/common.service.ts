import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, Subject } from 'rxjs';
import { Page } from '../model/page.model';

@Injectable({
    providedIn: 'root'
})
export class CommonService {
    public readonly springbootUrl: string;
    public readonly serviceFlaskUrl: string;

    constructor(public httpClient: HttpClient) {
        this.springbootUrl = 'http://localhost:8080';
        this.serviceFlaskUrl = 'http://localhost:8077';
    }

    public hasEntitlement(entitlement_id: number): boolean {
        return true;
    }

    public save(controller: string, entity: any): Observable<any> {
        const temporaryAccessSubject = new Subject();

        this.httpClient.post<any>(`${this.springbootUrl}${controller}`, entity).subscribe(response => {
            temporaryAccessSubject.next(response);
        });
        return temporaryAccessSubject.asObservable();
    }

    public update(controller: string, entity: any): Observable<any> {
        const temporaryAccessSubject = new Subject();

        this.httpClient.put<any>(`${this.springbootUrl}${controller}`, entity).subscribe(response => {
            temporaryAccessSubject.next(response);
        });
        return temporaryAccessSubject.asObservable();
    }

    public getById(controller: string, id: any): Observable<any> {
        const temporaryAccessSubjectPage = new Subject();

        this.httpClient.get<any>( `${this.springbootUrl}${controller}/${id}`).subscribe(response => {
            temporaryAccessSubjectPage.next(response);
        });
        return temporaryAccessSubjectPage.asObservable();
    }

    public getAll(controller: string): Observable<any[]> {
        const temporaryAccessSubjectArray = new Subject<Array<any>>();
        this.httpClient.get<any>(`${this.springbootUrl}${controller}`).subscribe(response => {
            temporaryAccessSubjectArray.next(response);
        });
        return temporaryAccessSubjectArray.asObservable();
    }

    public getAllPaginatedFiltered(controller: string, filter: any): Observable<Page> {
        const temporaryAccessSubjectPage = new Subject<Page>();
        this.httpClient.post<any>(`${this.springbootUrl}${controller}`, filter).subscribe(response => {
            temporaryAccessSubjectPage.next(response);
        });
        return temporaryAccessSubjectPage.asObservable();
    }
}
