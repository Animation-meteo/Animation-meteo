package carte;

/**
 * @autor ianturton
 * @autor jeremy
 */

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.LineString;
import com.vividsolutions.jts.geom.Point;

import java.util.ArrayList;

import org.geotools.feature.simple.SimpleFeatureBuilder;
import org.opengis.feature.simple.SimpleFeature;
import org.opengis.feature.simple.SimpleFeatureType;


public class CreationLigne {

  /**
   * Cette méthode créee une ligne et l'ajoute à la map
   * @param schema.
   * @return feature
   */

  public static SimpleFeature createSimpleLineFeature(SimpleFeatureType schema) {
    SimpleFeatureBuilder featureBuilder = new SimpleFeatureBuilder(schema);

    LineString line = createRandomLineString(1);
    featureBuilder.add(line);

    SimpleFeature feature = featureBuilder.buildFeature(null);
    return feature;
  }

  /** Cette méthode permet de créer une ligne à partir de coordonnées.
   * Il faut modifier les valeurs de la longitude et latitude.
   * 
   * @return new line
   */

  public static LineString createRandomLineString(int n) {
    
    //Calcul de la latitude et longitude
    
    double latitude = (0.47013099588653107 * 180.0) - 90.0;
    double longitude = (0.771067804923124 * 360.0) - 180.0;

    GeometryFactory geometryFactory = new GeometryFactory();

    /* Longitude (= x coord) first ! */
    
    //Creation d'un tableau permettant stockant les points (coordonnées)
    
    ArrayList<Coordinate> points = new ArrayList<Coordinate>();
    points.add(new Coordinate(longitude, latitude));

    for (int i = 1; i < n; i++) {
      double deltaX = (0.6128962390474391 * 10.0) - 5.0;
      double deltaY = (0.6128962390474391 * 10.0) - 5.0;

      longitude += deltaX;
      latitude += deltaY;

      points.add(new Coordinate(longitude, latitude));
    }

    LineString line = 
        geometryFactory.createLineString((Coordinate[]) points.toArray(new Coordinate[] {}));

    return line;
  }
  

  /**
   * Cette méthode créee un Point.
   * @return point
   */

  public static Point createRandomPoint() {
    double latitude = (0.6128962390474391 * 180.0) - 90.0;
    double longitude = (0.6128962390474391 * 360.0) - 180.0;
    GeometryFactory geometryFactory = new GeometryFactory();

    /* Longitude (= x coord) first ! */
    com.vividsolutions.jts.geom.Point point =
        geometryFactory.createPoint(new Coordinate(longitude, latitude));

    return point;
  }
}
