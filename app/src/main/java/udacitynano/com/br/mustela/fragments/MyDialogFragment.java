package udacitynano.com.br.mustela.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.model.Measure;
import udacitynano.com.br.mustela.model.User;
import udacitynano.com.br.mustela.util.Constant;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mCurrentWeight;
    private EditText mCurrentFatPercentage;
    private CalendarView mCalendarView;
    private Button mButtonSave;
    private User user;
    private int  projectId;
    private DialogInterface.OnDismissListener onDismissListener;


    // Empty constructor required for DialogFragment
    public MyDialogFragment() {

    }

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_name, container);
        mCurrentWeight = (EditText) view.findViewById(R.id.editText_current_weight);
        mCurrentFatPercentage = (EditText) view.findViewById(R.id.editText_current_percentage);
        mCalendarView = (CalendarView) view.findViewById(R.id.calendarView);
        mButtonSave = (Button) view.findViewById(R.id.button_save);

        mCurrentWeight.requestFocus();

        mButtonSave.setOnClickListener(this);
        getDialog().setTitle(getResources().getString(R.string.dialog_fragment_title));

        Bundle bundle = getActivity().getIntent().getExtras();
        user = bundle.getParcelable(Constant.INTENT_USER_DETAIL);
        projectId = getActivity().getIntent().getIntExtra(Constant.INTENT_USER_PROJECT,1);

        return view;
    }

    @Override
    public void onClick(View v) {

       if(saveMeasures()){
           Toast.makeText(getActivity(),"Measure added with success!",Toast.LENGTH_SHORT).show();

       }else{

           Toast.makeText(getActivity(),"Error adding the measure!",Toast.LENGTH_SHORT).show();
       }
    }

    public boolean saveMeasures() {

        Measure measure = new Measure();
        String weight = mCurrentWeight.getText().toString();
        String fatPer = mCurrentFatPercentage.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat(getActivity().getString(R.string.date_format_mask));
        Log.e("Debug","format mask "+getString(R.string.date_format_mask));
        String selectedDate = sdf.format(new Date(mCalendarView.getDate()));

        Log.e("Debug", "measure peso: " + weight + " fatper: " + fatPer + " data: " + selectedDate);

        long result = measure.addMeasures(getActivity(), user, selectedDate, weight, fatPer);

        if (result > 0) {
            return true;
        } else {
            return false;
        }

    }





}


