package cn.mandata.react_native_mpchart;

import android.util.Log;

import cn.mandata.react_native_mpchart.events.MPMarkerChangeEvent;

import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.data.Entry;

/**
 * Created by raininfall on 2017/3/24
 */
public class MPMarkerView extends MarkerView {
  private final Chart chart;
  private final EventDispatcher eventDispatcher;
  private float posX = 0;
  private float posY = 0;

  public MPMarkerView(Chart chart, ThemedReactContext reactContext) {
    super(reactContext, R.layout.marker_dummy);

    this.chart = chart;
    this.eventDispatcher = reactContext.getNativeModule(UIManagerModule.class).getEventDispatcher();

    chart.setMarkerView(this);
  }

  @Override
  public void refreshContent(Entry e, Highlight highlight) {
    //Do nothing, this callback is late for new position
  }

  @Override
  public int getXOffset(float xPos) {
    this.posX = xPos;
    dispatch();
    return 0;
  }

  @Override
  public int getYOffset(float yPos) {
    this.posY = yPos;
    dispatch();
    return 0;
  }

  private void dispatch() {
    eventDispatcher.dispatchEvent(new MPMarkerChangeEvent(chart.getId(), posX, posY));
  }

}
