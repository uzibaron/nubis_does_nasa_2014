package com.google.android.stardroid.layers;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.Log;

import com.google.android.stardroid.R;
import com.google.android.stardroid.base.Lists;
import com.google.android.stardroid.base.TimeConstants;
import com.google.android.stardroid.control.AstronomerModel;
import com.google.android.stardroid.provider.ephemeris.OrbitalElements;
import com.google.android.stardroid.provider.ephemeris.Planet;
import com.google.android.stardroid.provider.ephemeris.PlanetSource;
import com.google.android.stardroid.source.AbstractAstronomicalSource;
import com.google.android.stardroid.source.AstronomicalSource;
import com.google.android.stardroid.source.ImageSource;
import com.google.android.stardroid.source.PointSource;
import com.google.android.stardroid.source.TextSource;
import com.google.android.stardroid.source.impl.PointSourceImpl;
import com.google.android.stardroid.source.impl.TextSourceImpl;
import com.google.android.stardroid.units.GeocentricCoordinates;
import com.google.android.stardroid.units.HeliocentricCoordinates;
import com.google.android.stardroid.units.RaDec;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by george.slavov on 12/04/2014.
 */
public class EarthLayer extends AbstractSourceLayer {

    private final AstronomerModel model;
    private EarthSource earthSource;

    public EarthLayer(AstronomerModel model, Resources resources) {
        super(resources, true);
        this.model = model;
    }

    @Override
    protected void initializeAstroSources(ArrayList<AstronomicalSource> sources) {
        this.earthSource = new EarthSource(model, getResources());
        sources.add(earthSource);
    }

    @Override
    public String getPreferenceId() {
        return "source_provider.20";
    }

    @Override
    public int getLayerId() {
        return -120;
    }

    @Override
    protected int getLayerNameId() {
        return R.string.show_planets_pref;
    }

    static class EarthSource extends AbstractAstronomicalSource {

        private static final int EARTH_COLOR = Color.MAGENTA;

        private GeocentricCoordinates coords = new GeocentricCoordinates(1f, 0f, 0f);
        private final ArrayList<PointSource> pointSources = new ArrayList<PointSource>();
        private final ArrayList<TextSource> textSources = new ArrayList<TextSource>();
        private final AstronomerModel model;
        private final String name;

        public EarthSource(AstronomerModel model, Resources resources) {
            this.model = model;
            this.name = "EARTH";

            coords.updateFromRaDec(new RaDec(0, 0));
            Log.d("EARTH LAYER", "coord: " + coords.toString());

            pointSources.add(new PointSourceImpl(coords, EARTH_COLOR, PointSource.Shape.CIRCLE.getImageIndex()));
            textSources.add(new TextSourceImpl(coords, name, EARTH_COLOR));
        }

        //@Override
        //public List<? extends ImageSource> getImages() {
        //    return imageSources;
        //}

        @Override
        public List<? extends TextSource> getLabels() {
            return textSources;
        }

        @Override
        public List<? extends PointSource> getPoints() {
            return pointSources;
        }
    }
}
