package winkhanh.com.finalprojet;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

import winkhanh.com.finalprojet.models.Post;

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();


        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId(getString(R.string.back4app_id))
                .clientKey(getString(R.string.back4app_key))
                .server(getString(R.string.back4app_server))
                .build()
        );
    }
}
