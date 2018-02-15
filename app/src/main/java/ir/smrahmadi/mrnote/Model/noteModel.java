package ir.smrahmadi.mrnote.Model;

/**
 * Created by cloner on 18/05/16.
 */
public class noteModel {
    private int id;
    private String title;
    private String text;
    private String image;
    private int backgroundColor;
    private int bookmark;






    public noteModel(int id , String title,String text, String image,  int backgroundColor,int bookmark) {
        this.id=id;
        this.title=title;
        this.text=text;
        this.image=image;
        this.backgroundColor = backgroundColor ;
        this.bookmark = bookmark ;


    }


    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setBookmark(int bookmark) {
        this.bookmark = bookmark;
    }

    public int getBookmark() {
        return bookmark;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }
}
