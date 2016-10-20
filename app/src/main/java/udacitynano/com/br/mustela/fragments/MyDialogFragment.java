package udacitynano.com.br.mustela.fragments;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import udacitynano.com.br.mustela.R;
import udacitynano.com.br.mustela.model.Measure;

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    private EditText mCurrentWeight;
    private EditText mCurrentFatPercentage;
    private CalendarView mCalendarView;
    private Button mButtonSave;


    public interface UserNameListener {
        void onFinishUserDialog(String user);
    }

    // Empty constructor required for DialogFragment
    public MyDialogFragment() {

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

        return view;
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getActivity(),"test",Toast.LENGTH_SHORT).show();

    }

    public void saveMeasures(){

        Measure measure = new Measure();
        measure.addMeasure(getActivity());

    }


}