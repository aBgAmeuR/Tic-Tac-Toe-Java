package models;

public class Symbol {
    private String symbol;

    public Symbol(String symbol) {
        this.symbol = symbol;
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
}
