package com.fogitive.java2d.apple;

import javax.swing.*;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Menggambar logo Apple Inc. menggunakan Java2D
 *
 * @author chz
 */
public class Apple {

    public static void buildInterface() {
        JFrame window = new JFrame("Apple Inc.");
        window.setBackground(Color.white);
        window.add(new LogoDrawer());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.pack();
        window.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                buildInterface();
            }
        });
    }

    private static class LogoDrawer extends JPanel {

        public static final String FONT_NAME = "Helvetica Neue";

        private Area buatBuah(double baseX, double baseY) {

            Ellipse2D e2 = new Ellipse2D.Double();

            e2.setFrame(baseX - 30, baseY - 18, 40.0, 50.0);
            Area one = new Area(e2);

            e2.setFrame(baseX - 10, baseY - 18, 40.0, 50.0);
            Area two = new Area(e2);

            one.add(two);

            e2.setFrame(baseX + 20, baseY - 10, 30, 30);
            Area three = new Area(e2);

            one.subtract(three);

            return one;
        }

        private Area buatDaun(double baseX, double baseY) {

            Ellipse2D e2 = new Ellipse2D.Double();

            e2.setFrame(baseX - 15, baseY - 50, 30.0, 30.0);
            Area one = new Area(e2);

            e2.setFrame(baseX, baseY - 35, 30.0, 30.0);
            Area two = new Area(e2);

            one.intersect(two);

            return one;
        }

        private void gambarDeskripsi(Graphics2D g2, double baseX, double baseY){
            g2.setPaint(Color.gray);
            // Should be handled by multiline text renderer
            this.gambarDanTransformasiTeks(g2, new Font(FONT_NAME, Font.PLAIN, 10),
                    "Pemrograman Berorientasi Objek II", baseX, baseY, false);
            this.gambarDanTransformasiTeks(g2, new Font(FONT_NAME, Font.PLAIN, 10),
                    "STIMIK STIKOM Bali", baseX, baseY+12, false);
        }

        private void gambarMerk(Graphics2D g2, double baseX, double baseY) {
            g2.setPaint(Color.gray);
            this.gambarDanTransformasiTeks(g2,
                    new Font(FONT_NAME, Font.BOLD, 30),
                    "Apple Inc.", baseX,baseY + 60, true);
        }

        private void gambarSlogan(Graphics2D g2, double baseX, double baseY) {
            g2.setPaint(Color.darkGray);
            this.gambarDanTransformasiTeks(g2,
                    new Font(FONT_NAME, Font.BOLD, 20),
                    "Think Different.", baseX, baseY + 85, false);
        }

        private void gambarDanTransformasiTeks(Graphics2D g2, Font f, String s,
            double x, double y, boolean isOutline) {

            FontRenderContext c = g2.getFontRenderContext();
            TextLayout textLayout = new TextLayout(s, f, c);
            Shape outline = textLayout.getOutline(null);

            AffineTransform transform = AffineTransform.getTranslateInstance(
                x - (outline.getBounds().width / 2), y);
            outline = transform.createTransformedShape(outline);

            if (isOutline) {
                g2.draw(outline);
            } else {
                g2.fill(outline);
            }
        }

        private void gambarSignatureApple(Graphics g) {
            Dimension d = this.getSize();
            double baseX = d.width / 2;
            double baseY = d.height / 2;

            Graphics2D g2 = (Graphics2D) g;

            g2.setColor(Color.black);
            
            RenderingHints rh = new RenderingHints(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            rh.put(
                    RenderingHints.KEY_RENDERING,
                    RenderingHints.VALUE_RENDER_QUALITY);
            g2.setRenderingHints(rh);


            Area buah = this.buatBuah(baseX, baseY);
            Area daun = this.buatDaun(baseX, baseY);

            buah.add(daun);

            Rectangle2D r = buah.getBounds2D();
            float minY = (float) r.getMinY();
            float maxY = (float) r.getMaxY();
            float minX = (float) r.getMinX();
            float maxX = (float) r.getMaxX();

            GradientPaint gp = new GradientPaint(maxX, minY, Color.white, 
                minX, maxY, Color.black);
            g2.setPaint(gp);
            g2.fill(buah);

            this.gambarMerk(g2, baseX, baseY);
            this.gambarSlogan(g2, baseX, baseY);
            this.gambarDeskripsi(g2, baseX, 20);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(300, 300);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            this.setBackground(Color.white);
            this.gambarSignatureApple(g);
        }
    }
}
