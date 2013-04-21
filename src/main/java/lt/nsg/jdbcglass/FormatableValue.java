package lt.nsg.jdbcglass;

public abstract class FormatableValue {
    public String getFormattedStringValue() {
        return "'".concat(this.toString()).concat("'");
    }
}
