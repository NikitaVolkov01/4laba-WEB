import {Component, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {User} from "../../model/request/model.user";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {MustMatch} from "./must-match.validator";
import {AuthService} from "../../services/auth.service";
import {NgbModal} from "@ng-bootstrap/ng-bootstrap";
import {TokenStorageService} from "../../services/token-storage.service";
import {Router} from "@angular/router";
import {UserService} from "../../services/user.service";


@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['../login/login.component.scss']
})
export class RegisterComponent implements OnInit {
  @ViewChild("content") private contentRef: TemplateRef<Object>;

  registerForm: FormGroup;
  submitted = false;
  errorMessage: string;

  constructor(private userService: UserService, private formBuilder: FormBuilder, private authService: AuthService, private modalService: NgbModal, private router: Router, private tokenService: TokenStorageService) { }

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', [Validators.required, Validators.minLength(6)]],
      confirmPassword: ['', Validators.required]
    }, {
      validator: MustMatch('password', 'confirmPassword')
    });
  }

  get f() { return this.registerForm.controls; }

  register() {
    this.submitted = true;

    if (this.registerForm.invalid) {
      return;
    }

    this.authService
      .register(new User(this.registerForm.value.username, this.registerForm.value.password))
      .subscribe(data => {
        this.tokenService.saveToken(data.token);
        this.userService.getCurrentProfile()
          .subscribe(data => {
            this.tokenService.saveUser(data);
            this.router.navigate(['/main'])
          });
      }, error => {
        this.errorMessage = "User exist, try again!";
        this.open(this.contentRef);
      });
  }

  open(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title'});
  }

}
