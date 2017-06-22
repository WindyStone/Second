package com.windy.second.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.windy.second.R;
import com.windy.second.tools.Utils;

/**
 * Created by windy on 2017/6/18.
 */

public class LeftFragment extends Fragment implements View.OnClickListener{
    private ImageView mUserFace;
    private TextView mOrderInfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_left, container, false);
        initView(root);
        initEvent();
        return root;
    }

    private void initView(View root) {
        mUserFace = (ImageView) root.findViewById(R.id.iv_user_face);
        mOrderInfo = (TextView) root.findViewById(R.id.tv_order_info);
    }

    private void initEvent() {
        mUserFace.setOnClickListener(this);
        mOrderInfo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!Utils.canClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.iv_user_face) {

        } else if (id == R.id.tv_order_info) {

        }
    }
}
