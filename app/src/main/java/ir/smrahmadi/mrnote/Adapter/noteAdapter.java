package ir.smrahmadi.mrnote.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import ir.smrahmadi.mrnote.Model.noteModel;
import ir.smrahmadi.mrnote.R;
import ir.smrahmadi.mrnote.UI.Note;


import static ir.smrahmadi.mrnote.Tools.DrawableTools.Base64ToBitmap;
import static ir.smrahmadi.mrnote.Tools.utils.shareText;
import static ir.smrahmadi.mrnote.app.DB;


public class noteAdapter extends RecyclerView.Adapter<noteAdapter.MyViewHolder> {

    private Context mContext;
    private List<noteModel> noteList;



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title ;
        public ImageView overflow;
        public ImageView img;
        public RelativeLayout mainLayout;





        public MyViewHolder(View view) {
            super(view);
            title= (TextView) view.findViewById(R.id.title);
            img = (ImageView) view.findViewById(R.id.img);
            overflow = (ImageView) view.findViewById(R.id.overflow);
            mainLayout = (RelativeLayout)view.findViewById(R.id.note_card_MainLayout);
        }
    }


    public noteAdapter(Context mContext, List<noteModel> noteList) {
        this.mContext = mContext;
        this.noteList = noteList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final noteModel note = noteList.get(position);
        holder.title.setText(note.getTitle());







        if(note.getImage().equals("noImage")) {
            holder.img.setVisibility(View.GONE);

        }else if (!note.getImage().equals("noImage")){

            holder.img.setVisibility(View.VISIBLE);
            Bitmap decodedByte = Base64ToBitmap(note.getImage());
            holder.img.setImageBitmap(decodedByte);
        }

//        if(note.getImage()!=null) {
//            holder.img.setImageBitmap(note.getImage());
//        }
//        else if(note.getImage()==null){
//            holder.img.setVisibility(View.GONE);
//        }




        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startactivityNote(note.getId());
            }
        });

        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startactivityNote(note.getId());
            }
        });


        holder.overflow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(holder.overflow,position);
            }
        });

        String[] Colorsb = { "#000000","#ffffff", "#00ddff", "#0099cc", "#33b5e5", "#669900", "#99cc00", "#ff8800", "#ffbb33", "#aa66cc", "#cc0000", "#ff4444"};


        holder.mainLayout.setBackgroundColor(Color.parseColor(Colorsb[note.getBackgroundColor()]));
    }

    /**
     * Showing popup menu when tapping on 3 dots
     */
    private void showPopupMenu(View view, int position) {
//        // inflate menu
        PopupMenu popup = new PopupMenu(mContext, view);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_note, popup.getMenu());


        MenuItem menuItem =  popup.getMenu().findItem(R.id.addToBookmarks);

        noteModel note = noteList.get(position);

        if(note.getBookmark()==1){
            menuItem.setTitle("Remove from Bookmark");
        }


        popup.setOnMenuItemClickListener(new MyMenuItemClickListener(position));
        popup.show();
    }


    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        private int position;

        public MyMenuItemClickListener(int position) {
            this.position = position;
        }

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {

                case R.id.open:
                    noteModel note = noteList.get(position);
                    startactivityNote(note.getId());
                    return true;

                case R.id.addToBookmarks:
                     note = noteList.get(position);
                     if(0 == note.getBookmark()){
                         updateBookmark(note.getId(),1);
                         note.setBookmark(1);
                         Toast.makeText(mContext, note.getTitle()+" Added To Bookmark", Toast.LENGTH_SHORT).show();
                     }else{
                         updateBookmark(note.getId(),0);
                         note.setBookmark(0);
                         Toast.makeText(mContext, note.getTitle()+" Remove From Bookmark", Toast.LENGTH_SHORT).show();
                     }
                    return true;

                case R.id.share :
                    note = noteList.get(position);
                    shareText(note.getTitle(),note.getText(),mContext);
                    return true;

                case R.id.copy:
                    note = noteList.get(position);
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText(" "+R.string.app_name, note.getTitle() + "\n" + note.getText());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, "Copied to ClipBoard", Toast.LENGTH_SHORT).show();
                    return true;

                case R.id.delete:


                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Do you want Delete this Note ?");
                    builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO
                            noteModel note = noteList.get(position);
                            deleteNote(note.getId());
                            noteList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position,noteList.size());

                            dialog.dismiss();

                        }
                    });
                    builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //TODO

                            dialog.dismiss();

                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();

                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    void startactivityNote(int id){

        Intent i = new Intent(mContext.getApplicationContext(), Note.class);
        i.putExtra("id", id);
        i.putExtra("newNoteg",false);
        mContext.startActivity(i);
    }



    private void updateBookmark(int id,int bookmark){
        String UpdateQuery = "UPDATE notes SET bookmark="+bookmark+" WHERE id="+id;
        Cursor cursor = DB.rawQuery(UpdateQuery, null);
        cursor.moveToFirst();
    }

    private void deleteNote(int id){
        String UpdateQuery = "DELETE FROM notes WHERE id="+id;
        Cursor cursor = DB.rawQuery(UpdateQuery, null);
        cursor.moveToFirst();
    }



}
