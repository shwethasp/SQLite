package com.shwethasp.mysqldb;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by shwethap on 05-07-2016.
 */
public class DetailsAdapter extends ArrayAdapter<DetailsModel> implements View.OnClickListener {
    Context context;
    ArrayList<DetailsModel> dataarray;
    Dialog modifyDialog;
    EditText patternEditName, patternEditAddress;
    public static Button dialog_cancel, dialog_confirm;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dialog_cancel:
                modifyDialog.dismiss();
                break;
        }
    }


    /**
     * applying ViewHolder pattern to speed up ListView, smoother and faster
     * item loading by caching view in A ViewHolder object
     */
    private static class ViewHolder {
        TextView textid, textName, textAdd;
        Button modifybtn, deletebtn;
    }

    public DetailsAdapter(Context context, ArrayList<DetailsModel> data) {
        super(context, 0, data);
        this.context = context;
        this.dataarray = data;
    }


    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // view lookup cache stored in tag
        final ViewHolder viewHolder;
        // Check if an existing view is being reused, otherwise inflate the
        // item view
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.all_details, parent, false);

            viewHolder.textid = (TextView) convertView.findViewById(R.id.detail_id);
            viewHolder.textName =
                    (TextView) convertView.findViewById(R.id.detail_name);
            viewHolder.textAdd =
                    (TextView) convertView.findViewById(R.id.detail_address);

            viewHolder.modifybtn = (Button) convertView.findViewById(R.id.modify_btn);
            viewHolder.deletebtn = (Button) convertView.findViewById(R.id.delete_btn);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final DetailsModel alldetail = dataarray.get(position);
        //  viewHolder.textid.setText(alldetail.getId().toString());
        // set name text
        viewHolder.textName.setText(alldetail.getName().toString());
        // set description text
        viewHolder.textAdd.setText(alldetail.getAddress().toString());


        viewHolder.deletebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(context);
                db.deleteData(dataarray.get(position).getId());
               /* db.deleteData(position);*/
                dataarray.remove(position);
                notifyDataSetChanged();
            }
        });

        viewHolder.modifybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activityPlusDialog(dataarray.get(position).getName(), dataarray.get(position).getAddress(), dataarray.get(position).getId());

            }
        });
        // Return the completed view to render on screen
        return convertView;
    }


    private void activityPlusDialog(String oldname, String oldadd, final String id) {
        modifyDialog = new Dialog(context);
        modifyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        modifyDialog.setContentView(R.layout.modify_dialog);
        modifyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        modifyDialog.setCancelable(false);

        //  PatternDialogTitle = (TextView) modifyDialog.findViewById(R.id.dialog_title);
        patternEditName = (EditText) modifyDialog.findViewById(R.id.edit_name);
        patternEditAddress = (EditText) modifyDialog.findViewById(R.id.edit_address);
        patternEditName.setText(oldname);
        patternEditAddress.setText(oldadd);

        dialog_cancel = (Button) modifyDialog.findViewById(R.id.dialog_cancel);
        dialog_confirm = (Button) modifyDialog.findViewById(R.id.dialog_confirm);
        dialog_cancel.setOnClickListener(this);
        modifyDialog.show();
        dialog_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBHelper db = new DBHelper(context);
                String name = patternEditName.getText().toString().trim();
                String address = patternEditAddress.getText().toString().trim();
                if (name.equals("") || address.equals("")) {
                    Toast.makeText(getContext(), "Please fill the fields", Toast.LENGTH_LONG).show();
                    return;
                }
                db.updateDetails(id, name, address);
                notifyDataSetChanged();
                modifyDialog.dismiss();
            }
        });

    }

}



