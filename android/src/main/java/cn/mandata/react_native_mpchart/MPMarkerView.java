package cn.mandata.react_native_mpchart;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.charts.Chart;

import cn.mandata.react_native_mpchart.event.MPMarkerChangeEvent;
/**
 * Created by raininfall on 2017/3/24
 */
public class MPMarkerView extends MarkerView {
  /* http://stackoverflow.com/questions/11419797/why-is-float-min-value-in-java-a-positive-value */
  static final float UNSET_POS = -Float.MAX_VALUE;
  private final Chart chart;
  private final EventDispatcher eventDispatcher;
  private float xPos = UNSET_POS;
  private float yPos = UNSET_POS;

  public MPMarkerView(Chart chart, EventDispatcher eventDispatcher) {
    super(context, R.layout.marker_dummy);

    this.chart = chart;
    this.eventDispatcher = eventDispatcher;
    chart.setMarkerView(this);
  }

  @Override
  public void refreshContent(Entry e, Highlight highlight) {
    if (this.xPos == UNSET_POS && this.yPos == UNSET_POS) {
     return;
    }
    eventDispatcher.dispatchEvent(new MPMarkerChangeEvent(
      chart.getId(), posX, posY));
  }

  @Override
  public int getXOffset(float xPos) {
    this.xPos = xPos;
    return 0;
  }

  @Override
  public int getYOffset(float yPos) {
    this.yPos = yPos;
    return 0;
  }

}
