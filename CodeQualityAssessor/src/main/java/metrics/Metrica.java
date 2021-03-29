package metrics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;

public class Metrica {

	public static List<Metric> Ciclo_complex(String filepath) {
		try {
			File file = new File(filepath);

			List<Metric> fileMetrics = new ArrayList<Metric>();

			// node geral
			CompilationUnit compilationUnit = StaticJavaParser.parse(file);
			// node da class
			ClassOrInterfaceDeclaration fileText = compilationUnit.getClassByName(compilationUnit.getPrimaryTypeName().get()).get();
			// node dos methods
			List<MethodDeclaration> methodList = fileText.getMethods();

			// Package, Class declarations
			String packageDec = compilationUnit.getPackageDeclaration().get().toString();
			String classDec = fileText.getNameAsString();

			// Lista de metricas para o ficheiro fornecido
			for (MethodDeclaration method : methodList) {
				Metric i = new Metric();

				// Method declaration
				String loopMethodDec = method.getNameAsString() + "(" + method.getParameters() + ")";
				i.setM_package(packageDec);
				i.setM_class(classDec);
				i.setM_method(loopMethodDec);

				// If's
				method.findAll(IfStmt.class).forEach(f -> i.incrementCount());
				// method.findAll(IfStmt.class).forEach(f ->
				// System.out.println(StringUtils.countMatches(f.getCondition().toString(),
				// "&&")));
				// Iterative
				method.findAll(WhileStmt.class).forEach(f -> i.incrementCount());
				// method.findAll(WhileStmt.class).forEach(f ->
				// System.out.println(StringUtils.countMatches(f.getCondition().toString(),
				// "&&")));
				method.findAll(ForStmt.class).forEach(f -> i.incrementCount());
				method.findAll(ForEachStmt.class).forEach(f -> i.incrementCount());
				method.findAll(DoStmt.class).forEach(f -> i.incrementCount());
				// Switch
				method.findAll(SwitchStmt.class).forEach(f -> f.getEntries().forEach(g -> i.incrementCount()));
				// method.findAll(SwitchStmt.class).forEach(f->i.incrementCount());
				// Boolean Conditionals
				i.addCount(StringUtils.countMatches(method.toString(), "&&"));
				i.addCount(StringUtils.countMatches(method.toString(), "||"));
				fileMetrics.add(i);
			}
			
			return fileMetrics;
		} catch (Exception e) {
			System.out.println("Something Went Terribly Wrong");
			return null;
		}

	}

}
