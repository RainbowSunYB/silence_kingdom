package org.rainbow.silence_kingdom.view;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by chenjensen on 16/11/26.
 */

/**
 * com.jensen.Dashboard 是继承自JComonent实现的一个仪表盘
 * 当前支持线性刻度盘，弧形刻度盘，圆形刻度盘，半圆型刻度盘
 */
public class Dashboard extends JComponent {

    /**
     * 默认字体大小
     */
    private static final int VALUE_FONT_SIZE = 18;

    /**
     * 线性刻度名称
     */
    public static final String LINE = "line";
    /**
     * 弧形刻度名称
     */
    public static final String ARC = "arc";
    /**
     * 圆形刻度名称
     */
    public static final String CIRCLE = "circle";
    /**
     * 半圆刻度名称
     */
    public static final String SEMI_CIRCLE = "semi_circle";
    /**
     * 圆形角度
     */
    public static final double CIRCLE_ANGLE = 360;
    /**
     * 弧形角度数据切割索引
     */
    public static final int ARC_ANGLE_INDEX = 3;
    /**
     * 半圆角度数据切割索引
     */
    public static final int SEMI_ANGLE_INDEX = 11;
    /**
     * 圆形起始弧度
     */
    public static final double CIRCLE_ANGLE_START = Math.PI;

    public static final Color DEFAULT_COLOR = Color.black;

    private double from = 0;
    private double to = 10;

    private String type = "line";

    //最大刻度，最小刻度
    private double major = 1;
    private double minor = 0.1;

    //当前指针指向的位置
    private int value = 0;

    //单位
    private String unit = "";

    private double width;
    private double height;

    private Graphics2D g2;
    private int fontSize;

    private Color majorScaleColor;
    private Color minorScaleColor;
    private Color pointerColor;
    private Color textColor;
    private Color valueColor;
    private Color foregroundColor;
    private Color backgroundColor;

    private Dimension size;

    private Dashboard(Builder builder) {
        from = builder.from;
        to = builder.to;
        type = builder.type;
        major = builder.major;
        minor = builder.minor;
        value = builder.value;
        unit = builder.unit;
        majorScaleColor = builder.majorScaleColor;
        minorScaleColor = builder.minorScaleColor;
        pointerColor = builder.pointerColor;
        textColor = builder.textColor;
        valueColor = builder.valueColor;
        foregroundColor = builder.foregroundColor;
        backgroundColor = builder.backgroundColor;
        size = builder.size;

    }

    /**
     * 重写paintComponent方法，获取控件大小，对画布进行配置，根据传递类型，进行相应判断，调用相应绘制方法
     *
     * @param g
     */
    public void paintComponent(Graphics g) {
        width = getWidth();
        height = getHeight();
        g2 = graphicsConfig(g);
        initComponent();
        drawSemiCircle();
        drawSemiCircleText();
    }

    private void initComponent() {
//        if (foregroundColor != null)
//            this.setBackground(foregroundColor);
//        if (backgroundColor != null)
//            this.setBackground(backgroundColor);
        this.setOpaque(false);
        if (size != null)
            this.setPreferredSize(size);
    }

    private Graphics2D graphicsConfig(Graphics g) {
        fontSize = 18;
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(this.getBackground());
//        g2.fillRect(0, 0, (int) width, (int) height);
        g2.setColor(this.getForeground());
        g2.setStroke(new BasicStroke(1));
        g2.setFont(new Font(Font.SERIF, Font.PLAIN, fontSize));
        return g2;
    }

    private void drawSemiCircle() {
        //整体角度和起始弧度
        double angle = toDouble(type.substring(SEMI_ANGLE_INDEX));
        double startAngle = (180 + (angle - 180) / 2) / 180 * Math.PI;
        //根据控件的大小来确定指针的长度
        double r = angle <= 180 ? Math.min(width / 2, height) : Math.min(width / 2, height / 2);
        double yOffset = angle <= 180 ? 0 : r;
        //确定每一个刻度所表示的弧度
        double dunit = (angle / 180 * Math.PI) / (to - from);
        //绘制出刻度盘

        drawSemiCircleScale(startAngle, dunit, r, yOffset);
        //绘制当前指针的指向
        drawSemiCircleValue(startAngle, dunit, r, yOffset, angle);
    }

    private void drawSemiCircleScale(double startAngle, double dunit, double r, double yOffset) {
        for (int k = 0; k < 2; k++) {
            startAngle = k == 0 ? startAngle : startAngle - Math.PI / 2;
            drawArcScale(startAngle, dunit, r, yOffset);
        }
    }

