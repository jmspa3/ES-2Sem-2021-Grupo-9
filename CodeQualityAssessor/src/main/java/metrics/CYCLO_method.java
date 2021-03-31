package metrics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.commons.lang3.StringUtils;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.DataKey;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.nodeTypes.SwitchNode;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import com.github.javaparser.symbolsolver.JavaSymbolSolver;

import javassist.compiler.ast.AssignExpr;

public class CYCLO_method {
	
	public static void main(String[] args) {
		try {
			File file = new File("C:\\Users\\Daniel\\git\\ES-2Sem-2021-Grupo-9\\CodeQualityAssessor\\src\\main\\java\\test\\testFile1.java");
			
			List<Metric> fileMetrics = new ArrayList<Metric>();
			
			//node geral
			CompilationUnit compilationUnit = StaticJavaParser.parse(file);
			//node da class
			ClassOrInterfaceDeclaration fileText = compilationUnit.getClassByName(compilationUnit.getPrimaryTypeName().get()).get();
			//node dos methods
			List<MethodDeclaration> methodList = fileText.getMethods();
			
			//Package, Class declarations
			String packageDec = compilationUnit.getPackageDeclaration().get().toString();
			String classDec = fileText.getNameAsString();
			
			
			//TODO
			//Meter count boolean conditions a funcionar
			//Iterar sobre construtores
			
			//Lista de metricas para o ficheiro fornecido	
			for(MethodDeclaration method: methodList) {
				Metric i = new Metric();
				
				//Method declaration
				String loopMethodDec = method.getNameAsString()+"("+method.getParameters()+")";
				i.setM_package(packageDec);
				i.setM_class(classDec);
				i.setM_method(loopMethodDec);
		
				//If's
				method.findAll(IfStmt.class).forEach(f->i.incrementCount());
				//method.findAll(IfStmt.class).forEach(f -> System.out.println(StringUtils.countMatches(f.getCondition().toString(), "&&")));
				//Iterative
				method.findAll(WhileStmt.class).forEach(f->i.incrementCount());
				//method.findAll(WhileStmt.class).forEach(f -> System.out.println(StringUtils.countMatches(f.getCondition().toString(), "&&")));
				method.findAll(ForStmt.class).forEach(f->i.incrementCount());    
				method.findAll(ForEachStmt.class).forEach(f->i.incrementCount());
				method.findAll(DoStmt.class).forEach(f->i.incrementCount());
				//Switch
				method.findAll(SwitchStmt.class).forEach(f-> f.getEntries().forEach(g -> i.incrementCount()));
				//method.findAll(SwitchStmt.class).forEach(f->i.incrementCount());
				//Boolean Conditionals
				i.addCount(StringUtils.countMatches(method.toString(), "&&"));
				i.addCount(StringUtils.countMatches(method.toString(), "||"));
				
				fileMetrics.add(i);
				System.out.println(i);
			}
			
							
		}catch (Exception e) {
			System.out.println("Something Went Terribly Wrong");
		}
		
	}

		
}