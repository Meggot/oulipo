export class PaginationLinks {
    constructor(public first: {href: string},
        public last: {href: string},
        public next: {href: string},
        public prev: {href: string}) {}
}