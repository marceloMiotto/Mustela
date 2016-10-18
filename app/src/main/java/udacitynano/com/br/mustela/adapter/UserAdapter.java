package udacitynano.com.br.mustela.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import udacitynano.com.br.mustela.DetailActivity;
import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.model.User;


public class UserAdapter extends RecyclerView.Adapter<UserAdapter.DataObjectHolder> {


    private ArrayList<User> mUsers;
    private Context mContext;
    private StringBuilder url = new StringBuilder(0);

    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView  userName;
        ImageView userPhoto;

        public DataObjectHolder(View itemView) {
            super(itemView);
            userName   = (TextView) itemView.findViewById(R.id.textView_user_name);
            userPhoto  = (ImageView) itemView.findViewById(R.id.imageView_user_photo);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            User usr = mUsers.get(position);
            Intent intent = new Intent(mContext, DetailActivity.class);
            mContext.startActivity(intent);
        }
    }

    public UserAdapter(Context context,ArrayList<User> myDataset) {
        mContext = context;
        mUsers = myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_card_view, parent, false);

        return new DataObjectHolder(view);

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.userName.setText(mUsers.get(position).getUserName());
        holder.userPhoto.setImageResource(R.mipmap.ic_launcher);

    }

    public void addItem(User dataObj, int index) {
        mUsers.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {
        mUsers.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {

        return mUsers.size();
    }

}