package com.pd.SportyPoseDetection;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseDetectorOptions;

/** A processor to run pose detector. */
public class PoseDetectorProcessor extends VisionProcessorBase<Pose> {

    private static final String TAG = "PoseDetectorProcessor";

    private final PoseDetector detector;

    private final boolean showInFrameLikelihood;

    public PoseDetectorProcessor(
            Context context, PoseDetectorOptions options, boolean showInFrameLikelihood) {
        super(context);
        this.showInFrameLikelihood = showInFrameLikelihood;
        detector = PoseDetection.getClient(options);
    }

    @Override
    public void stop() {
        super.stop();
        detector.close();
    }

    @Override
    protected Task<Pose> detectInImage(InputImage image) {
        return detector.process(image);
    }

    @Override
    protected void onSuccess(@NonNull Pose pose, @NonNull GraphicOverlay graphicOverlay) {
        graphicOverlay.add(new PoseGraphic(graphicOverlay, pose, showInFrameLikelihood));
    }

    @Override
    protected void onFailure(@NonNull Exception e) {
        Log.e(TAG, "Pose detection failed!", e);
    }
}
