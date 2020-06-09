export class Page {
    public content: Array<any> = [];
    public totalPages: number;
    public totalElements: number;
    public countRows: number;
    public pageSize: number;
    public pageNumber: number;

    public constructor(init?: Partial<Page>) {
        Object.assign(this, init);
    }
}
