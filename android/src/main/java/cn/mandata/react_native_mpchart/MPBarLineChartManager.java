package cn.mandata.react_native_mpchart;

import android.graphics.Color;
import android.util.Log;

import cn.mandata.react_native_mpchart.events.MPMarkerChangeEvent;

import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.SoftAssertions;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.annotations.ReactProp;
import com.facebook.react.uimanager.SimpleViewManager;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerModule;
import com.facebook.react.uimanager.events.EventDispatcher;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.Random;
import java.util.Map;

import javax.annotation.Nullable;

/**
 * Created by Administrator on 2015/11/6.
 */
public class MPBarLineChartManager extends SimpleViewManager<BarLineChartBase> {
    private String CLASS_NAME="MPBarLineChart";

    @Override
    public String getName() {
        return this.CLASS_NAME;
    }

    @Override
    protected BarLineChartBase createViewInstance(ThemedReactContext reactContext) {
      SoftAssertions.assertCondition(
        false,
        "MPBarLineChartManager is father class, should not create instance");

      return null;
    }

    @Override
    protected void addEventEmitters(ThemedReactContext reactContext, BarLineChartBase chart) {
      new MPChartSelectionEventListener(chart);
      new MPMarkerView(chart, reactContext);
    }

    @Override
    public @Nullable Map getExportedCustomDirectEventTypeConstants() {
      return MapBuilder.of(
        MPMarkerChangeEvent.EVENT_NAME, MapBuilder.of("registrationName", "onMarkerChange")
      );
    }

    @ReactProp(name="touchEnabled",defaultBoolean = true)
    public void setTouchEnabled(BarLineChartBase chart,boolean enable){
        chart.setTouchEnabled(enable);
    }
    @ReactProp(name="dragEnabled",defaultBoolean = true)
    public  void  setDragEnabled(BarLineChartBase chart,boolean enable){
        chart.setDragEnabled(enable);
    }
    @ReactProp(name="scaleEnabled",defaultBoolean = true)
    public  void  setScaleEnabled(BarLineChartBase chart,boolean enable){
        chart.setScaleEnabled(enable);
    }
    @ReactProp(name="scaleXEnabled",defaultBoolean = true)
    public  void  setScaleXEnabled(BarLineChartBase chart,boolean enable){
        chart.setScaleXEnabled(enable);
    }
    @ReactProp(name="scaleYEnabled",defaultBoolean = true)
    public  void  setScaleYEnabled(BarLineChartBase chart,boolean enable){
        chart.setScaleYEnabled(enable);
    }
    @ReactProp(name="pinchZoom",defaultBoolean = true)
    public  void  setPinchZoom(BarLineChartBase chart,boolean enable){
        chart.setPinchZoom(enable);
    }

    @ReactProp(name="doubleTapToZoomEnabled",defaultBoolean = true)
    public  void  setDoubleTapToZoomEnabled(BarLineChartBase chart,boolean enable){
        chart.setDoubleTapToZoomEnabled(enable);
    }

    @ReactProp(name="highlightPerDragEnabled",defaultBoolean = true)
    public  void  setHighlightPerDragEnabled(BarLineChartBase chart,boolean enable){
        chart.setHighlightPerDragEnabled(enable);
    }
    @ReactProp(name="highlightPerTapEnabled",defaultBoolean = true)
    public  void  setHighlightPerTapEnabled(BarLineChartBase chart,boolean enable){
        chart.setHighlightPerTapEnabled(enable);
    }
    @ReactProp(name="dragDecelerationEnabled",defaultBoolean = true)
    public  void  setDragDecelerationEnabled(BarLineChartBase chart,boolean enable){
        chart.setDragDecelerationEnabled(enable);
    }

