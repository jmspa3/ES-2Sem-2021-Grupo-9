package metrics;

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

	public String getId() {
		return String.valueOf(id);
	}
	
	public String getMethod_package() {
		return method_package;
	}

	public String getClass_Name() {
		return method_class;
	}
	
	public String getMethod_name() {
		return method_name;
	}
	
	public String getLOC_class() {
		return String.valueOf(LOC_class);
	}
	
	public String getLOC_method() {
		return String.valueOf(LOC_method);
	}
	
	public String getCYCLO_method() {
		return String.valueOf(CYCLO_method);
	}
	
	public String getNOM_class() {
		return String.valueOf(NOM_class);
	}
	
	public String getWMC_class() {
		return String.valueOf(WMC_class);
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public void setMethod_package(String method_package) {
		this.method_package = method_package;
	}

	

	public void setClass_Name(String method_class) {
		this.method_class = method_class;
	}

	

	public void setMethod_name(String method_name) {
		this.method_name = method_name;
	}

	

	public void setLOC_class(int lOC_class) {
		LOC_class = lOC_class;
	}

	

	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}

	

	public void setCYCLO_method(int cYCLO_method) {
		CYCLO_method = cYCLO_method;
	}

	

	public void setNOM_class(int nOM_class) {
		NOM_class = nOM_class;
	}

	

	public void setWMC_class(int wMC_class) {
		WMC_class = wMC_class;
	}

}
