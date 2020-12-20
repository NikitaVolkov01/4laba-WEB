import {Observable} from "rxjs";

export class Points {
  total: number;
  points: Point[];
}

export class Point {
  id: number;
  x: number;
  y: number;
  r: number;
  hit: boolean;
}
