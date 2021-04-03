package metrics;

import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
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
			String[] arr = md.getDeclarationAsString(false, false, false).split(" ");
			arr = Arrays.copyOfRange(arr, 1, arr.length);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]);
			}
			str = sb.toString();
		} else if (o instanceof ConstructorDeclaration) {
			ConstructorDeclaration cd = (ConstructorDeclaration) o;
			str = cd.getDeclarationAsString(false, false, false);
		}
		return str;
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
		int begin = 0;
		int end = 0;
		if (o instanceof MethodDeclaration) {
			begin = ((MethodDeclaration) o).getBegin().get().line;
			end = ((MethodDeclaration) o).getEnd().get().line;
		} else if (o instanceof ConstructorDeclaration) {
			begin = ((ConstructorDeclaration) o).getBegin().get().line;
			end = ((ConstructorDeclaration) o).getEnd().get().line;
		}
		return end - begin + 1;
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