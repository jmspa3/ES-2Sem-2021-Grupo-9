package metrics;

import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorInfo extends VoidVisitorAdapter<List<Metric>> {

	private CompilationUnit cu;
	private int id;

	/**
	 * Constructor of ConstructorInfo class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param cu the file we are running trough 
	 * @param id the id/number of the method/contructor we are visiting
	 * @see CompilationUnit
	 */
	public ConstructorInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	/**
	 * This method visits all constructors, one by one, returns all information about each one, putting it in
	 * a new Metric instance and adding it to a List<Metric>.
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param c the constructor that is going to be visited
	 * @param collector the list we are adding this new Metric with all the information to
	 * @return Nothing.
	 * @see Metric, ConstructorDeclaration
	 */
	@Override
	public void visit(ConstructorDeclaration c, List<Metric> collector) {
		super.visit(c, collector);
		MetricUtils mu = new MetricUtils(cu);
		Metric newmetric = new Metric();

		ClassOrInterfaceDeclaration cid = null;
		if (c.getParentNode().isPresent()) {
			if (c.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
				cid = (ClassOrInterfaceDeclaration) c.getParentNode().get();
			} else {
				return;
			}
		} else {
			return;
		}

		newMetricCreator(c, mu, newmetric, cid);

		collector.add(newmetric);
	}

	
	/**
	 * This method uses the MetricUtils methods to extract all information about a constructor and adds it to the new Metric.
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param c the method that is going to be visited
	 * @param mu instance of the MetricUtils class, used to retrieve information about a certain method/constructor
	 * @param newmetric the instance of Metric receiving the information
	 * @param cid gives us some type of information, like the class name or LOC_Class
	 * @return Nothing.
	 * @see Metric, {@link ConstructorDeclaration}, {@link MetricUtils}, {@link ClassOrInterfaceDeclaration}
	 */
	private void newMetricCreator(ConstructorDeclaration c, MetricUtils mu, Metric newmetric,
			ClassOrInterfaceDeclaration cid) {
		newmetric.setId(id++);
		newmetric.setMethod_package(mu.getPackageName());
		newmetric.setMethod_name(mu.getMethodName(c));
		newmetric.setLOC_method(mu.getLOC_Method(c));
		newmetric.setCYCLO_method(mu.getCYCLO_Method(c));
		if (cid.isNestedType()) {
			newmetric.setClass_Name(mu.getClassName() + "." + cid.getNameAsString());
			newmetric.setLOC_class(mu.getLOC_Inner_class(cid));
			newmetric.setNOM_class((cid.getConstructors().size() + cid.getMethods().size()));
		} else {
			newmetric.setClass_Name(mu.getClassName());
			newmetric.setLOC_class(mu.getLOC_class());
			newmetric.setNOM_class((cid.getConstructors().size() + cid.getMethods().size()));
		}
	}

}
