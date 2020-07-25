package com.example.myroutines.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myroutines.AlarmHelper;
import com.example.myroutines.R;
import com.example.myroutines.app.Constant;
import com.example.myroutines.data.Routine;
import com.example.myroutines.data.RoutineHelper;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class InactiveFragment extends Fragment {

    private List<Routine> routines;

    @NonNull
    static InactiveFragment newInstance() {
        InactiveFragment fragment = new InactiveFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RoutineHelper db = new RoutineHelper(this.getActivity().getApplicationContext());
        routines = db.getAllInactiveRoutine();
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.inactive_fragment, container, false);
        RelativeLayout rl = root.findViewById(R.id.inactive_layout);
        RelativeLayout base = root.findViewById(R.id.base);
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        base.setLayoutParams(rlp);
        int prevId = base.getId();
        for (Routine data : routines){
            RelativeLayout routine = (RelativeLayout) inflater.inflate(R.layout.routine, container, false);
            routine.setId(prevId+1);
            rlp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            rlp.addRule(RelativeLayout.BELOW, prevId);
            routine.setLayoutParams(rlp);
            TextView cond = routine.findViewById(R.id.routine_condition);
            switch (data.getCond().getId()){
                case 1:
                    cond.setText("ALARM");
                    break;
                case 2:
                    cond.setText("ACC");
                    break;
            }
            TextView action = routine.findViewById(R.id.routine_action);
            switch (data.getAction().getId()){
                case 1:
                    action.setText("NOTIFY");
                    break;
                case 2:
                    action.setText("API");
                    break;
                case 3:
                    action.setText("WIFI");
                    break;
            }
            rl.addView(routine);
            prevId = routine.getId();
            ImageButton buttonA = routine.findViewById(R.id.imageView);
            buttonA.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutineHelper db = new RoutineHelper(getActivity().getApplicationContext());
                    db.changeState(data.getId(), Constant.ROUTINE_ACTIVE);
                    if (data.getCond().getId() != 1){
                        AlarmHelper ah = new AlarmHelper();
                        ah.setAlarm(getActivity().getApplicationContext(), data);
                    }
                }
            });
            ImageButton buttonD = routine.findViewById(R.id.imageView2);
            buttonD.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RoutineHelper db = new RoutineHelper(getActivity().getApplicationContext());
                    db.deleteRoutine(data.getId());
                    if (data.getCond().getId() != 1){
                        AlarmHelper ah = new AlarmHelper();
                        ah.killAlarm(getActivity().getApplicationContext(), data);
                    }
                }
            });
        }
        return root;
    }
}