    private void drawArcScale(double startAngle, double dunit, double r, double yOffset) {
        for (int i = 0; i <= (to - from) / major; i++) {
            if (majorScaleColor != null)
                g2.setColor(majorScaleColor);
            g2.draw(getArcMajorLine(startAngle, dunit, r, yOffset, i));
            if (minor > 0 && i < (to - from) / major) {
                for (int j = 1; j < major / minor; j++) {
                    if (minorScaleColor != null)
                        g2.setColor(minorScaleColor);
                    g2.draw(getArcMinorLine(startAngle, dunit, r, yOffset, i, j));
                }
            }
        }
    }

    private Line2D getArcMajorLine(double startAngle, double dunit, double r, double yOffset, int num) {
        double x1 = Math.cos(startAngle - num * major * dunit) * r + width / 2;
        double y1 = height - yOffset - Math.sin(startAngle - num * major * dunit) * r;
        double x2 = Math.cos(startAngle - num * major * dunit) * r * 0.75 + width / 2;
        double y2 = height - yOffset - Math.sin(startAngle - num * major * dunit) * r * 0.75;
        return new Line2D.Double(x1, y1, x2, y2);
    }

    private Line2D getArcMinorLine(double startAngle, double dunit, double r, double yOffset, int num1, int num2) {
        double x1 = Math.cos(startAngle - (num1 * major + num2 * minor) * dunit) * r + width / 2;
        double y1 = height - yOffset - Math.sin(startAngle - (num1 * major + num2 * minor) * dunit) * r;
        double x2 = Math.cos(startAngle - (num1 * major + num2 * minor) * dunit) * r * 0.875 + width / 2;
        double y2 = height - yOffset - Math.sin(startAngle - (num1 * major + num2 * minor) * dunit) * r * 0.875;
        return new Line2D.Double(x1, y1, x2, y2);
    }

