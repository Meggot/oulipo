export class MatrixModel {
  constructor(public entityId: number,
              public metricCounts: { metricType: string, metricCount: number }[]) {
  }
}
