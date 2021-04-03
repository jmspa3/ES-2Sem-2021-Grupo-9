package metrics;

import java.util.ArrayList;
import java.util.List;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodInfo extends VoidVisitorAdapter<List<ArrayList<String>>> {

	private CompilationUnit cu;
	private int id;

	public MethodInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	// visit all methods and return all info about each one
	@Override
	public void visit(MethodDeclaration md, List<ArrayList<String>> collector) {
		super.visit(md, collector);
		MetricUtils mu = new MetricUtils(cu);

		this.id++;

		String className;
		String packageName;
		String constructorName;
		int classLines;
		int methodLines;
		int cycloMethod;
		int NOM_class;

		ArrayList<String> temp = new ArrayList<String>();

		ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) md.getParentNode().get();

		packageName = mu.getPackageName();
		constructorName = mu.getMethodName(md);
		methodLines = mu.getLOC_Method(md);
		cycloMethod = mu.getCYCLO_Method(md);
		
		if (cid.isNestedType()) {
			className = mu.getClassName() + "." + cid.getNameAsString();
			classLines = mu.getLOC_Inner_class(cid);
			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
		} else {
			className = mu.getClassName();
			classLines = mu.getLOC_class();
			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
		}

		temp.add(Integer.toString(id));
		temp.add(packageName);
		temp.add(className);
		temp.add(constructorName);
		temp.add(Integer.toString(classLines));
		temp.add(Integer.toString(methodLines));
		temp.add(Integer.toString(cycloMethod));
		temp.add(Integer.toString(NOM_class));

		collector.add(temp);
	}

}
