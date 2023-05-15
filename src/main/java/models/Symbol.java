package models;

import javafx.scene.paint.Color;

public class Symbol {
    private String symbol;

    private Color color;

    public Symbol(String symbol) {
        this.symbol = symbol;
        this.color = Color.WHITE;
    }

    public String getSymbol() {
        return symbol;
    }

    @Override
    public String toString() {
        return symbol;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!Symbol.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        final Symbol other = (Symbol) obj;
        if ((this.getSymbol() == null) ? (other.getSymbol() != null) : !this.getSymbol().equals(other.getSymbol())) {
            return false;
        }
        return true;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    public int[] getRGB() {
        int[] rgb = new int[3];
        rgb[0] = (int) (color.getRed() * 255);
        rgb[1] = (int) (color.getGreen() * 255);
        rgb[2] = (int) (color.getBlue() * 255);
        return rgb;
    }
}
