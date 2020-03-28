import { PaginationLinks } from './pagination-links.model';
import { PaginationData } from './pagination-data.model';
import { ProjectModel } from './project.model';

export class ExplorerSearchResults {
    constructor(public page: ProjectModel[], 
        public pageLinks: PaginationLinks, 
        public pageData: PaginationData) {}
}