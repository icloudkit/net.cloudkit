/*
 * Copyright (C) 2016. The CloudKit Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.cloudkit.enterprises.ocr;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * @author hongquanli <hongquanli@qq.com>
 * @version 1.0 2016/4/6 18:51
 */
public class Rotate {

    /**
     * @param args
     */
    public static void main(String[] args) {
        JFrame jf = new JFrame();
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.getContentPane().add(new RotatePanel());
        jf.setPreferredSize(new Dimension(500, 400));
        jf.pack();
        jf.setVisible(true);

    }

}

class RotatePanel extends JPanel {
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, this.getWidth(), this.getHeight());

        String s = "Java 2d 旋转";
        Font f = new Font("宋体", Font.BOLD, 16);
        g2d.setColor(Color.BLACK);
        Color[] colors = {Color.ORANGE, Color.LIGHT_GRAY};
        g2d.setFont(f);

        //   平移原点到图形环境的中心
        g2d.translate(this.getWidth() / 2, this.getHeight() / 2);

        /** 设置字体在图片中的位置 在这里是居中* */
        FontRenderContext context = g2d.getFontRenderContext();
        Rectangle2D bounds = f.getStringBounds("1", context);
        double x = (500 - bounds.getWidth()) / 2;
        double y = (400 - bounds.getHeight()) / 2;

        double ascent = -bounds.getY();
        double baseY = y + ascent;

        //   旋转文本
        for (int i = 0; i < 12; i++) {
            g2d.rotate(1 * Math.PI / 180);
            g2d.setPaint(colors[i % 2]);
            g2d.drawString(s, (int) x, (int) baseY);
        }
    }
}
