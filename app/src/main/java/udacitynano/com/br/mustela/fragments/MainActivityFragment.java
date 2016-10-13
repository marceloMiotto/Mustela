package udacitynano.com.br.mustela.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import udacitynano.com.br.mustela.R;

public class MainActivityFragment extends Fragment implements MyDialogFragment.UserNameListener,View.OnClickListener {


    Button mDialogFragment;

    public MainActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_main, container, false);

        mDialogFragment = (Button) view.findViewById(R.id.showCustomFragment);
        mDialogFragment.setOnClickListener(this);

        return view;
    }

    @Override
    public void onFinishUserDialog(String user) {
        Toast.makeText(getActivity(), "Hello, " + user, Toast.LENGTH_SHORT).show();
    }

    public void onClick(View view) {
        // close existing dialog fragments
        FragmentManager manager = getFragmentManager();
        Fragment frag = manager.findFragmentByTag("fragment_edit_name");
        if (frag != null) {
            manager.beginTransaction().remove(frag).commit();
        }
        switch (view.getId()) {
            case R.id.showCustomFragment:
                MyDialogFragment editNameDialog = new MyDialogFragment();
                editNameDialog.show(manager, "fragment_edit_name");
                break;

        }
    }
}


