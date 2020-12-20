import {Component, EventEmitter, OnInit, Output} from '@angular/core';
import {Observable} from "rxjs";
import {Point} from "../../model/response/model.point";
import {PointService} from "../../services/point.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-history',
  templateUrl: './history.component.html',
  styleUrls: ['./history.component.scss']
})
export class HistoryComponent implements OnInit {
  points: Point[];
  total: number;
  page = 1;
  itemsPerPage = 8;

  @Output() onDeleteEvent: EventEmitter<any> = new EventEmitter();


  constructor(private pointService: PointService, private router: ActivatedRoute) {

  }

  ngOnInit(): void {
    this.pointService.getPoints().subscribe(data =>{
      this.points = data.points;
      this.total = data.total;
      this.hideLoader();

      if(this.total == 0) this.statusSetText("No data");
      else {
        this.hideStatus();

        this.router.params.subscribe(params => {
          if(params["id"] != null) {
            setTimeout(()=> this.flashRow(params["id"]), 500);
          }
        });
      }

    }, err => {
      this.hideLoader();
      this.statusSetText("Failed to load");
    });
  }

  refresh(data){
    this.points = data.points;
    this.total = data.total;
    this.hideLoader();

    if(this.total == 0) this.statusSetText("No data");
    else this.hideStatus();
  }


  hideLoader() {
    document.getElementById('m-loading').style.display = 'none';
  }

  hideStatus() {
    document.getElementById('status-text').style.display = 'none';
  }

  showStatus(){
    document.getElementById('status-text').style.display = 'block';
  }

  statusSetText(message){
    document.getElementById('status-text').innerHTML = message;
  }

  flashRow(id){
    document.getElementById(id).className = "flash";
  }

  onClear() {
    this.showStatus();
    this.statusSetText("No data");

    this.pointService.removePoints().subscribe(data => console.log(data));
    this.points = [];
    this.total = 0;

    this.onDeleteEvent.emit(null);
  }
}
