package com.example.android.bakingapp.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.adapters.RecipeStepsAdapter;
import com.example.android.bakingapp.domain.Step;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;

import org.apache.commons.lang3.StringUtils;

import butterknife.BindBool;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by angelov on 9/23/2018.
 */

public class RecipeStepDetailsFragment extends Fragment {

    @Getter
    @Setter
    private Step step;
    private Unbinder unbinder;
    @BindView(R.id.step_video_player)
    public SimpleExoPlayerView mStepVideoPlayerView;
    @BindView(R.id.step_description)
    public TextView mStepDescriptionView;
    @BindBool(R.bool.is_tablet)
    public boolean sTwoPane;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.step_details_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        mStepDescriptionView.setText(step.getDescription());

        if(!StringUtils.isEmpty(step.getVideoURL())) {
            setupExoPlayer();
        }
        else {
            mStepVideoPlayerView.setVisibility(View.GONE);
        }

        return view;
    }

    private void setupExoPlayer() {

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
