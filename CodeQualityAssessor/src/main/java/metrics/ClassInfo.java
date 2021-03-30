package metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class ClassInfo extends VoidVisitorAdapter<List<ArrayList<String>>> {

	private CompilationUnit cu;
	private int id;

	public ClassInfo(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	@Override
	public void visit(ClassOrInterfaceDeclaration n, List<ArrayList<String>> arg) {
		super.visit(n, arg);

		ArrayList<String> temp = new ArrayList<String>();

		String className = null;
		String packageName = null;
		String methodName = null;
		int classLines = 0;
		int methodLines = 0;
		int cycloMethod = 0;

//		List<BodyDeclaration<?>> members = n.getMembers();
//		for (BodyDeclaration<?> member : members) {
		if (n.isClassOrInterfaceDeclaration()) {
			if (n.isNestedType()) {
				//System.out.println("class Name: " + n.asClassOrInterfaceDeclaration().getNameAsString());
				for (ConstructorDeclaration constructor : n.asTypeDeclaration().asClassOrInterfaceDeclaration()
						.getConstructors()) {

					className = cu.getPrimaryTypeName().get() + "."
							+ n.asClassOrInterfaceDeclaration().getNameAsString();
					packageName = getPackageName();
					methodName = constructor.getNameAsString();

					int classBegin = n.asClassOrInterfaceDeclaration().getBegin().get().line;
					int classEnd = n.asClassOrInterfaceDeclaration().getEnd().get().line;
					classLines = classEnd - classBegin + 1;

					int methodBegin = constructor.getBegin().get().line;
					int methodEnd = constructor.getEnd().get().line;
					methodLines = methodEnd - methodBegin + 1;

					cycloMethod = getCYCLO(constructor);

//						System.out.println("class name: " + className);
//						System.out.println("package name: " + packageName);
//						System.out.println("method name: " + methodName);
//						System.out.println("class Lines: " + classLines);
//						System.out.println("method lines: " + methodLines);
//						System.out.println("cyclo: " + cycloMethod + "\n");

				}

				for (MethodDeclaration method : n.asClassOrInterfaceDeclaration().getMethods()) {
					className = cu.getPrimaryTypeName().get() + "."
							+ n.asClassOrInterfaceDeclaration().getNameAsString();
					packageName = getPackageName();
					methodName = method.getNameAsString();

					int classBegin = n.asClassOrInterfaceDeclaration().getBegin().get().line;
					int classEnd = n.asClassOrInterfaceDeclaration().getEnd().get().line;
					classLines = classEnd - classBegin + 1;

					int methodBegin = method.getBegin().get().line;
					int methodEnd = method.getEnd().get().line;
					methodLines = methodEnd - methodBegin + 1;

					cycloMethod = getCYCLO(method);

//						System.out.println("class name: " + className);
//						System.out.println("package name: " + packageName);
//						System.out.println("method name: " + methodName);
//						System.out.println("class Lines: " + classLines);
//						System.out.println("method lines: " + methodLines);
//						System.out.println("cyclo: " + cycloMethod + "\n");

				}
			}

		}

		temp.add(Integer.toString(id));
		temp.add(packageName);
		temp.add(className);
		temp.add(methodName);
		temp.add(Integer.toString(classLines));
		temp.add(Integer.toString(methodLines));
		temp.add(Integer.toString(cycloMethod));
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

	private int getCYCLO(Object methOrCons) {
		Object mcd = null;
		int i = 1;
		if (methOrCons instanceof MethodDeclaration) {
			mcd = (MethodDeclaration) methOrCons;
		} else if (methOrCons instanceof ConstructorDeclaration) {
			mcd = (ConstructorDeclaration) methOrCons;
		}
		i += ((Node) mcd).findAll(IfStmt.class).size();
		i += ((Node) mcd).findAll(WhileStmt.class).size();
		i += ((Node) mcd).findAll(ForStmt.class).size();
		i += ((Node) mcd).findAll(ForEachStmt.class).size();
		i += ((Node) mcd).findAll(DoStmt.class).size();

		for (int j = 0; j < ((Node) mcd).findAll(SwitchStmt.class).size(); j++) {
			i += ((Node) mcd).findAll(SwitchStmt.class).get(j).getEntries().size();
		}

		i += StringUtils.countMatches(mcd.toString(), "&&");
		i += StringUtils.countMatches(mcd.toString(), "||");
		return i;

	}

}
