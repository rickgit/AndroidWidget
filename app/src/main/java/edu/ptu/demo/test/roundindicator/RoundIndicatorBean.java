package edu.ptu.demo.test.roundindicator;

import android.util.TypedValue;

import edu.ptu.demo.test.BaseApp;

/**
 * 仪表盘dash board;instrument panel
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorBean {
    private int[] panel;//仪表盘的宽高
    private Round panelFrame;
    private int pointerLen = (int) dp2px(6);
    private Round process;
    private Round dashRule;//刻度尺
    private Scale scale;

    private PanelText type;
    private PanelText value;

    public RoundIndicatorBean(int panelWidth) {
        initRedPanel(panelWidth);
    }

    public void initRedPanel(int panelWidth) {
        //        int panelWidth = 360;
        panel = new int[]{panelWidth, panelWidth / 2};
        int panelFrameBgColor = 0xfff5c8c8;
        initPanelFrame(panelWidth, panelFrameBgColor);
        int processColor = 0xffdd4a4a;
        initProcess(panelWidth, processColor);
        initDash(panelWidth);
        initScale(panelWidth);
        String text = "周回报率";
        initText(text);
    }

    public void initBluePanel(int panelWidth) {
        //        int panelWidth = 360;

        panel = new int[]{panelWidth, panelWidth / 2};
        int panelFrameBgColor = 0xffc2d0e4;
        initPanelFrame(panelWidth, panelFrameBgColor);
        int processColor = 0xff3465a6;
        initProcess(panelWidth, processColor);
        initDash(panelWidth);
        initScale(panelWidth);
        String text = "月回报率";
        initText(text);
    }

    private void initPanelFrame(int panelWidth, int panelFrameBgColor) {
        panelFrame = new Round();
        panelFrame.bgColor = panelFrameBgColor;
        panelFrame.strokeWidth = (int) dp2px(1);//2dp
        panelFrame.radius = panelWidth / 2 - dp2px(1);//半径减去线宽
        panelFrame.startAngle = 180;
        panelFrame.sweepAngle = 180;
    }

    private void initProcess(int panelWidth, int processColor) {
        process = new Round();
        process.bgColor = processColor;
        process.strokeWidth = (int) dp2px(7);
        process.radius = panelWidth / 2- dp2px(7) - dp2px(8)  - dp2px(1);//半径减去线宽
        process.startAngle = 180;
        process.sweepAngle = 90;
    }
    private void initDash(int panelWidth) {
        dashRule = new Round();
        dashRule.bgColor = 0xfff2eded;
        dashRule.strokeWidth = (int) dp2px(7);
        dashRule.radius = panelWidth / 2  - (int) dp2px(7)  - dp2px(8) - dp2px(1);
        dashRule.startAngle = 180;
        dashRule.sweepAngle = 180;
    }
    private void initScale(int panelWidth) {
        scale = new Scale();
        scale.arcAngle = 1;
        scale.strokeWidth = dp2px(6) ;
        scale.radius = panelWidth / 2  - dp2px(1)- dp2px(8) - (int) dp2px(7) - scale.strokeWidth;
    }


    private void initText(String text) {
        type = new PanelText();
        type.text = text;
        type.fontSize = (int) dp2px(12);
        type.fontColor = 0xffaea4a2;
        value = new PanelText();
        value.text = "-";
        value.fontSize = (int) dp2px(18);
        value.fontColor = 0xff6a5f5e;
    }



    public class Round {
        public float radius;
        public int bgColor = 0xfffae5e2;
        public int strokeWidth = 10;
        public int startAngle;
        public float sweepAngle;
    }

    public class Scale {
        public float strokeWidth;
        public float arcAngle;
        public float radius;
        public int maxScale;
        public float angleRangle[] = {0f, 180f};
        public float off = 2;
        public float scaleNum =   13;
        public float scaleSize = (angleRangle[1] - off * 2) / (scaleNum-1);


        public int getColor(int currentScalue) {
            if (maxScale >= currentScalue)
                return getProcess().bgColor;
            return getDashRule().bgColor;
        }

    }

    public class PanelText {
        public int fontSize;
        public int fontColor;
        public String text;
    }

    public int[] getPanel() {
        return panel;
    }

    public Round getPanelFrame() {
        return panelFrame;
    }

    public Round getDashRule() {
        return dashRule;
    }

    public Round getProcess() {
        return process;
    }

    public Scale getScale() {
        return scale;
    }

    public int getPointerLen() {
        return pointerLen;
    }

    public PanelText getType() {
        return type;
    }

    public PanelText getValue() {
        return value;
    }

    static float dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, BaseApp.mApp.getResources()
                .getDisplayMetrics());
    }

}
