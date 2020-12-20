import {
  Component,
  ContentChild,
  ContentChildren,
  ElementRef,
  EventEmitter,
  Host,
  OnInit, Output,
  Renderer2,
  ViewChild
} from '@angular/core';
import {PointService} from "../../../services/point.service";
import {PointReq} from "../../../model/request/req.point";
import {Point} from "../../../model/response/model.point";

@Component({
  selector: 'app-graph',
  templateUrl: './graph.component.html',
  styleUrls: ['./graph.component.scss']
})
export class GraphComponent implements OnInit {
  @ViewChild('graph_plate') plate:ElementRef<HTMLElement> = null;
  @ViewChild('graph_svg') svg:ElementRef<HTMLElement> = null;

  rawXValue: any;
  rawYValue: any;
  rawRValue: any;

  XValue: any;
  YValue: any;

  xValues = [-2, -1.5, -1, -0.5, 0, 0.5, 1, 1.5, 2];

  @Output() onGraphEvent: EventEmitter<any> = new EventEmitter();

  constructor(private rd: Renderer2, private pointService: PointService) {
  }

  loadPoints(points: Point[]){
    for(const value of points){
      this.setCrossings2(value);
    }
  }

  ngOnInit(): void {
  }


  onGraphClick() {
    if(this.rawXValue != null && this.rawYValue != null){
      this.XValue = this.rawXValue;
      this.YValue = this.rawYValue;

      this.setCrossings();
      this.onGraphEvent.emit(new PointReq(this.getXValue(), this.getYValue(), this.getRValue()));
    }
  }

  onGraphEnter($event: MouseEvent) {
    if(!this.checkRValue()) this.setInvalidGraph($event.target);
  }

  onGraphLeave($event: MouseEvent) {
    this.resetRawValues();
    this.resetValidGraph($event.target);
  }


  onGraphMove($event: MouseEvent) {
    const width = this.svg.nativeElement.getBoundingClientRect().width;
    const height = this.svg.nativeElement.getBoundingClientRect().height;

    this.setRawXValue( ($event.clientX - this.plate.nativeElement.getBoundingClientRect().left ) * 400 / width );
    this.setRawYValue( ($event.clientY - this.plate.nativeElement.getBoundingClientRect().top ) * 400 / height );
  }


  getXValue(){
    return this.XValue;
  }

  getYValue(){
    return this.YValue;
  }

  getRValue(){
    return this.rawRValue;
  }

  checkRValue(): boolean {
    return !(this.getRValue() == null || this.getRValue() <= 0);

  }

  setInvalidGraph($target){
    this.rd.addClass($target, "is-invalid")
  }

  resetValidGraph($target){
    this.rd.removeClass($target, "is-invalid")
  }

  createElement(name){
    return document.createElementNS('http://www.w3.org/2000/svg', name);
  }

  setRawXValue(rawXValue){
    if (!this.checkRValue() || rawXValue == null) return;

    const x = this.svg.nativeElement.getElementsByClassName("dotted-raw-x").item(0);
    if(x != null) this.rd.removeChild(this.svg.nativeElement, x);

    const xValue = (rawXValue - 200) * this.getRValue() / 160;

    for(let value of this.xValues){
      if (value - 0.25 < xValue && value + 0.25 >= xValue) {
        this.rawXValue = value;
        const line = this.createElement('line');
        this.rd.setAttribute(line, "x1", String(200 + value * 160 / this.getRValue()));
        this.rd.setAttribute(line, "x2", String(200 + value * 160 / this.getRValue()));
        this.rd.setAttribute(line, "y1", "0");
        this.rd.setAttribute(line, "y2", "400");
        this.rd.addClass(line, 'dotted-raw-x');

        this.rd.appendChild(this.svg.nativeElement, line);
      }
    }
  }

  setRawYValue(rawYValue){
    if (!this.checkRValue() || rawYValue == null) return;

    const el = this.svg.nativeElement.getElementsByClassName("dotted-raw-y").item(0);
    if(el != null) this.rd.removeChild(this.svg.nativeElement, el);

    this.rawYValue = (200 - rawYValue) * this.getRValue() / 160;


    const line = this.createElement('line');
    this.rd.setAttribute(line, "x1", "0");
    this.rd.setAttribute(line, "x2", "400");
    this.rd.setAttribute(line, "y1", rawYValue);
    this.rd.setAttribute(line, "y2", rawYValue);
    this.rd.addClass(line, 'dotted-raw-y');

    this.rd.appendChild(this.svg.nativeElement, line);

  }


  setCrossings(){
    const cr = this.svg.nativeElement.getElementsByClassName("crossing").item(0);
    if(cr != null) this.rd.removeChild(this.svg.nativeElement, cr);

    if (this.getXValue() == null || this.getYValue() == null || !this.checkRValue()) return;
    const yPosition = 200 - this.getYValue() * 160 / this.getRValue();

    const circle = this.createElement('circle');
    this.rd.setAttribute(circle, "cx", String(200 + this.getXValue() * 160 / this.getRValue()));
    this.rd.setAttribute(circle, "cy", String(yPosition));
    this.rd.setAttribute(circle, "r", "3");
    this.rd.addClass(circle, 'crossing');

    this.rd.appendChild(this.svg.nativeElement, circle);
  }


  setCrossings2(point: Point){
    if (point.x == null || point.y == null || point.r == null) return;
    const yPosition = 200 - point.y * 160 / point.r;

    const circle = this.createElement('circle');
    this.rd.setAttribute(circle, "cx", String(200 + point.x * 160 / point.r));
    this.rd.setAttribute(circle, "cy", String(yPosition));
    this.rd.setAttribute(circle, "r", "3");
    this.rd.addClass(circle, point.hit ? 'crossing-green' : 'crossing-red' );

    this.rd.appendChild(this.svg.nativeElement, circle);
  }

  removeAllPoints(){
    const cr_r = [].slice.call(this.svg.nativeElement.getElementsByClassName("crossing-green"));
    const cr_g = [].slice.call(this.svg.nativeElement.getElementsByClassName("crossing-red"));

    if(cr_r.length != 0) {
      for (let i = 0; i < cr_r.length; i++) {
        this.rd.removeChild(this.svg.nativeElement, cr_r[i]);
      }
    }

    if(cr_g.length != 0) {
      for (let i = 0; i < cr_g.length; i++) {
        this.rd.removeChild(this.svg.nativeElement, cr_g[i]);
      }
    }
  }


  resetRawValues(){
    const x = this.svg.nativeElement.getElementsByClassName("dotted-raw-x").item(0);
    if(x != null) this.rd.removeChild(this.svg.nativeElement, x);

    const y = this.svg.nativeElement.getElementsByClassName("dotted-raw-y").item(0);
    if(y != null) this.rd.removeChild(this.svg.nativeElement, y);

    // this.rawXValue = null;
    // this.rawYValue = null;
  }

}
