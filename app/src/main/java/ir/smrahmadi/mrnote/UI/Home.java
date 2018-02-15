package ir.smrahmadi.mrnote.UI;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;


import java.util.ArrayList;

import ir.smrahmadi.mrnote.Adapter.noteAdapter;
import ir.smrahmadi.mrnote.R;


import ir.smrahmadi.mrnote.Tools.SpacesItemDecoration;

import static ir.smrahmadi.mrnote.app.*;



public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {





    private RecyclerView recyclerView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if(passStatus && !unlock){
            startActivity(new Intent(Home.this,Password.class));
            finish();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                YoYo.with(Techniques.Pulse)
                        .duration(200)
                        .repeat(2)
                        .playOn(findViewById(R.id.fab));

                Intent i = new Intent(Home.this, Note.class);
                i.putExtra("newNote",true);
                startActivity(i);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);


        homeNoteList = new ArrayList<>();
        adapter = new noteAdapter(this, homeNoteList);



        if(viewMode) {
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        }else
            recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));

        recyclerView.setAdapter(adapter);

        SpacesItemDecoration decoration = new SpacesItemDecoration(16);
        recyclerView.addItemDecoration(decoration);



        prepareNotes();


    }



    @Override
    protected void onResume() {

        homeNoteList.clear();
        prepareNotes();




        super.onResume();


    }




    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home, menu);
        MenuItem OkItem = menu.findItem(R.id.ActionBarHome_switchColumn);

        if (viewMode) {
            //in production you'd probably be better off keeping a reference to the item

            OkItem.setIcon(getResources().getDrawable(R.drawable.ic_view_stream_black_24dp))
                    .setTitle("Single-column view");

        } else {

            OkItem.setIcon(getResources().getDrawable(R.drawable.ic_view_compact_black_24dp))
                    .setTitle("Multi-column view");


        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean result = true;
        switch (item.getItemId()) {
            case R.id.ActionBarHome_switchColumn:
                if (viewMode) { //you could modify this to check the icon/text of the menu item



                    viewMode = false;
                    viewModeUpdate(0);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "List Mode", 700).show();

                    homeNoteList = new ArrayList<>();
                    adapter = new noteAdapter(this, homeNoteList);

                    RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
                    recyclerView.setLayoutManager(mLayoutManager);


                    prepareNotes();

                } else {

                    viewMode = true;
                    viewModeUpdate(1);
                    Snackbar.make(getWindow().getDecorView().getRootView(), "Gride Mode", 700).show();

                    homeNoteList = new ArrayList<>();
                    adapter = new noteAdapter(this, homeNoteList);




                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(adapter);





                    prepareNotes();

                }

                YoYo.with(Techniques.FlipInY)
                        .duration(400)
                        .playOn(findViewById(R.id.ActionBarHome_switchColumn));
                invalidateOptionsMenu(); //cause a redraw
                break;
            case R.id.ActionBarHome_Search :
                YoYo.with(Techniques.Swing)
                        .duration(700)
                        .playOn(findViewById(R.id.ActionBarHome_Search));
                startActivity(new Intent(Home.this,Search.class));
                break;
            default:
                result = super.onOptionsItemSelected(item);

        }
        return result;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new) {
            // Handle the camera action
            Intent i = new Intent(getApplicationContext(), Note.class);
            i.putExtra("newNote",true);
            startActivity(i);
        } else if (id == R.id.nav_bookmarks) {
            startActivity(new Intent(Home.this,Search.class));
            Toast.makeText(this, "Switch On Bookmark for only show Bookmarks", Toast.LENGTH_SHORT).show();

        }  else if (id == R.id.nav_settings) {
            startActivity(new Intent(Home.this,Settings.class));

        } else if (id == R.id.nav_About) {
            startActivity(new Intent(Home.this,About.class));
        } else if (id == R.id.nav_exit) {
            super.onBackPressed();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }




    private void viewModeUpdate(int mode){
        String UpdateQuery = "UPDATE user SET viewMode="+mode;
        Cursor cursor = DB.rawQuery(UpdateQuery, null);
        cursor.moveToFirst();
    }




}
