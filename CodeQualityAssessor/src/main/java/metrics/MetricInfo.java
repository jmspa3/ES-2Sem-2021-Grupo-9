package metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;

/**
 * <h1>MetricInfo</h1> This is the main class to get the information about
 * Metrics of a certain project.
 * <p>
 * To print the Metrics info in the console, use runTroughJavaFilesPrint(). To
 * get the list of Metrics, use getMetrics().
 * 
 *
 * @author Nuno Dias
 * @version 1.0
 */
public class MetricInfo {

	// Attributes
	private ArrayList<String> pathnames;
	private List<Metric> methodList;

	/**
	 * Constructor of MetricInfo class.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param path String given by the user with the PATH to the project
	 * @see File
	 */
	public MetricInfo(String path) {
		this.pathnames = new ArrayList<String>();
		this.methodList = new ArrayList<Metric>();
		findJavaFilePaths(new File(path));
	}

	/**
	 * This method runs through files in the chosen directory and adds all .java
	 * files PATHS to a list.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param file new File with the given directory PATH
	 * @return Nothing.
	 * @see File
	 */
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

	/**
	 * This method runs through a .java file and return a list of Metric with
	 * information about each class, method and constructor.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @param path file PATH for each .java file, stored in the attribute pathnames
	 * @param id   the id of the starting method/constructor
	 * @return List<Metric> list of metrics with the information about each .java
	 *         file
	 * @see File, Metric
	 */
	private List<Metric> getInfoByJavaFile(String path, int id) throws FileNotFoundException {

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

	/**
	 * This method runs through the list pathnames, passes it to getInfoByJavaFile()
	 * an returns a list of all Metrics within the project PATH given by the user.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return List<Metric> list of metrics
	 * @see Metric
	 */
	public List<Metric> getMetrics() throws FileNotFoundException {
		int count = 1;

		for (String p : pathnames) {
			this.methodList.addAll(getInfoByJavaFile(p, count));
			count += getInfoByJavaFile(p, count).size();
		}

		return methodList;
	}

	/**
	 * This method runs through a list of metrics and sets the WMC Metric, returning
	 * the same list with the added WMC. Used in getInfoByJavaFile() method.
	 * 
	 * @author Nuno Dias
	 * @version 1.0
	 * @param List<Metric> list of metrics
	 * @return List<Metric> list of metrics
	 * @see Metric
	 */
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

	/**
	 * This method runs through a list of metrics and prints each informations in
	 * the console.
	 * 
	 * @author Nuno Dias
	 * @version 1.0
	 * @param List<Metric> list of metrics
	 * @return Nothing.
	 */
	private void showMetrics(List<Metric> methodList) throws FileNotFoundException {
		methodList.forEach(n -> System.out.println("method id: " + n.getId() + "\n" + "package: "
				+ n.getMethod_package() + "\n" + "class: " + n.getClass_Name() + "\n" + "method: " + n.getMethod_name()
				+ "\n" + "LOC_class: " + n.getLOC_class() + "\n" + "LOC_method: " + n.getLOC_method() + "\n"
				+ "CYCLO_method: " + n.getCYCLO_method() + "\n" + "NOM_class: " + n.getNOM_class() + "\n"
				+ "WMC_class: " + n.getWMC_class() + "\n"));
	}

	/**
	 * This method runs through files and prints the Metrics information in the
	 * console, using showMetrics() method.
	 *
	 * @author Nuno Dias
	 * @version 1.0
	 * @return Nothing.
	 */
	public void runTroughJavaFilesPrint() throws FileNotFoundException {
		showMetrics(getMetrics());
	}

//	// just for testing (to be removed)
//	public static void main(String[] args) throws FileNotFoundException {
//		MetricInfo tm = new MetricInfo("/Users/nunodias/Documents/jasml_0.10");
//		tm.runTroughJavaFilesPrint();
//	}
}
