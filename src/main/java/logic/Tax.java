package logic;

public enum Tax {
    CONDITIONED,
    INSTANT,
    ALCOHOL;

    public double applied(double value) {
        return switch (this) {
            case CONDITIONED -> value * 1.055;
            case INSTANT -> value * 1.1;
            case ALCOHOL -> value * 1.2;
        };
    }

    @Override
    public String toString() {
        return switch (this) {
            case CONDITIONED -> "5.5%";
            case INSTANT -> "1%";
            case ALCOHOL -> "2%";
        };
    }
}