    @ReactProp(name="dragDecelerationFrictionCoef",defaultFloat = 0.50f)
    public  void  setDragDecelerationFrictionCoef(BarLineChartBase chart,float v){
        chart.setDragDecelerationFrictionCoef(v);
    }
    @ReactProp(name="maxVisibleValueCount",defaultInt = 50)
    public  void  setMaxVisibleValueCount(BarLineChartBase chart,int v){
        chart.setMaxVisibleValueCount(v);
    }
    //{x:{LineColor:"",LineWidth:"",TextColor:"",TextSize:""},y:{}}
    @ReactProp(name="limitLine")
    public  void  setLimitLine(BarLineChartBase chart,ReadableMap v){
        if(v.getMap("x")!=null){
            setAxisLimit(chart.getXAxis(), v.getMap("x"));
        }
        if(v.getMap("y")!=null){
            if(chart.getAxisLeft()!=null) {
                setAxisLimit(chart.getAxisLeft(), v.getMap("y"));
            }
            else if(chart.getAxisRight()!=null){
                setAxisLimit(chart.getAxisRight(), v.getMap("y"));
            }
        }
    }
    private void setAxisLimit(AxisBase axis,ReadableMap x){
        String xLabel=x.getString("Label");
        float xLimit=(float)x.getDouble("Limit");
        String xLineColor=x.getString("LineColor");
        String xTextColor=x.getString("TextColor");
        float xTextSize=(float)x.getDouble("TextSize");
        float LineWidth=(float)x.getDouble("LineWidth");

        LimitLine ll = new LimitLine(xLimit, xLabel);
        ll.setLineColor(Color.parseColor(xLineColor));
        ll.setLineWidth(LineWidth);
        ll.setTextColor(Color.parseColor(xTextColor));
        ll.setTextSize(xTextSize);
        axis.addLimitLine(ll);
    }
    //{x:{},y:{}}
    @ReactProp(name="xAxis")
    public  void  setXAxis(BarLineChartBase chart,ReadableMap v){
        XAxis x= chart.getXAxis();
        setAxisInfo(x, v);
        setXAxisInfo(x, v);
    }
    @ReactProp(name="yAxisLeft")
    public  void  setYAxisLeft(BarLineChartBase chart,ReadableMap v){
        AxisBase x= chart.getAxisLeft();
        setAxisInfo(x,v);
        setYAxisInfo((YAxis) x, v);
    }
    @ReactProp(name="yAxisRight")
    public  void  setYAxisRight(BarLineChartBase chart,ReadableMap v){
        AxisBase x= chart.getAxisRight();
        setAxisInfo(x,v);
        setYAxisInfo((YAxis) x, v);
    }
    private void setAxisInfo(AxisBase axis,ReadableMap v){
        if(v.hasKey("enable")) axis.setEnabled(v.getBoolean("enable"));
        if(v.hasKey("drawAxisLine")) axis.setDrawAxisLine(v.getBoolean("drawAxisLine"));

        if(v.hasKey("drawGridLines")) axis.setDrawGridLines(v.getBoolean("drawGridLines"));
        if(v.hasKey("drawLabels")) axis.setDrawLabels(v.getBoolean("drawLabels"));

        if(v.hasKey("textColor")) axis.setTextColor(Color.parseColor(v.getString("textColor")));
        if(v.hasKey("textSize")) axis.setTextSize((float)v.getDouble("textSize"));
        if(v.hasKey("gridColor")) axis.setGridColor(Color.parseColor(v.getString("gridColor")));

        if(v.hasKey("gridLineWidth")) axis.setGridLineWidth((float)v.getDouble("gridLineWidth"));
        if(v.hasKey("axisLineColor")) axis.setAxisLineColor(Color.parseColor(v.getString("axisLineColor")));
        if(v.hasKey("axisLineWidth")) axis.setAxisLineWidth((float)(v.getDouble("axisLineWidth")));
        if(v.hasKey("gridDashedLine")) {
            ReadableMap gdl=v.getMap("gridDashedLine");
            axis.enableGridDashedLine((float)gdl.getDouble("lineLength"),
                    (float)gdl.getDouble("spaceLength"),
                    (float)gdl.getDouble("phase"));
        }
        if (v.hasKey("xOffset")) {
            axis.setXOffset((float)(v.getDouble("xOffset")));
        }
        if (v.hasKey("yOffset")) {
            axis.setYOffset((float)(v.getDouble("yOffset")));
        }
    }
    private  void setXAxisInfo(XAxis axis,ReadableMap v){

        if(v.hasKey("labelRotationAngle")) axis.setLabelRotationAngle((float) v.getDouble("labelRotationAngle"));
        if(v.hasKey("spaceBetweenLabels")) axis.setSpaceBetweenLabels(v.getInt("spaceBetweenLabels"));
        if(v.hasKey("labelsToSkip")) axis.setLabelsToSkip(v.getInt("labelsToSkip"));
        if(v.hasKey("avoidFirstLastClipping")) axis.setAvoidFirstLastClipping(v.getBoolean("avoidFirstLastClipping"));
        if(v.hasKey("position")) {
            String name=v.getString("position");
            axis.setPosition(XAxis.XAxisPosition.valueOf(name));
        }

//        if(v.hasKey("valueFormatter"))
//            axis.setValueFormatter(new XAxisValueFormatter() {
//                @Override
//                public String getXValue(String s, int i, ViewPortHandler viewPortHandler) {
//                    return String.format(s);
//                }
//            });
    }
    private  void setYAxisInfo(YAxis axis,ReadableMap v){
        if(v.hasKey("startAtZero")) axis.setStartAtZero(v.getBoolean("startAtZero"));
        if(v.hasKey("axisMaxValue")) axis.setAxisMaxValue((float) v.getDouble("axisMaxValue"));

        if(v.hasKey("axisMinValue")) axis.setAxisMinValue((float) v.getDouble("axisMinValue"));
        if(v.hasKey("inverted")) axis.setInverted(v.getBoolean("inverted"));

        if(v.hasKey("spaceTop")) axis.setSpaceTop((float) (v.getDouble("spaceTop")));
        if(v.hasKey("spaceBottom")) axis.setSpaceBottom((float) (v.getDouble("spaceBottom")));

        if(v.hasKey("showOnlyMinMax")) axis.setShowOnlyMinMax(v.getBoolean("showOnlyMinMax"));
        if(v.hasKey("labelCount")) {
            boolean labelCountForce = false;
            if (v.hasKey("labelCountForce")) {
                labelCountForce = v.getBoolean("labelCountForce");
            }
            axis.setLabelCount(v.getInt("labelCount"), labelCountForce);
        }

        if(v.hasKey("position")) {
            String name=v.getString("position");
            axis.setPosition(YAxis.YAxisLabelPosition.valueOf(name));
        }

        if(v.hasKey("valueFormatter"))
            axis.setValueFormatter(new CustomYAxisValueFormatter(v.getString("valueFormatter")) );
    }

