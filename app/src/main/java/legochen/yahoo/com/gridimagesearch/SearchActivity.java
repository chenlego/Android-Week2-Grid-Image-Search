package legochen.yahoo.com.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import lego.yahoo.com.gridimagesearch.adapters.ImageResultsAdapter;
import com.yahoo.googleimagesearch.helpers.EndlessScrollListener;


public class SearchActivity extends ActionBarActivity  {

    private EditText etQuery;
    private GridView gvResults;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImageResults;
    private String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
    private String parameters = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        imageResults = new ArrayList<ImageResult>();
        // Attaches the data source to an adapter
        aImageResults = new ImageResultsAdapter(this, imageResults);
        // link the adapter to the adapterview (gridview)
        gvResults.setAdapter(aImageResults);

    }

    private void setupViews() {
        etQuery = (EditText)findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Launch the image display activity
                // Creating an intent
                Intent intent = new Intent(SearchActivity.this, ImageDisplayActivity.class);
                // Get the image result to display
                ImageResult result = imageResults.get(position);
                // Pass image result into the intent
                intent.putExtra("result", result); // need to either be serializable or parcelable
                startActivity(intent);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                onImageSearch((page - 1) * 8);
            }
        });
    }

    public void onClick(View v) {
        // clear the existing images from the array (in cases where its a new search)
        imageResults.clear();
        searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8";
        onImageSearch(0);
    }

    public void onImageSearch( int offset) {
        String query = etQuery.getText().toString();
        Toast.makeText(this, "Search for: " + query, Toast.LENGTH_SHORT).show();
        AsyncHttpClient client = new AsyncHttpClient();
        // https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=android&rsz=8
            searchUrl += parameters + "&start=" + offset + "&q=" + query;
            Log.i("legochen", searchUrl);
            client.get(searchUrl, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.d("legochen", response.toString());
                try {
                    JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");

                    // When you make to the adapter, it does modify the underlying data
                    aImageResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                    aImageResults.notifyDataSetChanged();
                } catch (JSONException e) {
//                    e.printStackTrace();
                }
                Log.i("INFO", imageResults.toString());
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK && requestCode == 0) {
            String result = data.getStringExtra("parameters");
            Toast.makeText(this, result, Toast.LENGTH_LONG);

            if (data.hasExtra("parameters")) {
                parameters = data.getExtras().getString("parameters");
                Log.i("legochen", parameters);
                Log.i("legochen", "onActivityResult 2");

            }
            Log.i("legochen", "onActivityResult");

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(SearchActivity.this, SearchSettingActivity.class);
            startActivityForResult(intent, 0);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
