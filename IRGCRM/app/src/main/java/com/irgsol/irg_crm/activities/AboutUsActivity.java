package com.irgsol.irg_crm.activities;

import android.content.Context;
import android.os.Bundle;
import android.webkit.WebView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.irgsol.irg_crm.R;
import com.irgsol.irg_crm.common.OprActivity;

public class AboutUsActivity extends AppCompatActivity {

    Toolbar toolbar;
    Context context;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        initView();
    }

    private void initView() {
        context = AboutUsActivity.this;
        toolbar = findViewById(R.id.toolbar);
        OprActivity.setUpToolbarWithTitle(toolbar, "About Us", context);
        webView = findViewById(R.id.webView);
        loadContent();
    }

    private void loadContent() {
        String myHtmlString = "<html><body>Lorem Ipsum is simply dummy text of the printing " +
                "and typesetting industry. Lorem Ipsum has been the industry's standard dummy" +
                " text ever since the 1500s, when an unknown printer took a galley of type and" +
                " scrambled it to make a type specimen book. It has survived not only five" +
                " centuries, but also the leap into electronic typesetting, remaining" +
                " essentially unchanged. It was popularised in the 1960s with the release" +
                " of Letraset sheets containing Lorem Ipsum passages, and more recently with" +
                " desktop publishing software like Aldus PageMaker including versions of " +
                "Lorem Ipsum.</body></html>";
        webView.loadData(myHtmlString, "text/html", null);

    }

    @Override
    public boolean onSupportNavigateUp() {
        OprActivity.finishActivity(context);
        return true;
    }

    @Override
    public void onBackPressed() {
        OprActivity.finishActivity(context);
    }
}
