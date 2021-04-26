package metrics;

import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorInfo extends VoidVisitorAdapter<List<Metric>> {

	private CompilationUnit cu;
	private int id;

	public ConstructorInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	// visit all constructors and return all info about each one
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
