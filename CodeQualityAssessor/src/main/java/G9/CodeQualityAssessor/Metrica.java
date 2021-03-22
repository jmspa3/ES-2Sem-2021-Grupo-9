package G9.CodeQualityAssessor;


public class Metrica {
	private int NOM_CLASS;
	private int LOC_CLASS;
	private int WMC_CLASS;
	private int LOC_method;
	private int CYCLO_method;
	
	public Metrica() {

	} 
	
	
	public Metrica(int NOM_CLASS, int LOC_CLASS, int WMC_CLASS, int LOC_method, int CYCLO_method ) {
		this.NOM_CLASS=NOM_CLASS;
		this.LOC_CLASS=LOC_CLASS;
		this.WMC_CLASS = WMC_CLASS;
		this.LOC_method = LOC_method;
		this.CYCLO_method = CYCLO_method;
	}
	
	
	public int getNOM_CLASS() {
		return NOM_CLASS;
	}
	public void setNOM_CLASS(int nOM_CLASS) {
		NOM_CLASS = nOM_CLASS;
	}
	public int getLOC_CLASS() {
		return LOC_CLASS;
	}
	public void setLOC_CLASS(int lOC_CLASS) {
		LOC_CLASS = lOC_CLASS;
	}
	public int getWMC_CLASS() {
		return WMC_CLASS;
	}
	public void setWMC_CLASS(int wMC_CLASS) {
		WMC_CLASS = wMC_CLASS;
	}
	public int getLOC_method() {
		return LOC_method;
	}
	public void setLOC_method(int lOC_method) {
		LOC_method = lOC_method;
	}
	public int getCYCLO_method() {
		return CYCLO_method;
	}
	public void setCYCLO_method(int cYCLO_method) {
		CYCLO_method = cYCLO_method;
	}
	
	public String toString() 
    { 
        return String.format("%i - %i - %i - %i - %i", NOM_CLASS, 
                             LOC_CLASS, WMC_CLASS, LOC_method, CYCLO_method); 
    } 

}
