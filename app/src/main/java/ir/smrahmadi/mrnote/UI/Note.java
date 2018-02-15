package ir.smrahmadi.mrnote.UI;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import java.io.File;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.myinnos.awesomeimagepicker.activities.AlbumSelectActivity;
import in.myinnos.awesomeimagepicker.helpers.ConstantsCustomGallery;
import in.myinnos.awesomeimagepicker.models.Image;
import ir.smrahmadi.mrnote.Adapter.fontsAadapter;
import ir.smrahmadi.mrnote.R;
import ir.smrahmadi.mrnote.Tools.MyMovementMethod;
import petrov.kristiyan.colorpicker.ColorPicker;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.COLLAPSED;
import static com.sothree.slidinguppanel.SlidingUpPanelLayout.PanelState.EXPANDED;
import static ir.smrahmadi.mrnote.R.id.Note_imgAlignLeft;
import static ir.smrahmadi.mrnote.Tools.DrawableTools.Base64ToBitmap;
import static ir.smrahmadi.mrnote.Tools.DrawableTools.BitmapToBase64;
import static ir.smrahmadi.mrnote.app.DB;
import static ir.smrahmadi.mrnote.app.colors;
import static ir.smrahmadi.mrnote.app.fonts;
import static ir.smrahmadi.mrnote.app.fontsName;

public class Note extends AppCompatActivity {


    @BindView(R.id.sliding_layout)
    protected com.sothree.slidinguppanel.SlidingUpPanelLayout sliding_layout;


    @BindView(R.id.Note_imgTitle) protected ImageView imgTitle;
    @BindView(R.id.Note_edtTitle) protected EditText edtTitle;
    @BindView(R.id.Note_edtNote) protected EditText edtNote;


    @BindView(R.id.Note_mainLayout) protected LinearLayout mainLayout;

    @BindView(Note_imgAlignLeft) protected ImageView imgAlignLeft;
    @BindView(R.id.Note_imgAlignCenter) protected ImageView imgAlignCenter;
    @BindView(R.id.Note_imgAlignRight) protected ImageView imgAlignRight;

    @BindView(R.id.Note_imgBold) protected ImageView imgBold;
    @BindView(R.id.Note_imgItalic) protected ImageView imgItalic;

    @BindView(R.id.Note_imgTextSize) protected ImageView imgTextSize;
    @BindView(R.id.Note_imgTextColor) protected ImageView imgTextColor;
    @BindView(R.id.Note_imgFont) protected ImageView imgFont;

    @BindView(R.id.Note_imgBackgroundColor) protected ImageView imgBackgroundColor;
    @BindView(R.id.Note_imgSetImage) protected ImageView imgSetImage;


    String textAlignment;

    int bold;
    int italic;

    int textSize;
    int textColor;
    int textFont;


    int backgroundColor;
    int setImage;


    int bookmark;


    int id;
    boolean newNote ;


    boolean leastChange =false ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_note);
        ButterKnife.bind(this);




        Bundle extras = getIntent().getExtras();


        newNote = extras.getBoolean("newNote");

        Log.e("newNote",""+newNote);





