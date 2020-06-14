package com.rmolives.lrcolor;

public class ColorConvert {
    public static int hex2alpha(String hex) {
        return Math.round((float) (Integer.parseInt("0x" + hex, 16) / 255) * 100);
    }

    public static String rgb2hex(Rgb rgb) {
        return "#" + convert(rgb.r) + convert(rgb.g) + convert(rgb.b);
    }

    public static String rgba2hex(Rgba rgba) {
        return rgb2hex(rgba2rgb(rgba));
    }

    public static Rgb rgba2rgb(Rgba rgba) {
        int a = rgba.a / 100;
        return new Rgb(
                (1 - a) * 255 + a * rgba.r,
                (1 - a) * 255 + a * rgba.g,
                (1 - a) * 255 + a * rgba.b
        );
    }

    public static Hsl hex2hsl(String hex) {
        return hsv2hsl(rgb2hsv(hex2rgb(hex)));
    }

    public static Rgb hsl2rgb(Hsl hsl) {
        return hsv2rgb(hsl2hsv(hsl));
    }

    public static Hsl hsv2hsl(Hsv hsv) {
        int h = ((200 - hsv.s) * hsv.v) / 100;
        return new Hsl(
                h,
                (hsv.s * hsv.v) / (h < 100 ? h : 200 - h),
                h / 2
        );
    }

    public static Hsv hsl2hsv(Hsl hsl) {
        int s = hsl.s * ((hsl.l < 50 ? hsl.l : 100 - hsl.l) / 100);
        return new Hsv(hsl.h, ((2 * s) / (hsl.l + s)) * 100, hsl.l + s);
    }

    public static Hsv rgb2hsv(Rgb rgb) {
        if (rgb == null)
            return null;
        int h, s, v;
        int max = Math.max(Math.max(rgb.r, rgb.g), rgb.b);
        int min = Math.min(Math.min(rgb.r, rgb.g), rgb.b);
        int delta = max - min;
        if (delta == 0)
            h = 0;
        else if (rgb.r == max)
            h = ((rgb.g - rgb.b) / delta) % 6;
        else if (rgb.g == max)
            h = (rgb.b - rgb.r) / delta + 2;
        else
            h = (rgb.r - rgb.g) / delta + 4;
        h = Math.round(h * 60);
        if (h < 0)
            h += 360;
        s = Math.round((float) (max == 0 ? 0 : delta / max) * 100);
        v = Math.round((float) (max / 255) * 100);
        return new Hsv(h, s, v);
    }

    public static Rgb hsv2rgb(Hsv hsv) {
        int s = hsv.s / 100;
        int v = hsv.v / 100;
        int c = v * s;
        int h = hsv.h / 60;
        int x = c * (1 - Math.abs((h % 2) - 1));
        int m = v - c;
        Rgb rgb =
                h == 0 ? new Rgb(c, x, 0) :
                        h == 1 ? new Rgb(x, c, 0) :
                                h == 2 ? new Rgb(0, c, x) :
                                        h == 3 ? new Rgb(0, x, c) :
                                                h == 4 ? new Rgb(x, 0, c) :
                                                        h == 5 ? new Rgb(c, 0, x) : null;
        if (rgb == null)
            return null;
        return new Rgb(
                Math.round(255 * (rgb.r + m)),
                Math.round(255 * (rgb.g + m)),
                Math.round(255 * (rgb.b + m))
        );
    }

    public static Rgb hex2rgb(String hex) {
        if (hex.startsWith("#"))
            hex = hex.substring(1);
        if (hex.length() == 3)
            return new Rgb(
                    Integer.parseInt(String.valueOf(new char[]{hex.charAt(0), hex.charAt(0)}), 16),
                    Integer.parseInt(String.valueOf(new char[]{hex.charAt(1), hex.charAt(1)}), 16),
                    Integer.parseInt(String.valueOf(new char[]{hex.charAt(2), hex.charAt(2)}), 16)
            );
        return new Rgb(
                Integer.parseInt(hex.substring(0, 2), 16),
                Integer.parseInt(hex.substring(2, 4), 16),
                Integer.parseInt(hex.substring(4, 6), 16)
        );
    }

    private static String convert(int num) {
        String hex = Integer.toHexString(num);
        return hex.length() == 1 ? '0' + hex : hex;
    }
}
