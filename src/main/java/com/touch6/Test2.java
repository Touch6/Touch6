package com.pax;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LONG on 2017/4/11.
 */
public class Test2 {
    public static void draw(List<Map<String, Integer>> calc) throws IOException, ParseException {
        Double[] x = new Double[calc.size()-1];
        Double[] y = new Double[calc.size()-1];
        for (int i = 0; i < calc.size()-1; i++) {
            Map m = calc.get(i+1);
            Set<Map.Entry> keys = m.keySet();
            Iterator it = keys.iterator();
            String key = (String) it.next();
            Date d=new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(key);
            String t=new SimpleDateFormat("HH:mm").format(d);
            long hour= Long.parseLong(t.split(":")[0]);
            long min= Long.parseLong(t.split(":")[1]);
            x[i] = Double.valueOf((hour*60+min));
            y[i] = Double.valueOf(String.valueOf(m.get(key)))/10/400;
        }
        int width = 8000;
        int height = 6000;
        // 创建BufferedImage对象
        Font font = new Font("微软雅黑", Font.PLAIN, 16);
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取Graphics2D
        Graphics2D g2d = image.createGraphics();
        // 画图
        g2d.setBackground(new Color(255, 255, 255));
        g2d.setPaint(new Color(0, 0, 0));
        g2d.clearRect(0, 0, width, height);
        //画X轴，坐标(10,height-10)-(width-10,height-10)
        g2d.drawLine(10, height - 10, width - 10, height - 10);
        //画y轴,坐标(10,10)-(10,height-10)
        g2d.drawLine(10, 10, 10, height - 10);
        //画图
        for (int i = 0; i < x.length; i++) {
            Line2D line;
            if (i == 0) {
                line = new Line2D.Double(0, 0, x[i], y[i]);
            } else if (i == x.length - 1) {
                line = new Line2D.Double(x[i], y[i], x[i], y[i]);
            } else {
                line = new Line2D.Double(x[i], y[i], x[i + 1], y[i + 1]);
            }
            g2d.draw(line);
        }
        g2d.setFont(font);
        //释放对象
        g2d.dispose();
        // 保存文件
        ImageIO.write(image, "png", new File("D:/test.png"));
    }

    public static void main(String[] args) throws IOException, ParseException {
        BufferedReader reader = new BufferedReader(new FileReader("D://log.out"));
        String line;
        String time1 = "";
        List<Map<String, Integer>> calc = new ArrayList<>();

        Pattern patten = Pattern.compile("^.*\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}.*$");
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            String time2 = "";
            if (line.length() >= 30) {
                time2 = line.substring(14, 14 + 16);
            }
            Matcher matcher = patten.matcher(time2);
            if (matcher.matches()) {
                if (time1.equals(time2)) {
                    sb.append(line);
                } else {
                    Map param = new HashMap();
                    param.put(time1, sb.length());
                    calc.add(param);
                    sb = new StringBuilder();
                }
                time1 = time2;
            } else {
                sb.append(line);
            }
        }
//        draw(calc);
        for (int i = 0; i < calc.size()-1; i++) {
            Map m = calc.get(i+1);
            Set<Map.Entry> keys = m.keySet();
            Iterator it = keys.iterator();
            String key = (String) it.next();
            System.out.println(key+">>"+m.get(key));
        }
    }
}
