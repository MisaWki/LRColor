package com.rmolives.lrcolor;

public class Main {
    public static void main(String[] args) {
        Rgb rgb = ColorConvert.hex2rgb("#3498db");
        System.out.format("%d %d %d\n", rgb.r, rgb.g, rgb.b);
        System.out.println(ColorConvert.rgb2hex(rgb));
    }
}
