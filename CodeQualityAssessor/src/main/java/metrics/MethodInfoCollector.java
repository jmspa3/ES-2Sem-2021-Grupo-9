package metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.utils.Pair;

public class MethodInfoCollector extends VoidVisitorAdapter<List<ArrayList<String>>> {

	private CompilationUnit cu;
	private int id;
	

	public MethodInfoCollector(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	// visit all methods and return all info about each one
	@Override
	public void visit(MethodDeclaration md, List<ArrayList<String>> collector) {
		super.visit(md, collector);

		this.id++;

		ClassOrInterfaceDeclaration cid = (ClassOrInterfaceDeclaration) md.getParentNode().get();

		ArrayList<String> temp = new ArrayList<String>();

		boolean isNested = false;
		String className;
		String packageName;
		String constructorName;
		int classLines;
		int methodLines;
		int cycloMethod;
		int NOM_class;

		if (cid.isNestedType()) {	
			className = getClassName() + "." + cid.getNameAsString();
			packageName = getPackageName();
			constructorName = getMethodName(md);
			classLines = getLOC_Inner_class(cid);
			methodLines = getLOC_method(md);
			cycloMethod = getCYCLO_method(md);
			isNested = true;
			NOM_class = (cid.getConstructors().size() + cid.getMethods().size());
		} else {
			className = getClassName();
			packageName = getPackageName();
			constructorName = getMethodName(md);
			classLines = getLOC_class();
			methodLines = getLOC_method(md);
			cycloMethod = getCYCLO_method(md);
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

	// get method name
	private String getMethodName(MethodDeclaration md) {
		String[] arr = md.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 1, arr.length);
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i]);
		}

		String str = sb.toString();
		return str;
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
	private int getLOC_method(MethodDeclaration md) {
		int begin = md.getBegin().get().line;
		int end = md.getEnd().get().line;
		return end - begin + 1;
	}

	private int getCYCLO_method(MethodDeclaration md) {
		int i = 1;
		
		
		i += md.findAll(IfStmt.class).size();
		i += md.findAll(WhileStmt.class).size();
		i += md.findAll(ForStmt.class).size();
		i += md.findAll(ForEachStmt.class).size();
		i += md.findAll(DoStmt.class).size();

		for (int j = 0; j < md.findAll(SwitchStmt.class).size(); j++) {
			i += md.findAll(SwitchStmt.class).get(j).getEntries().size();
		}

		i += StringUtils.countMatches(md.toString(), "&&");
		i += StringUtils.countMatches(md.toString(), "||");	
		return i;
	}

}
