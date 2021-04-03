package metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class Metrics {

	// Attributes
	private ArrayList<String> pathnames;
	private List<ArrayList<String>> methodList;

	// Constructor
	public Metrics(String path) {
		this.pathnames = new ArrayList<String>();
		this.methodList = new ArrayList<ArrayList<String>>();
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

	// return an list of arraylists with the metrics and information for each method
	private List<ArrayList<String>> getInfoByJavaFIle(String path, int id) throws FileNotFoundException {

		CompilationUnit cu = StaticJavaParser.parse(new File(path));

		List<ArrayList<String>> consAndMethodInfo = new ArrayList<ArrayList<String>>();
		
		// get constructors information
		VoidVisitor<List<ArrayList<String>>> constructorCollector = new ConstructorInfo(cu, id);
		constructorCollector.visit(cu, consAndMethodInfo);

		// get methods information
		VoidVisitor<List<ArrayList<String>>> methodCollector = new MethodInfo(cu,
				id + consAndMethodInfo.size());
		methodCollector.visit(cu, consAndMethodInfo);

		Hashtable<String, Integer> dict = new Hashtable<String, Integer>();
		for (int i = 0; i < consAndMethodInfo.size(); i++) {
			if (!dict.containsKey(consAndMethodInfo.get(i).get(2))) {
				dict.put(consAndMethodInfo.get(i).get(2), Integer.parseInt(consAndMethodInfo.get(i).get(6)));
			} else {
				dict.replace(consAndMethodInfo.get(i).get(2),
						dict.get(consAndMethodInfo.get(i).get(2)) + Integer.parseInt(consAndMethodInfo.get(i).get(6)));
			}
		}

		consAndMethodInfo.forEach(n -> n.add(Integer.toString(dict.get(n.get(2)))));

		return consAndMethodInfo;
	}

	// returns metrics for every java file
	public List<ArrayList<String>> getMetrics() throws FileNotFoundException {
		int count = 0;
		for (String p : pathnames) {
			this.methodList.addAll(getInfoByJavaFIle(p, count));
			count += getInfoByJavaFIle(p, count).size();
		}
		return methodList;
	}

	// print metrics (to be removed)
	private void showMetrics(List<ArrayList<String>> methodList) throws FileNotFoundException {
		methodList.forEach(n -> System.out.println("method id: " + n.get(0) + "\n" + "package: " + n.get(1) + "\n"
				+ "class: " + n.get(2) + "\n" + "method: " + n.get(3) + "\n" + "LOC_class: " + n.get(4) + "\n"
				+ "LOC_method: " + n.get(5) + "\n" + "CYCLO_method: " + n.get(6) + "\n" + "NOM_class: " + n.get(7)
				+ "\n" + "WMC_class: " + n.get(8) + "\n"));
	}

	// prints metrics for every java file (to be removed)
	public void runTroughJavaFilesPrint() throws FileNotFoundException {
		showMetrics(getMetrics());
	}

	// just for testing (to be removed)
	public static void main(String[] args) throws FileNotFoundException {
		Metrics tm = new Metrics("/Users/nunodias/Documents/jasml_0.10/src");
		tm.runTroughJavaFilesPrint();
	}
}
