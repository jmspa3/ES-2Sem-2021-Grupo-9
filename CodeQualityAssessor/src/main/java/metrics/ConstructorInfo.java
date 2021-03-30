package metrics;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ConstructorInfo extends VoidVisitorAdapter<List<ArrayList<String>>> {

	private CompilationUnit cu;
	private int id;

	public ConstructorInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;

	}

	// visit all constructors and return all info about each one
	@Override
	public void visit(ConstructorDeclaration c, List<ArrayList<String>> collector) {
		super.visit(c, collector);

		this.id++;

		ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) c.getParentNode().get();
		
		ArrayList<String> temp = new ArrayList<String>();

		boolean isNested = false;
		String className;
		String packageName;
		String constructorName;
		int classLines;
		int methodLines;
		int cycloMethod;
		int NOM_class;
		
		if(cid.isNestedType()) {
			className = getClassName() + "." + cid.getNameAsString();
			packageName = getPackageName();
			constructorName = getConstructorName(c);
			classLines = getLOC_Inner_class(cid);
			methodLines = getLOC_method(c);
			cycloMethod = getCYCLO_method(c);
			isNested = true;
			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
		} else {
			className = getClassName();
			packageName = getPackageName();
			constructorName = getConstructorName(c);
			classLines = getLOC_class();
			methodLines = getLOC_method(c);
			cycloMethod = getCYCLO_method(c);
			isNested = false;
			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
		}
		
		temp.add(Integer.toString(id));
		temp.add(packageName);
		temp.add(className);
		temp.add(constructorName);
		temp.add(Integer.toString(classLines));
		temp.add(Integer.toString(methodLines));
		temp.add(Integer.toString(cycloMethod));
		temp.add(Boolean.toString(isNested));
		temp.add(Integer.toString(NOM_class));
		
		collector.add(temp);
		
	}
	


	// get class name
	private String getClassName() {
		return cu.getPrimaryTypeName().get();
	}

	// get constructor name
	private String getConstructorName(ConstructorDeclaration c) {
		return c.getDeclarationAsString(false, false, false);
	}

	// get class number of lines
	private int getLOC_class() {
		int classBegin = cu.getClassByName(getClassName()).get().getBegin().get().line;
		int classEnd = cu.getEnd().get().line;
		return classEnd - classBegin + 1;
	}
	
	// get class number of lines
		private int getLOC_Inner_class(ClassOrInterfaceDeclaration cid) {
			int classBegin = cid.getBegin().get().line;
			int classEnd = cid.getEnd().get().line;
			return classEnd - classBegin + 1;
		}

	// get package name
	private String getPackageName() {
		String packageName;
		if (!(cu.getPackageDeclaration().orElse(null) == null)) {
			int iend = cu.getPackageDeclaration().get().toString().split(" ")[1].indexOf(";");
			packageName = cu.getPackageDeclaration().get().toString().split(" ")[1].substring(0, iend);
		} else {
			packageName = "(default package)";
		}
		return packageName;
	}

	// get method number of lines
	private int getLOC_method(ConstructorDeclaration c) {
		int begin = c.getBegin().get().line;
		int end = c.getEnd().get().line;
		return end - begin + 1;
	}

	private int getCYCLO_method(ConstructorDeclaration c) {
		int i = 1;

		i += c.findAll(IfStmt.class).size();
		i += c.findAll(WhileStmt.class).size();
		i += c.findAll(ForStmt.class).size();
		i += c.findAll(ForEachStmt.class).size();
		i += c.findAll(DoStmt.class).size();

		for (int j = 0; j < c.findAll(SwitchStmt.class).size(); j++) {
			i += c.findAll(SwitchStmt.class).get(j).getEntries().size();
		}

		i += StringUtils.countMatches(c.toString(), "&&");
		i += StringUtils.countMatches(c.toString(), "||");

		return i;
	}

}
