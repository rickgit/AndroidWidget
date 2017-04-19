package edu.ptu.demo.test.roundindicator;

/**
 * 仪表盘dash board;instrument panel
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorBean {
    private int[] panel;//仪表盘的宽高
    private Round panelFrame;
    private int pointWidth=12;
    private Round process;
    private Round dashRule;//刻度尺
    private Scale scale;

    private PanelText type;
    private PanelText value;

    public RoundIndicatorBean(int pointWidth) {
//        int pointWidth = 360;
        panel = new int[]{pointWidth, pointWidth / 2};
        panelFrame = new Round();
        panelFrame.bgColor = 0xfffae5e2;
        panelFrame.width = 5;
        panelFrame.radius = pointWidth /2- 5;
        panelFrame.startAngle = 180;
        panelFrame.sweepAngle = 180;


        process = new Round();
        process.bgColor = 0xffde4a4a;
        process.width = 16;
        process.radius = pointWidth /2-60;
        process.startAngle = 180;
        process.sweepAngle = 90;

        dashRule = new Round();
        dashRule.bgColor = 0xfff3eded;
        dashRule.width = 16;
        dashRule.radius = pointWidth /2-32-28;
        dashRule.startAngle = 180;
        dashRule.sweepAngle = 180;

        scale = new Scale();
        scale.lineWidth = 0.5f;
        scale.length = 12;
        scale.radius = pointWidth /2-32-28-scale.length;


        type = new PanelText();
        type.text="周回报率";
        type.fontSize=16;
        type.fontColor=0xffb8b3b2;
        value = new PanelText();
        value.text="60%";
        value.fontSize=16;
        value.fontColor=0xff6a5f5d;
    }

    public class Round {
        public float radius;
        public int bgColor = 0xfffae5e2;
        public int width = 10;
        public int startAngle;
        public float sweepAngle;
    }

    public class Scale {
        public float length;
        public float lineWidth;
        public float radius;
        public float angleRangle[] = {0f, 180f};
        public float off = 6;
        public float scaleSize = (angleRangle[1] - off * 2) / 12;

        public int getColor(float current, float process) {
            if (process >= current)
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

    public int getPointWidth() {
        return pointWidth;
    }

    public PanelText getType() {
        return type;
    }

    public PanelText getValue() {
        return value;
    }
}
