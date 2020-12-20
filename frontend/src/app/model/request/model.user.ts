export class User {
  username: string="";
  password: string="";

  constructor(username = null, password = null){
    this.username = username;
    this.password = password;
  }
}
