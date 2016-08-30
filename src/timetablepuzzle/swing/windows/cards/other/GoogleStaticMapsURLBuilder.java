package timetablepuzzle.swing.windows.cards.other;

public class GoogleStaticMapsURLBuilder {
	private static final String API_KEY = "AIzaSyAkQefix--uDtXXUE1-yjQm6P-Fen8bR0I";
	private static final String BASIC_URL = "https://maps.googleapis.com/maps/api/staticmap";
	private static String DEFAULT_CENTER_LATITUDE = "47.154184";
	private static String DEFAULT_CENTER_LONGITUDE = "27.593960";
	private static String DEFAULT_ZOOM = "17";
	// {horizontal_value}x{vertical_value}
	private static String DEFAULT_SIZE = "600x300";
	private static String DEFAULT_SCALE = "1";

	private String centerLatitude;
	private String centerLongitude;
	private String zoom;
	private String size;
	private String scale;

	public GoogleStaticMapsURLBuilder() {
		this(DEFAULT_CENTER_LATITUDE, DEFAULT_CENTER_LONGITUDE);
	}

	public GoogleStaticMapsURLBuilder(String centerLatitude, String centerLongitude) {
		this.setCenterLatitude(centerLatitude);
		this.setCenterLongitude(centerLongitude);
		this.setZoom(DEFAULT_ZOOM);
		this.setSize(DEFAULT_SIZE);
		this.setScale(DEFAULT_SCALE);
	}

	/****************** Getters and setters ************************/
	public String getCenterLatitude() {
		return centerLatitude;
	}

	public void setCenterLatitude(String centerLatitude) {
		this.centerLatitude = centerLatitude;
	}

	public String getCenterLongitude() {
		return centerLongitude;
	}

	public void setCenterLongitude(String centerLongitude) {
		this.centerLongitude = centerLongitude;
	}

	public String getZoom() {
		return zoom;
	}

	public void setZoom(String zoom) {
		this.zoom = zoom;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	/*************** Methods that model class behavior ************************/
	public String GetStaticMapURL() {
		return BASIC_URL + "?" + GetCenterURLPart() + "&" + GetZoomURLPart() + "&" + GetSizeURLPart() + "&"
				+ GetScaleURLPart() + "&" + GetMarkersURLPart() + "&" + GetApiKeyURLPart();
	}

	private String GetCenterURLPart() {
		return "center=" + this.centerLatitude + "," + this.centerLongitude;
	}

	private String GetZoomURLPart() {
		return "zoom=" + this.zoom;
	}

	private String GetSizeURLPart() {
		return "size=" + this.size;
	}

	private String GetScaleURLPart() {
		return "scale=" + this.scale;
	}

	private String GetMarkersURLPart() {
		return "markers=" + this.centerLatitude + "," + this.centerLongitude;
	}

	private String GetApiKeyURLPart() {
		return "key=" + API_KEY;
	}
}
