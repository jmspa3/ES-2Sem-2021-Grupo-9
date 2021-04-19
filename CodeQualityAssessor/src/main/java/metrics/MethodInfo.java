package metrics;

import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodInfo extends VoidVisitorAdapter<List<Metric>> {

	private CompilationUnit cu;
	private int id;

	public MethodInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	// visit all methods and return all info about each one
	@Override
	public void visit(MethodDeclaration md, List<Metric> collector) {
		super.visit(md, collector);
		MetricUtils mu = new MetricUtils(cu);
		Metric newmetric = new Metric();
		
//		this.id++;

		ArrayList<String> temp = new ArrayList<String>();
		
		ClassOrInterfaceDeclaration cid = null;
		if(md.getParentNode().isPresent()) {
			if(md.getParentNode().get() instanceof ClassOrInterfaceDeclaration) {
				cid =(ClassOrInterfaceDeclaration) md.getParentNode().get();
			} else {
				return;
			}
		} else {
			return;
		}

//		packageName = mu.getPackageName();
//		constructorName = mu.getMethodName(md);
//		methodLines = mu.getLOC_Method(md);
//		cycloMethod = mu.getCYCLO_Method(md);
		newmetric.setId(id++);
		newmetric.setMethod_package(mu.getPackageName());
		newmetric.setMethod_name(mu.getMethodName(md));
		newmetric.setLOC_method(mu.getLOC_Method(md));
		newmetric.setCYCLO_method(mu.getCYCLO_Method(md));
		
		if (cid.isNestedType()) {
//			className = mu.getClassName() + "." + cid.getNameAsString();
//			classLines = mu.getLOC_Inner_class(cid);
//			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
			newmetric.setClass_Name(mu.getClassName() + "." + cid.getNameAsString());
			newmetric.setLOC_class(mu.getLOC_Inner_class(cid));
			newmetric.setNOM_class((cid.getConstructors().size() + cid.getMethods().size()));
		} else {
//			className = mu.getClassName();
//			classLines = mu.getLOC_class();
//			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
			newmetric.setClass_Name(mu.getClassName());
			newmetric.setLOC_class(mu.getLOC_class());
			newmetric.setNOM_class((cid.getConstructors().size() + cid.getMethods().size()));
		}

//		temp.add(Integer.toString(id));
//		temp.add(packageName);
//		temp.add(className);
//		temp.add(constructorName);
//		temp.add(Integer.toString(classLines));
//		temp.add(Integer.toString(methodLines));
//		temp.add(Integer.toString(cycloMethod));
//		temp.add(Integer.toString(NOM_class));

		collector.add(newmetric);
	}

}
