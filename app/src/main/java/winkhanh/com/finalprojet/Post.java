package winkhanh.com.finalprojet;

import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_DETAIL = "Detail";
    public static final String KEY_TITLE = "Title";
    public static final String KEY_USER = "User";
    public static final String KEY_CREATED_KEY = "createdAt";

    public String getKeyDetail(){
        return getString(KEY_DETAIL);
    }

    public void setKeyDetail(String Detail) {
        put(KEY_DETAIL, Detail);
    }

    public String getKeyTitle(){
        return getString(KEY_TITLE);
    }

    public void setKeyTitle(String Title) {
        put(KEY_DETAIL, Title);
    }

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }

}
