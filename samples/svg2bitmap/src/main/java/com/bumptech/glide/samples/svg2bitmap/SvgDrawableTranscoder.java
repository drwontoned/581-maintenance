package com.bumptech.glide.samples.svg2bitmap;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Picture;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

/**
 * Convert the {@link SVG} to {@link Bitmap}
 */
public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, Bitmap> {
  @Nullable
  @Override
  public Resource<Bitmap> transcode(
      @NonNull Resource<SVG> toTranscode, @NonNull Options options) {
    SVG svg = toTranscode.get();
    Picture picture = svg.renderToPicture();
    Bitmap bitmap = Bitmap.createBitmap(picture.getWidth(),picture.getHeight(), Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap);
    picture.draw(canvas);
    return new SimpleResource<>(bitmap);
  }
}