package com.riceroll.utils;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class MouseUtils {

    /**
     * 判断是否为人类操作的鼠标轨迹
     *
     * @param mouseData 每项为 long[]{x, y, time}
     * @return 是否为人类轨迹
     */
    public static boolean isHuman(List<Long[]> mouseData) {
        int directionChanges = 0;
        Double lastDirection = null;
        List<Double> speeds = new ArrayList<>();

        for (int i = 1; i < mouseData.size(); i++) {
            Long[] prev = mouseData.get(i - 1);
            Long[] curr = mouseData.get(i);

            int dx = (int)(curr[0] - prev[0]);  // x坐标差值
            int dy = (int)(curr[1] - prev[1]);  // y坐标差值
            long dt = curr[2] - prev[2];        // 时间差保持long类型

            double direction = atan2(dy, dx);

            if (dt != 0) {
                double speed = sqrt(dx * dx + dy * dy) / dt;
                speeds.add(speed);
            }

            if (lastDirection != null && abs(direction - lastDirection) > 0.5) {
                directionChanges++;
            }

            lastDirection = direction;
        }

        if (speeds.isEmpty()) return false;

        double meanSpeed = speeds.stream().mapToDouble(Double::doubleValue).average().orElse(0.0);
        double stdSpeed = calculateStandardDeviation(speeds, meanSpeed);

        if (meanSpeed == 0) return false;

        return (double) directionChanges / mouseData.size() > 0.1 || stdSpeed / meanSpeed > 0.1;
    }

    /**
     * 计算标准差
     */
    private static double calculateStandardDeviation(List<Double> values, double mean) {
        double sumSq = 0.0;
        for (double v : values) {
            sumSq += (v - mean) * (v - mean);
        }
        return sqrt(sumSq / values.size());
    }

}
