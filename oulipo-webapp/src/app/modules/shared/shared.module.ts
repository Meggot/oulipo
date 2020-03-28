import { NgModule } from '@angular/core';
import { ExploreItemComponentPanel } from './components/explore-item/explore-item-panel.component';
import { DropdownDirective } from './directives/dropdown.directive';
import { HighlightDirective } from './directives/highlight.directive';
import { PasswordDirective } from './directives/password.directive';
import { SearchbarDirective } from './directives/searchbar.directive';
import { CommonModule } from '@angular/common';
import { PartListItemComponent } from './components/part-list-item/part-list-item.component';
import { ProjectListItemComponent } from './components/project-list-item/project-list-item.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';
import { ReactiveProjectSearchComponent } from './components/reactive-project-search/reactive-project-search.component';
import { ReactiveAccountsSearchComponent } from './components/reactive-accounts-search/reactive-accounts-search.component';
import { ProjectDropdownComponent } from './components/project-dropdown/project-dropdown.component';
import { RouterModule } from '@angular/router';
import { MessageItemComponent } from 'src/app/modules/inbox/components/message-item/message-item.component';
import { DiffMatchPatchModule } from 'ng-diff-match-patch';
import { GroupListItemComponent } from './components/group-list-item/group-list-item.component';
import { AccountMembershipGroupListItemComponent } from './components/account-membership-group-list-item/account-membership-group-list-item.component';
import { TagsInputComponent } from './components/tags-input/tags-input.component';
import { DropUpDirective } from './directives/dropup-directive';
import { ReactiveGroupsSearchComponent } from './components/reactive-groups-search/reactive-groups-search.component';
import { Filter } from './pipes/filter.pipe';

@NgModule({
    declarations: [
        ExploreItemComponentPanel,
        PartListItemComponent,
        ProjectListItemComponent,
        DropdownDirective,
        HighlightDirective,
        PasswordDirective,
        SearchbarDirective,
        DropUpDirective,
        ReactiveProjectSearchComponent,
        ReactiveAccountsSearchComponent,
        ReactiveGroupsSearchComponent,
        ProjectDropdownComponent,
        MessageItemComponent,
        GroupListItemComponent,
        AccountMembershipGroupListItemComponent,
        TagsInputComponent,
        ReactiveGroupsSearchComponent,
        Filter
        ],
    imports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        RouterModule,
        DiffMatchPatchModule
    ],
    exports: [
        CommonModule,
        ReactiveFormsModule,
        FormsModule,
        HttpClientModule,
        RouterModule,
        DiffMatchPatchModule,
        ExploreItemComponentPanel,
        PartListItemComponent,
        ProjectListItemComponent,
        DropdownDirective,
        HighlightDirective,
        PasswordDirective,
        SearchbarDirective,
        DropUpDirective,
        ReactiveProjectSearchComponent,
        ReactiveAccountsSearchComponent,
        ReactiveGroupsSearchComponent,
        ProjectDropdownComponent,
        MessageItemComponent,
        GroupListItemComponent,
        AccountMembershipGroupListItemComponent,
        TagsInputComponent,
        ReactiveGroupsSearchComponent,
        Filter
        ]
})
export class SharedModule {

}