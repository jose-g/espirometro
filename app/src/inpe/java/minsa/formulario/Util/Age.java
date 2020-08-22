package minsa.formulario.Util;

public class Age {

    private int days;
    private int months;
    private int years;

    private Age() {
        //Prevent default constructor
    }

    public Age(int days, int months, int years) {
        this.days = days;
        this.months = months;
        this.years = years;
    }

    public int getDays() {
        return this.days;
    }

    public int getMonths() {
        return this.months;
    }

    public int getYears() {
        return this.years;
    }

    @Override
    public String toString() {
        return years + (years == 1 ? " año, " : " años, ") + months + (months == 1 ? " mes, " : " meses, ") + days + (days == 1 ? " día" : " días");
    }

}