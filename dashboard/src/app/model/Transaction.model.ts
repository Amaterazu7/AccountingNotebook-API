export class Transaction {
    created: string;
    amount: number;
    type: string;
    status: string;
    description: string;
    id: string;

    constructor(created: string, amount: number, type: string, status: string, description: string, id: string) {
        this.created = created;
        this.amount = amount;
        this.type = type;
        this.status = status;
        this.description = description;
        this.id = id;
    }
}