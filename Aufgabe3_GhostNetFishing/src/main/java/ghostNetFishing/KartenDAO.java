package ghostNetFishing;

import org.primefaces.event.map.OverlaySelectEvent;
import org.primefaces.model.map.Circle;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Overlay;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class KartenDAO implements Serializable {

    private static final long serialVersionUID = 1L;

    private MapModel<Long> circleModel;

    @Inject
    private NetzDAO netzDAO;

    @PostConstruct
    public void init() {
        circleModel = new DefaultMapModel<>();

        
        List<Netz> nets = netzDAO.getAllNets("gemeldet");

        for (Netz nets1 : nets) {
            double lat = convertGMS(nets1.getNorthSouth(), nets1.getLatituteDegree(), nets1.getLatituteMinute(), nets1.getLatituteSeconds());
            double lng = convertGMS(nets1.getEastWest(), nets1.getLongituteDegree(), nets1.getLongituteMinute(), nets1.getLongituteSeconds());

            LatLng position = new LatLng(lat, lng);
            Circle<Long> circle = new Circle<>(position, 30000); // Fester Radius

            circle.setStrokeColor("#ffd700");
            circle.setFillColor("#ffd700");
            circle.setFillOpacity(0.9);
            circle.setData(nets1.getId());

            circleModel.addOverlay(circle);
        }
    }

    private double convertGMS(String direction, int grad, int min, double sek) {
        double dezimal = grad + (min / 60.0) + (sek / 3600.0);
        if ("Süd".equalsIgnoreCase(direction) || "West".equalsIgnoreCase(direction)) {
            dezimal *= -1;
        }
        return dezimal;
    }

    public MapModel<Long> getCircleModel() {
        return circleModel;
    }

    public void onCircleSelect(OverlaySelectEvent<Long> event) {
        Overlay<Long> overlay = event.getOverlay();
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_INFO, "Netz mit der ID " + overlay.getData(), null));
    }
}