package by.grsu.myapplication;

/**
 * Created by User on 004 04.12.17.
 */

public class Outlay {

    private int id;
    private String outlayDate;
    private double outlaySum;
    private String categoryName;

    Outlay(int id, String outlayDate,String categoryName, double outlaySum){
        this.id = id;
        this.outlayDate = outlayDate;
        this.categoryName = categoryName;
        this.outlaySum = outlaySum;
    }
    public int getId() {
        return id;
    }
    public String getOutlayDate() {
        return outlayDate;
    }

    public void setOutlayDate(String outlayDate) {
        this.outlayDate = outlayDate;
    }

    public double getOutlaySum() {
        return outlaySum;
    }

    public void setOutlaySum(double outlaySum) {
        this.outlaySum = outlaySum;
    }

    public String getcategoryName() {
        return categoryName;
    }

    public void setcategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.outlayDate + "   " + this.categoryName + " \n " + this.outlaySum;
    }
}
