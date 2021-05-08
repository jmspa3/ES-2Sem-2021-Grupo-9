package metrics;

/**
 * <h1>Metric</h1> This is the aux class to set and get the metrics.
 *
 * @author Nuno Dias
 * @version 1.0
 */
public class Metric {

	private int id;
	private String method_package;
	private String method_class;
	private String method_name;
	private int LOC_class;
	private int LOC_method;
	private int CYCLO_method;
	private int NOM_class;
	private int WMC_class;

	/**
	 * Getter for the ID of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getId() {
		return String.valueOf(id);
	}

	/**
	 * Getter for the package of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getMethod_package() {
		return method_package;
	}

	/**
	 * Getter for the name of the class of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getClass_Name() {
		return method_class;
	}

	/**
	 * Getter for the name of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getMethod_name() {
		return method_name;
	}

	/**
	 * Getter for the lines of code of the class of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getLOC_class() {
		return String.valueOf(LOC_class);
	}

	/**
	 * Getter for the lines of code of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getLOC_method() {
		return String.valueOf(LOC_method);
	}

	/**
	 * Getter for CYCLO metric of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getCYCLO_method() {
		return String.valueOf(CYCLO_method);
	}

	/**
	 * Getter for number of methods.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getNOM_class() {
		return String.valueOf(NOM_class);
	}

	/**
	 * Getter for WMC metric of the class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 */
	public String getWMC_class() {
		return String.valueOf(WMC_class);
	}

	/**
	 * 
	 * Given a string returns the value of the metric with that name
	 * 
	 * @param metricName
	 * @return {@link Integer}
	 */
	public int getValueByMetricName(String metricName) {
		switch (metricName) {
		case "LOC_class":
			return LOC_class;
		case "LOC_method":
			return LOC_method;
		case "CYCLO_method":
			return CYCLO_method;
		case "NOM_class":
			return NOM_class;
		case "WMC_class":
			return WMC_class;
		}
		return -1;
	}

	/**
	 * Setter for the ID of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Setter for the package of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setMethod_package(String method_package) {
		this.method_package = method_package;
	}

	/**
	 * Setter for the name of the class of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setClass_Name(String method_class) {
		this.method_class = method_class;
	}

	/**
	 * Setter for the name of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	/**
	 * Setter for the lines of code of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setLOC_class(int lOC_class) {
		LOC_class = lOC_class;
	}

	/**
	 * Setter for the lines of code of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	/**
	 * Setter for CYCLO metric of the method/constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setCYCLO_method(int cYCLO_method) {
		CYCLO_method = cYCLO_method;
	}

	/**
	 * Setter for number of methods.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setNOM_class(int nOM_class) {
		NOM_class = nOM_class;
	}

	/**
	 * Setter for WMC metric of the class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Void}
	 */
	public void setWMC_class(int wMC_class) {
		WMC_class = wMC_class;
	}

}
