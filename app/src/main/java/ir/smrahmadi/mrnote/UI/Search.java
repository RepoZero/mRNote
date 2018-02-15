package ir.smrahmadi.mrnote.UI;

import android.database.Cursor;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.smrahmadi.mrnote.Adapter.noteAdapter;
import ir.smrahmadi.mrnote.Model.noteModel;
import ir.smrahmadi.mrnote.R;
import ir.smrahmadi.mrnote.Tools.SpacesItemDecoration;

import static ir.smrahmadi.mrnote.app.DB;
import static ir.smrahmadi.mrnote.app.viewMode;

public class Search extends AppCompatActivity {



    private noteAdapter adapter;
    private List<noteModel> homeNoteList;

    public ArrayList<Integer> id = new ArrayList<Integer>();
    private ArrayList<String> title = new ArrayList<String>();
    private ArrayList<String> text = new ArrayList<String>();
    private ArrayList<String> imgTitle = new ArrayList<String>();
    private ArrayList<Integer> backgroundColor = new ArrayList<Integer>();
    private ArrayList<Integer> bookmark = new ArrayList<Integer>();
    
    
    @BindView(R.id.search_swImages) protected Switch swImages ;
    @BindView(R.id.search_swBookmarks) protected Switch swBookmarks ;
    @BindView(R.id.search_recycleView) protected RecyclerView recyclerView;

    EditText edt_search;


    boolean Images=false ;
    boolean Bookmarks=false ;

    
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);






        homeNoteList = new ArrayList<>();
        adapter = new noteAdapter(this, homeNoteList);



        if(viewMode) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);



        search(null,Images,Bookmarks);





        swImages.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Images = b;
                search(edt_search.getText().toString(),Images,Bookmarks);
            }
        });


        swBookmarks.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Bookmarks = b;
                search(edt_search.getText().toString(),Images,Bookmarks);
            }
        });










        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.search_action_bar);
        View view =getSupportActionBar().getCustomView();

          edt_search = (EditText) findViewById(R.id.edt_action_bar_search);

        edt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                search(edt_search.getText().toString(),Images,Bookmarks);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });








    }

    @Override
    protected void onResume() {

        homeNoteList.clear();
        search(edt_search.getText().toString(),Images,Bookmarks);
        super.onResume();


    }





    void search(String textSearch , boolean images , Boolean bookmarks) {

        homeNoteList.clear();

        String SelectQuery;

        if(TextUtils.isEmpty(textSearch)){
            SelectQuery = "SELECT * FROM notes";
        }else
            SelectQuery = "SELECT * FROM notes WHERE title LIKE '%"+textSearch+"%' OR text LIKE '%"+textSearch+"%'";

//        if(images) {
//            if(TextUtils.isEmpty(textSearch)){
//                SelectQuery = SelectQuery + " WHERE NOT (image='noImage') ";
//            }else
//                SelectQuery = SelectQuery + " AND NOT (image='noImage') ";
//        }

        Log.e("Query",SelectQuery);


        Cursor cursor = DB.rawQuery(SelectQuery, null);


        if (cursor != null && cursor.getCount() > 0) {

            id.clear();
            title.clear();
            text.clear();
            imgTitle.clear();
            backgroundColor.clear();
            bookmark.clear();


            if (cursor.moveToFirst()) {
                do {
                    int _id = cursor.getInt(cursor.getColumnIndex("id"));
                    String _title = cursor.getString(cursor.getColumnIndex("title"));
                    String _text = cursor.getString(cursor.getColumnIndex("text"));
                    String _imgTitle = cursor.getString(cursor.getColumnIndex("image"));
                    int _backgroundColor = cursor.getInt(cursor.getColumnIndex("backgroundColor"));
                    int _bookmark = cursor.getInt(cursor.getColumnIndex("bookmark"));

                    if (images && _imgTitle.equals("noImage"))
                        continue;

                    if (bookmarks && _bookmark==0)
                        continue;



                        id.add(_id);
                    title.add(_title);
                    text.add(_text);
                    backgroundColor.add(_backgroundColor);
                    imgTitle.add(_imgTitle);
                    bookmark.add(_bookmark);


                } while (cursor.moveToNext());
            }

            cursor.close();

            for (int i = 0; i < title.size(); i++) {
                noteModel b = new noteModel(id.get(i), title.get(i), text.get(i), imgTitle.get(i), backgroundColor.get(i), bookmark.get(i));
                homeNoteList.add(b);
            }

            adapter.notifyDataSetChanged();

        }else{
            homeNoteList.clear();
            adapter.notifyDataSetChanged();
        }
    }


}
