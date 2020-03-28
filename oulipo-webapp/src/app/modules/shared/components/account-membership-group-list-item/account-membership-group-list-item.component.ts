import { Component, OnInit, Input } from '@angular/core';
import { AccountGroupMembership } from '../../models/account-group-membership.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-account-membership-group-list-item',
  templateUrl: './account-membership-group-list-item.component.html',
  styleUrls: ['./account-membership-group-list-item.component.css']
})
export class AccountMembershipGroupListItemComponent implements OnInit {

  @Input()
  membership: AccountGroupMembership;

  constructor(private router: Router) { }

  ngOnInit() {
  }

  navigateToGroupId() {
    this.router.navigate(['groups',this.membership.groupId])
  }

}
