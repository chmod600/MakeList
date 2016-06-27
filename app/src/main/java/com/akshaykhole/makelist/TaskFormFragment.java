package com.akshaykhole.makelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.akshaykhole.makelist.models.Task;

import java.util.Date;
import java.util.UUID;

import io.realm.Realm;

/**
 * Created by akshay on 6/26/16.
 */

public class TaskFormFragment extends DialogFragment {
    private Realm realm;
    private EditText description;
    private Button btnDone;
    private Button btnCancel;

    public TaskFormFragment() { }

    public static TaskFormFragment newInstance() {
        TaskFormFragment frag = new TaskFormFragment();
        frag.setStyle(DialogFragment.STYLE_NORMAL, R.style.Dialog_FullScreen);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.task_form, container);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        btnDone = (Button) view.findViewById(R.id.btnTaskFormDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                description = (EditText) getDialog().findViewById(R.id.editDescriptionInput);

                realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                Task t = realm.createObject(Task.class);
                t.setId(UUID.randomUUID().toString());
                t.setText(description.getText().toString());
                t.setPriority("low");
                t.setAssignedBy("self");
                t.setDueDate(new Date());
                t.setComplete(Boolean.FALSE);
                realm.commitTransaction();

                Toast.makeText(getActivity(), "SUCCESS!",
                        Toast.LENGTH_SHORT).show();
                getDialog().dismiss();
            }
        });

        btnCancel = (Button) view.findViewById(R.id.btnTaskFormCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();
            }
        });
    }

    @Override
    public void onResume() {
        // Get existing layout params for the window
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        // Assign window properties to fill the parent
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
        // Call super onResume after sizing
        super.onResume();
    }
}
