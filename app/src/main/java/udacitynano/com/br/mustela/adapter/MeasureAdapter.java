package udacitynano.com.br.mustela.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.data.MeasureContract;
import udacitynano.com.br.mustela.model.Measure;

public class MeasureAdapter extends RecyclerView.Adapter<MeasureAdapter.DataObjectHolder> {


    private ArrayList<Measure> mMeasures;
    private Context mContext;


    public class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView measureDateTime;
        TextView measureWeight;
        TextView measureFatPercentage;
        ImageButton imageButton;



        public DataObjectHolder(View itemView) {
            super(itemView);
            measureDateTime   = (TextView) itemView.findViewById(R.id.textView_measure_date);
            measureWeight   = (TextView) itemView.findViewById(R.id.textView_measure_weight);
            measureFatPercentage   = (TextView) itemView.findViewById(R.id.textView_measure_percentage);
            imageButton = (ImageButton) itemView.findViewById(R.id.imageButton_measure_delete);
            imageButton.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();

            switch (v.getId()){
                case R.id.imageButton_measure_delete:
                    deleteItem(position);
                    break;
                default:
                    break;
            }
        }
    }

    public MeasureAdapter(Context context,ArrayList<Measure> myDataset) {
        mContext = context;
        mMeasures= myDataset;
    }

    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent,
                                               int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.measure_item, parent, false);


        return new DataObjectHolder(view);

    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.measureDateTime.setText(String.format(mContext.getString(R.string.measure_date), mMeasures.get(position).getMeasureDateTime()));
        holder.measureWeight.setText(String.format(mContext.getString(R.string.measure_weight),String.valueOf(mMeasures.get(position).getMeasureWeight())));
        holder.measureFatPercentage.setText(String.format(mContext.getString(R.string.measure_percentage),String.valueOf(mMeasures.get(position).getMeasureFatPercentage())));

    }

    public void addItem(Measure dataObj, int index) {
        mMeasures.add(index, dataObj);
        notifyItemInserted(index);
    }

    public void deleteItem(int index) {

        Measure measure = new Measure();
        measure.deleteMeasures(mContext,MeasureContract.MeasureEntry.COLUMN_USER_KEY+" =? and "+ MeasureContract.MeasureEntry.COLUMN_PROJECT_KEY+" = ?", new String[] {String.valueOf(mMeasures.get(index).getMeasureUserId()),String.valueOf(mMeasures.get(index).getMeasureProjectId())});
      //
        Toast.makeText(mContext,mContext.getString(R.string.measure_item_deleted),Toast.LENGTH_SHORT).show();
        mMeasures.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount() {

        return mMeasures.size();
    }

}