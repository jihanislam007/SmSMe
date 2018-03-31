package devsbox.jihanislam007.smstweet.ModelClass;

/**
 * Created by muhmmod on 3/21/18.
 */

public class CategoryList {

    String categoryName;
    String layoutImageURL;
    public int categoryId;

    public CategoryList(String categoryName, String layoutImageURL, int categoryId) {
        this.categoryName = categoryName;
        this.layoutImageURL = layoutImageURL;
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getLayoutImageURL() {
        return layoutImageURL;
    }

    public void setLayoutImageURL(String layoutImageURL) {
        this.layoutImageURL = layoutImageURL;
    }
}
