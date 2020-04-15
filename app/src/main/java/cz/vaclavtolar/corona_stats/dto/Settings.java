package cz.vaclavtolar.corona_stats.dto;

public class Settings {

    private Column column1 = Column.ACTIVE;
    private Column column2 = Column.RECOVERED;

    public Column getColumn1() {
        return column1;
    }

    public void setColumn1(Column column1) {
        this.column1 = column1;
    }

    public Column getColumn2() {
        return column2;
    }

    public void setColumn2(Column column2) {
        this.column2 = column2;
    }

    public enum Column {
        CONFIRMED,
        ACTIVE,
        RECOVERED
    }
}
