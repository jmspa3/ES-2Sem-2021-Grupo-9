package metrics;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class MetricUtils {

	private CompilationUnit cu;

	public MetricUtils(CompilationUnit cu) {
		this.cu = cu;
	}

	// get class name
	protected String getClassName() {
		return cu.getPrimaryTypeName().get();
	}

	// get method name
	protected String getMethodName(Object o) {
		String str = "";
		if (o instanceof MethodDeclaration) {

			MethodDeclaration md = (MethodDeclaration) o;
			for (Parameter n : md.getParameters()) {
				if (n.toString().contains(".")) {
					int i = n.toString().indexOf(".") + 1;
					String s = n.toString().substring(i).split(" ")[0];
					md.getParameterByName(n.getNameAsString()).get().setType(s);
				}
			}
			String[] arr = getMethod_Name_Aux(o);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]);
			}
			str = sb.toString();
		} else if (o instanceof ConstructorDeclaration) {

			ConstructorDeclaration cd = (ConstructorDeclaration) o;
			for (Parameter n : cd.getParameters()) {
				if (n.toString().contains(".")) {
					int i = n.toString().indexOf(".") + 1;
					String s = n.toString().substring(i).split(" ")[0];
					cd.getParameterByName(n.getNameAsString()).get().setType(s);
				}
			}
			String[] arr = getConstructor_Name_Aux(o);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]);
			}
			str = sb.toString();
		}
		return str;
	}

	private String[] getMethod_Name_Aux(Object o) {
		MethodDeclaration md = (MethodDeclaration) o;
		String[] arr = md.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 1, arr.length);
		return arr;
	}

	private String[] getConstructor_Name_Aux(Object o) {
		ConstructorDeclaration cd = (ConstructorDeclaration) o;
		String[] arr = cd.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 0, arr.length);
		return arr;
	}

	// get class number of lines
	protected int getLOC_class() {
		int classBegin = cu.getClassByName(getClassName()).get().getBegin().get().line;
		int classEnd = cu.getEnd().get().line;
		return classEnd - classBegin + 1;
	}

	// get class number of lines
	protected int getLOC_Inner_class(ClassOrInterfaceDeclaration cid) {
		int classBegin = cid.getBegin().get().line;
		int classEnd = cid.getEnd().get().line;
		return classEnd - classBegin + 1;
	}

	// get package name
	protected String getPackageName() {
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
	protected int getLOC_Method(Object o) {
		int begin = getLOC_Method_Begin_Line(o);
		int end = getLOC_Method_End_Line(o);
		return end - begin + 1;
	}

	private int getLOC_Method_End_Line(Object o) {
		int end = 0;
		if (o instanceof MethodDeclaration) {
			end = ((MethodDeclaration) o).getEnd().get().line;
		} else if (o instanceof ConstructorDeclaration) {
			end = ((ConstructorDeclaration) o).getEnd().get().line;
		}
		return end;
	}

	private int getLOC_Method_Begin_Line(Object o) {
		int begin = 0;
		if (o instanceof MethodDeclaration) {
			begin = ((MethodDeclaration) o).getBegin().get().line;
		} else if (o instanceof ConstructorDeclaration) {
			begin = ((ConstructorDeclaration) o).getBegin().get().line;
		}
		return begin;
	}

	protected int getCYCLO_Method(Object o) {
		int i = 1;
		if (o instanceof MethodDeclaration) {
			MethodDeclaration md = (MethodDeclaration) o;
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
		} else if (o instanceof ConstructorDeclaration) {
			ConstructorDeclaration cd = (ConstructorDeclaration) o;
			i += cd.findAll(IfStmt.class).size();
			i += cd.findAll(WhileStmt.class).size();
			i += cd.findAll(ForStmt.class).size();
			i += cd.findAll(ForEachStmt.class).size();
			i += cd.findAll(DoStmt.class).size();
			for (int j = 0; j < cd.findAll(SwitchStmt.class).size(); j++) {
				i += cd.findAll(SwitchStmt.class).get(j).getEntries().size();
			}
			i += StringUtils.countMatches(cd.toString(), "&&");
			i += StringUtils.countMatches(cd.toString(), "||");
		}
		return i;
	}

}
