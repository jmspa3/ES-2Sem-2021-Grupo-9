package metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

public class LOC_class {
	
	private List<Metric> fileMetrics;
	private CompilationUnit compilationUnit;

	public LOC_class(File file) throws FileNotFoundException {
		 this.fileMetrics = new ArrayList<Metric>();
		 this.compilationUnit = StaticJavaParser.parse(file);
	}
	
}