    private void drawSemiCircleValue(double startAngle, double dunit, double r, double yOffset, double angle) {
        double val = from + value;
        //            if (value.startsWith("left")) {
        //                val = toDouble(value.substring(4));
        //                val = to - val;
        //            } else {
        //                val = toDouble(value.substring(5));
        //                val = to + val;
        //            }

        GeneralPath p = new GeneralPath();
        p.moveTo(Math.cos(startAngle - (val - from) * dunit) * r * 0.875 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit) * r * 0.875);
        p.lineTo(Math.cos(startAngle - (val - from) * dunit + Math.PI * 0.5) * 2 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit + Math.PI * 0.5) * 2);
        p.lineTo(Math.cos(startAngle - (val - from) * dunit - Math.PI * 0.5) * 2 + width / 2, height - yOffset - Math.sin(startAngle - (val - from) * dunit - Math.PI * 0.5) * 2);
        p.closePath();
        if (pointerColor != null)
            g2.setColor(pointerColor);
        g2.fill(p);
        g2.setFont(new Font("", Font.BOLD, VALUE_FONT_SIZE));
        yOffset = angle <= 180 ? 10 : r - fontSize / 2;
        drawValue(g2, unit + value, (int) (width / 2 - getStrBounds(g2, Integer.toString(value)).getWidth() / 2), (int) (height - yOffset));
    }

    private void drawSemiCircleText() {
        double angle = toDouble(type.substring(SEMI_ANGLE_INDEX));
        double startAngle = CIRCLE_ANGLE_START;
        double r = angle <= 180 ? Math.min(width / 2, height) : Math.min(width / 2, height / 2);
        double yOffset = angle <= 180 ? 0 : r;
        double dunit = (angle / 180 * Math.PI) / (to - from);
        int xoff = 0;
        int yoff = 0;
        double strAngle;

        for (int i = 0; i <= (to - from) / major; i++) {
            String str = format(from + i * major);
            strAngle = (startAngle - i * major * dunit + Math.PI * 2) % (Math.PI * 2);
            xoff = 0;
            yoff = 0;
            if (strAngle >= 0 && strAngle < Math.PI * 0.25) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
                yoff = fontSize / 2;
                if (strAngle == 0 && angle == 180) {
                    yoff = 0;
                }
            } else if (near(strAngle, Math.PI * 0.5)) {
                xoff = (int) -getStrBounds(g2, str).getWidth() / 2;
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.25 && strAngle < Math.PI * 0.5) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.5 && strAngle < Math.PI * 0.75) {
                yoff = fontSize;
            } else if (strAngle >= Math.PI * 0.75 && strAngle < Math.PI) {
                yoff = fontSize / 2;
            } else if (near(strAngle, Math.PI)) {
                xoff = 1;
                yoff = fontSize / 2;
                if (angle == 180) {
                    yoff = 0;
                }
            } else if (strAngle >= Math.PI && strAngle < Math.PI * 1.25) {
                yoff = fontSize / 4;
            } else if (near(strAngle, Math.PI * 1.5)) {
                xoff = (int) -getStrBounds(g2, str).getWidth() / 2;
            } else if (strAngle >= Math.PI * 1.5 && strAngle < Math.PI * 2) {
                xoff = (int) -getStrBounds(g2, str).getWidth();
            }
            if (textColor != null)
                g2.setColor(textColor);
            g2.drawString(str, (int) (Math.cos(strAngle) * r * 0.75 + width / 2) + xoff, (int) (height - yOffset - Math.sin(strAngle) * r * 0.75) + yoff);

        }
    }

    private void drawValue(Graphics2D g2, String value, int x, int y) {
        g2.setFont(new Font(Font.SERIF, Font.BOLD, VALUE_FONT_SIZE));
        if (valueColor != null)
            g2.setColor(valueColor);
        g2.drawString(value, x, y);
    }

    private String format(double d) {
        if ((int) d == d) {
            return String.valueOf((int) d);
        } else {
            return String.valueOf(d);
        }
    }

    private double toDouble(String string) {
        try {
            return Double.valueOf(string);
        } catch (Exception e) {
            return 0;
        }
    }

    private boolean near(double d1, double d2) {
        return Math.round(d1 * 1000000) == Math.round(d2 * 1000000);
    }

    private static Rectangle2D getStrBounds(Graphics2D g, String str) {
        Font f = g.getFont();
        Rectangle2D rect = f.getStringBounds(str, g.getFontRenderContext());
        if (rect.getHeight() < f.getSize()) {
            rect.setFrame(rect.getX(), rect.getY(), rect.getWidth(), f.getSize() + 1);
        }
        return rect;
    }

    public void updateValue(int value) {
        this.value = value;
        repaint();
    }

    public static final class Builder {
        private double from;
        private double to;
        private double major;
        private double minor;
        private String type;
        private String unit;
        private int value;
        private Color majorScaleColor;
        private Color minorScaleColor;
        private Color pointerColor;
        private Color textColor;
        private Color valueColor;
        private Color foregroundColor;
        private Color backgroundColor;

        private Dimension size;

        public Builder() {
            from = 0;
            to = 10;
            major = 1;
            minor = 0.1;
            type = LINE;
            unit = "";
            value = 0;
            majorScaleColor = DEFAULT_COLOR;
            minorScaleColor = DEFAULT_COLOR;
            pointerColor = DEFAULT_COLOR;
            textColor = DEFAULT_COLOR;
            valueColor = DEFAULT_COLOR;
            foregroundColor = Color.white;
            backgroundColor = Color.white;
        }

        public Builder from(double from) {
            this.from = from;
            return this;
        }

        public Builder to(double to) {
            this.to = to;
            return this;
        }

        public Builder major(double major) {
            this.major = major;
            return this;
        }

        public Builder minor(double minor) {
            this.minor = minor;
            return this;
        }

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder unit(String unit) {
            this.unit = unit;
            return this;
        }

        public Builder value(int value) {
            this.value = value;
            return this;
        }

        public Builder majorScaleColor(Color majorScaleColor) {
            this.majorScaleColor = majorScaleColor;
            return this;
        }

        public Builder minorScaleColor(Color minorScaleColor) {
            this.minorScaleColor = minorScaleColor;
            return this;
        }

        public Builder pointerColor(Color pointerColor) {
            this.pointerColor = pointerColor;
            return this;
        }

        public Builder textColor(Color textColor) {
            this.textColor = textColor;
            return this;
        }

        public Builder valueColor(Color valueColor) {
            this.valueColor = valueColor;
            return this;
        }

        public Builder foregroundColor(Color foregroundColor) {
            this.foregroundColor = foregroundColor;
            return this;
        }

        public Builder backgroundColor(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Builder size(Dimension size) {
            this.size = size;
            return this;
        }

        public Dashboard build() {
            return new Dashboard(this);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        JFrame f = new JFrame("速度展示");
        Container p = f.getContentPane();
        Dashboard dashboard = new Dashboard.Builder()
                .from(0)
                .to(100)
                .major(10)
                .minor(1)
                .value(10)
                .unit("M/S")
                .type("semi_circle180")
                .textColor(Color.black)
                .valueColor(Color.black)
                .pointerColor(Color.black)
                .majorScaleColor(Color.BLACK)
                .minorScaleColor(Color.DARK_GRAY)
                .foregroundColor(Color.BLUE)
                .backgroundColor(Color.WHITE)
                .size(new Dimension(400, 100))
                .build();

        p.add(dashboard);
        f.setSize(640, 480);
        f.setVisible(true);

        Thread.sleep(5000);
        dashboard.updateValue(50);
    }
}