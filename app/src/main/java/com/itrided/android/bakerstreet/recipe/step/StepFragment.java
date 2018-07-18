package com.itrided.android.bakerstreet.recipe.step;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.itrided.android.bakerstreet.R;
import com.itrided.android.bakerstreet.data.cache.CacheDataSourceFactory;
import com.itrided.android.bakerstreet.databinding.FragmentStepDetailsBinding;
import com.squareup.picasso.Picasso;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import static com.itrided.android.bakerstreet.StepActivity.EXTRA_STEP_POSITION;

public class StepFragment extends Fragment implements BlockingStep {

    private static final int MAX_CACHE_SIZE = 100 * 1024 * 1024; // one hundred megabytes
    private static final int MAX_FILE_SIZE = 5 * 1024 * 1024; // five megabytes

    private FragmentStepDetailsBinding binding;
    private StepListViewModel stepListViewModel;
    private SimpleExoPlayer exoPlayer;
    private int position;

    private int resumeWindow;
    private long resumePosition;
    private boolean shouldAutoPlay = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        stepListViewModel = ViewModelProviders.of(getActivity()).get(StepListViewModel.class);
        clearResumePosition();
        shouldAutoPlay = true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStepDetailsBinding.inflate(inflater, container, false);
        position = getArguments().getInt(EXTRA_STEP_POSITION, 0);

        if (getResources().getBoolean(R.bool.is_landscape)) {
            final String videoUrl = stepListViewModel.getStepsValue().get(position).getVideoURL();
            binding.videoEpv.setVisibility(TextUtils.isEmpty(videoUrl) ? View.GONE : View.VISIBLE);
            binding.descriptionTv.setVisibility(TextUtils.isEmpty(videoUrl) ? View.VISIBLE : View.GONE);
        } else {
            loadImageIntoView(stepListViewModel.getStepsValue().get(position).getThumbnailURL());
        }

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (exoPlayer == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        releasePlayer();
    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        stepListViewModel.setCurrentStepPosition(position + 1);
        releasePlayer();
        callback.goToNextStep();
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {
        getActivity().finish();
        callback.complete();
    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        stepListViewModel.setCurrentStepPosition(position - 1);
        releasePlayer();
        callback.goToPrevStep();
    }

    @Nullable
    @Override
    public VerificationError verifyStep() {
        if (position >= stepListViewModel.getStepsValue().size()) {
            return new VerificationError("INVALID STEP");
        } else {
            return null;
        }
    }

    @Override
    public void onSelected() {
        final String description = stepListViewModel.getStepsValue().get(position).getDescription();
        binding.descriptionTv.setText(description);
    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    private void loadImageIntoView(@Nullable String imageUrl) {
        if (imageUrl == null || imageUrl.isEmpty()) {
            return;
        }
        final Context context = getContext();

        Picasso.with(context)
                .load(imageUrl)
                .placeholder(R.drawable.ic_cake)
                .error(R.drawable.ic_cake)
                .into(binding.thumbnailIv);
    }

    private void initializePlayer() {
        final String url = stepListViewModel.getStepsValue().get(position).getVideoURL();

        if (TextUtils.isEmpty(url)) {
            return;
        }

        final Uri uri = Uri.parse(url);
        initializePlayerWithUri(uri);
    }

    private void initializePlayerWithUri(@NonNull Uri uri) {
        final BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        final TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        final TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        final MediaSource audioSource = new ExtractorMediaSource.Factory(
                new CacheDataSourceFactory(getContext(), MAX_CACHE_SIZE, MAX_FILE_SIZE))
                .createMediaSource(uri);

        exoPlayer = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                trackSelector, new DefaultLoadControl());

        binding.videoEpv.setPlayer(exoPlayer);
        exoPlayer.prepare(audioSource);
        exoPlayer.setPlayWhenReady(shouldAutoPlay);

        boolean haveResumePosition = resumeWindow != C.INDEX_UNSET;
        if (haveResumePosition) {
            exoPlayer.seekTo(resumeWindow, resumePosition);
        }
    }

    private void releasePlayer() {
        if (exoPlayer == null)
            return;

        shouldAutoPlay = exoPlayer.getPlayWhenReady();
        updateResumePosition();
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    private void updateResumePosition() {
        resumeWindow = exoPlayer.getCurrentWindowIndex();
        resumePosition = Math.max(0, exoPlayer.getContentPosition());
    }

    private void clearResumePosition() {
        resumeWindow = C.INDEX_UNSET;
        resumePosition = C.TIME_UNSET;
    }
}
