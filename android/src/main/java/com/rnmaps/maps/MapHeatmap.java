package com.rnmaps.maps;

import android.content.Context;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.TileOverlay;
import com.google.android.gms.maps.model.TileOverlayOptions;
import com.google.maps.android.heatmaps.HeatmapTileProvider;
import com.google.maps.android.heatmaps.WeightedLatLng;
import com.google.maps.android.heatmaps.Gradient;

import java.util.Arrays;
import java.util.List;

public class MapHeatmap extends MapFeature {

    private TileOverlayOptions heatmapOptions;
    private TileOverlay heatmap;
    private HeatmapTileProvider heatmapTileProvider;

    private List<WeightedLatLng> points;
    private Gradient gradient;
    private Double opacity;
    private Integer radius;
    private Double maximumZoomIntensity;

    public MapHeatmap(Context context) {
        super(context);
    }

    public void setPoints(WeightedLatLng[] points) {
        this.points = Arrays.asList(points);
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setWeightedData(this.points);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public void setGradient(Gradient gradient) {
        this.gradient = gradient;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setGradient(gradient);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }
    
    public void setOpacity(double opacity) {
        this.opacity = opacity;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setOpacity(opacity);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public void setRadius(int radius) {
        this.radius = radius;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setRadius(radius);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public void setMaximumZoomIntensity(double maximumZoomIntensity) {
        this.maximumZoomIntensity = maximumZoomIntensity;
        if (heatmapTileProvider != null) {
            heatmapTileProvider.setMaxIntensity(maximumZoomIntensity);
        }
        if (heatmap != null) {
            heatmap.clearTileCache();
        }
    }

    public TileOverlayOptions getHeatmapOptions() {
        if (heatmapOptions == null) {
            heatmapOptions = createHeatmapOptions();
        }
        return heatmapOptions;
    }

    private TileOverlayOptions createHeatmapOptions() {
        TileOverlayOptions options = new TileOverlayOptions();
        if (heatmapTileProvider == null) {
            HeatmapTileProvider.Builder builder =
                new HeatmapTileProvider.Builder().weightedData(this.points);
            if (radius != null) {
                builder.radius(radius);
            }
            if (opacity != null) {
                builder.opacity(opacity);
            }
            if (gradient != null) {
                builder.gradient(gradient);
            }
            if (maximumZoomIntensity != null) {
                builder.maxIntensity(maximumZoomIntensity);
            }
            heatmapTileProvider = builder.build();
        }
        options.tileProvider(heatmapTileProvider);
        return options;
    }

    @Override
    public Object getFeature() {
        return heatmap;
    }

    @Override
    public void addToMap(Object map) {
        heatmap = ((GoogleMap) map).addTileOverlay(getHeatmapOptions());
    }

    @Override
    public void removeFromMap(Object map) {
        heatmap.remove();
    }

}