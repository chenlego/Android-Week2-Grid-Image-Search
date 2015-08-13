package legochen.yahoo.com.gridimagesearch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.View.OnClickListener;


public class SearchSettingActivity extends Activity {

    Spinner spImageSize;
    Spinner spImageColor;
    Spinner spImageType;
    EditText etSiteFilter;
    String parameters;
    String spImageSizeTxt = "";
    String spImageColorTxt = "";
    String spImageTypeTxt = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_setting);
        setupView();
        Log.i("legochen", "SearchSettingActivity - onCreate ");
        parameters = "";
    }

    private void setupView() {
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageColor = (Spinner) findViewById(R.id.spImageColor);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
    }

    public void save(View v) {
        // String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&=imgcolor=black" + "&imgsz=huge" + "imgtype=face" + "as_sitesearch=yahoo.com";

        Intent data = new Intent();
        String etSiteFilterTxt = etSiteFilter.getText().toString();
        spImageColorTxt = spImageColor.getItemAtPosition(spImageColor.getSelectedItemPosition()).toString().toLowerCase();
        spImageSizeTxt = spImageSize.getItemAtPosition(spImageSize.getSelectedItemPosition()).toString().toLowerCase();
        spImageTypeTxt = spImageType.getItemAtPosition(spImageType.getSelectedItemPosition()).toString().toLowerCase();

        if (!etSiteFilterTxt.equals("")) {
            parameters = "&as_sitesearch=" + etSiteFilterTxt;
        }
        if (!spImageSizeTxt.equals("any type")) {
            parameters += "&imgsz=" + spImageSize.getSelectedItemPosition();
        }
        if (!spImageColorTxt.equals("any type")) {
            parameters += "&imgcolor=" + spImageColorTxt;
        }
        if (!spImageTypeTxt.equals("any type")) {
            parameters += "&imgtype=" + spImageTypeTxt;
        }
        data.putExtra("parameters", parameters);
        Log.i("legochen", "SearchSettingActivity: " + parameters);
        setResult(RESULT_OK, data);
        super.finish();
    }

}
