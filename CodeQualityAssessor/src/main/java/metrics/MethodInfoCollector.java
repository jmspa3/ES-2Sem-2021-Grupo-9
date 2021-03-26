package metrics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class MethodInfoCollector extends VoidVisitorAdapter<List<ArrayList<String>>> {
	
	private CompilationUnit cu;
	private int id;
	
	public MethodInfoCollector(CompilationUnit cu, int id) {
		this.cu = cu;
		this.id = id;
	}

	@Override
	public void visit(MethodDeclaration md, List<ArrayList<String>> collector) { 
		super.visit(md, collector);
	
		this.id++;
		
		ArrayList<String> temp = new ArrayList<String>();
		
		//get class name
		String className = cu.getPrimaryTypeName().get();
		
		
		//get class number of lines
		int classBegin = Integer.parseInt(cu.getClassByName(cu.getPrimaryTypeName().get()).get().getBegin().toString().split(",")[0].split(" ")[1]);
		int classEnd = Integer.parseInt(cu.getClassByName(cu.getPrimaryTypeName().get()).get().getEnd().toString().split(",")[0].split(" ")[1]);
		int classTotalLines = classEnd - classBegin + 1;
		
		//get package name
		String packageName;
		if(!cu.getPackageDeclaration().equals("")) {
			int iend = cu.getPackageDeclaration().get().toString().split(" ")[1].indexOf(";");
			packageName = cu.getPackageDeclaration().get().toString().split(" ")[1].substring(0, iend);
		} else {
			packageName = "(default package)";
		}

		//get method total lines
		int begin = Integer.parseInt(md.getBegin().get().toString().split(",")[0].split(" ")[1]);
		int end = Integer.parseInt(md.getEnd().get().toString().split(",")[0].split(" ")[1]);
		int total = end  - begin + 1;
		
		temp.add(Integer.toString(id));
		temp.add(packageName);
		temp.add(className);
		temp.add(Integer.toString(classTotalLines));
		
		String[] arr = md.getDeclarationAsString(false, false, false).split(" ");
		arr = Arrays.copyOfRange(arr, 1, arr.length);
		StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < arr.length; i++) {
	       sb.append(arr[i]);
	    }
	    
	    String str = sb.toString();
		temp.add(str);
		temp.add(Integer.toString(total));
		
		collector.add(temp);
	}


}
