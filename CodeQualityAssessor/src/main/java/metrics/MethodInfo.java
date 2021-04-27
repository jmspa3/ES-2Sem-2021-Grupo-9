package metrics;

import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodInfo extends VoidVisitorAdapter<List<Metric>> {

	private CompilationUnit cu;
	private int id;

	/**
	 * Constructor of MethodInfo class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param cu instance of CompilationUnit, the file we are running trough 
	 * @param id the id/number of the method/constructor we are visiting
	 * @see CompilationUnit
	 */
	public MethodInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	/**
	 * This method visits all methods, one by one, returns all information about each one, putting it in
	 * a new Metric instance and adding it to a List<Metric>.
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param md instance of MethodDeclaration, the method that is going to be visited
	 * @param collector the list we are adding this new Metric with all the information to
	 * @return Nothing.
	 * @see Metric, {@link MethodDeclaration}
	 */
	@Override
	public void visit(MethodDeclaration md, List<Metric> collector) {
		super.visit(md, collector);
		MetricUtils mu = new MetricUtils(cu);
		Metric newmetric = new Metric();

		ClassOrInterfaceDeclaration cid = null;
		if (md.getParentNode().isPresent()) {
			if (md.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
				cid = (ClassOrInterfaceDeclaration) md.getParentNode().get();
			} else {
				return;
			}
		} else {
			return;
		}

		newMetricCreator(md, mu, newmetric, cid);

		collector.add(newmetric);
	}

	/**
	 * This method uses the MetricUtils methods to extract all information about a method and adds it to the new Metric.
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param md instance of MethodDeclaration, the method that is going to be visited
	 * @param mu instance of the MetricUtils class
	 * @param newmetric instance of Metric, receiving the information
	 * @param cid instance of ClassOrInterfaceDeclaration that gives us some type of information, like the class name 
	 * @return Nothing.
	 * @see Metric, {@link ConstructorDeclaration}, {@link MetricUtils}, {@link ClassOrInterfaceDeclaration}
	 */
	private void newMetricCreator(MethodDeclaration md, MetricUtils mu, Metric newmetric,
			ClassOrInterfaceDeclaration cid) {
		newmetric.setId(id++);
		newmetric.setMethod_package(mu.getPackageName());
		newmetric.setMethod_name(mu.getMethodName(md));
		newmetric.setLOC_method(mu.getLOC_Method(md));
		newmetric.setCYCLO_method(mu.getCYCLO_Method(md));
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
