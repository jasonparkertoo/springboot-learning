import { Component } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {UserService} from "../user.service";
import {User} from "../user";

@Component({
  selector: 'app-user-form',
  templateUrl: './user-form.component.html',
  styleUrls: ['./user-form.component.scss']
})
export class UserFormComponent {

  user: User;

  constructor(private route: ActivatedRoute,
              private router: Router,
              private userService: UserService) {
    this.user = {"id": "", "email": "", "name": ""};
  }

  onSubmit() {
    this.userService.save(this.user).subscribe(result => this.gotoUserList())
  }

  gotoUserList() {
    return this.router.navigate(['/users']);
  }
}
