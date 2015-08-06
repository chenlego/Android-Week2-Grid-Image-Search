package legochen.yahoo.com.gridimagesearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by legochen on 8/5/15.
 */
public class ImageResult implements Serializable {
    private static final  long serialVersionUID = -289308957099247468L;
    public String fullUrl;
    public String thumbUrl;
    public String title;

    // new ImageResult(..raw item json..)
    public ImageResult(JSONObject json) {
        try {
            this.fullUrl = json.getString("url");
            this.thumbUrl = json.getString("tbUrl");
            this.title = json.getString("title");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // Take an array of json images and return arraylist of image results
    // ImageResult.fromJSONArray([..., ...])
    public static ArrayList<ImageResult> fromJSONArray(JSONArray array) {
        ArrayList<ImageResult> results = new ArrayList<ImageResult>();
        for (int i=0; i < array.length(); i++) {
            try {
                results.add(new ImageResult(array.getJSONObject(i)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return results;
    }
}
