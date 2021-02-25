package kolibri.example.kolibri.Singleton;

import android.app.Application;

public class CommentPersonMenu extends Application {
    private static CommentPersonMenu commentProgamMenu;
    private String commentMenuFile;


    public static synchronized CommentPersonMenu getCommentPersonMenu() {
        if (commentProgamMenu == null) {
            commentProgamMenu = new CommentPersonMenu();
        }
        return commentProgamMenu;
    }

    private CommentPersonMenu() {

    }

    public void addCommentMenuFile(String commentPersonTotal) {
        commentMenuFile = commentPersonTotal;
    }
    public String getCommentMenuFile () {
        return commentMenuFile;
    }

}
