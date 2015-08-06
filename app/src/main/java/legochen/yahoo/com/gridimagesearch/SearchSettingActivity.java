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


public class SearchSettingActivity extends Activity implements OnItemSelectedListener {

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
    }

    private void setupView() {
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageColor = (Spinner) findViewById(R.id.spImageColor);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        spImageColor.setOnItemSelectedListener(this);
        spImageSize.setOnItemSelectedListener(this);
        spImageType.setOnItemSelectedListener(this);
        etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
        Button btnSave = (Button) findViewById(R.id.btnSave);
        btnSave.setOnClickListener(
                new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                        Intent data = new Intent();
                        String etSiteFilterTxt = etSiteFilter.getText().toString();
                        if (!etSiteFilterTxt.equals("")) {
                            parameters = "&as_sitesearch=" + etSiteFilterTxt;
                        }
                        if (!spImageSizeTxt.equals("")) {
                            parameters += "&imgsz=" + spImageSizeTxt;
                        }
                        if (!spImageColorTxt.equals("")) {
                            parameters += "&imgcolor=" + spImageColorTxt;
                        }
                        if (!spImageTypeTxt.equals("")) {
                            parameters += "&imgtype=" + spImageTypeTxt;
                        }
                        data.putExtra("parameters", parameters);
                        setResult(RESULT_OK, data);
                        finish();
                    }
                });
    }

    public void save() {
        // String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query + "&rsz=8" + "&=imgcolor=black" + "&imgsz=huge" + "imgtype=face" + "as_sitesearch=yahoo.com";

        Intent data = new Intent();
        String etSiteFilterTxt = etSiteFilter.getText().toString();
        if (!etSiteFilterTxt.equals("")) {
            parameters = "&as_sitesearch=" + etSiteFilterTxt;
        }
        if (!spImageSizeTxt.equals("")) {
            parameters += "&imgsz=" + spImageSizeTxt;
        }
        if (!spImageColorTxt.equals("")) {
            parameters += "&imgcolor=" + spImageColorTxt;
        }
        if (!spImageTypeTxt.equals("")) {
            parameters += "&imgtype=" + spImageTypeTxt;
        }
        data.putExtra("parameters", parameters);
        setResult(RESULT_OK, data);
        super.finish();
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("legochen", "test");
        // An item was selected. You can retrieve the selected item using
        String text = parent.getItemAtPosition(position).toString();

        if ( id == R.id.spImageColor) {
            spImageColorTxt = text;
        } else if ((long) id == R.id.spImageSize) {
            spImageSizeTxt = text;
        } else if ((long) id == R.id.spImageType) {
            spImageTypeTxt = text;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("legochen", "test");
    }
}
