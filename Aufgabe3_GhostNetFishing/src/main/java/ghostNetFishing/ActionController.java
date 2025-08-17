package ghostNetFishing;

import java.io.Serializable;

import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named("actionController")
@ViewScoped
public class ActionController implements Serializable {
	private static final long serialVersionUID = 1L;

	public String impressum() {
		return "impressum";
	}

	public String index() {
		return "index";
	}

	public String notification() {
		return "meldung";
	}
	
	public String recovery() {
		return "bergung";
	}
	
	public String recoverySuccess() {
		return "bergungErfolg";
	}
}
