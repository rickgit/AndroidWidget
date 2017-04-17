package edu.ptu.demo.test.roundindicator;

import java.util.ArrayList;
import java.util.List;

/**
 * 仪表盘dash board;instrument panel
 * Created by anshu.wang on 2017/4/17.
 */

public class RoundIndicatorBean {
    private int[] panel;//仪表盘的宽高
    private Round panelFrame;

    private Round process;
    private Round dashRule;//刻度尺
    private List<Scale> scales=new ArrayList<>(9);

    private Font type;
    private Font value;

    public RoundIndicatorBean(){
        panelFrame=new Round();
        process=new Round();
        dashRule=new Round();

        for (int i = 0; i < 8; i++) {
            scales.add(new Scale());
        }

        type=new Font();
        value=new Font();
    }

    public class Round {
        private float radius;
        private int bgColor;
        private int width;
        private int end;
    }
    public class Scale{
        private float length;
        private float width;
        private float angle;
    }

    private class Font{
        private int fontSize;
        private int fontColor;
        private String text;
    }
}
