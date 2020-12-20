import {Component, OnInit, TemplateRef, ViewChild, ViewEncapsulation} from '@angular/core';
import {User} from "../../model/request/model.user";
import {Router} from "@angular/router";
import {AuthService} from "../../services/auth.service";
import {TokenStorageService} from "../../services/token-storage.service";
import {UserService} from "../../services/user.service";
import {NgbModal, ModalDismissReasons} from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class LoginComponent implements OnInit {
  user: User=new User();
  errorMessage:string;
  @ViewChild("content") private contentRef: TemplateRef<Object>;

  constructor(private modalService: NgbModal, private userService: UserService, private authService :AuthService, private tokenService: TokenStorageService, private router: Router) { }



  ngOnInit() {

  }

  login(){
    this.authService.login(this.user)
      .subscribe(data=>{
          this.tokenService.saveToken(data.token);
          this.userService.getCurrentProfile()
           .subscribe(data => {
             this.tokenService.saveUser(data);
             this.router.navigate(['/main'])
           });
        },err=>{
            this.errorMessage = "Username or password is incorrect";
            this.open(this.contentRef);
        }
      )
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }
}
