package cn.mandata.react_native_mpchart.event;

import com.facebook.react.bridge.WriteAble;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.uimanager.events.Event;
import com.facebook.react.uimanager.events.RCTEventEmitter;

public class MPMarkerChangeEvent extends Event<MPMarkerChangeEvent> {

  public static final String EVENT_NAME = "topMarkerChange";
  private final double posX;
  private final double posY;

  public MPMarkerChangeEvent(int viewId, double posX, double posY) {
    super(viewId);
    this.posX = posX;
    this.posY = posY;
  }

  double getPosX() {
    return posX;
  }

  double getPoxY() {
    return posY;
  }

  @Override
  public String getEventName() {
    return EVENT_NAME;
  }

  @Override
  public short getCoalescingKey() {
    // All events for a given view can be coalesced.
    return 0;
  }

  @Override
  public void dispatch(RCTEventEmitter rctEventEmitter) {
    rctEventEmitter.receiveEvent(getViewTag(), getEventName(), serializeEventData());
  }

  private WritableMap serializeEventData() {
    WritableMap eventData = Arguments.createMap();
    eventData.putDouble("posX", getPosX());
    eventData.putDouble("posY", getPosY());
    return eventData;
  }

}
