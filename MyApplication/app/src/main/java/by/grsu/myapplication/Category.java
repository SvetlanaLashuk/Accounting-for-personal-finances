package by.grsu.myapplication;

/**
 * Created by User on 030 30.11.17.
 */

public class Category {
    private int id;
    private String categoryName;
    private String categoryComment;

    Category(int id, String categoryName, String categoryComment){
        this.id = id;
        this.categoryName = categoryName;
        this.categoryComment = categoryComment;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return categoryName;
    }

    public void setName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getComment() {
        return categoryComment;
    }

    public void setComment(String categoryComment) {
        this.categoryComment = categoryComment;
    }

    @Override
    public String toString() {
        return this.categoryName + "    " + this.categoryComment;
    }
}
