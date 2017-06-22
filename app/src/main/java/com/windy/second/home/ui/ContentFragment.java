package com.windy.second.home.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windy.second.R;
import com.windy.second.home.adapter.ContentAdapter;
import com.windy.second.tools.Utils;

/**
 * Created by windy on 2017/6/18.
 */

public class ContentFragment extends Fragment implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private ContentAdapter mAdapter;
    private View mMore, mShare;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_content, container, false);
        initView(root);
        initEvent();
        return root;
    }

    private void initView(View root) {
        mRecyclerView = (RecyclerView) root.findViewById(R.id.rc_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mAdapter = new ContentAdapter(getContext());
        mRecyclerView.setAdapter(mAdapter);

        mMore = root.findViewById(R.id.rl_more);
        mShare = root.findViewById(R.id.rl_share);
    }

    private void initEvent() {
        mMore.setOnClickListener(this);
        mShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!Utils.canClick()) {
            return;
        }
        int id = v.getId();
        if (id == R.id.rl_more) {
            SlidingPaneLayout layout = (SlidingPaneLayout) getActivity().findViewById(R.id.spl_root);
            if (layout.isOpen()) {
                layout.closePane();
            } else {
                layout.openPane();
            }
        } else if (id == R.id.rl_share) {

        }
    }
}
