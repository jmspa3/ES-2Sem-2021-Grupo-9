package metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

public class MetricInfo {

	// Attributes
	private ArrayList<String> pathnames;
	private List<Metric> methodList;

	// Constructor
	public MetricInfo(String path) {
		this.pathnames = new ArrayList<String>();
		this.methodList = new ArrayList<Metric>();
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
	private List<Metric> getInfoByJavaFIle(String path, int id) throws FileNotFoundException {

		CompilationUnit cu = StaticJavaParser.parse(new File(path));
		List<Metric> consAndMethodInfo = new ArrayList<Metric>();

		// get constructors information
		VoidVisitor<List<Metric>> constructorCollector = new ConstructorInfo(cu, id);
		constructorCollector.visit(cu, consAndMethodInfo);

		// get methods information
		VoidVisitor<List<Metric>> methodCollector = new MethodInfo(cu, id + consAndMethodInfo.size());
		methodCollector.visit(cu, consAndMethodInfo);

		setWMC(consAndMethodInfo);

		return consAndMethodInfo;
	}

	// returns metrics for every java file
	public List<Metric> getMetrics() throws FileNotFoundException {
		int count = 1;

		for (String p : pathnames) {
			this.methodList.addAll(getInfoByJavaFIle(p, count));
			count += getInfoByJavaFIle(p, count).size();
		}

		return methodList;
	}

	// set the resulting WMC for each class
	private List<Metric> setWMC(List<Metric> consAndMethodInfo) {

		Hashtable<String, Integer> dict = new Hashtable<String, Integer>();

		for (int i = 0; i < consAndMethodInfo.size(); i++) {
			if (!dict.containsKey(consAndMethodInfo.get(i).getClass_Name())) {
				dict.put(consAndMethodInfo.get(i).getClass_Name(),
						Integer.parseInt(consAndMethodInfo.get(i).getCYCLO_method()));
			} else {
				dict.replace(consAndMethodInfo.get(i).getClass_Name(),
						dict.get(consAndMethodInfo.get(i).getClass_Name())
								+ Integer.parseInt(consAndMethodInfo.get(i).getCYCLO_method()));
			}
		}

		consAndMethodInfo.forEach(n -> n.setWMC_class(dict.get(n.getClass_Name())));

		return consAndMethodInfo;
	}

	// print metrics (to be removed)
	private void showMetrics(List<Metric> methodList) throws FileNotFoundException {
		methodList.forEach(n -> System.out.println("method id: " + n.getId() + "\n" + "package: "
				+ n.getMethod_package() + "\n" + "class: " + n.getClass_Name() + "\n" + "method: " + n.getMethod_name()
				+ "\n" + "LOC_class: " + n.getLOC_class() + "\n" + "LOC_method: " + n.getLOC_method() + "\n"
				+ "CYCLO_method: " + n.getCYCLO_method() + "\n" + "NOM_class: " + n.getNOM_class() + "\n"
				+ "WMC_class: " + n.getWMC_class() + "\n"));
	}

	// prints metrics for every java file (to be removed)
	public void runTroughJavaFilesPrint() throws FileNotFoundException {
		showMetrics(getMetrics());
	}

	// just for testing (to be removed)
	public static void main(String[] args) throws FileNotFoundException {
		MetricInfo tm = new MetricInfo("/Users/nunodias/Documents/jasml_0.10");
		tm.runTroughJavaFilesPrint();
	}
}