    //{EnableLeft:true,EnableRight:true}
    @ReactProp(name="yAxis")
    public  void  setYAxis(BarLineChartBase chart,ReadableMap v){
        YAxis x= chart.getAxisLeft();
        setAxisInfo(x,v);
        setYAxisInfo(x,v);
    }
    @ReactProp(name="description")
    public  void  setDescription(BarLineChartBase chart,String v){
       chart.setDescription(v);
    }
    @ReactProp(name="backgroundColor")
    public  void  setBackgroundColor(BarLineChartBase chart,String v){
        chart.setBackgroundColor(Color.parseColor(v));
    }
    @ReactProp(name="drawGridBackground")
    public  void  setDrawGridBackground(BarLineChartBase chart,boolean v){
        chart.setDrawGridBackground(v);
    }
    @ReactProp(name="borderColor")
    public  void  setBorderColor(BarLineChartBase chart,String v){
        chart.setBorderColor(Color.parseColor(v));
    }
    @ReactProp(name="borderWidth")
    public  void  setBorderWidth(BarLineChartBase chart,double v){
        chart.setBorderWidth((float) v);
    }
    @ReactProp(name="gridBackgroundColor")
    public  void  setGridBackgroundColor(BarLineChartBase chart,String v){
        chart.setGridBackgroundColor(Color.parseColor(v));
    }
    @ReactProp(name="visibleXRange")
      public  void  setVisibleXRange(BarLineChartBase chart,ReadableArray v){
        chart.setVisibleXRange((float) v.getDouble(0), (float) v.getDouble(1));

    }
    @ReactProp(name="chartPadding")
    public  void  setPadding(BarLineChartBase chart,String v){
        String[] padding=v.split(" ");
        if(padding.length==1){
            int pad=(Integer.parseInt(padding[0]));
            chart.setPadding(pad,pad,pad,pad);
        }else if(padding.length==2){
            int pad1=(Integer.parseInt(padding[0]));
            int pad2=(Integer.parseInt(padding[1]));
            chart.setPadding(pad2,pad1,pad2,pad1);
        }else if(padding.length==4){
            int pad1=(Integer.parseInt(padding[0]));
            int pad2=(Integer.parseInt(padding[1]));
            int pad3=(Integer.parseInt(padding[2]));
            int pad4=(Integer.parseInt(padding[3]));
            chart.setPadding(pad4,pad1,pad2,pad3);
        }
    }
    @ReactProp(name="extraOffsets")
    public  void  setExtraOffsets(BarLineChartBase chart, String v){
        String[] offsets = v.split(" ");
        if (offsets.length == 1) {
            int offset = (Integer.parseInt(offsets[0]));
            chart.setExtraOffsets(offset, offset, offset, offset);
        } else if(offsets.length == 2) {
            int offset1=(Integer.parseInt(offsets[0]));
            int offset2=(Integer.parseInt(offsets[1]));
            chart.setExtraOffsets(offset2, offset1, offset2, offset1);
        } else if(offsets.length == 4) {
            int offset1 = (Integer.parseInt(offsets[0]));
            int offset2 = (Integer.parseInt(offsets[1]));
            int offset3 = (Integer.parseInt(offsets[2]));
            int offset4 = (Integer.parseInt(offsets[3]));
            chart.setExtraOffsets(offset4, offset1, offset2, offset3);
        }
    }
    @ReactProp(name="legend")
    public void setLegend(BarLineChartBase chart,ReadableMap v){
        Legend legend=chart.getLegend();
        if(v.hasKey("enable")) legend.setEnabled(v.getBoolean("enable"));
        if(v.hasKey("position"))  legend.setPosition(Legend.LegendPosition.valueOf(v.getString("position")));
        if(v.hasKey("direction"))  legend.setDirection(Legend.LegendDirection.valueOf(v.getString("direction")));

        if(v.hasKey("legendForm"))  legend.setForm(Legend.LegendForm.valueOf(v.getString("legendForm")));
        if(v.hasKey("wordWrap")) legend.setWordWrapEnabled(v.getBoolean("wordWrap"));

        if(v.hasKey("textColor"))  legend.setTextColor(Color.parseColor(v.getString("textColor")));
        if(v.hasKey("textSize"))  legend.setTextSize((float) v.getDouble("textSize"));
        if(v.hasKey("xOffset"))  legend.setXOffset((float) v.getDouble("xOffset"));
        if(v.hasKey("yOffset"))  legend.setYOffset((float) v.getDouble("yOffset"));
        if(v.hasKey("xEntrySpace"))  legend.setXEntrySpace((float) v.getDouble("xEntrySpace"));
        if(v.hasKey("yEntrySpace"))  legend.setYEntrySpace((float) v.getDouble("yEntrySpace"));

        if(v.hasKey("custom")){
            ReadableMap custom=v.getMap("custom");
            ReadableArray colors=custom.getArray("colors");
            ReadableArray labels=custom.getArray("labels");
            if(colors.size()==labels.size()) {
                int[] cols = new int[colors.size()];
                String[] labs = new String[colors.size()];
                for (int j = 0; j < colors.size(); j++) {
                    cols[j] = Color.parseColor(colors.getString(j));
                    labs[j] = labels.getString(j);
                }
                legend.setCustom(cols,labs);
            }
        }
    }

    @ReactProp(name="fitScreen")
    public void setLegend(BarLineChartBase chart,boolean v){
        if(v)chart.fitScreen();
    }

    @ReactProp(name="zoomTo")
    public void setZoomTo(BarLineChartBase chart, ReadableMap v) {
        AxisDependency axisDependency = AxisDependency.LEFT;
        if(v.hasKey("axisDependency")) {
            if (v.getString("axisDependency").equalsIgnoreCase("RIGHT")) {
                axisDependency = AxisDependency.RIGHT;
            }
        }
        chart.zoom((long)v.getDouble("scaleX"), (long)v.getDouble("scaleY"), (long)v.getDouble("xValue"), (long)v.getDouble("yValue"), axisDependency);
    }

    @ReactProp(name="viewCenter")
    public void setViewCenter(BarLineChartBase chart, ReadableArray v){
        chart.centerViewTo(v.getInt(0), (float) v.getDouble(1), AxisDependency.LEFT);
    }
}
