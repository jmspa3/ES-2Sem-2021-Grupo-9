package metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class Metrics {

	private ArrayList<String> pathnames;

	// Constructor
	public Metrics(String path) {
		this.pathnames = new ArrayList<String>();
		findJavaFilePaths(new File(path));
	}

	// Used to find all .java file paths
	public void findJavaFilePaths(File file) {
		File[] list = file.listFiles();
		if (list != null) {
			for (File fil : list) {
				if (fil.isDirectory()) {
					findJavaFilePaths(fil);
				} else if (fil.getName().endsWith(".java")) {
					pathnames.add(fil.getAbsolutePath());
				}
			}
		}
	}

	// print metrics (to be removed)
	private void showMetrics(List<ArrayList<String>> methodList) throws FileNotFoundException {
		methodList.forEach(n -> System.out.println("method id: " + n.get(0) + "\n" + "package: " + n.get(1) + "\n"
				+ "class: " + n.get(2) + "\n" + "LOC_class: " + n.get(3) + "\n" + "method: " + n.get(4) + "\n"
				+ "LOC_method: " + n.get(5) + "\n" + "NOM_class: " + n.get(6) + "\n"));
	}

	// return an list of arraylists with the metrics and information for each method
	private List<ArrayList<String>> getMetrics(String path, int id) throws FileNotFoundException {
		CompilationUnit cu = StaticJavaParser.parse(new File(path));

		// get method information of lines of each one
		List<ArrayList<String>> consAndMethodInfo = new ArrayList<ArrayList<String>>();
		VoidVisitor<List<ArrayList<String>>> constructorCollector = new ConstructorInfo(cu, id);
		VoidVisitor<List<ArrayList<String>>> methodCollector = new MethodInfoCollector(cu, id + 1);
		constructorCollector.visit(cu, consAndMethodInfo);
		methodCollector.visit(cu, consAndMethodInfo);
		
		consAndMethodInfo.forEach(n -> n.add(Integer.toString(consAndMethodInfo.size())));
		return consAndMethodInfo;
	}

	// prints metrics for every java file (to be removed)
	public void runTroughJavaFilesPrint() throws FileNotFoundException {
		int count = 0;
		for (String p : pathnames) {
			showMetrics(getMetrics(p, count));
			int last = getMetrics(p, count).size() - 1;
			if (last >= 0) {
				count = Integer.parseInt(getMetrics(p, count).get(last).get(0));
			}
			// count = Integer.parseInt(getMetrics(p, count).get(0).get(0));
		}
	}

	// returns metrics for every java file
	public void runTroughJavaFiles() throws FileNotFoundException {
		int count = 0;
		for (String p : pathnames) {
			getMetrics(p, count);
			count = Integer.parseInt(getMetrics(p, count).get(getMetrics(p, count).size() - 1).get(0));
		}
	}

	// just for testing (to be removed)
	public static void main(String[] args) throws FileNotFoundException {

		Metrics tm = new Metrics("D:\\Eclipse-Workspace\\jasml_0.10");
		tm.runTroughJavaFilesPrint();
		// tm.runTroughJavaFiles();

	}
}
