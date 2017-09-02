package texteditor.pratilipi.com.texteditor;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView textView = (TextView) findViewById(R.id.finalOutputText1);
        TextView textView1 = (TextView) findViewById(R.id.finalOutputText2);
        String st = getIntent().getStringExtra("content");
        Spanned s = Html.fromHtml(st,Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST_ITEM|
                Html.FROM_HTML_SEPARATOR_LINE_BREAK_LIST);
        textView.setText(s);
        textView1.setText(st);
        if (st.contains("left")&& st.contains("align")){
            textView.setGravity(Gravity.LEFT);
        }
        if ((st.contains("right")&& st.contains("align"))){
            textView.setGravity(Gravity.RIGHT);
        }

    }
}
