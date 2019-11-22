package com.example.if26new;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.if26new.Model.PlaylistModel;
import com.example.if26new.Model.SinglePlaylistModel;

import java.util.List;
import java.util.regex.Pattern;

public class SearchViewAdapteur  extends RecyclerView.Adapter<SearchViewAdapteur.MyViewHolder> {

    // FOR DATA
    private List<String> allSong;
    private SaveMyMusicDatabase db;
    private Pattern slip=Pattern.compile(" - ");
    private Pattern slip2=Pattern.compile(" By ");
    private Context context;
    public SearchViewAdapteur (List<String> all){
        allSong=all;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView  textView;
        private SaveMyMusicDatabase db;
        private Pattern slip=Pattern.compile(" - ");
        private Pattern slip2=Pattern.compile(" By ");
        private ImageView photo;
        private ImageButton like;

        public MyViewHolder(View v) {
            super(v);
            db=SaveMyMusicDatabase.getInstance(v.getContext());
            textView = v.findViewById(R.id.fragment_main_item_title);
            like=v.findViewById(R.id.likeSearch);
            like.setImageResource(R.drawable.likenoclick);
            textView.setOnClickListener(this);
            photo=v.findViewById(R.id.imageViewForResearch);
        }
        public void onClick(View v){
            String[] sp=slip.split(textView.getText().toString());
            System.out.println("TEXT VIEW "+textView.getText().toString());
            if (sp[1].equals("Album")){
                Bundle bundle=new Bundle();
                bundle.putString("ALBUM_NAME",sp[0]);
                bundle.putInt("ALBUM_IMAGE_ID",db.mAlbumDao().getAlbumFromName(sp[0]).getImage());
                bundle.putInt("ALBUM_ID",db.mAlbumDao().getAlbumFromName(sp[0]).getId());
                bundle.putString("ARTIST_NAME",db.mArtistDao().getArtistFromId(db.mAlbumDao().getAlbumFromName(sp[0]).getArtistId()).getName());
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                bundle.putInt("IS_FROM_ARTIST_VIEW",0);
                Intent playListActivity = new Intent(v.getContext(), Album_view.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);
            } else if(sp[1].equals("Artist")){
                Bundle bundle=new Bundle();
                bundle.putString("ARTIST_NAME",sp[0]);
                bundle.putInt("ARTIST_IMAGE_ID",db.mArtistDao().getArtistFromName(sp[0]).getImage());
                bundle.putInt("ARTIST_BIO",db.mArtistDao().getArtistFromName(sp[0]).getBio());
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                Intent playListActivity = new Intent(v.getContext(), ActivityArtist.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);
            }else {
                String[] artistNameFromSingle = slip2.split(sp[1]);
                Bundle bundle=new Bundle();
                bundle.putString("SONG_NAME",sp[0]);
                bundle.putString("ARTIST_NAME",artistNameFromSingle[1]);
                bundle.putInt("ALBUM_ID",0);
                bundle.putString("CONTEXT","SearchViewActivity");
                bundle.putString("FRAGMENT_NAME","SearchViewFragment");
                Intent playListActivity = new Intent(v.getContext(), Listening.class);
                playListActivity.putExtras(bundle);
                v.getContext().startActivity(playListActivity);

            }
        }
    }
    public SearchViewAdapteur.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        context = parent.getContext();
        db=SaveMyMusicDatabase.getInstance(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_view_layout, parent, false);
        SearchViewAdapteur.MyViewHolder vh = new SearchViewAdapteur.MyViewHolder(view);
        return vh;
    }
    public void onBindViewHolder(final MyViewHolder viewHolder, int position) {
        viewHolder.textView.setText(allSong.get(position));
        db=SaveMyMusicDatabase.getInstance(context);
        String[] sp=slip.split(allSong.get(position));
        String[] artistNameFromSingle = slip2.split(sp[1]);
        if (artistNameFromSingle.length==1){
            if (artistNameFromSingle[0].equals("Artist")){
                viewHolder.photo.setImageResource(db.mArtistDao().getArtistFromName(sp[0]).getImage());
            }else{
                viewHolder.photo.setImageResource(db.mAlbumDao().getAlbumFromName(sp[0]).getImage());
            }
        }else{
            viewHolder.photo.setImageResource(db.mArtistDao().getArtistFromName(artistNameFromSingle[1]).getImage());
        }
        viewHolder.photo.setAdjustViewBounds(true);
        viewHolder.photo.setScaleType(ImageView.ScaleType.FIT_CENTER);

        /*PlaylistModel playlistLike=db.mPlaylistDao().getPlaylistFromUserAndName(db.getActualUser(),"Favorite");
        SinglePlaylistModel[] allSingles=db.mSinglePlaylistDao().getSinglesFromPlaylist(playlistLike.getId());
        for (int j=0;j<allSingles.length;j++){
            if ((allSingles[j].getSongName().equals(sp[0]))&&(allSingles[j].getArtistName().equals(artistNameFromSingle[1]))){
                viewHolder.like.setImageResource(R.drawable.likeonclick);
            }
        }*/
        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.like.getDrawable().getConstantState().equals(v.getContext().getDrawable(R.drawable.likenoclick).getConstantState())){
                    viewHolder.like.setImageResource(R.drawable.likeonclick);
                }else{
                    viewHolder.like.setImageResource(R.drawable.likenoclick);
                }
            }
        });
    }
    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST
    public int getItemCount() {
        return this.allSong.size();
    }
}
