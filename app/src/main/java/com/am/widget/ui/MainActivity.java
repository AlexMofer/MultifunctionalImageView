package com.am.widget.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Spinner;

import androidx.annotation.Nullable;

import com.am.appcompat.app.AppCompatActivity;
import com.am.widget.multifunctionalimageview.ClipOutlineProvider;
import com.am.widget.multifunctionalimageview.MultifunctionalImageView;
import com.am.widget.multifunctionalimageview.RoundRectClipOutlineProvider;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity implements
        CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener,
        AdapterView.OnItemSelectedListener {

    private final RoundRectClipOutlineProvider mRoundRect =
            new RoundRectClipOutlineProvider(0);
    private MultifunctionalImageView mVImage;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mVImage = findViewById(R.id.miv_image);
        final SwitchMaterial crop = findViewById(R.id.miv_sw_crop);
        final SeekBar radius = findViewById(R.id.miv_sb_radius);
        final SeekBar height = findViewById(R.id.miv_sb_height);
        final SeekBar border = findViewById(R.id.miv_sb_border);
        final SeekBar padding = findViewById(R.id.miv_sb_padding);

        crop.setOnCheckedChangeListener(this);
        crop.setChecked(true);
        radius.setOnSeekBarChangeListener(this);
        radius.setProgress(10);
        height.setOnSeekBarChangeListener(this);
        height.setProgress(0);
        border.setOnSeekBarChangeListener(this);
        border.setProgress(2);
        padding.setOnSeekBarChangeListener(this);
        padding.setProgress(0);
        this.<Spinner>findViewById(R.id.miv_sp_scale_type).setOnItemSelectedListener(this);
    }

    // Listener
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            mVImage.setClipOutlineProvider(ClipOutlineProvider.CIRCLE);
        } else {
            mVImage.setClipOutlineProvider(mRoundRect);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        final int id = seekBar.getId();
        if (id == R.id.miv_sb_height) {
            mVImage.setFixedSize(100, 100 - progress);
        } else if (id == R.id.miv_sb_border) {
            mVImage.setBorderWidth((int) (progress *
                    getResources().getDisplayMetrics().density));
        } else if (id == R.id.miv_sb_radius) {
            mRoundRect.setRadius(progress * getResources().getDisplayMetrics().density);
            mVImage.invalidateClipOutline();
        } else if (id == R.id.miv_sb_padding) {
            final int padding = (int) (progress * getResources().getDisplayMetrics().density);
            mVImage.setPadding(padding, padding, padding, padding);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // do nothing
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        // do nothing
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            default:
            case 0:
                mVImage.setScaleType(ImageView.ScaleType.CENTER);
                break;
            case 1:
                mVImage.setScaleType(ImageView.ScaleType.CENTER_CROP);
                break;
            case 2:
                mVImage.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                break;
            case 3:
                mVImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
                break;
            case 4:
                mVImage.setScaleType(ImageView.ScaleType.FIT_END);
                break;
            case 5:
                mVImage.setScaleType(ImageView.ScaleType.FIT_START);
                break;
            case 6:
                mVImage.setScaleType(ImageView.ScaleType.FIT_XY);
                break;
            case 7:
                mVImage.setScaleType(ImageView.ScaleType.MATRIX);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}