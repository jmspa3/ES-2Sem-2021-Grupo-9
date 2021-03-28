package metrics;

public class Metric {

	String m_package, m_class, m_method;
	int count = 1;
	
	public String getM_package() {
		return m_package;
	}
	
	public void setM_package(String m_package) {
		this.m_package = m_package;
	}
	
	public String getM_class() {
		return m_class;
	}
	
	public void setM_class(String m_class) {
		this.m_class = m_class;
	}
	
	public String getM_method() {
		return m_method;
	}
	
	public void setM_method(String m_method) {
		this.m_method = m_method;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public void addCount(int count) {
		this.count = this.count + count;
	}
	
	public void incrementCount() {
		this.count++;
	}
	
	
	public String toString() {
		return "package: "+m_package+"\n"+"class: "+m_class+"\n"+"method: "+m_method+"\n"+"count: "+count;
	}
	
}
