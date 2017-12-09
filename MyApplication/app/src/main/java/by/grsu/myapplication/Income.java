package by.grsu.myapplication;

/**
 * Created by User on 030 30.11.17.
 */

public class Income {
    private int id;
    private String incomeDate;
    private double incomeSum;
    private String categoryName;

    Income(int id, String incomeDate,String categoryName, double incomeSum){
        this.id = id;
        this.incomeDate = incomeDate;
        this.categoryName = categoryName;
        this.incomeSum = incomeSum;
    }
    public int getId() {
        return id;
    }
    public String getIncomeDate() {
        return incomeDate;
    }

    public void setIncomeDate(String incomeDate) {
        this.incomeDate = incomeDate;
    }

    public double getIncomeSum() {
        return incomeSum;
    }

    public void setIncomeSum(double incomeSum) {
        this.incomeSum = incomeSum;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return this.incomeDate + "   " + this.categoryName + " \n " + this.incomeSum;
    }

}
