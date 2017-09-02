package texteditor.pratilipi.com.texteditor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements TextWatcher{

    EditText editText;
    TextView wordCountText;
    String finalOutput="";
    Spanned st;
    private String TAG ="MAINACTIVITY";
    MyHelper myHelper;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyHelper(this);
        finalOutput = myHelper.getAllData().content;
        id = myHelper.getAllData().id;
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(finalOutput);
        editText.addTextChangedListener(this);
        wordCountText = (TextView) findViewById(R.id.textView);
        wordCountText.setText(checkWordCount()+"");
        editText.setCustomSelectionActionModeCallback(new ActionMode.Callback() {
            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.selection_menu, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.bold:
                        Toast.makeText(getApplicationContext(), "Bold", Toast.LENGTH_SHORT).show();
                        finalOutput=getSelectedText("bold");
                        editText.setText(finalOutput);
                        return true;
                    case R.id.italics:
                        Toast.makeText(getApplicationContext(), "Italics", Toast.LENGTH_SHORT).show();
                        finalOutput=getSelectedText("italics");
                        editText.setText(finalOutput);
                        return true;
                    case R.id.underlined:
                        Toast.makeText(getApplicationContext(), "UnderLined", Toast.LENGTH_SHORT).show();
                        finalOutput=getSelectedText("underlined");
                        editText.setText(finalOutput);
                        return true;
                    case R.id.bullets:
                        Toast.makeText(getApplicationContext(), "Bullets", Toast.LENGTH_SHORT).show();
                        if (finalOutput.contains("<align>")){
                            Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_SHORT).show();
                        }else {
                            finalOutput = getSelectedText("bullets");
                            editText.setText(finalOutput);
                        }
                        return true;
                    case R.id.blockquote:
                        Toast.makeText(getApplicationContext(), "BlockQuote", Toast.LENGTH_SHORT).show();
                        if(finalOutput.contains("<align>")){
                            Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_SHORT).show();
                        }else {
                            finalOutput = getSelectedText("blockquote");
                            editText.setText(finalOutput);
                        }
                        return true;
                    case R.id.centreAligned:
                        Toast.makeText(getApplicationContext(), "centreAlign", Toast.LENGTH_SHORT).show();
                        if(finalOutput.contains("<blockquote>")||finalOutput.contains("<ul>")){
                            Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_SHORT).show();
                        }else {
                            finalOutput = getSelectedText("centrealign");
                            editText.setText(finalOutput);
                        }
                        return true;
                    case R.id.leftAligned:
                        Toast.makeText(getApplicationContext(), "leftAlign", Toast.LENGTH_SHORT).show();
                        if(finalOutput.contains("<blockquote>")||finalOutput.contains("<ul>")){
                            Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_SHORT).show();
                        }else {
                            finalOutput = getSelectedText("leftalign");
                            editText.setText(finalOutput);
                        }
                        return true;
                    case R.id.rightAligned:
                        Toast.makeText(getApplicationContext(), "rightAlign", Toast.LENGTH_SHORT).show();
                        if(finalOutput.contains("<blockquote>")||finalOutput.contains("<ul>")){
                            Toast.makeText(MainActivity.this, "not possible", Toast.LENGTH_SHORT).show();
                        }else {
                            finalOutput = getSelectedText("rightalign");
                            editText.setText(finalOutput);
                        }
                        return true;
                }
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {

            }
        });
    }

    private String format(String s,String decision){

        Log.v(TAG,decision);
        String temp;

        switch (decision){
            case "bold":
                temp =  "<b>"+s+"</b>";
                break;
            case "italics":
                temp = "<i>"+s+"</i>";
                break;
            case "underlined":
                temp =  "<u>"+s+"</u>";
                break;
            case "blockquote":
                temp =  "<blockquote>"+s+"</blockquote>";
                break;
            case "bullets":
                temp =  "<ul>"+bullet(s)+"</ul>";
                break;
            case "centrealign":
                temp =  "<div style=\"text-align: center;\">"+s+"</div>";
                break;
            case "leftalign":
                temp =  "<div style=\"text-align: left;\">"+s+"</div>";
                break;
            case "rightalign":
                temp =  "<div style=\"text-align: right;\">"+s+"</div>";
                break;
            default:
                temp =  "";
                break;
        }

        return  temp;

    }

    private  String getSelectedText(String s){
        Log.v(TAG,"editText "+"getSelectedText: " +Html.toHtml(editText.getText()));


        int startSelection=editText.getSelectionStart();
        int endSelection=editText.getSelectionEnd();
        String selectedText = finalOutput.substring(startSelection, endSelection);
        String prevText = finalOutput.substring(0,startSelection);
        String endText = finalOutput.substring(endSelection);


        String findStr = "<ul>";
        int lastIndex = 0;
        int count1 = 0;

        while(lastIndex != -1){

            lastIndex = prevText.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count1 ++;
                lastIndex += findStr.length();
            }
        }

        findStr = "</ul>";
        lastIndex = 0;
        int count2 = 0;

        while(lastIndex != -1){

            lastIndex = prevText.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count2 ++;
                lastIndex += findStr.length();
            }
        }

        if ((count1!=count2 || selectedText.contains("<ul>"))&& s.equalsIgnoreCase("blockquote"))
        {
            Toast.makeText(this, "not possible", Toast.LENGTH_SHORT).show();
            return finalOutput;
        }
        findStr = "<blockquote>";
        lastIndex = 0;
        count1 = 0;

        while(lastIndex != -1){

            lastIndex = prevText.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count1 ++;
                lastIndex += findStr.length();
            }
        }

        findStr = "</blockquote>";
        lastIndex = 0;
        count2 = 0;

        while(lastIndex != -1){

            lastIndex = prevText.indexOf(findStr,lastIndex);

            if(lastIndex != -1){
                count2 ++;
                lastIndex += findStr.length();
            }
        }

        if ((count1!=count2 || selectedText.contains("<blockquote>"))&& s.equalsIgnoreCase("bullets"))
        {
            Toast.makeText(this, "not possible", Toast.LENGTH_SHORT).show();
            return finalOutput;
        }


        if (s.contains("leftalign")){
            if (finalOutput.contains("align")){

                finalOutput = finalOutput.replaceAll("left","left");
                finalOutput = finalOutput.replaceAll("right","left");
                finalOutput = finalOutput.replaceAll("center","left");
            }
            else
            {
                finalOutput = "<div style=\"text-align: center;\">" + finalOutput + "</div>";
            }
            return finalOutput;
        }
        if (s.contains("rightalign")){
            if (finalOutput.contains("align")){

                finalOutput = finalOutput.replaceAll("left","right");
                finalOutput = finalOutput.replaceAll("right","right");
                finalOutput = finalOutput.replaceAll("center","right");
            }
            else
            {
                finalOutput = "<div style=\"text-align: right;\">" + finalOutput + "</div>";
            }
            return finalOutput;
        }
        if (s.contains("centrealign")){
            if (finalOutput.contains("align")){

                finalOutput = finalOutput.replaceAll("left","center");
                finalOutput = finalOutput.replaceAll("right","center");
                finalOutput = finalOutput.replaceAll("center","center");
            }
            else
            {
                finalOutput = "<div style=\"text-align: center;\">" + finalOutput + "</div>";
            }
            return finalOutput;
        }
        return prevText+format(selectedText,s)+endText;
    }

    public String bullet(String s) {
        String pad = "" ;
        String[] lines = s.split("\n");

        for (int i = 0; i < lines.length; i++) {
            if (TextUtils.isEmpty(lines[i].trim()))
            {
                pad+=(lines[i]);
            }
            else
            {
                pad+=("<li>" + lines[i].trim() +"</li>");
            }
        }
        return pad;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

        wordCountText.setText(checkWordCount()+"");

        finalOutput = (editText.getText()).toString();
        Log.v(TAG,"finalOutput:" + finalOutput);
        Log.v(TAG,"editText:" + editText.getText().toString());

        if(editText.getText().toString()==""){
            finalOutput = "";
        }

    }

    private int checkWordCount() {

        int sum =0;

        String[] lines = String.valueOf(editText.getText()).split("\n");
        for (int i = 0; i < lines.length; i++) {
            if(TextUtils.isEmpty(lines[i].trim()))
            {
                continue;
            }
            else
            {
                String str="";
                for (int j=0;j<lines[i].length();j++){

                    if(lines[i].charAt(j)=='<'){
                        while(j< lines[i].length() && lines[i].charAt(j)!='>')
                            j++;
                    }
                    else
                        str= str + lines[i].charAt(j);

                }
                String ar[] = str.trim().split("\\s+");
                sum+=ar.length;
            }
        }
        return sum;
    }

    public void submit(View view) {
        Intent intent = new Intent(this,Main2Activity.class);
        intent.putExtra("content",finalOutput);
        startActivity(intent);
    }

    public void save(View view) {
        if (id==0){
            myHelper.insertTable(finalOutput);
        }
        else
            myHelper.updateTable(id,finalOutput);
    }
}
