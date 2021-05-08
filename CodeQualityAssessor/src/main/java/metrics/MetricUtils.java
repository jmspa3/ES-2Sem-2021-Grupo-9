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

/**
 * 
 * Helper class to the visitor classes.
 * 
 * @author Nuno Dias
 *
 */
public class MetricUtils {

	private CompilationUnit cu;

	/**
	 * Constructor of MetricUtils class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param cu the file we are running trough
	 * @see CompilationUnit
	 */
	public MetricUtils(CompilationUnit cu) {
		this.cu = cu;
	}

	/**
	 * Retrieve the class name, given the CompilationUnit
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link String}
	 * @see CompilationUnit
	 */
	protected String getClassName() {
		return cu.getPrimaryTypeName().get();
	}

	/**
	 * Retrieve the method name, given a {@link MethodDeclaration} or a
	 * {@link ConstructorDeclaration} instance
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor {@link MethodDeclaration} or
	 *                            {@link ConstructorDeclaration} instance
	 * @return {@link String}
	 * @see Object
	 */
	protected String getMethodName(Object methodOrConstructor) {
		String str = "";
		if (methodOrConstructor instanceof MethodDeclaration) {

			MethodDeclaration md = (MethodDeclaration) methodOrConstructor;
			for (Parameter n : md.getParameters()) {
				if (n.toString().contains(".")) {
					int i = n.toString().indexOf(".") + 1;
					String s = n.toString().substring(i).split(" ")[0];
					md.getParameterByName(n.getNameAsString()).get().setType(s);
				}
			}
			String[] arr = getMethod_Name_Aux(methodOrConstructor);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]);
			}
			str = sb.toString();
		} else if (methodOrConstructor instanceof ConstructorDeclaration) {

			ConstructorDeclaration cd = (ConstructorDeclaration) methodOrConstructor;
			for (Parameter n : cd.getParameters()) {
				if (n.toString().contains(".")) {
					int i = n.toString().indexOf(".") + 1;
					String s = n.toString().substring(i).split(" ")[0];
					cd.getParameterByName(n.getNameAsString()).get().setType(s);
				}
			}
			String[] arr = getConstructor_Name_Aux(methodOrConstructor);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < arr.length; i++) {
				sb.append(arr[i]);
			}
			str = sb.toString();
		}
		return str;
	}

	/**
	 * Auxiliary function for the getMethodName method, returning an list with the
	 * components of a method declaration name
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor instance of {@link MethodDeclaration}
	 * @return String[]
	 * @see Object
	 */
	private String[] getMethod_Name_Aux(Object methodOrConstructor) {
		MethodDeclaration md = (MethodDeclaration) methodOrConstructor;
		String[] arr = md.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 1, arr.length);
		return arr;
	}

	/**
	 * Auxiliary function for the getMethodName method, returning an list with the
	 * components of a constructor declaration name
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor instance of {@link ConstructorDeclaration}
	 * @return String[]
	 * @see Object
	 */
	private String[] getConstructor_Name_Aux(Object methodOrConstructor) {
		ConstructorDeclaration cd = (ConstructorDeclaration) methodOrConstructor;
		String[] arr = cd.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 0, arr.length);
		return arr;
	}

	/**
	 * This method returns the number of lines of code of a class, using the already
	 * initialized instance of {@link CompilationUnit}
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Integer}
	 * @see CompilationUnit
	 */
	protected int getLOC_class() {
		int classBegin = cu.getClassByName(getClassName()).get().getBegin().get().line;
		int classEnd = cu.getEnd().get().line;
		return classEnd - classBegin + 1;
	}

	/**
	 * This method returns the number of lines of code of an inner class, using the
	 * already initialized instance of {@link CompilationUnit}
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return {@link Integer}
	 * @see ClassOrInterfaceDeclaration
	 */
	protected int getLOC_Inner_class(ClassOrInterfaceDeclaration cid) {
		int classBegin = cid.getBegin().get().line;
		int classEnd = cid.getEnd().get().line;
		return classEnd - classBegin + 1;
	}

	/**
	 * This method returns the package name of the {@link CompilationUnit} we are
	 * visiting
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return int
	 * @see CompilationUnit
	 */
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

	/**
	 * This method returns the number of lines of code of a method/constructor
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor {@link MethodDeclaration} or
	 *                            {@link ConstructorDeclaration} instance
	 * @return {@link Integer}
	 */
	protected int getLOC_Method(Object methodOrConstructor) {
		int begin = getLOC_Method_Begin_Line(methodOrConstructor);
		int end = getLOC_Method_End_Line(methodOrConstructor);
		return end - begin + 1;
	}

	/**
	 * Auxiliary method of getLOC_Method() that returns the last line of a method
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor {@link MethodDeclaration} or
	 *                            {@link ConstructorDeclaration} instance
	 * @return {@link Integer}
	 */
	private int getLOC_Method_End_Line(Object methodOrConstructor) {
		int end = 0;
		if (methodOrConstructor instanceof MethodDeclaration) {
			end = ((MethodDeclaration) methodOrConstructor).getEnd().get().line;
		} else if (methodOrConstructor instanceof ConstructorDeclaration) {
			end = ((ConstructorDeclaration) methodOrConstructor).getEnd().get().line;
		}
		return end;
	}

	/**
	 * Auxiliary method of getLOC_Method() that returns the first line of a method
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor {@link MethodDeclaration} or
	 *                            {@link ConstructorDeclaration} instance
	 * @return {@link Integer}
	 */
	private int getLOC_Method_Begin_Line(Object methodOrConstructor) {
		int begin = 0;
		if (methodOrConstructor instanceof MethodDeclaration) {
			begin = ((MethodDeclaration) methodOrConstructor).getBegin().get().line;
		} else if (methodOrConstructor instanceof ConstructorDeclaration) {
			begin = ((ConstructorDeclaration) methodOrConstructor).getBegin().get().line;
		}
		return begin;
	}

	
	/**
	 * Method that return the cyclomatic complexity of a method/constructor
	 * 
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param methodOrConstructor {@link MethodDeclaration} or
	 *                            {@link ConstructorDeclaration} instance
	 * @return {@link Integer}
	 */
	protected int getCYCLO_Method(Object methodOrConstructor) {
		int i = 1;
		if (methodOrConstructor instanceof MethodDeclaration) {
			MethodDeclaration md = (MethodDeclaration) methodOrConstructor;
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
		} else if (methodOrConstructor instanceof ConstructorDeclaration) {
			ConstructorDeclaration cd = (ConstructorDeclaration) methodOrConstructor;
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