//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setDisplayShowCustomEnabled(true);
//        getSupportActionBar().setCustomView(R.layout.note_action_bar);
//        View view = getSupportActionBar().getCustomView();
//
//        TextView txtChanges = (TextView) view.findViewById(R.id.Note_txtChanges);
//
//        ImageButton action_bar_back = (ImageButton) view.findViewById(R.id.action_bar_back);
//
//        action_bar_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        ImageButton action_bar_forward = (ImageButton) view.findViewById(R.id.action_bar_forward);
//
//        action_bar_forward.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "Forward Button is clicked", Toast.LENGTH_LONG).show();
//            }
//        });


        if (newNote) {



            textAlignment = "l";

            bold = 0;
            italic = 0;

            textSize = 25;
            textColor = 0;
            textFont = 0;

            backgroundColor = 1;
            setImage = 0;

            bookmark=0;
        } else {

            id = extras.getInt("id");
            GetNote(id);

        }

        switch (textAlignment) {

            case "l":
                edtNote.setGravity(Gravity.LEFT);
                imgAlignLeft.setColorFilter(Color.parseColor("#2196F3"));
                break;
            case "c":
                edtNote.setGravity(Gravity.CENTER);
                imgAlignCenter.setColorFilter(Color.parseColor("#2196F3"));
                break;
            case "r":
                edtNote.setGravity(Gravity.RIGHT);
                imgAlignRight.setColorFilter(Color.parseColor("#2196F3"));
                break;
        }

        edtNote.setTypeface(fonts.get(textFont));

        switch (bold) {

            case 0:
                if (italic == 0)
                    edtNote.setTypeface(fonts.get(textFont));
                else {
                    edtNote.setTypeface(fonts.get(textFont), Typeface.ITALIC);
                    imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                }
                break;

            case 1:
                if (italic == 0) {
                    edtNote.setTypeface(fonts.get(textFont), Typeface.BOLD);
                    imgBold.setColorFilter(Color.parseColor("#2196F3"));
                } else {
                    edtNote.setTypeface(fonts.get(textFont), Typeface.BOLD_ITALIC);
                    imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                    imgBold.setColorFilter(Color.parseColor("#2196F3"));
                }
                break;
        }




        edtNote.setTextSize(textSize);

        edtNote.setTextColor(Color.parseColor(colors[textColor]));


        edtTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                leastChange =true;
            }
        });


        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                leastChange =true;
            }
        });



        mainLayout.setBackgroundColor(Color.parseColor(colors[backgroundColor]));


        edtNote.setLinksClickable(true);
        edtNote.setAutoLinkMask(Linkify.WEB_URLS);
        edtNote.setMovementMethod(MyMovementMethod.getInstance());
        //If the edit text contains previous text with potential links
        Linkify.addLinks(edtNote, Linkify.WEB_URLS);


        edtNote.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {

                Linkify.addLinks(s, Linkify.WEB_URLS);

            }
        });


        imgAlignLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAlignLeft.setColorFilter(Color.parseColor("#2196F3"));
                imgAlignCenter.setColorFilter(Color.parseColor("#000000"));
                imgAlignRight.setColorFilter(Color.parseColor("#000000"));
                edtNote.setGravity(Gravity.LEFT);
                textAlignment = "l";
                leastChange =true;
            }
        });


        imgAlignCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAlignLeft.setColorFilter(Color.parseColor("#000000"));
                imgAlignCenter.setColorFilter(Color.parseColor("#2196F3"));
                imgAlignRight.setColorFilter(Color.parseColor("#000000"));
                edtNote.setGravity(Gravity.CENTER);
                textAlignment = "c";
                leastChange =true;
            }
        });


        imgAlignRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imgAlignLeft.setColorFilter(Color.parseColor("#000000"));
                imgAlignCenter.setColorFilter(Color.parseColor("#000000"));
                imgAlignRight.setColorFilter(Color.parseColor("#2196F3"));
                edtNote.setGravity(Gravity.RIGHT);
                textAlignment = "r";
                leastChange =true;
            }
        });


        imgBold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leastChange =true;

                switch (bold) {

                    case 0:
                        if (italic == 0) {
                            edtNote.setTypeface(fonts.get(textFont), Typeface.BOLD);
                            imgBold.setColorFilter(Color.parseColor("#2196F3"));
                        } else {
                            edtNote.setTypeface(fonts.get(textFont), Typeface.BOLD_ITALIC);
                            imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                            imgBold.setColorFilter(Color.parseColor("#2196F3"));
                        }
                        bold = 1;
                        break;

                    case 1:
                        if (italic == 0) {
                            edtNote.setTypeface(fonts.get(textFont));
                            imgBold.setColorFilter(Color.parseColor("#000000"));
                            imgItalic.setColorFilter(Color.parseColor("#000000"));
                        } else {
                            edtNote.setTypeface(fonts.get(textFont), Typeface.ITALIC);
                            imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                            imgBold.setColorFilter(Color.parseColor("#000000"));
                        }
                        bold = 0;
                        break;
                }

            }
        });


        imgItalic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                leastChange =true;

                switch (italic) {
                    case 0:
                        if (bold == 0) {
                            edtNote.setTypeface(edtNote.getTypeface(), Typeface.ITALIC);
                            imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                        } else {
                            edtNote.setTypeface(edtNote.getTypeface(), Typeface.BOLD_ITALIC);
                            imgItalic.setColorFilter(Color.parseColor("#2196F3"));
                            imgBold.setColorFilter(Color.parseColor("#2196F3"));
                        }
                        italic = 1;
                        break;

                    case 1:
                        if (bold == 0) {
                            edtNote.setTypeface(fonts.get(textFont));
                            imgBold.setColorFilter(Color.parseColor("#000000"));
                            imgItalic.setColorFilter(Color.parseColor("#000000"));
                        } else {
                            edtNote.setTypeface(edtNote.getTypeface(), Typeface.BOLD);
                            imgBold.setColorFilter(Color.parseColor("#2196F3"));
                            imgItalic.setColorFilter(Color.parseColor("#000000"));
                        }
                        italic = 0;
                        break;
                }

            }
        });


        imgTextSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTextSizeDialog();
                sliding_layout.setPanelState(COLLAPSED);
            }
        });


        imgTextColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTextColorDialog();
                sliding_layout.setPanelState(COLLAPSED);
            }
        });


        imgBackgroundColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showbackgroundColorDialog();
                sliding_layout.setPanelState(COLLAPSED);
            }
        });

        imgFont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFontDialog();
                sliding_layout.setPanelState(COLLAPSED);
            }
        });

        imgSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageDialog();
                sliding_layout.setPanelState(COLLAPSED);
            }
        });


    }

    @Override
    public void onBackPressed() {


        if (sliding_layout.getPanelState() == EXPANDED) {
            sliding_layout.setPanelState(COLLAPSED);
            return;
        }

        if (newNote){

            if(imgTitle.getDrawable()!=null || !TextUtils.isEmpty(edtTitle.getText()) || !TextUtils.isEmpty(edtNote.getText())){

                        saveNote();
                        finish();

            }else{
                super.onBackPressed();
            }
        }else{
            if(leastChange){


                        updateNote(id);
                        finish();



            }else
            super.onBackPressed();
        }


    }


    public void showTextSizeDialog() {
        final AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
        final SeekBar seek = new SeekBar(this);
        seek.setMax(25);
        seek.setProgress(textSize-10);
        seek.setKeyProgressIncrement(1);

        popDialog.setTitle("Text Size :");
        popDialog.setView(seek);


        popDialog.show();


        seek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                textSize = 10 + i;
                edtNote.setTextSize(textSize);
                leastChange =true;


            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });





    }

    public void showTextColorDialog(){

        final ColorPicker colorPicker = new ColorPicker(Note.this);
        ArrayList<String> arrayColor = new ArrayList<String>();

        for(int i=0 ; i<colors.length;i++)
            arrayColor.add(colors[i]);
        colorPicker.setColors(arrayColor);

        colorPicker.setTitle("Choose the text color");


        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                // put code
            }

            @Override
            public void onCancel(){
                // put code
            }
        }).addListenerButton("Choose", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int selectedColor) {
                // put code
                if(position==-1){
                    colorPicker.dismissDialog();
                    return;
                }
                textColor = position;
                edtNote.setTextColor(Color.parseColor(colors[textColor]));
                colorPicker.dismissDialog();
                leastChange =true;
            }
        }).disableDefaultButtons(true).setRoundColorButton(true).setColumns(4).show();


    }

    public void showbackgroundColorDialog(){
        final ColorPicker colorPicker = new ColorPicker(Note.this);
        ArrayList<String> arrayColor = new ArrayList<String>();

        for(int i=0 ; i<colors.length;i++)
            arrayColor.add(colors[i]);
        colorPicker.setColors(arrayColor);

        colorPicker.setTitle("Choose the background color");


        colorPicker.setOnChooseColorListener(new ColorPicker.OnChooseColorListener() {
            @Override
            public void onChooseColor(int position,int color) {
                // put code
            }

            @Override
            public void onCancel(){
                // put code
            }
        }).addListenerButton("Choose", new ColorPicker.OnButtonListener() {
            @Override
            public void onClick(View v, int position, int selectedColor) {
                // put code
                if(position==-1){
                    colorPicker.dismissDialog();
                    return;
                }
                backgroundColor = position;
                mainLayout.setBackgroundColor(Color.parseColor(colors[backgroundColor]));
                colorPicker.dismissDialog();
                leastChange =true;
            }
        }).disableDefaultButtons(true).setColumns(4).show();


    }

    public void showFontDialog(){

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(Note.this);
        builderSingle.setTitle("Choose the font : ");

        fontsName.clear();

        for(int i=0 ; i<fonts.size();i++){

            fontsName.add("Font"+" | "+ "فونت" );
        }

        final fontsAadapter fonts2 = new fontsAadapter(Note.this,fontsName , fonts);

        builderSingle.setAdapter(fonts2, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                edtNote.setTypeface(fonts.get(which));
                textFont=which;
                leastChange =true;
            }
        });
        builderSingle.show();

    }


    public void showImageDialog(){

        if(setImage==0){

            Intent intent = new Intent(this, AlbumSelectActivity.class);
            intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
            startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);



        }else {

            AlertDialog.Builder builderSingle = new AlertDialog.Builder(Note.this);

            final ArrayAdapter<String> adapter = new ArrayAdapter<String>(Note.this, android.R.layout.select_dialog_singlechoice);

            adapter.add("Set new image for this Note");
            adapter.add("Delete image");

            builderSingle.setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(which==0){

                        Intent intent = new Intent(Note.this, AlbumSelectActivity.class);
                        intent.putExtra(ConstantsCustomGallery.INTENT_EXTRA_LIMIT, 1); // set limit for image selection
                        startActivityForResult(intent, ConstantsCustomGallery.REQUEST_CODE);

                    }else{

                        setImage=0;
                        imgTitle.setImageDrawable(null);
                        leastChange =true;

                    }
                }
            });
            builderSingle.show();
        }

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ConstantsCustomGallery.REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {
            //The array list has the image paths of the selected images
            ArrayList<Image> images = data.getParcelableArrayListExtra(ConstantsCustomGallery.INTENT_EXTRA_IMAGES);
            setImage=1;

            File imgFile = new  File(images.get(0).path);

            if(imgFile.exists()){

                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());

                imgTitle.setImageBitmap(myBitmap);

                leastChange =true;

            }

        }
    }




    public void saveNote(){

        String Title;
        String Note;

        if(TextUtils.isEmpty(edtTitle.getText().toString())){
            Title = "Untitle";
        }else
            Title=edtTitle.getText().toString();

        if(TextUtils.isEmpty(edtNote.getText().toString())){
            Note = "";
        }else
            Note=edtNote.getText().toString();



        String byteImageTitle;

        if (imgTitle.getDrawable() == null){
            byteImageTitle="noImage";
        }else {

            BitmapDrawable drawable = (BitmapDrawable) imgTitle.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

             byteImageTitle = BitmapToBase64(bitmap);


        }




        String InsertQuery = "INSERT INTO notes (title,text,image,font,textSize,textColor,textAlignment,bold,italic,backgroundColor,bookmark) VALUES (\""+Title+"\",\""+Note+"\",\""+byteImageTitle+"\","+textFont+","+textSize+","+textColor+",\""+textAlignment+"\","+bold+","+italic+","+backgroundColor+","+bookmark+")";


        Cursor cursorAddNote = DB.rawQuery(InsertQuery, null);
        cursorAddNote.moveToFirst();

    }

    public void GetNote(int id){

        String SelectQuery = "SELECT * FROM notes WHERE id="+id;
        Cursor cursor = DB.rawQuery(SelectQuery, null);
        cursor.moveToFirst();


        textAlignment = cursor.getString(cursor.getColumnIndex("textAlignment"));

        bold = cursor.getInt(cursor.getColumnIndex("bold"));
        italic = cursor.getInt(cursor.getColumnIndex("italic"));

        textSize = cursor.getInt(cursor.getColumnIndex("textSize"));
        textColor = cursor.getInt(cursor.getColumnIndex("textColor"));
        textFont = cursor.getInt(cursor.getColumnIndex("font"));
        backgroundColor = cursor.getInt(cursor.getColumnIndex("backgroundColor"));


        String imagetitle = cursor.getString(cursor.getColumnIndex("image"));

        if(imagetitle.equals("noImage")) {
            setImage = 0;

        }else if (!imagetitle.equals("noImage")){
            setImage = 1;
            imgTitle.setImageBitmap(Base64ToBitmap(imagetitle));
        }


        edtTitle.setText(cursor.getString(cursor.getColumnIndex("title")));
        edtNote.setText(cursor.getString(cursor.getColumnIndex("text")));

    }

    public void updateNote(int id){


        String Title;
        String Note;

        if(TextUtils.isEmpty(edtTitle.getText().toString())){
            Title = "Untitle";
        }else
            Title=edtTitle.getText().toString();

        if(TextUtils.isEmpty(edtNote.getText().toString())){
            Note = "";
        }else
            Note=edtNote.getText().toString();



        String byteImageTitle;

        if (imgTitle.getDrawable() == null){
            byteImageTitle="noImage";
        }else {

            BitmapDrawable drawable = (BitmapDrawable) imgTitle.getDrawable();
            Bitmap bitmap = drawable.getBitmap();

            byteImageTitle = BitmapToBase64(bitmap);

        }


            String UpdateQuery = "UPDATE notes SET title=\""+Title+"\", text=\""+Note+"\", image=\""+byteImageTitle+"\" , font="+textFont+", textSize="+textSize+", textColor="+textColor+", textAlignment=\""+textAlignment+"\" , bold="+bold+", italic="+italic+", backgroundColor="+backgroundColor+", bookmark="+bookmark+" WHERE id="+id;

            Cursor cursorAddNote = DB.rawQuery(UpdateQuery, null);
            cursorAddNote.moveToFirst();




    }
}

